package org.ehfg.app.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface SpeakerRepresentation {
	@XmlElement(name = "id")
	String getId();

	@XmlElement(name = "firstName")
	String getFirstName();

	@XmlElement(name = "lastName")
	String getLastName();

	@XmlElement(name = "description")
	String getDescription();

	@XmlElement(name = "imageUrl")
	String getImageUrl();

	@XmlElement(name = "fullName")
	String getFullName();
}
