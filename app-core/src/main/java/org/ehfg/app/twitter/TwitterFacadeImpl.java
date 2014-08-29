package org.ehfg.app.twitter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.ehfg.app.base.AppConfigRepository;
import org.ehfg.app.base.ConfigurationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 * @since 13.03.2014
 */
@Component
public class TwitterFacadeImpl implements TwitterFacade {
	private final TweetRepository tweetRepository;
	private final TwitterStreamingFacade streamingFacade;
	private final AppConfigRepository configRepository;
	
	@Autowired
	public TwitterFacadeImpl(TweetRepository tweetRepository, TwitterStreamingFacade streamingFacade,
			AppConfigRepository configRepository) {
		this.tweetRepository = tweetRepository;
		this.streamingFacade = streamingFacade;
		this.configRepository = configRepository;
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

	@Override
	public List<TweetDTO> findAllTweets() {
		return tweetRepository.findTweets();
	}

	@Override
	public String findHashtag() {
		return configRepository.find().getHashtag();
	}

	@Override
	public List<TweetDTO> findNewerTweetsForCongress(Date lastTweet) {
		final ConfigurationDTO config = configRepository.find();
		if (config != null && config.getHashtag() != null) {
			return tweetRepository.findNewerTweetsByHashtag(config.getHashtag(), lastTweet);
		}

		return Collections.emptyList();
	}

	@Override
	public TweetPageDTO findTweetPage(Integer pageId) {
		final ConfigurationDTO config = configRepository.find();
		final Page<Tweet> tweets = tweetRepository.findByHashtagOrderByCreationDateDesc(config.getHashtag(),
				new PageRequest(pageId, config.getNumberOfTweets()));

		return new TweetPageDTO(TweetMapper.map(tweets.getContent()), pageId, tweets.hasNextPage());
	}
}
