package org.ehfg.app.converter;

import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author patrick
 * @since 06.2015
 */
public class LongToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
	@Override
	public LocalDateTime convert(String source) {
		if (source == null) {
			return LocalDateTime.now();
		}

		return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(source)), ZoneId.systemDefault());
	}
}
