package org.ehfg.app.rest;

import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlRootElement(name = "day")
@XmlAccessorType(XmlAccessType.NONE)
public interface ConferenceDayRepresentation {
	@XmlElement(name = "description")
	String getDescription();

	@XmlElement(name = "sessions")
	Collection<SessionRepresentation> getSessions();
}
