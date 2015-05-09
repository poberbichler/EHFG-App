package org.ehfg.app.program;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
		return findAllSpeakers().stream()
				.filter(speaker -> eventSpeakers.contains(speaker.getId()))
				.distinct().sorted()
				.collect(Collectors.toList());
	}

	private Set<String> findEverySpeakerForSession() {
		return findAllSessionsWithoutDayInformation().stream()
				.map(SessionDTO::getSpeakers)
				.reduce(new HashSet<>(), (allSpeakers, currentSpeakers) -> {
					allSpeakers.addAll(currentSpeakers);
					return allSpeakers;
				});
	}

	@Override
	public Map<ConferenceDayDTO, List<SessionDTO>> findAllSessions() {
		final List<ConferenceDayDTO> conferenceDays = ConferenceDayMapper.mapToDTO(conferenceDayRepository.findAll());

		return sessionRepository.findAll().stream()
				.filter(session -> findDay(conferenceDays, session).isPresent())
				.collect(Collectors.groupingBy(session -> findDay(conferenceDays, session).get()));
	}

	/**
	 * searches for a {@link ConferenceDayDTO} for the given date
	 */
	private Optional<ConferenceDayDTO> findDay(final List<ConferenceDayDTO> days, final SessionDTO session) {
		final DateTime sessionDate = session.getStartTime();

		return days.stream()
				.filter(day -> day.getDay().equals(sessionDate.toLocalDate()))
				.findFirst();
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
		return sessionRepository.findAll().stream()
				.map(SessionDTO::getLocationId)
				.distinct()
				.collect(Collectors.toList());
	}
}
