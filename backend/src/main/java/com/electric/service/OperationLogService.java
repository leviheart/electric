package com.electric.service;

import com.electric.model.OperationLog;
import com.electric.repository.OperationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationLogService {
    
    @Autowired
    private OperationLogRepository operationLogRepository;
    
    public List<OperationLog> findAll() {
        return operationLogRepository.findAll();
    }
    
    public Optional<OperationLog> findById(Long id) {
        return operationLogRepository.findById(id);
    }
    
    public OperationLog save(OperationLog log) {
        return operationLogRepository.save(log);
    }
    
    public List<OperationLog> findByUsername(String username) {
        return operationLogRepository.findByUsername(username);
    }
    
    public List<OperationLog> findByModule(String module) {
        return operationLogRepository.findByModule(module);
    }
    
    public List<OperationLog> findByTimeRange(LocalDateTime start, LocalDateTime end) {
        return operationLogRepository.findByOperationTimeBetween(start, end);
    }
    
    public List<OperationLog> findRecentLogs(int days) {
        LocalDateTime start = LocalDateTime.now().minusDays(days);
        return operationLogRepository.findRecentLogs(start);
    }
    
    public Long countFailedOperations() {
        return operationLogRepository.countFailedOperations();
    }
    
    public List<Object[]> countByModule() {
        return operationLogRepository.countByModule();
    }
    
    public List<Object[]> countByDate(int days) {
        LocalDateTime start = LocalDateTime.now().minusDays(days);
        return operationLogRepository.countByDate(start);
    }
    
    public void log(String username, String userId, String operation, String method, String module,
                   String targetType, Long targetId, String targetName, String ip,
                   Integer status, String errorMsg, Long duration, String requestParams) {
        OperationLog log = new OperationLog();
        log.setUsername(username);
        log.setUserId(userId);
        log.setOperation(operation);
        log.setMethod(method);
        log.setModule(module);
        log.setTargetType(targetType);
        log.setTargetId(targetId);
        log.setTargetName(targetName);
        log.setIp(ip);
        log.setStatus(status);
        log.setErrorMsg(errorMsg);
        log.setDuration(duration);
        log.setRequestParams(requestParams);
        log.setOperationTime(LocalDateTime.now());
        operationLogRepository.save(log);
    }
}
