package org.ehfg.app.external.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.core.external.SessionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SessionRepositoryImpl implements SessionRepository {
	private final static Map<Long, SessionDTO> sessionMap = new HashMap<Long, SessionDTO>() {
		private static final long serialVersionUID = -32316877290559606L;
		{
			put(0L, new SessionDTO(0L, "session Name 1", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
			put(6L, new SessionDTO(0L, "session Name 2", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
			put(7L, new SessionDTO(0L, "session Name 3", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
			put(8L, new SessionDTO(0L, "session Name 4", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
			put(9L, new SessionDTO(0L, "session Name 5", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
			
			put(1L, new SessionDTO(1L, "another session Name 6", "antoher sessionDescription", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
			put(2L, new SessionDTO(1L, "another session Name 7", "antoher sessionDescription", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
			put(3L, new SessionDTO(1L, "another session Name 8", "antoher sessionDescription", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
			put(4L, new SessionDTO(1L, "another session Name 9", "antoher sessionDescription", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
			put(5L, new SessionDTO(1L, "another session Name 0", "antoher sessionDescription", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
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
			if (entry.getValue().getSpeakers().contains(speakerId)) {
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
