package org.ehfg.app.program;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author patrick
 * @since 06.2015
 */
interface ConferenceDayRepository extends MongoRepository<ConferenceDay, String> {
	// empty
}
