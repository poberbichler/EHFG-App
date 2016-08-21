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

		final TwitterUser author = twitterUserRepository.findOne(Long.toString(user.getId())).orElseGet(() -> {
			LOGGER.debug("adding user [{}]", user);

			final TwitterUser result = new TwitterUser();
			result.setId(Long.toString(user.getId()));
			return result;
		});

		author.setFullName(user.getName());
		author.setNickName(user.getScreenName());
		author.setProfileImage(user.getProfileImageUrl());

        if (sourceTweet.isRetweet()) {
            LOGGER.info("tweet got retweeted...");
            org.ehfg.app.twitter.Tweet retweetedTweet = tweetRepository.findOne(Long.toString(sourceTweet.getRetweetedStatus().getId()));
            if (retweetedTweet == null) {
                LOGGER.warn("no tweet with id [{}] found that could have been retweeted", sourceTweet.getRetweetedStatus().getId());
            } else {
                LOGGER.info("tweet [{}] got retweeted", retweetedTweet.getId());
                retweetedTweet.addRetweet(user.getScreenName());
                tweetRepository.save(retweetedTweet);
            }
        }

		twitterUserRepository.save(author);
		tweetRepository.save(TweetFactory.create(sourceTweet, hashtag.getHashtagForDb(), author));
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
