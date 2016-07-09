package org.ehfg.app.rest;

import org.ehfg.app.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author patrick
 * @since 06.2016
 */
@RestController
@RequestMapping("rest/search")
public class SearchRestEndpoint {
	private final SearchService searchService;

	@Autowired
	public SearchRestEndpoint(SearchService searchService) {
		this.searchService = searchService;
	}

	@RequestMapping(value = "{input}", method = RequestMethod.GET)
	public SearchResultRepresentation find(@PathVariable("input") String input) {
		return searchService.findBy(input);
	}
}
