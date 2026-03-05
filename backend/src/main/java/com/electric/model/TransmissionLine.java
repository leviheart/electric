package com.electric.model;

import jakarta.persistence.Column;
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
    @Column(columnDefinition = "TEXT")
    private String geometry;
    
    /**
     * 线路类型
     * - overhead: 架空线
     * - cable: 电缆
     */
    private String lineType;
    
    /**
     * 负载率
     * - 0-100+ 的数值
     * - 用于动态显示线路负载状态
     */
    private Double loadRate;

    /**
     * 设备编号
     */
    private String deviceCode;

    /**
     * 投运日期
     */
    private String commissionDate;

    /**
     * 设备厂家
     */
    private String manufacturer;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 导线型号
     */
    private String conductorModel;

    /**
     * 导线截面
     * - 单位：mm²
     */
    private Double conductorSection;

    /**
     * 检修周期
     * - 单位：月
     */
    private Integer maintenanceCycle;

    /**
     * 上次检修日期
     */
    private String lastMaintenanceDate;

    /**
     * 下次检修日期
     */
    private String nextMaintenanceDate;

    /**
     * 设计寿命
     * - 单位：年
     */
    private Integer designLife;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 设计电流
     * - 单位：A
     */
    private Double designCurrent;

    /**
     * 线路回路数
     */
    private Integer circuitCount;

    /**
     * 杆塔数量
     */
    private Integer towerCount;

    /**
     * 建设性质
     */
    private String constructionType;

    /**
     * 资产性质
     */
    private String assetType;

    /**
     * 备注
     */
    @Column(columnDefinition = "TEXT")
    private String remark;

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
    
    public String getLineType() {
        return lineType;
    }
    
    public void setLineType(String lineType) {
        this.lineType = lineType;
    }
    
    public Double getLoadRate() {
        return loadRate;
    }
    
    public void setLoadRate(Double loadRate) {
        this.loadRate = loadRate;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(String commissionDate) {
        this.commissionDate = commissionDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getConductorModel() {
        return conductorModel;
    }

    public void setConductorModel(String conductorModel) {
        this.conductorModel = conductorModel;
    }

    public Double getConductorSection() {
        return conductorSection;
    }

    public void setConductorSection(Double conductorSection) {
        this.conductorSection = conductorSection;
    }

    public Integer getMaintenanceCycle() {
        return maintenanceCycle;
    }

    public void setMaintenanceCycle(Integer maintenanceCycle) {
        this.maintenanceCycle = maintenanceCycle;
    }

    public String getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public void setLastMaintenanceDate(String lastMaintenanceDate) {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public String getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }

    public void setNextMaintenanceDate(String nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public Integer getDesignLife() {
        return designLife;
    }

    public void setDesignLife(Integer designLife) {
        this.designLife = designLife;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Double getDesignCurrent() {
        return designCurrent;
    }

    public void setDesignCurrent(Double designCurrent) {
        this.designCurrent = designCurrent;
    }

    public Integer getCircuitCount() {
        return circuitCount;
    }

    public void setCircuitCount(Integer circuitCount) {
        this.circuitCount = circuitCount;
    }

    public Integer getTowerCount() {
        return towerCount;
    }

    public void setTowerCount(Integer towerCount) {
        this.towerCount = towerCount;
    }

    public String getConstructionType() {
        return constructionType;
    }

    public void setConstructionType(String constructionType) {
        this.constructionType = constructionType;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
