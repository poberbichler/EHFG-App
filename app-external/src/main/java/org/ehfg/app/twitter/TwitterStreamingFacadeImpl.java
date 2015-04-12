package org.ehfg.app.twitter;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Stream;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 03.2015
 */
@Service
class TwitterStreamingFacadeImpl implements TwitterStreamingFacade {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Twitter twitter;
	private final StreamListenerFactory streamListenerFactory;
	
	private final Map<String, Stream> streams = new HashMap<>();

	@Autowired
	public TwitterStreamingFacadeImpl(Twitter twitter, PersistentStreamFactory streamFactory) {
		this.twitter = twitter;
		this.streamListenerFactory = streamFactory;
	}

	@Override
	public void addListener(String input) {
		String hashtag = validateAndAddHashToTag(input);
		
		logger.info("adding filter for hastag [{}]", input);
		final Stream filter = twitter.streamingOperations().filter(hashtag, Arrays.asList(streamListenerFactory.getObject(hashtag)));
		streams.put(input, filter);
	}

	@Override
	public void removeListener(String input) {
		String hashtag = validateAndAddHashToTag(input);
		logger.info("removing filter for hashtag [{}]", hashtag);
		
		final Stream stream = streams.get(hashtag);
		if (stream != null) {
			stream.close();
			streams.remove(hashtag);
		}
	}

	@Override
	public Collection<String> findAllListeners() {
		return streams.keySet();
	}
	
	private String validateAndAddHashToTag(String input) {
		Validate.notNull(input, "input must not be nul");
		
		if (!input.startsWith("#")) {
			return "#".concat(input);
		}
		
		return input;
	}
}
