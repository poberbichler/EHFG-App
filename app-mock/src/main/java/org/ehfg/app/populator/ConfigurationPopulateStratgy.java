package org.ehfg.app.populator;

import org.ehfg.app.InMemoryService;
import org.ehfg.app.base.ConfigurationDTO;

/**
 * @author patrick
 * @since 12.2014
 */
@InMemoryService
class ConfigurationPopulateStratgy extends AbstractPopulateStrategy {
	@Override
	public void execute() throws Exception {
		masterDataFacade.saveAppConfiguration(new ConfigurationDTO("EHFG2014", 10, ""));
	}
}
