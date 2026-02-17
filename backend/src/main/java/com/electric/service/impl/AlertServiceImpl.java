package com.electric.service.impl;

import com.electric.model.Alert;
import com.electric.service.AlertService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * AlertServiceImpl - 告警服务实现类
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、WebSocket 实时推送机制
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ WebSocket 是一种全双工通信协议，允许服务器主动向客户端推送消息            │
 * │                                                                          │
 * │ 传统 HTTP 请求：                                                         │
 * │ 客户端 → 请求 → 服务器 → 响应 → 客户端（单向，客户端发起）                │
 * │                                                                          │
 * │ WebSocket 通信：                                                         │
 * │ 客户端 ←→ 服务器（双向，服务器可主动推送）                                │
 * │                                                                          │
 * │ 本项目 WebSocket 流程：                                                  │
 * │                                                                          │
 * │ 1. 前端订阅主题：                                                        │
 * │    stompClient.subscribe('/topic/alerts', (message) => {                │
 * │        const alert = JSON.parse(message.body);                          │
 * │        // 处理新告警                                                     │
 * │    });                                                                   │
 * │                                                                          │
 * │ 2. 后端推送消息：                                                        │
 * │    messagingTemplate.convertAndSend("/topic/alerts", alert);            │
 * │                                                                          │
 * │ 3. 所有订阅者收到消息                                                    │
 * │                                                                          │
 * │ STOMP 协议：                                                             │
 * │ - Simple Text Oriented Messaging Protocol                               │
 * │ - 在 WebSocket 之上提供消息订阅/发布模式                                  │
 * │ - 支持主题订阅（/topic/xxx）和点对点消息（/queue/xxx）                    │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、ConcurrentHashMap 线程安全
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * private final Map<String, Alert> alertStore = new ConcurrentHashMap<>();
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 为什么用 ConcurrentHashMap 而不是 HashMap？                              │
 * │                                                                          │
 * │ HashMap 在多线程环境下的问题：                                           │
 * │ - 非线程安全，并发写入可能导致数据丢失                                    │
 * │ - 扩容时可能导致死循环（JDK7）                                           │
 * │ - 读取时可能读到不一致的数据                                              │
 * │                                                                          │
 * │ ConcurrentHashMap 的线程安全机制：                                       │
 * │ - JDK 8+: 使用 CAS + synchronized 锁单个桶                               │
 * │ - 读操作完全无锁                                                         │
 * │ - 写操作只锁住相关的桶，不影响其他桶                                      │
 * │ - 支持高并发读写                                                         │
 * │                                                                          │
 * │ 常用方法：                                                               │
 * │ - put(key, value): 写入                                                 │
 * │ - get(key): 读取                                                        │
 * │ - remove(key): 删除                                                     │
 * │ - values(): 获取所有值                                                   │
 * │ - computeIfAbsent(key, function): 原子性的"不存在则创建"                 │
 * │                                                                          │
 * │ 其他线程安全集合：                                                       │
 * │ - Collections.synchronizedMap(new HashMap<>()) - 性能较差                │
 * │ - Hashtable - 古老实现，全表锁，性能差                                   │
 * │ - ConcurrentHashMap - 推荐，高性能                                       │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 三、Java Stream API
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Stream API 是 Java 8 引入的函数式编程特性                                │
 * │                                                                          │
 * │ 传统写法：                                                               │
 * │   List<Alert> result = new ArrayList<>();                               │
 * │   for (Alert alert : alertStore.values()) {                             │
 * │       if (alert.getStatus() == AlertStatus.ACTIVE) {                    │
 * │           result.add(alert);                                             │
 * │       }                                                                  │
 * │   }                                                                      │
 * │   Collections.sort(result, (a, b) -> b.getTimestamp()                   │
 * │       .compareTo(a.getTimestamp()));                                     │
 * │   return result;                                                         │
 * │                                                                          │
 * │ Stream 写法：                                                            │
 * │   return alertStore.values().stream()                                   │
 * │       .filter(alert -> alert.getStatus() == AlertStatus.ACTIVE)         │
 * │       .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))   │
 * │       .collect(Collectors.toList());                                    │
 * │                                                                          │
 * │ 常用操作：                                                               │
 * │ - filter(Predicate): 过滤元素                                           │
 * │ - map(Function): 转换元素                                               │
 * │ - sorted(Comparator): 排序                                              │
 * │ - limit(n): 取前 n 个                                                    │
 * │ - forEach(Consumer): 遍历                                               │
 * │ - collect(Collectors.toList()): 收集为 List                              │
 * │ - count(): 计数                                                         │
 * │ - anyMatch(Predicate): 是否存在匹配                                      │
 * │ - findFirst(): 获取第一个元素                                            │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 四、SimpMessagingTemplate 用法
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * SimpMessagingTemplate 是 Spring 提供的 WebSocket 消息发送模板
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 常用方法：                                                               │
 * │                                                                          │
 * │ 1. 广播消息（所有订阅者）                                                │
 * │    messagingTemplate.convertAndSend("/topic/alerts", alert);            │
 * │                                                                          │
 * │ 2. 发送给特定用户                                                        │
 * │    messagingTemplate.convertAndSendToUser(                              │
 * │        "admin", "/queue/notifications", message);                       │
 * │                                                                          │
 * │ 3. 发送带 header 的消息                                                  │
 * │    Map<String, Object> headers = new HashMap<>();                       │
 * │    headers.put("priority", "high");                                     │
 * │    messagingTemplate.convertAndSend("/topic/alerts", alert, headers);   │
 * │                                                                          │
 * │ 主题命名规范：                                                           │
 * │ - /topic/xxx: 广播主题，所有订阅者都能收到                               │
 * │ - /queue/xxx: 点对点队列，只有特定用户能收到                              │
 * │ - /user/queue/xxx: 用户专属队列                                          │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - AlertService: 服务接口
 * - Alert: 告警实体类
 * - WebSocketConfig: WebSocket 配置
 * - AlertController: 告警 API 控制器
 */
