package org.ehfg.app.twitter;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.TweetRepresentation;

/**
 * @author patrick
 * @since 20.03.2014
 */
public final class TweetDTO implements TweetRepresentation {
	private final Long id;
	private final String fullName;
	private final String nickName;
	private final String message;
	private final String profileImage;
	private final Date timestamp;

	public TweetDTO(Long id, String fullName, String nickName, String message, String profileImage, Date timestamp) {
		this.id = id;
		this.fullName = fullName;
		this.nickName = nickName;
		this.message = message;
		this.profileImage = profileImage;
		this.timestamp = timestamp;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public String getNickName() {
		return nickName;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getProfileImage() {
		return profileImage;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
