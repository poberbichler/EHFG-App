package org.ehfg.app.twitter;

import org.ehfg.app.twitter.TweetRepository;
import org.ehfg.app.twitter.TwitterUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component
class PersistenceStreamListenerFactory {
	private final TweetRepository tweetRepository;
	private final TwitterUserRepository userRepository;

	@Autowired
	public PersistenceStreamListenerFactory(TweetRepository tweetRepository, TwitterUserRepository userRepository) {
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
	}

	public PeristenceStreamListener getInstance(String hashtag) {
		return new PeristenceStreamListener(tweetRepository, userRepository, hashtag);
	}
	
	/**
	 * inner class implementing a {@link StatusListener}
	 * 
	 * @author patrick
	 * @since 14.03.2014
	 */
	private static class PeristenceStreamListener implements StatusListener {
		private static final Logger LOGGER = LoggerFactory.getLogger(PeristenceStreamListener.class);

		private final TweetRepository tweetRepository;
		private final TwitterUserRepository twitterUserRepository;
		private final String hashtag;

		public PeristenceStreamListener(TweetRepository tweetRepository, TwitterUserRepository twitterUserRepository, String hashtag) {
			this.tweetRepository = tweetRepository;
			this.twitterUserRepository = twitterUserRepository;
			this.hashtag = hashtag;
		}

		@Override
		@Transactional(readOnly = false)
		public void onStatus(Status status) {
			final User user = status.getUser();

			TwitterUser author = twitterUserRepository.findOne(user.getId());
			if (author == null) {
				LOGGER.debug("adding user {}", author);
				author = new TwitterUser(user.getId(), user.getName(), user.getScreenName(), user.getProfileImageURL());
			}

			else {
				author.setFullName(user.getName());
				author.setNickName(user.getScreenName());
				author.setProfileImage(user.getProfileImageURL());
			}

			twitterUserRepository.save(author);

			Tweet tweet = tweetRepository.findOne(status.getId());
			if (tweet == null) {
				LOGGER.debug("adding new tweet");
				tweet = TweetFactory.create(status, hashtag, author);
			}

			tweetRepository.save(tweet);
		}

		@Override
		@Transactional(readOnly = false)
		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			LOGGER.debug("deleting tweet with id '{}'", statusDeletionNotice.getStatusId());
			tweetRepository.delete(statusDeletionNotice.getStatusId());
		}

		@Override
		public void onException(Exception ex) {
			
		}

		@Override
		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

		}

		@Override
		public void onScrubGeo(long userId, long upToStatusId) {

		}

		@Override
		public void onStallWarning(StallWarning warning) {

		}
	}
}
