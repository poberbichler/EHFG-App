package org.ehfg.app.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface CoordinateRepresentation {
	@XmlElement(name = "latitude")
	double getLatitude();

	@XmlElement(name = "longitude")
	double getLongitude();
}
