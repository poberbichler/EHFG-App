package org.ehfg.app.api.dto.twitter;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 14.03.2014
 */
public class SaveTweetDTO extends TweetDTO {
	public final String fullName;
	public final String nickName;
	public final String profileImage;

	public SaveTweetDTO(Long id, Long authorId, Date creationDate,
			String message, String hashtag, String fullName, String nickName,
			String profileImage) {
		super(id, authorId, creationDate, message, hashtag);

		this.fullName = fullName;
		this.nickName = nickName;
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
