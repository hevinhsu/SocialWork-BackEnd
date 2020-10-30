package com.socialWork.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.socialWork.Util.JWTTokenUtils;
import com.socialWork.config.WebSecurityConfig;
import com.socialWork.user.loginDTO.LoginDTO;
import com.socialWork.user.repository.UserRepository;

@RestController
@RequestMapping(value="/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTTokenUtils jwtTokenUtils;
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String login(@Valid LoginDTO loginDTO, BindingResult br, HttpServletResponse httpResponse) throws Exception{
//	通過使用者名稱和密碼建立一個 Authentication 認證物件，實現類為 UsernamePasswordAuthenticationToken
	UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),loginDTO.getPassword());
	if (Objects.nonNull(authenticationToken)){
		userRepository.findByUsername(authenticationToken.getPrincipal().toString())
		.orElseThrow(()->new Exception("使用者不存在"));
	}
	try {
			//通過 AuthenticationManager（預設實現為ProviderManager）的authenticate方法驗證 Authentication 物件
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			//將 Authentication 繫結到 SecurityContext
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenUtils.createToken(authentication);
			httpResponse.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER,"Bearer " + token);
			return "Bearer " + token;
		}catch (BadCredentialsException authentication){
			throw new Exception("密碼錯誤");
		}
	}

}
