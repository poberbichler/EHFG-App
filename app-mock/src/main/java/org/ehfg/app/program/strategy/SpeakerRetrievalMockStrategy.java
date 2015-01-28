package org.ehfg.app.program.strategy;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.program.data.speaker.RssSpeaker;
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
public class SpeakerRetrievalMockStrategy extends AbstractDataRetrievalStrategy<RssSpeaker> {
	private RssSpeaker rssSpeaker;

	SpeakerRetrievalMockStrategy() {
		super(RssSpeaker.class, "");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		final Resource sessionResource = new ClassPathResource("mock/speaker.xml");
		final Unmarshaller unmarshaller = JAXBContext.newInstance(RssSpeaker.class).createUnmarshaller();
		this.rssSpeaker= (RssSpeaker) unmarshaller.unmarshal(sessionResource.getFile());
	}
	
	@Override
	public RssSpeaker fetchData() {
		return rssSpeaker;
	}
}
