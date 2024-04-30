package com.cenci.security.dao.entity;

import java.sql.Types;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token", uniqueConstraints = 
		@UniqueConstraint(columnNames = {"userId", "type"}))
public class Token {

    @Id
    @GeneratedValue
    @Column(name = "token")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID token;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "expiration_date", nullable = false)
    private OffsetDateTime expiration;
    
    @Column(name = "type", nullable = false)
    private TokenType type;
}