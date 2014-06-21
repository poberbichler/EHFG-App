package org.ehfg.app.external.rss.mock;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.core.external.SessionRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 * @since 24.03.2014
 */
@Repository
@Profile("mock")
class SessionRepositoryMock implements SessionRepository {
	private final static Map<Long, SessionDTO> sessionMap = new LinkedHashMap<>();

	static {
		sessionMap.put(0L, new SessionDTO(0L, "session Name 1", "sessionDescription", "14.06.2014 07:00", "14.06.2014 15:00", 0L, 1L, 0L));
		sessionMap.put(6L, new SessionDTO(0L, "session Name 2", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
		sessionMap.put(7L, new SessionDTO(0L, "session Name 3", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
		sessionMap.put(8L, new SessionDTO(0L, "session Name 4", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));
		sessionMap.put(9L, new SessionDTO(0L, "session Name 5", "sessionDescription", "09.04.2014 10:00", "09.04.2014 12:00", 0L, 1L, 0L));

		sessionMap.put(1L, new SessionDTO(1L, "another Name 6", "antoher Description", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
		sessionMap.put(2L, new SessionDTO(1L, "another Name 7", "antoher Description", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
		sessionMap.put(3L, new SessionDTO(1L, "another Name 8", "antoher Description", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
		sessionMap.put(4L, new SessionDTO(1L, "another Name 9", "antoher Description", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
		sessionMap.put(5L, new SessionDTO(1L, "another Name 0", "antoher Description", "11.04.2014 15:00", "11.04.2014 18:00", 1L, 0L));
	}

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
