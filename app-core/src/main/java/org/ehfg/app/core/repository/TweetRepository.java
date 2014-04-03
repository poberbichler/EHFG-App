package org.ehfg.app.core.repository;

import java.util.Date;
import java.util.List;

import org.ehfg.app.api.dto.TweetDTO;
import org.ehfg.app.core.entities.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author patrick
 * @since 13.03.2014
 */
public interface TweetRepository extends PagingAndSortingRepository<Tweet, Long> {
	@Query("SELECT new org.ehfg.app.api.dto.TweetDTO(a.fullName, a.nickName, t.message, a.profileImage, t.creationDate) "
			+ "FROM Tweet t INNER JOIN t.author a ORDER BY t.creationDate DESC")
	List<TweetDTO> findTweets();

	@Query("SELECT new org.ehfg.app.api.dto.TweetDTO(a.fullName, a.nickName, t.message, a.profileImage, t.creationDate) "
			+ "FROM Tweet t INNER JOIN t.author a WHERE t.hashtag = ?1 ORDER BY t.creationDate DESC")
	List<TweetDTO> findTweetsByHashtag(String hashtag);

	@Query("SELECT new org.ehfg.app.api.dto.TweetDTO(a.fullName, a.nickName, t.message, a.profileImage, t.creationDate) "
			+ "FROM Tweet t INNER JOIN t.author a WHERE t.hashtag = ?1 AND t.creationDate > ?2 ORDER BY t.creationDate DESC")
	List<TweetDTO> findNewerTweetsByHashtag(String hashtag, Date lastTweet);
	
	Page<Tweet> findByHashtagOrderByCreationDateDesc(String hashtag, Pageable pageable);
}
