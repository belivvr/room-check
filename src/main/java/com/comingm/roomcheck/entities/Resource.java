package com.comingm.roomcheck.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@Table( name="resource")
public abstract class Resource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 881161043569898066L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id")
    private Long resourceId;
	
	//자원 이름​
	@Column(name="name",columnDefinition = "varchar(255) NOT NULL")
	private String name;
	
	//리소스 sid​
	@Column(name="resource_sid",columnDefinition = "varchar(20) NOT NULL")
	private String resourceSid;
	
	//활성화여부 삭제 N
    @Column(name="active",columnDefinition = "bpchar NOT NULL DEFAULT 'Y'")
    private String active;
    
    @Column(name ="type", insertable = false, updatable = false)
    protected String type;
    
	//공개,비공개​
	@Column(name="publish_type",columnDefinition = "varchar(100) NOT NULL")
	private String publishType; 

	@Column(name="inserted_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
	@CreationTimestamp
    private LocalDateTime insertedAt;

    @Column(name="updated_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @ManyToOne(targetEntity=Member.class, fetch=FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

		
	public String getResourceSid() {
		return resourceSid;
	}

	public void setResourceSid(String resourceSid) {
		this.resourceSid = resourceSid;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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

	
	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}
	
	

	public String getType() {
		return type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    

}
