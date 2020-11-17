package com.socialWork.auth.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Role implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7221779844031532190L;
	public static final long USER = 1L;
	public static final long ADMIN = 2L;
	public static final long ROLE_ADMIN = 3L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long roleId;
    @Column(name = "rolename", nullable = false)
    String roleName;
    @Column(name = "createTime", nullable = false)
    Timestamp createTime;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
    public Role(String roleName, Timestamp createTime) {
    	this.roleName = roleName;
    	this.createTime = createTime;
    }
}
