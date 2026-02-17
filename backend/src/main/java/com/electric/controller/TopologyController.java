package com.electric.controller;

import com.electric.dto.TopologyData;
import com.electric.model.ApiResponse;
import com.electric.model.Area;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import com.electric.service.AreaService;
import com.electric.service.SubstationService;
import com.electric.service.TransmissionLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TopologyController - 拓扑关系控制器
 * 
 * 功能说明：
 * 提供设备拓扑关系查询的 RESTful API
 * 用于展示变电站、线路、台区之间的关联关系
 * 
 * API 接口列表：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ HTTP 方法 │ URL 路径                        │ 操作说明            │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ GET       │ /api/topology/substation/{id}   │ 获取变电站拓扑      │
 * │ GET       │ /api/topology/line/{id}         │ 获取线路拓扑        │
 * │ GET       │ /api/topology/area/{id}         │ 获取台区拓扑        │
 * │ GET       │ /api/topology/impact/{type}/{id}│ 故障影响分析        │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 拓扑关系说明：
 * 1. 变电站拓扑：
 *    - 相连的输电线路（起点或终点为该变电站）
 *    - 相连的其他变电站（通过线路连接）
 *    - 供电的台区列表
 * 
 * 2. 线路拓扑：
 *    - 起始变电站
 *    - 终止变电站
 *    - 途经的台区
 * 
 * 3. 台区拓扑：
 *    - 所属变电站
 *    - 同变电站的其他台区
 * 
 * 文件关联：
 * - SubstationService: 变电站业务逻辑
 * - TransmissionLineService: 线路业务逻辑
 * - AreaService: 台区业务逻辑
 * - TopologyData: 拓扑数据封装
 */
@RestController
@RequestMapping("/api/topology")
@CrossOrigin(origins = "*")
public class TopologyController {

    private final SubstationService substationService;
    private final TransmissionLineService transmissionLineService;
    private final AreaService areaService;

    public TopologyController(
            SubstationService substationService,
            TransmissionLineService transmissionLineService,
            AreaService areaService) {
        this.substationService = substationService;
        this.transmissionLineService = transmissionLineService;
        this.areaService = areaService;
    }

    /**
     * 获取变电站拓扑关系
     * 
     * HTTP 请求：
     * GET /api/topology/substation/{id}
     * 
     * 返回数据：
     * - 变电站信息
     * - 相连的输电线路
     * - 相连的其他变电站
     * - 供电的台区列表
     * - 统计信息
     * 
     * @param id 变电站ID
     * @return 拓扑数据
     */
    @GetMapping("/substation/{id}")
    public ResponseEntity<ApiResponse<TopologyData>> getSubstationTopology(@PathVariable Long id) {
        Substation substation = substationService.getSubstationById(id);
        if (substation == null) {
            return ResponseEntity.notFound().build();
        }
        
        TopologyData topology = new TopologyData();
        topology.setCenter(substation);
        topology.setCenterType("SUBSTATION");
        
        List<TransmissionLine> connectedLines = transmissionLineService.getAllTransmissionLines().stream()
                .filter(line -> substation.getName().equals(line.getStartSubstation()) 
                        || substation.getName().equals(line.getEndSubstation()))
                .collect(Collectors.toList());
        topology.setConnectedLines(connectedLines);
        
        List<String> connectedNames = new ArrayList<>();
        for (TransmissionLine line : connectedLines) {
            if (line.getStartSubstation().equals(substation.getName())) {
                connectedNames.add(line.getEndSubstation());
            } else {
                connectedNames.add(line.getStartSubstation());
            }
        }
        
        List<Substation> connectedSubstations = substationService.getAllSubstations().stream()
                .filter(s -> connectedNames.contains(s.getName()) && !s.getId().equals(id))
                .collect(Collectors.toList());
        topology.setConnectedSubstations(connectedSubstations);
        
        List<Area> suppliedAreas = areaService.getAllAreas().stream()
                .filter(area -> substation.getName().equals(area.getSubstationName()))
                .collect(Collectors.toList());
        topology.setSuppliedAreas(suppliedAreas);
        
        TopologyData.TopologyStats stats = topology.getStatistics();
        stats.setConnectedLineCount(connectedLines.size());
        stats.setConnectedSubstationCount(connectedSubstations.size());
        stats.setSuppliedAreaCount(suppliedAreas.size());
        stats.setTotalCustomers(suppliedAreas.stream()
                .mapToInt(a -> a.getCustomerCount() != null ? a.getCustomerCount() : 0)
                .sum());
        stats.setTotalLineLength(connectedLines.stream()
                .mapToDouble(l -> l.getLength() != null ? l.getLength() : 0)
                .sum());
        
        return ResponseEntity.ok(ApiResponse.success(topology));
    }

