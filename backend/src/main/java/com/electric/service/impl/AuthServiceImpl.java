package com.electric.service.impl;

import com.electric.dto.LoginRequest;
import com.electric.dto.LoginResponse;
import com.electric.exception.BusinessException;
import com.electric.model.User;
import com.electric.repository.UserRepository;
import com.electric.security.JwtUtil;
import com.electric.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * AuthServiceImpl - 认证服务实现类
 * 
 * 功能说明：
 * 实现用户登录、注册等认证功能
 * 使用 Spring Security 的 PasswordEncoder 进行密码加密
 * 使用 JwtUtil 生成和验证 Token
 * 
 * 文件关联：
 * - AuthService: 服务接口
 * - UserRepository: 用户数据访问
 * - JwtUtil: JWT 工具类
 * - PasswordEncoder: 密码加密器
 * - AuthController: 控制器调用此服务
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> BusinessException.badRequest("用户名或密码错误"));
        
        if (!user.getEnabled()) {
            throw BusinessException.badRequest("账户已被禁用");
        }
        
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw BusinessException.badRequest("用户名或密码错误");
        }
        
        String token = jwtUtil.generateToken(user.getUsername());
        
        return new LoginResponse(token, user.getUsername(), user.getRole(), jwtExpiration / 1000);
    }

    @Override
    public boolean register(LoginRequest loginRequest) {
        if (userRepository.existsByUsername(loginRequest.getUsername())) {
            throw BusinessException.conflict("用户名已存在");
        }
        
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        user.setRole("USER");
        user.setEnabled(true);
        
        userRepository.save(user);
        return true;
    }
}
