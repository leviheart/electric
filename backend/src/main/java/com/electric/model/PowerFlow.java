package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "power_flow")
public class PowerFlow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calculationCode;
    private String calculationName;
    private LocalDateTime calculationTime;
    private String calculationType;
    private String status;
    private String model;
    @Column(columnDefinition = "TEXT")
    private String nodeData;
    @Column(columnDefinition = "TEXT")
    private String branchData;
    @Column(columnDefinition = "TEXT")
    private String resultData;
    private Integer nodeCount;
    private Integer branchCount;
    private Integer iterationCount;
    private Double convergence;
    private Double totalGeneration;
    private Double totalLoad;
    private Double totalLoss;
    private Double maxNodeVoltage;
    private Double minNodeVoltage;
    private String maxVoltageNode;
    private String minVoltageNode;
    private Double maxBranchLoad;
    private String maxLoadBranch;
    private String overloadBranches;
    private String abnormalNodes;
    private String analysisResult;
    private String suggestion;
    private String remark;

    public PowerFlow() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCalculationCode() { return calculationCode; }
    public void setCalculationCode(String calculationCode) { this.calculationCode = calculationCode; }
    public String getCalculationName() { return calculationName; }
    public void setCalculationName(String calculationName) { this.calculationName = calculationName; }
    public LocalDateTime getCalculationTime() { return calculationTime; }
    public void setCalculationTime(LocalDateTime calculationTime) { this.calculationTime = calculationTime; }
    public String getCalculationType() { return calculationType; }
    public void setCalculationType(String calculationType) { this.calculationType = calculationType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getNodeData() { return nodeData; }
    public void setNodeData(String nodeData) { this.nodeData = nodeData; }
    public String getBranchData() { return branchData; }
    public void setBranchData(String branchData) { this.branchData = branchData; }
    public String getResultData() { return resultData; }
    public void setResultData(String resultData) { this.resultData = resultData; }
    public Integer getNodeCount() { return nodeCount; }
    public void setNodeCount(Integer nodeCount) { this.nodeCount = nodeCount; }
    public Integer getBranchCount() { return branchCount; }
    public void setBranchCount(Integer branchCount) { this.branchCount = branchCount; }
    public Integer getIterationCount() { return iterationCount; }
    public void setIterationCount(Integer iterationCount) { this.iterationCount = iterationCount; }
    public Double getConvergence() { return convergence; }
    public void setConvergence(Double convergence) { this.convergence = convergence; }
    public Double getTotalGeneration() { return totalGeneration; }
    public void setTotalGeneration(Double totalGeneration) { this.totalGeneration = totalGeneration; }
    public Double getTotalLoad() { return totalLoad; }
    public void setTotalLoad(Double totalLoad) { this.totalLoad = totalLoad; }
    public Double getTotalLoss() { return totalLoss; }
    public void setTotalLoss(Double totalLoss) { this.totalLoss = totalLoss; }
    public Double getMaxNodeVoltage() { return maxNodeVoltage; }
    public void setMaxNodeVoltage(Double maxNodeVoltage) { this.maxNodeVoltage = maxNodeVoltage; }
    public Double getMinNodeVoltage() { return minNodeVoltage; }
    public void setMinNodeVoltage(Double minNodeVoltage) { this.minNodeVoltage = minNodeVoltage; }
    public String getMaxVoltageNode() { return maxVoltageNode; }
    public void setMaxVoltageNode(String maxVoltageNode) { this.maxVoltageNode = maxVoltageNode; }
    public String getMinVoltageNode() { return minVoltageNode; }
    public void setMinVoltageNode(String minVoltageNode) { this.minVoltageNode = minVoltageNode; }
    public Double getMaxBranchLoad() { return maxBranchLoad; }
    public void setMaxBranchLoad(Double maxBranchLoad) { this.maxBranchLoad = maxBranchLoad; }
    public String getMaxLoadBranch() { return maxLoadBranch; }
    public void setMaxLoadBranch(String maxLoadBranch) { this.maxLoadBranch = maxLoadBranch; }
    public String getOverloadBranches() { return overloadBranches; }
    public void setOverloadBranches(String overloadBranches) { this.overloadBranches = overloadBranches; }
    public String getAbnormalNodes() { return abnormalNodes; }
    public void setAbnormalNodes(String abnormalNodes) { this.abnormalNodes = abnormalNodes; }
    public String getAnalysisResult() { return analysisResult; }
    public void setAnalysisResult(String analysisResult) { this.analysisResult = analysisResult; }
    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
