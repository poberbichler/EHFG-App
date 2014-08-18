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

	Map<ConferenceDayDTO, List<SessionDTO>> findAllSessions();

	List<ConferenceDayDTO> findDays();

	void saveDays(List<ConferenceDayDTO> dayList);

	ConferenceDayDTO addDay();
	
	List<SessionDTO> findAllSessionsWithoutDayInformation();
	
	List<String> findAvailableLocations();
}
