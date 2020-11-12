package com.socialWork;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.socialWork.auth.pojo.Role;
import com.socialWork.auth.pojo.User;
import com.socialWork.auth.repository.RoleRepository;
import com.socialWork.auth.repository.UserRepository;


@SpringBootApplication
public class SocialWorkApplication implements CommandLineRunner {
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepository userRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(SocialWorkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role role = new Role();
		role.setRoleName("ROLE_USER");
		Role role1 = new Role();
		role1.setRoleName("ROLE_ADMIN");
		roleRepo.save(role);
		roleRepo.save(role1);
		
		User user = new User(null, "test", "$2a$10$C5EM7Y4NeBerGx8STduaxOBCAf4g9wdPBKSUErhTKS.lNOfziZnPe", "test@xxx.xx", "nickTest", "", Arrays.asList(role, role1));
		userRepo.save(user);
	}



}
