package org.ehfg.app.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * basic represenation of a speaker
 * TODO: refactor to builder pattern
 * 
 * @author patrick
 * @since 25.01.2014
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SpeakerDTO {
	private String id;
	private String firstName;
	private String lastName;
	private String description;
	private String imageUrl;

	public SpeakerDTO() {

	}

	public SpeakerDTO(String id, String firstName, String lastName, String description, String imageUrl) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFullName() {
		return String.format("%s %s", firstName, lastName);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
