package com.electric.service;

import com.electric.model.LineLoss;
import com.electric.repository.LineLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LineLossService {
    
    @Autowired
    private LineLossRepository lineLossRepository;
    
    public List<LineLoss> findAll() {
        return lineLossRepository.findAll();
    }
    
    public Optional<LineLoss> findById(Long id) {
        return lineLossRepository.findById(id);
    }
    
    public LineLoss save(LineLoss lineLoss) {
        return lineLossRepository.save(lineLoss);
    }
    
    public void deleteById(Long id) {
        lineLossRepository.deleteById(id);
    }
    
    public List<LineLoss> findByLineId(Long lineId) {
        return lineLossRepository.findByLineId(lineId);
    }
    
    public List<LineLoss> findByTimeRange(LocalDateTime start, LocalDateTime end) {
        return lineLossRepository.findByRecordTimeBetween(start, end);
    }
    
    public List<LineLoss> findHighLossLines(Double threshold) {
        return lineLossRepository.findHighLossLines(threshold);
    }
    
    public Double getAverageLossRate(LocalDateTime start) {
        return lineLossRepository.getAverageLossRate(start);
    }
    
    public Double getTotalLossPower(LocalDateTime start, LocalDateTime end) {
        return lineLossRepository.getTotalLossPower(start, end);
    }
    
    public List<Object[]> getLatestLossRates() {
        return lineLossRepository.getLatestLossRates();
    }
    
    public LineLoss calculateLoss(Long lineId, Double supplyPower, Double sellPower, 
                                  Double resistance, Double current, Double voltage, Double powerFactor) {
        LineLoss loss = new LineLoss();
        loss.setLineId(lineId);
        loss.setRecordTime(LocalDateTime.now());
        loss.setSupplyPower(supplyPower);
        loss.setSellPower(sellPower);
        loss.setLossPower(supplyPower - sellPower);
        loss.setLossRate((supplyPower - sellPower) / supplyPower * 100);
        loss.setLineResistance(resistance);
        loss.setLineCurrent(current);
        loss.setLineVoltage(voltage);
        loss.setPowerFactor(powerFactor);
        loss.setTechnicalLoss(3 * resistance * current * current * 24 / 1000);
        loss.setManagementLoss(loss.getLossPower() - loss.getTechnicalLoss());
        
        if (loss.getLossRate() > 10) {
            loss.setAnalysisResult("线损率偏高");
            loss.setAbnormalReason("可能存在窃电、计量误差或设备老化");
            loss.setSuggestion("建议进行线损排查，检查计量装置和线路设备");
        } else if (loss.getLossRate() > 5) {
            loss.setAnalysisResult("线损率正常偏高");
            loss.setAbnormalReason("技术损耗占比较高");
            loss.setSuggestion("建议优化无功补偿，提高功率因数");
        } else {
            loss.setAnalysisResult("线损率正常");
            loss.setSuggestion("继续保持良好运行状态");
        }
        
        return lineLossRepository.save(loss);
    }
}
