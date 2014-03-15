package org.ehfg.app.api.facade;

import org.ehfg.app.api.dto.ConfigurationDTO;

/**
 * @author patrick
 * @since 14.03.2014
 */
public interface MasterDataFacade {
	ConfigurationDTO getAppConfiguration();
	void saveAppConfiguration(ConfigurationDTO config);
}
