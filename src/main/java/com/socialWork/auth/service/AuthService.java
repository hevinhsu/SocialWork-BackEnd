package com.socialWork.auth.service;

import java.util.List;

import com.socialWork.auth.dto.RegisterDto;
import com.socialWork.auth.dto.UserDto;

public interface AuthService {

	public void register(RegisterDto registerDto, List<Long> roleIds) throws Exception;

	public UserDto findUserData(Long userId);

}