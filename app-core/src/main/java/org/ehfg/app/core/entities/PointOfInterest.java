package org.ehfg.app.core.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 13.04.2014
 */
@Entity
public class PointOfInterest {
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String address;

	@NotNull
	private String description;

	@NotNull
	@Embedded
	private Coordinate coordinate;

	public PointOfInterest() {

	}

	public PointOfInterest(Long id, String name, String address, String description, Coordinate coordinate) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
