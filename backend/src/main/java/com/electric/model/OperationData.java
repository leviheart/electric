package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "operation_data")
public class OperationData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String entityType;
    
    private Long entityId;
    
    private String entityName;
    
    private LocalDateTime recordTime;
    
    private Double voltage;
    
    private Double current;
    
    private Double power;
    
    private Double powerFactor;
    
    private Double frequency;
    
    private Double temperature;
    
    private String status;
    
    public OperationData() {}
    
    public OperationData(String entityType, Long entityId, String entityName, LocalDateTime recordTime,
                         Double voltage, Double current, Double power, Double powerFactor,
                         Double frequency, Double temperature, String status) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.entityName = entityName;
        this.recordTime = recordTime;
        this.voltage = voltage;
        this.current = current;
        this.power = power;
        this.powerFactor = powerFactor;
        this.frequency = frequency;
        this.temperature = temperature;
        this.status = status;
    }
    
    @PrePersist
    protected void onCreate() {
        if (recordTime == null) {
            recordTime = LocalDateTime.now();
        }
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    
    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }
    
    public LocalDateTime getRecordTime() { return recordTime; }
    public void setRecordTime(LocalDateTime recordTime) { this.recordTime = recordTime; }
    
    public Double getVoltage() { return voltage; }
    public void setVoltage(Double voltage) { this.voltage = voltage; }
    
    public Double getCurrent() { return current; }
    public void setCurrent(Double current) { this.current = current; }
    
    public Double getPower() { return power; }
    public void setPower(Double power) { this.power = power; }
    
    public Double getPowerFactor() { return powerFactor; }
    public void setPowerFactor(Double powerFactor) { this.powerFactor = powerFactor; }
    
    public Double getFrequency() { return frequency; }
    public void setFrequency(Double frequency) { this.frequency = frequency; }
    
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
