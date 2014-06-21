package org.ehfg.app.external.rss.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.ehfg.app.external.rss.strategy.AbstractDataRetrievalStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

/**
 * Reads the data from a rss stream, converts it and caches it for an hour<br>
 * after the hour is over, the internally known task is ran again by the spring scheduler library
 * 
 * @author patrick
 * @since 06.06.2014
 */
@Repository
@Profile("!mock")
class RSSReader {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final Map<Class<?>, Object> dataCache = new HashMap<>();
	private final List<AbstractDataRetrievalStrategy<?>> strategies;
	
	@Autowired
	public RSSReader(List<AbstractDataRetrievalStrategy<?>> strategies) {
		this.strategies = strategies;
		
		for (AbstractDataRetrievalStrategy<?> strategy : strategies) {
			logger.info("registered strategy {}", strategy.getClass());
		}
	}

	@Scheduled(fixedRate = 5000)
	private void createDataFromInput() throws IOException, JAXBException {
		logger.info("hell, it's about time - starting to update cache");
		
		for (AbstractDataRetrievalStrategy<?> strategy : strategies) {
			dataCache.put(strategy.getFetchedClass(), strategy.fetchData());
		}
	}
}
