package org.ehfg.app.program.strategy;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.program.data.speakerevents.RssSpeakerEvents;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 01.2015
 */
@Service
@Profile({ "mock" })
public class SpeakerEventRetrievalMockStrategy extends AbstractDataRetrievalStrategy<RssSpeakerEvents> {
	private RssSpeakerEvents rssSpeakerEvents;

	SpeakerEventRetrievalMockStrategy() {
		super(RssSpeakerEvents.class, "");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		final Resource speakerEventResource = new ClassPathResource("mock/speaker-events.xml");
		final Unmarshaller speakerEventunmarshaller = JAXBContext.newInstance(RssSpeakerEvents.class).createUnmarshaller();
		this.rssSpeakerEvents = (RssSpeakerEvents) speakerEventunmarshaller.unmarshal(speakerEventResource.getFile());
	}
	
	@Override
	public RssSpeakerEvents fetchData() {
		return rssSpeakerEvents;
	}
}
