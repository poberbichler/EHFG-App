package org.ehfg.app.core.mapper;

import java.util.ArrayList;
import java.util.Iterator;
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

	}

	public static final List<ConferenceDay> map(final List<ConferenceDayDTO> source) {
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
		final Iterator<ConferenceDay> iterator = source.iterator();
		final List<ConferenceDayDTO> result = new ArrayList<>();
		while (iterator.hasNext()) {
			result.add(map(iterator.next()));
		}
		
		return result;
	}
	
	public static final ConferenceDayDTO map(final ConferenceDay source) {
		return new ConferenceDayDTO(source.getId(), LocalDate.fromDateFields(source.getDate()), source.getDescription());
	}
}
