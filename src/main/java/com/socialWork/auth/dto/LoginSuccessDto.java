package com.socialWork.auth.dto;

import lombok.ToString;

@ToString
public class LoginSuccessDto {
	public final String jwtToken;
	public final Long userId;
	public final String usernickname;
	public final String username;

	private LoginSuccessDto(String jwtToken, Long userId, String username, String usernickanme) {
		this.jwtToken = "Bearer " + jwtToken;
		this.userId = userId;
		this.username = username;
		this.usernickname = usernickanme;
	}

	public static LoginSuccessDto of(String jwtToken, Long userId, String username, String usernickanme) {
		return new LoginSuccessDto(jwtToken, userId, username, usernickanme);
	}
}