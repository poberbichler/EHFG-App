package org.ehfg.app.search;

import java.util.Collection;
import java.util.Set;

/**
 * Generic interface for every class which returns data needed to be indexed for the full text search.<br>
 * Every implementation of this class will be validated by ValidateSearchIndexDataProvider
 *
 * @author patrick
 * @since 07.2016
 */
public interface SearchIndexDataProvider<T> {
	/**
	 * @return the concrete data provded for the search index
	 */
	Collection<? extends T> getData();

	/**
	 * @return the ResultTypes as provided by the service
	 */
	Set<ResultType> getResultTypes();
}
