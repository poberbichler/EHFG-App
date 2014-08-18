package org.ehfg.app.api.facade;

import java.util.Date;
import java.util.List;

import org.ehfg.app.api.dto.TweetDTO;
import org.ehfg.app.api.dto.TweetPageDTO;

/**
 * @author patrick
 * @since 13.03.2014
 */
public interface TwitterFacade {
	List<String> findStreams();
	void addStream(String hashtag);
	void removeStream(String hashtag);
	
	List<TweetDTO> findAllTweets();
	String findHashtag();
	
	List<TweetDTO> findNewerTweetsForCongress(Date lastTweet);
	TweetPageDTO findTweetPage(Integer pageId);
}
