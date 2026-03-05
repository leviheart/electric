package com.electric.service;

import com.electric.model.ReliabilityIndex;
import com.electric.repository.ReliabilityIndexRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReliabilityIndexService {
    
    @Autowired
    private ReliabilityIndexRepository reliabilityIndexRepository;
    
    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    public List<ReliabilityIndex> findAll() {
        return reliabilityIndexRepository.findAll();
    }
    
    public Optional<ReliabilityIndex> findById(Long id) {
        return reliabilityIndexRepository.findById(id);
    }
    
    public ReliabilityIndex save(ReliabilityIndex index) {
        return reliabilityIndexRepository.save(index);
    }
    
    public void deleteById(Long id) {
        reliabilityIndexRepository.deleteById(id);
    }
    
    public Optional<ReliabilityIndex> findLatestByTarget(String targetType, Long targetId) {
        return reliabilityIndexRepository.findLatestByTarget(targetType, targetId);
    }
    
    public Double getAverageSaidi() {
        return reliabilityIndexRepository.getAverageSaidi();
    }
    
    public Double getAverageSaifi() {
        return reliabilityIndexRepository.getAverageSaifi();
    }
    
    public Double getAverageAsai() {
        return reliabilityIndexRepository.getAverageAsai();
    }
    
    public ReliabilityIndex calculate(String targetType, Long targetId, String targetName,
                                      String timeGranularity, Integer totalCustomerCount,
                                      Integer totalOutageCount, Integer plannedOutageCount,
                                      Integer unplannedOutageCount, Integer totalOutageDuration,
                                      Integer plannedOutageDuration, Integer unplannedOutageDuration,
                                      Integer customerInterruptCount, Integer momentaryOutageCount) {
        ReliabilityIndex index = new ReliabilityIndex();
        index.setTargetType(targetType);
        index.setTargetId(targetId);
        index.setTargetName(targetName);
        index.setStatisticTime(LocalDateTime.now());
        index.setTimeGranularity(timeGranularity);
        index.setTotalCustomerCount(totalCustomerCount);
        index.setTotalOutageCount(totalOutageCount);
        index.setPlannedOutageCount(plannedOutageCount);
        index.setUnplannedOutageCount(unplannedOutageCount);
        index.setTotalOutageDuration(totalOutageDuration);
        index.setPlannedOutageDuration(plannedOutageDuration);
        index.setUnplannedOutageDuration(unplannedOutageDuration);
        index.setCustomerInterruptCount(customerInterruptCount);
        index.setMomentaryOutageCount(momentaryOutageCount);
        
        if (totalCustomerCount > 0) {
            index.setSaidi(round2((double) totalOutageDuration / totalCustomerCount));
            index.setSaifi(round2((double) customerInterruptCount / totalCustomerCount));
        }
        
        if (customerInterruptCount > 0) {
            index.setCaidi(round2((double) totalOutageDuration / customerInterruptCount));
        }
        
        double hoursInPeriod = "月度".equals(timeGranularity) ? 720 : ("季度".equals(timeGranularity) ? 2160 : 8760);
        index.setAsai(round2((hoursInPeriod * totalCustomerCount - totalOutageDuration) / (hoursInPeriod * totalCustomerCount) * 100));
        
        if (totalCustomerCount > 0) {
            index.setMaifi(round2((double) momentaryOutageCount / totalCustomerCount));
        }
        
        index.setAffectedCustomerCount(customerInterruptCount);
        index.setAvgRestoreTime(totalOutageCount > 0 ? round2((double) totalOutageDuration / totalOutageCount) : 0);
        
        if (unplannedOutageCount > plannedOutageCount) {
            index.setMainOutageReason("故障停电");
            index.setImprovementSuggestion("建议加强设备巡检，减少故障停电");
        } else {
            index.setMainOutageReason("计划停电");
            index.setImprovementSuggestion("建议优化停电计划，减少停电时间");
        }
        
        return reliabilityIndexRepository.save(index);
    }
}
