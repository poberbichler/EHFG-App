package org.ehfg.app.program.repository;

import org.apache.commons.lang3.StringUtils;
import org.ehfg.app.program.SessionDTO;
import org.ehfg.app.program.SessionRepository;
import org.ehfg.app.program.data.events.Event;
import org.ehfg.app.program.data.events.RssEvent;
import org.ehfg.app.program.data.speakerevents.RssSpeakerEvents;
import org.ehfg.app.program.data.speakerevents.SpeakerEvent;
import org.ehfg.app.program.strategy.AbstractDataRetrievalStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 01.2015
 */
@Repository
class SessionRepositoryImpl implements SessionRepository {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final AbstractDataRetrievalStrategy<RssEvent> eventStrategy;
	private final AbstractDataRetrievalStrategy<RssSpeakerEvents> speakerEventStrategy;

	@Autowired
	public SessionRepositoryImpl(AbstractDataRetrievalStrategy<RssEvent> eventStrategy,
			AbstractDataRetrievalStrategy<RssSpeakerEvents> speakerEventStrategy) {
		this.eventStrategy = eventStrategy;
		this.speakerEventStrategy = speakerEventStrategy;
	}

	@Override
	@Cacheable("session")
	public Collection<SessionDTO> findAll() {
		List<Event> sessions = eventStrategy.fetchData().getChannel().getItems();
		Map<String, Set<String>> speakerMap = fetchSpeakerMap();

		logger.info("received {} sessions", sessions.size());

		return sessions.stream().map(session -> {
			logger.trace("preparing text for session {}", session);

			String details = EscapeUtils.escapeText(session.getDetails());
			details = EscapeUtils.escapeLinks(details);
			details = StringUtils.removeStart(details, "<p> ");
			details = StringUtils.removeStart(details, "<strong>");
			details = StringUtils.removeStart(details, EscapeUtils.escapeText(session.getEvent()));
			details = StringUtils.removeStart(details, ".");
			details = StringUtils.removeStart(details, "<br>");
			details = StringUtils.removeStart(details, "<br/>");
			details = StringUtils.removeStart(details, "<br />");
			details = StringUtils.removeStart(details, "<br></br>");

			return new SessionDTO.Builder().id(session.getId())
					.name(session.getEvent()).sessionCode(session.getCode())
					.description(EscapeUtils.escapeText(details))
					.location(session.getRoom()).speakers(speakerMap.get(session.getId()))
					.startTime(session.getDay().atTime(session.getStart()))
					.endTime(session.getDay().atTime(session.getEnd()))
					.build();
		}).sorted().collect(Collectors.toList());
	}
	
	private Map<String, Set<String>> fetchSpeakerMap() {
		final List<SpeakerEvent> speakerEvents = speakerEventStrategy.fetchData().getChannel().getSpeakerEvents();
		logger.info("received {} speakers for events", speakerEvents.size());
		return speakerEvents.stream()
				.collect(Collectors.groupingBy(
						SpeakerEvent::getEventid,
						Collectors.mapping(SpeakerEvent::getSpeakerid, Collectors.toSet())));
	}
}
