package com.socialWork.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.socialWork.Util.AccessAddressUtils;
import com.socialWork.auth.dto.LoginSuccessDto;
import com.socialWork.auth.entity.Role;
import com.socialWork.auth.service.AuthService;
import com.socialWork.auth.vo.EditUserVo;
import com.socialWork.auth.vo.LoginVo;
import com.socialWork.auth.vo.RefreshVo;
import com.socialWork.config.WebSecurityConfig;
import com.socialWork.exceptions.LoginException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/auth")
@Api(tags = "登入註冊相關Controller")
@Slf4j
public class AuthController extends BaseController {
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	private AuthService authService;

	@ApiResponses(value = { @ApiResponse(code = 500, message = "Account not found") })
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(notes = "登入", value = "")
	public String login(@RequestBody @Validated LoginVo loginVo, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
		try {
			loginVo.setIp(AccessAddressUtils.getIpAddress(request));
			LoginSuccessDto loginSuccessDto = authService.login(loginVo);
			httpResponse.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER, loginSuccessDto.jwtToken);
			return objectMapper.writeValueAsString(loginSuccessDto);
		} catch (BadCredentialsException authentication) {
			authService.wrongPasswordBlackListCheck(loginVo.getUsername());
			throw new LoginException("密碼錯誤");
		}
	}

	@RequestMapping(value = "/testLogin", method = RequestMethod.POST)
	public String testLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println("username: " + username);
		System.out.println("password: " + password);

		return "test success!";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(notes = "註冊", value = "")
	public String register(@RequestBody @Validated EditUserVo editUserDto) throws Exception {
		authService.register(editUserDto, Arrays.asList(Role.USER));
		return "success!";
	}
	
	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	@ApiOperation(notes = "用refresh token 重新登入", value = "")
	public String refresh(@RequestBody @Validated RefreshVo refreshVo, HttpServletRequest request) throws JsonProcessingException {
		refreshVo.setIp(AccessAddressUtils.getIpAddress(request));
		LoginSuccessDto loginSuccessDto = authService.refresh(refreshVo);
		return objectMapper.writeValueAsString(loginSuccessDto);
	}
}