package org.ehfg.app.base;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * representation of a coordinate
 * 
 * @author patrick
 * @since 02.03.2014
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class CoordinateDTO {
	@NotNull
	@XmlElement(name = "latitude")
	private Double xValue;

	@NotNull
	@XmlElement(name = "longitude")
	private Double yValue;

	public CoordinateDTO() {

	}

	public CoordinateDTO(Double xValue, Double yValue) {
		super();
		this.xValue = xValue;
		this.yValue = yValue;
	}

	public Double getxValue() {
		return xValue;
	}

	public void setxValue(Double xValue) {
		this.xValue = xValue;
	}

	public Double getyValue() {
		return yValue;
	}

	public void setyValue(Double yValue) {
		this.yValue = yValue;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
