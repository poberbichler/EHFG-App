package org.ehfg.app.rest;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface TweetRepresentation {
	@XmlElement(name = "id")
	Long getId();

	@XmlElement(name = "fullName")
	String getFullName();

	@XmlElement(name = "nickName")
	String getNickName();

	@XmlElement(name = "message")
	String getMessage();

	@XmlElement(name = "profileImage")
	String getProfileImage();

	@XmlElement(name = "timestamp")
	Date getTimestamp();
}
