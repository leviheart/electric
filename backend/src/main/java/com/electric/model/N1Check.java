package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "n1_check")
public class N1Check {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkCode;
    private String checkName;
    private LocalDateTime checkTime;
    private String status;
    private String checkType;
    private String targetType;
    private Long targetId;
    private String targetName;
    @Column(columnDefinition = "TEXT")
    private String checkItems;
    @Column(columnDefinition = "TEXT")
    private String resultData;
    private Integer totalItems;
    private Integer passedItems;
    private Integer failedItems;
    private String failedDetails;
    private String overloadLines;
    private String overloadTransformers;
    private String voltageViolationNodes;
    private Double maxOverloadRate;
    private String maxOverloadDevice;
    private String riskLevel;
    private String riskAssessment;
    private String suggestion;
    private String remark;

    public N1Check() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCheckCode() { return checkCode; }
    public void setCheckCode(String checkCode) { this.checkCode = checkCode; }
    public String getCheckName() { return checkName; }
    public void setCheckName(String checkName) { this.checkName = checkName; }
    public LocalDateTime getCheckTime() { return checkTime; }
    public void setCheckTime(LocalDateTime checkTime) { this.checkTime = checkTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCheckType() { return checkType; }
    public void setCheckType(String checkType) { this.checkType = checkType; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }
    public String getCheckItems() { return checkItems; }
    public void setCheckItems(String checkItems) { this.checkItems = checkItems; }
    public String getResultData() { return resultData; }
    public void setResultData(String resultData) { this.resultData = resultData; }
    public Integer getTotalItems() { return totalItems; }
    public void setTotalItems(Integer totalItems) { this.totalItems = totalItems; }
    public Integer getPassedItems() { return passedItems; }
    public void setPassedItems(Integer passedItems) { this.passedItems = passedItems; }
    public Integer getFailedItems() { return failedItems; }
    public void setFailedItems(Integer failedItems) { this.failedItems = failedItems; }
    public String getFailedDetails() { return failedDetails; }
    public void setFailedDetails(String failedDetails) { this.failedDetails = failedDetails; }
    public String getOverloadLines() { return overloadLines; }
    public void setOverloadLines(String overloadLines) { this.overloadLines = overloadLines; }
    public String getOverloadTransformers() { return overloadTransformers; }
    public void setOverloadTransformers(String overloadTransformers) { this.overloadTransformers = overloadTransformers; }
    public String getVoltageViolationNodes() { return voltageViolationNodes; }
    public void setVoltageViolationNodes(String voltageViolationNodes) { this.voltageViolationNodes = voltageViolationNodes; }
    public Double getMaxOverloadRate() { return maxOverloadRate; }
    public void setMaxOverloadRate(Double maxOverloadRate) { this.maxOverloadRate = maxOverloadRate; }
    public String getMaxOverloadDevice() { return maxOverloadDevice; }
    public void setMaxOverloadDevice(String maxOverloadDevice) { this.maxOverloadDevice = maxOverloadDevice; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public String getRiskAssessment() { return riskAssessment; }
    public void setRiskAssessment(String riskAssessment) { this.riskAssessment = riskAssessment; }
    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
