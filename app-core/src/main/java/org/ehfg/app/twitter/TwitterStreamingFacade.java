package org.ehfg.app.twitter;

import java.util.Collection;

/**
 * @author patrick
 * @since 03.2014
 */
public interface TwitterStreamingFacade {
	/**
	 * adds a listener for the given hashtag
	 */
	void addListener(Hashtag hashtag);

	/**
	 * removes the listener for the given hashtag. does nothing if the hashtag is not found
	 */
	void removeListener(Hashtag hashtag);

	/**
	 * @return an unmodifiable list of every listener
	 */
	Collection<String> findAllListeners();
}
