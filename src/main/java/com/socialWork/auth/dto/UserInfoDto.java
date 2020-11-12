package com.socialWork.auth.dto;

import com.socialWork.auth.pojo.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserInfoDto {
	private Long id;
	private String email;
	private String nickname;
	
	private UserInfoDto(User user, boolean isView){
		this.id = isView? 0 : user.getUserId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
	}
	public static UserInfoDto of(User user, boolean isView) {
		return new UserInfoDto(user, isView);
	}
}
