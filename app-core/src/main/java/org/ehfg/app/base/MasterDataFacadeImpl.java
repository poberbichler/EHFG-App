package org.ehfg.app.base;

import java.util.List;
import java.util.stream.Collectors;

import org.ehfg.app.validation.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component
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
		AppConfig config = configRepository.find();
		if (config == null) {
			return new ConfigurationDTO("EHFG", 1, "");
		}

		return new ConfigurationDTO(config.getHashtag(), config.getNumberOfTweets(), config.getBackdoorScript());
	}

	@Override
	@Validate
	public ConfigurationDTO saveAppConfiguration(ConfigurationDTO source) {
		final AppConfig target = new AppConfig();
		
		target.setAndFixHashtag(source.getHashtag());
		target.setNumberOfTweets(source.getNumberOfTweets());
		target.setBackdoorScript(source.getBackdoorScript());
		
		configRepository.save(target);
		
		return getAppConfiguration();
	}

	@Override
	public List<PointOfInterestDTO> findAllPointsOfInterest() {
		return pointOfInterestRepository.findAll().stream()
				.map(point -> new PointOfInterestDTO(point.getId(), point.getName(), point.getAddress(),
								point.getDescription(), point.getContact(), point.getWebsite(),
								point.getCoordinate().getxValue(), point.getCoordinate().getyValue())
				).collect(Collectors.toList());
	}

	@Override
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
	private PointOfInterest fetchOrCreatePointOfInterest(String id) {
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
	public void removePoint(String id) {
		pointOfInterestRepository.delete(id);
	}

	@Override
	public List<LocationDTO> findAllLocation() {
		return locationRepository.findAll().stream()
				.map(l -> new LocationDTO(l.getId(), l.getName(), l.getCoordinate().getxValue(), l.getCoordinate().getyValue()))
				.collect(Collectors.toList());
	}

	@Override
	@Validate
	public String saveLocation(LocationDTO source) {
		Location target = new Location(source.getId(), source.getName(), 
				source.getCoordinate().getxValue(), source.getCoordinate().getyValue());
		
		locationRepository.save(target);
		return target.getId();
	}

	@Override
	public void deleteLocation(String locationId) {
		locationRepository.delete(locationId);
	}
}
