package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author patrick
 * @since 07.2014
 */
@Document
class Location {
	@Id
	private String id;

	@NotNull
	private String name;

	@DBRef
	private PointOfInterest point;

	private Coordinate coordinate;

    public Location() {
        // no arg ctor needed by mongodb
    }

	public Location(String id, String name, PointOfInterest point) {
		this.id = id;
		this.name = name;
		this.point = point;
	}

	public Location(String id, String name, Coordinate coordinate) {
		this.id = id;
		this.name = name;
		this.coordinate = coordinate;
	}

	public Location(String id, String name, Double xValue, Double yValue) {
		this(id, name, new Coordinate(xValue, yValue));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public PointOfInterest getPoint() {
		return point;
	}

	public void setPoint(PointOfInterest point) {
		this.point = point;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
