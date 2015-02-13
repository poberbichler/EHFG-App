package org.ehfg.app.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.BooleanUtils;
import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component("twitterStreamingFacade")
class TwitterStreamingFacadeImpl implements TwitterStreamingFacade {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final Map<String, TwitterStream> streams = new HashMap<>();
	private final TwitterStreamFactoryHolder streamFactory;
	private final PersistenceStreamListenerFactory listenerFactory;
	private final MasterDataFacade masterDataFacade;

	@Value("${twitter.default.listener.start}")
	private boolean defaultStartup;

	@Autowired
	public TwitterStreamingFacadeImpl(TwitterStreamFactoryHolder streamFactory, PersistenceStreamListenerFactory listenerFactory,
			MasterDataFacade masterDataFacade) {
		this.streamFactory = streamFactory;
		this.listenerFactory = listenerFactory;
		this.masterDataFacade = masterDataFacade;
	}

	@Override
	public void addListener(String hashtag) {
		if (!hashtag.startsWith("#")) {
			hashtag = "#".concat(hashtag);
		}

		if (!streams.containsKey(hashtag)) {
			final TwitterStream stream = streamFactory.getStream();
			stream.addListener(listenerFactory.getInstance(hashtag));

			FilterQuery query = new FilterQuery();

			query.track(new String[] { hashtag });
			stream.filter(query);
			streams.put(hashtag, stream);
		}
	}

	@Override
	public List<String> findAllListeners() {
		return new ArrayList<>(streams.keySet());
	}

	@Override
	public List<TweetDTO> executeSearch(String hashtag) {
		return Collections.emptyList();
	}

	@Override
	public void removeListener(String hashtag) {
		if (!hashtag.startsWith("#")) {
			hashtag = "#".concat(hashtag);
		}

		logger.info("removing stream for hashtag '{}'", hashtag);

		TwitterStream stream = streams.get(hashtag);
		if (stream != null) {
			stream.cleanUp();
			streams.remove(hashtag);
		}
	}

	@PostConstruct
	public void afterPropertiesSet() {
		if (BooleanUtils.isTrue(defaultStartup)) {
			final ConfigurationDTO config = masterDataFacade.getAppConfiguration();
			if (config != null && config.getHashtag() != null) {
				this.addListener(config.getHashtag());
			}
		}
	}

	@PreDestroy
	public void destroy() throws Exception {
		for (final String hashtag : findAllListeners()) {
			removeListener(hashtag);
		}
	}
}
