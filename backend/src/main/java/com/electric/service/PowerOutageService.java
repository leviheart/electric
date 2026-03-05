package com.electric.service;

import com.electric.model.PowerOutage;
import com.electric.repository.PowerOutageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PowerOutageService {
    
    @Autowired
    private PowerOutageRepository powerOutageRepository;
    
    public List<PowerOutage> findAll() {
        return powerOutageRepository.findAll();
    }
    
    public Optional<PowerOutage> findById(Long id) {
        return powerOutageRepository.findById(id);
    }
    
    public PowerOutage save(PowerOutage outage) {
        return powerOutageRepository.save(outage);
    }
    
    public void deleteById(Long id) {
        powerOutageRepository.deleteById(id);
    }
    
    public List<PowerOutage> findByStatus(String status) {
        return powerOutageRepository.findByStatus(status);
    }
    
    public List<PowerOutage> findActiveOutages() {
        return powerOutageRepository.findActiveOutages();
    }
    
    public PowerOutage apply(Long id, String applicant) {
        Optional<PowerOutage> opt = powerOutageRepository.findById(id);
        if (opt.isPresent()) {
            PowerOutage outage = opt.get();
            outage.setStatus("待审批");
            outage.setApplicant(applicant);
            outage.setApplyTime(LocalDateTime.now());
            return powerOutageRepository.save(outage);
        }
        return null;
    }
    
    public PowerOutage approve(Long id, String approver) {
        Optional<PowerOutage> opt = powerOutageRepository.findById(id);
        if (opt.isPresent()) {
            PowerOutage outage = opt.get();
            outage.setStatus("已批准");
            outage.setApprover(approver);
            outage.setApproveTime(LocalDateTime.now());
            return powerOutageRepository.save(outage);
        }
        return null;
    }
    
    public PowerOutage start(Long id, String executor) {
        Optional<PowerOutage> opt = powerOutageRepository.findById(id);
        if (opt.isPresent()) {
            PowerOutage outage = opt.get();
            outage.setStatus("执行中");
            outage.setExecutor(executor);
            outage.setActualStartTime(LocalDateTime.now());
            return powerOutageRepository.save(outage);
        }
        return null;
    }
    
    public PowerOutage complete(Long id, String actualRecovery) {
        Optional<PowerOutage> opt = powerOutageRepository.findById(id);
        if (opt.isPresent()) {
            PowerOutage outage = opt.get();
            outage.setStatus("已完成");
            outage.setActualEndTime(LocalDateTime.now());
            outage.setActualRecovery(actualRecovery);
            if (outage.getActualStartTime() != null && outage.getActualEndTime() != null) {
                long minutes = java.time.Duration.between(outage.getActualStartTime(), outage.getActualEndTime()).toMinutes();
                outage.setDuration((int) minutes);
            }
            return powerOutageRepository.save(outage);
        }
        return null;
    }
    
    public Long countByStatus(String status) {
        return powerOutageRepository.countByStatus(status);
    }
    
    public Long getTotalOutageDuration() {
        return powerOutageRepository.getTotalOutageDuration();
    }
    
    public Long getTotalAffectedCustomers() {
        return powerOutageRepository.getTotalAffectedCustomers();
    }
}
