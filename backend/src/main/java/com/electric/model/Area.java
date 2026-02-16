package com.electric.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

/**
 * Area - 台区实体类
 * 
 * 功能说明：
 * 存储台区的基本信息，包括名称、所属变电站、用户数、状态和几何边界
 * 使用 Jakarta Validation 注解进行参数校验
 * 
 * 实体属性：
 * - id: 主键ID，自增生成
 * - name: 台区名称（必填，长度2-50）
 * - substationName: 所属变电站名称（必填）
 * - customerCount: 用户数量（必填，必须大于等于0）
 * - status: 运行状态（必填）
 * - geometry: 台区边界坐标（必填，JSON格式）
 * 
 * 文件关联：
 * - 数据库表：对应数据库中的area表
 * - Repository：AreaRepository接口用于数据访问
 * - Service：AreaService接口及其实现类处理业务逻辑
 * - Controller：AreaController提供RESTful API接口
 */
@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "台区名称不能为空")
    @Size(min = 2, max = 50, message = "台区名称长度必须在2-50个字符之间")
    private String name;

    @NotBlank(message = "所属变电站不能为空")
    private String substationName;

    @NotNull(message = "用户数量不能为空")
    @Min(value = 0, message = "用户数量不能小于0")
    private Integer customerCount;

    @NotBlank(message = "运行状态不能为空")
    @Pattern(regexp = "^(正常|异常|检修)$", message = "运行状态必须是正常、异常或检修")
    private String status;

    @NotBlank(message = "台区边界坐标不能为空")
    private String geometry;

    // 默认构造函数
    public Area() {}

    // 带参数的构造函数
    public Area(String name, String substationName, Integer customerCount, String status, String geometry) {
        this.name = name;
        this.substationName = substationName;
        this.customerCount = customerCount;
        this.status = status;
        this.geometry = geometry;
    }

    // Getters and Setters
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
