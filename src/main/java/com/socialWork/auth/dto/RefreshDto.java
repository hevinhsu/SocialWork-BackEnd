package com.socialWork.auth.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class RefreshDto {
	@NotBlank(message = "Null RefreshToken")
	@ApiModelProperty(value = "refresh token", position = 1)
	private String refreshToken;
	@NotNull
	@ApiModelProperty(value = "userId", position = 2)
	private Long userId;
}
