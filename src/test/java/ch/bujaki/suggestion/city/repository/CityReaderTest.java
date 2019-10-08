package ch.bujaki.suggestion.city.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.junit4.SpringRunner;

import ch.bujaki.suggestion.city.model.City;
import ch.bujaki.suggestion.city.repository.CityReader;

import static java.util.stream.Collectors.toList;

@RunWith(SpringRunner.class)
public class CityReaderTest {
	
	@Test
	public void test_testCities_canBeLoaded() throws Exception {
		try(CityReader reader = new CityReader()) 
		{
			// Mock the cities file
			URL testFile = CityReaderTest.class.getResource("/testCities.tsv");
			reader.resourceFile = new UrlResource(testFile);
			
			reader.init();
			
			List<City> cities = reader.cities()
					.collect( toList() );
			
			assertTrue(cities.size() > 0);
			
			Optional<City> santJulia = cities.stream()
				.filter( city -> "Sant Julià de Lòria".equals(city.getName()))
				.findFirst();
			
			assertTrue("Sant Julià de Lòria should be loaded", santJulia.isPresent());
			
			City stJulia = santJulia.get();
			
			assertEquals("Sant Julià de Lòria", stJulia.getName());
			assertEquals("Sant Julia de Loria", stJulia.getAsciiName());
			assertEquals(8022L, stJulia.getPopulation());
			assertEquals("Sant Julià de Lòria, Andorra", stJulia.getPrettyName());
		}
	}

	
	@Test(expected = FileNotFoundException.class)
	public void test_missingFile() throws Exception {
		try(CityReader reader = new CityReader()) 
		{
			// Mock the cities file
			URL testFile = new URL("file://missingFile.tsv");
			reader.resourceFile = new UrlResource(testFile);
			
			reader.init();
		}
	}
}
