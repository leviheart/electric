package com.electric.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Role - 角色实体类
 * 
 * 功能说明：
 * 定义系统角色，用于权限控制
 * 采用 RBAC（基于角色的访问控制）模型
 * 
 * RBAC 模型说明：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 用户 ──────► 角色 ──────► 权限                                  │
 * │                                                                 │
 * │ 示例：                                                          │
 * │ admin 用户 ──► 管理员角色 ──► 所有权限                          │
 * │ operator 用户 ──► 运维角色 ──► 查看、编辑权限                    │
 * │ viewer 用户 ──► 只读角色 ──► 查看权限                            │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 预置角色：
 * ┌─────────────────┬────────────────────────────────┐
 * │ 角色名          │ 说明                            │
 * ├─────────────────┼────────────────────────────────┤
 * │ ADMIN           │ 管理员：拥有所有权限            │
 * │ OPERATOR        │ 运维人员：查看、编辑、告警处理  │
 * │ VIEWER          │ 只读用户：仅查看                │
 * └─────────────────┴────────────────────────────────┘
 * 
 * 文件关联：
 * - User: 用户实体，与角色多对多关系
 * - Permission: 权限实体
 * - SecurityConfig: Spring Security 配置
 */
@Entity
@Table(name = "roles")
public class Role {
    
    /**
     * 角色ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 角色名称
     * - ADMIN: 管理员
     * - OPERATOR: 运维人员
     * - VIEWER: 只读用户
     */
    @Column(unique = true, nullable = false)
    private String name;
    
    /**
     * 角色描述
     */
    private String description;
    
    /**
     * 角色拥有的权限列表
     * - 使用 Set 避免重复
     * - 多对多关系
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_permissions",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    // ==================== 构造函数 ====================

    public Role() {}

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // ==================== Getter 和 Setter 方法 ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
