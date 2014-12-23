package org.ehfg.app.twitter;

import java.util.Date;
import java.util.List;

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
	TweetPageDTO findTweetPageWithSize(Integer pageId, Integer pageSize);
	
	/**
	 * checks if this year's stream is running, and starts it in case it wasn't running
	 * 
	 * @return the {@link TwitterStreamStatus}, based on the current stream
	 */
	TwitterStreamStatus checkIfRelevantStreamIsRunning();
}
