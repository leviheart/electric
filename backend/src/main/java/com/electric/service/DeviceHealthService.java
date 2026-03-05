package com.electric.service;

import com.electric.model.DeviceHealth;
import com.electric.repository.DeviceHealthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DeviceHealthService {
    
    @Autowired
    private DeviceHealthRepository deviceHealthRepository;
    
    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    public List<DeviceHealth> findAll() {
        return deviceHealthRepository.findAll();
    }
    
    public Optional<DeviceHealth> findById(Long id) {
        return deviceHealthRepository.findById(id);
    }
    
    public DeviceHealth save(DeviceHealth health) {
        return deviceHealthRepository.save(health);
    }
    
    public void deleteById(Long id) {
        deviceHealthRepository.deleteById(id);
    }
    
    public Optional<DeviceHealth> findLatestByDevice(String deviceType, Long deviceId) {
        return deviceHealthRepository.findLatestByDevice(deviceType, deviceId);
    }
    
    public Double getAverageHealthScore() {
        return deviceHealthRepository.getAverageHealthScore();
    }
    
    public List<DeviceHealth> findLowHealthDevices(Double threshold) {
        return deviceHealthRepository.findLowHealthDevices(threshold);
    }
    
    public List<Object[]> countByHealthLevel() {
        return deviceHealthRepository.countByHealthLevel();
    }
    
    public DeviceHealth evaluate(String deviceType, Long deviceId, String deviceName,
                                 Integer runYears, Integer faultCount, Integer defectCount,
                                 Integer maintenanceCount, Double avgLoadRate, Double maxLoadRate) {
        DeviceHealth health = new DeviceHealth();
        health.setDeviceType(deviceType);
        health.setDeviceId(deviceId);
        health.setDeviceName(deviceName);
        health.setEvaluateTime(LocalDateTime.now());
        health.setEvaluateType("综合评估");
        health.setRunYears(runYears);
        health.setFaultCount(faultCount);
        health.setDefectCount(defectCount);
        health.setMaintenanceCount(maintenanceCount);
        health.setAvgLoadRate(avgLoadRate);
        health.setMaxLoadRate(maxLoadRate);
        
        double reliabilityScore = calculateReliabilityScore(runYears, faultCount);
        double performanceScore = calculatePerformanceScore(avgLoadRate, maxLoadRate);
        double maintenanceScore = calculateMaintenanceScore(maintenanceCount, defectCount);
        double environmentScore = round2(80 + new Random().nextDouble() * 15);
        double loadScore = calculateLoadScore(avgLoadRate);
        
        health.setReliabilityScore(round2(reliabilityScore));
        health.setPerformanceScore(round2(performanceScore));
        health.setMaintenanceScore(round2(maintenanceScore));
        health.setEnvironmentScore(round2(environmentScore));
        health.setLoadScore(round2(loadScore));
        
        double totalScore = (reliabilityScore * 0.3 + performanceScore * 0.25 + 
                           maintenanceScore * 0.2 + environmentScore * 0.1 + loadScore * 0.15);
        health.setHealthScore(round2(totalScore));
        
        if (totalScore >= 90) {
            health.setHealthLevel("优秀");
            health.setRiskLevel("低");
            health.setSuggestion("设备运行状态良好，继续保持");
        } else if (totalScore >= 75) {
            health.setHealthLevel("良好");
            health.setRiskLevel("低");
            health.setSuggestion("设备运行正常，建议定期维护");
        } else if (totalScore >= 60) {
            health.setHealthLevel("一般");
            health.setRiskLevel("中");
            health.setSuggestion("设备存在潜在问题，建议加强监测");
        } else {
            health.setHealthLevel("较差");
            health.setRiskLevel("高");
            health.setSuggestion("设备状态不佳，建议尽快检修或更换");
        }
        
        return deviceHealthRepository.save(health);
    }
    
    private double calculateReliabilityScore(int runYears, int faultCount) {
        double score = 100;
        score -= runYears * 1.5;
        score -= faultCount * 10;
        return Math.max(0, Math.min(100, score));
    }
    
    private double calculatePerformanceScore(double avgLoadRate, double maxLoadRate) {
        double score = 100;
        if (avgLoadRate > 80) score -= (avgLoadRate - 80) * 2;
        if (maxLoadRate > 100) score -= (maxLoadRate - 100) * 5;
        return Math.max(0, Math.min(100, score));
    }
    
    private double calculateMaintenanceScore(int maintenanceCount, int defectCount) {
        double score = 100;
        score -= defectCount * 8;
        if (maintenanceCount > 0) score += 5;
        return Math.max(0, Math.min(100, score));
    }
    
    private double calculateLoadScore(double avgLoadRate) {
        if (avgLoadRate < 50) return 90;
        if (avgLoadRate < 70) return 85;
        if (avgLoadRate < 85) return 75;
        return 60;
    }
}
