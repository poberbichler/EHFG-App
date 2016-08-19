package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.MapCategoryRepresentation;
import org.ehfg.app.rest.PointOfInterestRepresentation;
import org.ehfg.app.search.Indexable;
import org.ehfg.app.search.ResultType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author patrick
 * @since 13.04.2014
 */
public final class PointOfInterestDTO implements PointOfInterestRepresentation, Indexable {
	private String id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	private String address;
	private String contact;
	private String website;

	private MapCategoryRepresentation category;

	private String categoryName;

	@Valid
	@NotNull
	private CoordinateDTO coordinate;

	public PointOfInterestDTO() {
		this.coordinate = new CoordinateDTO();
	}

	public PointOfInterestDTO(String id, String name, String address, String description, String contact, String website,
			CoordinateDTO coordinate, MapCategoryRepresentation category) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.description = description;
		this.contact = contact;
		this.website = website;
		this.coordinate = coordinate;
		this.category = category;
	}

	public PointOfInterestDTO(String id, String name, String address, String description, String contact, String website, Double xCoordinate,
			Double yCoordinate, MapCategoryRepresentation category) {
		this(id, name, address, description, contact, website, new CoordinateDTO(xCoordinate, yCoordinate), category);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDisplayName() {
		return name;
	}

	@Override
	public ResultType getType() {
		return ResultType.LOCATION;
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
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public CoordinateDTO getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(CoordinateDTO coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Override
	public MapCategoryRepresentation getCategory() {
		return category;
	}

	public void setCategory(MapCategoryRepresentation category) {
		this.category = category;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
