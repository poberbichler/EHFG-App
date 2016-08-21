package org.ehfg.app.rest;

import org.ehfg.app.search.ResultType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Collection;

/**
 * @author patrick
 * @since 06.2016
 */
@XmlAccessorType(XmlAccessType.NONE)
public interface SearchResultRepresentation {
    @XmlElement(name = "hasAnyResult")
    boolean hasAnyResult();

	@XmlElement(name = "results")
	Collection<SearchResultDataRepresentation> getResults();

	@XmlElement(name = "tweets")
	Collection<TweetRepresentation> getTweets();

	@XmlAccessorType(XmlAccessType.NONE)
	interface SearchResultDataRepresentation {
		@XmlElement(name = "type")
		ResultType getType();

		@XmlElement(name = "data")
		Collection<SearchResultItemRepresentation> getResults();
	}
}
