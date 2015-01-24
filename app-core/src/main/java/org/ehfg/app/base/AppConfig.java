package org.ehfg.app.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * final class for the general configuration of the app
 * 
 * @author patrick
 * @since 14.03.2014
 */
@Entity
public final class AppConfig {
	public static final Long CONFIG_ID = 42L;

	@Id
	private final Long id = CONFIG_ID;

	private String hashtag;
	private Integer numberOfTweets;
	
	@Column(length = 1000)
	private String backdoorScript;
	
	/**
	 * sets the given hashtag, and adds a {@code #} sign at the beginning, whether there is one or not 
	 */
	public void setAndFixHashtag(String hashtag) {
		if (hashtag.startsWith("#")) {
			this.hashtag = hashtag;
		}
		
		else {
			this.hashtag = "#".concat(hashtag);
		}
	}

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
