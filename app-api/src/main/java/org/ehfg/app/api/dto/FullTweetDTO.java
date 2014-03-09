package org.ehfg.app.api.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class FullTweetDTO {
	public final String name;
	public final String screenName;
	public final String profileImageUrl;
	public final String text;

	public FullTweetDTO(String name, String screenName, String profileImageUrl, String text) {
		super();
		this.name = name;
		this.screenName = screenName;
		this.profileImageUrl = profileImageUrl;
		this.text = text;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
