package org.ehfg.app.external.rss.impl;

import java.util.List;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.core.external.SessionRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 * @since 21.06.2014
 */
@Repository
@Profile("!mock")
class SessionRepositoryImpl implements SessionRepository {

	@Override
	public List<SessionDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionDTO findById(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SessionDTO> findBySpeaker(Long speakerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SessionDTO> findByDay() {
		// TODO Auto-generated method stub
		return null;
	}

}
