package com.socialWork.socialWork.auth.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.socialWork.auth.dto.RegisterDto;
import com.socialWork.auth.pojo.Role;
import com.socialWork.auth.pojo.User;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.AuthService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
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
		RegisterDto registerDto = new RegisterDto();
		registerDto.setUsername(username);
		registerDto.setPassword(password);
		registerDto.setEmail(email);
		registerDto.setNickname(nickname);
		authService.register(registerDto, Arrays.asList(Role.USER));
		User user = userRepo.findByUsername(username).orElse(null);
		assertEquals(username, user.getUsername());
		assertEquals(true, passwordEncoder.matches(password, user.getPassword()));
		assertEquals(nickname, user.getNickname());
		assertEquals(email, user.getEmail());
	}
	
}
