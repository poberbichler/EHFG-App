package org.ehfg.app.twitter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author patrick
 * @since 06.2015
 */
@Document
public class Tweet {
	@Id
	private String id;

	private String message;
	private LocalDateTime creationDate;
	private String hashtag;
	private String formattedMesssage;

	@DBRef
	private TwitterUser author;

	public Tweet() {

	}

	public Tweet(String id, String message, LocalDateTime creationDate, String hashtag, String formattedMesssage, TwitterUser author) {
		this.id = id;
		this.message = message;
		this.creationDate = creationDate;
		this.hashtag = hashtag;
		this.formattedMesssage = formattedMesssage;
		this.author = author;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TwitterUser getAuthor() {
		return author;
	}

	public void setAuthor(TwitterUser author) {
		this.author = author;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public String getFormattedMesssage() {
		return formattedMesssage;
	}

	public void setFormattedMesssage(String formattedMesssage) {
		this.formattedMesssage = formattedMesssage;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
