package org.ehfg.app.api.dto.twitter;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 13.03.2014
 */
public class TweetDTO {
	public final Long id;
	public final Long authorId;
	public final Date creationDate;
	public final String message;
	public final String hashtag;

	public TweetDTO(Long id, Long authorId, Date creationDate, String message,
			String hashtag) {
		super();
		this.id = id;
		this.authorId = authorId;
		this.creationDate = creationDate;
		this.message = message;
		this.hashtag = hashtag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
