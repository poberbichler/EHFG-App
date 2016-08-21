package org.ehfg.app.twitter;

import org.springframework.social.twitter.api.UrlEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 04.2015
 */
final class TweetFactory {
	private TweetFactory() {
		// do not allow instantiation
	}

	static Tweet create(final org.springframework.social.twitter.api.Tweet source, final String hashtag, final TwitterUser user) {
		final Tweet result = new Tweet();
		result.setAuthor(user);
		result.setCreationDate(LocalDateTime.ofInstant(source.getCreatedAt().toInstant(), ZoneId.systemDefault()));
		result.setHashtag(hashtag);
		result.setId(Long.toString(source.getId()));
		result.setMessage(source.getUnmodifiedText());
        result.setRetweet(source.isRetweet());
		
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
	 * The key of the map will be the <b>displaytext</b> of the url,
	 * where the value will be the full {@link UrlEntity}
	 */
	private static Map<String, UrlEntity> createUrlMap(final Collection<UrlEntity> urlEntities) {
		if (urlEntities == null || urlEntities.isEmpty()) {
			return Collections.emptyMap();
		}
		
		return urlEntities.stream().collect(Collectors.toMap(UrlEntity::getUrl, Function.identity()));
	}
}
