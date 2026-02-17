package com.electric.service;

import com.electric.model.Alert;
import java.util.List;
import java.util.Optional;

/**
 * AlertService - 告警服务接口
 * 
 * 功能说明：
 * 定义告警相关的业务操作接口
 * 包括告警的创建、查询、确认、解决等操作
 * 
 * 业务方法说明：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 方法名              │ 说明                                      │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ createAlert()       │ 创建告警并推送给所有订阅者                │
 * │ getAllAlerts()      │ 获取所有告警列表                          │
 * │ getActiveAlerts()   │ 获取活动告警列表                          │
 * │ getAlertById()      │ 根据ID获取告警详情                        │
 * │ acknowledgeAlert()  │ 确认告警                                  │
 * │ resolveAlert()      │ 解决告警                                  │
 * │ deleteAlert()       │ 删除告警                                  │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - AlertServiceImpl: 具体业务逻辑实现
 * - Alert: 告警实体类
 * - AlertController: RESTful API 控制器
 */
public interface AlertService {
    
    /**
     * 创建告警
     * 
     * 创建告警后，会自动通过 WebSocket 推送给所有订阅者
     * 
     * @param alert 告警对象
     * @return 创建后的告警对象
     */
    Alert createAlert(Alert alert);
    
    /**
     * 获取所有告警列表
     * 
     * @return 告警列表
     */
    List<Alert> getAllAlerts();
    
    /**
     * 获取活动告警列表
     * 
     * 只返回状态为 ACTIVE 的告警
     * 
     * @return 活动告警列表
     */
    List<Alert> getActiveAlerts();
    
    /**
     * 根据ID获取告警详情
     * 
     * @param id 告警ID
     * @return 告警对象（Optional 包装）
     */
    Optional<Alert> getAlertById(String id);
    
    /**
     * 确认告警
     * 
     * 将告警状态从 ACTIVE 改为 ACKNOWLEDGED
     * 记录确认时间和确认用户
     * 
     * @param id 告警ID
     * @param username 确认用户名
     * @return 更新后的告警对象
     */
    Alert acknowledgeAlert(String id, String username);
    
    /**
     * 解决告警
     * 
     * 将告警状态改为 RESOLVED
     * 记录解决时间
     * 
     * @param id 告警ID
     * @return 更新后的告警对象
     */
    Alert resolveAlert(String id);
    
    /**
     * 删除告警
     * 
     * @param id 告警ID
     */
    void deleteAlert(String id);
    
    /**
     * 获取指定级别的告警数量
     * 
     * @param level 告警级别
     * @return 告警数量
     */
    long countByLevel(Alert.AlertLevel level);
    
    /**
     * 获取指定状态的告警数量
     * 
     * @param status 告警状态
     * @return 告警数量
     */
    long countByStatus(Alert.AlertStatus status);
}
