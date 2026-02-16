package com.electric.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * User - 用户实体类
 * 
 * 功能说明：
 * 存储用户账户信息，用于身份认证和授权
 * 支持基于角色的访问控制（RBAC）
 * 
 * 数据库映射：
 * - 表名：users（使用 @Table 指定，避免使用 H2 保留字 "user"）
 * - 主键：id，自增生成
 * 
 * 实体属性：
 * ┌─────────────┬──────────┬────────────────────────────────┐
 * │ 属性名      │ 类型     │ 说明                            │
 * ├─────────────┼──────────┼────────────────────────────────┤
 * │ id          │ Long     │ 主键ID，数据库自动生成           │
 * │ username    │ String   │ 用户名，唯一，必填，3-50字符      │
 * │ password    │ String   │ 密码（BCrypt加密），必填          │
 * │ role        │ String   │ 角色（ADMIN/USER）               │
 * │ enabled     │ Boolean  │ 账户是否启用                     │
 * └─────────────┴──────────┴────────────────────────────────┘
 * 
 * 角色说明：
 * - ADMIN: 管理员，拥有所有权限
 * - USER: 普通用户，拥有基本权限
 * 
 * 安全说明：
 * - 密码使用 BCrypt 算法加密存储
 * - 明文密码不会存储到数据库
 * 
 * 文件关联：
 * - UserRepository: 数据访问接口
 * - AuthService: 认证服务使用此类
 * - SecurityConfig: 安全配置
 * 
 * 新人提示：
 * - @Table(name = "users") 指定表名，避免与数据库保留字冲突
 * - 密码加密在 AuthService 中处理，不在实体类中
 */
@Entity
@Table(name = "users")
public class User {
    
    /**
     * 主键ID
     * - 使用数据库自增策略生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     * - 必填字段，不能为空
     * - 长度限制：3-50个字符
     * - 用于登录认证
     * - 应该唯一（在注册时检查）
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之间")
    private String username;

    /**
     * 密码
     * - 必填字段
     * - 存储 BCrypt 加密后的密码
     * - 不存储明文密码
     * 
     * BCrypt 特点：
     * - 自动加盐
     * - 单向加密，不可逆
     * - 每次加密结果不同（包含随机盐）
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 角色
     * - 默认值：USER
     * - 可选值：ADMIN、USER
     * - 用于权限控制
     */
    private String role = "USER";

    /**
     * 账户是否启用
     * - 默认值：true
     * - false 表示账户被禁用，无法登录
     */
    private Boolean enabled = true;

    // ==================== 构造函数 ====================

    /**
     * 默认构造函数
     * JPA 要求实体类必须有无参构造函数
     */
    public User() {}

    /**
     * 带参数的构造函数
     * 用于快速创建用户对象（密码需要提前加密）
     * 
     * @param username 用户名
     * @param password 密码（已加密）
     * @param role 角色
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ==================== Getter 和 Setter 方法 ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
