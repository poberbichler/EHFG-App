package org.ehfg.app.twitter;

import java.util.Collection;

/**
 * @author patrick
 * @since 14.03.2014
 */
public interface TwitterStreamingFacade {
	void addListener(String hashtag);
	void removeListener(String hashtag);
	Collection<String> findAllListeners();
}
