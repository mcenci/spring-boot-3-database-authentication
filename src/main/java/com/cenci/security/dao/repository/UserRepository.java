package com.cenci.security.dao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cenci.security.dao.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
 
	Optional<User> findByUsername(String username);
}