package com.electric.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * LoginRequest - 登录请求DTO
 * 
 * 功能说明：
 * 封装用户登录请求参数
 * 使用 Jakarta Validation 进行参数校验
 * 
 * 文件关联：
 * - AuthController: 接收登录请求
 */
public class LoginRequest {
    
    @NotBlank(message = "用户名不能为空")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
