package org.ehfg.app.converter;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
	private static final String DEAFULT_PATTERN = "yyyy-MM-dd";

	@Override
	public LocalDate convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return LocalDate.now();
		}

		return createLocalDateTimeFrom(source).toLocalDate();
	}
	
	private LocalDateTime createLocalDateTimeFrom(String source) {
		return LocalDateTime.parse(source, new DateTimeFormatterFactory(DEAFULT_PATTERN).createDateTimeFormatter());
	}
}