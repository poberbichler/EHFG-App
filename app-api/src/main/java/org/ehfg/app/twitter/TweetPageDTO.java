package org.ehfg.app.twitter;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author patrick
 * @since 02.04.2014
 */
public class TweetPageDTO {
	public final List<TweetDTO> tweets;
	public final Integer currentPage;
	public final Boolean morePages;

	public TweetPageDTO(List<TweetDTO> tweets, Integer currentPage, Boolean morePages) {
		this.tweets = tweets;
		this.currentPage = currentPage;
		this.morePages = morePages;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
