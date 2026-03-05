package com.electric.controller;

import com.electric.model.MaintenancePlan;
import com.electric.service.MaintenancePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance-plans")
@CrossOrigin(origins = "*")
public class MaintenancePlanController {
    
    @Autowired
    private MaintenancePlanService maintenancePlanService;
    
    @GetMapping
    public List<MaintenancePlan> getAll() {
        return maintenancePlanService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MaintenancePlan> getById(@PathVariable Long id) {
        return maintenancePlanService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public List<MaintenancePlan> getByStatus(@PathVariable String status) {
        return maintenancePlanService.findByStatus(status);
    }
    
    @GetMapping("/active")
    public List<MaintenancePlan> getActive() {
        return maintenancePlanService.findActivePlans();
    }
    
    @PostMapping
    public MaintenancePlan create(@RequestBody MaintenancePlan plan) {
        return maintenancePlanService.save(plan);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MaintenancePlan> update(@PathVariable Long id, @RequestBody MaintenancePlan plan) {
        return maintenancePlanService.findById(id)
            .map(existing -> {
                plan.setId(id);
                return ResponseEntity.ok(maintenancePlanService.save(plan));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (maintenancePlanService.findById(id).isPresent()) {
            maintenancePlanService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/submit")
    public ResponseEntity<MaintenancePlan> submit(@PathVariable Long id) {
        MaintenancePlan plan = maintenancePlanService.submit(id);
        return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/approve")
    public ResponseEntity<MaintenancePlan> approve(@PathVariable Long id, 
                                                   @RequestParam String approver,
                                                   @RequestParam(required = false) String comment) {
        MaintenancePlan plan = maintenancePlanService.approve(id, approver, comment);
        return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/reject")
    public ResponseEntity<MaintenancePlan> reject(@PathVariable Long id,
                                                  @RequestParam String approver,
                                                  @RequestParam(required = false) String comment) {
        MaintenancePlan plan = maintenancePlanService.reject(id, approver, comment);
        return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/start")
    public ResponseEntity<MaintenancePlan> start(@PathVariable Long id) {
        MaintenancePlan plan = maintenancePlanService.start(id);
        return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<MaintenancePlan> complete(@PathVariable Long id, @RequestParam String result) {
        MaintenancePlan plan = maintenancePlanService.complete(id, result);
        return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.notFound().build();
    }
}