@Service
public class AlertServiceImpl implements AlertService {

    /**
     * WebSocket 消息发送模板
     * 
     * SimpMessagingTemplate 由 Spring 自动注入
     * 用于向 WebSocket 订阅者推送消息
     */
    private final SimpMessagingTemplate messagingTemplate;
    
    /**
     * 告警存储
     * 
     * 使用 ConcurrentHashMap 保证线程安全
     * Key: 告警ID (String)
     * Value: 告警对象 (Alert)
     * 
     * 注意：这是内存存储，重启后数据丢失
     * 生产环境应使用数据库（如 MySQL、MongoDB）
     */
    private final Map<String, Alert> alertStore = new ConcurrentHashMap<>();

    /**
     * 构造函数依赖注入
     * @param messagingTemplate WebSocket 消息发送模板
     */
    public AlertServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * 创建告警
     * 
     * 流程：
     * 1. 生成告警ID（如果未设置）
     * 2. 设置创建时间（如果未设置）
     * 3. 设置初始状态为 ACTIVE
     * 4. 存储告警到内存
     * 5. 通过 WebSocket 推送给所有订阅者
     * 
     * @param alert 告警对象
     * @return 创建后的告警对象
     */
    @Override
    public Alert createAlert(Alert alert) {
        // 生成唯一 ID
        if (alert.getId() == null || alert.getId().isEmpty()) {
            alert.setId(UUID.randomUUID().toString());
        }
        // 设置创建时间
        if (alert.getTimestamp() == null) {
            alert.setTimestamp(LocalDateTime.now());
        }
        // 设置初始状态
        if (alert.getStatus() == null) {
            alert.setStatus(Alert.AlertStatus.ACTIVE);
        }
        
        // 存储到内存
        alertStore.put(alert.getId(), alert);
        
        // 推送给所有订阅 /topic/alerts 的客户端
        messagingTemplate.convertAndSend("/topic/alerts", alert);
        
        return alert;
    }

