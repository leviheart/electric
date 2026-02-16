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
 * 
 * 实体属性：
 * - id: 主键ID，自增生成
 * - username: 用户名（唯一，必填，长度3-50）
 * - password: 密码（加密存储，必填）
 * - role: 角色（ADMIN, USER等）
 * - enabled: 账户是否启用
 * 
 * 文件关联：
 * - 数据库表：对应数据库中的users表
 * - UserRepository: 用户数据访问接口
 * - AuthService: 认证服务使用此类
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String role = "USER";

    private Boolean enabled = true;

    public User() {}

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

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
