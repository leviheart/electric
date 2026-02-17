package com.electric.controller;

import com.electric.dto.SearchRequest;
import com.electric.dto.SearchResult;
import com.electric.model.ApiResponse;
import com.electric.model.Area;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import com.electric.service.AreaService;
import com.electric.service.SubstationService;
import com.electric.service.TransmissionLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SearchController - 全局搜索控制器
 * 
 * 功能说明：
 * 提供全局搜索功能，支持跨设备类型（变电站、线路、台区）搜索
 * 用户可以通过关键字快速定位设备，并跳转到地图对应位置
 * 
 * API 接口列表：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ HTTP 方法 │ URL 路径              │ 操作说明                      │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ GET       │ /api/search           │ 全局搜索（跨所有设备类型）     │
 * │ GET       │ /api/search/substations│ 搜索变电站                    │
 * │ GET       │ /api/search/lines     │ 搜索输电线路                   │
 * │ GET       │ /api/search/areas     │ 搜索台区                       │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 搜索逻辑：
 * 1. 关键字搜索：模糊匹配设备名称
 * 2. 条件筛选：支持电压等级、状态等条件
 * 3. 地理范围筛选：只返回地图可见范围内的设备
 * 
 * 使用场景：
 * - 用户在搜索框输入关键字，实时显示匹配结果
 * - 用户点击搜索结果，地图自动定位到对应位置
 * - 用户使用高级筛选，按条件过滤设备
 * 
 * 请求示例：
 * GET /api/search?keyword=朝阳
 * GET /api/search?keyword=220kV&voltageLevel=220kV
 * 
 * 响应示例：
 * {
 *   "code": 200,
 *   "message": "success",
 *   "data": {
 *     "substations": [...],
 *     "lines": [...],
 *     "areas": [...],
 *     "totalCount": 10,
 *     "keyword": "朝阳"
 *   }
 * }
 * 
 * 文件关联：
 * - SubstationService: 变电站业务逻辑
 * - TransmissionLineService: 线路业务逻辑
 * - AreaService: 台区业务逻辑
 * - SearchResult: 搜索结果封装
 * - SearchRequest: 搜索参数封装
 * 
 * 新人提示：
 * - 全局搜索会查询所有设备类型，性能优化时可考虑异步查询
 * - 关键字搜索使用 Java Stream 的 filter 方法
 * - 地理范围筛选需要计算坐标是否在矩形区域内
 */
