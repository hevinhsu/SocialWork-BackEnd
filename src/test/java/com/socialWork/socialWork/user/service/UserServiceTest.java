package com.socialWork.socialWork.user.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.socialWork.auth.dto.UserInfoDto;
import com.socialWork.auth.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void findUserInfo() {
		String username = "test";
		Long userId = 1L;
		UserInfoDto dto = userService.findUserInfo(username, userId);
	    Assertions.assertEquals("nickTest", dto.getNickname(), "test nickname");

	}
}
