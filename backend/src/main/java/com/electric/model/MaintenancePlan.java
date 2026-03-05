package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_plan")
public class MaintenancePlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planCode;
    private String title;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String maintenanceType;
    private String priority;
    private String status;
    private LocalDateTime planStartTime;
    private LocalDateTime planEndTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private String content;
    private String reason;
    private Double estimatedCost;
    private Double actualCost;
    private String applicant;
    private LocalDateTime applyTime;
    private String approver;
    private LocalDateTime approveTime;
    private String approveComment;
    private String executor;
    private String executorTeam;
    private String safetyMeasures;
    private String riskAssessment;
    private String result;
    private String remark;

    @Column(columnDefinition = "TEXT")
    private String attachments;

    public MaintenancePlan() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlanCode() { return planCode; }
    public void setPlanCode(String planCode) { this.planCode = planCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getMaintenanceType() { return maintenanceType; }
    public void setMaintenanceType(String maintenanceType) { this.maintenanceType = maintenanceType; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getPlanStartTime() { return planStartTime; }
    public void setPlanStartTime(LocalDateTime planStartTime) { this.planStartTime = planStartTime; }
    public LocalDateTime getPlanEndTime() { return planEndTime; }
    public void setPlanEndTime(LocalDateTime planEndTime) { this.planEndTime = planEndTime; }
    public LocalDateTime getActualStartTime() { return actualStartTime; }
    public void setActualStartTime(LocalDateTime actualStartTime) { this.actualStartTime = actualStartTime; }
    public LocalDateTime getActualEndTime() { return actualEndTime; }
    public void setActualEndTime(LocalDateTime actualEndTime) { this.actualEndTime = actualEndTime; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }
    public Double getActualCost() { return actualCost; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }
    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }
    public LocalDateTime getApplyTime() { return applyTime; }
    public void setApplyTime(LocalDateTime applyTime) { this.applyTime = applyTime; }
    public String getApprover() { return approver; }
    public void setApprover(String approver) { this.approver = approver; }
    public LocalDateTime getApproveTime() { return approveTime; }
    public void setApproveTime(LocalDateTime approveTime) { this.approveTime = approveTime; }
    public String getApproveComment() { return approveComment; }
    public void setApproveComment(String approveComment) { this.approveComment = approveComment; }
    public String getExecutor() { return executor; }
    public void setExecutor(String executor) { this.executor = executor; }
    public String getExecutorTeam() { return executorTeam; }
    public void setExecutorTeam(String executorTeam) { this.executorTeam = executorTeam; }
    public String getSafetyMeasures() { return safetyMeasures; }
    public void setSafetyMeasures(String safetyMeasures) { this.safetyMeasures = safetyMeasures; }
    public String getRiskAssessment() { return riskAssessment; }
    public void setRiskAssessment(String riskAssessment) { this.riskAssessment = riskAssessment; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public String getAttachments() { return attachments; }
    public void setAttachments(String attachments) { this.attachments = attachments; }
}
