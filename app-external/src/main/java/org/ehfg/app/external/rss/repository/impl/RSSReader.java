package org.ehfg.app.external.rss.repository.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.ehfg.app.external.rss.strategy.AbstractDataRetrievalStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

/**
 * Reads the data from a rss stream and converts it<br>
 * every hour, the data will be updated and an {@link DataUpdatedEvent} will be sent
 * 
 * @author patrick
 * @since 06.06.2014
 */
@Repository
@Profile({ "!mock" })
class RSSReader implements ApplicationContextAware {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private ApplicationContext ctx;
	private final List<AbstractDataRetrievalStrategy<?>> strategies;

	@Autowired
	public RSSReader(List<AbstractDataRetrievalStrategy<?>> strategies) {
		this.strategies = strategies;

		for (AbstractDataRetrievalStrategy<?> strategy : strategies) {
			logger.info("registered strategy {}", strategy.getClass());
		}
	}

	@Scheduled(fixedRate = 1000 * 60 * 60) //hourly
	private void createDataFromInput() throws IOException, JAXBException {
		logger.info("hell, it's about time - starting to update cache");

		Map<Class<?>, Object> dataCache = new HashMap<>();
		for (AbstractDataRetrievalStrategy<?> strategy : strategies) {
			dataCache.put(strategy.getFetchedClass(), strategy.fetchData());
		}
		
		ctx.publishEvent(new DataUpdatedEvent(dataCache));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}
}
