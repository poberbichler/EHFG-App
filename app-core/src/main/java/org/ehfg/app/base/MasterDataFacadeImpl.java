package org.ehfg.app.base;

import java.util.List;

import org.ehfg.app.validation.Validate;
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
	private final LocationRepository locationRepository;

	@Autowired
	public MasterDataFacadeImpl(AppConfigRepository configRepository, PointOfInterestRepository pointOfInterestRepository,
			LocationRepository locationRepository) {
		this.configRepository = configRepository;
		this.pointOfInterestRepository = pointOfInterestRepository;
		this.locationRepository = locationRepository;
	}

	@Override
	public ConfigurationDTO getAppConfiguration() {
		return configRepository.find();
	}

	@Override
	@Validate
	@Transactional(readOnly = false)
	public void saveAppConfiguration(ConfigurationDTO source) {
		final AppConfig target = new AppConfig();
		if (source.getHashtag().startsWith("#")) {
			target.setHashtag(source.getHashtag());
		}

		else {
			target.setHashtag(source.getHashtag());
		}

		target.setNumberOfTweets(source.getNumberOfTweets());
		configRepository.save(target);
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

	@Override
	public List<LocationDTO> findAllLocation() {
		return locationRepository.findAllLocations();
	}

	@Override
	@Validate
	@Transactional(readOnly = false)
	public Long saveLocation(LocationDTO source) {
		Location target = new Location(source.getId(), source.getName(), 
				source.getCoordinate().getxValue(), source.getCoordinate().getyValue());
		
		locationRepository.save(target);
		return target.getId();
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteLocation(Long locationId) {
		locationRepository.delete(locationId);
	}
}
