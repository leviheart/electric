package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_log")
public class OperationLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String userId;
    private String operation;
    private String method;
    private String module;
    private String targetType;
    private Long targetId;
    private String targetName;
    private String ip;
    private String location;
    private String browser;
    private String os;
    private Integer status;
    private String errorMsg;
    private Long duration;
    @Column(columnDefinition = "TEXT")
    private String requestParams;
    @Column(columnDefinition = "TEXT")
    private String responseData;
    private LocalDateTime operationTime;

    public OperationLog() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public String getModule() { return module; }
    public void setModule(String module) { this.module = module; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getBrowser() { return browser; }
    public void setBrowser(String browser) { this.browser = browser; }
    public String getOs() { return os; }
    public void setOs(String os) { this.os = os; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public String getErrorMsg() { return errorMsg; }
    public void setErrorMsg(String errorMsg) { this.errorMsg = errorMsg; }
    public Long getDuration() { return duration; }
    public void setDuration(Long duration) { this.duration = duration; }
    public String getRequestParams() { return requestParams; }
    public void setRequestParams(String requestParams) { this.requestParams = requestParams; }
    public String getResponseData() { return responseData; }
    public void setResponseData(String responseData) { this.responseData = responseData; }
    public LocalDateTime getOperationTime() { return operationTime; }
    public void setOperationTime(LocalDateTime operationTime) { this.operationTime = operationTime; }
}
