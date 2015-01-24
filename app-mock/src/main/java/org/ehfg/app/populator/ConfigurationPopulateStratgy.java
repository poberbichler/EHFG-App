package org.ehfg.app.populator;

import org.ehfg.app.MockService;
import org.ehfg.app.base.ConfigurationDTO;

/**
 * @author patrick
 * @since 12.2014
 */
@MockService
class ConfigurationPopulateStratgy extends AbstractPopulateStrategy {
	@Override
	public void execute() throws Exception {
		masterDataFacade.saveAppConfiguration(new ConfigurationDTO("EHFG2014", 10, ""));
	}
}
