package com.electric.repository;

import com.electric.model.TransmissionLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * TransmissionLineRepository - 输电线路数据访问接口
 * 
 * 功能说明：
 * 提供输电线路实体的数据访问操作
 * 继承 JpaRepository，自动实现基本 CRUD 操作
 * 
 * 继承的主要方法：
 * - findAll(): 获取所有输电线路列表
 * - findById(Long id): 根据ID查询输电线路
 * - save(TransmissionLine entity): 保存或更新输电线路
 * - deleteById(Long id): 根据ID删除输电线路
 * - count(): 统计输电线路总数
 * 
 * 使用说明：
 * 1. 此接口不需要实现类，Spring Data JPA 会自动生成实现
 * 2. 可以定义自定义查询方法，如按电压等级或状态查询
 * 
 * 文件关联：
 * - 实体类：TransmissionLine.java - 输电线路实体定义
 * - 服务层：TransmissionLineServiceImpl.java - 调用此接口进行数据操作
 * 
 * 新人提示：
 * - Repository 层只负责数据访问，不包含业务逻辑
 * - 自定义查询方法遵循 Spring Data JPA 命名约定：
 *   findBy + 属性名，如 findByVoltageLevel(String level)
 */
@Repository
public interface TransmissionLineRepository extends JpaRepository<TransmissionLine, Long> {
    // JpaRepository 已经提供了基本的 CRUD 操作
    // 如需自定义查询方法，可以在此添加
}
