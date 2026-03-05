package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "line_loss")
public class LineLoss {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lineCode;
    private String lineName;
    private String lineType;
    private Long lineId;
    private LocalDateTime recordTime;
    private String timeGranularity;
    private Double supplyPower;
    private Double sellPower;
    private Double lossPower;
    private Double lossRate;
    private Double theoreticalLoss;
    private Double actualLoss;
    private Double managementLoss;
    private Double technicalLoss;
    private Double lineResistance;
    private Double lineCurrent;
    private Double lineVoltage;
    private Double powerFactor;
    private Double loadRate;
    private String analysisResult;
    private String abnormalReason;
    private String suggestion;
    private String remark;

    public LineLoss() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLineCode() { return lineCode; }
    public void setLineCode(String lineCode) { this.lineCode = lineCode; }
    public String getLineName() { return lineName; }
    public void setLineName(String lineName) { this.lineName = lineName; }
    public String getLineType() { return lineType; }
    public void setLineType(String lineType) { this.lineType = lineType; }
    public Long getLineId() { return lineId; }
    public void setLineId(Long lineId) { this.lineId = lineId; }
    public LocalDateTime getRecordTime() { return recordTime; }
    public void setRecordTime(LocalDateTime recordTime) { this.recordTime = recordTime; }
    public String getTimeGranularity() { return timeGranularity; }
    public void setTimeGranularity(String timeGranularity) { this.timeGranularity = timeGranularity; }
    public Double getSupplyPower() { return supplyPower; }
    public void setSupplyPower(Double supplyPower) { this.supplyPower = supplyPower; }
    public Double getSellPower() { return sellPower; }
    public void setSellPower(Double sellPower) { this.sellPower = sellPower; }
    public Double getLossPower() { return lossPower; }
    public void setLossPower(Double lossPower) { this.lossPower = lossPower; }
    public Double getLossRate() { return lossRate; }
    public void setLossRate(Double lossRate) { this.lossRate = lossRate; }
    public Double getTheoreticalLoss() { return theoreticalLoss; }
    public void setTheoreticalLoss(Double theoreticalLoss) { this.theoreticalLoss = theoreticalLoss; }
    public Double getActualLoss() { return actualLoss; }
    public void setActualLoss(Double actualLoss) { this.actualLoss = actualLoss; }
    public Double getManagementLoss() { return managementLoss; }
    public void setManagementLoss(Double managementLoss) { this.managementLoss = managementLoss; }
    public Double getTechnicalLoss() { return technicalLoss; }
    public void setTechnicalLoss(Double technicalLoss) { this.technicalLoss = technicalLoss; }
    public Double getLineResistance() { return lineResistance; }
    public void setLineResistance(Double lineResistance) { this.lineResistance = lineResistance; }
    public Double getLineCurrent() { return lineCurrent; }
    public void setLineCurrent(Double lineCurrent) { this.lineCurrent = lineCurrent; }
    public Double getLineVoltage() { return lineVoltage; }
    public void setLineVoltage(Double lineVoltage) { this.lineVoltage = lineVoltage; }
    public Double getPowerFactor() { return powerFactor; }
    public void setPowerFactor(Double powerFactor) { this.powerFactor = powerFactor; }
    public Double getLoadRate() { return loadRate; }
    public void setLoadRate(Double loadRate) { this.loadRate = loadRate; }
    public String getAnalysisResult() { return analysisResult; }
    public void setAnalysisResult(String analysisResult) { this.analysisResult = analysisResult; }
    public String getAbnormalReason() { return abnormalReason; }
    public void setAbnormalReason(String abnormalReason) { this.abnormalReason = abnormalReason; }
    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
