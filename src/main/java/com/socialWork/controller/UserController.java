package com.socialWork.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.socialWork.auth.dto.EditUserDto;
import com.socialWork.auth.dto.UserInfoDto;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.service.AuthService;
import com.socialWork.exceptions.UserInfoException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "會員資料相關Controller")
@Slf4j
public class UserController extends BaseController {
	@Autowired
	private AuthService authService;

	@RequestMapping(value = "/info/{username}", method = RequestMethod.GET)
	@ApiOperation(notes = "不限身分，查詢使用者資訊", value = "")
	public String getUserInfo(@PathVariable("username") String username)
			throws JsonProcessingException, UserInfoException {
		log.info("get user information: username = " + username);
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserInfoException("no match user"));
		return objectMapper.writeValueAsString(UserInfoDto.of(user, true));
	}

	@RequestMapping(value = "/showEditBtn", method = RequestMethod.GET)
	@ApiOperation(notes = "能編輯使用者資訊權限查詢", value = "")
	public Boolean showEditBtn(@RequestParam String username,
			@RequestParam(required = false, name = "userId") Long id) {
		log.info("search sohw edit btn. username = " + username + ", id = " + id);
		if (Objects.isNull(id))
			return false;
		return userRepository.findByUsername(username).map(User::getUserId).filter(userId -> userId.equals(id))
				.isPresent();
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	@ApiOperation(notes = "編輯使用者資訊", value = "")
	public String saveEditBtn(@RequestBody @Validated EditUserDto editUserDto) throws JsonProcessingException {
		if (Objects.isNull(editUserDto.getUserId())) throw new UserInfoException("invalid params");
		User user = authService.updateUserInfo(editUserDto);
		return objectMapper.writeValueAsString(UserInfoDto.of(user, false));
	}
}

