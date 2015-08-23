package org.ehfg.app.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 03.2015
 */
@Service
class TwitterStreamingFacadeImpl implements TwitterStreamingFacade {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Twitter twitter;
	private final StreamListenerFactory streamListenerFactory;
	
	private final Map<Hashtag, Stream> streams = new HashMap<>();

	@Autowired
	public TwitterStreamingFacadeImpl(Twitter twitter, StreamListenerFactory streamFactory) {
		this.twitter = twitter;
		this.streamListenerFactory = streamFactory;
	}

	@Override
	public void addListener(Hashtag hashtag) {
		logger.info("adding filter for hastag [{}]", hashtag);

		final Stream filter = twitter.streamingOperations().filter(hashtag.getHashtagWithoutHash(), Arrays.asList(streamListenerFactory.getObject(hashtag)));
		streams.put(hashtag, filter);
	}

	@Override
	public void removeListener(Hashtag hashtag) {
		logger.info("removing filter for hashtag [{}]", hashtag);
		
		final Stream stream = streams.get(hashtag);
		if (stream != null) {
			stream.close();
			streams.remove(hashtag);
		}
	}

	@Override
	public Collection<String> findAllListeners() {
		return Collections.unmodifiableCollection(streams.keySet().stream()
				.map(Hashtag::getHashtagWithHash)
				.collect(Collectors.toList()));
	}
}
