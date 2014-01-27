package org.ehfg.app.web.components;

import javax.inject.Inject;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;

@Import(stack = {"bootstrap"})
public class BootstrapLayout {
	@Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;
	
	@Parameter(allowNull = false, value = "true", defaultPrefix = BindingConstants.LITERAL)
	private Boolean singleWidget;
	
	@Property
	private Block footer;
	
	@Component
	private Sidebar sidebar;
	
	@Inject
	private ComponentResources resources;
	
	@Inject
	private Messages messages;
	
	String defaultTitle() {
		final String pageTitle = "pagetitle." + resources.getPageName();
		return messages.contains(pageTitle) ? messages.get(pageTitle) : messages.get("default.title");
	}
	
	public String getSingleWidgetClass() {
		return singleWidget ? "well" : "remove-padding";
	}

}
