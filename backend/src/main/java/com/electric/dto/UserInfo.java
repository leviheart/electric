package com.electric.dto;

/**
 * UserInfo - 用户信息响应 DTO
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 什么是 DTO (Data Transfer Object)？
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * DTO 是数据传输对象，用于在不同层之间传递数据。
 * 
 * 为什么需要 DTO？
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 1. 解耦：API 响应格式与数据库实体分离                                     │
 * │ 2. 安全：避免暴露敏感字段（如密码）                                       │
 * │ 3. 灵活：可以组合多个实体的数据                                           │
 * │ 4. 文档：清晰的 API 契约                                                  │
 * │                                                                          │
 * │ 例如：User 实体包含 password 字段，但 UserInfo 不应该包含密码             │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
public class UserInfo {
    
    private Long id;
    private String username;
    private String role;
    private boolean enabled;
    
    public UserInfo() {}
    
    public UserInfo(Long id, String username, String role, boolean enabled) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.enabled = enabled;
    }
    
    public UserInfo(String username, String role, boolean enabled) {
        this.username = username;
        this.role = role;
        this.enabled = enabled;
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
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
