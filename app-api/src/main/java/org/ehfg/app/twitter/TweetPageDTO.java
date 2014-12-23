package org.ehfg.app.twitter;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.AbstractPageableDTO;

/**
 * @author patrick
 * @since 12.2014
 */
public final class TweetPageDTO extends AbstractPageableDTO<TweetDTO> {
	public TweetPageDTO(Collection<TweetDTO> data, int currentPage, int maxPages) {
		super(data, currentPage, maxPages);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
