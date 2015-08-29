package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.LocationRepresentation;
import org.ehfg.app.validation.LocationMappingValid;

import javax.validation.constraints.NotNull;

/**
 * basic representation of a location
 * 
 * @author patrick
 * @since 03.2014
 */
@LocationMappingValid
public final class LocationDTO implements LocationRepresentation {
	private String id;
	@NotNull
	private String name;
	
	private CoordinateDTO coordinate;
	private PointOfInterestDTO mappedPointOfInterest;

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

    public LocationDTO(String id, String name, PointOfInterestDTO pointOfInterestDTO) {
        this.id = id;
        this.name = name;
        this.mappedPointOfInterest = pointOfInterestDTO;
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

	@Override
	public String getPointOfInterestId() {
		return mappedPointOfInterest != null ? mappedPointOfInterest.getId() : null;
	}

	public void setCoordinate(CoordinateDTO coordinate) {
		this.coordinate = coordinate;
	}

    public PointOfInterestDTO getMappedPointOfInterest() {
        return mappedPointOfInterest;
    }

    public void setMappedPointOfInterest(PointOfInterestDTO mappedPointOfInterest) {
        this.mappedPointOfInterest = mappedPointOfInterest;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
