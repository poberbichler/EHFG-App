package org.ehfg.app.external.rss.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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

/**
 * 
 * @author patrick
 * @since 12.07.2014
 */
public abstract class AbstractSessionRepository implements SessionRepository {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected final Map<String, SessionDTO> dataCache = new LinkedHashMap<>();

	protected void fillCache(RssEvent data, RssSpeakerEvents speakerEvents) {
		if (data != null) {
			List<Event> sessions = data.getChannel().getItems();
			Map<String, Set<String>> speakerMap = buildSpeakerMap(speakerEvents);

			dataCache.clear();
			logger.info("received {} sessions", sessions.size());

			List<SessionDTO> sessionList = new ArrayList<>(sessions.size());
			for (final Event session : sessions) {
				logger.debug("preparing text for session {}", session);

				sessionList.add(new SessionDTO.Builder().id(session.getId())
						.name(new StringBuilder(session.getCode()).append(" - ").append(session.getEvent()).toString())
						.description(EscapeUtils.escapeText(session.getDetails()))
						.startTime(session.getDay().toDateTime(session.getStart())).endTime(session.getDay().toDateTime(session.getEnd()))
						.location(session.getRoom()).speakers(speakerMap.get(session.getId())).build());
			}

			Collections.sort(sessionList);
			for (SessionDTO session : sessionList) {
				dataCache.put(session.getId(), session);
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
