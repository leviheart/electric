package com.electric.service;

import com.electric.model.InspectionTask;
import com.electric.repository.InspectionTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InspectionTaskService {
    
    @Autowired
    private InspectionTaskRepository inspectionTaskRepository;
    
    public List<InspectionTask> findAll() {
        return inspectionTaskRepository.findAll();
    }
    
    public Optional<InspectionTask> findById(Long id) {
        return inspectionTaskRepository.findById(id);
    }
    
    public InspectionTask save(InspectionTask task) {
        return inspectionTaskRepository.save(task);
    }
    
    public void deleteById(Long id) {
        inspectionTaskRepository.deleteById(id);
    }
    
    public List<InspectionTask> findByStatus(String status) {
        return inspectionTaskRepository.findByStatus(status);
    }
    
    public List<InspectionTask> findActiveTasks() {
        return inspectionTaskRepository.findActiveTasks();
    }
    
    public InspectionTask start(Long id) {
        Optional<InspectionTask> opt = inspectionTaskRepository.findById(id);
        if (opt.isPresent()) {
            InspectionTask task = opt.get();
            task.setStatus("执行中");
            task.setActualStartTime(LocalDateTime.now());
            return inspectionTaskRepository.save(task);
        }
        return null;
    }
    
    public InspectionTask complete(Long id, String result, Integer normalItems, Integer abnormalItems) {
        Optional<InspectionTask> opt = inspectionTaskRepository.findById(id);
        if (opt.isPresent()) {
            InspectionTask task = opt.get();
            task.setStatus("已完成");
            task.setActualEndTime(LocalDateTime.now());
            task.setResult(result);
            task.setNormalItems(normalItems);
            task.setAbnormalItems(abnormalItems);
            return inspectionTaskRepository.save(task);
        }
        return null;
    }
    
    public Long countByStatus(String status) {
        return inspectionTaskRepository.countByStatus(status);
    }
    
    public Double getAverageNormalRate() {
        return inspectionTaskRepository.getAverageNormalRate();
    }
}
