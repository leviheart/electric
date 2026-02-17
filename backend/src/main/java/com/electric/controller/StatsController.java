package com.electric.controller;

import com.electric.dto.DashboardStats;
import com.electric.model.ApiResponse;
import com.electric.model.Alert;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import com.electric.service.AlertService;
import com.electric.service.AreaService;
import com.electric.service.SubstationService;
import com.electric.service.TransmissionLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * StatsController - 统计数据控制器
 * 
 * 功能说明：
 * 提供仪表盘统计数据相关的 RESTful API
 * 包括设备统计、运行状态统计、告警统计等
 * 
 * API 接口列表：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ HTTP 方法 │ URL 路径              │ 操作说明                      │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ GET       │ /api/stats/dashboard │ 获取仪表盘统计数据             │
 * │ GET       │ /api/stats/devices   │ 获取设备统计                   │
 * │ GET       │ /api/stats/status    │ 获取运行状态统计               │
 * │ GET       │ /api/stats/voltage   │ 获取电压等级统计               │
 * │ GET       │ /api/stats/trend     │ 获取负载趋势数据               │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 统计数据说明：
 * - 设备统计：变电站、线路、台区、用户总数
 * - 状态统计：运行中、停运、检修数量
 * - 电压统计：220kV、110kV、35kV 设备数量
 * - 告警统计：各级别告警数量
 * - 趋势数据：模拟负载变化趋势
 * 
 * 文件关联：
 * - SubstationService: 变电站业务逻辑
 * - TransmissionLineService: 线路业务逻辑
 * - AreaService: 台区业务逻辑
 * - AlertService: 告警业务逻辑
 * - DashboardStats: 统计数据封装
 */
@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*")
public class StatsController {

    private final SubstationService substationService;
    private final TransmissionLineService transmissionLineService;
    private final AreaService areaService;
    private final AlertService alertService;

    public StatsController(
            SubstationService substationService,
            TransmissionLineService transmissionLineService,
            AreaService areaService,
            AlertService alertService) {
        this.substationService = substationService;
        this.transmissionLineService = transmissionLineService;
        this.areaService = areaService;
        this.alertService = alertService;
    }

    /**
     * 获取仪表盘统计数据
     * 
     * 汇总所有统计数据，用于仪表盘页面展示
     * 
     * HTTP 请求：
     * GET /api/stats/dashboard
     * 
     * @return 仪表盘统计数据
     */
    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<DashboardStats>> getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        stats.setDeviceStats(getDeviceStats());
        stats.setStatusStats(getStatusStatsMap());
        stats.setVoltageStats(getVoltageStatsMap());
        stats.setAlertStats(getAlertStatsData());
        stats.setLoadTrend(generateLoadTrend());
        stats.setAreaDistribution(generateAreaDistribution());
        
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    /**
     * 获取设备统计数据
     * 
     * HTTP 请求：
     * GET /api/stats/devices
     * 
     * @return 设备统计数据
     */
    @GetMapping("/devices")
    public ResponseEntity<ApiResponse<DashboardStats.DeviceStats>> getDeviceStatsEndpoint() {
        return ResponseEntity.ok(ApiResponse.success(getDeviceStats()));
    }

    /**
     * 获取运行状态统计
     * 
     * HTTP 请求：
     * GET /api/stats/status
     * 
     * @return 运行状态统计
     */
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getStatusStatsEndpoint() {
        return ResponseEntity.ok(ApiResponse.success(getStatusStatsMap()));
    }

    /**
     * 获取电压等级统计
     * 
     * HTTP 请求：
     * GET /api/stats/voltage
     * 
     * @return 电压等级统计
     */
    @GetMapping("/voltage")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getVoltageStatsEndpoint() {
        return ResponseEntity.ok(ApiResponse.success(getVoltageStatsMap()));
    }

    /**
     * 获取负载趋势数据
     * 
     * HTTP 请求：
     * GET /api/stats/trend
     * 
     * @return 负载趋势数据
     */
    @GetMapping("/trend")
    public ResponseEntity<ApiResponse<List<DashboardStats.TrendData>>> getLoadTrendEndpoint() {
        return ResponseEntity.ok(ApiResponse.success(generateLoadTrend()));
    }

    // ==================== 私有方法 ====================

    /**
     * 获取设备统计数据
     */
    private DashboardStats.DeviceStats getDeviceStats() {
        DashboardStats.DeviceStats stats = new DashboardStats.DeviceStats();
        
        stats.setTotalSubstations(substationService.getAllSubstations().size());
        stats.setTotalLines(transmissionLineService.getAllTransmissionLines().size());
        stats.setTotalAreas(areaService.getAllAreas().size());
        
        long totalCustomers = areaService.getAllAreas().stream()
                .mapToLong(area -> area.getCustomerCount() != null ? area.getCustomerCount() : 0)
                .sum();
        stats.setTotalCustomers(totalCustomers);
        
        double totalLength = transmissionLineService.getAllTransmissionLines().stream()
                .mapToDouble(line -> line.getLength() != null ? line.getLength() : 0)
                .sum();
        stats.setTotalLength((long) totalLength);
        
        return stats;
    }

