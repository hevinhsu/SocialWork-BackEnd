package com.socialWork.auth.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "login validate Dto")
public final class LoginVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3157279962216631429L;
	@NotBlank(message = "使用者名稱不能為空")
	@ApiModelProperty(value = "帳號", position = 1)
	private String username;
	@ApiModelProperty(value = "密碼", position = 2)
	@NotBlank(message = "密碼不能為空")
	private String password;
}
