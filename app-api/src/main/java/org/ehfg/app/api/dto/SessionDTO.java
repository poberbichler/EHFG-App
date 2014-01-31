package org.ehfg.app.api.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SessionDTO {
	private Long id;
	private Long speakerId;
	private String name;
	private String description;
	private Date startTime;
	private Date endTime;

	public SessionDTO(Long id, Long speakerId, String name, String description,
			Date startTime, Date endTime) {
		super();
		this.id = id;
		this.speakerId = speakerId;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * another consturctor, but uses strings for the dates
	 * format is: <strong>dd.MM.yyyy hh:mm</strong>
	 */
	public SessionDTO(Long id, Long speakerId, String name, String description,
			String startString, String endString) {
		this.id = id;
		this.speakerId = speakerId;
		this.name = name;
		this.description = description;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
			this.startTime = sdf.parse(startString);
			this.endTime = sdf.parse(endString);
		}

		catch (Exception e) {
			this.startTime = new Date();
			this.endTime = new Date();
		}
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
