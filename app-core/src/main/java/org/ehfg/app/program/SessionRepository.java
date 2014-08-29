package org.ehfg.app.program;

import java.util.List;

interface SessionRepository {
	List<SessionDTO> findAll();
	SessionDTO findById(Long sessionId);
	List<SessionDTO> findBySpeaker(Long speakerId);
}
