package com.electric.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * TransmissionLine - 输电线路实体类
 * 
 * 功能说明：
 * 存储输电线路的基本信息，包括起止变电站、长度、电压等级和几何路径
 * 用于在地图上绘制输电线路并展示详情
 * 
 * 数据库映射：
 * - 表名：transmission_line（默认使用类名转下划线）
 * - 主键：id，自增生成
 * 
 * 实体属性：
 * ┌───────────────┬──────────┬────────────────────────────────┐
 * │ 属性名        │ 类型     │ 说明                            │
 * ├───────────────┼──────────┼────────────────────────────────┤
 * │ id            │ Long     │ 主键ID，数据库自动生成           │
 * │ name          │ String   │ 线路名称，必填                   │
 * │ startSubstation│ String  │ 起始变电站名称                   │
 * │ endSubstation │ String   │ 终止变电站名称                   │
 * │ length        │ Double   │ 线路长度（公里）                  │
 * │ voltageLevel  │ String   │ 电压等级（220kV/110kV/35kV）     │
 * │ status        │ String   │ 运行状态                         │
 * │ geometry      │ String   │ 几何路径（JSON格式的坐标数组）    │
 * └───────────────┴──────────┴────────────────────────────────┘
 * 
 * geometry 字段格式：
 * JSON 数组，每个元素是 [纬度, 经度] 格式的坐标点
 * 示例：[[39.9142, 116.4174], [39.8942, 116.3974]]
 * 
 * 文件关联：
 * - TransmissionLineRepository: 数据访问接口
 * - TransmissionLineService: 业务逻辑处理
 * - TransmissionLineController: RESTful API 接口
 * 
 * 新人提示：
 * - geometry 字段存储 JSON 字符串，前端需要解析后使用
 * - 线路颜色根据 voltageLevel 在前端决定
 */
@Entity
public class TransmissionLine {
    
    /**
     * 主键ID
     * - 使用数据库自增策略生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 线路名称
     * - 必填字段
     * - 示例：朝阳-海淀线
     */
    @NotBlank(message = "线路名称不能为空")
    private String name;

    /**
     * 起始变电站名称
     * - 线路的起点
     * - 与变电站名称对应
     */
    private String startSubstation;

    /**
     * 终止变电站名称
     * - 线路的终点
     * - 与变电站名称对应
     */
    private String endSubstation;

    /**
     * 线路长度
     * - 单位：公里（km）
     * - 用于显示线路距离
     */
    private Double length;

    /**
     * 电压等级
     * - 常见值：220kV、110kV、35kV
     * - 用于区分不同电压等级的输电网络
     * - 前端根据此值决定线路颜色
     */
    private String voltageLevel;

    /**
     * 运行状态
     * - 常见值：运行中、停运、检修
     */
    private String status;

    /**
     * 几何路径
     * - JSON 格式的坐标数组
     * - 格式：[[纬度1, 经度1], [纬度2, 经度2], ...]
     * - 用于在地图上绘制线路
     * - 可以包含多个中间点，实现曲线效果
     */
    private String geometry;

    // ==================== 构造函数 ====================

    /**
     * 默认构造函数
     * JPA 要求实体类必须有无参构造函数
     */
    public TransmissionLine() {}

    /**
     * 带参数的构造函数
     * 用于快速创建实体对象
     * 
     * @param name 线路名称
     * @param startSubstation 起始变电站
     * @param endSubstation 终止变电站
     * @param length 长度（公里）
     * @param voltageLevel 电压等级
     * @param status 运行状态
     * @param geometry 几何路径（JSON）
     */
    public TransmissionLine(String name, String startSubstation, String endSubstation, 
                           Double length, String voltageLevel, String status, String geometry) {
        this.name = name;
        this.startSubstation = startSubstation;
        this.endSubstation = endSubstation;
        this.length = length;
        this.voltageLevel = voltageLevel;
        this.status = status;
        this.geometry = geometry;
    }

    // ==================== Getter 和 Setter 方法 ====================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartSubstation() {
        return startSubstation;
    }

    public void setStartSubstation(String startSubstation) {
        this.startSubstation = startSubstation;
    }

    public String getEndSubstation() {
        return endSubstation;
    }

    public void setEndSubstation(String endSubstation) {
        this.endSubstation = endSubstation;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(String voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }
}
