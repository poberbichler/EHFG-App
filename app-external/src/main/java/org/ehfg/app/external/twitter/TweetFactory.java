package org.ehfg.app.external.twitter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.ehfg.app.core.entities.Tweet;
import org.ehfg.app.core.entities.TwitterUser;

import twitter4j.Status;
import twitter4j.URLEntity;

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

		final Map<String, URLEntity> urlMap = createUrlMap(status.getURLEntities());

		final StringBuilder builder = new StringBuilder();
		for (String split : status.getText().split(" ")) {
			if (split.startsWith("#")) {
				split = split.replace("#", "<span class=\"hashtag\">#").concat("</span>");
			}

			else if (urlMap.containsKey(split)) {
				URLEntity urlEntity = urlMap.get(split);
				split = String.format("<a href=\"%s\" target=\"_blank\">%s</a>", urlEntity.getExpandedURL(), urlEntity.getDisplayURL());
			}

			builder.append(split).append(" ");
		}

		result.setFormattedMesssage(builder.toString());
		return result;
	}

	/**
	 * Creates a map for the given array of {@link URLEntity}.<br>
	 * The key of the map will be the <strong>displaytext</strong> of the url,
	 * where the value will be the full {@link URLEntity}
	 */
	private static Map<String, URLEntity> createUrlMap(final URLEntity[] urlEntities) {
		if (urlEntities == null || urlEntities.length == 0) {
			return Collections.emptyMap();
		}

		final Map<String, URLEntity> result = new HashMap<>();
		for (final URLEntity urlEntity : urlEntities) {
			result.put(urlEntity.getURL(), urlEntity);
		}

		return result;
	}
}
