package com.socialWork.auth.service;

import com.socialWork.auth.dto.UserInfoDto;

public interface UserService {

	UserInfoDto findUserInfo(String username, Long userId);

}