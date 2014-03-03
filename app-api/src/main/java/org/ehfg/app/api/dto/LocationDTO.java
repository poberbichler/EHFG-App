package org.ehfg.app.api.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * basic representation of a location
 * 
 * @author patrick
 * @since 02.03.2014
 */
public class LocationDTO {
	private Long id;
	private String name;
	private CoordinateDTO coordinate;

	public LocationDTO(Long id, String name, CoordinateDTO coordinate) {
		super();
		this.id = id;
		this.name = name;
		this.coordinate = coordinate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
