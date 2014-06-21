package org.ehfg.app.api.facade;

import java.util.List;
import java.util.Map;

import org.ehfg.app.api.dto.ConferenceDayDTO;
import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.dto.SpeakerDTO;

/**
 * @author patrick
 * @since 06.04.2014
 */
public interface ProgramFacade {
	List<SpeakerDTO> findAllSpeakers();

	SpeakerDTO findSpeakerById(Long speakerId);

	Map<ConferenceDayDTO, List<SessionDTO>> findAllSessions();

	SessionDTO findSessionById(Long sessionId);

	List<SessionDTO> findSessionsBySpeaker(Long speakerId);

	List<SessionDTO> findSessionByDay();

	List<ConferenceDayDTO> findDays();

	void saveDays(List<ConferenceDayDTO> dayList);

	void removeDay(Long dayId);

	ConferenceDayDTO addDay();
	
	List<SessionDTO> findAllSessionsWithoutDayInformation();
}
