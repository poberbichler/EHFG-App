package org.ehfg.app.twitter;

import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * @author patrick
 * @since 06.2015
 */
interface TwitterUserRepository extends Repository<TwitterUser, String> {
	Optional<TwitterUser> findOne(String id);

	TwitterUser save(TwitterUser user);
}
