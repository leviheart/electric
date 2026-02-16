package com.electric.controller;

import com.electric.dto.LoginRequest;
import com.electric.dto.LoginResponse;
import com.electric.model.ApiResponse;
import com.electric.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController - 认证控制器
 * 
 * 功能说明：
 * 提供用户认证相关的 RESTful API 接口
 * 
 * API 接口：
 * - POST /api/auth/login: 用户登录
 * - POST /api/auth/register: 用户注册
 * 
 * 文件关联：
 * - AuthService: 认证服务
 * - LoginRequest: 登录请求DTO
 * - LoginResponse: 登录响应DTO
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录响应
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("登录成功", response));
    }

    /**
     * 用户注册
     * @param loginRequest 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody LoginRequest loginRequest) {
        authService.register(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("注册成功", null));
    }
}
