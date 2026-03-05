package com.electric.controller;

import com.electric.model.N1Check;
import com.electric.service.N1CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/n1-checks")
@CrossOrigin(origins = "*")
public class N1CheckController {
    
    @Autowired
    private N1CheckService n1CheckService;
    
    @GetMapping
    public List<N1Check> getAll() { return n1CheckService.findAll(); }
    
    @GetMapping("/{id}")
    public N1Check getById(@PathVariable Long id) { return n1CheckService.findById(id).orElse(null); }
    
    @GetMapping("/latest")
    public N1Check getLatest() { return n1CheckService.findLatestCompleted(); }
    
    @PostMapping("/perform")
    public N1Check perform(@RequestBody Map<String, Object> params) {
        String name = (String) params.getOrDefault("name", "N-1校验");
        String scope = (String) params.getOrDefault("scope", "全电网");
        String items = (String) params.getOrDefault("items", "[]");
        return n1CheckService.performCheck(name, scope, items);
    }
}
