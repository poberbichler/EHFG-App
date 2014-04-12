package org.ehfg.app.external.twitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.BooleanUtils;
import org.ehfg.app.api.dto.ConfigurationDTO;
import org.ehfg.app.api.dto.TweetDTO;
import org.ehfg.app.core.external.TwitterStreamingFacade;
import org.ehfg.app.core.repository.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component("twitterStreamingFacade")
class TwitterStreamingFacadeImpl implements TwitterStreamingFacade {
	private final Map<String, TwitterStream> streams = new HashMap<String, TwitterStream>();
	private final TwitterStreamFactory streamFactory;
	private final PersistenceStreamListenerFactory listenerFactory;
	private final AppConfigRepository configRepository;

	@Value("${twitter.default.listener.start}")
	private Boolean defaultStartup;

	@Autowired
	public TwitterStreamingFacadeImpl(TwitterStreamFactory streamFactory,
			PersistenceStreamListenerFactory listenerFactory, AppConfigRepository configRepository) {
		super();
		this.streamFactory = streamFactory;
		this.listenerFactory = listenerFactory;
		this.configRepository = configRepository;
	}

	@PostConstruct
	private void addDefaultStream() {
		if (BooleanUtils.isTrue(defaultStartup)) {
			final ConfigurationDTO config = configRepository.find();
			if (config != null && config.getHashtag() != null) {
				this.addListener(config.getHashtag());
			}
		}
	}

	@PreDestroy
	private void removeStreams() {
		for (final String hashtag : findAllListeners()) {
			removeListener(hashtag);
		}
	}

	@Override
	public void addListener(String hashtag) {
		if (!hashtag.startsWith("#")) {
			hashtag = "#".concat(hashtag);
		}

		if (!streams.containsKey(hashtag)) {
			final TwitterStream stream = streamFactory.getInstance();
			stream.addListener(listenerFactory.getInstance(hashtag));

			FilterQuery query = new FilterQuery();

			query.track(new String[] { hashtag });
			stream.filter(query);

			streams.put(hashtag, stream);
		}
	}

	@Override
	public List<String> findAllListeners() {
		return new ArrayList<String>(streams.keySet());
	}

	@Override
	public List<TweetDTO> executeSearch(String hashtag) {
		return Collections.emptyList();
	}

	@Override
	public void removeListener(String hashtag) {
		TwitterStream stream = streams.get(hashtag);
		if (stream != null) {
			stream.cleanUp();
			streams.remove(hashtag);
		}
	}
}
