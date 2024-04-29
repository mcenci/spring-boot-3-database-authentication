package com.cenci.security.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cenci.security.dao.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {
 
	Optional<User> findByUsername(String username);
	
	Boolean existsUserByUsername(String username);
}