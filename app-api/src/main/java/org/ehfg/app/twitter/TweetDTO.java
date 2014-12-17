package org.ehfg.app.twitter;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 20.03.2014
 */
public final class TweetDTO {
	private final static int MAX_LENGTH = 30;

	public final Long id;
	public final String fullName;
	public final String nickName;
	public final String message;
	public final String profileImage;
	public final Date timestamp;

	public TweetDTO(Long id, String fullName, String nickName, String message, String profileImage, Date timestamp) {
		this.id = id;
		this.fullName = fullName;
		this.nickName = nickName;
		this.message = message;
		this.profileImage = profileImage;
		this.timestamp = timestamp;
	}

	/**
	 * @return the fullname of the person, but shortended, so it can easily be
	 *         viewed on an iphone - damn you steve jobs!
	 */
	public String getFullNameForScreen() {
		int totalLength = fullName.length() + nickName.length();
		
		if (totalLength >= MAX_LENGTH) {
			int maxLengthFullName = fullName.length() - (totalLength - MAX_LENGTH);
			return StringUtils.abbreviate(fullName, maxLengthFullName);
		}

		return fullName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
