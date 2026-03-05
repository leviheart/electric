package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document")
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docCode;
    private String title;
    private String docType;
    private String category;
    private String deviceType;
    private Long deviceId;
    private String deviceName;
    private String version;
    private String author;
    private String department;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String status;
    private String filePath;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String description;
    private String keywords;
    private String uploader;
    private LocalDateTime uploadTime;
    private Integer downloadCount;
    private String permission;
    private String remark;

    public Document() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDocCode() { return docCode; }
    public void setDocCode(String docCode) { this.docCode = docCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDocType() { return docType; }
    public void setDocType(String docType) { this.docType = docType; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDeviceType() { return deviceType; }
    public void setDeviceType(String deviceType) { this.deviceType = deviceType; }
    public Long getDeviceId() { return deviceId; }
    public void setDeviceId(Long deviceId) { this.deviceId = deviceId; }
    public String getDeviceName() { return deviceName; }
    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }
    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getKeywords() { return keywords; }
    public void setKeywords(String keywords) { this.keywords = keywords; }
    public String getUploader() { return uploader; }
    public void setUploader(String uploader) { this.uploader = uploader; }
    public LocalDateTime getUploadTime() { return uploadTime; }
    public void setUploadTime(LocalDateTime uploadTime) { this.uploadTime = uploadTime; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
    public String getPermission() { return permission; }
    public void setPermission(String permission) { this.permission = permission; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
