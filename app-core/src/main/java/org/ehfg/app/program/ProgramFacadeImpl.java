package org.ehfg.app.program;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
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
final class ProgramFacadeImpl implements ProgramFacade {
	private final SpeakerRepository speakerRepository;
	private final SessionRepository sessionRepository;
	private final ConferenceDayRepository conferenceDayRepository;

	@Autowired
	public ProgramFacadeImpl(SpeakerRepository speakerRepository, SessionRepository sessionRepository,
			ConferenceDayRepository conferenceDayRepository) {
		this.speakerRepository = speakerRepository;
		this.sessionRepository = sessionRepository;
		this.conferenceDayRepository = conferenceDayRepository;
	}

	@Override
	public Collection<SpeakerDTO> findAllSpeakers() {
		return speakerRepository.findAll();
	}
	
	@Override
	public Collection<SpeakerDTO> findSpeakersWithSession() {
		final Set<String> eventSpeakers = findEverySpeakerForSession();
		final Set<SpeakerDTO> allSpeakers = new HashSet<>(findAllSpeakers());
		
		CollectionUtils.filter(allSpeakers, new Predicate<SpeakerDTO>() {
			@Override
			public boolean evaluate(SpeakerDTO object) {
				return eventSpeakers.contains(object.getId());
			}
		});
		
		List<SpeakerDTO> result = new ArrayList<>(allSpeakers);
		Collections.sort(result);
		
		return result;
	}

	private Set<String> findEverySpeakerForSession() {
		final Set<String> result = new HashSet<>();
		for (SessionDTO session : findAllSessionsWithoutDayInformation()) {
			result.addAll(session.getSpeakers());
		}
		
		return result;
	}

	@Override
	public Map<ConferenceDayDTO, List<SessionDTO>> findAllSessions() {
		final Map<ConferenceDayDTO, List<SessionDTO>> result = new TreeMap<>();
		final List<ConferenceDayDTO> conferenceDays = ConferenceDayMapper.mapToDTO(conferenceDayRepository.findAll());

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
		List<ConferenceDayDTO> result = ConferenceDayMapper.mapToDTO(conferenceDayRepository.findAll());
		Collections.sort(result);

		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveDays(List<ConferenceDayDTO> dayList) {
		final List<ConferenceDay> source = ConferenceDayMapper.mapToEntity(dayList);
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
	public Collection<SessionDTO> findAllSessionsWithoutDayInformation() {
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
