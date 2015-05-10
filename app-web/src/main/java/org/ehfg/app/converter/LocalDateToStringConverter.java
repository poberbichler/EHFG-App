package org.ehfg.app.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author patrick
 * @since 05.2015
 */
public class LocalDateToStringConverter implements Converter<LocalDate, String> {
	@Override
	public String convert(LocalDate source) {
		return source.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}
}
