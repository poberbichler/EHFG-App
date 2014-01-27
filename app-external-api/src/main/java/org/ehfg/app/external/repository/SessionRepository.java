package org.ehfg.app.external.repository;

import java.util.List;

import org.ehfg.app.api.dto.SessionDTO;

public interface SessionRepository {
	List<SessionDTO> findAll();
	SessionDTO findById(Long sessionId);
	List<SessionDTO> findBySpeaker(Long speakerId);
	List<SessionDTO> findByDay();
}
