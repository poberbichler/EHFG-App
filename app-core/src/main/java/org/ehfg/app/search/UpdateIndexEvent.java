package org.ehfg.app.search;

import org.springframework.context.ApplicationEvent;

/**
 * @author patrick
 * @since 07.2016
 */
public class UpdateIndexEvent extends ApplicationEvent {

	public UpdateIndexEvent(Object source) {
		super(source);
	}
}
