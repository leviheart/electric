package com.electric.security;

import com.electric.model.User;
import com.electric.model.Role;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtAuthenticationFilter - JWT 认证过滤器
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、什么是过滤器 (Filter)？
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * 过滤器是 Servlet 规范中的组件，位于客户端请求和服务器资源之间。
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 请求流程：                                                               │
 * │                                                                          │
 * │ 客户端 → Filter1 → Filter2 → ... → Controller → 响应                    │
 * │                                                                          │
 * │ 过滤器的作用：                                                           │
 * │ 1. 请求预处理：验证身份、记录日志、修改请求头                             │
 * │ 2. 响应后处理：添加响应头、压缩响应                                       │
 * │ 3. 可以中断请求链：直接返回错误响应                                       │
 * │                                                                          │
 * │ 过滤器链 (FilterChain)：                                                 │
 * │ - 多个过滤器按顺序执行                                                   │
 * │ - filterChain.doFilter() 调用下一个过滤器                                 │
 * │ - 如果不调用，请求被中断                                                 │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、核心注解
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * @Component
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 将此类注册为 Spring Bean                                                │
 * │ Spring 会自动创建实例并注入到 SecurityConfig 中                          │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * extends OncePerRequestFilter
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Spring 提供的过滤器基类，确保每个请求只过滤一次                            │
 * │                                                                          │
 * │ 为什么需要 OncePerRequestFilter？                                        │
 * │ - 普通 Filter 在请求转发(forward)时会多次执行                            │
 * │ - OncePerRequestFilter 保证每次 HTTP 请求只执行一次                       │
 * │                                                                          │
 * │ 需要实现的方法：                                                         │
 * │ - doFilterInternal(): 核心过滤逻辑                                       │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 三、JWT 认证流程
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 客户端请求流程：                                                         │
 * │                                                                          │
 * │ 1. 用户登录成功，服务器返回 JWT Token                                    │
 * │ 2. 客户端存储 Token（localStorage/sessionStorage）                       │
 * │ 3. 后续请求在 Header 中携带 Token：                                      │
 * │    Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...        │
 * │ 4. 服务器验证 Token 并提取用户信息                                        │
 * │                                                                          │
 * │ Token 格式：                                                             │
 * │ ┌─────────────────────────────────────────────────────────────────────┐ │
 * │ │ Header.Payload.Signature                                             │ │
 * │ │                                                                      │ │
 * │ │ Header:  {"alg":"HS256","typ":"JWT"}                                │ │
 * │ │ Payload: {"sub":"admin","exp":1234567890}                           │ │
 * │ │ Signature: HMACSHA256(base64(Header)+"."+base64(Payload), secret)   │ │
 * │ └─────────────────────────────────────────────────────────────────────┘ │
 * │                                                                          │
 * │ 认证流程图：                                                             │
 * │                                                                          │
 * │ 请求 → 提取Token → 验证Token → 查询用户 → 设置SecurityContext → 继续    │
 * │   ↓         ↓           ↓           ↓              ↓                    │
 * │ 无Token  Token无效  用户不存在  用户禁用    认证成功                      │
 * │   ↓         ↓           ↓           ↓              ↓                    │
 * │ 继续     继续        继续       继续         有权限访问                   │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 四、Spring Security 核心概念
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * SecurityContext
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Spring Security 的安全上下文，存储当前用户的认证信息                       │
 * │                                                                          │
 * │ 获取方式：                                                               │
 * │ SecurityContextHolder.getContext().getAuthentication()                  │
 * │                                                                          │
 * │ 在任何地方都可以获取当前登录用户：                                        │
 * │ Authentication auth = SecurityContextHolder.getContext().getAuthentication(); │
 * │ String username = auth.getName(); // 当前用户名                          │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * UsernamePasswordAuthenticationToken
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Spring Security 的标准认证令牌                                           │
 * │                                                                          │
 * │ 构造参数：                                                               │
 * │ 1. principal: 用户标识（通常是用户名）                                   │
 * │ 2. credentials: 凭证（密码，已验证后设为 null）                          │
 * │ 3. authorities: 权限列表                                                 │
 * │                                                                          │
 * │ 权限格式：ROLE_XXX                                                       │
 * │ - ROLE_ADMIN: 管理员                                                    │
 * │ - ROLE_USER: 普通用户                                                   │
 * │ - ROLE_VIEWER: 只读用户                                                 │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * SimpleGrantedAuthority
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 权限对象，表示一个授权                                                   │
 * │                                                                          │
 * │ 创建方式：                                                               │
 * │ new SimpleGrantedAuthority("ROLE_ADMIN")                                │
 * │                                                                          │
 * │ 在 Controller 中检查权限：                                              │
 * │ @PreAuthorize("hasRole('ADMIN')")                                       │
 * │ 或                                                                       │
 * │ if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    /**
     * 核心过滤方法
     * 
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @param filterChain 过滤器链，用于调用下一个过滤器
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        // ═══════════════════════════════════════════════════════════════════
        // 步骤 1：从请求头获取 Authorization 字段
        // ═══════════════════════════════════════════════════════════════════
        // 格式：Authorization: Bearer <token>
        // 例如：Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
        String authHeader = request.getHeader("Authorization");
        
        // 如果没有 Token 或格式不对，直接放行
        // 后续 SecurityConfig 会根据配置决定是否允许访问
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // 提取 Token（去掉 "Bearer " 前缀，共 7 个字符）
        String token = authHeader.substring(7);
        
        try {
            // ═══════════════════════════════════════════════════════════════
            // 步骤 2：验证 Token 并提取用户信息
            // ═══════════════════════════════════════════════════════════════
            if (jwtUtil.validateTokenFormat(token)) {
                // 从 Token 中提取用户名
                String username = jwtUtil.extractUsername(token);
                
                // 查询用户信息
                User user = userRepository.findByUsername(username).orElse(null);
                
                // ═══════════════════════════════════════════════════════════
                // 步骤 3：设置认证信息
                // ═══════════════════════════════════════════════════════════
                if (user != null && user.getEnabled()) {
                    // 构建权限列表
                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    
                    if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                        // 从用户角色构建权限
                        // Role.ADMIN → ROLE_ADMIN
                        authorities = user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                                .collect(Collectors.toList());
                    } else {
                        // 没有角色，赋予默认用户权限
                        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                    }
                    
                    // 创建认证令牌
                    UsernamePasswordAuthenticationToken authentication = 
                            new UsernamePasswordAuthenticationToken(
                                    username,    // principal: 用户标识
                                    null,        // credentials: 密码（已验证，设为 null）
                                    authorities  // authorities: 权限列表
                            );
                    
                    // 设置到 SecurityContext
                    // 后续可以通过 SecurityContextHolder.getContext().getAuthentication() 获取
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // Token 验证失败，清除安全上下文，确保不会有残留的认证信息
            logger.error("JWT认证失败: " + e.getMessage());
            SecurityContextHolder.clearContext();
        }
        
        // ═══════════════════════════════════════════════════════════════════
        // 步骤 4：继续执行后续过滤器和 Controller
        // ═══════════════════════════════════════════════════════════════════
        // 无论认证成功与否，都要调用 filterChain.doFilter()
        // 否则请求会被中断
        filterChain.doFilter(request, response);
    }
}
