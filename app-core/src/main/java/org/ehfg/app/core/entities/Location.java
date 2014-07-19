package org.ehfg.app.core.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 19.07.2014
 */
@Entity
public class Location {
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@Valid
	@NotNull
	@Embedded
	private Coordinate coordinate;
	
	public Location() {
		
	}

	public Location(Long id, String name, Coordinate coordinate) {
		this.id = id;
		this.name = name;
		this.coordinate = coordinate;
	}
	
	public Location(Long id, String name, Double xValue, Double yValue) {
		this(id, name, new Coordinate(xValue, yValue));
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

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
