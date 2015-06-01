package org.ehfg.app.twitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.Tweet;

/**
 * @author patrick
 * @since 04.2015
 */
class PersistentTwitterStreamListener implements StreamListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(PersistentTwitterStreamListener.class);
	
	private final TweetRepository tweetRepository;
	private final TwitterUserRepository twitterUserRepository;
	
	private final Hashtag hashtag;
	
	public PersistentTwitterStreamListener(TweetRepository tweetRepository, TwitterUserRepository twitterUserRepository, Hashtag hashtag) {
		this.tweetRepository = tweetRepository;
		this.twitterUserRepository = twitterUserRepository;
		this.hashtag = hashtag;
	}

	@Override
	public void onTweet(Tweet sourceTweet) {
		TwitterProfile user = sourceTweet.getUser();

		TwitterUser author = twitterUserRepository.findOne(Long.toString(user.getId()));
		if (author == null) {
			LOGGER.debug("adding user [{}]", user);
			author = new TwitterUser(Long.toString(user.getId()), user.getName(), user.getScreenName(), user.getProfileImageUrl());
		}

		else {
			author.setFullName(user.getName());
			author.setNickName(user.getScreenName());
			author.setProfileImage(user.getProfileImageUrl());
		}

		twitterUserRepository.save(author);

		org.ehfg.app.twitter.Tweet tweet = tweetRepository.findOne(Long.toString(sourceTweet.getId()));
		if (tweet == null) {
			LOGGER.debug("adding new tweet");
			tweet = TweetFactory.create(sourceTweet, hashtag.getHashtagWithHash(), author);
		}

		tweetRepository.save(tweet);

	}

	@Override
	public void onDelete(StreamDeleteEvent deleteEvent) {
		tweetRepository.delete(Long.toString(deleteEvent.getTweetId()));
	}

	@Override
	public void onLimit(int numberOfLimitedTweets) {
		LOGGER.info("tweet limit [{}] reached!?", numberOfLimitedTweets);
	}

	@Override
	public void onWarning(StreamWarningEvent warningEvent) {
		LOGGER.info("twitter stream received warning [{}]!?", warningEvent.getMessage());
	}
}
