package ch.bujaki.suggestion.city.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import ch.bujaki.suggestion.city.model.City;
import ch.bujaki.suggestion.city.model.CityNameWithScore;

@RunWith(SpringRunner.class)
public class CityNameWithScoreTest {
	
	@Test
	public void test_createFor_twoCities() {
		City london = new City("London", "ENG", "London", "UK", 5_000_000L);
		City budapest = new City("Budapest", "15", "Budapest", "HU", 2_000_000L);
		
		var citiesWithScore = CityNameWithScore.createFor( Arrays.asList( london, budapest ) );

		assertEquals(1.0, citiesWithScore.get(0).getScore(), 0.0001);
		assertEquals("London, UK", citiesWithScore.get(0).getName());
		
		assertEquals(0.5, citiesWithScore.get(1).getScore(), 0.0001);
		assertEquals("Budapest, Hungary", citiesWithScore.get(1).getName());
	}

	@Test
	public void test_createFor_invalidInput() {
		var citiesForNull = CityNameWithScore.createFor(null);
		assertNotNull(citiesForNull);
		assertEquals(0, citiesForNull.size());
		
		var citiesForEmptyList = CityNameWithScore.createFor( Arrays.asList( ));
		assertNotNull(citiesForEmptyList);
		assertEquals(0, citiesForEmptyList.size());
	}
}
