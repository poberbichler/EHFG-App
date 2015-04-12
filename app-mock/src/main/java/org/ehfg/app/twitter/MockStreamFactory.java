package org.ehfg.app.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Component;

import ch.qos.logback.core.status.StatusListener;

/**
 * Factory used for creating {@link StreamListener} mock instances, where every
 * method throws an {@link UnsupportedOperationException}<br>
 * The only exceptions are {@link TwitterStream#cleanUp()},
 * {@link TwitterStream#filter(FilterQuery)} and
 * {@link TwitterStream#addListener(StatusListener)}, due to the fact that these
 * are used directly
 * 
 * @author patrick
 * @since 03.11.2014
 */
@Component
@Profile("mock")
final class MockStreamFactory implements StreamListenerFactory {
	@Override
	public StreamListener getObject(String hashtag) {
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
