package org.ehfg.app.program.repository.mock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.program.AbstractSpeakerRepository;
import org.ehfg.app.program.data.speaker.RssSpeaker;
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
class SpeakerRepositoryMock extends AbstractSpeakerRepository implements InitializingBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		final Resource sessionResource = new ClassPathResource("mock/speaker.xml");
		
		final Unmarshaller unmarshaller = JAXBContext.newInstance(RssSpeaker.class).createUnmarshaller();
		final RssSpeaker rssSpeaker= (RssSpeaker) unmarshaller.unmarshal(sessionResource.getFile());
		
		super.fillCache(rssSpeaker);
	}
}
