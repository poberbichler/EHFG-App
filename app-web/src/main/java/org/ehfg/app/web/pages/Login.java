package org.ehfg.app.web.pages;

import javax.inject.Inject;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.PageActivationContext;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.services.Request;
import org.ehfg.app.web.components.BootstrapLayout;

/**
 * @author patrick
 * @since 26.04.2014
 */
public class Login {
	@Component(parameters = {"hideSidebar=true"})
	private BootstrapLayout layout;
	
	@Inject
	private Request request;
	
	@PageActivationContext
	private String failed;
	
	@Property
	private Boolean loginSuccessful;
	
	void beginRender() {
		loginSuccessful = failed != null ? Boolean.TRUE : Boolean.FALSE;
	}
	
	public String getContextPath() {
		return request.getContextPath();
	}
}
