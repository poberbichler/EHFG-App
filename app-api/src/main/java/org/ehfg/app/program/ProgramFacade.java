package org.ehfg.app.program;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author patrick
 * @since 06.04.2014
 */
public interface ProgramFacade {
	List<SpeakerDTO> findAllSpeakers();
	
	Collection<SpeakerDTO> findSpeakersWithSession();

	Map<ConferenceDayDTO, List<SessionDTO>> findAllSessions();

	List<ConferenceDayDTO> findDays();

	void saveDays(List<ConferenceDayDTO> dayList);

	ConferenceDayDTO addDay();
	
	List<SessionDTO> findAllSessionsWithoutDayInformation();
	
	List<String> findAvailableLocations();
}
