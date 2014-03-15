package org.ehfg.app.core.facade;

import java.util.List;

import org.ehfg.app.api.dto.twitter.SaveTweetDTO;
import org.ehfg.app.api.dto.twitter.TweetDTO;
import org.ehfg.app.api.dto.twitter.TwitterUserDTO;
import org.ehfg.app.api.facade.TwitterFacade;
import org.ehfg.app.core.entities.Tweet;
import org.ehfg.app.core.entities.TwitterUser;
import org.ehfg.app.core.external.TwitterStreamingFacade;
import org.ehfg.app.core.repository.TweetRepository;
import org.ehfg.app.core.repository.TwitterUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author patrick
 * @since 13.03.2014
 */
public class TwitterFacadeImpl implements TwitterFacade {
	private final static Logger LOG = LoggerFactory.getLogger(TwitterFacadeImpl.class);

	private final TwitterUserRepository userRepository;
	private final TweetRepository tweetRepository;
	private final TwitterStreamingFacade streamingFacade;

	public TwitterFacadeImpl(TwitterUserRepository userRepository, TweetRepository tweetRepository,
			TwitterStreamingFacade streamingFacade) {
		super();
		this.userRepository = userRepository;
		this.tweetRepository = tweetRepository;
		this.streamingFacade = streamingFacade;
	}

	@Deprecated
	@Transactional(readOnly = false)
	public void saveTweet(TweetDTO source) {
		Tweet target = tweetRepository.findOne(source.id);
		if (target == null) {
			final TwitterUser user = userRepository.findOne(source.authorId);
			if (user != null) {
				LOG.trace(String.format("adding tweet [%s]", source));
				tweetRepository.save(new Tweet(source.id, source.message, source.creationDate, source.hashtag, user));
			}

			else {
				LOG.error(String.format("user has to be created first"));
			}
		}
	}

	@Deprecated
	@Transactional(readOnly = true)
	public void saveTwitterUser(TwitterUserDTO source) {
		TwitterUser target = userRepository.findOne(source.id);
		if (target == null) {
			LOG.trace(String.format("adding user [%s]", source));
			target = new TwitterUser(source.id, source.fullName, source.nickName, source.profileImage);
		}

		else {
			target.setFullName(source.fullName);
			target.setNickName(source.nickName);
			target.setProfileImage(source.profileImage);

			LOG.info(String.format("updated user with id '%s'", source.id));
		}

		userRepository.save(target);
	}

	@Deprecated
	public void saveTweetWithUser(SaveTweetDTO tweet) {
		saveTwitterUser(new TwitterUserDTO(tweet.authorId, tweet.fullName, tweet.nickName, tweet.profileImage));
		saveTweet(new TweetDTO(tweet.id, tweet.authorId, tweet.creationDate, tweet.message, tweet.hashtag));
	}

	@Override
	public void removeTweet(Long tweetId) {
		LOG.trace(String.format("deleting tweet with id '%s'", tweetId));
		if (tweetId != null) {
			tweetRepository.delete(tweetId);
		}
	}

	@Override
	public List<String> findStreams() {
		return streamingFacade.findAllListeners();
	}

	@Override
	public void addStream(String hashtag) {
		streamingFacade.addListener(hashtag);
	}

	@Override
	public void removeStream(String hashtag) {
		streamingFacade.removeListener(hashtag);
	}
}
