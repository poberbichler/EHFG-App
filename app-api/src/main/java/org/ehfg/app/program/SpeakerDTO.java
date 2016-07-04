package org.ehfg.app.program;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.SpeakerRepresentation;
import org.ehfg.app.search.Indexable;
import org.ehfg.app.search.ResultType;

/**
 * basic represenation of a speaker
 * 
 * @author patrick
 * @since 01.2014
 */
public final class SpeakerDTO implements Comparable<SpeakerDTO>, SpeakerRepresentation, Indexable {
	private String id;
	private String firstName;
	private String lastName;
	private String description;
	private String imageUrl;

	private SpeakerDTO(Builder builder) {
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.description = builder.description;
		this.imageUrl = builder.imageUrl;
	}
	
	@Override
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String getFullName() {
		return String.format("%s %s", lastName, firstName);
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDisplayName() {
		return getFullName();
	}

	@Override
	public ResultType getType() {
		return ResultType.SPEAKER;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
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

	@Override
	public int compareTo(SpeakerDTO that) {
		if (StringUtils.isEmpty(this.firstName) && StringUtils.isEmpty(that.firstName)) {
			return this.lastName.compareTo(that.lastName);
		}

		else if (StringUtils.isEmpty(this.firstName)) {
			return 1;
		}
		
		else if (StringUtils.isEmpty(that.firstName)) {
			return -1;
		}
		
		if (StringUtils.isEmpty(this.firstName) && StringUtils.isEmpty(that.firstName)) {
			return this.firstName.compareTo(that.firstName);
		}
		
		else if (StringUtils.isEmpty(this.lastName)) {
			return 1;
		}
		
		else if (StringUtils.isEmpty(that.lastName)) {
			return -1;
		}
		
		int result = this.lastName.compareTo(that.lastName);
		if (result != 0) {
			return result;
		}
		
		return this.firstName.compareTo(that.firstName);
	}

	/**
	 * internal builder class
	 * 
	 * @author patrick
	 * @since 29.06.2014
	 */
	public static class Builder {
		private String id;
		private String firstName;
		private String lastName;
		private String description;
		private String imageUrl;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder imageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public SpeakerDTO build() {
			return new SpeakerDTO(this);
		}
	}

}
