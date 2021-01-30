package com.socialWork.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.socialWork.auth.dto.UserInfoDto;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.UserService;
import com.socialWork.exceptions.UserInfoException;

@Service
public class UserServiceImpl implements UserService {

	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	private UserRepository userRepo;
	
	@Override
	@Transactional(readOnly = true)
	public UserInfoDto findUserInfo(String username, Long userId) {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UserInfoException("no match user"));
		return UserInfoDto.of(user, userId);
	}
}
