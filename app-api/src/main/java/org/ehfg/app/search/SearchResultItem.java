package org.ehfg.app.search;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ehfg.app.rest.SearchResultItemRepresentation;

/**
 * @author patrick
 * @since 06.2016
 */
public class SearchResultItem implements SearchResultItemRepresentation {
	private final String id;
	private final ResultType resultType;
	private final String description;

	public SearchResultItem(String id, ResultType resultType, String description) {
		this.id = id;
		this.resultType = resultType;
		this.description = description;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ResultType getType() {
		return resultType;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
