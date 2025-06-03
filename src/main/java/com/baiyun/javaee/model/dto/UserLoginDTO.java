package com.baiyun.javaee.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户登录信息DTO
 */
@Data
public class UserLoginDTO {

    /**
     * 用户名或邮箱
     */
    @NotBlank(message = "用户名或邮箱不能为空")
    private String usernameOrEmail;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}
