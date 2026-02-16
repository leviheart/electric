package com.electric.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

/**
 * Area - 台区实体类
 * 
 * 功能说明：
 * 存储台区的基本信息，包括名称、所属变电站、客户数量和边界范围
 * 用于在地图上绘制台区多边形区域并展示详情
 * 
 * 什么是台区？
 * 台区是指一台配电变压器的供电范围区域
 * 是电力系统中配电网络的基本单元
 * 
 * 数据库映射：
 * - 表名：area
 * - 主键：id，自增生成
 * 
 * 实体属性：
 * ┌───────────────┬──────────┬────────────────────────────────┐
 * │ 属性名        │ 类型     │ 说明                            │
 * ├───────────────┼──────────┼────────────────────────────────┤
 * │ id            │ Long     │ 主键ID，数据库自动生成           │
 * │ name          │ String   │ 台区名称，必填                   │
 * │ substationName│ String   │ 所属变电站名称                   │
 * │ customerCount │ Integer  │ 客户数量                         │
 * │ status        │ String   │ 运行状态                         │
 * │ geometry      │ String   │ 边界范围（JSON格式的坐标数组）    │
 * └───────────────┴──────────┴────────────────────────────────┘
 * 
 * geometry 字段格式：
 * JSON 数组，每个元素是 [纬度, 经度] 格式的坐标点
 * 多边形顶点，首尾相连形成封闭区域
 * 示例：[[39.91, 116.41], [39.92, 116.41], [39.92, 116.42], [39.91, 116.42], [39.91, 116.41]]
 * 
 * 文件关联：
 * - AreaRepository: 数据访问接口
 * - AreaService: 业务逻辑处理
 * - AreaController: RESTful API 接口
 * 
 * 新人提示：
 * - geometry 字段存储多边形顶点坐标
 * - 前端解析后使用 Mapbox GL 绘制多边形
 * - 多边形首尾坐标应该相同，形成闭合区域
 */
@Entity
public class Area {
    
    /**
     * 主键ID
     * - 使用数据库自增策略生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 台区名称
     * - 必填字段
     * - 示例：朝阳区A台区
     */
    @NotBlank(message = "台区名称不能为空")
    private String name;

    /**
     * 所属变电站名称
     * - 标识台区由哪个变电站供电
     * - 与变电站名称对应
     */
    private String substationName;

    /**
     * 客户数量
     * - 该台区服务的客户总数
     * - 用于统计和展示
     */
    private Integer customerCount;

    /**
     * 运行状态
     * - 常见值：正常、异常、检修
     */
    private String status;

    /**
     * 边界范围
     * - JSON 格式的坐标数组
     * - 格式：[[纬度1, 经度1], [纬度2, 经度2], ...]
     * - 多边形顶点，首尾相连形成封闭区域
     * - 用于在地图上绘制台区范围
     */
    private String geometry;

    // ==================== 构造函数 ====================

    /**
     * 默认构造函数
     * JPA 要求实体类必须有无参构造函数
     */
    public Area() {}

    /**
     * 带参数的构造函数
     * 用于快速创建实体对象
     * 
     * @param name 台区名称
     * @param substationName 所属变电站
     * @param customerCount 客户数量
     * @param status 运行状态
     * @param geometry 边界范围（JSON）
     */
    public Area(String name, String substationName, Integer customerCount, String status, String geometry) {
        this.name = name;
        this.substationName = substationName;
        this.customerCount = customerCount;
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

    public String getSubstationName() {
        return substationName;
    }

    public void setSubstationName(String substationName) {
        this.substationName = substationName;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
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
