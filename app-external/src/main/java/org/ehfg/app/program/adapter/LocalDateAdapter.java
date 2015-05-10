package org.ehfg.app.program.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author patrick
 * @since 06.2014
 */
public final class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
	private static final Logger logger = LoggerFactory.getLogger(LocalDateAdapter.class);
	
	@Override
	public LocalDate unmarshal(String value) throws Exception {
		try {
			return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
		}
		
		catch (Exception e) {
			logger.error("'{}' could not be parsed to a valid LocalDate", value);
		}
		
		return LocalDate.now();
	}

	@Override
	public String marshal(LocalDate value) throws Exception {
		if (value == null) {
			return "";
		}

		return value.toString();
	}

}
