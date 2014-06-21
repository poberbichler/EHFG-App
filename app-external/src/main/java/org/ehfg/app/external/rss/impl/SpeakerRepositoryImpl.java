package org.ehfg.app.external.rss.impl;

import java.util.List;

import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.core.external.SpeakerRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 * @since 21.06.2014
 */
@Repository
@Profile("!mock")
class SpeakerRepositoryImpl implements SpeakerRepository {

	@Override
	public SpeakerDTO findById(Long speakerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpeakerDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
