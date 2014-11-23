package org.ehfg.app.program.repository.mock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.program.AbstractSessionRepository;
import org.ehfg.app.program.data.events.RssEvent;
import org.ehfg.app.program.data.speakerevents.RssSpeakerEvents;
import org.springframework.beans.factory.InitializingBean;
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
class SessionRepositoryMock extends AbstractSessionRepository implements InitializingBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		final Resource sessionResource = new ClassPathResource("mock/events.xml");
		final Unmarshaller eventUnmarshaller = JAXBContext.newInstance(RssEvent.class).createUnmarshaller();
		final RssEvent rssEvent = (RssEvent) eventUnmarshaller.unmarshal(sessionResource.getFile());
		
		final Resource speakerEventResource = new ClassPathResource("mock/speaker-events.xml");
		final Unmarshaller speakerEventunmarshaller = JAXBContext.newInstance(RssSpeakerEvents.class).createUnmarshaller();
		final RssSpeakerEvents rssSpeakerEvents = (RssSpeakerEvents) speakerEventunmarshaller.unmarshal(speakerEventResource.getFile());
		
		super.fillCache(rssEvent, rssSpeakerEvents);
	}
}
