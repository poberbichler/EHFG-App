package org.ehfg.app.api.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SessionDTO {
	private Long id;
	private Long speakerId;
	private String name;
	private String description;

	public SessionDTO(Long id, Long speakerId, String name, String description) {
		super();
		this.id = id;
		this.speakerId = speakerId;
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSpeakerId() {
		return speakerId;
	}

	public void setSpeakerId(Long speakerId) {
		this.speakerId = speakerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
