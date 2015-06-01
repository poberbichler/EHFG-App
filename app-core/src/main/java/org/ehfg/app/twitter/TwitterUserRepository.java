package org.ehfg.app.twitter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 06.2015
 */
interface TwitterUserRepository extends MongoRepository<TwitterUser, String> {
	// empty
}
