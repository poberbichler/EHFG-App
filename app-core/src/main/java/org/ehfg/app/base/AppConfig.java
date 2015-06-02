package org.ehfg.app.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * final class for the general configuration of the app
 * 
 * @author patrick
 * @since 06.2015
 */
@Document
public class AppConfig {
	public static final String CONFIG_ID = "42";

	@Id
	private final String id = CONFIG_ID;

	private String hashtag;
	private Integer numberOfTweets;
	
	private String backdoorScript;

	/**
	 * @return a new instance of Appconfig, including some default values
	 */
	static AppConfig withDefaultValues() {
		final AppConfig result = new AppConfig();
		result.setHashtag("#EHFG2015");
		result.setBackdoorScript("console.log('hello world');");
		result.setNumberOfTweets(10);
		return result;
	}

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

	public String getId() {
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
