package com.socialWork.user.userDTO;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


    @Data
    public class LoginDTO implements Serializable {
        @NotBlank(message = "使用者名稱不能為空")
        private String username;
        @NotBlank(message = "密碼不能為空")
        private String password;
    }

