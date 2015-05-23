package org.ehfg.app.program;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.SessionRepresentation;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * basic representation of a session
 * 
 * @author patrick
 * @since 03.2014
 */
public final class SessionDTO implements Comparable<SessionDTO>, SessionRepresentation {
	private String id;
	private String name;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String sessionCode;

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
		this.sessionCode = builder.sessionCode;
	}

	public LocalDate getDay() {
		return startTime.toLocalDate();
	}

	@Override
	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	@Override
	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String getId() {
		return id;
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
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Set<String> getSpeakers() {
		if (speakers == null) {
			return Collections.emptySet();
		}

		return speakers;
	}

	@Override
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
		if (this.startTime.equals(that.startTime)) {
			if (this.sessionCode.equals(that.sessionCode)) {
				return this.name.compareTo(that.name);
			}
			
			return this.sessionCode.compareTo(that.sessionCode);
		}
		
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
		private LocalDateTime startTime;
		private LocalDateTime endTime;
		private String sessionCode;

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

		public Builder startTime(LocalDateTime startTime) {
			this.startTime = startTime;
			return this;
		}

		public Builder endTime(LocalDateTime endTime) {
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

		public Builder sessionCode(String sessionCode) {
			this.sessionCode = sessionCode;
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
		 * @return new {@link LocalDateTime} with the given value, or current timestamp in case of an error
		 */
		private LocalDateTime convertToDate(String timeString) {
			try {
				return LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
			}

			catch (Exception e) {
				return LocalDateTime.now();
			}
		}
	}
}
