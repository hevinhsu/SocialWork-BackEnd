package com.socialWork.user.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@Table(name = "role")
@Data
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long roldId;
    @Column(name = "rolename", nullable = false)
    String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
