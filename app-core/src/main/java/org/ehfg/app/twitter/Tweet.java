package org.ehfg.app.twitter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 13.03.2014
 */
@Entity
final class Tweet {
	@Id
	private Long id;

	private String message;
	private Date creationDate;
	private String hashtag;
	private String formattedMesssage;

	@ManyToOne
	private TwitterUser author;

	public Tweet() {

	}

	public Tweet(Long id, String message, Date creationDate, String hashtag, String formattedMesssage, TwitterUser author) {
		super();
		this.id = id;
		this.message = message;
		this.creationDate = creationDate;
		this.hashtag = hashtag;
		this.formattedMesssage = formattedMesssage;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
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
