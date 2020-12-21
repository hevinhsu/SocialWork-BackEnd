package com.socialWork.auth.service.impl;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.transaction.annotation.Transactional;

import com.socialWork.Util.JWTTokenUtils;
import com.socialWork.Util.RedisUtils;
import com.socialWork.auth.dto.LoginSuccessDto;
import com.socialWork.auth.entity.Role;
import com.socialWork.auth.entity.User;
import com.socialWork.auth.repository.RoleRepository;
import com.socialWork.auth.repository.UserRepository;
import com.socialWork.auth.service.AuthService;
import com.socialWork.auth.vo.EditUserVo;
import com.socialWork.auth.vo.LoginVo;
import com.socialWork.auth.vo.RefreshVo;
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
	private Timestamp now = new Timestamp(System.currentTimeMillis());
	@Value("${token.timeout.refresh}")
	private long EXPIRATIONTIME;  
	
	@Override
	@Transactional(readOnly = true)
	public void register(EditUserVo editUserVo, List<Long> roleIds) throws Exception {
		Optional<User> userOpt = userRepo.findByUsername(editUserVo.getUsername());
		if(userOpt.isPresent()) throw new UserInfoException("帳號重複");
		if(roleIds==null || roleIds.isEmpty()) {
			throw new Exception("無效的使用者權限");
		}
		List<Role> roleList = roleRepo.findAllById(roleIds);
		User user = User.builder().username(editUserVo.getUsername())
								  .password(passwordEncoder.encode(editUserVo.getPassword()))
								  .email(editUserVo.getEmail())
								  .nickname(editUserVo.getNickname())
								  .roles(roleList)
								  .createTime(now)
								  .build();
		userRepo.save(user);
		log.info(editUserVo.getNickname() + "create new account,username = "+editUserVo.getUsername());
	}
	
	@Override
	@Transactional
	public User updateUserInfo(EditUserVo editUserVo) {
		User user = userRepo.findByUsername(editUserVo.getUsername())
				.orElseThrow(()-> new UserInfoException("查無此帳號"));
		if(user.getUserId().longValue() != editUserVo.getUserId()) throw new UserInfoException("Id不正確");
		
		user.setPassword(passwordEncoder.encode(editUserVo.getPassword()));
		user.setEmail(editUserVo.getEmail());
		user.setNickname(editUserVo.getNickname());
		user.setUpdateTime(now);
		userRepo.save(user);
		return user;
	}

	@Override
	@Transactional
	public LoginSuccessDto login(LoginVo loginVo) throws Exception{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginVo.getUsername(), loginVo.getPassword());
		if (Objects.isNull(authenticationToken)) return null;
		
		User user = userRepo.findByUsername(authenticationToken.getPrincipal().toString())
				.orElseThrow(() -> new LoginException("使用者不存在"));
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtTokenString = tokenProvider.createToken(authentication);
		return LoginSuccessDto.of(jwtTokenString, user.getUserId(), user.getUsername(), user.getNickname());
	}

	@Override
	public String createRefreshToken(String ip) {
		String token = JWTTokenUtils.createRefreshToken();
		int expiredTime = (int) (System.currentTimeMillis()+EXPIRATIONTIME*60*1000);
		RedisUtils.hset(token, "ip", ip, expiredTime);
		return token;
	}
	
	@Override
	@Transactional(readOnly = true)
	public LoginSuccessDto refresh(RefreshVo refreshVo) {
		String token = refreshVo.getRefreshToken();
		if(!RedisUtils.hasKey(token)) throw new LoginException("refresh token 不存在");
		if(!RedisUtils.hget(token, "ip").toString().equals(refreshVo.getIp())) throw new LoginException("ip 不正確");
		System.out.println("for test: "+RedisUtils.hasKey(refreshVo.getRefreshToken()));
		User user = userRepo.findById(refreshVo.getUserId())
							.orElseThrow( ()->new LoginException("使用者不存在"));
		Collection<? extends GrantedAuthority> authorities = 
							(Collection<? extends GrantedAuthority>) user.getRoles()
							.stream()
							.map(list->list.getRoleName())
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList());
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				user.getUsername(), "", authorities);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		String jwtTokenString = tokenProvider.createToken(authenticationToken);
		return LoginSuccessDto.of(jwtTokenString, user.getUserId(), user.getUsername(), user.getNickname());
	}
	
}
