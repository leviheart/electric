package com.electric.controller;

import com.electric.model.PowerOutage;
import com.electric.service.PowerOutageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/power-outages")
@CrossOrigin(origins = "*")
public class PowerOutageController {
    
    @Autowired
    private PowerOutageService powerOutageService;
    
    @GetMapping
    public List<PowerOutage> getAll() { return powerOutageService.findAll(); }
    
    @GetMapping("/{id}")
    public PowerOutage getById(@PathVariable Long id) { return powerOutageService.findById(id).orElse(null); }
    
    @GetMapping("/status/{status}")
    public List<PowerOutage> getByStatus(@PathVariable String status) { return powerOutageService.findByStatus(status); }
    
    @GetMapping("/active")
    public List<PowerOutage> getActive() { return powerOutageService.findActiveOutages(); }
    
    @PostMapping
    public PowerOutage create(@RequestBody PowerOutage outage) { return powerOutageService.save(outage); }
    
    @PutMapping("/{id}")
    public PowerOutage update(@PathVariable Long id, @RequestBody PowerOutage outage) {
        outage.setId(id);
        return powerOutageService.save(outage);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { powerOutageService.deleteById(id); }
}
