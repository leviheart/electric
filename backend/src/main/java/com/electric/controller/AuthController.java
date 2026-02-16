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
 * 处理用户登录、注册等认证操作
 * 
 * RESTful API 接口：
 * - POST /api/auth/login: 用户登录，返回 JWT Token
 * - POST /api/auth/register: 用户注册，创建新账户
 * 
 * 认证流程：
 * 1. 用户提交用户名和密码
 * 2. 服务端验证凭证
 * 3. 验证成功后生成 JWT Token
 * 4. 客户端保存 Token，后续请求携带 Token
 * 
 * 文件关联：
 * - AuthService: 认证服务，处理登录/注册逻辑
 * - LoginRequest: 登录请求 DTO
 * - LoginResponse: 登录响应 DTO（包含 Token）
 * - JwtUtil: JWT 工具类，生成和验证 Token
 * 
 * 新人提示：
 * - 登录接口不需要认证（在 SecurityConfig 中配置）
 * - 密码使用 BCrypt 加密存储
 * - Token 有效期在 application.properties 中配置
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    /**
     * 构造函数依赖注入
     * @param authService 认证服务接口
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 用户登录
     * 
     * 请求示例：POST /api/auth/login
     * 请求体：{"username":"admin","password":"admin123"}
     * 响应示例：{"code":200,"message":"登录成功","data":{"token":"xxx","username":"admin","role":"ADMIN"}}
     * 
     * @param loginRequest 登录请求（包含用户名和密码）
     * @return 登录响应（包含 JWT Token 和用户信息）
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("登录成功", response));
    }

    /**
     * 用户注册
     * 
     * 请求示例：POST /api/auth/register
     * 请求体：{"username":"newuser","password":"password123"}
     * 响应示例：{"code":200,"message":"注册成功","data":null}
     * 
     * @param loginRequest 注册请求（包含用户名和密码）
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody LoginRequest loginRequest) {
        authService.register(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("注册成功", null));
    }
}
