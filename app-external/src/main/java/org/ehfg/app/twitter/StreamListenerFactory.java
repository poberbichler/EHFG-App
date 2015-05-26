package org.ehfg.app.twitter;

import org.springframework.social.twitter.api.StreamListener;

/**
 * @author patrick
 * @since 04.2015
 */
interface StreamListenerFactory {
	/**
	 * @return a new {@link StreamListener} for the given hashtag
	 */
	StreamListener getObject(Hashtag hashtag);
}
