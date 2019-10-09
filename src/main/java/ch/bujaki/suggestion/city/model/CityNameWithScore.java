package ch.bujaki.suggestion.city.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityNameWithScore {
	
	/**
	 * A factory method to create {@link CityNameWithScore} instances from {@link City} instances.
	 * 
	 * @return a {@link List} city names augmented with scores.
	 */
	public static List<CityNameWithScore> createFor(List<City> cities) {
		if (cities == null || cities.size() == 0) {
			return Collections.emptyList();
		}
		
		List<CityNameWithScore> result = new ArrayList<>(cities.size());

		for (int i = 0; i < cities.size(); i++) {
			double score = (cities.size() - i) / (double) cities.size();
			result.add( new CityNameWithScore(cities.get(i).getPrettyName(), score) );
		}
		
		return result;
	}

	private String name;
	private double score;
	
	/**
	 * Constructor.
	 * 
	 * @param cityName   The name of the city.
	 * @param score      The score of the suggestion.
	 */
	private CityNameWithScore(String cityName, double score) {
		this.name = cityName;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public double getScore() {
		return score;
	}
}
 