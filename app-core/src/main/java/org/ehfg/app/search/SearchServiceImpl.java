package org.ehfg.app.search;

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
	public Map<ResultType, Collection<? extends SearchResultRepresentation>> findBy(String input) {
		logger.info("searching for [{}]", input);

		if (StringUtils.isEmpty(input)) {
			return Collections.emptyMap();
		}

		Map<ResultType, Collection<? extends SearchResultRepresentation>> resultMap = new HashMap<>();
		resultMap.put(ResultType.SPEAKER, Arrays.asList(new SearchResult("1", ResultType.SPEAKER, "Sepp Forcher"),
				new SearchResult("2", ResultType.SPEAKER, "Günther Leiner"),
				new SearchResult("3", ResultType.SPEAKER, "Noch wer")));
		resultMap.put(ResultType.LOCATION, Arrays.asList(new SearchResult("2", ResultType.LOCATION, "Festsaal")));
		resultMap.put(ResultType.SESSION, Arrays.asList(new SearchResult("3", ResultType.SESSION, "W1 - Keynote")));
		resultMap.put(ResultType.TWEET, Arrays.asList(new SearchResult("4", ResultType.TWEET, "#EHFG2016 is awesome")));
		return resultMap;
	}
}
