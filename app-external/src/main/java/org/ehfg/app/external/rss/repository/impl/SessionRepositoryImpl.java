package org.ehfg.app.external.rss.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.external.rss.data.events.RssEvent;
import org.ehfg.app.external.rss.data.speakerevents.RssSpeakerEvents;
import org.ehfg.app.external.rss.repository.AbstractSessionRepository;
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
	public List<SessionDTO> findAll() {
		return new ArrayList<>(dataCache.values());
	}

	@Override
	public SessionDTO findById(Long sessionId) {
		return dataCache.get(sessionId.toString());
	}

	@Override
	public List<SessionDTO> findBySpeaker(Long speakerId) {
		final List<SessionDTO> result = new ArrayList<>();
		for (final SessionDTO session : dataCache.values()) {
			if (session.getSpeakers().contains(speakerId)) {
				result.add(session);
			}
		}

		return result;
	}

	@Override
	public void onApplicationEvent(DataUpdatedEvent event) {
		super.fillCache(event.getDataForClass(RssEvent.class), event.getDataForClass(RssSpeakerEvents.class));
	}
}
