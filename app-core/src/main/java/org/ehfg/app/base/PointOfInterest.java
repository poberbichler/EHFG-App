package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author patrick
 * @since 06.2015
 */
@Document
final class PointOfInterest {
	@Id
	private String id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	private String address;
	private String contact;

	private String website;

	@Valid
	@NotNull
	private Coordinate coordinate;

	public PointOfInterest() {

	}

	public PointOfInterest(String id, String name, String address, String description, String contact, String website, Coordinate coordinate) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.contact = contact;
		this.website = website;
		this.coordinate = coordinate;
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
