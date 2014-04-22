package org.ehfg.app.core.facade;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.ehfg.app.api.dto.ConfigurationDTO;
import org.ehfg.app.api.dto.CoordinateDTO;
import org.ehfg.app.api.dto.PointOfInterestDTO;
import org.ehfg.app.api.facade.MasterDataFacade;
import org.ehfg.app.core.entities.AppConfig;
import org.ehfg.app.core.entities.Coordinate;
import org.ehfg.app.core.entities.PointOfInterest;
import org.ehfg.app.core.repository.AppConfigRepository;
import org.ehfg.app.core.repository.PointOfInterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component
@Transactional(readOnly = true)
public class MasterDataFacadeImpl implements MasterDataFacade {
	private final AppConfigRepository configRepository;
	private final PointOfInterestRepository pointOfInterestRepository;

	@Autowired
	public MasterDataFacadeImpl(AppConfigRepository configRepository, PointOfInterestRepository pointOfInterestRepository) {
		super();
		this.configRepository = configRepository;
		this.pointOfInterestRepository = pointOfInterestRepository;
	}

	@Override
	public ConfigurationDTO getAppConfiguration() {
		return configRepository.find();
	}

	@Override
	@Transactional(readOnly = false)
	public void saveAppConfiguration(ConfigurationDTO source) {
		final AppConfig target = new AppConfig();
		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		// FIXME: better, but still not ideal
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

	@Override
	public List<PointOfInterestDTO> findAllPointsOfInterest() {
		return pointOfInterestRepository.findAllPoints();
	}

	@Override
	@Transactional(readOnly = false)
	public List<PointOfInterestDTO> savePointOfInterest(PointOfInterestDTO source) {
		PointOfInterest target;
		if (source.getId() == null) {
			target = new PointOfInterest();
		}

		else {
			target = pointOfInterestRepository.findOne(source.getId());
		}

		target.setAddress(source.getAddress());
		target.setDescription(source.getDescription());
		target.setName(source.getName());
		target.setContact(source.getContact());
		target.setWebsite(source.getWebsite());

		final CoordinateDTO coordinate = source.getCoordinate();
		target.setCoordinate(new Coordinate(coordinate.getxValue(), coordinate.getyValue()));

		pointOfInterestRepository.save(target);
		return findAllPointsOfInterest();
	}

	@Override
	@Transactional(readOnly = false)
	public void removePoint(Long id) {
		pointOfInterestRepository.delete(id);
	}
}
