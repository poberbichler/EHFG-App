package org.ehfg.app.base;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 13.04.2014
 */
public class PointOfInterestDTO {
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	private String address;
	private String contact;
	private String website;

	@Valid
	@NotNull
	private CoordinateDTO coordinate;

	public PointOfInterestDTO() {
		this.coordinate = new CoordinateDTO();
	}

	public PointOfInterestDTO(Long id, String name, String address, String description, String contact, String website,
			CoordinateDTO coordinate) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.contact = contact;
		this.website = website;
		this.coordinate = coordinate;
	}

	public PointOfInterestDTO(Long id, String name, String address, String description, String contact, String website, Double xCoordinate,
			Double yCoordinate) {
		this(id, name, address, description, contact, website, new CoordinateDTO(xCoordinate, yCoordinate));
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

	public CoordinateDTO getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(CoordinateDTO coordinate) {
		this.coordinate = coordinate;
	}

	public Double getxValue() {
		return coordinate.getxValue();
	}

	public Double getyValue() {
		return coordinate.getyValue();
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
