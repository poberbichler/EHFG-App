package org.ehfg.app.search;

import org.ehfg.app.rest.SearchResultItemRepresentation;
import org.ehfg.app.rest.SearchResultRepresentation;
import org.ehfg.app.rest.TweetRepresentation;

import java.util.*;

/**
 * @author patrick
 * @since 06.2016
 */
public class SearchResult implements SearchResultRepresentation {
	private final Map<ResultType, Collection<SearchResultItemRepresentation>> items = new EnumMap<>(ResultType.class);
	private final Collection<TweetRepresentation> tweets = new ArrayList<>();

	public static SearchResult empty() {
		final SearchResult result = new SearchResult();
		for (ResultType type : ResultType.values()){
			result.items.put(type, Collections.emptyList());
		}
		return result;
	}

	public SearchResult add(SearchResultItemRepresentation item) {
		this.items.computeIfAbsent(item.getType(), i -> new LinkedList<>()).add(item);
		return this;
	}

	public SearchResult add(TweetRepresentation tweet) {
		this.tweets.add(tweet);
		return this;
	}

    @Override
    public boolean hasAnyResult() {
        return items.values().stream()
                .filter(c -> !c.isEmpty())
                .findAny().isPresent()
                || !tweets.isEmpty();
    }

    @Override
	public Collection<SearchResultDataRepresentation> getResults() {
		return Arrays.asList(
				new SearchResultData(ResultType.LOCATION, items.getOrDefault(ResultType.LOCATION, Collections.emptyList())),
				new SearchResultData(ResultType.SESSION, items.getOrDefault(ResultType.SESSION, Collections.emptyList())),
				new SearchResultData(ResultType.SPEAKER, items.getOrDefault(ResultType.SPEAKER, Collections.emptyList())));
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
