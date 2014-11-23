package org.ehfg.app.program;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalDate;

/**
 * @author patrick
 * @since 04.04.2014
 */
final class ConferenceDayMapper {
	private ConferenceDayMapper() {
		// do not allow instantiation
	}

	public static final List<ConferenceDay> mapToEntity(final Iterable<ConferenceDayDTO> source) {
		final List<ConferenceDay> result = new LinkedList<>();

		for (final ConferenceDayDTO day : source) {
			if (!day.isDeleted()) {
				ConferenceDay target = new ConferenceDay();
				target.setId(day.getId());
				target.setDate(day.getDay().toDate());
				target.setDescription(day.getDescription());
				
				result.add(target);
			}
		}

		return result;
	}
	
	public static final List<ConferenceDayDTO> mapToDTO(final Iterable<ConferenceDay> source) {
		final List<ConferenceDayDTO> result = new LinkedList<>();
		for (final ConferenceDay day : source) {
			result.add(map(day));
		}
		
		return result;
	}
	
	public static final ConferenceDayDTO map(final ConferenceDay source) {
		return new ConferenceDayDTO(source.getId(), LocalDate.fromDateFields(source.getDate()), source.getDescription());
	}
}
