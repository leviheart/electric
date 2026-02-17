package com.electric.repository;

import com.electric.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * PermissionRepository - 权限数据访问接口
 * 
 * 功能说明：
 * 提供权限实体的数据访问操作
 * 继承 JpaRepository，自动实现基本 CRUD 操作
 * 
 * 文件关联：
 * - Permission: 权限实体类
 * - RoleService: 角色服务，调用此接口
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
    /**
     * 根据权限名称查询权限
     * 
     * @param name 权限名称（如 substation:read）
     * @return 权限对象（Optional 包装）
     */
    Optional<Permission> findByName(String name);
    
    /**
     * 根据资源查询权限列表
     * 
     * @param resource 资源名称
     * @return 权限列表
     */
    java.util.List<Permission> findByResource(String resource);
}
