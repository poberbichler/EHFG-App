package org.ehfg.app.base;

import java.util.Collection;
import java.util.List;

/**
 * @author patrick
 * @since 03.2014
 */
public interface MasterDataFacade {
	ConfigurationDTO getAppConfiguration();
	ConfigurationDTO saveAppConfiguration(ConfigurationDTO config);
	List<PointOfInterestDTO> findAllPointsOfInterest();
	List<PointOfInterestDTO> savePointOfInterest(PointOfInterestDTO source);
	void removePoint(String id);
	List<LocationDTO> findAllLocation();

	Collection<MapCategoryDTO> findAllMapCategories();
	void saveMapCategory(MapCategoryDTO categoryDTO);
	void deleteMapCategory(String id);
	
	String saveLocation(LocationDTO newLocation);
	void deleteLocation(String locationId);
}
