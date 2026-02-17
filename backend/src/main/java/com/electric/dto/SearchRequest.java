package com.electric.dto;

/**
 * SearchRequest - 搜索请求参数封装类
 * 
 * 功能说明：
 * 封装前端传递的搜索参数，支持关键字搜索和多条件筛选
 * 用于变电站、线路、台区的统一搜索接口
 * 
 * 参数说明：
 * ┌─────────────────┬──────────┬────────────────────────────────┐
 * │ 参数名          │ 类型     │ 说明                            │
 * ├─────────────────┼──────────┼────────────────────────────────┤
 * │ keyword         │ String   │ 搜索关键字，匹配名称            │
 * │ voltageLevel    │ String   │ 电压等级筛选（220kV/110kV/35kV）│
 * │ status          │ String   │ 运行状态筛选                    │
 * │ minLat/maxLat   │ Double   │ 纬度范围（地图视野范围筛选）     │
 * │ minLng/maxLng   │ Double   │ 经度范围（地图视野范围筛选）     │
 * └─────────────────┴──────────┴────────────────────────────────┘
 * 
 * 使用场景：
 * 1. 全局搜索框：用户输入关键字搜索设备
 * 2. 高级筛选：按电压等级、状态等条件过滤
 * 3. 地图视野筛选：只显示当前地图可见范围内的设备
 * 
 * 请求示例：
 * GET /api/substations/search?keyword=朝阳&voltageLevel=220kV&status=运行中
 * 
 * 文件关联：
 * - SubstationController: 使用此DTO接收搜索参数
 * - TransmissionLineController: 使用此DTO接收搜索参数
 * - AreaController: 使用此DTO接收搜索参数
 * 
 * 新人提示：
 * - DTO = Data Transfer Object，用于前后端数据传输
 * - 使用 @RequestParam 注解将URL参数绑定到DTO属性
 * - 所有参数都是可选的，支持灵活组合查询
 */
public class SearchRequest {
    
    /**
     * 搜索关键字
     * - 用于模糊匹配设备名称
     * - 示例：输入"朝阳"可匹配"朝阳220kV变电站"
     */
    private String keyword;
    
    /**
     * 电压等级筛选
     * - 可选值：220kV、110kV、35kV
     * - 为空表示不限制电压等级
     */
    private String voltageLevel;
    
    /**
     * 运行状态筛选
     * - 变电站/线路：运行中、停运、检修
     * - 台区：正常、异常、检修
     * - 为空表示不限制状态
     */
    private String status;
    
    /**
     * 最小纬度（地图视野范围筛选）
     * - 用于筛选当前地图可见范围内的设备
     * - 范围：-90 到 90
     */
    private Double minLat;
    
    /**
     * 最大纬度（地图视野范围筛选）
     * - 用于筛选当前地图可见范围内的设备
     * - 范围：-90 到 90
     */
    private Double maxLat;
    
    /**
     * 最小经度（地图视野范围筛选）
     * - 用于筛选当前地图可见范围内的设备
     * - 范围：-180 到 180
     */
    private Double minLng;
    
    /**
     * 最大经度（地图视野范围筛选）
     * - 用于筛选当前地图可见范围内的设备
     * - 范围：-180 到 180
     */
    private Double maxLng;
    
    /**
     * 判断是否有搜索关键字
     * @return true表示有关键字搜索
     */
    public boolean hasKeyword() {
        return keyword != null && !keyword.trim().isEmpty();
    }
    
    /**
     * 判断是否有电压等级筛选
     * @return true表示有电压等级筛选
     */
    public boolean hasVoltageLevel() {
        return voltageLevel != null && !voltageLevel.trim().isEmpty();
    }
    
    /**
     * 判断是否有状态筛选
     * @return true表示有状态筛选
     */
    public boolean hasStatus() {
        return status != null && !status.trim().isEmpty();
    }
    
    /**
     * 判断是否有地理范围筛选
     * @return true表示有地理范围筛选
     */
    public boolean hasBounds() {
        return minLat != null && maxLat != null && minLng != null && maxLng != null;
    }
    
    /**
     * 获取格式化的关键字（去除首尾空格）
     * @return 格式化后的关键字
     */
    public String getFormattedKeyword() {
        return keyword != null ? keyword.trim() : null;
    }
    
    // ==================== Getter 和 Setter 方法 ====================
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
    
    public Double getMinLat() {
        return minLat;
    }
    
    public void setMinLat(Double minLat) {
        this.minLat = minLat;
    }
    
    public Double getMaxLat() {
        return maxLat;
    }
    
    public void setMaxLat(Double maxLat) {
        this.maxLat = maxLat;
    }
    
    public Double getMinLng() {
        return minLng;
    }
    
    public void setMinLng(Double minLng) {
        this.minLng = minLng;
    }
    
    public Double getMaxLng() {
        return maxLng;
    }
    
    public void setMaxLng(Double maxLng) {
        this.maxLng = maxLng;
    }
}
