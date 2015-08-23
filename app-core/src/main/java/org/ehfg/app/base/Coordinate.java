package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

/**
 * @author patrick
 * @since 13.04.2014
 */
class Coordinate {
	@NotNull
	private Double xValue;

	@NotNull
	private Double yValue;

	public Coordinate() {

	}

	public Coordinate(Double xValue, Double yValue) {
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
