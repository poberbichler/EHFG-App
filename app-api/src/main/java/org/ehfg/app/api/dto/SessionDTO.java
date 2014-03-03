package org.ehfg.app.api.dto;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * basic representation of a session
 * 
 * @author patrick
 * @since 02.03.2014
 */
public class SessionDTO {
	private Long id;
	private String name;
	private String description;
	private Date startTime;
	private Date endTime;

	private Long locationId;
	private Set<Long> speakers;
	
	public SessionDTO() {
		
	}

	public SessionDTO(Long id, String name, String description, Date startTime,
			Date endTime, Long locationId, Set<Long> speakers) {
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
	 * @deprecated use only for testing
	 */
	public SessionDTO(Long id, String name, String description,
			String startString, String endString, Long locationId, Long... speakers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.locationId = locationId;
		this.speakers = new HashSet<Long>(Arrays.asList(speakers));

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

	public Set<Long> getSpeakers() {
		return speakers;
	}

	public void setSpeakers(Set<Long> speakers) {
		this.speakers = speakers;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
