package org.ehfg.app.twitter;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 09.2015
 */
public class TwitterStatisticLine {
	private final String session;
	private final int tweets;

	public TwitterStatisticLine(String session, int tweets) {
		this.session = session;
		this.tweets = tweets;
	}

	public String getSession() {
		return session;
	}

	public int getTweets() {
		return tweets;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
