package org.ehfg.app.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface SessionRepresentation {
	@XmlElement(name = "id")
	String getId();

	@XmlElement(name = "name")
	String getName();

	@XmlElement(name = "description")
	String getDescription();

	@XmlElement(name = "startTime")
	LocalDateTime getStartTime();

	@XmlElement(name = "endTime")
	LocalDateTime getEndTime();

	@XmlElement(name = "sessionCode")
	String getSessionCode();

	@XmlElement(name = "locationId")
	String getLocationId();

	@XmlElement(name = "speakers")
	Set<String> getSpeakers();
}
