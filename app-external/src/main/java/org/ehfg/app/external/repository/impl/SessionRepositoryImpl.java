package org.ehfg.app.external.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.external.repository.SessionRepository;

public class SessionRepositoryImpl implements SessionRepository {
	private final static Map<Long, SessionDTO> sessionMap = new HashMap<Long, SessionDTO>() {
		private static final long serialVersionUID = -32316877290559606L;
		{
			put(0L, new SessionDTO(0L, 0L, "sessioName", "sessionDescription"));
			put(1L, new SessionDTO(1L, 1L, "another sessioName","antoher sessionDescription"));
		}
	};

	@Override
	public List<SessionDTO> findAll() {
		return new ArrayList<SessionDTO>(sessionMap.values());
	}

	@Override
	public SessionDTO findById(Long sessionId) {
		return sessionMap.get(sessionId);
	}

	@Override
	public List<SessionDTO> findBySpeaker(Long speakerId) {
		List<SessionDTO> result = new ArrayList<SessionDTO>();
		for (Entry<Long, SessionDTO> entry : sessionMap.entrySet()) {
			if (entry.getValue().getSpeakerId().equals(speakerId)) {
				result.add(entry.getValue());
			}
		}
		
		return result;
	}

	@Override
	public List<SessionDTO> findByDay() {
		List<SessionDTO> result = new ArrayList<SessionDTO>();
		
		return result;
	}

}
