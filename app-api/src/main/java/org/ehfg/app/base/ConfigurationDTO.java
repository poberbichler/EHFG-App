package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author patrick
 * @since 03.2013
 */
public class ConfigurationDTO {
	@NotNull
	private String hashtag;

	@NotNull
	@Max(10)
	private Integer numberOfTweets;
	
	@Size(max = 4000)
	private String backdoorScript;
	
	@SuppressWarnings("unused")
	private ConfigurationDTO() {
		// default ctor, needed by various frameworks
	}

	public ConfigurationDTO(String hashtag, Integer numberOfTweets, String backdoorScript) {
		this.hashtag = hashtag;
		this.numberOfTweets = numberOfTweets;
		this.backdoorScript = backdoorScript;
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
	
	public String getBackdoorScript() {
		return backdoorScript;
	}
	
	public void setBackdoorScript(String backdoorScript) {
		this.backdoorScript = backdoorScript;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
