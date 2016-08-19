package org.ehfg.app.base;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.ehfg.app.search.Indexable;
import org.ehfg.app.search.ResultType;
import org.ehfg.app.search.SearchIndexDataProvider;
import org.ehfg.app.validation.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author patrick
 * @since 14.03.2014
 */
@Component
class MasterDataFacadeImpl implements MasterDataFacade, SearchIndexDataProvider<Indexable> {
    private final AppConfigRepository configRepository;
    private final PointOfInterestRepository pointOfInterestRepository;
    private final LocationRepository locationRepository;
    private final MapCategoryRepository mapCategoryRepository;

    @Autowired
    public MasterDataFacadeImpl(AppConfigRepository configRepository, PointOfInterestRepository pointOfInterestRepository, LocationRepository locationRepository, MapCategoryRepository mapCategoryRepository) {
        this.configRepository = configRepository;
        this.pointOfInterestRepository = pointOfInterestRepository;
        this.locationRepository = locationRepository;
        this.mapCategoryRepository = mapCategoryRepository;
    }

    @Override
    public ConfigurationDTO getAppConfiguration() {
        final AppConfig config = configRepository.find().orElse(AppConfig.withDefaultValues());
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
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PointOfInterestDTO mapToDto(PointOfInterest input) {
        if (input == null) {
            return null;
        }

        return new PointOfInterestDTO(input.getId(), input.getName(), input.getAddress(), input.getDescription(),
                input.getContact(), input.getWebsite(), input.getCoordinate().getxValue(), input.getCoordinate().getyValue(),
                input.getMapCategory() == null ? null : mapToDto(input.getMapCategory()));
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
        if (StringUtils.isEmpty(id)) {
            return new PointOfInterest();
        } else {
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
        target.setMapCategory(mapCategoryRepository.findByName(source.getCategoryName()));

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
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<MapCategoryDTO> findAllMapCategories() {
        return mapCategoryRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private MapCategoryDTO mapToDto(MapCategory source) {
        return new MapCategoryDTO(source.getId(), source.getName(), source.getCssClass(), source.getImageUrl());
    }

    @Override
    public void saveMapCategory(MapCategoryDTO source) {
        MapCategory categoryToSave = null;

        if (source.getId() != null) {
            categoryToSave = mapCategoryRepository.findOne(source.getId());
        }

        if (categoryToSave == null) {
            categoryToSave = new MapCategory();
        }

        categoryToSave.setCssClass(source.getCssClass());
        categoryToSave.setImageUrl(source.getImageUrl());
        categoryToSave.setName(source.getName());

        mapCategoryRepository.save(categoryToSave);
    }

    @Override
    public void deleteMapCategory(String id) {
        mapCategoryRepository.delete(id);
    }

    private LocationDTO mapToDto(Location input) {
        if (input == null) {
            return null;
        }

        if (input.getPoint() != null) {
            return new LocationDTO(input.getId(), input.getName(), mapToDto(input.getPoint()));
        }

        return new LocationDTO(input.getId(), input.getName(), input.getCoordinate().getxValue(), input.getCoordinate().getyValue());
    }

    @Override
    public String saveLocation(LocationDTO source) {
        Location target = null;
        if (source.getMappedPointOfInterest() != null && !StringUtils.isEmpty(source.getMappedPointOfInterest().getId())) {
            PointOfInterest point = pointOfInterestRepository.findOne(source.getMappedPointOfInterest().getId());
            target = new Location(source.getId(), source.getName(), point);

        } else {
            target = new Location(source.getId(), source.getName(),
                    source.getCoordinate().getxValue(), source.getCoordinate().getyValue());
        }

        locationRepository.save(target);
        return target.getId();
    }

    @Override
    public void deleteLocation(String locationId) {
        locationRepository.delete(locationId);
    }

    @Override
    public Collection<? extends Indexable> getData() {
        return CollectionUtils.union(findAllLocation(), findAllPointsOfInterest());
    }

    @Override
    public Set<ResultType> getResultTypes() {
        return EnumSet.of(ResultType.LOCATION);
    }
}
