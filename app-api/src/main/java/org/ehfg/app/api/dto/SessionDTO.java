package org.ehfg.app.api.dto;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

/**
 * basic representation of a session
 * 
 * @author patrick
 * @since 02.03.2014
 */
public class SessionDTO implements Comparable<SessionDTO> {
	private String id;
	private String name;
	private String description;
	private DateTime startTime;
	private DateTime endTime;

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

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
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
	
	@Override
	public int compareTo(SessionDTO that) {
		return this.startTime.compareTo(that.startTime);
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
		private DateTime startTime;
		private DateTime endTime;

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

		public Builder startTime(DateTime startTime) {
			this.startTime = startTime;
			return this;
		}

		public Builder endTime(DateTime endTime) {
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
		 * @return new {@link DateTime} with the given value, or current timestamp in case of an error
		 */
		private DateTime convertToDate(String timeString) {
			try {
				return DateTime.parse(timeString, DateTimeFormat.forPattern("dd.MM.yyyy HH:mm")); 
			}

			catch (Exception e) {
				return DateTime.now();
			}
		}
	}
}
