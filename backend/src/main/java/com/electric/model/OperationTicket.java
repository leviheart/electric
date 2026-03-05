package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_ticket")
public class OperationTicket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketCode;
    private String ticketType;
    private String title;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String operationTask;
    @Column(columnDefinition = "TEXT")
    private String operationItems;
    private Integer totalSteps;
    private Integer completedSteps;
    private LocalDateTime planTime;
    private LocalDateTime actualTime;
    private String status;
    private String priority;
    private String applicant;
    private LocalDateTime applyTime;
    private String approver;
    private LocalDateTime approveTime;
    private String issuer;
    private LocalDateTime issueTime;
    private String operator;
    private String guardian;
    private String commander;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String result;
    private String remark;

    public OperationTicket() {}

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
    public String getOperationTask() { return operationTask; }
    public void setOperationTask(String operationTask) { this.operationTask = operationTask; }
    public String getOperationItems() { return operationItems; }
    public void setOperationItems(String operationItems) { this.operationItems = operationItems; }
    public Integer getTotalSteps() { return totalSteps; }
    public void setTotalSteps(Integer totalSteps) { this.totalSteps = totalSteps; }
    public Integer getCompletedSteps() { return completedSteps; }
    public void setCompletedSteps(Integer completedSteps) { this.completedSteps = completedSteps; }
    public LocalDateTime getPlanTime() { return planTime; }
    public void setPlanTime(LocalDateTime planTime) { this.planTime = planTime; }
    public LocalDateTime getActualTime() { return actualTime; }
    public void setActualTime(LocalDateTime actualTime) { this.actualTime = actualTime; }
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
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public LocalDateTime getIssueTime() { return issueTime; }
    public void setIssueTime(LocalDateTime issueTime) { this.issueTime = issueTime; }
    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
    public String getGuardian() { return guardian; }
    public void setGuardian(String guardian) { this.guardian = guardian; }
    public String getCommander() { return commander; }
    public void setCommander(String commander) { this.commander = commander; }
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
