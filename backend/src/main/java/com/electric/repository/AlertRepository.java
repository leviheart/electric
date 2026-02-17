package com.electric.repository;

import com.electric.model.Alert;
import com.electric.model.Alert.AlertLevel;
import com.electric.model.Alert.AlertStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AlertRepository - 告警数据访问接口
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * Spring Data JPA Repository 接口
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * @Repository
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 标记这是一个数据访问层组件                                               │
 * │ - Spring 会自动扫描并创建实现类                                          │
 * │ - 可以被注入到 Service 层使用                                            │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * extends JpaRepository<Alert, String>
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 继承 Spring Data JPA 提供的基础接口                                      │
 * │                                                                          │
 * │ 泛型参数：                                                               │
 * │ - Alert: 实体类类型                                                      │
 * │ - String: 主键类型                                                       │
 * │                                                                          │
 * │ 自动提供的方法：                                                         │
 * │ - save(entity): 保存/更新实体                                            │
 * │ - findById(id): 根据 ID 查询                                             │
 * │ - findAll(): 查询所有                                                    │
 * │ - deleteById(id): 根据 ID 删除                                           │
 * │ - count(): 统计数量                                                      │
 * │ - existsById(id): 判断是否存在                                           │
 * │                                                                          │
 * │ 自定义查询方法：                                                         │
 * │ - 方法名遵循命名规则，Spring 自动生成 SQL                                 │
 * │ - findByStatus(AlertStatus status) → WHERE status = ?                   │
 * │ - findByLevelOrderByTimestampDesc(AlertLevel level)                     │
 * │   → WHERE level = ? ORDER BY timestamp DESC                              │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
@Repository
public interface AlertRepository extends JpaRepository<Alert, String> {
    
    /**
     * 根据状态查询告警
     * Spring 会自动生成 SQL: SELECT * FROM alerts WHERE status = ?
     */
    List<Alert> findByStatus(AlertStatus status);
    
    /**
     * 根据级别查询告警
     */
    List<Alert> findByLevel(AlertLevel level);
    
    /**
     * 根据设备类型和设备ID查询告警
     */
    List<Alert> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    
    /**
     * 查询活动告警（按时间倒序）
     */
    List<Alert> findByStatusOrderByTimestampDesc(AlertStatus status);
    
    /**
     * 统计指定状态的告警数量
     */
    long countByStatus(AlertStatus status);
    
    /**
     * 统计指定级别的告警数量
     */
    long countByLevel(AlertLevel level);
}
