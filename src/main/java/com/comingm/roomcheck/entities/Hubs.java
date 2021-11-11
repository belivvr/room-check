package com.comingm.roomcheck.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Hubs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2161582425404210608L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hubs_id")
    private Long hubsId;
	
	@Column(name="hub_sid",columnDefinition = "varchar(255) NULL",unique=true)
	private String hubSid;
	
	@Column(name="name",columnDefinition = "varchar(255) NOT NULL")
	private String name;
	
	@Column(name="slug",columnDefinition = "varchar(255) NOT NULL")
	private String slug;
	
	@Column(name="scene_sid",columnDefinition = "varchar(255) NULL")
	private String sceneSid;

	@Column(name="room_size", columnDefinition = "int8 NOT NULL")
	private int roomSize;
	
	@Column(name="lobby_count", columnDefinition = "int8 NOT NULL")
	private int lobbyCount;
	
	@Column(name="member_count", columnDefinition = "int8 NOT NULL")
	private int memberCount;
	
	@Column(name="delete_yn", columnDefinition = "bpchar NOT NULL")
	private String deleteYn;

	
	
	@Column(name="inserted_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
	@CreationTimestamp
    private LocalDateTime insertedAt;

    @Column(name="updated_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @JsonIgnore
    @ManyToOne(targetEntity=ApiKey.class, fetch=FetchType.LAZY)
    @JoinColumn(name = "api_key_id")
    private ApiKey apiKey;
	
}
