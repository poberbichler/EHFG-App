package org.ehfg.app.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author patrick
 * @since 11.2014
 */
public class StringToLocalDateConverter implements Converter<String, LocalDate> {
	private static final String DEFAULT_PATTERN = "dd.MM.yyyy";

	@Override
	public LocalDate convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return LocalDate.now();
		}

		return createLocalDateTimeFrom(source).toLocalDate();
	}
	
	private LocalDateTime createLocalDateTimeFrom(String source) {
		return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DEFAULT_PATTERN));
	}
}
