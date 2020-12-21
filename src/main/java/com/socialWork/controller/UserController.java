package com.socialWork.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.socialWork.auth.dto.UserInfoDto;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.service.AuthService;
import com.socialWork.auth.service.UserService;
import com.socialWork.auth.vo.EditUserVo;
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
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/info/{username}/{userId}", method = RequestMethod.GET)
	@ApiOperation(notes = "不限身分，查詢使用者資訊", value = "")
	public String getUserInfo(@PathVariable("username") String username, @PathVariable("userId") Long userId)
			throws JsonProcessingException, UserInfoException {
		if(Objects.isNull(username) || username.trim().length()==0) throw new UserInfoException("使用者名稱不可為空");
		log.info("get user information: username = " + username + " userId = " + userId);
		UserInfoDto dto = userService.findUserInfo(username, userId);
		return objectMapper.writeValueAsString(dto);
	}

	@RequestMapping(value = "/saveEdit", method = RequestMethod.POST)
	@ApiOperation(notes = "編輯使用者資訊", value = "")
	public String saveEditBtn(@RequestBody @Validated EditUserVo editUserDto) throws JsonProcessingException {
		if (Objects.isNull(editUserDto.getUserId())) throw new UserInfoException("invalid params");
		User user = authService.updateUserInfo(editUserDto);
		return objectMapper.writeValueAsString(UserInfoDto.of(user));
	}
}

