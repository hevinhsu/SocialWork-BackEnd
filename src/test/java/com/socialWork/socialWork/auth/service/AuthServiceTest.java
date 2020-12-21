package com.socialWork.socialWork.auth.service;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.socialWork.auth.entity.Role;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.AuthService;
import com.socialWork.auth.vo.EditUserVo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceTest {
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private String username = "testUsername";
	private String password = "testPassword";
	private String email = "email@xxxx.xxx";
	private String nickname = "testNickname";
	
	@Test
	@Transactional
	public void testRegiser() throws Exception {
		EditUserVo editUserVo = new EditUserVo();
		editUserVo.setUsername(username);
		editUserVo.setPassword(password);
		editUserVo.setEmail(email);
		editUserVo.setNickname(nickname);
		authService.register(editUserVo, Arrays.asList(Role.USER));
		User user = userRepo.findByUsername(username).orElse(null);
		Assertions.assertEquals(username, user.getUsername());
		Assertions.assertEquals(true, passwordEncoder.matches(password, user.getPassword()));
		Assertions.assertEquals(nickname, user.getNickname());
		Assertions.assertEquals(email, user.getEmail());
	}
}
