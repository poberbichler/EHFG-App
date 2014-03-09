package org.ehfg.app.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * basic represenation of a speaker
 * 
 * @author patrick
 * @since 25.01.2014
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SpeakerDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String description;
	
	public SpeakerDTO() {
		
	}

	public SpeakerDTO(Long id, String firstName, String lastName,
			String description) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
