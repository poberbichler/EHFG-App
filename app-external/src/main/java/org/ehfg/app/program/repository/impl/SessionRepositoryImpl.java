package org.ehfg.app.program.repository.impl;

import org.ehfg.app.program.SessionDTO;
import org.ehfg.app.program.data.events.RssEvent;
import org.ehfg.app.program.data.speakerevents.RssSpeakerEvents;
import org.ehfg.app.program.repository.AbstractSessionRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * takes care of transforming the data from {@link RssEvent} into usable
 * {@link SessionDTO}, and caches the given data
 * 
 * @author patrick
 * @since 21.06.2014
 */
@Repository
@Profile({ "!mock" })
class SessionRepositoryImpl extends AbstractSessionRepository implements ApplicationListener<DataUpdatedEvent> {
	@Override
	public void onApplicationEvent(DataUpdatedEvent event) {
		super.fillCache(event.getDataForClass(RssEvent.class), event.getDataForClass(RssSpeakerEvents.class));
	}
}
