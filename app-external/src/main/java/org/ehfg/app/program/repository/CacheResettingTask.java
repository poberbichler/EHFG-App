package org.ehfg.app.program.repository;

import org.ehfg.app.program.SessionRepository;
import org.ehfg.app.program.SpeakerRepository;
import org.ehfg.app.search.UpdateIndexEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Task to clean every {@link Cache} of the configured {@link CacheManager} every hour<br>
 * After this hour, the {@link Cache caches} will be initialized again
 *
 * @author patrick
 * @since 01.2015
 */
@Service
class CacheResettingTask implements ApplicationEventPublisherAware {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final CacheManager cacheManager;
	private final SessionRepository sessionRepository;
	private final SpeakerRepository speakerRepository;

	private ApplicationEventPublisher publisher;

	@Autowired
	public CacheResettingTask(CacheManager cacheManager, SessionRepository sessionRepository, SpeakerRepository speakerRepository) {
		this.cacheManager = cacheManager;
		this.sessionRepository = sessionRepository;
		this.speakerRepository = speakerRepository;
	}

	@Scheduled(fixedRate = 1000 * 60 * 60) // hourly
	public void resetCaches() {
		logger.info("going to clean every cache");

		for (final String cacheName : cacheManager.getCacheNames()) {
			logger.debug("cleaning cache [{}]", cacheName);
			cacheManager.getCache(cacheName).clear();
		}

		sessionRepository.findAll();
		speakerRepository.findAll();

		publisher.publishEvent(new UpdateIndexEvent(this));
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
