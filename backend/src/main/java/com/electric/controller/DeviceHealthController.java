package com.electric.controller;

import com.electric.model.DeviceHealth;
import com.electric.service.DeviceHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/device-healths")
@CrossOrigin(origins = "*")
public class DeviceHealthController {
    
    @Autowired
    private DeviceHealthService deviceHealthService;
    
    @GetMapping
    public List<DeviceHealth> getAll() { return deviceHealthService.findAll(); }
    
    @GetMapping("/{id}")
    public DeviceHealth getById(@PathVariable Long id) { return deviceHealthService.findById(id).orElse(null); }
    
    @GetMapping("/device/{deviceType}/{deviceId}")
    public DeviceHealth getByDevice(@PathVariable String deviceType, @PathVariable Long deviceId) {
        return deviceHealthService.findLatestByDevice(deviceType, deviceId).orElse(null);
    }
    
    @GetMapping("/low-health")
    public List<DeviceHealth> getLowHealth(@RequestParam(defaultValue = "60.0") double threshold) {
        return deviceHealthService.findLowHealthDevices(threshold);
    }
    
    @PostMapping("/evaluate")
    public DeviceHealth evaluate(@RequestBody Map<String, Object> params) {
        String deviceType = (String) params.get("deviceType");
        Long deviceId = Long.valueOf(params.get("deviceId").toString());
        String deviceName = (String) params.get("deviceName");
        int runningYears = params.get("runningYears") != null ? Integer.parseInt(params.get("runningYears").toString()) : 0;
        int defectCount = params.get("defectCount") != null ? Integer.parseInt(params.get("defectCount").toString()) : 0;
        int maintenanceCount = params.get("maintenanceCount") != null ? Integer.parseInt(params.get("maintenanceCount").toString()) : 0;
        int faultCount = params.get("faultCount") != null ? Integer.parseInt(params.get("faultCount").toString()) : 0;
        double loadRate = params.get("loadRate") != null ? Double.parseDouble(params.get("loadRate").toString()) : 0;
        double avgLoadRate = params.get("avgLoadRate") != null ? Double.parseDouble(params.get("avgLoadRate").toString()) : 0;
        return deviceHealthService.evaluate(deviceType, deviceId, deviceName, runningYears, defectCount, maintenanceCount, faultCount, loadRate, avgLoadRate);
    }
}
