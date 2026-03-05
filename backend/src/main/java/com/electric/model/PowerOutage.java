package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "power_outage")
public class PowerOutage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String outageCode;
    private String title;
    private String outageType;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String affectedArea;
    private Integer affectedCustomers;
    private String reason;
    private String status;
    private LocalDateTime planStartTime;
    private LocalDateTime planEndTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private String applicant;
    private LocalDateTime applyTime;
    private String approver;
    private LocalDateTime approveTime;
    private String executor;
    private String executorTeam;
    private String notifyStatus;
    private LocalDateTime notifyTime;
    private String recoveryPlan;
    private String actualRecovery;
    private Integer duration;
    private String remark;

    public PowerOutage() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOutageCode() { return outageCode; }
    public void setOutageCode(String outageCode) { this.outageCode = outageCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getOutageType() { return outageType; }
    public void setOutageType(String outageType) { this.outageType = outageType; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getAffectedArea() { return affectedArea; }
    public void setAffectedArea(String affectedArea) { this.affectedArea = affectedArea; }
    public Integer getAffectedCustomers() { return affectedCustomers; }
    public void setAffectedCustomers(Integer affectedCustomers) { this.affectedCustomers = affectedCustomers; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
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
    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }
    public LocalDateTime getApplyTime() { return applyTime; }
    public void setApplyTime(LocalDateTime applyTime) { this.applyTime = applyTime; }
    public String getApprover() { return approver; }
    public void setApprover(String approver) { this.approver = approver; }
    public LocalDateTime getApproveTime() { return approveTime; }
    public void setApproveTime(LocalDateTime approveTime) { this.approveTime = approveTime; }
    public String getExecutor() { return executor; }
    public void setExecutor(String executor) { this.executor = executor; }
    public String getExecutorTeam() { return executorTeam; }
    public void setExecutorTeam(String executorTeam) { this.executorTeam = executorTeam; }
    public String getNotifyStatus() { return notifyStatus; }
    public void setNotifyStatus(String notifyStatus) { this.notifyStatus = notifyStatus; }
    public LocalDateTime getNotifyTime() { return notifyTime; }
    public void setNotifyTime(LocalDateTime notifyTime) { this.notifyTime = notifyTime; }
    public String getRecoveryPlan() { return recoveryPlan; }
    public void setRecoveryPlan(String recoveryPlan) { this.recoveryPlan = recoveryPlan; }
    public String getActualRecovery() { return actualRecovery; }
    public void setActualRecovery(String actualRecovery) { this.actualRecovery = actualRecovery; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
