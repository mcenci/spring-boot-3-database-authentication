package com.cenci.security.dao.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cenci.security.dao.entity.Token;

public interface TokenRepository extends JpaRepository<Token, UUID> {
 
}