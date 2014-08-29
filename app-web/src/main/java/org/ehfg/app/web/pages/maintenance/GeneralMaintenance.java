package org.ehfg.app.web.pages.maintenance;

import javax.inject.Inject;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Submit;
import org.apache.tapestry5.corelib.components.TextField;
import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.ehfg.app.web.components.BootstrapLayout;

/**
 * @author patrick
 * @since 14.03.2014
 */
public class GeneralMaintenance {
	@Component
	private BootstrapLayout layout;
	
	@Component
	private Form inputForm;
	
	@Component(parameters = {"value=configuration.hashtag"})
	private TextField hashtag;
	
	@Component(parameters = {"value=configuration.numberOfTweets"})
	private TextField numberOfTweets;
	
	@Component
	private Submit submitInputForm;
	
	@Inject
	private MasterDataFacade masterDataFacade;
	
	@Property
	private ConfigurationDTO configuration;
	
	void onPrepare() {
		configuration = masterDataFacade.getAppConfiguration();
	}
	
	@OnEvent(component = "inputForm", value = EventConstants.SUCCESS)
	void onSuccessFromInputForm() {
		masterDataFacade.saveAppConfiguration(configuration);
	}
	
	Object onException(Exception e) {
		inputForm.recordError(e.getMessage());
		return this;
	}
}
