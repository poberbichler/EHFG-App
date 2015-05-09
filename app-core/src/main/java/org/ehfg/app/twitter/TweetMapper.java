package org.ehfg.app.twitter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 04.2014
 */
final class TweetMapper {
	private TweetMapper() {
		// do not allow instantiation
	}

	/**
	 * Takes the given collection of {@link Tweet} and creates a collection of {@link TweetDTO}
	 * @return never null
	 */
	final Collection<TweetDTO> map(final Collection<Tweet> source) {
		if (source == null) {
			return Collections.emptyList();
		}

		return source.stream().map(tweet -> {
			final TwitterUser user = tweet.getAuthor();

			final String message = tweet.getFormattedMesssage() != null ? tweet.getFormattedMesssage() : tweet.getMessage();
			return new TweetDTO(tweet.getId(), user.getFullName(), user.getNickName(), message, user.getProfileImage(), tweet.getCreationDate());
		}).collect(Collectors.toList());
	}
}
