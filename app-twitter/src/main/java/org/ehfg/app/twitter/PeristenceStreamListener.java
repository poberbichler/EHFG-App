package org.ehfg.app.twitter;

import org.ehfg.app.core.entities.Tweet;
import org.ehfg.app.core.entities.TwitterUser;
import org.ehfg.app.core.repository.TweetRepository;
import org.ehfg.app.core.repository.TwitterUserRepository;
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
public class PeristenceStreamListener implements StatusListener {
	private final TweetRepository tweetRepository;
	private final TwitterUserRepository twitterUserRepository;
	private final String hashtag;

	public PeristenceStreamListener(TweetRepository tweetRepository, TwitterUserRepository twitterUserRepository,
			String hashtag) {
		super();
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
			tweet = new Tweet(status.getId(), status.getText(), status.getCreatedAt(), hashtag, author);
		}
		
		tweetRepository.save(tweet);
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		tweetRepository.delete(statusDeletionNotice.getStatusId());
	}

	@Override
	public void onException(Exception ex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStallWarning(StallWarning warning) {
		// TODO Auto-generated method stub

	}
}
