package org.ehfg.app.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface PointOfInterestRepresentation {
	@XmlElement(name = "id")
	Long getId();

	@XmlElement(name = "name")
	String getName();

	@XmlElement(name = "description")
	String getDescription();

	@XmlElement(name = "address")
	String getAddress();

	@XmlElement(name = "contact")
	String getContact();

	@XmlElement(name = "website")
	String getWebsite();

	@XmlElement(name = "coordinate")
	CoordinateRepresentation getCoordinate();
}
