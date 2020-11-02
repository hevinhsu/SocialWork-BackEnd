package com.socialWork.auth.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@Table(name = "role")
@Data
public class Role implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7221779844031532190L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long roleId;
    @Column(name = "rolename", nullable = false)
    String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
