package org.ehfg.app.twitter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author patrick
 * @since 06.2015
 */
@Document
public class TwitterUser {
	@Id
	private String id;

	private String fullName;
	private String nickName;
	private String profileImage;

	public TwitterUser() {
		
	}

	public TwitterUser(String id, String fullName, String nickName, String profileImage) {
		this.id = id;
		this.fullName = fullName;
		this.nickName = nickName;
		this.profileImage = profileImage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
