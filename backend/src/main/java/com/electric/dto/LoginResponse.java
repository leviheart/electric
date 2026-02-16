package com.electric.dto;

/**
 * LoginResponse - 登录响应DTO
 * 
 * 功能说明：
 * 封装登录成功后的响应数据
 * 包含 JWT Token 和用户基本信息
 * 
 * 文件关联：
 * - AuthController: 返回登录响应
 */
public class LoginResponse {
    
    private String token;
    private String username;
    private String role;
    private Long expiresIn;

    public LoginResponse() {}

    public LoginResponse(String token, String username, String role, Long expiresIn) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
