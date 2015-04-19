package org.ehfg.app.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

class PersistentTwitterStreamListener implements StreamListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistentTwitterStreamListener.class);
	
	private final TweetRepository tweetRepository;
	private final TwitterUserRepository twitterUserRepository;
	
	private final String hashtag;
	
	public PersistentTwitterStreamListener(TweetRepository tweetRepository, TwitterUserRepository twitterUserRepository, String hashtag) {
		this.tweetRepository = tweetRepository;
		this.twitterUserRepository = twitterUserRepository;
		this.hashtag = hashtag;
	}

	@Override
	public void onTweet(Tweet sourceTweet) {
		TwitterProfile user = sourceTweet.getUser();

		TwitterUser author = twitterUserRepository.findOne(user.getId());
		if (author == null) {
			LOGGER.debug("adding user {}", author);
			author = new TwitterUser(user.getId(), user.getName(), user.getScreenName(), user.getProfileImageUrl());
		}

		else {
			author.setFullName(user.getName());
			author.setNickName(user.getScreenName());
			author.setProfileImage(user.getProfileImageUrl());
		}

		twitterUserRepository.save(author);

		org.ehfg.app.twitter.Tweet tweet = tweetRepository.findOne(sourceTweet.getId());
		if (tweet == null) {
			LOGGER.debug("adding new tweet");
			tweet = TweetFactory.create(sourceTweet, hashtag, author);
		}

		tweetRepository.save(tweet);

	}

	@Override
	public void onDelete(StreamDeleteEvent deleteEvent) {
		tweetRepository.delete(deleteEvent.getTweetId());
	}

	@Override
	public void onLimit(int numberOfLimitedTweets) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onWarning(StreamWarningEvent warningEvent) {
		// TODO Auto-generated method stub
	}
}