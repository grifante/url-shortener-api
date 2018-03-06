package com.urlshortener.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.urlshortener.model.Url;

public interface UrlRepository extends CrudRepository<Url, Long> {

	Optional<Url> findById(long id);
	
	Optional<Url> findByShortUrl(String shortUrl);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM url WHERE id = :id", nativeQuery = true)
	void delete(@Param("id") Long id);

	@Modifying
	@Transactional
	@Query(value = "UPDATE url SET hits = hits + 1 WHERE id = :id", nativeQuery = true)
	void incrementHits(@Param("id") long id);

	@Query(value = "SELECT COUNT(1), IFNULL(SUM(hits), 0) FROM url", nativeQuery = true)
	Object[] getStats();

	@Query(value = "SELECT * FROM url ORDER BY hits DESC LIMIT :limit", nativeQuery = true)
	List<Url> findTopUrls(@Param("limit") int limit);

	@Query(value = "SELECT COUNT(1), IFNULL(SUM(hits), 0) FROM url WHERE user_id = :userId", nativeQuery = true)
	Object[] getStats(@Param("userId") String userId);

	@Query(value = "SELECT * FROM url WHERE user_id = :userId ORDER BY hits DESC LIMIT :limit", nativeQuery = true)
	List<Url> findTopUrls(@Param("limit") int limit, @Param("userId") String userId);

}
