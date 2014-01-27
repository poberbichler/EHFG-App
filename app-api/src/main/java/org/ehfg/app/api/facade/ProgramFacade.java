package org.ehfg.app.api.facade;

import java.util.List;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.dto.SpeakerDTO;

public interface ProgramFacade {
	List<SpeakerDTO> findAllSpeakers();
	
	SpeakerDTO findSpeakerById(Long speakerId);
	
	List<SessionDTO> findAllSessions();
	
	SessionDTO findSessionById(Long sessionId);
	
	List<SessionDTO> findSessionsBySpeaker(Long speakerId);
	
	List<SessionDTO> findSessionByDay();
}
