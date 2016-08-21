package org.ehfg.app.twitter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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

    private boolean retweet = false;
    private List<String> retweetedBy = new LinkedList<>();

	@DBRef
	private TwitterUser author;

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

    public List<String> getRetweetedBy() {
        return retweetedBy;
    }

    public void addRetweet(String userName) {
        retweetedBy.add(userName);
    }

    public boolean isRetweet() {
        return retweet;
    }

    public void setRetweet(boolean retweet) {
        this.retweet = retweet;
    }

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
