package com.electric.repository;

import com.electric.model.Substation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SubstationRepository - 变电站数据访问接口
 * 
 * 功能说明：
 * 提供变电站实体的数据访问操作
 * 继承 JpaRepository，自动实现基本 CRUD 操作
 * 
 * 继承的主要方法：
 * - findAll(): 获取所有变电站列表
 * - findById(Long id): 根据ID查询变电站
 * - save(Substation entity): 保存或更新变电站
 * - deleteById(Long id): 根据ID删除变电站
 * - count(): 统计变电站总数
 * - existsById(Long id): 检查变电站是否存在
 * 
 * 使用说明：
 * 1. 此接口不需要实现类，Spring Data JPA 会自动生成实现
 * 2. 可以在此接口中定义自定义查询方法，如：
 *    List<Substation> findByVoltageLevel(String voltageLevel);
 *    List<Substation> findByStatus(String status);
 * 
 * 文件关联：
 * - 实体类：Substation.java - 变电站实体定义
 * - 服务层：SubstationServiceImpl.java - 调用此接口进行数据操作
 * 
 * 新人提示：
 * - @Repository 注解标记这是一个数据访问层组件
 * - JpaRepository<Substation, Long> 中的泛型参数：
 *   第一个是实体类类型，第二个是主键类型
 */
@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long> {
    // JpaRepository 已经提供了基本的 CRUD 操作
    // 如需自定义查询方法，可以在此添加
    // 例如：List<Substation> findByStatus(String status);
}
