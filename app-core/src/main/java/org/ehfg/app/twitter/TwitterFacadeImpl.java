package org.ehfg.app.twitter;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * @author patrick
 * @since 13.03.2014
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
	@org.springframework.transaction.annotation.Transactional
	public TweetPageDTO findTweetPage(Integer pageId) {
		final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
		final Page<Tweet> tweets = tweetRepository.findByHashtagOrderByCreationDateDesc(config.getHashtag(),
				new PageRequest(pageId, config.getNumberOfTweets()));

		return new TweetPageDTO(TweetMapper.map(tweets.getContent()), pageId, tweets.hasNextPage());
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
