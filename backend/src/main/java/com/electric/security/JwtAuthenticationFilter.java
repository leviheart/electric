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
 * 过滤器执行流程：
 * 1. 从请求头提取 Authorization Token
 * 2. 验证 Token 有效性（签名、过期时间）
 * 3. 从 Token 提取用户名
 * 4. 查询用户信息
 * 5. 设置用户认证信息到 SecurityContext
 * 6. 继续执行后续过滤器和控制器
 * 
 * Token 格式：
 * 请求头：Authorization: Bearer <token>
 * 
 * 认证信息存储：
 * - SecurityContext: Spring Security 的安全上下文
 * - Authentication: 包含用户名、权限等信息
 * 
 * 文件关联：
 * - JwtUtil: JWT 工具类，用于验证和解析 Token
 * - UserRepository: 查询用户信息
 * - SecurityConfig: 注册此过滤器到过滤链
 * 
 * 新人提示：
 * - OncePerRequestFilter 确保每个请求只过滤一次
 * - SecurityContextHolder 存储当前用户的认证信息
 * - 如果 Token 无效，过滤器不会阻止请求，只是不设置认证信息
 *   后续 SecurityConfig 会根据配置决定是否允许访问
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    /**
     * 构造函数依赖注入
     * @param jwtUtil JWT 工具类
     * @param userRepository 用户数据访问接口
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    /**
     * 过滤器核心方法
     * 
     * 执行流程：
     * 1. 检查请求头是否包含 Authorization
     * 2. 提取 Bearer Token
     * 3. 验证 Token 并提取用户信息
     * 4. 设置认证信息到 SecurityContext
     * 
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param filterChain 过滤器链
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // 从请求头获取 Authorization 字段
        String authHeader = request.getHeader("Authorization");
        
        // 检查是否有 Token（格式：Bearer <token>）
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 没有 Token，继续执行后续过滤器
            // 后续 SecurityConfig 会根据配置决定是否允许访问
            filterChain.doFilter(request, response);
            return;
        }
        
        // 提取 Token（去掉 "Bearer " 前缀）
        String token = authHeader.substring(7);
        
        try {
            // 验证 Token 格式和过期时间
            if (jwtUtil.validateTokenFormat(token)) {
                // 从 Token 提取用户名
                String username = jwtUtil.extractUsername(token);
                
                // 查询用户信息
                User user = userRepository.findByUsername(username).orElse(null);
                
                // 如果用户存在且已启用，设置认证信息
                if (user != null && user.getEnabled()) {
                    // 创建认证对象
                    // - principal: 用户名
                    // - credentials: 密码（Token 认证不需要，设为 null）
                    // - authorities: 权限列表（从用户角色构建）
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    null,
                                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
                            );
                    
                    // 将认证信息存入 SecurityContext
                    // 后续可以通过 SecurityContextHolder.getContext().getAuthentication() 获取
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // Token 验证失败，记录错误但不阻止请求
            logger.error("JWT认证失败: " + e.getMessage());
        }
        
        // 继续执行后续过滤器和控制器
        filterChain.doFilter(request, response);
    }
}
