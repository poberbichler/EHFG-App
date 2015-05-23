package org.ehfg.app.program;

import org.ehfg.app.rest.ConferenceDayRepresentation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author patrick
 * @since 06.04.2014
 */
public interface ProgramFacade {
	Collection<SpeakerDTO> findAllSpeakers();
	
	Collection<SpeakerDTO> findSpeakersWithSession();

	Map<LocalDate, ? extends ConferenceDayRepresentation> findAllSessions();

	List<ConferenceDayDTO> findDays();

	void saveDays(List<ConferenceDayDTO> dayList);

	ConferenceDayDTO addDay();
	
	Collection<SessionDTO> findAllSessionsWithoutDayInformation();
	
	List<String> findAvailableLocations();
}
