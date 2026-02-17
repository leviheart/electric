package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Alert - 告警实体类
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、实体类注解说明
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * @Entity
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 标记这是一个 JPA 实体类，会被 Hibernate 管理                             │
 * │ 对应数据库表：alerts                                                    │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @Enumerated(EnumType.STRING)
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 枚举类型的存储方式                                                      │
 * │ - STRING: 存储枚举名称（如 "CRITICAL"）                                  │
 * │ - ORDINAL: 存储枚举序号（如 0, 1, 2）                                   │
 * │                                                                          │
 * │ 为什么用 STRING？                                                       │
 * │ - 可读性好：数据库中直接看到枚举值                                       │
 * │ - 安全：枚举顺序改变不会影响数据                                         │
 * │ - ORDINAL 方式如果枚举顺序改变，数据会错乱                               │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * 功能说明：
 * 存储告警信息，包括告警类型、级别、设备信息、告警内容等
 * 用于实时监控设备状态，及时发现和处理异常
 * 
 * 告警类型：
 * ┌─────────────────┬────────────────────────────────┐
 * │ 类型            │ 说明                            │
 * ├─────────────────┼────────────────────────────────┤
 * │ DEVICE_OFFLINE  │ 设备离线                        │
 * │ OVERLOAD        │ 负载过高                        │
 * │ VOLTAGE_ABNORMAL│ 电压异常                        │
 * │ TEMPERATURE_HIGH│ 温度过高                        │
 * │ FAULT           │ 故障告警                        │
 * │ MAINTENANCE     │ 检修提醒                        │
 * └─────────────────┴────────────────────────────────┘
 * 
 * 告警级别：
 * ┌─────────────────┬────────────────────────────────┐
 * │ 级别            │ 说明                            │
 * ├─────────────────┼────────────────────────────────┤
 * │ CRITICAL        │ 严重：需要立即处理              │
 * │ HIGH            │ 高：需要尽快处理                │
 * │ MEDIUM          │ 中：需要关注                    │
 * │ LOW             │ 低：一般提醒                    │
 * └─────────────────┴────────────────────────────────┘
 * 
 * 告警状态：
 * - ACTIVE: 活动告警，未处理
 * - ACKNOWLEDGED: 已确认，处理中
 * - RESOLVED: 已解决
 * 
 * 文件关联：
 * - AlertService: 告警业务逻辑
 * - AlertController: 告警 RESTful API
 * - AlertRepository: 告警数据访问
 * - WebSocketConfig: 实时推送告警
 */
@Entity
@Table(name = "alerts")
public class Alert {
    
    /**
     * 告警ID
     * - 使用 UUID 生成唯一ID
     * 
     * @GeneratedValue 不使用自动生成，因为 ID 在构造函数中通过 UUID 生成
     */
    @Id
    private String id;
    
    /**
     * 告警类型
     * - DEVICE_OFFLINE: 设备离线
     * - OVERLOAD: 负载过高
     * - VOLTAGE_ABNORMAL: 电压异常
     * - TEMPERATURE_HIGH: 温度过高
     * - FAULT: 故障告警
     * - MAINTENANCE: 检修提醒
     */
    @Enumerated(EnumType.STRING)
    private AlertType type;
    
    /**
     * 告警级别
     * - CRITICAL: 严重
     * - HIGH: 高
     * - MEDIUM: 中
     * - LOW: 低
     */
    @Enumerated(EnumType.STRING)
    private AlertLevel level;
    
    /**
     * 告警状态
     * - ACTIVE: 活动
     * - ACKNOWLEDGED: 已确认
     * - RESOLVED: 已解决
     */
    @Enumerated(EnumType.STRING)
    private AlertStatus status;
    
    /**
     * 设备类型
     * - SUBSTATION: 变电站
     * - LINE: 输电线路
     * - AREA: 台区
     */
    private String deviceType;
    
    /**
     * 设备ID
     * - 关联到具体的设备
     */
    private Long deviceId;
    
    /**
     * 设备名称
     * - 用于显示
     */
    private String deviceName;
    
    /**
     * 告警标题
     * - 简短描述告警内容
     */
    private String title;
    
    /**
     * 告警详情
     * - 详细描述告警信息
     */
    private String message;
    
    /**
     * 告警发生时间
     * - 自动记录
     */
    private LocalDateTime timestamp;
    
    /**
     * 确认时间
     * - 用户确认告警的时间
     */
    private LocalDateTime acknowledgedAt;
    
    /**
     * 解决时间
     * - 告警解决的时间
     */
    private LocalDateTime resolvedAt;
    
    /**
     * 确认用户
     * - 确认告警的用户名
     */
    private String acknowledgedBy;
    
