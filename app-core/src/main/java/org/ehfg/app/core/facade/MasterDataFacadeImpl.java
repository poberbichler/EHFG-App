package org.ehfg.app.core.facade;

import org.ehfg.app.api.dto.ConfigurationDTO;
import org.ehfg.app.api.facade.MasterDataFacade;
import org.ehfg.app.core.entities.AppConfig;
import org.ehfg.app.core.repository.AppConfigRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author patrick
 * @since 14.03.2014
 */
public class MasterDataFacadeImpl implements MasterDataFacade {
	private final AppConfigRepository configRepository;
	
	public MasterDataFacadeImpl(AppConfigRepository configRepository) {
		super();
		this.configRepository = configRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public ConfigurationDTO getAppConfiguration() {
		return configRepository.find();
	}

	@Override
	@Transactional(readOnly = false)
	public void saveAppConfiguration(ConfigurationDTO source) {
		AppConfig target = new AppConfig();
		
		//FIXME: use bean validation instead
		if (source.getHashtag() != null) {
			if (source.getHashtag().startsWith("#")) {
				target.setHashtag(source.getHashtag());
			}
			
			else {
				target.setHashtag(source.getHashtag());
			}
		}
		
		configRepository.save(target);
	}
}
