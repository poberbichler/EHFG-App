package org.ehfg.app.api.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * representation of a coordinate
 * 
 * @author patrick
 * @since 02.03.2014
 */
public class CoordinateDTO {
	public final Double yCoordinate;
	public final Double xCoordinate;

	public CoordinateDTO(Double yCoordinate, Double xCoordinate) {
		super();
		this.yCoordinate = yCoordinate;
		this.xCoordinate = xCoordinate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
