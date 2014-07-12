package org.ehfg.app.external.rss.repository.mock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.external.rss.data.speaker.RssSpeaker;
import org.ehfg.app.external.rss.repository.AbstractSpeakerRepository;
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
class SpeakerRepositoryMock extends AbstractSpeakerRepository {
	@Override
	public SpeakerDTO findById(Long speakerId) {
		return dataCache.get(speakerId);
	}

	@Override
	public List<SpeakerDTO> findAll() {
		return new ArrayList<SpeakerDTO>(dataCache.values());
	}
	
	@PostConstruct
	private void readDataFromFile() throws JAXBException, IOException {
		final Resource sessionResource = new ClassPathResource("mock/speaker.xml");
		
		final Unmarshaller unmarshaller = JAXBContext.newInstance(RssSpeaker.class).createUnmarshaller();
		final RssSpeaker rssSpeaker= (RssSpeaker) unmarshaller.unmarshal(sessionResource.getFile());
		
		super.fillCache(rssSpeaker);
	}
}
