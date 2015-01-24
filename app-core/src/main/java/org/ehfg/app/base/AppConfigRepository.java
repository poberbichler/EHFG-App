package org.ehfg.app.base;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 14.03.2014
 */
interface AppConfigRepository extends CrudRepository<AppConfig, Long> {
	@Query("SELECT new org.ehfg.app.base.ConfigurationDTO(c.hashtag, c.numberOfTweets, c.backdoorScript) "
			+ "FROM AppConfig c WHERE c.id = org.ehfg.app.base.AppConfig.CONFIG_ID")
	ConfigurationDTO find();
}
