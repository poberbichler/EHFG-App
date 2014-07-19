package org.ehfg.app.api.facade;

import java.util.List;

import org.ehfg.app.api.dto.ConfigurationDTO;
import org.ehfg.app.api.dto.LocationDTO;
import org.ehfg.app.api.dto.PointOfInterestDTO;

/**
 * @author patrick
 * @since 14.03.2014
 */
public interface MasterDataFacade {
	ConfigurationDTO getAppConfiguration();
	void saveAppConfiguration(ConfigurationDTO config);
	List<PointOfInterestDTO> findAllPointsOfInterest();
	List<PointOfInterestDTO> savePointOfInterest(PointOfInterestDTO source);
	void removePoint(Long id);
	List<LocationDTO> findAllLocation();
	
	Long saveLocation(LocationDTO newLocation);
	void deleteLocation(Long locationId);
}
