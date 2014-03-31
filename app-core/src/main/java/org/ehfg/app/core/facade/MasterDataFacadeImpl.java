package org.ehfg.app.core.facade;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;

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
	public void saveAppConfiguration(ConfigurationDTO source)  {
		final AppConfig target = new AppConfig();
		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		//FIXME: better, but still not ideal
		final Set<ConstraintViolation<ConfigurationDTO>> validationResult = validator.validate(source, Default.class);
		if (validationResult.isEmpty()) {
			if (source.getHashtag().startsWith("#")) {
				target.setHashtag(source.getHashtag());
			}

			else {
				target.setHashtag(source.getHashtag());
			}

			target.setNumberOfTweets(source.getNumberOfTweets());
			configRepository.save(target);
		}
		
		else {
			throw new ValidationException(String.format("%s input parameters are invalid", validationResult.size()));
		}

	}
}
