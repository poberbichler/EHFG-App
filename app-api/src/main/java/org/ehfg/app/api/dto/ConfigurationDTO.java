package org.ehfg.app.api.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 14.03.2013
 */
public class ConfigurationDTO {
	@NotNull
	private String hashtag;

	public ConfigurationDTO(String hashtag) {
		this.hashtag = hashtag;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
