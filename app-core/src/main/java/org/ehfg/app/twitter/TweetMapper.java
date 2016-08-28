package org.ehfg.app.twitter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
	 *
	 * @return never null
	 */
	static Collection<TweetDTO> map(final Collection<Tweet> source) {
		if (source == null) {
			return Collections.emptyList();
		}

		return source.stream().map(tweet -> mapTweet(tweet, ShowFormattedMesage.YES)).collect(Collectors.toList());
	}

	static TweetDTO mapUnformattedTweet(Tweet tweet) {
		return mapTweet(tweet, ShowFormattedMesage.NO);
	}

	private static TweetDTO mapTweet(Tweet tweet, ShowFormattedMesage showFormattedMesage) {
		final TwitterUser user = tweet.getAuthor();

		final String message = showFormattedMesage == ShowFormattedMesage.YES ?
				tweet.getFormattedMesssage() != null ? tweet.getFormattedMesssage() : tweet.getMessage()
				: tweet.getMessage();

		// TODO: not an ideal solution, creationDate inside Tweet should be ZoneDateTime next year (?)
		final ZonedDateTime utcCreationDate = tweet.getCreationDate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("Europe/Vienna"));
		return new TweetDTO(tweet.getId(), user.getFullName(), user.getNickName(), message, user.getProfileImage(), utcCreationDate.toLocalDateTime(), tweet.isRetweet(), tweet.getRetweetedBy());
	}

	private enum ShowFormattedMesage {
		YES, NO;
	}
}
