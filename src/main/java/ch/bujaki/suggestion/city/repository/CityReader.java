package ch.bujaki.suggestion.city.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import ch.bujaki.suggestion.city.model.City;

/**
 * Reads the cities from the "cities.tsv" file.
 */
@Repository
public class CityReader implements AutoCloseable {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private BufferedReader input;

	@Value("classpath:cities.tsv")
	public Resource resourceFile;
	
	@PostConstruct
	public void init() throws IOException {
		try {
			FileReader fileReader = new FileReader(resourceFile.getFile(), Charset.forName("UTF-8"));
			input = new BufferedReader(fileReader);
		} catch (IOException ಠ_ಠ) {
			logger.error("init - Failed to load the cities file:" + resourceFile, ಠ_ಠ);
			throw ಠ_ಠ;
		}
	}

	@PreDestroy
	public void cleanUp() throws Exception {
		try {
			input.close();
		} catch (IOException ಠ_ಠ) {
			logger.error("cleanUp - Failed to clean up.", ಠ_ಠ);
			throw ಠ_ಠ;
		}
	}
	
	/**
	 * @return the {@link Stream} of the cities available in the TSV file.
	 */
	public Stream<City> cities() {
		return input.lines()
			.filter( line -> !line.isBlank() )
			.map( line -> line.split("\\t") )
			.filter( cols -> cols.length > 14 )
			.map( cols -> parseLine(cols) )
			.filter( city -> city != null ); 
	}

	private City parseLine(String[] cols) {
		try {
			String name = trim(cols[1], "name");
			String region = trim(cols[10], "region");
			String asciiName = trim(cols[2], "asciiName");
			String countryCode = trim(cols[8], "countryCode");
			String populationString = trim(cols[14], "population");
			long population = Long.parseLong(populationString);
			
			return new City(name, region, asciiName, countryCode, population);
		} 
		catch (NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException ಠ_ಠ ) {
			logger.error("parseLine - failed to parse line" , ಠ_ಠ);
			return null;
		}
	}
	
	private String trim(String value, String name) {
		Objects.requireNonNull(value, () -> name + " must not be null.");
		
		return value.trim();
	}
	
	@Override
	public void close() throws Exception {
		input.close();
	}
}
