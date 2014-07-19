package org.ehfg.app.core.repository;

import java.util.List;

import org.ehfg.app.api.dto.LocationDTO;
import org.ehfg.app.core.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 19.07.2014
 */
public interface LocationRepository extends CrudRepository<Location, Long>{
	@Query("SELECT new org.ehfg.app.api.dto.LocationDTO(l.id, l.name, l.coordinate.xValue, l.coordinate.yValue) FROM Location l")
	List<LocationDTO> findAllLocations();
}
