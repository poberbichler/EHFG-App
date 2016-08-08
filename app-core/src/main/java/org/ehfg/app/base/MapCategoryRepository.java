package org.ehfg.app.base;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author poberbichler
 * @since 08.2016
 */
public interface MapCategoryRepository extends MongoRepository<MapCategory, String> {
    MapCategory findByName(String name);
}
