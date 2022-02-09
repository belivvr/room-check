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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table( name="hub_space")
public class HubSpace implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 963137825657932084L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hub_space_id")
    private Long hubSpaceId;
	
	//공간 sid​
	@Column(name="hub_sid",columnDefinition = "varchar(100) NOT NULL")
	private String hubSid;
	
	//공간 이름(ex//채널 1,채널 2)​
	@Column(name="name",columnDefinition = "varchar(255) NOT NULL")
	private String name;
	
	//공개,비공개 방​
	@Column(name="publish_type",columnDefinition = "varchar(20) NOT NULL")
	private String publishType; 

    //활성화여부 삭제 N
    @Column(name="active",columnDefinition = "bpchar NOT NULL DEFAULT 'Y'")
    private String active;
	
	@Column(name="inserted_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
	@CreationTimestamp
    private LocalDateTime insertedAt;

    @Column(name="updated_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    @UpdateTimestamp
    private LocalDateTime updatedAt; 
    
    @ManyToOne(targetEntity=HubScene.class, fetch=FetchType.EAGER)
    @JoinColumn(name = "resource_id")
    private HubScene hubScene;

	public Long getHubSpaceId() {
		return hubSpaceId;
	}

	public void setHubSpaceId(Long hubSpaceId) {
		this.hubSpaceId = hubSpaceId;
	}

	public String getHubSid() {
		return hubSid;
	}

	public void setHubSid(String hubSid) {
		this.hubSid = hubSid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public LocalDateTime getInsertedAt() {
		return insertedAt;
	}

	public void setInsertedAt(LocalDateTime insertedAt) {
		this.insertedAt = insertedAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public HubScene getHubScene() {
		return hubScene;
	}

	public void setHubScene(HubScene hubScene) {
		this.hubScene = hubScene;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
    

}
