package org.ehfg.app;

import java.util.Collection;
import java.util.Collections;

/**
 * @author patrick
 * @since 12.2014
 */
public abstract class AbstractPageableDTO<T> {
	private final Collection<T> data;

	private final int currentPage;
	private final int maxPages;

	/**
	 * @param data
	 * @param currentPage
	 * @param maxPages
	 */
	public AbstractPageableDTO(Collection<T> data, int currentPage, int maxPages) {
		this.data = data;
		this.currentPage = currentPage;
		this.maxPages = maxPages;
	}

	public boolean isMorePages() {
		return currentPage != maxPages;
	}
	
	public Collection<T> getData() {
		return Collections.unmodifiableCollection(data);
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getMaxPages() {
		return maxPages;
	}
}
