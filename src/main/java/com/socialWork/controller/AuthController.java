package com.socialWork.controller;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialWork.Util.JWTTokenUtils;
import com.socialWork.auth.dto.LoginDto;
import com.socialWork.auth.dto.RegisterDto;
import com.socialWork.auth.pojo.Role;
import com.socialWork.auth.pojo.User;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.AuthService;
import com.socialWork.config.WebSecurityConfig;
import com.socialWork.exceptions.LoginException;
import com.socialWork.exceptions.RegisterException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value="/auth")
@Api(tags = "登入註冊相關Controller")
@Slf4j
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTTokenUtils jwtTokenUtils;
	@Autowired
	private AuthService authService;
	@Autowired
	private ObjectMapper objectMapper;
	
	@ApiResponses(value = {@ApiResponse(code = 500, message = "Account not found")})
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String login(@RequestBody @Validated  LoginDto loginDto, HttpServletResponse httpResponse) throws Exception{
		log.info("user login: " + loginDto.getUsername());
	//	通過使用者名稱和密碼建立一個 Authentication 認證物件，實現類為 UsernamePasswordAuthenticationToken
	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
	if (Objects.nonNull(authenticationToken)){
		userRepository.findByUsername(authenticationToken.getPrincipal().toString())
		.orElseThrow(()->new LoginException("使用者不存在"));
	}
	try {
			//通過 AuthenticationManager（預設實現為ProviderManager）的authenticate方法驗證 Authentication 物件
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			//將 Authentication 繫結到 SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenUtils.createToken(authentication);
			httpResponse.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER,"Bearer " + token);
			Optional<User> user = userRepository.findByUsername(loginDto.getUsername());
			String nickname = user.isPresent()?user.get().getNickname():"";
			return objectMapper.writeValueAsString(LoginSuccessDto.of("Bearer " + token	, nickname));
		}catch (BadCredentialsException authentication){
			throw new LoginException("密碼錯誤");
		}
	}
	
	@RequestMapping(value = "/testLogin",method = RequestMethod.POST)
	public String testLogin(@RequestParam("username") String username,@RequestParam("password")  String password) {
		System.out.println("username: "+username);
		System.out.println("password: "+password);
		
		return "test success!";
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.POST)
	public String register(@RequestBody @Validated RegisterDto registerDto) throws Exception {
		authService.register(registerDto, Arrays.asList(Role.USER));
		return "success!";
	}

}

class LoginSuccessDto {
	public String token;
	public String usernickname;
	
	private LoginSuccessDto(String token, String usernickname) {
		this.token = token;
		this.usernickname = usernickname;
	}
	
	public static LoginSuccessDto of(String token, String usernickname) {
		return new LoginSuccessDto(token, usernickname);
	}
	@Override
	public String toString() {
		return "{ token = " + this.token + " usernickname = "+ this.usernickname + "}";
	}
}
