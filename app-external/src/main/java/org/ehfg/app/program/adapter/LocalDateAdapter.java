package org.ehfg.app.program.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 * @since 06.2014
 */
public final class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
	private static final Logger logger = LoggerFactory.getLogger(LocalDateAdapter.class);
	
	@Override
	public LocalDate unmarshal(String value) throws Exception {
		try {
			return LocalDate.parse(value, DateTimeFormat.forPattern("yyyy-MM-dd"));
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
		
		return value.toString("dd.MM.yyyy");
	}

}