    /**
     * 获取输电线路拓扑关系
     * 
     * HTTP 请求：
     * GET /api/topology/line/{id}
     * 
     * @param id 线路ID
     * @return 拓扑数据
     */
    @GetMapping("/line/{id}")
    public ResponseEntity<ApiResponse<TopologyData>> getLineTopology(@PathVariable Long id) {
        TransmissionLine line = transmissionLineService.getTransmissionLineById(id);
        if (line == null) {
            return ResponseEntity.notFound().build();
        }
        
        TopologyData topology = new TopologyData();
        topology.setCenter(line);
        topology.setCenterType("LINE");
        
        List<Substation> connectedSubstations = substationService.getAllSubstations().stream()
                .filter(s -> s.getName().equals(line.getStartSubstation()) 
                        || s.getName().equals(line.getEndSubstation()))
                .collect(Collectors.toList());
        topology.setConnectedSubstations(connectedSubstations);
        
        List<TransmissionLine> connectedLines = new ArrayList<>();
        connectedLines.add(line);
        topology.setConnectedLines(connectedLines);
        
        TopologyData.TopologyStats stats = topology.getStatistics();
        stats.setConnectedLineCount(1);
        stats.setConnectedSubstationCount(connectedSubstations.size());
        stats.setTotalLineLength(line.getLength() != null ? line.getLength() : 0);
        
        return ResponseEntity.ok(ApiResponse.success(topology));
    }

    /**
     * 获取台区拓扑关系
     * 
     * HTTP 请求：
     * GET /api/topology/area/{id}
     * 
     * @param id 台区ID
     * @return 拓扑数据
     */
    @GetMapping("/area/{id}")
    public ResponseEntity<ApiResponse<TopologyData>> getAreaTopology(@PathVariable Long id) {
        Area area = areaService.getAreaById(id);
        if (area == null) {
            return ResponseEntity.notFound().build();
        }
        
        TopologyData topology = new TopologyData();
        topology.setCenter(area);
        topology.setCenterType("AREA");
        
        if (area.getSubstationName() != null) {
            List<Substation> connectedSubstations = substationService.getAllSubstations().stream()
                    .filter(s -> s.getName().equals(area.getSubstationName()))
                    .collect(Collectors.toList());
            topology.setConnectedSubstations(connectedSubstations);
            
            List<Area> suppliedAreas = areaService.getAllAreas().stream()
                    .filter(a -> area.getSubstationName().equals(a.getSubstationName()))
                    .collect(Collectors.toList());
            topology.setSuppliedAreas(suppliedAreas);
        }
        
        TopologyData.TopologyStats stats = topology.getStatistics();
        stats.setConnectedSubstationCount(topology.getConnectedSubstations().size());
        stats.setSuppliedAreaCount(topology.getSuppliedAreas().size());
        stats.setTotalCustomers(area.getCustomerCount() != null ? area.getCustomerCount() : 0);
        
        return ResponseEntity.ok(ApiResponse.success(topology));
    }

    /**
     * 故障影响分析
     * 
     * 分析某个设备故障会影响哪些其他设备
     * 
     * HTTP 请求：
     * GET /api/topology/impact/{type}/{id}
     * 
     * @param type 设备类型（substation/line/area）
     * @param id 设备ID
     * @return 影响范围数据
     */
    @GetMapping("/impact/{type}/{id}")
    public ResponseEntity<ApiResponse<TopologyData>> analyzeImpact(
            @PathVariable String type,
            @PathVariable Long id) {
        
        switch (type.toLowerCase()) {
            case "substation":
                return analyzeSubstationImpact(id);
            case "line":
                return analyzeLineImpact(id);
            case "area":
                return analyzeAreaImpact(id);
            default:
                return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 分析变电站故障影响
     */
    private ResponseEntity<ApiResponse<TopologyData>> analyzeSubstationImpact(Long id) {
        Substation substation = substationService.getSubstationById(id);
        if (substation == null) {
            return ResponseEntity.notFound().build();
        }
        
        TopologyData impact = new TopologyData();
        impact.setCenter(substation);
        impact.setCenterType("SUBSTATION");
        
        List<Area> affectedAreas = areaService.getAllAreas().stream()
                .filter(area -> substation.getName().equals(area.getSubstationName()))
                .collect(Collectors.toList());
        impact.setSuppliedAreas(affectedAreas);
        
        TopologyData.TopologyStats stats = impact.getStatistics();
        stats.setSuppliedAreaCount(affectedAreas.size());
        stats.setTotalCustomers(affectedAreas.stream()
                .mapToInt(a -> a.getCustomerCount() != null ? a.getCustomerCount() : 0)
                .sum());
        
        return ResponseEntity.ok(ApiResponse.success(impact));
    }

    /**
     * 分析线路故障影响
     */
    private ResponseEntity<ApiResponse<TopologyData>> analyzeLineImpact(Long id) {
        return getLineTopology(id);
    }

    /**
     * 分析台区故障影响
     */
    private ResponseEntity<ApiResponse<TopologyData>> analyzeAreaImpact(Long id) {
        return getAreaTopology(id);
    }
}
