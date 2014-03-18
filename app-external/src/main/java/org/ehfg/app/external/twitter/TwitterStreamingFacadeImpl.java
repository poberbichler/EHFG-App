package org.ehfg.app.external.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehfg.app.api.dto.twitter.TweetDTO;
import org.ehfg.app.core.external.TwitterStreamingFacade;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * @author patrick
 * @since 14.03.2014
 */
class TwitterStreamingFacadeImpl implements TwitterStreamingFacade {
	private final Map<String, TwitterStream> streams = new HashMap<String, TwitterStream>();
	private final TwitterStreamFactory streamFactory;
	private final PersistenceStreamListenerFactory listenerFactory;

	public TwitterStreamingFacadeImpl(TwitterStreamFactory streamFactory,
			PersistenceStreamListenerFactory listenerFactory) {
		super();
		this.streamFactory = streamFactory;
		this.listenerFactory = listenerFactory;
	}

	@Override
	public void addListener(String hashtag) {
		if (!streams.containsKey(hashtag)) {
			final TwitterStream stream = streamFactory.getInstance();
			stream.addListener(listenerFactory.getInstance(hashtag));

			FilterQuery query = new FilterQuery();
			if (!hashtag.startsWith("#")) {
				hashtag = "#".concat(hashtag);
			}
			
			query.track(new String[] { hashtag });
			stream.filter(query);

			streams.put(hashtag, stream);
		}
	}

	@Override
	public List<String> findAllListeners() {
		return new ArrayList<String>(streams.keySet());
	}

	@Override
	public List<TweetDTO> executeSearch(String hashtag) {
		return Collections.emptyList();
	}

	@Override
	public void removeListener(String hashtag) {
		TwitterStream stream = streams.get(hashtag);
		if (stream != null) {
			stream.cleanUp();
			streams.remove(hashtag);
		}
	}
}
