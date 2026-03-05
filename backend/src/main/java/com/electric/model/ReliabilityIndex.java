package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reliability_index")
public class ReliabilityIndex {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String targetType;
    private Long targetId;
    private String targetName;
    private LocalDateTime statisticTime;
    private String timeGranularity;
    private Double saidi;
    private Double saifi;
    private Double caidi;
    private Double asai;
    private Double maifi;
    private Integer totalOutageCount;
    private Integer plannedOutageCount;
    private Integer unplannedOutageCount;
    private Integer totalOutageDuration;
    private Integer plannedOutageDuration;
    private Integer unplannedOutageDuration;
    private Integer totalCustomerCount;
    private Integer affectedCustomerCount;
    private Integer customerInterruptCount;
    private Double avgRestoreTime;
    private Integer momentaryOutageCount;
    private String mainOutageReason;
    private String improvementSuggestion;
    private String remark;

    public ReliabilityIndex() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }
    public LocalDateTime getStatisticTime() { return statisticTime; }
    public void setStatisticTime(LocalDateTime statisticTime) { this.statisticTime = statisticTime; }
    public String getTimeGranularity() { return timeGranularity; }
    public void setTimeGranularity(String timeGranularity) { this.timeGranularity = timeGranularity; }
    public Double getSaidi() { return saidi; }
    public void setSaidi(Double saidi) { this.saidi = saidi; }
    public Double getSaifi() { return saifi; }
    public void setSaifi(Double saifi) { this.saifi = saifi; }
    public Double getCaidi() { return caidi; }
    public void setCaidi(Double caidi) { this.caidi = caidi; }
    public Double getAsai() { return asai; }
    public void setAsai(Double asai) { this.asai = asai; }
    public Double getMaifi() { return maifi; }
    public void setMaifi(Double maifi) { this.maifi = maifi; }
    public Integer getTotalOutageCount() { return totalOutageCount; }
    public void setTotalOutageCount(Integer totalOutageCount) { this.totalOutageCount = totalOutageCount; }
    public Integer getPlannedOutageCount() { return plannedOutageCount; }
    public void setPlannedOutageCount(Integer plannedOutageCount) { this.plannedOutageCount = plannedOutageCount; }
    public Integer getUnplannedOutageCount() { return unplannedOutageCount; }
    public void setUnplannedOutageCount(Integer unplannedOutageCount) { this.unplannedOutageCount = unplannedOutageCount; }
    public Integer getTotalOutageDuration() { return totalOutageDuration; }
    public void setTotalOutageDuration(Integer totalOutageDuration) { this.totalOutageDuration = totalOutageDuration; }
    public Integer getPlannedOutageDuration() { return plannedOutageDuration; }
    public void setPlannedOutageDuration(Integer plannedOutageDuration) { this.plannedOutageDuration = plannedOutageDuration; }
    public Integer getUnplannedOutageDuration() { return unplannedOutageDuration; }
    public void setUnplannedOutageDuration(Integer unplannedOutageDuration) { this.unplannedOutageDuration = unplannedOutageDuration; }
    public Integer getTotalCustomerCount() { return totalCustomerCount; }
    public void setTotalCustomerCount(Integer totalCustomerCount) { this.totalCustomerCount = totalCustomerCount; }
    public Integer getAffectedCustomerCount() { return affectedCustomerCount; }
    public void setAffectedCustomerCount(Integer affectedCustomerCount) { this.affectedCustomerCount = affectedCustomerCount; }
    public Integer getCustomerInterruptCount() { return customerInterruptCount; }
    public void setCustomerInterruptCount(Integer customerInterruptCount) { this.customerInterruptCount = customerInterruptCount; }
    public Double getAvgRestoreTime() { return avgRestoreTime; }
    public void setAvgRestoreTime(Double avgRestoreTime) { this.avgRestoreTime = avgRestoreTime; }
    public Integer getMomentaryOutageCount() { return momentaryOutageCount; }
    public void setMomentaryOutageCount(Integer momentaryOutageCount) { this.momentaryOutageCount = momentaryOutageCount; }
    public String getMainOutageReason() { return mainOutageReason; }
    public void setMainOutageReason(String mainOutageReason) { this.mainOutageReason = mainOutageReason; }
    public String getImprovementSuggestion() { return improvementSuggestion; }
    public void setImprovementSuggestion(String improvementSuggestion) { this.improvementSuggestion = improvementSuggestion; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
