package org.ehfg.app.program;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
				.map(day -> new ConferenceDay(day.getDescription(), day.getDay()))
				.collect(Collectors.toList());
	}
	
	public static List<ConferenceDayDTO> mapToDTO(final Iterable<ConferenceDay> source) {
        return StreamSupport.stream(source.spliterator(), false)
                .map(day -> map(day))
                .collect(Collectors.toList());
	}
	
	public static ConferenceDayDTO map(final ConferenceDay source) {
		return new ConferenceDayDTO(source.getId(), source.getDate(), source.getDescription());
	}
}
