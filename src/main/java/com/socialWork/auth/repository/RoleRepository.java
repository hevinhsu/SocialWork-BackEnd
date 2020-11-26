package com.socialWork.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.socialWork.auth.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
