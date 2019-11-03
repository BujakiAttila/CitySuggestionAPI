package ch.bujaki.suggestion.city.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ch.bujaki.suggestion.city.model.City;

/**
 * The {@link CityIndex} class provides indexing capabilities to be able to provide
 * relevant suggestions based on 
 * 
 * After creation:
 *  - The cities to be indexed must be added by calling the addCity method.
 *  - After all the cities were added, the init method builds the index.
 *  - After the init method returns, the index is ready to be used. 
 */
@Service
public class CityIndex {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Map<String, Set<City>> index;
	
	private final Comparator<City> populationComparer = Comparator.comparingLong( city-> city.getPopulation() );
	private final Comparator<City> nameComparer = Comparator.comparing(city -> city.getName(), (a,b) -> a.compareTo(b));
	private final Comparator<? super City> citySorter = populationComparer.reversed().thenComparing(nameComparer);
	
	private final Set<City> citiesByPopulation = new TreeSet<>(citySorter);
	
	/**
	 * @param query   A part of the city name to provide suggestions for.
	 * 
	 * @return an ordered {@link List} of the suggestions with the most relevant
	 *         suggestion being the first item. An empty list is returned, if there
	 *         is no suggestion. (Maximum 10 suggestions are provided)
	 */
	public List<City> getSuggestionsFor(String query) {
		if(! isInitialized()) {
			throw new IllegalStateException("CityIndex must be initialized before getting suggestions.");
		}
		
		String key = query.toLowerCase().trim();
		Set<City> result = index.getOrDefault(key, Collections.emptySet());
		
		return result.stream()
			.limit(10)
			.collect(Collectors.toList());
	}
	
	/**
	 * Adds cities to be indexed. Cities must be called before calling init.
	 * 
	 * @param newCity    the city to be added to the index.
	 */
	public void addCity(City newCity) {
		if(isInitialized()) {
			throw new IllegalStateException("CityIndex is already initialized. Cannot add new cities anymore.");
		}
		
		Objects.requireNonNull(newCity, "The newCity must not be null.");
		
		citiesByPopulation.add(newCity);
	}
	
	/**
	 * Creates the index for the previously added cities.
	 * 
	 * After this method returns, the index is ready to provide suggestions.
	 */
	public void init() {
		index = createIndex();
	}
	
	private boolean isInitialized() {
		return (index != null);
	}
	
	private Map<String, Set<City>> createIndex() {
		logger.info("Creating index.");
		
		Map<String, Set<City>> cityIndex = new HashMap<>();
		for (City city : citiesByPopulation) {
			for (int size = 1; size < city.getName().length() + 1; size++) {
				String keyCandidate = city.getName().substring(0, size).toLowerCase().trim();
				addKeyToIndex(cityIndex, city, keyCandidate);
			}
			
			for (int size = 1; size < city.getAsciiName().length() + 1; size++) {
				String keyCandidate = city.getAsciiName().substring(0, size).toLowerCase().trim();
				addKeyToIndex(cityIndex, city, keyCandidate);
			}
		}
		
		logger.info("Created index for {} cities.", citiesByPopulation.size());
		
		return cityIndex;
	}
	
	private void addKeyToIndex(Map<String, Set<City>> cityIndex, City city, String keyCandidate) {
		Set<City> citiesForKey = cityIndex.getOrDefault(keyCandidate, new TreeSet<>(citySorter));
		
		if (citiesForKey.size() >= 10) {
			return;
		}
		
		citiesForKey.add(city);
		cityIndex.putIfAbsent(keyCandidate, citiesForKey);
	}
}
