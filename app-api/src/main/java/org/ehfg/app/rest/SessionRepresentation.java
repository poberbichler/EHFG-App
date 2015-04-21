package org.ehfg.app.rest;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.joda.time.DateTime;

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
	DateTime getStartTime();

	@XmlElement(name = "endTime")
	DateTime getEndTime();

	@XmlElement(name = "sessionCode")
	String getSessionCode();

	@XmlElement(name = "locationId")
	String getLocationId();

	@XmlElement(name = "speakers")
	Set<String> getSpeakers();
}
