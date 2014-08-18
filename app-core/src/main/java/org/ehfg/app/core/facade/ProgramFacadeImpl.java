package org.ehfg.app.core.facade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.ehfg.app.api.dto.ConferenceDayDTO;
import org.ehfg.app.api.dto.SessionDTO;
import org.ehfg.app.api.dto.SpeakerDTO;
import org.ehfg.app.api.facade.ProgramFacade;
import org.ehfg.app.core.entities.ConferenceDay;
import org.ehfg.app.core.external.SessionRepository;
import org.ehfg.app.core.external.SpeakerRepository;
import org.ehfg.app.core.mapper.ConferenceDayMapper;
import org.ehfg.app.core.repository.ConferenceDayRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author patrick
 * @since 06.04.2014
 */
@Component("programFacade")
@Transactional(readOnly = true)
public class ProgramFacadeImpl implements ProgramFacade {
	private final SpeakerRepository speakerRepository;
	private final SessionRepository sessionRepository;
	private final ConferenceDayRepository conferenceDayRepository;

	@Autowired
	public ProgramFacadeImpl(SpeakerRepository speakerRepository, SessionRepository sessionRepository,
			ConferenceDayRepository conferenceDayRepository) {
		super();
		this.speakerRepository = speakerRepository;
		this.sessionRepository = sessionRepository;
		this.conferenceDayRepository = conferenceDayRepository;
	}

	@Override
	public List<SpeakerDTO> findAllSpeakers() {
		return speakerRepository.findAll();
	}

	@Override
	public Map<ConferenceDayDTO, List<SessionDTO>> findAllSessions() {
		final Map<ConferenceDayDTO, List<SessionDTO>> result = new TreeMap<>();
		final List<ConferenceDayDTO> conferenceDays = ConferenceDayMapper.map(conferenceDayRepository.findAll());

		for (final SessionDTO session : sessionRepository.findAll()) {
			final ConferenceDayDTO conferenceDay = findDay(conferenceDays, session);

			if (conferenceDay == null) {
				continue;
			}

			if (!result.containsKey(conferenceDay)) {
				result.put(conferenceDay, new ArrayList<SessionDTO>());
			}

			result.get(conferenceDay).add(session);
		}

		return result;
	}

	/**
	 * searches for a {@link ConferenceDayDTO} for the given date
	 * 
	 * @param days
	 *            list of {@link ConferenceDayDTO}
	 * @param session
	 *            to be searched for
	 * @return the resulting {@link ConferenceDayDTO}
	 */
	private ConferenceDayDTO findDay(final List<ConferenceDayDTO> days, final SessionDTO session) {
		final DateTime sessionDate = session.getStartTime();
		
		for (final ConferenceDayDTO day : days) {
			if (sessionDate.toLocalDate().equals(day.getDay())) {
				return day;
			}
		}

		return null;
	}

	@Override
	public List<ConferenceDayDTO> findDays() {
		List<ConferenceDayDTO> result = ConferenceDayMapper.map(conferenceDayRepository.findAll());
		Collections.sort(result);

		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveDays(List<ConferenceDayDTO> dayList) {
		final List<ConferenceDay> source = ConferenceDayMapper.map(dayList);
		conferenceDayRepository.deleteAll();
		conferenceDayRepository.save(source);
	}

	@Override
	@Transactional(readOnly = false)
	public ConferenceDayDTO addDay() {
		final ConferenceDay day = new ConferenceDay();
		day.setDate(new Date());
		day.setDescription("description");
		conferenceDayRepository.save(day);

		return ConferenceDayMapper.map(day);
	}

	@Override
	public List<SessionDTO> findAllSessionsWithoutDayInformation() {
		return sessionRepository.findAll();
	}

	@Override
	public List<String> findAvailableLocations() {
		Set<String> result = new HashSet<>();
		for (SessionDTO session : sessionRepository.findAll()) {
			result.add(session.getLocationId());
		}
		
		return new LinkedList<>(result);
	}
}
