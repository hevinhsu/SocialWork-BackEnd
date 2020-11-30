package com.socialWork.socialWork;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialWork.auth.dto.EditUserDto;
import com.socialWork.auth.dto.LoginDto;
import com.socialWork.auth.entity.Role;
import com.socialWork.auth.service.AuthService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
class SocialWorkApplicationTests {

	@Autowired
    private WebApplicationContext webApplicationContext;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	AuthService authService;
	MockMvc mvc;
	LoginDto loginDto;
	
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
//	@Test
	void contextLoads() {
	}
	
	@Test
	@Transactional
	void testLogin() throws Exception {
		EditUserDto editUserDto = new EditUserDto(); 
		editUserDto.setUsername("testController");
		editUserDto.setPassword("testCon123");
		editUserDto.setEmail("test@xxx.xxx");
		editUserDto.setNickname("XXXX");
		authService.register(editUserDto, Arrays.asList(Role.USER));
		loginDto = new LoginDto();
		loginDto.setUsername("testController");
		loginDto.setPassword("testCon123");
		String loginUrl = "/auth/login";
		try{
			MvcResult result = mvc.perform(MockMvcRequestBuilders.post(loginUrl)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(loginDto))
					.accept(MediaType.APPLICATION_JSON)).andReturn();
			int status = result.getResponse().getStatus();
			Assertions.assertEquals(200, status, "success");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
