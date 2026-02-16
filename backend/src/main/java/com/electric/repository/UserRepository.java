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
 * 包含用户认证相关的自定义查询方法
 * 
 * 继承的主要方法：
 * - findAll(): 获取所有用户列表
 * - findById(Long id): 根据ID查询用户
 * - save(User user): 保存或更新用户
 * - deleteById(Long id): 根据ID删除用户
 * - count(): 统计用户总数
 * 
 * 自定义查询方法：
 * ┌────────────────────────────────────────────────────────────────┐
 * │ 方法名                    │ 说明                                │
 * ├────────────────────────────────────────────────────────────────┤
 * │ findByUsername(String)    │ 根据用户名查询用户                   │
 * │ existsByUsername(String)  │ 检查用户名是否已存在                 │
 * └────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - 实体类：User.java - 用户实体定义
 * - 服务层：AuthServiceImpl.java - 认证服务调用此接口
 * - 安全层：JwtAuthenticationFilter.java - JWT 过滤器调用此接口
 * 
 * 新人提示：
 * - Optional 是 Java 8 引入的容器类，用于避免空指针异常
 * - 使用 isPresent() 检查值是否存在
 * - 使用 orElse() 提供默认值
 * - 使用 orElseThrow() 在值不存在时抛出异常
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查询用户
     * 
     * 使用场景：
     * - 登录时验证用户是否存在
     * - JWT 过滤器获取用户信息
     * 
     * 返回值说明：
     * - Optional.empty(): 用户不存在
     * - Optional.of(user): 用户存在
     * 
     * @param username 用户名
     * @return Optional 包装的用户对象
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 检查用户名是否已存在
     * 
     * 使用场景：
     * - 注册时检查用户名是否已被使用
     * 
     * @param username 用户名
     * @return true 表示用户名已存在，false 表示可用
     */
    boolean existsByUsername(String username);
}
