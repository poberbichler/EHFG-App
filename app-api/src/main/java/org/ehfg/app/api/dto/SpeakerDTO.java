package org.ehfg.app.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * basic represenation of a speaker
 * 
 * @author patrick
 * @since 25.01.2014
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SpeakerDTO implements Comparable<SpeakerDTO> {
	private String id;
	private String firstName;
	private String lastName;
	private String description;
	private String imageUrl;

	public SpeakerDTO() {
		// needed for jaxrs
	}
	
	private SpeakerDTO(Builder builder) {
		this.id = builder.id;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.description = builder.description;
		this.imageUrl = builder.imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFullName() {
		return String.format("%s %s", lastName, firstName);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