    /**
     * 获取所有告警列表
     * 
     * Stream API 处理流程：
     * 1. alertStore.values() - 获取所有告警
     * 2. .stream() - 转换为流
     * 3. .sorted() - 按时间倒序排序
     * 4. .collect() - 收集为 List
     * 
     * @return 告警列表，按时间倒序排列
     */
    @Override
    public List<Alert> getAllAlerts() {
        return alertStore.values().stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .collect(Collectors.toList());
    }

    /**
     * 获取活动告警列表
     * 
     * Stream API 处理流程：
     * 1. alertStore.values() - 获取所有告警
     * 2. .stream() - 转换为流
     * 3. .filter() - 过滤出 ACTIVE 状态的告警
     * 4. .sorted() - 按时间倒序排序
     * 5. .collect() - 收集为 List
     * 
     * @return 活动告警列表
     */
    @Override
    public List<Alert> getActiveAlerts() {
        return alertStore.values().stream()
                .filter(alert -> alert.getStatus() == Alert.AlertStatus.ACTIVE)
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .collect(Collectors.toList());
    }

    /**
     * 根据ID获取告警详情
     * 
     * Optional 是 Java 8 引入的容器类，用于避免空指针异常
     * - Optional.of(value): 包装非空值
     * - Optional.ofNullable(value): 可能为空的值
     * - Optional.empty(): 空值
     * 
     * @param id 告警ID
     * @return 告警对象（Optional 包装）
     */
    @Override
    public Optional<Alert> getAlertById(String id) {
        return Optional.ofNullable(alertStore.get(id));
    }

    /**
     * 确认告警
     * 
     * 状态变更：ACTIVE → ACKNOWLEDGED
     * 记录确认人和确认时间
     * 推送更新通知
     * 
     * @param id 告警ID
     * @param username 确认用户名
     * @return 更新后的告警对象
     */
    @Override
    public Alert acknowledgeAlert(String id, String username) {
        Alert alert = alertStore.get(id);
        if (alert != null) {
            alert.setStatus(Alert.AlertStatus.ACKNOWLEDGED);
            alert.setAcknowledgedAt(LocalDateTime.now());
            alert.setAcknowledgedBy(username);
            
            // 推送更新通知
            messagingTemplate.convertAndSend("/topic/alerts/updated", alert);
        }
        return alert;
    }

    /**
     * 解决告警
     * 
     * 状态变更：ACKNOWLEDGED → RESOLVED
     * 记录解决时间
     * 推送更新通知
     * 
     * @param id 告警ID
     * @return 更新后的告警对象
     */
    @Override
    public Alert resolveAlert(String id) {
        Alert alert = alertStore.get(id);
        if (alert != null) {
            alert.setStatus(Alert.AlertStatus.RESOLVED);
            alert.setResolvedAt(LocalDateTime.now());
            
            // 推送更新通知
            messagingTemplate.convertAndSend("/topic/alerts/updated", alert);
        }
        return alert;
    }

    /**
     * 删除告警
     * 
     * 从内存中移除告警
     * 推送删除通知
     * 
     * @param id 告警ID
     */
    @Override
    public void deleteAlert(String id) {
        Alert alert = alertStore.remove(id);
        if (alert != null) {
            // 推送删除通知
            messagingTemplate.convertAndSend("/topic/alerts/deleted", id);
        }
    }

    /**
     * 获取指定级别的告警数量
     * 
     * Stream API 处理流程：
     * 1. 获取所有告警
     * 2. 过滤出指定级别
     * 3. 计数
     * 
     * @param level 告警级别
     * @return 告警数量
     */
    @Override
    public long countByLevel(Alert.AlertLevel level) {
        return alertStore.values().stream()
                .filter(alert -> alert.getLevel() == level)
                .count();
    }

    /**
     * 获取指定状态的告警数量
     * @param status 告警状态
     * @return 告警数量
     */
    @Override
    public long countByStatus(Alert.AlertStatus status) {
        return alertStore.values().stream()
                .filter(alert -> alert.getStatus() == status)
                .count();
    }
}
