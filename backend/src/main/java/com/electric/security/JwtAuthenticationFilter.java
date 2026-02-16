package com.electric.security;

import com.electric.model.User;
import com.electric.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JwtAuthenticationFilter - JWT 认证过滤器
 * 
 * 功能说明：
 * 拦截所有 HTTP 请求，验证 JWT Token
 * 如果 Token 有效，设置用户认证信息到 SecurityContext
 * 
 * 工作流程：
 * 1. 从请求头提取 Authorization Token
 * 2. 验证 Token 有效性
 * 3. 从 Token 提取用户信息
 * 4. 设置用户认证信息到 SecurityContext
 * 
 * 文件关联：
 * - JwtUtil: JWT 工具类
 * - UserRepository: 用户数据访问
 * - SecurityConfig: 安全配置中注册此过滤器
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = authHeader.substring(7);
        
        try {
            if (jwtUtil.validateTokenFormat(token)) {
                String username = jwtUtil.extractUsername(token);
                
                User user = userRepository.findByUsername(username).orElse(null);
                
                if (user != null && user.getEnabled()) {
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                            );
                    
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            logger.error("JWT认证失败: " + e.getMessage());
        }
        
        filterChain.doFilter(request, response);
    }
}
