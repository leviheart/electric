package com.electric.controller;

import com.electric.model.VideoMonitor;
import com.electric.service.VideoMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/video-monitors")
@CrossOrigin(origins = "*")
public class VideoMonitorController {
    
    @Autowired
    private VideoMonitorService videoMonitorService;
    
    @GetMapping
    public List<VideoMonitor> getAll() { return videoMonitorService.findAll(); }
    
    @GetMapping("/{id}")
    public VideoMonitor getById(@PathVariable Long id) { return videoMonitorService.findById(id).orElse(null); }
    
    @GetMapping("/status/{status}")
    public List<VideoMonitor> getByStatus(@PathVariable String status) { return videoMonitorService.findByStatus(status); }
    
    @GetMapping("/device/{deviceType}/{deviceId}")
    public List<VideoMonitor> getByDevice(@PathVariable String deviceType, @PathVariable Long deviceId) {
        return videoMonitorService.findByDevice(deviceType, deviceId);
    }
    
    @PostMapping
    public VideoMonitor create(@RequestBody VideoMonitor monitor) { return videoMonitorService.save(monitor); }
    
    @PutMapping("/{id}")
    public VideoMonitor update(@PathVariable Long id, @RequestBody VideoMonitor monitor) {
        monitor.setId(id);
        return videoMonitorService.save(monitor);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { videoMonitorService.deleteById(id); }
    
    @PostMapping("/{id}/status")
    public VideoMonitor updateStatus(@PathVariable Long id, @RequestParam String status) {
        return videoMonitorService.updateStatus(id, status);
    }
}
