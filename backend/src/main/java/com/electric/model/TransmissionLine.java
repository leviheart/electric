package com.electric.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

/**
 * TransmissionLine - 输电线路实体类
 * 
 * 功能说明：
 * 存储输电线路的基本信息，包括起点、终点、长度、电压等级、运行状态和几何坐标
 * 使用 Jakarta Validation 注解进行参数校验
 * 
 * 实体属性：
 * - id: 主键ID，自增生成
 * - name: 线路名称（必填，长度2-50）
 * - startSubstation: 起始变电站名称（必填）
 * - endSubstation: 终止变电站名称（必填）
 * - length: 线路长度（必填，必须大于0）
 * - voltageLevel: 电压等级（必填）
 * - status: 运行状态（必填）
 * - geometry: 线路几何坐标（必填，JSON格式）
 * 
 * 文件关联：
 * - 数据库表：对应数据库中的transmission_line表
 * - Repository：TransmissionLineRepository接口用于数据访问
 * - Service：TransmissionLineService接口及其实现类处理业务逻辑
 * - Controller：TransmissionLineController提供RESTful API接口
 */
@Entity
public class TransmissionLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "线路名称不能为空")
    @Size(min = 2, max = 50, message = "线路名称长度必须在2-50个字符之间")
    private String name;

    @NotBlank(message = "起始变电站不能为空")
    private String startSubstation;

    @NotBlank(message = "终止变电站不能为空")
    private String endSubstation;

    @NotNull(message = "线路长度不能为空")
    @Positive(message = "线路长度必须大于0")
    private Double length;

    @NotBlank(message = "电压等级不能为空")
    @Pattern(regexp = "^(220kV|110kV|35kV|10kV)$", message = "电压等级必须是220kV、110kV、35kV或10kV")
    private String voltageLevel;

    @NotBlank(message = "运行状态不能为空")
    @Pattern(regexp = "^(运行中|检修中|停运)$", message = "运行状态必须是运行中、检修中或停运")
    private String status;

    @NotBlank(message = "线路几何坐标不能为空")
    private String geometry;

    // 默认构造函数
    public TransmissionLine() {}

    // 带参数的构造函数
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
