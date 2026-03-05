package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inspection_task")
public class InspectionTask {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskCode;
    private String title;
    private String inspectionType;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String route;
    private String status;
    private String priority;
    private LocalDateTime planStartTime;
    private LocalDateTime planEndTime;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private String inspector;
    private String inspectorTeam;
    private String checklist;
    @Column(columnDefinition = "TEXT")
    private String inspectionItems;
    private String result;
    private Integer totalItems;
    private Integer normalItems;
    private Integer abnormalItems;
    private String abnormalDesc;
    @Column(columnDefinition = "TEXT")
    private String photos;
    private String weather;
    private String temperature;
    private String humidity;
    private String remark;

    public InspectionTask() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTaskCode() { return taskCode; }
    public void setTaskCode(String taskCode) { this.taskCode = taskCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getInspectionType() { return inspectionType; }
    public void setInspectionType(String inspectionType) { this.inspectionType = inspectionType; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDateTime getPlanStartTime() { return planStartTime; }
    public void setPlanStartTime(LocalDateTime planStartTime) { this.planStartTime = planStartTime; }
    public LocalDateTime getPlanEndTime() { return planEndTime; }
    public void setPlanEndTime(LocalDateTime planEndTime) { this.planEndTime = planEndTime; }
    public LocalDateTime getActualStartTime() { return actualStartTime; }
    public void setActualStartTime(LocalDateTime actualStartTime) { this.actualStartTime = actualStartTime; }
    public LocalDateTime getActualEndTime() { return actualEndTime; }
    public void setActualEndTime(LocalDateTime actualEndTime) { this.actualEndTime = actualEndTime; }
    public String getInspector() { return inspector; }
    public void setInspector(String inspector) { this.inspector = inspector; }
    public String getInspectorTeam() { return inspectorTeam; }
    public void setInspectorTeam(String inspectorTeam) { this.inspectorTeam = inspectorTeam; }
    public String getChecklist() { return checklist; }
    public void setChecklist(String checklist) { this.checklist = checklist; }
    public String getInspectionItems() { return inspectionItems; }
    public void setInspectionItems(String inspectionItems) { this.inspectionItems = inspectionItems; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public Integer getTotalItems() { return totalItems; }
    public void setTotalItems(Integer totalItems) { this.totalItems = totalItems; }
    public Integer getNormalItems() { return normalItems; }
    public void setNormalItems(Integer normalItems) { this.normalItems = normalItems; }
    public Integer getAbnormalItems() { return abnormalItems; }
    public void setAbnormalItems(Integer abnormalItems) { this.abnormalItems = abnormalItems; }
    public String getAbnormalDesc() { return abnormalDesc; }
    public void setAbnormalDesc(String abnormalDesc) { this.abnormalDesc = abnormalDesc; }
    public String getPhotos() { return photos; }
    public void setPhotos(String photos) { this.photos = photos; }
    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    public String getTemperature() { return temperature; }
    public void setTemperature(String temperature) { this.temperature = temperature; }
    public String getHumidity() { return humidity; }
    public void setHumidity(String humidity) { this.humidity = humidity; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
