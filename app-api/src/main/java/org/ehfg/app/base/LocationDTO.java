package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.LocationRepresentation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * basic representation of a location
 * 
 * @author patrick
 * @since 02.03.2014
 */
public final class LocationDTO implements LocationRepresentation {
	private String id;
	@NotNull
	private String name;
	
	@Valid
	@NotNull
	private CoordinateDTO coordinate;
	
	public LocationDTO() {
		coordinate = new CoordinateDTO();
	}

	public LocationDTO(String id, String name, CoordinateDTO coordinate) {
		super();
		this.id = id;
		this.name = name;
		this.coordinate = coordinate;
	}
	
	public LocationDTO(String id, String name, Double xValue, Double yValue) {
		this(id, name, new CoordinateDTO(xValue, yValue));
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public CoordinateDTO getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(CoordinateDTO coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
