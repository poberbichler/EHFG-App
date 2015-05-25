package org.ehfg.app.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;

/**
 * @author patrick
 * @since 05.2015
 */
public class LocalDateToUTCTimestampSerializer extends JsonSerializer<LocalDate> {
	@Override
	public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
		if (value != null) {
			generator.writeNumber(value.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
		}
	}
}
