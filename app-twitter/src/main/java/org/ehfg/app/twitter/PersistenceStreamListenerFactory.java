package org.ehfg.app.twitter;

import org.ehfg.app.core.repository.TweetRepository;
import org.ehfg.app.core.repository.TwitterUserRepository;

/**
 * @author patrick
 * @since 14.03.2014
 */
public class PersistenceStreamListenerFactory {
	private final TweetRepository tweetRepository;
	private final TwitterUserRepository userRepository;

	public PersistenceStreamListenerFactory(TweetRepository tweetRepository, TwitterUserRepository userRepository) {
		super();
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
	}

	public PeristenceStreamListener getInstance(String hashtag) {
		return new PeristenceStreamListener(tweetRepository, userRepository, hashtag);
	}
}
