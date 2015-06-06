package org.ehfg.app.twitter;

import org.ehfg.app.MockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;

/**
 * Factory used for creating {@link StreamListener} mock instances, where ever method just logs the ouput
 *
 * @author patrick
 * @since 11.2014
 */
@MockService
final class MockStreamFactory implements StreamListenerFactory {
	@Override
	public StreamListener getObject(Hashtag hashtag) {
		return INSTANCE;
	}

	private static final StreamListener INSTANCE = new StreamListener() {
		private final Logger LOG = LoggerFactory.getLogger(getClass());

		@Override
		public void onTweet(Tweet tweet) {
			LOG.info("mock received tweet [{}]", tweet);
		}

		@Override
		public void onDelete(StreamDeleteEvent deleteEvent) {
			LOG.info("mock received delete event [{}]", deleteEvent);
		}

		@Override
		public void onLimit(int numberOfLimitedTweets) {
			LOG.info("mock received limit [{}]", numberOfLimitedTweets);
		}

		@Override
		public void onWarning(StreamWarningEvent warningEvent) {
			LOG.info("mock received warning event [{}]", warningEvent);
		}
	};
}
