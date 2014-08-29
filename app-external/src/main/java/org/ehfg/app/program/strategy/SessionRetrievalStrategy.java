package org.ehfg.app.program.strategy;

import org.ehfg.app.program.data.events.RssEvent;
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
