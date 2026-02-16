package com.electric.repository;

import com.electric.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * AreaRepository - 台区数据访问接口
 * 
 * 功能说明：
 * 提供台区实体的数据访问操作
 * 继承 JpaRepository，自动实现基本 CRUD 操作
 * 
 * 继承的主要方法：
 * - findAll(): 获取所有台区列表
 * - findById(Long id): 根据ID查询台区
 * - save(Area entity): 保存或更新台区
 * - deleteById(Long id): 根据ID删除台区
 * - count(): 统计台区总数
 * 
 * 使用说明：
 * 1. 此接口不需要实现类，Spring Data JPA 会自动生成实现
 * 2. 可以定义自定义查询方法，如按变电站名称或状态查询
 * 
 * 文件关联：
 * - 实体类：Area.java - 台区实体定义
 * - 服务层：AreaServiceImpl.java - 调用此接口进行数据操作
 * 
 * 新人提示：
 * - 台区是指配电变压器的供电范围区域
 * - 台区数据包含几何边界，用于在地图上绘制多边形区域
 */
@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    // JpaRepository 已经提供了基本的 CRUD 操作
    // 如需自定义查询方法，可以在此添加
}
