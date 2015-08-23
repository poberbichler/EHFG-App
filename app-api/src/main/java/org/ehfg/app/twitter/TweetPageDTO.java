package org.ehfg.app.twitter;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.AbstractPageableDTO;

import java.util.Collection;

/**
 * @author patrick
 * @since 12.2014
 */
public final class TweetPageDTO extends AbstractPageableDTO<TweetDTO> {
	private final String currentHashtag;
	
	public TweetPageDTO(Collection<TweetDTO> data, int currentPage, int maxPages, String currentHashtag) {
		super(data, currentPage, maxPages);
		this.currentHashtag = currentHashtag;
	}
	
	public String getCurrentHashtag() {
		return currentHashtag;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
