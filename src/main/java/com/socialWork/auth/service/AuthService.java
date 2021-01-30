package com.socialWork.auth.service;

import java.util.List;

import com.socialWork.auth.dto.LoginSuccessDto;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.vo.EditUserVo;
import com.socialWork.auth.vo.LoginVo;
import com.socialWork.auth.vo.RefreshVo;

public interface AuthService {

	public void register(EditUserVo editUserDto, List<Long> roleIds) throws Exception;

	public User updateUserInfo(EditUserVo editUserDto);

	public LoginSuccessDto login(LoginVo loginDto) throws Exception;

	public LoginSuccessDto refresh(RefreshVo refreshDto);
	
	public String createRefreshToken(String ip);

	public void wrongPasswordBlackListCheck(String username);
}