    /**
     * 获取运行状态统计
     */
    private Map<String, Long> getStatusStatsMap() {
        Map<String, Long> statusStats = new HashMap<>();
        
        Map<String, Long> substationStatus = substationService.getAllSubstations().stream()
                .collect(Collectors.groupingBy(
                        s -> s.getStatus() != null ? s.getStatus() : "未知",
                        Collectors.counting()
                ));
        
        Map<String, Long> lineStatus = transmissionLineService.getAllTransmissionLines().stream()
                .collect(Collectors.groupingBy(
                        l -> l.getStatus() != null ? l.getStatus() : "未知",
                        Collectors.counting()
                ));
        
        Map<String, Long> areaStatus = areaService.getAllAreas().stream()
                .collect(Collectors.groupingBy(
                        a -> a.getStatus() != null ? a.getStatus() : "未知",
                        Collectors.counting()
                ));
        
        long running = substationStatus.getOrDefault("运行中", 0L) 
                + lineStatus.getOrDefault("运行中", 0L)
                + areaStatus.getOrDefault("正常", 0L);
        long stopped = substationStatus.getOrDefault("停运", 0L) 
                + lineStatus.getOrDefault("停运", 0L)
                + areaStatus.getOrDefault("异常", 0L);
        long maintenance = substationStatus.getOrDefault("检修", 0L) 
                + lineStatus.getOrDefault("检修", 0L)
                + areaStatus.getOrDefault("检修", 0L);
        
        statusStats.put("运行中", running);
        statusStats.put("停运", stopped);
        statusStats.put("检修", maintenance);
        
        return statusStats;
    }

    /**
     * 获取电压等级统计
     */
    private Map<String, Long> getVoltageStatsMap() {
        Map<String, Long> voltageStats = new HashMap<>();
        
        Map<String, Long> substationVoltage = substationService.getAllSubstations().stream()
                .filter(s -> s.getVoltageLevel() != null)
                .collect(Collectors.groupingBy(
                        Substation::getVoltageLevel,
                        Collectors.counting()
                ));
        
        Map<String, Long> lineVoltage = transmissionLineService.getAllTransmissionLines().stream()
                .filter(l -> l.getVoltageLevel() != null)
                .collect(Collectors.groupingBy(
                        TransmissionLine::getVoltageLevel,
                        Collectors.counting()
                ));
        
        voltageStats.put("220kV", 
                substationVoltage.getOrDefault("220kV", 0L) + lineVoltage.getOrDefault("220kV", 0L));
        voltageStats.put("110kV", 
                substationVoltage.getOrDefault("110kV", 0L) + lineVoltage.getOrDefault("110kV", 0L));
        voltageStats.put("35kV", 
                substationVoltage.getOrDefault("35kV", 0L) + lineVoltage.getOrDefault("35kV", 0L));
        
        return voltageStats;
    }

    /**
     * 获取告警统计数据
     */
    private DashboardStats.AlertStats getAlertStatsData() {
        DashboardStats.AlertStats stats = new DashboardStats.AlertStats();
        
        stats.setTotal(alertService.getAllAlerts().size());
        stats.setActive(alertService.countByStatus(Alert.AlertStatus.ACTIVE));
        stats.setAcknowledged(alertService.countByStatus(Alert.AlertStatus.ACKNOWLEDGED));
        stats.setResolved(alertService.countByStatus(Alert.AlertStatus.RESOLVED));
        stats.setCritical(alertService.countByLevel(Alert.AlertLevel.CRITICAL));
        stats.setHigh(alertService.countByLevel(Alert.AlertLevel.HIGH));
        
        return stats;
    }

    /**
     * 生成负载趋势数据（模拟数据）
     */
    private List<DashboardStats.TrendData> generateLoadTrend() {
        List<DashboardStats.TrendData> trend = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 23; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR, -i);
            String time = String.format("%02d:00", cal.get(Calendar.HOUR_OF_DAY));
            double value = 50 + random.nextDouble() * 40;
            trend.add(new DashboardStats.TrendData(time, Math.round(value * 10.0) / 10.0));
        }
        
        return trend;
    }

    /**
     * 生成区域分布数据
     */
    private List<DashboardStats.AreaDistribution> generateAreaDistribution() {
        List<DashboardStats.AreaDistribution> distribution = new ArrayList<>();
        
        Map<String, Long> areaBySubstation = areaService.getAllAreas().stream()
                .filter(a -> a.getSubstationName() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getSubstationName(),
                        Collectors.counting()
                ));
        
        long total = areaService.getAllAreas().size();
        
        areaBySubstation.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .forEach(entry -> {
                    double percentage = total > 0 ? (entry.getValue() * 100.0 / total) : 0;
                    distribution.add(new DashboardStats.AreaDistribution(
                            entry.getKey(), 
                            entry.getValue(), 
                            Math.round(percentage * 10.0) / 10.0
                    ));
                });
        
        return distribution;
    }
}
