package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_ticket")
public class WorkTicket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketCode;
    private String ticketType;
    private String title;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String workLocation;
    private String workContent;
    private LocalDateTime planStartTime;
    private LocalDateTime planEndTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private String status;
    private String priority;
    private String applicant;
    private LocalDateTime applyTime;
    private String approver;
    private LocalDateTime approveTime;
    private String workLeader;
    private String workTeam;
    private Integer workerCount;
    private String safetyMeasures;
    @Column(columnDefinition = "TEXT")
    private String dangerPoints;
    private String powerOffScope;
    private String groundingWire;
    private String safetyDistance;
    private String issuer;
    private LocalDateTime issueTime;
    private String receiver;
    private LocalDateTime receiveTime;
    private String guardian;
    private String workPermissioner;
    private LocalDateTime permitTime;
    private String workEnder;
    private LocalDateTime endTime;
    private String terminator;
    private LocalDateTime terminateTime;
    private String result;
    private String remark;

    public WorkTicket() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTicketCode() { return ticketCode; }
    public void setTicketCode(String ticketCode) { this.ticketCode = ticketCode; }
    public String getTicketType() { return ticketType; }
    public void setTicketType(String ticketType) { this.ticketType = ticketType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getWorkLocation() { return workLocation; }
    public void setWorkLocation(String workLocation) { this.workLocation = workLocation; }
    public String getWorkContent() { return workContent; }
    public void setWorkContent(String workContent) { this.workContent = workContent; }
    public LocalDateTime getPlanStartTime() { return planStartTime; }
    public void setPlanStartTime(LocalDateTime planStartTime) { this.planStartTime = planStartTime; }
    public LocalDateTime getPlanEndTime() { return planEndTime; }
    public void setPlanEndTime(LocalDateTime planEndTime) { this.planEndTime = planEndTime; }
    public LocalDateTime getActualStartTime() { return actualStartTime; }
    public void setActualStartTime(LocalDateTime actualStartTime) { this.actualStartTime = actualStartTime; }
    public LocalDateTime getActualEndTime() { return actualEndTime; }
    public void setActualEndTime(LocalDateTime actualEndTime) { this.actualEndTime = actualEndTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }
    public LocalDateTime getApplyTime() { return applyTime; }
    public void setApplyTime(LocalDateTime applyTime) { this.applyTime = applyTime; }
    public String getApprover() { return approver; }
    public void setApprover(String approver) { this.approver = approver; }
    public LocalDateTime getApproveTime() { return approveTime; }
    public void setApproveTime(LocalDateTime approveTime) { this.approveTime = approveTime; }
    public String getWorkLeader() { return workLeader; }
    public void setWorkLeader(String workLeader) { this.workLeader = workLeader; }
    public String getWorkTeam() { return workTeam; }
    public void setWorkTeam(String workTeam) { this.workTeam = workTeam; }
    public Integer getWorkerCount() { return workerCount; }
    public void setWorkerCount(Integer workerCount) { this.workerCount = workerCount; }
    public String getSafetyMeasures() { return safetyMeasures; }
    public void setSafetyMeasures(String safetyMeasures) { this.safetyMeasures = safetyMeasures; }
    public String getDangerPoints() { return dangerPoints; }
    public void setDangerPoints(String dangerPoints) { this.dangerPoints = dangerPoints; }
    public String getPowerOffScope() { return powerOffScope; }
    public void setPowerOffScope(String powerOffScope) { this.powerOffScope = powerOffScope; }
    public String getGroundingWire() { return groundingWire; }
    public void setGroundingWire(String groundingWire) { this.groundingWire = groundingWire; }
    public String getSafetyDistance() { return safetyDistance; }
    public void setSafetyDistance(String safetyDistance) { this.safetyDistance = safetyDistance; }
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public LocalDateTime getIssueTime() { return issueTime; }
    public void setIssueTime(LocalDateTime issueTime) { this.issueTime = issueTime; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public LocalDateTime getReceiveTime() { return receiveTime; }
    public void setReceiveTime(LocalDateTime receiveTime) { this.receiveTime = receiveTime; }
    public String getGuardian() { return guardian; }
    public void setGuardian(String guardian) { this.guardian = guardian; }
    public String getWorkPermissioner() { return workPermissioner; }
    public void setWorkPermissioner(String workPermissioner) { this.workPermissioner = workPermissioner; }
    public LocalDateTime getPermitTime() { return permitTime; }
    public void setPermitTime(LocalDateTime permitTime) { this.permitTime = permitTime; }
    public String getWorkEnder() { return workEnder; }
    public void setWorkEnder(String workEnder) { this.workEnder = workEnder; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getTerminator() { return terminator; }
    public void setTerminator(String terminator) { this.terminator = terminator; }
    public LocalDateTime getTerminateTime() { return terminateTime; }
    public void setTerminateTime(LocalDateTime terminateTime) { this.terminateTime = terminateTime; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
