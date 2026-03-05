package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_health")
public class DeviceHealth {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private LocalDateTime evaluateTime;
    private String evaluateType;
    private Double healthScore;
    private String healthLevel;
    private Double reliabilityScore;
    private Double performanceScore;
    private Double maintenanceScore;
    private Double environmentScore;
    private Double loadScore;
    private Integer runYears;
    private Integer faultCount;
    private Integer defectCount;
    private Integer maintenanceCount;
    private Double avgLoadRate;
    private Double maxLoadRate;
    private String lastFaultDate;
    private String lastMaintenanceDate;
    private String riskLevel;
    private String riskFactors;
    private String suggestion;
    @Column(columnDefinition = "TEXT")
    private String detailData;
    private String remark;

    public DeviceHealth() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public LocalDateTime getEvaluateTime() { return evaluateTime; }
    public void setEvaluateTime(LocalDateTime evaluateTime) { this.evaluateTime = evaluateTime; }
    public String getEvaluateType() { return evaluateType; }
    public void setEvaluateType(String evaluateType) { this.evaluateType = evaluateType; }
    public Double getHealthScore() { return healthScore; }
    public void setHealthScore(Double healthScore) { this.healthScore = healthScore; }
    public String getHealthLevel() { return healthLevel; }
    public void setHealthLevel(String healthLevel) { this.healthLevel = healthLevel; }
    public Double getReliabilityScore() { return reliabilityScore; }
    public void setReliabilityScore(Double reliabilityScore) { this.reliabilityScore = reliabilityScore; }
    public Double getPerformanceScore() { return performanceScore; }
    public void setPerformanceScore(Double performanceScore) { this.performanceScore = performanceScore; }
    public Double getMaintenanceScore() { return maintenanceScore; }
    public void setMaintenanceScore(Double maintenanceScore) { this.maintenanceScore = maintenanceScore; }
    public Double getEnvironmentScore() { return environmentScore; }
    public void setEnvironmentScore(Double environmentScore) { this.environmentScore = environmentScore; }
    public Double getLoadScore() { return loadScore; }
    public void setLoadScore(Double loadScore) { this.loadScore = loadScore; }
    public Integer getRunYears() { return runYears; }
    public void setRunYears(Integer runYears) { this.runYears = runYears; }
    public Integer getFaultCount() { return faultCount; }
    public void setFaultCount(Integer faultCount) { this.faultCount = faultCount; }
    public Integer getDefectCount() { return defectCount; }
    public void setDefectCount(Integer defectCount) { this.defectCount = defectCount; }
    public Integer getMaintenanceCount() { return maintenanceCount; }
    public void setMaintenanceCount(Integer maintenanceCount) { this.maintenanceCount = maintenanceCount; }
    public Double getAvgLoadRate() { return avgLoadRate; }
    public void setAvgLoadRate(Double avgLoadRate) { this.avgLoadRate = avgLoadRate; }
    public Double getMaxLoadRate() { return maxLoadRate; }
    public void setMaxLoadRate(Double maxLoadRate) { this.maxLoadRate = maxLoadRate; }
    public String getLastFaultDate() { return lastFaultDate; }
    public void setLastFaultDate(String lastFaultDate) { this.lastFaultDate = lastFaultDate; }
    public String getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(String lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public String getRiskFactors() { return riskFactors; }
    public void setRiskFactors(String riskFactors) { this.riskFactors = riskFactors; }
    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }
    public String getDetailData() { return detailData; }
    public void setDetailData(String detailData) { this.detailData = detailData; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
