package org.ehfg.app.twitter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.social.twitter.api.UrlEntity;


/**
 * @author patrick
 * @since 12.04.2014
 */
class TweetFactory {
	public final static Tweet create(final org.springframework.social.twitter.api.Tweet source, final String hashtag, final TwitterUser user) {
		final Tweet result = new Tweet();
		result.setAuthor(user);
		result.setCreationDate(source.getCreatedAt());
		result.setHashtag(hashtag);
		result.setId(source.getId());
		result.setMessage(source.getUnmodifiedText());
		
		final Map<String, UrlEntity> urlMap = createUrlMap(source.getEntities().getUrls());

		final StringBuilder builder = new StringBuilder();
		for (String split : source.getText().split(" ")) {
			if (split.startsWith("#")) {
				split = split.replace("#", "<span class=\"hashtag\">#").concat("</span>");
			}

			else if (urlMap.containsKey(split)) {
				UrlEntity urlEntity = urlMap.get(split);
				split = String.format("<a href=\"#\" onclick=\"window.open('%s', '_blank')\">%s</a>", urlEntity.getExpandedUrl(), urlEntity.getDisplayUrl());
			}

			builder.append(split).append(" ");
		}

		result.setFormattedMesssage(builder.toString());
		return result;
	}

	/**
	 * Creates a map for the given array of {@link UrlEntity}.<br>
	 * The key of the map will be the <strong>displaytext</strong> of the url,
	 * where the value will be the full {@link URLEntity}
	 */
	private static Map<String, UrlEntity> createUrlMap(final Collection<UrlEntity> urlEntities) {
		if (urlEntities == null || urlEntities.isEmpty()) {
			return Collections.emptyMap();
		}

		final Map<String, UrlEntity> result = new HashMap<>();
		for (final UrlEntity urlEntity : urlEntities) {
			result.put(urlEntity.getUrl(), urlEntity);
		}

		return result;
	}
}
