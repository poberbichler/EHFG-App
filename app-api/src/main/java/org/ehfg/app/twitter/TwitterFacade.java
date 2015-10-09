package org.ehfg.app.twitter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 13.03.2014
 */
public interface TwitterFacade {
	Collection<String> findStreams();
	void addStream(String hashtag);
	void removeStream(String hashtag);
	
	String findHashtag();
	
	Collection<TweetDTO> findNewerTweetsForCongress(LocalDateTime lastTweet);
	TweetPageDTO findTweetPage(Integer pageId);
	TweetPageDTO findTweetPageWithSize(Integer pageId, Integer pageSize);

	Collection<TwitterStatisticLine> findStats();
	
	/**
	 * checks if this year's stream is running, and starts it in case it wasn't running
	 * 
	 * @return the {@link TwitterStreamStatus}, based on the current stream
	 */
	TwitterStreamStatus checkIfRelevantStreamIsRunning();

    List<TweetDTO> findTweetsForExport(String hashtag);
}
