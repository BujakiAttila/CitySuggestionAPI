package ch.bujaki.suggestion.city.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import ch.bujaki.suggestion.city.model.City;

@RunWith(SpringRunner.class)
public class CityTest {
	
	@Test
	public void test_toString_includesAllData() {
		String result = new City("Tápiószentmárton", "123", "Tapioszentmarton", "HU", 50000L).toString();

		assertTrue(result.contains("Tápiószentmárton"));
		assertTrue(result.contains("123"));
		assertTrue(result.contains("Tapioszentmarton"));
		assertTrue(result.contains("HU"));
		assertTrue(result.contains("" + 50000));
	}
	
	@Test
	public void test_getters() {
		City city = new City("Tápiószentmárton", "123", "Tapioszentmarton", "HU", 50000L);

		assertEquals("Tápiószentmárton", city.getName());
		assertEquals("Tapioszentmarton", city.getAsciiName());
		assertEquals(50000L, city.getPopulation());
	}

	@Test
	public void test_getPrettyName_nonUS() {
		String result = new City("Tápiószentmárton", "123", "Tapioszentmarton", "HU", 50000L).getPrettyName();

		assertEquals("Tápiószentmárton, Hungary", result);
	}

	@Test
	public void test_getPrettyName_US() {
		String result = new City("Michigan City", "IN", "Michigan City", "US", 50000L).getPrettyName();

		assertEquals("Michigan City, IN, United States", result);
	}
}
