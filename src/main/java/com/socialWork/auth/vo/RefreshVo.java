package com.socialWork.auth.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
public class RefreshVo {
	@NotBlank(message = "Null RefreshToken")
	@ApiModelProperty(value = "refresh token", position = 1)
	private String refreshToken;
	@NotNull
	@ApiModelProperty(value = "userId", position = 2)
	private Long userId;
	@ApiModelProperty(value = "IP", position = 3)
	@Setter
	private String ip;
}
