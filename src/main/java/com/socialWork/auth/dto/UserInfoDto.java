package com.socialWork.auth.dto;

import com.socialWork.auth.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class UserInfoDto {
	private Long id;
	private String email;
	private String nickname;
	private Boolean canEdit;
	
	private UserInfoDto(User user, boolean isView, boolean canEdit){
		this.id = isView? 0 : user.getUserId();
		this.email = user.getEmail();
		this.nickname = user.getNickname();
		this.canEdit = canEdit;
	}
	public static UserInfoDto of(User user) {
		return new UserInfoDto(user, false, false);
	}
	
	public static UserInfoDto of(User user, Long userId) {
		return new UserInfoDto(user, true, user.getUserId().equals(userId));
	}
}
