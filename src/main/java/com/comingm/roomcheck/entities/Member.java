package com.comingm.roomcheck.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table( name="members",
        indexes = {
                    @Index(name = "member_index_1",  columnList="login_id"),
                    @Index(name = "member_index_2",  columnList="indvdlinfo_agre"),
                    @Index(name = "member_index_3",  columnList="login_id,secsn,login_type")
                  },
        uniqueConstraints={
        			@UniqueConstraint(columnNames={"login_id"})
        		  }
)
public class Member implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2213159483144479237L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    //계정(이메일)
    @Column(name = "login_id",columnDefinition = "varchar(50) NOT NULL")
    private String loginId;

    //비밀번호
    @JsonIgnore
    @Column(name = "password",columnDefinition = "varchar(255) NULL")
    private String password;

    //개인정보 동의 여부(INDIVIDUAL INFORMATION AGREEMENT YN)
    @Column(name = "indvdlinfo_agre",columnDefinition = "bpchar NOT NULL")
    private String indvdlinfoAgre;

    //개인정보 동의 날짜(INDIVIDUAL INFORMATION AGREEMENT DATE)
    @Column(name = "indvdlinfo_agre_dt",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    @CreationTimestamp
    private LocalDateTime indvdlinfoAgreDt;

    //로그인 타입(로컬,소셜)
    @Column(name = "login_type",columnDefinition = "varchar(10) NOT NULL")
    private String loginType;
    
    //탈퇴 여부(SECESSION 탈퇴)
    @Column(name = "secsn",columnDefinition = "bpchar NOT NULL DEFAULT 'N'")
    private String secsn;
    
    //탈퇴 시간(SECESSION AT)
    @Column(name = "secsn_at",columnDefinition = "timestamp NULL DEFAULT now()")
    private LocalDateTime secsnAt;
    
    //마지막 로그인 시간
    @Column(name = "last_login",columnDefinition = "timestamp NULL DEFAULT now()")
    private LocalDateTime lastLogin;
    
    //결제정보 등록 여부
    @Column(name = "pay_method",columnDefinition = "bpchar NOT NULL DEFAULT 'N'")
    private String payMethod;
 
    //권한
    @Column(name = "role", columnDefinition = "varchar(50) NOT NULL")
    private String role;
    
    @Column(name = "inserted_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    @CreationTimestamp
    private LocalDateTime insertedAt;

    @Column(name = "updated_at",columnDefinition = "timestamp NOT NULL DEFAULT now()")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIndvdlinfoAgre() {
		return indvdlinfoAgre;
	}

	public void setIndvdlinfoAgre(String indvdlinfoAgre) {
		this.indvdlinfoAgre = indvdlinfoAgre;
	}

	public LocalDateTime getIndvdlinfoAgreDt() {
		return indvdlinfoAgreDt;
	}

	public void setIndvdlinfoAgreDt(LocalDateTime indvdlinfoAgreDt) {
		this.indvdlinfoAgreDt = indvdlinfoAgreDt;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	
	public String getSecsn() {
		return secsn;
	}

	public void setSecsn(String secsn) {
		this.secsn = secsn;
	}

	public LocalDateTime getSecsnAt() {
		return secsnAt;
	}

	public void setSecsnAt(LocalDateTime secsnAt) {
		this.secsnAt = secsnAt;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
  
    
    
}
