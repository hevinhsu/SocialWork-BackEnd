package com.socialWork.auth.service;

import com.socialWork.auth.dto.UserInfoDto;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.vo.EditUserVo;

public interface UserService {

	UserInfoDto findUserInfo(String username, Long userId);
}