package org.ehfg.app.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ehfg.app.api.dto.ConferenceDayDTO;
import org.ehfg.app.core.entities.ConferenceDay;
import org.joda.time.LocalDate;

/**
 * @author patrick
 * @since 04.04.2014
 */
public class ConferenceDayMapper {
	private ConferenceDayMapper() {
		// do not allow instantiation
	}

	public static final List<ConferenceDay> map(final Collection<ConferenceDayDTO> source) {
		final List<ConferenceDay> result = new ArrayList<>(source.size());

		for (final ConferenceDayDTO day : source) {
			ConferenceDay target = new ConferenceDay();
			target.setId(day.getId());
			target.setDate(day.getDay().toDate());
			target.setDescription(day.getDescription());

			result.add(target);
		}

		return result;
	}
	
	public static final List<ConferenceDayDTO> map(final Iterable<ConferenceDay> source) {
		final List<ConferenceDayDTO> result = new ArrayList<>();
		for (final ConferenceDay day : source) {
			result.add(map(day));
		}
		
		return result;
	}
	
	public static final ConferenceDayDTO map(final ConferenceDay source) {
		return new ConferenceDayDTO(source.getId(), LocalDate.fromDateFields(source.getDate()), source.getDescription());
	}
}
