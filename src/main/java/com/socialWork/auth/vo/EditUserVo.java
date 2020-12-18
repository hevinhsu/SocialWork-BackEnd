package com.socialWork.auth.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "register/update User validate Dto")
public class EditUserDto {
	@ApiModelProperty(value = "Id")
	private Long userId;
	@NotBlank(message = "使用者名稱不能為空")
	@ApiModelProperty(value = "帳號", position = 1)
	@Size(min = 3, max = 15, message = "長度不正確")
	@Pattern(regexp="^[a-zA-Z0-9]{3,15}$", message = "內容有非法字元")
	private String username;
	@ApiModelProperty(value = "密碼", position = 2)
	@NotBlank(message = "密碼不能為空")
	@Size(min = 3, max = 15, message = "長度不正確")
	@Pattern(regexp="^[a-zA-Z0-9]{3,15}$", message = "內容有非法字元")
	private String password;
	@ApiModelProperty(value = "E-mail", position = 2)
	@Email
	@NotBlank(message = "email不可為空")
	private String email;
	@NotBlank(message = "暱稱不能為空")
	@Size(max = 15, message = "長度不正確")
	@Pattern(regexp="^[\u4e00-\u9fa5_a-zA-Z0-9]{1,15}$", message = "內容有非法字元")
	private String nickname;
}
