package org.ehfg.app.rest;

import org.ehfg.app.search.ResultType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author patrick
 * @since 06.2016
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface SearchResultItemRepresentation {
	@XmlElement(name = "id")
	String getId();

	@XmlElement(name = "type")
	ResultType getType();

	@XmlElement(name = "description")
	String getDescription();
}
