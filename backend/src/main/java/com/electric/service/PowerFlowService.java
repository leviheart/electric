package com.electric.service;

import com.electric.model.PowerFlow;
import com.electric.repository.PowerFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PowerFlowService {
    
    @Autowired
    private PowerFlowRepository powerFlowRepository;
    
    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    public List<PowerFlow> findAll() {
        return powerFlowRepository.findAll();
    }
    
    public Optional<PowerFlow> findById(Long id) {
        return powerFlowRepository.findById(id);
    }
    
    public PowerFlow save(PowerFlow powerFlow) {
        return powerFlowRepository.save(powerFlow);
    }
    
    public void deleteById(Long id) {
        powerFlowRepository.deleteById(id);
    }
    
    public PowerFlow findLatestCompleted() {
        return powerFlowRepository.findLatestCompleted();
    }
    
    public PowerFlow calculate(String calculationName, String nodeData, String branchData) {
        PowerFlow pf = new PowerFlow();
        pf.setCalculationCode("PF" + System.currentTimeMillis());
        pf.setCalculationName(calculationName);
        pf.setCalculationTime(LocalDateTime.now());
        pf.setCalculationType("潮流计算");
        pf.setStatus("计算中");
        pf.setModel("牛顿-拉夫逊法");
        pf.setNodeData(nodeData);
        pf.setBranchData(branchData);
        
        PowerFlow saved = powerFlowRepository.save(pf);
        
        simulateCalculation(saved);
        
        return powerFlowRepository.save(saved);
    }
    
    private void simulateCalculation(PowerFlow pf) {
        Random random = new Random();
        
        pf.setStatus("计算完成");
        pf.setNodeCount(10 + random.nextInt(20));
        pf.setBranchCount(15 + random.nextInt(30));
        pf.setIterationCount(5 + random.nextInt(10));
        pf.setConvergence(round2(0.0001 + random.nextDouble() * 0.001));
        pf.setTotalGeneration(round2(500.0 + random.nextDouble() * 200));
        pf.setTotalLoad(round2(480.0 + random.nextDouble() * 180));
        pf.setTotalLoss(round2(pf.getTotalGeneration() - pf.getTotalLoad()));
        pf.setMaxNodeVoltage(round2(1.05 + random.nextDouble() * 0.03));
        pf.setMinNodeVoltage(round2(0.95 + random.nextDouble() * 0.03));
        pf.setMaxVoltageNode("节点" + (1 + random.nextInt(10)));
        pf.setMinVoltageNode("节点" + (1 + random.nextInt(10)));
        pf.setMaxBranchLoad(round2(85.0 + random.nextDouble() * 20));
        pf.setMaxLoadBranch("线路" + (1 + random.nextInt(10)));
        
        if (pf.getMaxBranchLoad() > 100) {
            pf.setOverloadBranches("线路" + (1 + random.nextInt(5)));
            pf.setAnalysisResult("存在过载线路");
            pf.setSuggestion("建议进行负荷转移或线路扩容");
        } else if (pf.getMinNodeVoltage() < 0.95) {
            pf.setAbnormalNodes("节点" + (1 + random.nextInt(5)));
            pf.setAnalysisResult("存在电压越限节点");
            pf.setSuggestion("建议增加无功补偿装置");
        } else {
            pf.setAnalysisResult("系统运行正常");
            pf.setSuggestion("继续保持良好运行状态");
        }
    }
    
    public Long countByStatus(String status) {
        return powerFlowRepository.countByStatus(status);
    }
}
