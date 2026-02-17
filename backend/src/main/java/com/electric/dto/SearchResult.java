package com.electric.dto;

import com.electric.model.Area;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import java.util.ArrayList;
import java.util.List;

/**
 * SearchResult - 全局搜索结果封装类
 * 
 * 功能说明：
 * 封装全局搜索的结果，包含变电站、线路、台区的匹配结果
 * 用于全局搜索接口返回统一格式的搜索结果
 * 
 * 返回结构：
 * {
 *   "substations": [...],  // 匹配的变电站列表
 *   "lines": [...],        // 匹配的输电线路列表
 *   "areas": [...],        // 匹配的台区列表
 *   "totalCount": 15,      // 总匹配数量
 *   "keyword": "朝阳"      // 搜索关键字
 * }
 * 
 * 使用场景：
 * 1. 全局搜索框：用户输入关键字，返回所有匹配的设备
 * 2. 快速定位：点击搜索结果跳转到地图对应位置
 * 3. 数据统计：显示各类型设备的匹配数量
 * 
 * 文件关联：
 * - SearchController: 使用此DTO返回搜索结果
 * - 前端 SearchBox.vue: 解析此DTO展示搜索结果
 * 
 * 新人提示：
 * - 使用泛型 List 存储不同类型的搜索结果
 * - totalCount 方法用于计算总匹配数量，便于前端显示
 */
public class SearchResult {
    
    /**
     * 匹配的变电站列表
     * - 包含名称或属性匹配关键字的变电站
     */
    private List<Substation> substations = new ArrayList<>();
    
    /**
     * 匹配的输电线路列表
     * - 包含名称或属性匹配关键字的线路
     */
    private List<TransmissionLine> lines = new ArrayList<>();
    
    /**
     * 匹配的台区列表
     * - 包含名称或属性匹配关键字的台区
     */
    private List<Area> areas = new ArrayList<>();
    
    /**
     * 搜索关键字
     * - 记录用户输入的搜索词
     * - 用于前端显示"搜索'xxx'的结果"
     */
    private String keyword;
    
    /**
     * 计算总匹配数量
     * @return 所有类型匹配数量的总和
     */
    public int getTotalCount() {
        int count = 0;
        if (substations != null) count += substations.size();
        if (lines != null) count += lines.size();
        if (areas != null) count += areas.size();
        return count;
    }
    
    /**
     * 判断是否有搜索结果
     * @return true表示至少有一个匹配结果
     */
    public boolean hasResults() {
        return getTotalCount() > 0;
    }
    
    /**
     * 获取各类型结果数量的描述字符串
     * @return 格式如 "变电站: 3, 线路: 2, 台区: 1"
     */
    public String getResultSummary() {
        return String.format("变电站: %d, 线路: %d, 台区: %d", 
            substations != null ? substations.size() : 0,
            lines != null ? lines.size() : 0,
            areas != null ? areas.size() : 0);
    }
    
    // ==================== Getter 和 Setter 方法 ====================
    
    public List<Substation> getSubstations() {
        return substations;
    }
    
    public void setSubstations(List<Substation> substations) {
        this.substations = substations != null ? substations : new ArrayList<>();
    }
    
    public List<TransmissionLine> getLines() {
        return lines;
    }
    
    public void setLines(List<TransmissionLine> lines) {
        this.lines = lines != null ? lines : new ArrayList<>();
    }
    
    public List<Area> getAreas() {
        return areas;
    }
    
    public void setAreas(List<Area> areas) {
        this.areas = areas != null ? areas : new ArrayList<>();
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
