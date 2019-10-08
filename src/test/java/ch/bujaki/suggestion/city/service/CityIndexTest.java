package ch.bujaki.suggestion.city.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import ch.bujaki.suggestion.city.model.City;
import ch.bujaki.suggestion.city.service.CityIndex;

@RunWith(SpringRunner.class)
public class CityIndexTest {

	// Object under test
	CityIndex index;
	
	// Cities with special characters
	City gyor = new City("Győr", "", "Gyor", "HU", 120_000L);
	City gyorMenfocsanak = new City("Győr-Ménfőcsanak", "", "Gyor-Menfocsanak", "HU", 12_000L);
	City gyorMarcalvaros = new City("Győr-Marcalvaros", "", "Gyor-Marcalvaros", "HU", 65_000L);

	City londonInOhio = new City("London", "OH", "London", "US", 650_000L);
	
	
	@Before
	public void before() {
		index = new CityIndex();

		index.addCity(gyor);
		index.addCity(gyorMenfocsanak);
		index.addCity(gyorMarcalvaros);
		
		index.addCity(londonInOhio);
	}
	
	@Test
	public void test_asciiNameCanBeUsed() {
		// Given
		index.init();
		
		// When
		List<City> suggestionsForGyor = index.getSuggestionsFor("Gyor");
		
		// Then
		assertEquals(3, suggestionsForGyor.size());
		
		assertEquals(gyor, suggestionsForGyor.get(0));
		assertEquals(gyorMarcalvaros, suggestionsForGyor.get(1));
		assertEquals(gyorMenfocsanak, suggestionsForGyor.get(2));
	}
	
	@Test
	public void test_fullNameCanBeUsed() {
		// Given
		index.init();
		
		// When
		List<City> suggestions = index.getSuggestionsFor("Győr-Ménfőcsanak");
		
		// Then
		assertEquals(1, suggestions.size());
		
		assertEquals(gyorMenfocsanak, suggestions.get(0));
		assertEquals(suggestions.get(0).getPrettyName(), "Győr-Ménfőcsanak, Hungary");
	}
	
	@Test
	public void test_stateIsIncludedForUSCities() {
		// Given
		index.init();
		
		// When
		List<City> suggestions = index.getSuggestionsFor("London");
		
		// Then
		assertEquals(1, suggestions.size());

		assertEquals(londonInOhio, suggestions.get(0));
		assertEquals(suggestions.get(0).getPrettyName(), "London, OH, United States");
	}

	@Test
	public void test_onlyTop10ResultsAreReturned() {
		// Given: 100 fake cities with name starting with "London"
		for (long i = 1; i < 100; i++) {
			index.addCity(new City("London" + i, "OH", "London" + i, "US", 650_000L - i));
		}
		
		index.init();
		
		// When: searching for London
		List<City> suggestions = index.getSuggestionsFor("London");
		
		// Then: only the top 10 suggestions are returned 
		//       and the city with the highest population is the first.
		assertEquals(10, suggestions.size());
		assertEquals(londonInOhio, suggestions.get(0));
	}
	
	@Test(expected = NullPointerException.class)
	public void test_nullCityCannotBeAdded() {
		index.addCity(null);
	}

	@Test(expected = IllegalStateException.class)
	public void test_theIndexMustBeInitializedBeforeRequestingSuggestions() {
		new CityIndex().getSuggestionsFor("London");
	}
	
	@Test(expected = IllegalStateException.class)
	public void test_mayNotAddCitiesAfterInitialization() {
		index.init();
		
		index.addCity(londonInOhio);
	}
}
