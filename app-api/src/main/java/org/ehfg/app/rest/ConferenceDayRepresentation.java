package org.ehfg.app.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlRootElement(name = "day")
@XmlAccessorType(XmlAccessType.NONE)
public interface ConferenceDayRepresentation {
	@XmlElement(name = "timestamp")
	LocalDate getDay();

	@XmlElement(name = "description")
	String getDescription();
}
