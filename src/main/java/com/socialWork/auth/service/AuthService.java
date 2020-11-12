package com.socialWork.auth.service;

import java.util.List;

import com.socialWork.auth.dto.EditUserDto;
import com.socialWork.auth.pojo.User;

public interface AuthService {

	public void register(EditUserDto editUserDto, List<Long> roleIds) throws Exception;

	public User updateUserInfo(EditUserDto editUserDto);

}