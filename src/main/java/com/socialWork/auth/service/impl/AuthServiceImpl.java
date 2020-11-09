package com.socialWork.auth.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.socialWork.auth.dto.RegisterDto;
import com.socialWork.auth.dto.UserDto;
import com.socialWork.auth.pojo.Role;
import com.socialWork.auth.pojo.User;
import com.socialWork.auth.repository.RoleRepository;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.AuthService;
import com.socialWork.exceptions.RegisterException;

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
	@Override
	@Transactional
	public void register(RegisterDto registerDto, List<Long> roleIds) throws Exception {
		Optional<User> userOpt = userRepo.findByUsername(registerDto.getUsername());
		if(userOpt.isPresent()) throw new RegisterException("帳號重複");
		if(roleIds==null || roleIds.isEmpty()) {
			throw new Exception("無效的使用者權限");
		}
		List<Role> roleList = roleRepo.findAllById(roleIds);
		User user = User.builder().username(registerDto.getUsername())
								  .password(passwordEncoder.encode(registerDto.getPassword()))
								  .email(registerDto.getEmail())
								  .nickname(registerDto.getNickname())
								  .roles(roleList)
								  .build();
		userRepo.save(user);
		log.info(registerDto.getNickname() + "create new account,username = "+registerDto.getUsername());
	}
	
	@Override
	@Transactional
	public UserDto findUserData(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new NullPointerException("查無此帳號"));
		return new UserDto(user);
	}
}
