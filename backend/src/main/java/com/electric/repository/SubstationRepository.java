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
 * 继承关系：
 * SubstationRepository extends JpaRepository<Substation, Long>
 * - 第一个泛型参数：实体类类型
 * - 第二个泛型参数：主键类型
 * 
 * 继承的主要方法：
 * ┌────────────────────────────────────────────────────────────────┐
 * │ 方法名              │ 说明                                      │
 * ├────────────────────────────────────────────────────────────────┤
 * │ findAll()           │ 获取所有变电站列表                          │
 * │ findById(Long id)   │ 根据ID查询变电站，返回 Optional            │
 * │ save(Substation s)  │ 保存或更新变电站                           │
 * │ deleteById(Long id) │ 根据ID删除变电站                           │
 * │ count()             │ 统计变电站总数                             │
 * │ existsById(Long id) │ 检查变电站是否存在                          │
 * └────────────────────────────────────────────────────────────────┘
 * 
 * 自定义查询方法：
 * 可以在此接口中定义自定义查询方法，Spring Data JPA 会自动实现
 * 命名约定：
 * - findBy + 属性名：根据属性查询
 * - countBy + 属性名：根据属性统计
 * - deleteBy + 属性名：根据属性删除
 * 
 * 示例：
 * List<Substation> findByVoltageLevel(String voltageLevel);
 * List<Substation> findByStatus(String status);
 * List<Substation> findByNameContaining(String keyword);
 * 
 * 文件关联：
 * - 实体类：Substation.java - 变电站实体定义
 * - 服务层：SubstationServiceImpl.java - 调用此接口进行数据操作
 * 
 * 新人提示：
 * - @Repository 注解标记这是一个数据访问层组件
 * - 此接口不需要实现类，Spring Data JPA 会自动生成实现
 * - 方法名遵循 Spring Data JPA 命名约定，自动生成 SQL
 */
@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long> {
    // JpaRepository 已经提供了基本的 CRUD 操作
    // 如需自定义查询方法，可以在此添加
    
    /**
     * 根据电压等级查询变电站
     * 示例用法：findByVoltageLevel("220kV")
     * 
     * @param voltageLevel 电压等级
     * @return 该电压等级的变电站列表
     */
    // List<Substation> findByVoltageLevel(String voltageLevel);
    
    /**
     * 根据状态查询变电站
     * 示例用法：findByStatus("运行中")
     * 
     * @param status 运行状态
     * @return 该状态的变电站列表
     */
    // List<Substation> findByStatus(String status);
}
