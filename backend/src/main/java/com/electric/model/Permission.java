package com.electric.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Permission - 权限实体类
 * 
 * 功能说明：
 * 定义系统权限，用于细粒度的访问控制
 * 权限格式：资源:操作（如 substation:read, substation:write）
 * 
 * 权限分类：
 * ┌─────────────────┬────────────────────────────────┐
 * │ 权限            │ 说明                            │
 * ├─────────────────┼────────────────────────────────┤
 * │ *:*             │ 所有权限（管理员）              │
 * │ substation:*    │ 变电站所有操作                  │
 * │ substation:read │ 查看变电站                      │
 * │ substation:write│ 编辑变电站                      │
 * │ substation:delete│ 删除变电站                     │
 * │ line:*          │ 线路所有操作                    │
 * │ area:*          │ 台区所有操作                    │
 * │ alert:*         │ 告警所有操作                    │
 * │ alert:ack       │ 确认告警                        │
 * │ alert:resolve   │ 解决告警                        │
 * └─────────────────┴────────────────────────────────┘
 * 
 * 文件关联：
 * - Role: 角色实体，与权限多对多关系
 * - SecurityConfig: Spring Security 配置
 */
@Entity
@Table(name = "permissions")
public class Permission {
    
    /**
     * 权限ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 权限名称
     * 格式：资源:操作
     * 示例：substation:read, substation:write
     */
    @Column(unique = true, nullable = false)
    private String name;
    
    /**
     * 权限描述
     */
    private String description;
    
    /**
     * 权限所属资源
     * - substation: 变电站
     * - line: 输电线路
     * - area: 台区
     * - alert: 告警
     * - user: 用户
     * - *: 所有资源
     */
    private String resource;
    
    /**
     * 权限操作类型
     * - read: 读取
     * - write: 写入
     * - delete: 删除
     * - *: 所有操作
     */
    private String action;

    // ==================== 构造函数 ====================

    public Permission() {}

    public Permission(String name, String description, String resource, String action) {
        this.name = name;
        this.description = description;
        this.resource = resource;
        this.action = action;
    }

    // ==================== 静态工厂方法 ====================

    /**
     * 创建读取权限
     */
    public static Permission read(String resource) {
        return new Permission(
            resource + ":read",
            "查看" + getResourceName(resource),
            resource,
            "read"
        );
    }

    /**
     * 创建写入权限
     */
    public static Permission write(String resource) {
        return new Permission(
            resource + ":write",
            "编辑" + getResourceName(resource),
            resource,
            "write"
        );
    }

    /**
     * 创建删除权限
     */
    public static Permission delete(String resource) {
        return new Permission(
            resource + ":delete",
            "删除" + getResourceName(resource),
            resource,
            "delete"
        );
    }

    /**
     * 创建所有权限
     */
    public static Permission all(String resource) {
        return new Permission(
            resource + ":*",
            getResourceName(resource) + "所有操作",
            resource,
            "*"
        );
    }

    /**
     * 创建超级管理员权限
     */
    public static Permission superAdmin() {
        return new Permission(
            "*:*",
            "超级管理员权限",
            "*",
            "*"
        );
    }

    /**
     * 获取资源中文名称
     */
    private static String getResourceName(String resource) {
        switch (resource) {
            case "substation": return "变电站";
            case "line": return "输电线路";
            case "area": return "台区";
            case "alert": return "告警";
            case "user": return "用户";
            default: return resource;
        }
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
