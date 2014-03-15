package org.ehfg.app.core.repository;

import org.ehfg.app.core.entities.Tweet;

import org.springframework.data.repository.CrudRepository;

/**
 * @author patrick
 * @since 13.03.2014
 */
public interface TweetRepository extends CrudRepository<Tweet, Long>{

}
