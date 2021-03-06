package org.ehfg.app.base;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author patrick
 * @since 06.2015
 */
interface PointOfInterestRepository extends MongoRepository<PointOfInterest, String> {
	// empty
}
