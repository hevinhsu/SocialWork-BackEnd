package com.socialWork.auth.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1463768367790619826L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @Column(name="username", nullable = false)
    String username;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "nickname", nullable = false)
    String nickname;

    @Column(name = "refresh_token")
    String refreshToken;
    @Column(name = "createTime", nullable = false)
    Timestamp createTime;
    @Column(name = "updateTime")
    Timestamp updateTime;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "userId")}, inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "roleId")})
    private List<Role> roles;

}
