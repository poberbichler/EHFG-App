package org.ehfg.app.search;

import java.util.Collection;
import java.util.Set;

/**
 *
 *
 * @author patrick
 * @since 07.2016
 */
public interface SearchIndexDataProvider<T> {
	Collection<? extends T> getData();

	Set<ResultType> getResultTypes();
}
