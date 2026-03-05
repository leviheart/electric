package com.electric.controller;

import com.electric.model.Defect;
import com.electric.service.DefectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/defects")
@CrossOrigin(origins = "*")
public class DefectController {
    
    @Autowired
    private DefectService defectService;
    
    @GetMapping
    public List<Defect> getAll() { return defectService.findAll(); }
    
    @GetMapping("/{id}")
    public ResponseEntity<Defect> getById(@PathVariable Long id) {
        return defectService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public List<Defect> getByStatus(@PathVariable String status) { return defectService.findByStatus(status); }
    
    @GetMapping("/active")
    public List<Defect> getActive() { return defectService.findActiveDefects(); }
    
    @PostMapping
    public Defect create(@RequestBody Defect defect) { return defectService.save(defect); }
    
    @PutMapping("/{id}")
    public ResponseEntity<Defect> update(@PathVariable Long id, @RequestBody Defect defect) {
        return defectService.findById(id).map(existing -> { defect.setId(id); return ResponseEntity.ok(defectService.save(defect)); }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (defectService.findById(id).isPresent()) { defectService.deleteById(id); return ResponseEntity.ok().build(); }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/handle")
    public ResponseEntity<Defect> handle(@PathVariable Long id, @RequestParam String handler, @RequestParam String solution) {
        Defect defect = defectService.handle(id, handler, solution);
        return defect != null ? ResponseEntity.ok(defect) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<Defect> complete(@PathVariable Long id, @RequestParam(required = false) String handlerComment) {
        Defect defect = defectService.complete(id, handlerComment);
        return defect != null ? ResponseEntity.ok(defect) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/verify")
    public ResponseEntity<Defect> verify(@PathVariable Long id, @RequestParam String verifier, @RequestParam String verifyResult, @RequestParam(required = false) String verifyComment) {
        Defect defect = defectService.verify(id, verifier, verifyResult, verifyComment);
        return defect != null ? ResponseEntity.ok(defect) : ResponseEntity.notFound().build();
    }
}
