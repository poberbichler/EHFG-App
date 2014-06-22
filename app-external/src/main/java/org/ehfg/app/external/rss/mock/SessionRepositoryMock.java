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
		sessionMap.put(0L,
				new SessionDTO.Builder().id("0").name("session Name 1").description("session Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("1", "0").build());
		sessionMap.put(1L,
				new SessionDTO.Builder().id("0").name("session Name 2").description("session Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("1", "0").build());
		sessionMap.put(2L,
				new SessionDTO.Builder().id("0").name("session Name 3").description("session Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("1", "0").build());
		sessionMap.put(3L,
				new SessionDTO.Builder().id("0").name("session Name 4").description("session Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("1", "0").build());
		sessionMap.put(4L,
				new SessionDTO.Builder().id("0").name("session Name 5").description("session Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("1", "0").build());

		sessionMap.put(5L,
				new SessionDTO.Builder().id("0").name("another Name 1").description("another Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("0").build());
		sessionMap.put(6L,
				new SessionDTO.Builder().id("0").name("another Name 2").description("another Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("0").build());
		sessionMap.put(7L,
				new SessionDTO.Builder().id("0").name("another Name 3").description("another Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("0").build());
		sessionMap.put(8L,
				new SessionDTO.Builder().id("0").name("another Name 4").description("another Description").startTime("14.06.2014 07:00")
						.endTime("14.06.2014 15:00").location("Raum1").speakers("0").build());
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
}
