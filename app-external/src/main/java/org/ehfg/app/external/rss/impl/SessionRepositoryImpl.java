package org.ehfg.app.external.rss.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.core.external.SessionRepository;
import org.ehfg.app.external.rss.data.events.Event;
import org.ehfg.app.external.rss.data.events.RssEvent;
import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.ehfg.app.external.rss.data.speakerevents.RssSpeakerEvents;
import org.ehfg.app.external.rss.data.speakerevents.SpeakerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
class SessionRepositoryImpl implements SessionRepository, ApplicationListener<DataUpdatedEvent> {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Map<String, SessionDTO> dataCache = new HashMap<>();

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
		final RssEvent data = event.getDataForClass(RssEvent.class);
		if (data != null) {
			List<Event> sessions = data.getChannel().getItems();
			Map<String, Set<String>> speakerMap = buildSpeakerMap(event.getDataForClass(RssSpeakerEvents.class));

			dataCache.clear();
			logger.info("received {} sessions", sessions.size());
			for (final Event session : sessions) {
				dataCache.put(session.getId(), new SessionDTO(session.getId(), session.getCode(), session.getDetails(), new Date(),
						new Date(), session.getRoom(), speakerMap.get(session.getId())));
			}
		}

		else {
			logger.error("did not receive data for {}", RssSpeaker.class.getName());
		}
	}

	private Map<String, Set<String>> buildSpeakerMap(RssSpeakerEvents data) {
		if (data != null) {
			List<SpeakerEvent> speakerEvents = data.getChannel().getSpeakerEvents();
			Map<String, Set<String>> result = new HashMap<>(speakerEvents.size());
			
			logger.info("received {} speakers for events", speakerEvents.size());
			for (SpeakerEvent speakerEvent : speakerEvents) {
				final String eventId = speakerEvent.getEventid();
				
				if (!result.containsKey(eventId)) {
					result.put(eventId, new HashSet<String>());
				}
				
				result.get(eventId).add(speakerEvent.getSpeakerid());
			}
			
			return result;
		}
		
		logger.error("did not receive data for {}", RssSpeakerEvents.class.getName());
		return Collections.emptyMap();
	}
}
