package com.electric.dto;

import java.util.Map;

/**
 * DashboardStats - 仪表盘统计数据封装类
 * 
 * 功能说明：
 * 封装仪表盘页面所需的所有统计数据
 * 包括设备统计、告警统计、运行状态统计等
 * 
 * 数据结构：
 * {
 *   "deviceStats": {
 *     "totalSubstations": 10,
 *     "totalLines": 15,
 *     "totalAreas": 50,
 *     "totalCustomers": 10000
 *   },
 *   "statusStats": {
 *     "running": 8,
 *     "stopped": 1,
 *     "maintenance": 1
 *   },
 *   "voltageStats": {
 *     "220kV": 3,
 *     "110kV": 5,
 *     "35kV": 2
 *   },
 *   "alertStats": {
 *     "total": 20,
 *     "active": 5,
 *     "critical": 1
 *   },
 *   "loadTrend": [...],
 *   "areaDistribution": {...}
 * }
 * 
 * 文件关联：
 * - StatsController: 使用此DTO返回统计数据
 * - 前端 DashboardView.vue: 解析此DTO展示仪表盘
 */
public class DashboardStats {
    
    /**
     * 设备统计
     */
    private DeviceStats deviceStats;
    
    /**
     * 运行状态统计
     */
    private Map<String, Long> statusStats;
    
    /**
     * 电压等级统计
     */
    private Map<String, Long> voltageStats;
    
    /**
     * 告警统计
     */
    private AlertStats alertStats;
    
    /**
     * 负载趋势数据
     */
    private java.util.List<TrendData> loadTrend;
    
    /**
     * 区域分布数据
     */
    private java.util.List<AreaDistribution> areaDistribution;

    /**
     * 设备统计内部类
     */
    public static class DeviceStats {
        private long totalSubstations;
        private long totalLines;
        private long totalAreas;
        private long totalCustomers;
        private long totalLength;
        private double avgLoadRate;
        private double totalCapacity;
        private double totalTransformerCapacity;
        
        public long getTotalSubstations() { return totalSubstations; }
        public void setTotalSubstations(long totalSubstations) { this.totalSubstations = totalSubstations; }
        public long getTotalLines() { return totalLines; }
        public void setTotalLines(long totalLines) { this.totalLines = totalLines; }
        public long getTotalAreas() { return totalAreas; }
        public void setTotalAreas(long totalAreas) { this.totalAreas = totalAreas; }
        public long getTotalCustomers() { return totalCustomers; }
        public void setTotalCustomers(long totalCustomers) { this.totalCustomers = totalCustomers; }
        public long getTotalLength() { return totalLength; }
        public void setTotalLength(long totalLength) { this.totalLength = totalLength; }
        public double getAvgLoadRate() { return avgLoadRate; }
        public void setAvgLoadRate(double avgLoadRate) { this.avgLoadRate = avgLoadRate; }
        public double getTotalCapacity() { return totalCapacity; }
        public void setTotalCapacity(double totalCapacity) { this.totalCapacity = totalCapacity; }
        public double getTotalTransformerCapacity() { return totalTransformerCapacity; }
        public void setTotalTransformerCapacity(double totalTransformerCapacity) { this.totalTransformerCapacity = totalTransformerCapacity; }
    }

    /**
     * 告警统计内部类
     */
    public static class AlertStats {
        private long total;
        private long active;
        private long acknowledged;
        private long resolved;
        private long critical;
        private long high;
        
        public long getTotal() { return total; }
        public void setTotal(long total) { this.total = total; }
        public long getActive() { return active; }
        public void setActive(long active) { this.active = active; }
        public long getAcknowledged() { return acknowledged; }
        public void setAcknowledged(long acknowledged) { this.acknowledged = acknowledged; }
        public long getResolved() { return resolved; }
        public void setResolved(long resolved) { this.resolved = resolved; }
        public long getCritical() { return critical; }
        public void setCritical(long critical) { this.critical = critical; }
        public long getHigh() { return high; }
        public void setHigh(long high) { this.high = high; }
    }

    /**
     * 趋势数据内部类
     */
    public static class TrendData {
        private String time;
        private double value;
        
        public TrendData(String time, double value) {
            this.time = time;
            this.value = value;
        }
        
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }
    }

    /**
     * 区域分布内部类
     */
    public static class AreaDistribution {
        private String name;
        private long count;
        private double percentage;
        
        public AreaDistribution(String name, long count, double percentage) {
            this.name = name;
            this.count = count;
            this.percentage = percentage;
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
        public double getPercentage() { return percentage; }
        public void setPercentage(double percentage) { this.percentage = percentage; }
    }

    // ==================== Getter 和 Setter 方法 ====================

    public DeviceStats getDeviceStats() { return deviceStats; }
    public void setDeviceStats(DeviceStats deviceStats) { this.deviceStats = deviceStats; }
    public Map<String, Long> getStatusStats() { return statusStats; }
    public void setStatusStats(Map<String, Long> statusStats) { this.statusStats = statusStats; }
    public Map<String, Long> getVoltageStats() { return voltageStats; }
    public void setVoltageStats(Map<String, Long> voltageStats) { this.voltageStats = voltageStats; }
    public AlertStats getAlertStats() { return alertStats; }
    public void setAlertStats(AlertStats alertStats) { this.alertStats = alertStats; }
    public java.util.List<TrendData> getLoadTrend() { return loadTrend; }
    public void setLoadTrend(java.util.List<TrendData> loadTrend) { this.loadTrend = loadTrend; }
    public java.util.List<AreaDistribution> getAreaDistribution() { return areaDistribution; }
    public void setAreaDistribution(java.util.List<AreaDistribution> areaDistribution) { this.areaDistribution = areaDistribution; }
}
