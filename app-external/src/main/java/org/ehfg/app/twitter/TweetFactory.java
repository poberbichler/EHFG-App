package org.ehfg.app.twitter;


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
		
//		final Map<String, URLEntity> urlMap = createUrlMap(status.getURLEntities());

//		final StringBuilder builder = new StringBuilder();
//		for (String split : source.getText().split(" ")) {
//			if (split.startsWith("#")) {
//				split = split.replace("#", "<span class=\"hashtag\">#").concat("</span>");
//			}
//
//			else if (urlMap.containsKey(split)) {
//				URLEntity urlEntity = urlMap.get(split);
//				split = String.format("<a href=\"#\" onclick=\"window.open('%s', '_blank')\">%s</a>", urlEntity.getExpandedURL(), urlEntity.getDisplayURL());
//			}
//
//			builder.append(split).append(" ");
//		}

		result.setFormattedMesssage(source.getText());
//		result.setFormattedMesssage(builder.toString());
		return result;
	}
//
//	/**
//	 * Creates a map for the given array of {@link URLEntity}.<br>
//	 * The key of the map will be the <strong>displaytext</strong> of the url,
//	 * where the value will be the full {@link URLEntity}
//	 */
//	private static Map<String, URLEntity> createUrlMap(final URLEntity[] urlEntities) {
//		if (urlEntities == null || urlEntities.length == 0) {
//			return Collections.emptyMap();
//		}
//
//		final Map<String, URLEntity> result = new HashMap<>();
//		for (final URLEntity urlEntity : urlEntities) {
//			result.put(urlEntity.getURL(), urlEntity);
//		}
//
//		return result;
//	}
}
