package org.ehfg.app.program.strategy;

import org.ehfg.app.MockService;
import org.ehfg.app.program.data.speaker.RssSpeaker;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author patrick
 * @since 01.2015
 */
@MockService
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
