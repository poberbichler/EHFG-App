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
final class MasterDataFacadeImpl implements MasterDataFacade {
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
		ConfigurationDTO config = configRepository.find();
		if (config == null) {
			config = new ConfigurationDTO("EHFG", 1);
		}
		
		return config;
	}

	@Override
	@Validate
	@Transactional(readOnly = false)
	public ConfigurationDTO saveAppConfiguration(ConfigurationDTO source) {
		final AppConfig target = new AppConfig();
		
		target.setAndFixHashtag(source.getHashtag());
		target.setNumberOfTweets(source.getNumberOfTweets());
		
		configRepository.save(target);
		
		return getAppConfiguration();
	}

	@Override
	public List<PointOfInterestDTO> findAllPointsOfInterest() {
		return pointOfInterestRepository.findAllPoints();
	}

	@Override
	@Transactional(readOnly = false)
	public List<PointOfInterestDTO> savePointOfInterest(PointOfInterestDTO source) {
		PointOfInterest target = fetchOrCreatePointOfInterest(source.getId());
		mapFromDtoEntity(source, target);

		pointOfInterestRepository.save(target);
		return findAllPointsOfInterest();
	}

	/**
	 * fetches the point for the given id.<br>
	 * if the id is null, a newly created point will be returned
	 * 
	 * @param id to be checked
	 * @return a {@link PointOfInterest} (never null)
	 */
	private PointOfInterest fetchOrCreatePointOfInterest(Long id) {
		if (id == null) {
			return new PointOfInterest();
		}
		
		else {
			return pointOfInterestRepository.findOne(id);
		}
	}

	/**
	 * maps the values from the source to the target
	 */
	private void mapFromDtoEntity(PointOfInterestDTO source, PointOfInterest target) {
		target.setAddress(source.getAddress());
		target.setDescription(source.getDescription());
		target.setName(source.getName());
		target.setContact(source.getContact());
		target.setWebsite(source.getWebsite());
		
		final CoordinateDTO coordinate = source.getCoordinate();
		target.setCoordinate(new Coordinate(coordinate.getxValue(), coordinate.getyValue()));
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
