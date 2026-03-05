package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dispatch_order")
public class DispatchOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderCode;
    private String title;
    private String orderType;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String content;
    private String priority;
    private String status;
    private LocalDateTime planTime;
    private LocalDateTime executeTime;
    private LocalDateTime completeTime;
    private String issuer;
    private String issuerDept;
    private LocalDateTime issueTime;
    private String receiver;
    private String receiverDept;
    private LocalDateTime receiveTime;
    private String executor;
    private String executorTeam;
    private String executeResult;
    private String feedback;
    private LocalDateTime feedbackTime;
    private String verifier;
    private LocalDateTime verifyTime;
    private String verifyResult;
    private String remark;

    public DispatchOrder() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getOrderType() { return orderType; }
    public void setOrderType(String orderType) { this.orderType = orderType; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getPlanTime() { return planTime; }
    public void setPlanTime(LocalDateTime planTime) { this.planTime = planTime; }
    public LocalDateTime getExecuteTime() { return executeTime; }
    public void setExecuteTime(LocalDateTime executeTime) { this.executeTime = executeTime; }
    public LocalDateTime getCompleteTime() { return completeTime; }
    public void setCompleteTime(LocalDateTime completeTime) { this.completeTime = completeTime; }
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }
    public String getIssuerDept() { return issuerDept; }
    public void setIssuerDept(String issuerDept) { this.issuerDept = issuerDept; }
    public LocalDateTime getIssueTime() { return issueTime; }
    public void setIssueTime(LocalDateTime issueTime) { this.issueTime = issueTime; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public String getReceiverDept() { return receiverDept; }
    public void setReceiverDept(String receiverDept) { this.receiverDept = receiverDept; }
    public LocalDateTime getReceiveTime() { return receiveTime; }
    public void setReceiveTime(LocalDateTime receiveTime) { this.receiveTime = receiveTime; }
    public String getExecutor() { return executor; }
    public void setExecutor(String executor) { this.executor = executor; }
    public String getExecutorTeam() { return executorTeam; }
    public void setExecutorTeam(String executorTeam) { this.executorTeam = executorTeam; }
    public String getExecuteResult() { return executeResult; }
    public void setExecuteResult(String executeResult) { this.executeResult = executeResult; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public LocalDateTime getFeedbackTime() { return feedbackTime; }
    public void setFeedbackTime(LocalDateTime feedbackTime) { this.feedbackTime = feedbackTime; }
    public String getVerifier() { return verifier; }
    public void setVerifier(String verifier) { this.verifier = verifier; }
    public LocalDateTime getVerifyTime() { return verifyTime; }
    public void setVerifyTime(LocalDateTime verifyTime) { this.verifyTime = verifyTime; }
    public String getVerifyResult() { return verifyResult; }
    public void setVerifyResult(String verifyResult) { this.verifyResult = verifyResult; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
