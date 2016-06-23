package org.ehfg.app.search;

import org.ehfg.app.rest.SearchResultItemRepresentation;
import org.ehfg.app.rest.SearchResultRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author patrick
 * @since 06.2016
 */
@Service
public class SearchServiceImpl implements SearchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public SearchResultRepresentation findBy(String input) {
		logger.info("searching for [{}]", input);

		if (StringUtils.isEmpty(input)) {
			return SearchResult.empty();
		}

		Map<ResultType, Collection<SearchResultItemRepresentation>> resultMap = new HashMap<>();
		resultMap.put(ResultType.SPEAKER, Arrays.asList(new SearchResultItem("124", ResultType.SPEAKER, "Helmut Brand"),
				new SearchResultItem("156", ResultType.SPEAKER, "John Bowis"),
				new SearchResultItem("742", ResultType.SPEAKER, "Annemans Lieven")));
		resultMap.put(ResultType.LOCATION, Arrays.asList(new SearchResultItem("2", ResultType.LOCATION, "Conference Centre Room 1")));
		resultMap.put(ResultType.SESSION, Arrays.asList(new SearchResultItem("3", ResultType.SESSION, "W1 - Keynote")));

		return new SearchResult(resultMap, Collections.emptyList());
	}
}
