package org.ehfg.app.core.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ehfg.app.api.dto.TweetDTO;
import org.ehfg.app.core.entities.Tweet;
import org.ehfg.app.core.entities.TwitterUser;

/**
 * @author patrick
 * @since 02.04.2014
 */
public class TweetMapper {
	private TweetMapper() {
	}

	public static final List<TweetDTO> map(final List<Tweet> source) {
		if (source == null) {
			return Collections.emptyList();
		}

		final List<TweetDTO> result = new ArrayList<>(source.size());

		for (final Tweet tweet : source) {
			final TwitterUser user = tweet.getAuthor();

			String message = tweet.getFormattedMesssage() != null ? tweet.getFormattedMesssage() : tweet.getMessage();
			result.add(new TweetDTO(user.getFullName(), user.getNickName(), message, user.getProfileImage(), tweet.getCreationDate()));
		}

		return result;
	}
}
