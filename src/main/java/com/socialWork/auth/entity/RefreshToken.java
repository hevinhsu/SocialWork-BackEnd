package com.socialWork.auth.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class RefreshToken {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "token", nullable = false)
	private String token;
	@Column(name = "expired_time", nullable = false)
	private Timestamp expiredTime;
	
	public RefreshToken(String token, Timestamp expiredTime) {
		this.token = token;
		this.expiredTime = expiredTime;
	}
}
