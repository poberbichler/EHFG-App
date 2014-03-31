package org.ehfg.app.core.repository;

import org.ehfg.app.api.dto.ConfigurationDTO;
import org.ehfg.app.core.entities.AppConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 14.03.2014
 */
public interface AppConfigRepository extends CrudRepository<AppConfig, Long> {
	@Query("SELECT new org.ehfg.app.api.dto.ConfigurationDTO(c.hashtag, c.numberOfTweets) "
			+ "FROM AppConfig c WHERE c.id = org.ehfg.app.core.entities.AppConfig.CONFIG_ID")
	ConfigurationDTO find();
}
