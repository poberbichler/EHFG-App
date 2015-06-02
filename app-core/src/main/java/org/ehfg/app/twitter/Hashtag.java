package org.ehfg.app.twitter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Immutable domainclass for a single hashtag. The static constructor ensures that the internal hashtag will always
 * include a {@code #} in front of the value
 *
 * @author patrick
 * @since 05.2015
 */
public class Hashtag {
	@NotNull
	private final String hashtag;

	/**
	 * @return a new instance of Hashtag with the given hashtag
	 */
	public static Hashtag valueOf(String hashtag) {
		if (hashtag.startsWith("#")) {
			return new Hashtag(hashtag);
		}

		return new Hashtag("#".concat(hashtag));
	}

	private Hashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public String getHashtagWithoutHash() {
		return hashtag.substring(1);
	}

	public String getHashtagWithHash() {
		return hashtag;
	}

	public String getHashtagForDb() {
		return getHashtagWithHash().toLowerCase();
	}

	@Override
	public String toString() {
		return Objects.toString(hashtag);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(hashtag);
	}

	@Override
	public boolean equals(Object thatObject) {
		if (this == thatObject) {
			return true;
		}

		if (!(thatObject instanceof Hashtag)) {
			return false;
		}

		final Hashtag that = (Hashtag) thatObject;
		return Objects.equals(this.hashtag, that.hashtag);
	}
}
