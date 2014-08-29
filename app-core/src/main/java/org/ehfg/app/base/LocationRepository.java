package org.ehfg.app.base;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 19.07.2014
 */
interface LocationRepository extends CrudRepository<Location, Long>{
	@Query("SELECT new org.ehfg.app.base.LocationDTO(l.id, l.name, l.coordinate.xValue, l.coordinate.yValue) FROM Location l")
	List<LocationDTO> findAllLocations();
}
