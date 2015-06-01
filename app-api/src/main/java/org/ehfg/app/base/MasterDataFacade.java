package org.ehfg.app.base;

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
	
	String saveLocation(LocationDTO newLocation);
	void deleteLocation(String locationId);
}
