package org.ehfg.app.twitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author patrick
 * @since 02.04.2014
 */
final class TweetMapper {
	private TweetMapper() {
		// do not allow instantiation
	}

	public static final List<TweetDTO> map(final Collection<Tweet> source) {
		if (source == null) {
			return Collections.emptyList();
		}

		final List<TweetDTO> result = new ArrayList<>(source.size());

		for (final Tweet tweet : source) {
			final TwitterUser user = tweet.getAuthor();

			final String message = tweet.getFormattedMesssage() != null ? tweet.getFormattedMesssage() : tweet.getMessage();
			result.add(new TweetDTO(tweet.getId(), user.getFullName(), user.getNickName(), message, user.getProfileImage(), tweet.getCreationDate()));
		}

		return result;
	}
}
