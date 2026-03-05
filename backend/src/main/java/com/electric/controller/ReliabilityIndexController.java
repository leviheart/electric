package com.electric.controller;

import com.electric.model.ReliabilityIndex;
import com.electric.service.ReliabilityIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reliability-indices")
@CrossOrigin(origins = "*")
public class ReliabilityIndexController {
    
    @Autowired
    private ReliabilityIndexService reliabilityIndexService;
    
    @GetMapping
    public List<ReliabilityIndex> getAll() { return reliabilityIndexService.findAll(); }
    
    @GetMapping("/{id}")
    public ReliabilityIndex getById(@PathVariable Long id) { return reliabilityIndexService.findById(id).orElse(null); }
    
    @GetMapping("/target/{targetType}/{targetId}")
    public ReliabilityIndex getByTarget(@PathVariable String targetType, @PathVariable Long targetId) {
        return reliabilityIndexService.findLatestByTarget(targetType, targetId).orElse(null);
    }
    
    @PostMapping("/calculate")
    public ReliabilityIndex calculate(@RequestBody Map<String, Object> params) {
        String targetType = (String) params.get("targetType");
        Long targetId = Long.valueOf(params.get("targetId").toString());
        String targetName = (String) params.get("targetName");
        String timeGranularity = (String) params.getOrDefault("timeGranularity", "月度");
        int totalCustomerCount = params.get("totalCustomerCount") != null ? Integer.parseInt(params.get("totalCustomerCount").toString()) : 0;
        int totalOutageCount = params.get("totalOutageCount") != null ? Integer.parseInt(params.get("totalOutageCount").toString()) : 0;
        int plannedOutageCount = params.get("plannedOutageCount") != null ? Integer.parseInt(params.get("plannedOutageCount").toString()) : 0;
        int unplannedOutageCount = params.get("unplannedOutageCount") != null ? Integer.parseInt(params.get("unplannedOutageCount").toString()) : 0;
        int totalOutageDuration = params.get("totalOutageDuration") != null ? Integer.parseInt(params.get("totalOutageDuration").toString()) : 0;
        int plannedOutageDuration = params.get("plannedOutageDuration") != null ? Integer.parseInt(params.get("plannedOutageDuration").toString()) : 0;
        int unplannedOutageDuration = params.get("unplannedOutageDuration") != null ? Integer.parseInt(params.get("unplannedOutageDuration").toString()) : 0;
        int customerInterruptCount = params.get("customerInterruptCount") != null ? Integer.parseInt(params.get("customerInterruptCount").toString()) : 0;
        int momentaryOutageCount = params.get("momentaryOutageCount") != null ? Integer.parseInt(params.get("momentaryOutageCount").toString()) : 0;
        return reliabilityIndexService.calculate(targetType, targetId, targetName, timeGranularity, 
            totalCustomerCount, totalOutageCount, plannedOutageCount, unplannedOutageCount,
            totalOutageDuration, plannedOutageDuration, unplannedOutageDuration, 
            customerInterruptCount, momentaryOutageCount);
    }
}
