package com.electric.controller;

import com.electric.model.Alert;
import com.electric.model.ApiResponse;
import com.electric.service.AlertService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * AlertController - 告警控制器
 * 
 * 功能说明：
 * 提供告警相关的 RESTful API 和 WebSocket 消息处理
 * 支持告警的创建、查询、确认、解决等操作
 * 
 * RESTful API 列表：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ HTTP 方法 │ URL 路径              │ 操作说明                      │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ GET       │ /api/alerts          │ 获取所有告警列表               │
 * │ GET       │ /api/alerts/active   │ 获取活动告警列表               │
 * │ GET       │ /api/alerts/{id}     │ 获取单个告警详情               │
 * │ POST      │ /api/alerts          │ 创建告警                       │
 * │ PUT       │ /api/alerts/{id}/ack │ 确认告警                       │
 * │ PUT       │ /api/alerts/{id}/resolve │ 解决告警                   │
 * │ DELETE    │ /api/alerts/{id}     │ 删除告警                       │
 * │ GET       │ /api/alerts/stats    │ 获取告警统计                   │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * WebSocket 消息处理：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 消息目的地            │ 说明                                    │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ /app/alerts/ack      │ 确认告警（WebSocket 方式）              │
 * │ /app/alerts/resolve  │ 解决告警（WebSocket 方式）              │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - AlertService: 告警业务逻辑
 * - Alert: 告警实体类
 * - WebSocketConfig: WebSocket 配置
 */
@RestController
@RequestMapping("/api/alerts")
@CrossOrigin(origins = "*")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * 获取所有告警列表
     * 
     * HTTP 请求：
     * GET /api/alerts
     * 
     * @return 告警列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Alert>>> getAllAlerts() {
        List<Alert> alerts = alertService.getAllAlerts();
        return ResponseEntity.ok(ApiResponse.success(alerts));
    }

    /**
     * 获取活动告警列表
     * 
     * HTTP 请求：
     * GET /api/alerts/active
     * 
     * @return 活动告警列表
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Alert>>> getActiveAlerts() {
        List<Alert> alerts = alertService.getActiveAlerts();
        return ResponseEntity.ok(ApiResponse.success(alerts));
    }

    /**
     * 获取单个告警详情
     * 
     * HTTP 请求：
     * GET /api/alerts/{id}
     * 
     * @param id 告警ID
     * @return 告警详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Alert>> getAlertById(@PathVariable String id) {
        return alertService.getAlertById(id)
                .map(alert -> ResponseEntity.ok(ApiResponse.success(alert)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 创建告警
     * 
     * HTTP 请求：
     * POST /api/alerts
     * Content-Type: application/json
     * 
     * 请求体示例：
     * {
     *   "type": "DEVICE_OFFLINE",
     *   "level": "HIGH",
     *   "deviceType": "SUBSTATION",
     *   "deviceId": 1,
     *   "deviceName": "朝阳220kV变电站",
     *   "title": "设备离线告警",
     *   "message": "设备已离线，请检查连接状态",
     *   "latitude": 39.9,
     *   "longitude": 116.4
     * }
     * 
     * @param alert 告警对象
     * @return 创建后的告警
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Alert>> createAlert(@RequestBody Alert alert) {
        Alert createdAlert = alertService.createAlert(alert);
        return ResponseEntity.ok(ApiResponse.success("告警创建成功", createdAlert));
    }

    /**
     * 确认告警
     * 
     * HTTP 请求：
     * PUT /api/alerts/{id}/ack
     * 
     * @param id 告警ID
     * @param body 请求体，包含 username 字段
     * @return 更新后的告警
     */
    @PutMapping("/{id}/ack")
    public ResponseEntity<ApiResponse<Alert>> acknowledgeAlert(
            @PathVariable String id,
            @RequestBody Map<String, String> body) {
        String username = body.getOrDefault("username", "system");
        Alert alert = alertService.acknowledgeAlert(id, username);
        if (alert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("告警已确认", alert));
    }

    /**
     * 解决告警
     * 
     * HTTP 请求：
     * PUT /api/alerts/{id}/resolve
     * 
     * @param id 告警ID
     * @return 更新后的告警
     */
    @PutMapping("/{id}/resolve")
    public ResponseEntity<ApiResponse<Alert>> resolveAlert(@PathVariable String id) {
        Alert alert = alertService.resolveAlert(id);
        if (alert == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.success("告警已解决", alert));
    }

    /**
     * 删除告警
     * 
     * HTTP 请求：
     * DELETE /api/alerts/{id}
     * 
     * @param id 告警ID
     * @return 无内容响应
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAlert(@PathVariable String id) {
        alertService.deleteAlert(id);
        return ResponseEntity.ok(ApiResponse.success("告警已删除", null));
    }

    /**
     * 获取告警统计
     * 
     * HTTP 请求：
     * GET /api/alerts/stats
     * 
     * @return 告警统计数据
     */
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAlertStats() {
        Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("total", alertService.getAllAlerts().size());
        stats.put("active", alertService.countByStatus(Alert.AlertStatus.ACTIVE));
        stats.put("acknowledged", alertService.countByStatus(Alert.AlertStatus.ACKNOWLEDGED));
        stats.put("resolved", alertService.countByStatus(Alert.AlertStatus.RESOLVED));
        stats.put("critical", alertService.countByLevel(Alert.AlertLevel.CRITICAL));
        stats.put("high", alertService.countByLevel(Alert.AlertLevel.HIGH));
        stats.put("medium", alertService.countByLevel(Alert.AlertLevel.MEDIUM));
        stats.put("low", alertService.countByLevel(Alert.AlertLevel.LOW));
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    // ==================== WebSocket 消息处理 ====================

    /**
     * WebSocket 方式确认告警
     * 
     * 客户端发送消息到 /app/alerts/ack
     * 
     * @param payload 消息内容，包含 alertId 和 username
     */
    @MessageMapping("/alerts/ack")
    public void acknowledgeAlertViaWebSocket(@Payload Map<String, String> payload,
                                              SimpMessageHeaderAccessor headerAccessor) {
        String alertId = payload.get("alertId");
        String username = payload.getOrDefault("username", "anonymous");
        alertService.acknowledgeAlert(alertId, username);
    }

    /**
     * WebSocket 方式解决告警
     * 
     * 客户端发送消息到 /app/alerts/resolve
     * 
     * @param payload 消息内容，包含 alertId
     */
    @MessageMapping("/alerts/resolve")
    public void resolveAlertViaWebSocket(@Payload Map<String, String> payload) {
        String alertId = payload.get("alertId");
        alertService.resolveAlert(alertId);
    }
}
