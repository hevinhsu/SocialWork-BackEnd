package com.socialWork.user.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="user")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;
    @Column(name="username", nullable = false)
    String username;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "refresh_token")
    String refreshToken;

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "uid", referencedColumnName = "userId")}, inverseJoinColumns = {@JoinColumn(name = "rid", referencedColumnName = "roldId")})
    private List<Role> roles;

    public User() {

    }

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}
