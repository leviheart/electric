package com.electric.repository;

import com.electric.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository - 用户数据访问接口
 * 
 * 功能说明：
 * 提供用户实体的数据访问操作
 * 继承 JpaRepository，自动实现基本 CRUD 操作
 * 
 * 自定义查询方法：
 * - findByUsername: 根据用户名查询用户
 * 
 * 文件关联：
 * - User: 用户实体类
 * - AuthService: 认证服务调用此接口
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象（可选）
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
}
