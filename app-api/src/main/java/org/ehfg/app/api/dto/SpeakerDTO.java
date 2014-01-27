package org.ehfg.app.api.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * basic represenation of a speaker
 * 
 * @author patrick
 * @since 25.01.2014
 */
public class SpeakerDTO {
	private Long id;
	private String firstName;
	private String lastName;

	public SpeakerDTO(Long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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
