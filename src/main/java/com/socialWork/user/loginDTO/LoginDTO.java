package com.socialWork.user.loginDTO;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3157279962216631429L;
	@NotBlank(message = "使用者名稱不能為空")
	private String username;
	@NotBlank(message = "密碼不能為空")
	private String password;
}
