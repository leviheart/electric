package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "defect")
public class Defect {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String defectCode;
    private String title;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String defectType;
    private String defectLevel;
    private String status;
    private String discoverySource;
    private LocalDateTime discoveryTime;
    private String discoverer;
    private String description;
    @Column(columnDefinition = "TEXT")
    private String photos;
    private String location;
    private String cause;
    private String solution;
    private String handler;
    private String handlerTeam;
    private LocalDateTime planTime;
    private LocalDateTime handleTime;
    private LocalDateTime completeTime;
    private String handlerComment;
    private String verifier;
    private LocalDateTime verifyTime;
    private String verifyResult;
    private String verifyComment;
    private Double estimatedCost;
    private Double actualCost;
    private String remark;

    public Defect() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDefectCode() { return defectCode; }
    public void setDefectCode(String defectCode) { this.defectCode = defectCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getDefectType() { return defectType; }
    public void setDefectType(String defectType) { this.defectType = defectType; }
    public String getDefectLevel() { return defectLevel; }
    public void setDefectLevel(String defectLevel) { this.defectLevel = defectLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDiscoverySource() { return discoverySource; }
    public void setDiscoverySource(String discoverySource) { this.discoverySource = discoverySource; }
    public LocalDateTime getDiscoveryTime() { return discoveryTime; }
    public void setDiscoveryTime(LocalDateTime discoveryTime) { this.discoveryTime = discoveryTime; }
    public String getDiscoverer() { return discoverer; }
    public void setDiscoverer(String discoverer) { this.discoverer = discoverer; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPhotos() { return photos; }
    public void setPhotos(String photos) { this.photos = photos; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getCause() { return cause; }
    public void setCause(String cause) { this.cause = cause; }
    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }
    public String getHandler() { return handler; }
    public void setHandler(String handler) { this.handler = handler; }
    public String getHandlerTeam() { return handlerTeam; }
    public void setHandlerTeam(String handlerTeam) { this.handlerTeam = handlerTeam; }
    public LocalDateTime getPlanTime() { return planTime; }
    public void setPlanTime(LocalDateTime planTime) { this.planTime = planTime; }
    public LocalDateTime getHandleTime() { return handleTime; }
    public void setHandleTime(LocalDateTime handleTime) { this.handleTime = handleTime; }
    public LocalDateTime getCompleteTime() { return completeTime; }
    public void setCompleteTime(LocalDateTime completeTime) { this.completeTime = completeTime; }
    public String getHandlerComment() { return handlerComment; }
    public void setHandlerComment(String handlerComment) { this.handlerComment = handlerComment; }
    public String getVerifier() { return verifier; }
    public void setVerifier(String verifier) { this.verifier = verifier; }
    public LocalDateTime getVerifyTime() { return verifyTime; }
    public void setVerifyTime(LocalDateTime verifyTime) { this.verifyTime = verifyTime; }
    public String getVerifyResult() { return verifyResult; }
    public void setVerifyResult(String verifyResult) { this.verifyResult = verifyResult; }
    public String getVerifyComment() { return verifyComment; }
    public void setVerifyComment(String verifyComment) { this.verifyComment = verifyComment; }
    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }
    public Double getActualCost() { return actualCost; }
    public void setActualCost(Double actualCost) { this.actualCost = actualCost; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
