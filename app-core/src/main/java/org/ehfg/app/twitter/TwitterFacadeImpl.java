package org.ehfg.app.twitter;

import org.apache.commons.lang3.Validate;
import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

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
	public Collection<String> findStreams() {
		return streamingFacade.findAllListeners();
	}

	@Override
	public void addStream(String hashtag) {
		Validate.notNull(hashtag, "hashtag must not be null");
		streamingFacade.addListener(Hashtag.valueOf(hashtag));
	}

	@Override
	public void removeStream(String hashtag) {
		Validate.notNull(hashtag, "hashtag must not be null");
		streamingFacade.removeListener(Hashtag.valueOf(hashtag));
	}

	@Override
	public String findHashtag() {
		return masterDataFacade.getAppConfiguration().getHashtag();
	}

	@Override
	public Collection<TweetDTO> findNewerTweetsForCongress(LocalDateTime lastTweet) {
		final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
		if (config != null && config.getHashtag() != null) {
			return TweetMapper.map(tweetRepository.findNewerTweetsByHashtag(config.getHashtag().toLowerCase(), lastTweet, byCreationDate()));
		}

		return Collections.emptyList();
	}

	private Sort byCreationDate() {
		return new Sort(new Sort.Order(Sort.Direction.DESC, "creationDate"));
	}

	@Override
	public TweetPageDTO findTweetPage(Integer pageId) {
		final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
		return this.findTweetPageWithSize(pageId, config.getNumberOfTweets());
	}


	@Override
	public TweetPageDTO findTweetPageWithSize(Integer pageId, Integer pageSize) {
		Validate.notNull(pageId, "pageId must not be null!");
		Validate.notNull(pageSize, "pageSize must not be null!");

		final String currentHashtag = this.findHashtag();
		final Page<Tweet> tweets = tweetRepository.findByHashtagOrderByCreationDateDesc(
				currentHashtag.toLowerCase(), new PageRequest(pageId, pageSize));

		return new TweetPageDTO(TweetMapper.map(tweets.getContent()), pageId, tweets.getTotalPages(), currentHashtag);
	}

	@Override
	public TwitterStreamStatus checkIfRelevantStreamIsRunning() {
		final Collection<String> currentStreams = findStreams();
		final String thisYearsHashtag = findHashtag();

		if (currentStreams.isEmpty() || !currentStreams.contains(thisYearsHashtag)) {
			addStream(thisYearsHashtag);
			return TwitterStreamStatus.HAD_TO_RESTART;
		}

		return TwitterStreamStatus.RUNNING;
	}
}
