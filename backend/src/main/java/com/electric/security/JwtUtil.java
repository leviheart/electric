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
 * JWT 简介：
 * JWT (JSON Web Token) 是一种开放标准，用于在各方之间安全传输信息
 * 格式：Header.Payload.Signature
 * - Header: 算法和令牌类型
 * - Payload: 用户信息和过期时间
 * - Signature: 签名，用于验证令牌完整性
 * 
 * 主要功能：
 * 1. generateToken(): 生成 JWT Token
 * 2. extractUsername(): 从 Token 提取用户名
 * 3. validateToken(): 验证 Token 有效性
 * 4. extractExpiration(): 获取 Token 过期时间
 * 
 * 配置项（application.properties）：
 * - jwt.secret: 签名密钥（至少 256 位）
 * - jwt.expiration: Token 有效期（毫秒），默认 24 小时
 * 
 * 文件关联：
 * - AuthController: 登录时调用 generateToken 生成 Token
 * - JwtAuthenticationFilter: 请求时调用 validateToken 验证 Token
 * - AuthServiceImpl: 调用 extractUsername 获取用户信息
 * 
 * 新人提示：
 * - JWT Token 一旦签发，在过期前无法撤销
 * - 密钥必须保密，泄露后任何人都可以伪造 Token
 * - 生产环境应使用足够长的密钥（至少 32 字符）
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 获取签名密钥
     * 
     * 将配置的密钥字符串转换为 SecretKey 对象
     * 使用 HMAC-SHA 算法进行签名
     * 
     * @return SecretKey 签名密钥
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 从 Token 中提取用户名
     * 
     * 用户名存储在 Token 的 subject 字段中
     * 
     * @param token JWT Token
     * @return 用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从 Token 中提取过期时间
     * 
     * 用于判断 Token 是否已过期
     * 
     * @param token JWT Token
     * @return 过期时间
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 从 Token 中提取指定声明
     * 
     * 使用 Function 接口实现灵活的声明提取
     * 
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
     * 
     * 解析 Token 并验证签名
     * 如果签名无效或 Token 格式错误，会抛出异常
     * 
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
     * 
     * 比较过期时间与当前时间
     * 
     * @param token JWT Token
     * @return 是否过期
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 生成 Token
     * 
     * 使用默认声明生成 JWT Token
     * 
     * @param username 用户名（将作为 Token 的 subject）
     * @return JWT Token
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * 生成 Token（带额外声明）
     * 
     * 可以在 Token 中存储额外信息，如角色、权限等
     * 
     * @param username 用户名
     * @param claims 额外声明
     * @return JWT Token
     */
    public String generateToken(String username, Map<String, Object> claims) {
        return createToken(claims, username);
    }

    /**
     * 创建 Token
     * 
     * Token 结构：
     * - claims: 自定义声明
     * - subject: 用户名
     * - issuedAt: 签发时间
     * - expiration: 过期时间
     * - signature: 签名
     * 
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
     * 
     * 验证 Token 是否属于指定用户且未过期
     * 
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
     * 
     * 只验证 Token 格式和签名，不验证用户名
     * 用于过滤器中快速验证 Token 有效性
     * 
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
