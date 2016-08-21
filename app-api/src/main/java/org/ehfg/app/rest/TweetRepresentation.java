package org.ehfg.app.rest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author patrick
 * @since 04.2015
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface TweetRepresentation {
	@XmlElement(name = "id")
	String getId();

	@XmlElement(name = "fullName")
	String getFullName();

	@XmlElement(name = "nickName")
	String getNickName();

	@XmlElement(name = "message")
	String getMessage();

	@XmlElement(name = "profileImage")
	String getProfileImage();

	@XmlElement(name = "timestamp")
	LocalDateTime getTimestamp();

    @XmlElement(name = "retweet")
    boolean isRetweet();

    @XmlElement(name = "retweetedBy")
    Collection<String> getRetweetedBy();
}
