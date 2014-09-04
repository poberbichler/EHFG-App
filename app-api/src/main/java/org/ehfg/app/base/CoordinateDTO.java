package org.ehfg.app.base;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * representation of a coordinate
 * 
 * @author patrick
 * @since 02.03.2014
 */
public final class CoordinateDTO {
	@NotNull
	private Double xValue;

	@NotNull
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