package org.ehfg.app.api.dto;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * basic representation of a session
 * TODO: refactor to builder pattern
 * 
 * @author patrick
 * @since 02.03.2014
 */
public class SessionDTO {
	private String id;
	private String name;
	private String description;
	private Date startTime;
	private Date endTime;

	private String location;
	private Set<String> speakers;

	public SessionDTO() {

	}

	public SessionDTO(String id, String name, String description, Date startTime, Date endTime, String locationId, Set<String> speakers) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.startTime = startTime;
		this.endTime = endTime;
		this.speakers = speakers;
	}

	/**
	 * another constructor, but uses strings for the dates format is:
	 * <strong>dd.MM.yyyy hh:mm</strong>
	 * 
	 * should only be used for testing...
	 */
	public SessionDTO(String id, String name, String description, String startString, String endString, String location, String... speakers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.speakers = new HashSet<>(Arrays.asList(speakers));

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getSpeakers() {
		if (speakers == null) {
			return Collections.emptySet();
		}
		
		return speakers;
	}

	public String getLocationId() {
		return location;
	}

	public void setLocationId(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
