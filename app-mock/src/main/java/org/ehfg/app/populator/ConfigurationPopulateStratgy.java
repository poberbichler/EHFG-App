package org.ehfg.app.populator;

import org.ehfg.app.base.ConfigurationDTO;
import org.ehfg.app.base.MasterDataFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author patrick
 * @since 12.2014
 */
@Service
public class ConfigurationPopulateStratgy extends AbstractPopulateStrategy {
	private final MasterDataFacade masterDataFacade;

	@Autowired
	public ConfigurationPopulateStratgy(MasterDataFacade masterDataFacade) {
		this.masterDataFacade = masterDataFacade;
	}

	@Override
	public void execute() throws Exception {
		masterDataFacade.saveAppConfiguration(new ConfigurationDTO("EHFG2014", 10));
	}
}
