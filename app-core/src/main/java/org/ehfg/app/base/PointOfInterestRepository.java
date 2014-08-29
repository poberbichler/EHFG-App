package org.ehfg.app.base;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 13.04.2014
 */
interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long> {
	@Query("SELECT new org.ehfg.app.base.PointOfInterestDTO(p.id, p.name, p.address, p.description, "
			+ "p.contact, p.website, p.coordinate.xValue, p.coordinate.yValue) "
			+ "FROM PointOfInterest p")
	List<PointOfInterestDTO> findAllPoints();
}
