package org.ehfg.app.external.twitter;

import org.ehfg.app.core.entities.Tweet;
import org.ehfg.app.core.entities.TwitterUser;

import twitter4j.Status;

/**
 * @author patrick
 * @since 12.04.2014
 */
public class TweetFactory {
	public final static Tweet create(final Status status, final String hashtag, TwitterUser user) {
		final Tweet result = new Tweet();
		result.setAuthor(user);
		result.setCreationDate(status.getCreatedAt());
		result.setHashtag(hashtag);
		result.setId(status.getId());
		result.setMessage(status.getText());
		
		final StringBuilder builder = new StringBuilder();
		for (String split : status.getText().split(" ")) {
			if (split.startsWith("#")) {
				split = split.replace("#", "<span class=\"hashtag\">#").concat("</span>");
			}
			
			builder.append(split).append(" ");
		}
		
		result.setFormattedMesssage(builder.toString());
		return result;
	}
}
