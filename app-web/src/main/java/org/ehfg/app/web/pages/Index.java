package org.ehfg.app.web.pages;

import javax.inject.Inject;
import javax.naming.NamingException;

import org.apache.tapestry5.annotations.BeginRender;
import org.apache.tapestry5.annotations.Component;
import org.ehfg.app.api.facade.MasterDataFacade;
import org.ehfg.app.web.components.BootstrapLayout;

public class Index {
	@Component
	private BootstrapLayout layout;
	
	@Inject
	private MasterDataFacade masterDataFacade;
	
	@BeginRender
	public void jndi() throws NamingException {
		masterDataFacade.getClass();
	}
}
