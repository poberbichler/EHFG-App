package org.ehfg.app.api.dto;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 20.03.2014
 */
public class TweetDTO {
	public final String fullName;
	public final String nickName;
	public final String message;
	public final String profileImage;
	public final Date timestamp;

	public TweetDTO(String fullName, String nickName, String message, String profileImage, Date timestamp) {
		super();
		this.fullName = fullName;
		this.nickName = nickName;
		this.message = message;
		this.profileImage = profileImage;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
