package org.ehfg.app.twitter;

import java.util.List;

/**
 * @author patrick
 * @since 14.03.2014
 */
interface TwitterStreamingFacade {
	void addListener(String hashtag);
	void removeListener(String hashtag);
	List<String> findAllListeners();
	List<TweetDTO> executeSearch(String hashtag);
}
