package com.socialWork.auth.dto;

import com.socialWork.auth.pojo.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String password;
	private String email;
	private String nickname;
	
	public UserDto(User user){
		this.id = user.getUserId();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
	}
}
