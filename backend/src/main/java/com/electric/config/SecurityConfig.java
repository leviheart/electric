package com.electric.config;

import com.electric.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * SecurityConfig - Spring Security 安全配置
 * 
 * 功能说明：
 * 配置 Spring Security 的安全策略，保护 API 接口
 * 实现 JWT Token 认证机制
 * 
 * 主要配置内容：
 * 1. 密码加密器：使用 BCrypt 算法加密密码
 * 2. 安全过滤链：定义哪些 URL 需要认证
 * 3. CORS 配置：允许前端跨域请求
 * 4. JWT 过滤器：验证请求中的 Token
 * 
 * 安全策略说明：
 * - /api/auth/**: 登录注册接口，不需要认证
 * - /h2-console/**: H2 数据库控制台，不需要认证
 * - /api/**: 其他 API 接口，需要 JWT Token 认证
 * 
 * 认证流程：
 * 1. 用户登录获取 JWT Token
 * 2. 后续请求在 Header 中携带 Token
 * 3. JwtAuthenticationFilter 验证 Token
 * 4. 验证通过后允许访问受保护资源
 * 
 * 文件关联：
 * - JwtAuthenticationFilter: JWT 认证过滤器
 * - JwtUtil: JWT 工具类
 * - AuthController: 登录接口
 * 
 * 新人提示：
 * - Spring Security 默认保护所有接口
 * - 通过 requestMatchers 配置哪些接口不需要认证
 * - CORS 配置用于允许前端跨域请求
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * 构造函数依赖注入
     * @param jwtAuthenticationFilter JWT 认证过滤器
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * 密码加密器
     * 
     * 使用 BCrypt 算法加密密码
     * BCrypt 是一种安全的单向哈希算法，自动加盐
     * 
     * @return PasswordEncoder 密码加密器实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 安全过滤链配置
     * 
     * 定义 HTTP 请求的安全规则：
     * - 禁用 CSRF（前后端分离项目不需要）
     * - 配置 CORS 跨域
     * - 无状态会话（使用 JWT Token）
     * - 定义哪些 URL 需要认证
     * - 添加 JWT 过滤器
     * 
     * @param http HttpSecurity 配置对象
     * @return SecurityFilterChain 安全过滤链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF 保护（前后端分离项目使用 Token 认证）
                .csrf(AbstractHttpConfigurer::disable)
                // 配置 CORS 跨域
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 配置会话管理：无状态，不使用 Session
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 配置请求授权
                .authorizeHttpRequests(auth -> auth
                        // 登录注册接口不需要认证
                        .requestMatchers("/api/auth/**").permitAll()
                        // H2 控制台不需要认证（仅开发环境）
                        .requestMatchers("/h2-console/**").permitAll()
                        // 其他 API 接口需要认证
                        .requestMatchers("/api/**").authenticated()
                        // 其他请求允许访问
                        .anyRequest().permitAll()
                )
                // 允许 H2 控制台使用 iframe
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // 添加 JWT 过滤器，在用户名密码过滤器之前
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    /**
     * CORS 跨域配置
     * 
     * 允许前端应用跨域访问后端 API
     * 
     * 配置说明：
     * - allowedOrigins: 允许的来源域名（* 表示所有域名）
     * - allowedMethods: 允许的 HTTP 方法
     * - allowedHeaders: 允许的请求头
     * - exposedHeaders: 暴露给前端的响应头
     * 
     * @return CorsConfigurationSource CORS 配置源
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 允许所有来源（生产环境应限制具体域名）
        configuration.setAllowedOrigins(Arrays.asList("*"));
        // 允许的 HTTP 方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // 允许所有请求头
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // 暴露 Authorization 响应头给前端
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 对所有路径应用此 CORS 配置
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