    /**
     * 设备纬度
     * - 用于地图定位
     */
    private Double latitude;
    
    /**
     * 设备经度
     * - 用于地图定位
     */
    private Double longitude;

    /**
     * 告警类型枚举
     */
    public enum AlertType {
        DEVICE_OFFLINE("设备离线"),
        OVERLOAD("负载过高"),
        VOLTAGE_ABNORMAL("电压异常"),
        TEMPERATURE_HIGH("温度过高"),
        FAULT("故障告警"),
        MAINTENANCE("检修提醒");
        
        private final String description;
        
        AlertType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }

    /**
     * 告警级别枚举
     */
    public enum AlertLevel {
        CRITICAL("严重", "#ff0080"),
        HIGH("高", "#ff6600"),
        MEDIUM("中", "#ffff00"),
        LOW("低", "#00ff80");
        
        private final String description;
        private final String color;
        
        AlertLevel(String description, String color) {
            this.description = description;
            this.color = color;
        }
        
        public String getDescription() {
            return description;
        }
        
        public String getColor() {
            return color;
        }
    }

    /**
     * 告警状态枚举
     */
    public enum AlertStatus {
        ACTIVE("活动"),
        ACKNOWLEDGED("已确认"),
        RESOLVED("已解决");
        
        private final String description;
        
        AlertStatus(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }

    // ==================== 构造函数 ====================

    public Alert() {
        this.id = java.util.UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.status = AlertStatus.ACTIVE;
    }

    // ==================== 静态工厂方法 ====================

    /**
     * 创建设备离线告警
     */
    public static Alert deviceOffline(String deviceType, Long deviceId, String deviceName, 
                                      Double lat, Double lng) {
        Alert alert = new Alert();
        alert.setType(AlertType.DEVICE_OFFLINE);
        alert.setLevel(AlertLevel.HIGH);
        alert.setDeviceType(deviceType);
        alert.setDeviceId(deviceId);
        alert.setDeviceName(deviceName);
        alert.setTitle("设备离线告警");
        alert.setMessage(String.format("设备 %s 已离线，请检查设备连接状态", deviceName));
        alert.setLatitude(lat);
        alert.setLongitude(lng);
        return alert;
    }

    /**
     * 创建负载过高告警
     */
    public static Alert overload(String deviceType, Long deviceId, String deviceName,
                                  double loadPercent, Double lat, Double lng) {
        Alert alert = new Alert();
        alert.setType(AlertType.OVERLOAD);
        alert.setLevel(loadPercent > 90 ? AlertLevel.CRITICAL : AlertLevel.HIGH);
        alert.setDeviceType(deviceType);
        alert.setDeviceId(deviceId);
        alert.setDeviceName(deviceName);
        alert.setTitle("负载过高告警");
        alert.setMessage(String.format("设备 %s 负载率达到 %.1f%%，超过安全阈值", deviceName, loadPercent));
        alert.setLatitude(lat);
        alert.setLongitude(lng);
        return alert;
    }

    /**
     * 创建电压异常告警
     */
    public static Alert voltageAbnormal(String deviceType, Long deviceId, String deviceName,
                                         double voltage, Double lat, Double lng) {
        Alert alert = new Alert();
        alert.setType(AlertType.VOLTAGE_ABNORMAL);
        alert.setLevel(AlertLevel.HIGH);
        alert.setDeviceType(deviceType);
        alert.setDeviceId(deviceId);
        alert.setDeviceName(deviceName);
        alert.setTitle("电压异常告警");
        alert.setMessage(String.format("设备 %s 检测到电压异常：%.1fV", deviceName, voltage));
        alert.setLatitude(lat);
        alert.setLongitude(lng);
        return alert;
    }

    /**
     * 创建故障告警
     */
    public static Alert fault(String deviceType, Long deviceId, String deviceName,
                              String faultDetail, Double lat, Double lng) {
        Alert alert = new Alert();
        alert.setType(AlertType.FAULT);
        alert.setLevel(AlertLevel.CRITICAL);
        alert.setDeviceType(deviceType);
        alert.setDeviceId(deviceId);
        alert.setDeviceName(deviceName);
        alert.setTitle("故障告警");
        alert.setMessage(String.format("设备 %s 发生故障：%s", deviceName, faultDetail));
        alert.setLatitude(lat);
        alert.setLongitude(lng);
        return alert;
    }

    // ==================== Getter 和 Setter 方法 ====================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public AlertLevel getLevel() {
        return level;
    }

    public void setLevel(AlertLevel level) {
        this.level = level;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public String getAcknowledgedBy() {
        return acknowledgedBy;
    }

    public void setAcknowledgedBy(String acknowledgedBy) {
        this.acknowledgedBy = acknowledgedBy;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
