package com.electric.controller;

import com.electric.model.PowerFlow;
import com.electric.service.PowerFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/power-flows")
@CrossOrigin(origins = "*")
public class PowerFlowController {
    
    @Autowired
    private PowerFlowService powerFlowService;
    
    @GetMapping
    public List<PowerFlow> getAll() { return powerFlowService.findAll(); }
    
    @GetMapping("/{id}")
    public PowerFlow getById(@PathVariable Long id) { return powerFlowService.findById(id).orElse(null); }
    
    @GetMapping("/latest")
    public PowerFlow getLatest() { return powerFlowService.findLatestCompleted(); }
    
    @PostMapping("/calculate")
    public PowerFlow calculate(@RequestBody Map<String, Object> params) {
        String name = (String) params.getOrDefault("name", "潮流计算");
        String nodes = (String) params.getOrDefault("nodes", "[]");
        String branches = (String) params.getOrDefault("branches", "[]");
        return powerFlowService.calculate(name, nodes, branches);
    }
}
