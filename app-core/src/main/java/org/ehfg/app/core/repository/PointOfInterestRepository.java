package org.ehfg.app.core.repository;

import java.util.List;

import org.ehfg.app.api.dto.PointOfInterestDTO;
import org.ehfg.app.core.entities.PointOfInterest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 13.04.2014
 */
public interface PointOfInterestRepository extends CrudRepository<PointOfInterest, Long> {
	@Query("SELECT new org.ehfg.app.api.dto.PointOfInterestDTO(p.id, p.name, p.address, p.description, "
			+ "p.contact, p.website, p.coordinate.xValue, p.coordinate.yValue) "
			+ "FROM PointOfInterest p")
	List<PointOfInterestDTO> findAllPoints();
}
