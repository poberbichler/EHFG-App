package org.ehfg.app.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.stereotype.Service;

@Service
@Profile("!mock")
class PersistentStreamFactory implements StreamListenerFactory {
	private final TweetRepository tweetRepository;
	private final TwitterUserRepository twitterUserRepository;

	@Autowired
	public PersistentStreamFactory(TweetRepository tweetRepository, TwitterUserRepository twitterUserRepository) {
		this.tweetRepository = tweetRepository;
		this.twitterUserRepository = twitterUserRepository;
	}

	@Override
	public StreamListener getObject(Hashtag hashtag) {
		return new PersistentTwitterStreamListener(tweetRepository, twitterUserRepository, hashtag);
	}
}
