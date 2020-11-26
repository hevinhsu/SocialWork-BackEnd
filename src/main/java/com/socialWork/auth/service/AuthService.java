package com.socialWork.auth.service;

import java.util.List;

import com.socialWork.auth.dto.EditUserDto;
import com.socialWork.auth.dto.LoginDto;
import com.socialWork.auth.dto.LoginSuccessDto;
import com.socialWork.auth.dto.RefreshDto;
import com.socialWork.auth.entity.User;

public interface AuthService {

	public void register(EditUserDto editUserDto, List<Long> roleIds) throws Exception;

	public User updateUserInfo(EditUserDto editUserDto);

	public LoginSuccessDto login(LoginDto loginDto) throws Exception;

	public LoginSuccessDto refresh(RefreshDto refreshDto);
	
	public String createRefreshToken();

}