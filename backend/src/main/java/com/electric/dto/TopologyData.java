package com.electric.dto;

import com.electric.model.Area;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import java.util.ArrayList;
import java.util.List;

/**
 * TopologyData - 拓扑关系数据封装类
 * 
 * 功能说明：
 * 封装设备之间的拓扑关系数据
 * 用于展示变电站、线路、台区之间的关联关系
 * 
 * 拓扑关系说明：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 变电站A ──────┬──────► 线路1 ──────► 变电站B                     │
 * │              │                                                 │
 * │              ├──► 台区1（供电范围）                             │
 * │              ├──► 台区2                                         │
 * │              └──► 台区3                                         │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 数据结构：
 * {
 *   "center": { 变电站信息 },
 *   "connectedLines": [ 相连的线路列表 ],
 *   "connectedSubstations": [ 相连的变电站列表 ],
 *   "suppliedAreas": [ 供电的台区列表 ],
 *   "statistics": { 统计信息 }
 * }
 * 
 * 使用场景：
 * 1. 点击变电站，展示其供电范围
 * 2. 点击线路，展示起止变电站
 * 3. 分析故障影响范围
 * 
 * 文件关联：
 * - TopologyController: 使用此DTO返回拓扑数据
 * - 前端 MapContainer.vue: 解析此数据展示拓扑关系
 */
public class TopologyData {
    
    /**
     * 中心设备信息
     * - 可以是变电站、线路或台区
     */
    private Object center;
    
    /**
     * 中心设备类型
     * - SUBSTATION: 变电站
     * - LINE: 输电线路
     * - AREA: 台区
     */
    private String centerType;
    
    /**
     * 相连的输电线路列表
     * - 以该变电站为起点或终点的线路
     */
    private List<TransmissionLine> connectedLines = new ArrayList<>();
    
    /**
     * 相连的变电站列表
     * - 通过线路连接的变电站
     */
    private List<Substation> connectedSubstations = new ArrayList<>();
    
    /**
     * 供电的台区列表
     * - 该变电站供电的台区
     */
    private List<Area> suppliedAreas = new ArrayList<>();
    
    /**
     * 拓扑统计信息
     */
    private TopologyStats statistics = new TopologyStats();

    /**
     * 拓扑统计内部类
     */
    public static class TopologyStats {
        private int connectedLineCount;
        private int connectedSubstationCount;
        private int suppliedAreaCount;
        private int totalCustomers;
        private double totalLineLength;
        
        public int getConnectedLineCount() { return connectedLineCount; }
        public void setConnectedLineCount(int connectedLineCount) { this.connectedLineCount = connectedLineCount; }
        public int getConnectedSubstationCount() { return connectedSubstationCount; }
        public void setConnectedSubstationCount(int connectedSubstationCount) { this.connectedSubstationCount = connectedSubstationCount; }
        public int getSuppliedAreaCount() { return suppliedAreaCount; }
        public void setSuppliedAreaCount(int suppliedAreaCount) { this.suppliedAreaCount = suppliedAreaCount; }
        public int getTotalCustomers() { return totalCustomers; }
        public void setTotalCustomers(int totalCustomers) { this.totalCustomers = totalCustomers; }
        public double getTotalLineLength() { return totalLineLength; }
        public void setTotalLineLength(double totalLineLength) { this.totalLineLength = totalLineLength; }
    }

    // ==================== Getter 和 Setter 方法 ====================

    public Object getCenter() { return center; }
    public void setCenter(Object center) { this.center = center; }
    public String getCenterType() { return centerType; }
    public void setCenterType(String centerType) { this.centerType = centerType; }
    public List<TransmissionLine> getConnectedLines() { return connectedLines; }
    public void setConnectedLines(List<TransmissionLine> connectedLines) { this.connectedLines = connectedLines; }
    public List<Substation> getConnectedSubstations() { return connectedSubstations; }
    public void setConnectedSubstations(List<Substation> connectedSubstations) { this.connectedSubstations = connectedSubstations; }
    public List<Area> getSuppliedAreas() { return suppliedAreas; }
    public void setSuppliedAreas(List<Area> suppliedAreas) { this.suppliedAreas = suppliedAreas; }
    public TopologyStats getStatistics() { return statistics; }
    public void setStatistics(TopologyStats statistics) { this.statistics = statistics; }
}
