package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "video_monitor")
public class VideoMonitor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String monitorCode;
    private String name;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String location;
    private String ip;
    private Integer port;
    private String streamUrl;
    private String streamType;
    private String status;
    private String manufacturer;
    private String model;
    private String resolution;
    private String ptzSupport;
    private String installer;
    private LocalDateTime installTime;
    private LocalDateTime lastOnlineTime;
    private String recorder;
    private String storageLocation;
    private Integer retentionDays;
    private String alertConfig;
    private String remark;

    public VideoMonitor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMonitorCode() { return monitorCode; }
    public void setMonitorCode(String monitorCode) { this.monitorCode = monitorCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public Integer getPort() { return port; }
    public void setPort(Integer port) { this.port = port; }
    public String getStreamUrl() { return streamUrl; }
    public void setStreamUrl(String streamUrl) { this.streamUrl = streamUrl; }
    public String getStreamType() { return streamType; }
    public void setStreamType(String streamType) { this.streamType = streamType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }
    public String getPtzSupport() { return ptzSupport; }
    public void setPtzSupport(String ptzSupport) { this.ptzSupport = ptzSupport; }
    public String getInstaller() { return installer; }
    public void setInstaller(String installer) { this.installer = installer; }
    public LocalDateTime getInstallTime() { return installTime; }
    public void setInstallTime(LocalDateTime installTime) { this.installTime = installTime; }
    public LocalDateTime getLastOnlineTime() { return lastOnlineTime; }
    public void setLastOnlineTime(LocalDateTime lastOnlineTime) { this.lastOnlineTime = lastOnlineTime; }
    public String getRecorder() { return recorder; }
    public void setRecorder(String recorder) { this.recorder = recorder; }
    public String getStorageLocation() { return storageLocation; }
    public void setStorageLocation(String storageLocation) { this.storageLocation = storageLocation; }
    public Integer getRetentionDays() { return retentionDays; }
    public void setRetentionDays(Integer retentionDays) { this.retentionDays = retentionDays; }
    public String getAlertConfig() { return alertConfig; }
    public void setAlertConfig(String alertConfig) { this.alertConfig = alertConfig; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
