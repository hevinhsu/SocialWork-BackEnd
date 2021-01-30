package com.socialWork;

import java.sql.Timestamp;
import java.util.Arrays;

import com.socialWork.forum.entity.Article;
import com.socialWork.forum.entity.ArticleType;
import com.socialWork.forum.entity.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.socialWork.auth.entity.Role;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.repository.RoleRepository;
import com.socialWork.auth.repository.UserRepository;


@SpringBootApplication
public class SocialWorkApplication implements CommandLineRunner {
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ArticleTypeRepository articleTypeRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(SocialWorkApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("     ============ insert basic data in database ============");

		Timestamp now = new  Timestamp(System.currentTimeMillis());
		Role role = new Role("ROLE_USER", now);
		Role role1 = new Role("ROLE_ADMIN", now);
		System.out.println("     =            insert Role :USER                        =");
		System.out.println("     =            insert Role :ADMIN                       =");
		roleRepo.save(role);
		roleRepo.save(role1);
		User user = new User(null, "test", "$2a$10$C5EM7Y4NeBerGx8STduaxOBCAf4g9wdPBKSUErhTKS.lNOfziZnPe", "test@xxx.xx", "nickTest", now, null, User.ENABLE, Arrays.asList(role, role1));
		userRepo.save(user);
		System.out.println("     =            insert username :test                    =");
		User user2 = new User(null, "test2", "$2a$10$C5EM7Y4NeBerGx8STduaxOBCAf4g9wdPBKSUErhTKS.lNOfziZnPe", "test@xxx.xx", "nickTest2", now, null, User.ENABLE, Arrays.asList(role, role1));
		userRepo.save(user2);
		ArticleType articleType1 = new ArticleType(null, "發問", ArticleType.ENABLE);
		articleTypeRepo.save(articleType1);
		System.out.println("     =            insert ArticleType :發問                  =");
		ArticleType articleType2 = new ArticleType(null, "徵才", ArticleType.ENABLE);
		articleTypeRepo.save(articleType2);
		System.out.println("     =            insert ArticleType :徵才                  =");

		System.out.println("     ============       insert completed        ============");
	}



}
