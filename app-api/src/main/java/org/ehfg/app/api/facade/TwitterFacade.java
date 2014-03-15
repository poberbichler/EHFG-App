package org.ehfg.app.api.facade;

import java.util.List;

/**
 * @author patrick
 * @since 13.03.2014
 */
public interface TwitterFacade {
	void removeTweet(Long tweetId);
	List<String> findStreams();
	void addStream(String hashtag);
	void removeStream(String hashtag);
}
