package com.urlshortener.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.urlshortener.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findById(String id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM user WHERE id = :id", nativeQuery = true)
	void delete(@Param("id") String id);

	
	
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user VALUES (:id)", nativeQuery = true)
	void insert(@Param("id") String id) throws DataIntegrityViolationException;
	
}
