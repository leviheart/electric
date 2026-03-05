package com.electric.controller;

import com.electric.model.InspectionTask;
import com.electric.service.InspectionTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inspection-tasks")
@CrossOrigin(origins = "*")
public class InspectionTaskController {
    
    @Autowired
    private InspectionTaskService inspectionTaskService;
    
    @GetMapping
    public List<InspectionTask> getAll() { return inspectionTaskService.findAll(); }
    
    @GetMapping("/{id}")
    public ResponseEntity<InspectionTask> getById(@PathVariable Long id) {
        return inspectionTaskService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public List<InspectionTask> getByStatus(@PathVariable String status) { return inspectionTaskService.findByStatus(status); }
    
    @GetMapping("/active")
    public List<InspectionTask> getActive() { return inspectionTaskService.findActiveTasks(); }
    
    @PostMapping
    public InspectionTask create(@RequestBody InspectionTask task) { return inspectionTaskService.save(task); }
    
    @PutMapping("/{id}")
    public ResponseEntity<InspectionTask> update(@PathVariable Long id, @RequestBody InspectionTask task) {
        return inspectionTaskService.findById(id).map(existing -> { task.setId(id); return ResponseEntity.ok(inspectionTaskService.save(task)); }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (inspectionTaskService.findById(id).isPresent()) { inspectionTaskService.deleteById(id); return ResponseEntity.ok().build(); }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/start")
    public ResponseEntity<InspectionTask> start(@PathVariable Long id) {
        InspectionTask task = inspectionTaskService.start(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<InspectionTask> complete(@PathVariable Long id, @RequestParam String result, @RequestParam Integer normalItems, @RequestParam Integer abnormalItems) {
        InspectionTask task = inspectionTaskService.complete(id, result, normalItems, abnormalItems);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }
}
