package com.electric.controller;

import com.electric.model.ApiResponse;
import com.electric.model.OperationData;
import com.electric.service.OperationDataService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/operation-data")
@CrossOrigin(origins = "*")
public class OperationDataController {
    
    private final OperationDataService operationDataService;

    public OperationDataController(OperationDataService operationDataService) {
        this.operationDataService = operationDataService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OperationData>>> getAll() {
        List<OperationData> data = operationDataService.getAllOperationData();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OperationData>> getById(@PathVariable Long id) {
        return operationDataService.getOperationDataById(id)
                .map(data -> ResponseEntity.ok(ApiResponse.success(data)))
                .orElse(ResponseEntity.ok(ApiResponse.error("数据不存在")));
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<ApiResponse<List<OperationData>>> getByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        List<OperationData> data = operationDataService.getOperationDataByEntity(entityType, entityId);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    @GetMapping("/entity/{entityType}/{entityId}/latest")
    public ResponseEntity<ApiResponse<OperationData>> getLatestByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        return operationDataService.getLatestOperationData(entityType, entityId)
                .map(data -> ResponseEntity.ok(ApiResponse.success(data)))
                .orElse(ResponseEntity.ok(ApiResponse.success(null)));
    }
    
    @GetMapping("/entity/{entityType}/{entityId}/date/{date}")
    public ResponseEntity<ApiResponse<List<OperationData>>> getByEntityAndDate(
            @PathVariable String entityType,
            @PathVariable Long entityId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<OperationData> data = operationDataService.getOperationDataByEntityAndDate(entityType, entityId, date);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    @GetMapping("/entity/{entityType}/{entityId}/range")
    public ResponseEntity<ApiResponse<List<OperationData>>> getByEntityAndRange(
            @PathVariable String entityType,
            @PathVariable Long entityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<OperationData> data = operationDataService.getOperationDataByEntityAndTimeRange(entityType, entityId, start, end);
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    @GetMapping("/entity/{entityType}/{entityId}/dates")
    public ResponseEntity<ApiResponse<List<LocalDate>>> getDatesWithData(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        List<LocalDate> dates = operationDataService.getAvailableDates(entityType, entityId);
        return ResponseEntity.ok(ApiResponse.success(dates));
    }
}
