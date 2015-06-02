package org.ehfg.app.program;

import org.ehfg.app.rest.ConferenceDayRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 06.04.2014
 */
@Component
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
				.flatMap(session -> session.getSpeakers().stream())
				.collect(Collectors.toSet());
	}

	@Override
	public Map<LocalDate, ? extends ConferenceDayRepresentation> findAllSessions() {
		final List<ConferenceDayDTO> conferenceDays = ConferenceDayMapper.mapToDTO(conferenceDayRepository.findAll());

		return sessionRepository.findAll().stream()
				.filter(session -> findDay(conferenceDays, session).isPresent())
				.collect(Collectors.toMap(SessionDTO::getDay,
						session -> new ConferenceDayWithSessionsDTO(findDay(conferenceDays, session).get().getDescription(), session),
						(existingSession, newSession) -> existingSession.addSession(newSession.getSessions()),
						TreeMap::new));
	}

	/**
	 * searches for a {@link ConferenceDayDTO} for the given date
	 */
	private Optional<ConferenceDayDTO> findDay(final List<ConferenceDayDTO> days, final SessionDTO session) {
		final LocalDateTime sessionDate = session.getStartTime();

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
	public void saveDays(List<ConferenceDayDTO> dayList) {
		final List<ConferenceDay> source = ConferenceDayMapper.mapToEntity(dayList);
		conferenceDayRepository.deleteAll();
		conferenceDayRepository.save(source);
	}

	@Override
	public ConferenceDayDTO addDay() {
		final ConferenceDay day = new ConferenceDay();
		day.setDate(LocalDate.now());
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
				.map(SessionDTO::getLocation)
				.distinct()
				.collect(Collectors.toList());
	}
}
