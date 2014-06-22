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
		// default ctor is needed for jaxrs
	}

	private SessionDTO(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;
		this.location = builder.location;
		this.speakers = builder.speakers;
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
	
	/**
	 * builder to map the given values
	 * 
	 * @author patrick
	 * @since 21.06.2014
	 */
	public static class Builder {
		private String id;
		private String name;
		private String description;
		private Date startTime;
		private Date endTime;

		private String location;
		private Set<String> speakers;

		public SessionDTO build() {
			return new SessionDTO(this);
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder startTime(Date startTime) {
			this.startTime = startTime;
			return this;
		}

		public Builder endTime(Date endTime) {
			this.endTime = endTime;
			return this;
		}

		public Builder location(String location) {
			this.location = location;
			return this;
		}

		public Builder speakers(Set<String> speakers) {
			this.speakers = speakers;
			return this;
		}
		
		public Builder speakers(String... speakers) {
			this.speakers = new HashSet<>(Arrays.asList(speakers));
			return this;
		}

		public Builder startTime(String startTime) {
			this.startTime = convertToDate(startTime);
			return this;
		}
		
		public Builder endTime(String endTime) {
			this.endTime = convertToDate(endTime);
			return this;
		}
		
		/**
		 * @param timeString to be converted
		 * @return new {@link Date} with the given value, or current timestamp in case of an error
		 */
		private Date convertToDate(String timeString) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
				return sdf.parse(timeString);
			}

			catch (Exception e) {
				return new Date();
			}
		}
	}
}
