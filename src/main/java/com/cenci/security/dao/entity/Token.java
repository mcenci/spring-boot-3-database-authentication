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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue
    @Column(name = "token")
    @JdbcTypeCode(Types.VARCHAR)
    private UUID token;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "expiration_date", nullable = false)
    private OffsetDateTime expiration;

}