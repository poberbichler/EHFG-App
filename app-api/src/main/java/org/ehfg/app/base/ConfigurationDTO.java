package org.ehfg.app.base;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 14.03.2013
 */
public class ConfigurationDTO {
	@NotNull
	private String hashtag;

	@NotNull
	@Max(10)
	private Integer numberOfTweets;
	
	@SuppressWarnings("unused")
	private ConfigurationDTO() {
		// default ctor, needed by various frameworks
	}

	public ConfigurationDTO(String hashtag, Integer numberOfTweets) {
		this.hashtag = hashtag;
		this.numberOfTweets = numberOfTweets;
	}

	public Integer getNumberOfTweets() {
		return numberOfTweets;
	}

	public void setNumberOfTweets(Integer numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
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
