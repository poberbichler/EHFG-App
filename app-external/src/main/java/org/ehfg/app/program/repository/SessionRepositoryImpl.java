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

import static java.util.stream.Collectors.*;

/**
 * @author patrick
 * @since 01.2015
 */
@Repository
class SessionRepositoryImpl implements SessionRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AbstractDataRetrievalStrategy<RssEvent> eventStrategy;
    private final AbstractDataRetrievalStrategy<RssSpeakerEvents> speakerEventStrategy;

    private Map<String, Set<String>> speakerMap;

    @Autowired
    public SessionRepositoryImpl(AbstractDataRetrievalStrategy<RssEvent> eventStrategy,
                                 AbstractDataRetrievalStrategy<RssSpeakerEvents> speakerEventStrategy) {
        this.eventStrategy = eventStrategy;
        this.speakerEventStrategy = speakerEventStrategy;
    }

    @Override
    @Cacheable("session")
    public Collection<SessionDTO> findAll() {
        updateSpeakerMap();
        List<Event> sessions = eventStrategy.fetchData().getChannel().getItems();

        logger.info("received {} sessions", sessions.size());

        return sessions.stream()
                .map(this::toSessionDTO)
                .sorted()
                .collect(toList());
    }

    private SessionDTO toSessionDTO(Event event) {
        logger.trace("preparing text for session {}", event);

        String details = EscapeUtils.escapeText(event.getDetails());
        details = EscapeUtils.escapeLinks(details);
        details = StringUtils.removeStart(details, "<p> ");
        details = StringUtils.removeStart(details, "<strong>");
        details = StringUtils.removeStart(details, EscapeUtils.escapeText(event.getEvent()));
        details = StringUtils.removeStart(details, ".");
        details = StringUtils.removeStart(details, "<br>");
        details = StringUtils.removeStart(details, "<br/>");
        details = StringUtils.removeStart(details, "<br />");
        details = StringUtils.removeStart(details, "<br></br>");

        // the backend does return '?' instead of the real character... *sigh*
        String eventName = event.getEvent()
                .replace("?Best", "\"Best")
                .replace("Service?", "Service\"")
                .replace("?Healthy?", "\"Healthy\"")
                .replace("Alzheimer?s", "Alzheimer's");

        return new SessionDTO.Builder().id(event.getId())
                .name(eventName).sessionCode(event.getCode())
                .description(EscapeUtils.escapeText(details))
                .location(event.getRoom()).speakers(speakerMap.get(event.getId()))
                .startTime(event.getDay().atTime(event.getStart()))
                .endTime(event.getDay().atTime(event.getEnd()))
                .build();

    }

    private void updateSpeakerMap() {
        final List<SpeakerEvent> speakerEvents = speakerEventStrategy.fetchData().getChannel().getSpeakerEvents();
        logger.info("received {} speakers for events", speakerEvents.size());
        this.speakerMap = speakerEvents.stream()
                .collect(groupingBy(SpeakerEvent::getEventid,
                        mapping(SpeakerEvent::getSpeakerid, toSet())));
    }
}
