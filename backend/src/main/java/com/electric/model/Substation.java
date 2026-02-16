package com.electric.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

/**
 * Substation - 变电站实体类
 * 
 * 功能说明：
 * 存储变电站的基本信息，包括位置、名称、电压等级和运行状态
 * 使用 Jakarta Validation 注解进行参数校验
 * 
 * 实体属性：
 * - id: 主键ID，自增生成
 * - name: 变电站名称（必填，长度2-50）
 * - latitude: 纬度坐标（必填，范围-90到90）
 * - longitude: 经度坐标（必填，范围-180到180）
 * - voltageLevel: 电压等级（必填，如220kV、110kV等）
 * - status: 运行状态（必填，如运行中、检修中等）
 * 
 * 文件关联：
 * - 数据库表：对应数据库中的substation表
 * - Repository：SubstationRepository接口用于数据访问
 * - Service：SubstationService接口及其实现类处理业务逻辑
 * - Controller：SubstationController提供RESTful API接口
 */
@Entity
public class Substation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "变电站名称不能为空")
    @Size(min = 2, max = 50, message = "变电站名称长度必须在2-50个字符之间")
    private String name;

    @NotNull(message = "纬度不能为空")
    @Min(value = -90, message = "纬度必须在-90到90之间")
    @Max(value = 90, message = "纬度必须在-90到90之间")
    private Double latitude;

    @NotNull(message = "经度不能为空")
    @Min(value = -180, message = "经度必须在-180到180之间")
    @Max(value = 180, message = "经度必须在-180到180之间")
    private Double longitude;

    @NotBlank(message = "电压等级不能为空")
    @Pattern(regexp = "^(220kV|110kV|35kV|10kV)$", message = "电压等级必须是220kV、110kV、35kV或10kV")
    private String voltageLevel;

    @NotBlank(message = "运行状态不能为空")
    @Pattern(regexp = "^(运行中|检修中|停运)$", message = "运行状态必须是运行中、检修中或停运")
    private String status;

    // 默认构造函数
    public Substation() {}

    // 带参数的构造函数
    public Substation(String name, Double latitude, Double longitude, String voltageLevel, String status) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.voltageLevel = voltageLevel;
        this.status = status;
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
}