@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    private final SubstationService substationService;
    private final TransmissionLineService transmissionLineService;
    private final AreaService areaService;

    /**
     * 构造函数依赖注入
     * @param substationService 变电站服务
     * @param transmissionLineService 输电线路服务
     * @param areaService 台区服务
     */
    public SearchController(
            SubstationService substationService,
            TransmissionLineService transmissionLineService,
            AreaService areaService) {
        this.substationService = substationService;
        this.transmissionLineService = transmissionLineService;
        this.areaService = areaService;
    }

    /**
     * 全局搜索接口
     * 
     * 同时搜索变电站、线路、台区，返回所有匹配结果
     * 
     * HTTP 请求：
     * GET /api/search?keyword=xxx&voltageLevel=xxx&status=xxx
     * 
     * @param keyword 搜索关键字（可选）
     * @param voltageLevel 电压等级筛选（可选）
     * @param status 状态筛选（可选）
     * @param minLat 最小纬度（可选）
     * @param maxLat 最大纬度（可选）
     * @param minLng 最小经度（可选）
     * @param maxLng 最大经度（可选）
     * @return 搜索结果
     */
    @GetMapping
    public ResponseEntity<ApiResponse<SearchResult>> globalSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String voltageLevel,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minLat,
            @RequestParam(required = false) Double maxLat,
            @RequestParam(required = false) Double minLng,
            @RequestParam(required = false) Double maxLng) {
        
        SearchResult result = new SearchResult();
        result.setKeyword(keyword);
        
        result.setSubstations(searchSubstations(keyword, voltageLevel, status, minLat, maxLat, minLng, maxLng));
        result.setLines(searchLines(keyword, voltageLevel, status));
        result.setAreas(searchAreas(keyword, status));
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    /**
     * 搜索变电站
     * 
     * HTTP 请求：
     * GET /api/search/substations?keyword=xxx&voltageLevel=xxx
     * 
     * @param keyword 搜索关键字
     * @param voltageLevel 电压等级
     * @param status 状态
     * @param minLat 最小纬度
     * @param maxLat 最大纬度
     * @param minLng 最小经度
     * @param maxLng 最大经度
     * @return 匹配的变电站列表
     */
    @GetMapping("/substations")
    public ResponseEntity<ApiResponse<List<Substation>>> searchSubstationsEndpoint(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String voltageLevel,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Double minLat,
            @RequestParam(required = false) Double maxLat,
            @RequestParam(required = false) Double minLng,
            @RequestParam(required = false) Double maxLng) {
        
        List<Substation> results = searchSubstations(keyword, voltageLevel, status, minLat, maxLat, minLng, maxLng);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    /**
     * 搜索输电线路
     * 
     * HTTP 请求：
     * GET /api/search/lines?keyword=xxx&voltageLevel=xxx
     * 
     * @param keyword 搜索关键字
     * @param voltageLevel 电压等级
     * @param status 状态
     * @return 匹配的线路列表
     */
    @GetMapping("/lines")
    public ResponseEntity<ApiResponse<List<TransmissionLine>>> searchLinesEndpoint(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String voltageLevel,
            @RequestParam(required = false) String status) {
        
        List<TransmissionLine> results = searchLines(keyword, voltageLevel, status);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    /**
     * 搜索台区
     * 
     * HTTP 请求：
     * GET /api/search/areas?keyword=xxx&status=xxx
     * 
     * @param keyword 搜索关键字
     * @param status 状态
     * @return 匹配的台区列表
     */
    @GetMapping("/areas")
    public ResponseEntity<ApiResponse<List<Area>>> searchAreasEndpoint(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status) {
        
        List<Area> results = searchAreas(keyword, status);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    /**
     * 搜索变电站的内部方法
     * 
     * 搜索逻辑：
     * 1. 获取所有变电站
     * 2. 按关键字过滤（名称包含关键字）
     * 3. 按电压等级过滤
     * 4. 按状态过滤
     * 5. 按地理范围过滤
     * 
     * @param keyword 关键字
     * @param voltageLevel 电压等级
     * @param status 状态
     * @param minLat 最小纬度
     * @param maxLat 最大纬度
     * @param minLng 最小经度
     * @param maxLng 最大经度
     * @return 过滤后的变电站列表
     */
    private List<Substation> searchSubstations(
            String keyword, String voltageLevel, String status,
            Double minLat, Double maxLat, Double minLng, Double maxLng) {
        
        List<Substation> allSubstations = substationService.getAllSubstations();
        
        return allSubstations.stream()
                .filter(s -> matchesKeyword(s.getName(), keyword))
                .filter(s -> matchesVoltageLevel(s.getVoltageLevel(), voltageLevel))
                .filter(s -> matchesStatus(s.getStatus(), status))
                .filter(s -> isWithinBounds(s.getLatitude(), s.getLongitude(), 
                        minLat, maxLat, minLng, maxLng))
                .collect(Collectors.toList());
    }

    /**
     * 搜索输电线路的内部方法
     * 
     * @param keyword 关键字
     * @param voltageLevel 电压等级
     * @param status 状态
     * @return 过滤后的线路列表
     */
    private List<TransmissionLine> searchLines(
            String keyword, String voltageLevel, String status) {
        
        List<TransmissionLine> allLines = transmissionLineService.getAllTransmissionLines();
        
        return allLines.stream()
                .filter(l -> matchesKeyword(l.getName(), keyword) || 
                            matchesKeyword(l.getStartSubstation(), keyword) ||
                            matchesKeyword(l.getEndSubstation(), keyword))
                .filter(l -> matchesVoltageLevel(l.getVoltageLevel(), voltageLevel))
                .filter(l -> matchesStatus(l.getStatus(), status))
                .collect(Collectors.toList());
    }

    /**
     * 搜索台区的内部方法
     * 
     * @param keyword 关键字
     * @param status 状态
     * @return 过滤后的台区列表
     */
    private List<Area> searchAreas(String keyword, String status) {
        List<Area> allAreas = areaService.getAllAreas();
        
        return allAreas.stream()
                .filter(a -> matchesKeyword(a.getName(), keyword) ||
                            matchesKeyword(a.getSubstationName(), keyword))
                .filter(a -> matchesStatus(a.getStatus(), status))
                .collect(Collectors.toList());
    }

    /**
     * 检查字符串是否包含关键字（不区分大小写）
     * 
     * @param text 待检查的文本
     * @param keyword 关键字
     * @return true表示匹配
     */
    private boolean matchesKeyword(String text, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        if (text == null) {
            return false;
        }
        return text.toLowerCase().contains(keyword.toLowerCase().trim());
    }

    /**
     * 检查电压等级是否匹配
     * 
     * @param actual 实际电压等级
     * @param expected 期望电压等级
     * @return true表示匹配
     */
    private boolean matchesVoltageLevel(String actual, String expected) {
        if (expected == null || expected.trim().isEmpty()) {
            return true;
        }
        return expected.equals(actual);
    }

    /**
     * 检查状态是否匹配
     * 
     * @param actual 实际状态
     * @param expected 期望状态
     * @return true表示匹配
     */
    private boolean matchesStatus(String actual, String expected) {
        if (expected == null || expected.trim().isEmpty()) {
            return true;
        }
        return expected.equals(actual);
    }

    /**
     * 检查坐标是否在指定范围内
     * 
     * 用于筛选地图可见范围内的设备
     * 
     * @param lat 纬度
     * @param lng 经度
     * @param minLat 最小纬度
     * @param maxLat 最大纬度
     * @param minLng 最小经度
     * @param maxLng 最大经度
     * @return true表示在范围内
     */
    private boolean isWithinBounds(Double lat, Double lng,
            Double minLat, Double maxLat, Double minLng, Double maxLng) {
        if (minLat == null || maxLat == null || minLng == null || maxLng == null) {
            return true;
        }
        if (lat == null || lng == null) {
            return false;
        }
        return lat >= minLat && lat <= maxLat && lng >= minLng && lng <= maxLng;
    }
}
