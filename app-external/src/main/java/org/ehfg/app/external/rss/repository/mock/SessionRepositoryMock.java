package org.ehfg.app.external.rss.repository.mock;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.external.rss.data.events.RssEvent;
import org.ehfg.app.external.rss.data.speakerevents.RssSpeakerEvents;
import org.ehfg.app.external.rss.repository.AbstractSessionRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 * @since 24.03.2014
 */
@Repository
@Profile("mock")
class SessionRepositoryMock extends AbstractSessionRepository {
	@PostConstruct
	private void readDataFromFile() throws JAXBException, IOException {
		final Resource sessionResource = new ClassPathResource("mock/events.xml");
		final Unmarshaller eventUnmarshaller = JAXBContext.newInstance(RssEvent.class).createUnmarshaller();
		final RssEvent rssEvent = (RssEvent) eventUnmarshaller.unmarshal(sessionResource.getFile());

		final Resource speakerEventResource = new ClassPathResource("mock/speaker-events.xml");
		final Unmarshaller speakerEventunmarshaller = JAXBContext.newInstance(RssSpeakerEvents.class).createUnmarshaller();
		final RssSpeakerEvents rssSpeakerEvents = (RssSpeakerEvents) speakerEventunmarshaller.unmarshal(speakerEventResource.getFile());
		
		super.fillCache(rssEvent, rssSpeakerEvents);
	}
}
