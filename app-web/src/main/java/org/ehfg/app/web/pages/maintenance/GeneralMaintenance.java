package org.ehfg.app.web.pages.maintenance;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.ehfg.app.web.components.BootstrapLayout;

public class GeneralMaintenance {
	@Component
	private BootstrapLayout layout;
	
	@Component
	private Form inputForm;
	
	@Component(parameters = {"value=configuration"})
	private TextField hashtag;
	
	@Property
	private String configuration;
}
