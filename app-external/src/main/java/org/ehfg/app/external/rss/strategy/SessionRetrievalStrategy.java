package org.ehfg.app.external.rss.strategy;

import org.ehfg.app.external.rss.data.events.RssEvent;
import org.springframework.stereotype.Service;

@Service
class SessionRetrievalStrategy extends AbstractDataRetrievalStrategy<RssEvent> { 
	SessionRetrievalStrategy() {
		super(RssEvent.class, "events");
	}
}
