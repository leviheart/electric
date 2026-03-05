package com.electric.controller;

import com.electric.model.OperationLog;
import com.electric.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/operation-logs")
@CrossOrigin(origins = "*")
public class OperationLogController {
    
    @Autowired
    private OperationLogService operationLogService;
    
    @GetMapping
    public List<OperationLog> getAll() { return operationLogService.findAll(); }
    
    @GetMapping("/{id}")
    public ResponseEntity<OperationLog> getById(@PathVariable Long id) {
        return operationLogService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/username/{username}")
    public List<OperationLog> getByUsername(@PathVariable String username) { return operationLogService.findByUsername(username); }
    
    @GetMapping("/module/{module}")
    public List<OperationLog> getByModule(@PathVariable String module) { return operationLogService.findByModule(module); }
    
    @GetMapping("/recent")
    public List<OperationLog> getRecent(@RequestParam(defaultValue = "7") int days) { return operationLogService.findRecentLogs(days); }
    
    @GetMapping("/time-range")
    public List<OperationLog> getByTimeRange(@RequestParam String start, @RequestParam String end) {
        return operationLogService.findByTimeRange(LocalDateTime.parse(start), LocalDateTime.parse(end));
    }
    
    @GetMapping("/count-by-module")
    public List<Object[]> countByModule() { return operationLogService.countByModule(); }
    
    @GetMapping("/count-by-date")
    public List<Object[]> countByDate(@RequestParam(defaultValue = "30") int days) { return operationLogService.countByDate(days); }
    
    @GetMapping("/failed-count")
    public Long getFailedCount() { return operationLogService.countFailedOperations(); }
}
