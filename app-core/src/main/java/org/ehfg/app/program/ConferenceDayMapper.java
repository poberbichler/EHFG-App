package org.ehfg.app.program;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;

/**
 * @author patrick
 * @since 04.04.2014
 */
final class ConferenceDayMapper {
	private ConferenceDayMapper() {
		// do not allow instantiation
	}

	public static List<ConferenceDay> mapToEntity(final Collection<ConferenceDayDTO> source) {
		return source.stream()
				.filter(day -> !day.isDeleted())
				.map(day -> new ConferenceDay(day.getDescription(), day.getDay().toDate()))
				.collect(Collectors.toList());
	}
	
	public static List<ConferenceDayDTO> mapToDTO(final Iterable<ConferenceDay> source) {
		final List<ConferenceDayDTO> result = new LinkedList<>();
		source.forEach(day -> result.add(map(day)));
		return result;
	}
	
	public static ConferenceDayDTO map(final ConferenceDay source) {
		return new ConferenceDayDTO(source.getId(), LocalDate.fromDateFields(source.getDate()), source.getDescription());
	}
}
