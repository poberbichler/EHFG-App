package org.ehfg.app.search;

/**
 * Common interface for objects which can be index via lucene
 *
 * @author patrick
 * @since 07.2016
 */
public interface Indexable {
	String ID_FIELD = "id";
	String NAME_FIELD = "name";
	String TYPE_FIELD = "type";
	String CONTENT_FIELD = "content";


	/**
	 * @return the id of the object, used for building the url
	 */
	String getId();

	/**
	 * @return the name of the element which should be displayed on the ui
	 */
	String getDisplayName();

	/**
	 * @return the ResultType of the given oject
	 */
	ResultType getType();

	/**
	 * @return the description for the given object. used for the search index
	 */
	String getDescription();
}
