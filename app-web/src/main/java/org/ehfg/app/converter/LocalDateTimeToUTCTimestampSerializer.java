package org.ehfg.app.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author patrick
 * @since 05.2015
 */
public class LocalDateTimeToUTCTimestampSerializer extends JsonSerializer<LocalDateTime> {
	@Override
	public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
		if (value != null) {
			generator.writeNumber(value.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli());
		}
	}
}
