package ch.bujaki.suggestion.city.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.bujaki.suggestion.city.model.CityNameWithScore;
import ch.bujaki.suggestion.city.repository.CityReader;
import ch.bujaki.suggestion.city.service.CityIndex;

/**
 * {@link RestController} class for the City-Suggestion API.
 */
@RestController
public class CitySuggestionController {
	
	Logger logger = LoggerFactory.getLogger(CitySuggestionController.class);
	
	@Autowired
	CityIndex index;

	@Autowired
	CityReader reader;
	
	@PostConstruct
	public void init() throws Exception{
		logger.info("init - Started.");
		
		reader.cities()
			.forEach( index::addCity );
		
		
		index.init();
		
		logger.info("init - Done.");
	}

	@RequestMapping(value = "/suggestions", method = RequestMethod.GET)
	public Map<String, List<CityNameWithScore>> getSuggestions(@RequestParam(value="q", required = true)String query) {
		Map<String, List<CityNameWithScore>> response = new HashMap<>();
		
		List<CityNameWithScore> suggestions = CityNameWithScore.createFor(index.getSuggestionsFor(query));
		response.put("suggestions", suggestions);
		
		return response;
	}
}
