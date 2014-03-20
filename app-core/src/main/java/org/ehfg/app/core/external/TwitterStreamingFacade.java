package org.ehfg.app.core.external;

import java.util.List;

import org.ehfg.app.api.dto.TweetDTO;

/**
 * @author patrick
 * @since 14.03.2014
 */
public interface TwitterStreamingFacade {
	void addListener(String hashtag);
	void removeListener(String hashtag);
	List<String> findAllListeners();
	List<TweetDTO> executeSearch(String hashtag);
}
