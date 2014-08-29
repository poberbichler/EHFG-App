package org.ehfg.app.base;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * class for the general configuration of the app
 * 
 * @author patrick
 * @since 14.03.2014
 */
@Entity
public class AppConfig {
	public static final Long CONFIG_ID = 42L;

	@Id
	private final Long id = CONFIG_ID;

	private String hashtag;
	private Integer numberOfTweets;

	public Long getId() {
		return id;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public Integer getNumberOfTweets() {
		return numberOfTweets;
	}

	public void setNumberOfTweets(Integer numberOfTweets) {
		this.numberOfTweets = numberOfTweets;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
