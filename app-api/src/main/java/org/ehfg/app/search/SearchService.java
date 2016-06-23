package org.ehfg.app.search;

import org.ehfg.app.rest.SearchResultRepresentation;

/**
 * @author patrick
 * @since 06.2016
 */
public interface SearchService {
	/**
	 * @return a list of SearchResultRepresentation based on the input parameter.<br>
	 *     An empty list will be returned if the input parameter is null or empty
	 */
	SearchResultRepresentation findBy(String input);
}
