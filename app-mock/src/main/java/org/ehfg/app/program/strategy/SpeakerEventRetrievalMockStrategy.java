package org.ehfg.app.program.strategy;

import org.ehfg.app.MockService;
import org.ehfg.app.program.data.speakerevents.RssSpeakerEvents;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author patrick
 * @since 01.2015
 */
@MockService
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
