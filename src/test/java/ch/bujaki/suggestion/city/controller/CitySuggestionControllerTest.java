package ch.bujaki.suggestion.city.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.junit4.SpringRunner;

import ch.bujaki.suggestion.city.controller.CitySuggestionController;
import ch.bujaki.suggestion.city.repository.CityReader;
import ch.bujaki.suggestion.city.service.CityIndex;

@RunWith(SpringRunner.class)
public class CitySuggestionControllerTest {
	
	private final static double DELTA = 0.01;
	
	// Object under test
	CitySuggestionController controller;
	
	@Before
	public void before() throws Exception {
		controller = new CitySuggestionController();
		
		controller.index = new CityIndex();
		controller.reader = new CityReader();

		URL testFile = CitySuggestionControllerTest.class.getResource("/testCities.tsv");
		controller.reader.resourceFile = new UrlResource(testFile);

		controller.reader.init();
		controller.init();
	}
	
	@Test
	public void test_integration() throws IOException {
		// When: requesting suggestions for "Sant"
		var suggestions = controller.getSuggestions("Sant");
		
		// Then: three cities are found and the scores are decreasing.
		var suggestedCities = suggestions.get("suggestions");
		assertEquals(3, suggestedCities.size());

		assertEquals("Sant Julià de Lòria, Andorra", suggestedCities.get(0).getName());
		assertEquals(1.0, suggestedCities.get(0).getScore(), DELTA);

		assertEquals("Sant Julià de Cerdanyola, Spain", suggestedCities.get(1).getName());
		assertEquals(0.66, suggestedCities.get(1).getScore(), DELTA);

		assertEquals("Sant Julià de Vilatorta, Spain", suggestedCities.get(2).getName());
		assertEquals(0.33, suggestedCities.get(2).getScore(), DELTA);
		
	}
}
