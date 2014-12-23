package org.ehfg.app.twitter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author patrick
 * @since 03.2014
 */
@Component
final class TwitterFacadeImpl implements TwitterFacade {
	private final TweetRepository tweetRepository;
	private final TwitterStreamingFacade streamingFacade;
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public TwitterFacadeImpl(TweetRepository tweetRepository, TwitterStreamingFacade streamingFacade, MasterDataFacade masterDataFacade) {
		this.tweetRepository = tweetRepository;
		this.streamingFacade = streamingFacade;
		this.masterDataFacade = masterDataFacade;
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
		return masterDataFacade.getAppConfiguration().getHashtag();
	}

	@Override
	public List<TweetDTO> findNewerTweetsForCongress(Date lastTweet) {
		final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
		if (config != null && config.getHashtag() != null) {
			return tweetRepository.findNewerTweetsByHashtag(config.getHashtag(), lastTweet);
		}

		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = false)
	public TweetPageDTO findTweetPage(Integer pageId) {
		final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
		return this.findTweetPageWithSize(pageId, config.getNumberOfTweets());
	}
	

	@Override
	@Transactional(readOnly = false)
	public TweetPageDTO findTweetPageWithSize(Integer pageId, Integer pageSize) {
		Validate.notNull(pageId, "pageId must not be null!");
		Validate.notNull(pageSize, "pageSize must not be null!");
		
		final Page<Tweet> tweets = tweetRepository.findByHashtagOrderByCreationDateDesc(
				this.findHashtag(), new PageRequest(pageId, pageSize));
		
		return new TweetPageDTO(TweetMapper.map(tweets.getContent()), pageId, tweets.getTotalPages());
	}
	
	@Override
	public TwitterStreamStatus checkIfRelevantStreamIsRunning() {
		final List<String> currentStreams = findStreams();
		final String thisYearsHashtag = findHashtag();
		
		if (currentStreams.isEmpty() || !currentStreams.contains(thisYearsHashtag)) {
			addStream(thisYearsHashtag);
			return TwitterStreamStatus.HAD_TO_RESTART;
		}
		
		return TwitterStreamStatus.RUNNING;
	}
}
