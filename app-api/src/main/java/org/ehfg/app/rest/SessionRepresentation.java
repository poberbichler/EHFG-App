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
public interface SessionRepresentation extends Comparable<SessionRepresentation> {
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

	@XmlElement(name = "code")
	String getCode();

	@XmlElement(name = "location")
	String getLocation();

	@XmlElement(name = "speakers")
	Set<String> getSpeakers();

	@Override
	default int compareTo(SessionRepresentation that) {
		if (this.getStartTime().equals(that.getStartTime())) {
			if (getCode().equals(that.getCode())) {
				return this.getName().compareTo(that.getName());
			}

			return this.getCode().compareTo(that.getCode());
		}

		return this.getStartTime().compareTo(that.getStartTime());
	}
}
