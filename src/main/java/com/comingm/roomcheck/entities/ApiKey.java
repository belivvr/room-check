package com.comingm.roomcheck.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table( name="api_key")
public class ApiKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5780020424323811923L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_key_id")
    private Long apiKeyId;
	
	@Column(name="apiKey",columnDefinition = "varchar(255) NOT NULL")
    private String apiKey;
	
	@Column(name="domain",columnDefinition = "varchar(50) NOT NULL")
    private String domain;
	
	@Column(name="inserted_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    private LocalDateTime insertedAt;

    @Column(name="updated_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    private LocalDateTime updatedAt;

    @Column(name="delete_yn",columnDefinition = "char NOT NULL DEFAULT 'N'")
    private char deleteYn;
	
}
