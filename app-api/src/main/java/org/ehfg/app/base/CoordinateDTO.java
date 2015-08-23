package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.CoordinateRepresentation;

import javax.validation.constraints.NotNull;

/**
 * representation of a coordinate
 * 
 * @author patrick
 * @since 02.03.2014
 */
public final class CoordinateDTO implements CoordinateRepresentation {
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
	public double getLatitude() {
		return getxValue();
	}

	@Override
	public double getLongitude() {
		return getyValue();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
