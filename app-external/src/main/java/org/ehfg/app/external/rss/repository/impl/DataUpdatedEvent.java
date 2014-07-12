package org.ehfg.app.external.rss.repository.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

/**
 * event fired when data from the ehfg server was updated
 * 
 * @author patrick
 * @since 21.06.2014
 */
class DataUpdatedEvent extends ApplicationEvent {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final long serialVersionUID = 1L;
	private final Map<Class<?>, Object> dataCache;

	public DataUpdatedEvent(Map<Class<?>, Object> data) {
		super("");
		this.dataCache = data;
	}

	/**
	 * return the jaxbelement for the given class
	 * 
	 * @param clazz
	 *            to be cheked for
	 * @return the cached object <code>(may be null)</code>
	 */
	public <T> T getDataForClass(final Class<T> clazz) {
		Object cachedObject = dataCache.get(clazz);

		if (clazz.isInstance(cachedObject)) {
			return clazz.cast(cachedObject);
		}

		logger.error("retrieved class {} instead of {}", cachedObject.getClass().getName(), clazz.getName());
		return null;
	}
}
