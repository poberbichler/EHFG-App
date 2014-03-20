package org.ehfg.app.api.facade;

import java.util.List;

import org.ehfg.app.api.dto.TweetDTO;

/**
 * @author patrick
 * @since 13.03.2014
 */
public interface TwitterFacade {
	List<String> findStreams();
	void addStream(String hashtag);
	void removeStream(String hashtag);
	
	List<TweetDTO> findAllTweets();
	List<TweetDTO> findByHashtag(String hashtag);
	List<TweetDTO> findTweetsForCongress();
}
