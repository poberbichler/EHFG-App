package org.ehfg.app.base;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * @author patrick
 * @since 06.2015
 */
interface AppConfigRepository extends Repository<AppConfig, String> {
	@Query("{id: '" + AppConfig.CONFIG_ID + "'}")
	Optional<AppConfig> find();

	AppConfig save(AppConfig config);
}
