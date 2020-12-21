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
import com.socialWork.auth.entity.Role;
import com.socialWork.auth.service.AuthService;
import com.socialWork.auth.vo.EditUserVo;
import com.socialWork.auth.vo.LoginVo;

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
	LoginVo loginVo;
	
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
		EditUserVo editUserVo = new EditUserVo(); 
		editUserVo.setUsername("testController");
		editUserVo.setPassword("testCon123");
		editUserVo.setEmail("test@xxx.xxx");
		editUserVo.setNickname("XXXX");
		authService.register(editUserVo, Arrays.asList(Role.USER));
		loginVo = new LoginVo();
		loginVo.setUsername("testController");
		loginVo.setPassword("testCon123");
		String loginUrl = "/auth/login";
		try{
			MvcResult result = mvc.perform(MockMvcRequestBuilders.post(loginUrl)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(loginVo))
					.accept(MediaType.APPLICATION_JSON)).andReturn();
			int status = result.getResponse().getStatus();
			Assertions.assertEquals(200, status, "success");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
