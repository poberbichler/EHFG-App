package org.ehfg.app.search;

import org.ehfg.app.rest.SearchResultItemRepresentation;
import org.ehfg.app.rest.SearchResultRepresentation;
import org.ehfg.app.rest.TweetRepresentation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author patrick
 * @since 06.2016
 */
public class SearchResult implements SearchResultRepresentation {
	private final Map<ResultType, Collection<SearchResultItemRepresentation>> items;
	private final Collection<TweetRepresentation> tweets;

	public SearchResult(Map<ResultType, Collection<SearchResultItemRepresentation>> items, Collection<TweetRepresentation> tweets) {
		this.items = items;
		this.tweets = tweets;
	}

	public static SearchResultRepresentation empty() {
		return new SearchResult(Collections.emptyMap(), Collections.emptyList());
	}

	@Override
	public Collection<SearchResultDataRepresentation> getResults() {
		return Arrays.asList(
				new SearchResultData(ResultType.LOCATION, items.get(ResultType.LOCATION)),
				new SearchResultData(ResultType.SESSION, items.get(ResultType.SESSION)),
				new SearchResultData(ResultType.SPEAKER, items.get(ResultType.SPEAKER)));
	}

	@Override
	public Collection<TweetRepresentation> getTweets() {
		return tweets;
	}


	private static class SearchResultData implements SearchResultDataRepresentation {
		private final ResultType type;
		private final Collection<SearchResultItemRepresentation> results;

		public SearchResultData(ResultType type, Collection<SearchResultItemRepresentation> results) {
			this.type = type;
			this.results = results;
		}

		@Override
		public ResultType getType() {
			return type;
		}

		@Override
		public Collection<SearchResultItemRepresentation> getResults() {
			return results;
		}
	}
}
