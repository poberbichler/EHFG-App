package org.ehfg.app.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author poberbichler
 * @since 08.2016
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface MapCategoryRepresentation {
    @XmlElement(name = "name")
    String getName();

    @XmlElement(name = "cssClass")
    String getCssClass();

    @XmlElement(name = "imageUrl")
    String getImageUrl();
}
