package org.ehfg.app.external.rss.strategy;

import org.ehfg.app.external.rss.data.events.RssEvent;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 21.06.2014
 */
@Service
final class SessionRetrievalStrategy extends AbstractDataRetrievalStrategy<RssEvent> { 
	SessionRetrievalStrategy() {
		super(RssEvent.class, "events");
	}
}
