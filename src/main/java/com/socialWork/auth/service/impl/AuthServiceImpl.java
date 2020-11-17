package com.socialWork.auth.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.socialWork.auth.dto.EditUserDto;
import com.socialWork.auth.pojo.Role;
import com.socialWork.auth.pojo.User;
import com.socialWork.auth.repository.RoleRepository;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.AuthService;
import com.socialWork.exceptions.UserInfoException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepo;
	private Timestamp now = new Timestamp(System.currentTimeMillis());
	
	@Override
	@Transactional
	public void register(EditUserDto editUserDto, List<Long> roleIds) throws Exception {
		Optional<User> userOpt = userRepo.findByUsername(editUserDto.getUsername());
		if(userOpt.isPresent()) throw new UserInfoException("帳號重複");
		if(roleIds==null || roleIds.isEmpty()) {
			throw new Exception("無效的使用者權限");
		}
		List<Role> roleList = roleRepo.findAllById(roleIds);
		User user = User.builder().username(editUserDto.getUsername())
								  .password(passwordEncoder.encode(editUserDto.getPassword()))
								  .email(editUserDto.getEmail())
								  .nickname(editUserDto.getNickname())
								  .roles(roleList)
								  .createTime(now)
								  .build();
		userRepo.save(user);
		log.info(editUserDto.getNickname() + "create new account,username = "+editUserDto.getUsername());
	}
	
	@Override
	public User updateUserInfo(EditUserDto editUserDto) {
		User user = userRepo.findByUsername(editUserDto.getUsername()).orElseThrow(()-> new UserInfoException("查無此帳號"));
		if(user.getUserId().longValue() != editUserDto.getUserId()) throw new UserInfoException("Id不正確");
			user.setPassword(passwordEncoder.encode(editUserDto.getPassword()));
			user.setEmail(editUserDto.getEmail());
			user.setNickname(editUserDto.getNickname());
			user.setUpdateTime(now);
			userRepo.save(user);
			return user;
	}
	
	
}
