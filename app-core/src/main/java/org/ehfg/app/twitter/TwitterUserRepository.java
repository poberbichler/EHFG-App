package org.ehfg.app.twitter;

import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 13.03.2014
 */
interface TwitterUserRepository extends CrudRepository<TwitterUser, Long> {

}
