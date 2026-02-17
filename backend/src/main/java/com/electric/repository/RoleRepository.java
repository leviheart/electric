package com.electric.repository;

import com.electric.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * RoleRepository - 角色数据访问接口
 * 
 * 功能说明：
 * 提供角色实体的数据访问操作
 * 继承 JpaRepository，自动实现基本 CRUD 操作
 * 
 * 自定义查询方法：
 * - findByName: 根据角色名称查询角色
 * 
 * 文件关联：
 * - Role: 角色实体类
 * - UserService: 用户服务，调用此接口
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * 根据角色名称查询角色
     * 
     * @param name 角色名称（ADMIN/OPERATOR/VIEWER）
     * @return 角色对象（Optional 包装）
     */
    Optional<Role> findByName(String name);
    
    /**
     * 检查角色名称是否存在
     * 
     * @param name 角色名称
     * @return true 表示存在
     */
    boolean existsByName(String name);
}
