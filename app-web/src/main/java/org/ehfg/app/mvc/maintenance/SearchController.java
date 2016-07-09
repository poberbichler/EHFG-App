package org.ehfg.app.mvc.maintenance;

import org.ehfg.app.rest.SearchResultRepresentation;
import org.ehfg.app.search.SearchResult;
import org.ehfg.app.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author patrick
 * @since 07.2016
 */
@Controller
@RequestMapping("maintenance/search")
public class SearchController {
	private final SearchService searchService;

	@Autowired
	public SearchController(SearchService searchService) {
		this.searchService = searchService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getPage() {
		return createAndFillView(null);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView handleForm(String searchInput) {
		return createAndFillView(searchService.findBy(searchInput));
	}

	@RequestMapping(method = RequestMethod.GET, value = "refresh")
	public ModelAndView refreshIndex() {
		searchService.buildIndex();
		return createAndFillView(null);
	}

	private ModelAndView createAndFillView(SearchResultRepresentation searchResult) {
		final ModelAndView view = new ModelAndView("searchMaintenance");
		view.addObject("activePage", "search");
		view.addObject("searchResult", searchResult == null ? SearchResult.empty() : searchResult);
		return view;
	}
}
