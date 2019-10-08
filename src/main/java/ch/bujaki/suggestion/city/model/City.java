package ch.bujaki.suggestion.city.model;

import java.util.Locale;

public class City {

	private final String name;
	private final String region;
	private final String asciiName;
	private final String country;
	private final long population;
	
	/**
	 * Constructor.
	 * 
	 * @param name        The name of the city (for example: "New York")
	 * @param region      The name of the region of the city (for example the state)
	 * @param country     The ISO 3166 alpha-2 code of the country of the city.
	 * @param population  The population of the city.
	 */
	public City(String name, String region, String asciiName, String country, long population) {
		this.name = name;
		this.region = region;
		this.asciiName = asciiName;
		this.country = country;
		this.population = population;
	}

	/**
	 * @return the population of the city.
	 */
	public long getPopulation() { 
		return this.population;
	}

	/**
	 * @return the name of the city.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the name of the city in ASCII chars.
	 */
	public String getAsciiName() {
		return asciiName;
	}
	
	/**
	 * @return the pretty formatted full name of the city. (for example: "London, United Kingdom")
	 * 
	 * Please note: for the cities in the US, also the name of the state is being added.
	 */
	public String getPrettyName() {
		if ("US".equals(this.country)) {
			// Special handling for the US: add the name of the state.
			return String.format("%s, %s, %s",
				name,
				region,
				getCountryName());
		}
		else {
			return String.format("%s, %s",
				name,
				getCountryName());
		}
		
		
	}
	
	@Override
	public String toString() {
		return "City [name=" + name + ", region=" + region + ", asciiName=" + asciiName + ", country=" + country + ", population=" + population + "]";
	}
	
	private String getCountryName() {
		return new Locale("",country).getDisplayCountry(Locale.ENGLISH);
	}
}
