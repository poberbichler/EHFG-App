package org.ehfg.app.core.external;

import java.util.List;

import org.ehfg.app.api.dto.SessionDTO;

public interface SessionRepository {
	List<SessionDTO> findAll();
	SessionDTO findById(Long sessionId);
	List<SessionDTO> findBySpeaker(Long speakerId);
}
