package org.ehfg.app.core.facade;

import java.util.List;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.api.facade.ProgramFacade;
import org.ehfg.app.core.repository.SessionRepository;
import org.ehfg.app.core.repository.SpeakerRepository;

public class ProgramFacadeImpl implements ProgramFacade {
	private final SpeakerRepository speakerRepository;
	private final SessionRepository sessionRepository;

	public ProgramFacadeImpl(SpeakerRepository speakerRepository,
			SessionRepository sessionRepository) {
		super();
		this.speakerRepository = speakerRepository;
		this.sessionRepository = sessionRepository;
	}

	@Override
	public List<SpeakerDTO> findAllSpeakers() {
		return speakerRepository.findAll();
	}

	@Override
	public SpeakerDTO findSpeakerById(Long speakerId) {
		return speakerRepository.findById(speakerId);
	}

	@Override
	public List<SessionDTO> findAllSessions() {
		return sessionRepository.findAll();
	}

	@Override
	public SessionDTO findSessionById(Long sessionId) {
		return sessionRepository.findById(sessionId);
	}

	@Override
	public List<SessionDTO> findSessionsBySpeaker(Long speakerId) {
		return sessionRepository.findBySpeaker(speakerId);
	}

	@Override
	public List<SessionDTO> findSessionByDay() {
		return null;
	}

}
