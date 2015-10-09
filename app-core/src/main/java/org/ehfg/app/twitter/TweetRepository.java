package org.ehfg.app.twitter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author patrick
 * @since 06.2015
 */
interface TweetRepository extends MongoRepository<Tweet, String> {
	@Query("{hashtag: ?0, creationDate: {$gt: ?1}}")
	List<Tweet> findNewerTweetsByHashtag(String hashtag, LocalDateTime lastTweet, Sort sort);

	Page<Tweet> findByHashtagOrderByCreationDateDesc(String hashtag, Pageable pageable);

    List<Tweet> findByHashtagIgnoreCase(String hashtag);
}
