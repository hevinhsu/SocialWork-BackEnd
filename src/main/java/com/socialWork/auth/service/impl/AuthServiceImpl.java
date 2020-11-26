package com.socialWork.auth.service.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.socialWork.Util.JWTTokenUtils;
import com.socialWork.auth.dto.EditUserDto;
import com.socialWork.auth.dto.LoginDto;
import com.socialWork.auth.dto.LoginSuccessDto;
import com.socialWork.auth.dto.RefreshDto;
import com.socialWork.auth.entity.RefreshToken;
import com.socialWork.auth.entity.Role;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.repository.RefreshTokenRepository;
import com.socialWork.auth.repository.RoleRepository;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.AuthService;
import com.socialWork.exceptions.LoginException;
import com.socialWork.exceptions.UserInfoException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTTokenUtils tokenProvider;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private RefreshTokenRepository refreshTokenRepo;
	private Timestamp now = new Timestamp(System.currentTimeMillis());
	@Value("${token.timeout.refresh}")
	private long EXPIRATIONTIME;  
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
		User user = userRepo.findByUsername(editUserDto.getUsername())
				.orElseThrow(()-> new UserInfoException("查無此帳號"));
		if(user.getUserId().longValue() != editUserDto.getUserId()) throw new UserInfoException("Id不正確");
		
		user.setPassword(passwordEncoder.encode(editUserDto.getPassword()));
		user.setEmail(editUserDto.getEmail());
		user.setNickname(editUserDto.getNickname());
		user.setUpdateTime(now);
		userRepo.save(user);
		return user;
	}

	@Override
	@Transactional
	public LoginSuccessDto login(LoginDto loginDto) throws Exception{
		// TODO Auto-generated method stub
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		if (Objects.isNull(authenticationToken)) return null;
		
		User user = userRepo.findByUsername(authenticationToken.getPrincipal().toString())
				.orElseThrow(() -> new LoginException("使用者不存在"));
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtTokenString = tokenProvider.createToken(authentication);
		return LoginSuccessDto.of(jwtTokenString, user.getUserId(), user.getUsername(), user.getNickname());
	}

	@Override
	public String createRefreshToken() {
		String token = JWTTokenUtils.createRefreshToken();
		Timestamp expiredTime = new Timestamp(System.currentTimeMillis()+EXPIRATIONTIME*60*1000);
		refreshTokenRepo.save(new RefreshToken(token, expiredTime));
		return token;
	}
	
	@Transactional
	@Override
	public LoginSuccessDto refresh(RefreshDto refreshDto) {
		RefreshToken refreshToken = refreshTokenRepo.findByToken(refreshDto.getRefreshToken())
				.orElseThrow(() -> new LoginException("refresh token 不存在"));
		if(refreshToken.getExpiredTime().before(now)) throw new LoginException("refresh token time out");
		refreshTokenRepo.delete(refreshToken);
		
		User user = userRepo.findById(refreshDto.getUserId())
							.orElseThrow( ()->new LoginException("使用者不存在"));
		Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) user.getRoles().stream().map(list->list.getRoleName()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				user.getUsername(), "", authorities);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		String jwtTokenString = tokenProvider.createToken(authenticationToken);
		return LoginSuccessDto.of(jwtTokenString, user.getUserId(), user.getUsername(), user.getNickname());
	}
	
}
