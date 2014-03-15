package org.ehfg.app.core.repository;

import org.ehfg.app.core.entities.TwitterUser;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 13.03.2014
 */
public interface TwitterUserRepository extends CrudRepository<TwitterUser, Long> {

}
