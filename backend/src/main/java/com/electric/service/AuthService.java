package com.electric.service;

import com.electric.dto.LoginRequest;
import com.electric.dto.LoginResponse;
import com.electric.dto.UserInfo;

/**
 * AuthService - 认证服务接口
 * 
 * 功能说明：
 * 定义认证相关的业务操作
 * 
 * 文件关联：
 * - AuthServiceImpl: 服务实现类
 * - AuthController: 控制器调用此接口
 */
public interface AuthService {
    
    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @return 登录响应（包含Token）
     */
    LoginResponse login(LoginRequest loginRequest);
    
    /**
     * 用户注册
     * @param loginRequest 注册请求
     * @return 是否成功
     */
    boolean register(LoginRequest loginRequest);
    
    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserInfo getUserInfo(String username);
}
