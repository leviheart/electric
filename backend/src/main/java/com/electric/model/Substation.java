package com.electric.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Substation - 变电站实体类
 * 
 * 功能说明：
 * 存储变电站的基本信息，包括名称、位置、电压等级和运行状态
 * 用于在地图上展示变电站位置和详情
 * 
 * 数据库映射：
 * - 表名：substation（默认使用类名小写）
 * - 主键：id，自增生成
 * 
 * 实体属性：
 * ┌─────────────┬──────────┬────────────────────────────────┐
 * │ 属性名      │ 类型     │ 说明                            │
 * ├─────────────┼──────────┼────────────────────────────────┤
 * │ id          │ Long     │ 主键ID，数据库自动生成           │
 * │ name        │ String   │ 变电站名称，必填，最长100字符     │
 * │ latitude    │ Double   │ 纬度，用于地图定位                │
 * │ longitude   │ Double   │ 经度，用于地图定位                │
 * │ voltageLevel│ String   │ 电压等级（220kV/110kV/35kV）     │
 * │ status      │ String   │ 运行状态（运行中/停运/检修）      │
 * └─────────────┴──────────┴────────────────────────────────┘
 * 
 * 校验规则：
 * - name: 不能为空，长度3-100字符
 * 
 * 文件关联：
 * - SubstationRepository: 数据访问接口
 * - SubstationService: 业务逻辑处理
 * - SubstationController: RESTful API 接口
 * 
 * 新人提示：
 * - @Entity 注解标记这是一个 JPA 实体类
 * - @Id 和 @GeneratedValue 定义主键生成策略
 * - @NotBlank 和 @Size 用于参数校验
 */
@Entity
public class Substation {
    
    /**
     * 主键ID
     * - 使用数据库自增策略生成
     * - GenerationType.IDENTITY 表示使用数据库的自增字段
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 变电站名称
     * - 必填字段，不能为空
     * - 长度限制：3-100个字符
     * - 校验注解会在 Controller 层自动触发校验
     */
    @NotBlank(message = "变电站名称不能为空")
    @Size(min = 3, max = 100, message = "变电站名称长度必须在3-100个字符之间")
    private String name;

    /**
     * 纬度
     * - 用于地图定位（Y坐标）
     * - 范围：-90 到 90
     * - 北京纬度约 39.9
     */
    private Double latitude;

    /**
     * 经度
     * - 用于地图定位（X坐标）
     * - 范围：-180 到 180
     * - 北京经度约 116.4
     */
    private Double longitude;

    /**
     * 电压等级
     * - 常见值：220kV、110kV、35kV
     * - 用于区分不同电压等级的输电网络
     */
    private String voltageLevel;

    /**
     * 运行状态
     * - 常见值：运行中、停运、检修
     * - 用于监控变电站运行情况
     */
    private String status;

    // ==================== 构造函数 ====================

    /**
     * 默认构造函数
     * JPA 要求实体类必须有无参构造函数
     */
    public Substation() {}

    /**
     * 带参数的构造函数
     * 用于快速创建实体对象
     * 
     * @param name 变电站名称
     * @param latitude 纬度
     * @param longitude 经度
     * @param voltageLevel 电压等级
     * @param status 运行状态
     */
    public Substation(String name, Double latitude, Double longitude, String voltageLevel, String status) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.voltageLevel = voltageLevel;
        this.status = status;
    }

    // ==================== Getter 和 Setter 方法 ====================

    /**
     * 获取主键ID
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     * 通常不需要手动设置，由数据库自动生成
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取变电站名称
     * @return 变电站名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置变电站名称
     * @param name 变电站名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取纬度
     * @return 纬度
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     * @param latitude 纬度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取经度
     * @return 经度
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     * @param longitude 经度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取电压等级
     * @return 电压等级
     */
    public String getVoltageLevel() {
        return voltageLevel;
    }

    /**
     * 设置电压等级
     * @param voltageLevel 电压等级
     */
    public void setVoltageLevel(String voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    /**
     * 获取运行状态
     * @return 运行状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置运行状态
     * @param status 运行状态
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
