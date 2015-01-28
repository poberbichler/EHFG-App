package org.ehfg.app.program.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link XmlAdapter} to map a given string of the format {@code 15:24} to a {@link LocalTime}
 *
 * @author patrick
 * @since 06.2014
 */
public final class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
	private static final Logger logger = LoggerFactory.getLogger(LocalTimeAdapter.class);
	private static final DateTimeFormatter PATTERN = DateTimeFormat.forPattern("HH:mm");
	
	@Override
	public LocalTime unmarshal(String value) throws Exception {
		try {
			if (value.equals("24:00")) {
				return LocalTime.parse("23:59", PATTERN);
			}
			
			return LocalTime.parse(value, PATTERN);
		}
		
		catch (Exception e) {
			logger.error("'{}' could not be parsed to a valid LocalTime", value);
		}
		
		return LocalTime.now();
	}

	@Override
	public String marshal(LocalTime value) throws Exception {
		if (value == null) {
			return "";
		}
		
		return value.toString("hh:mm");
	}

}
