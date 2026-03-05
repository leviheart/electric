package com.electric.service;

import com.electric.model.MaintenancePlan;
import com.electric.repository.MaintenancePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenancePlanService {
    
    @Autowired
    private MaintenancePlanRepository maintenancePlanRepository;
    
    public List<MaintenancePlan> findAll() {
        return maintenancePlanRepository.findAll();
    }
    
    public Optional<MaintenancePlan> findById(Long id) {
        return maintenancePlanRepository.findById(id);
    }
    
    public MaintenancePlan save(MaintenancePlan plan) {
        return maintenancePlanRepository.save(plan);
    }
    
    public void deleteById(Long id) {
        maintenancePlanRepository.deleteById(id);
    }
    
    public List<MaintenancePlan> findByStatus(String status) {
        return maintenancePlanRepository.findByStatus(status);
    }
    
    public List<MaintenancePlan> findByDevice(String deviceType, Long deviceId) {
        return maintenancePlanRepository.findByDeviceTypeAndDeviceId(deviceType, deviceId);
    }
    
    public List<MaintenancePlan> findActivePlans() {
        return maintenancePlanRepository.findActivePlans();
    }
    
    public MaintenancePlan submit(Long id) {
        Optional<MaintenancePlan> opt = maintenancePlanRepository.findById(id);
        if (opt.isPresent()) {
            MaintenancePlan plan = opt.get();
            plan.setStatus("待审批");
            plan.setApplyTime(LocalDateTime.now());
            return maintenancePlanRepository.save(plan);
        }
        return null;
    }
    
    public MaintenancePlan approve(Long id, String approver, String comment) {
        Optional<MaintenancePlan> opt = maintenancePlanRepository.findById(id);
        if (opt.isPresent()) {
            MaintenancePlan plan = opt.get();
            plan.setStatus("已批准");
            plan.setApprover(approver);
            plan.setApproveTime(LocalDateTime.now());
            plan.setApproveComment(comment);
            return maintenancePlanRepository.save(plan);
        }
        return null;
    }
    
    public MaintenancePlan reject(Long id, String approver, String comment) {
        Optional<MaintenancePlan> opt = maintenancePlanRepository.findById(id);
        if (opt.isPresent()) {
            MaintenancePlan plan = opt.get();
            plan.setStatus("已拒绝");
            plan.setApprover(approver);
            plan.setApproveTime(LocalDateTime.now());
            plan.setApproveComment(comment);
            return maintenancePlanRepository.save(plan);
        }
        return null;
    }
    
    public MaintenancePlan start(Long id) {
        Optional<MaintenancePlan> opt = maintenancePlanRepository.findById(id);
        if (opt.isPresent()) {
            MaintenancePlan plan = opt.get();
            plan.setStatus("执行中");
            plan.setActualStartTime(LocalDateTime.now());
            return maintenancePlanRepository.save(plan);
        }
        return null;
    }
    
    public MaintenancePlan complete(Long id, String result) {
        Optional<MaintenancePlan> opt = maintenancePlanRepository.findById(id);
        if (opt.isPresent()) {
            MaintenancePlan plan = opt.get();
            plan.setStatus("已完成");
            plan.setActualEndTime(LocalDateTime.now());
            plan.setResult(result);
            return maintenancePlanRepository.save(plan);
        }
        return null;
    }
    
    public Long countByStatus(String status) {
        return maintenancePlanRepository.countByStatus(status);
    }
}
