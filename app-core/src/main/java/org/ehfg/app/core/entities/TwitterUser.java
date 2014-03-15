package org.ehfg.app.core.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 13.03.2014
 */
@Entity
public class TwitterUser {
	@Id
	private Long id;

	private String fullName;
	private String nickName;
	private String profileImage;

	@OneToMany(mappedBy = "author")
	private final List<Tweet> tweets = new ArrayList<Tweet>();

	public TwitterUser() {
		
	}
	

	public TwitterUser(Long id, String fullName, String nickName,
			String profileImage) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.nickName = nickName;
		this.profileImage = profileImage;
	}

	public void addTweet(Tweet tweet) {
		tweets.add(tweet);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public List<Tweet> getTweets() {
		return Collections.unmodifiableList(tweets);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
