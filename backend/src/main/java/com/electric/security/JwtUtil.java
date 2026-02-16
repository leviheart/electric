package com.electric.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * JwtUtil - JWT 工具类
 * 
 * 功能说明：
 * 提供 JWT Token 的生成、解析和验证功能
 * 使用 JJWT 库实现 JWT 操作
 * 
 * 主要功能：
 * 1. 生成 JWT Token
 * 2. 解析 JWT Token
 * 3. 验证 Token 有效性
 * 4. 从 Token 中提取用户信息
 * 
 * 文件关联：
 * - AuthController: 调用此工具类生成 Token
 * - JwtAuthenticationFilter: 调用此工具类验证 Token
 * - application.properties: 读取 JWT 配置
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 获取签名密钥
     * @return SecretKey 签名密钥
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 从 Token 中提取用户名
     * @param token JWT Token
     * @return 用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从 Token 中提取过期时间
     * @param token JWT Token
     * @return 过期时间
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从 Token 中提取指定声明
     * @param token JWT Token
     * @param claimsResolver 声明解析函数
     * @return 声明值
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * 从 Token 中提取所有声明
     * @param token JWT Token
     * @return 所有声明
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 检查 Token 是否过期
     * @param token JWT Token
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 生成 Token
     * @param username 用户名
     * @return JWT Token
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * 生成 Token（带额外声明）
     * @param username 用户名
     * @param claims 额外声明
     * @return JWT Token
     */
    public String generateToken(String username, Map<String, Object> claims) {
        return createToken(claims, username);
    }

    /**
     * 创建 Token
     * @param claims 声明
     * @param subject 主题（通常是用户名）
     * @return JWT Token
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 验证 Token
     * @param token JWT Token
     * @param username 用户名
     * @return 是否有效
     */
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * 验证 Token 格式
     * @param token JWT Token
     * @return 是否有效
     */
    public Boolean validateTokenFormat(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
