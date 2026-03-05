package com.electric.repository;

import com.electric.model.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationLogRepository extends JpaRepository<OperationLog, Long> {
    List<OperationLog> findByUsername(String username);
    List<OperationLog> findByModule(String module);
    List<OperationLog> findByOperation(String operation);
    List<OperationLog> findByStatus(Integer status);
    List<OperationLog> findByTargetTypeAndTargetId(String targetType, Long targetId);
    List<OperationLog> findByOperationTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT ol FROM OperationLog ol WHERE ol.operationTime >= :start ORDER BY ol.operationTime DESC")
    List<OperationLog> findRecentLogs(LocalDateTime start);
    
    @Query("SELECT COUNT(ol) FROM OperationLog ol WHERE ol.status = 0")
    Long countFailedOperations();
    
    @Query("SELECT ol.module, COUNT(ol) FROM OperationLog ol GROUP BY ol.module")
    List<Object[]> countByModule();
    
    @Query("SELECT DATE(ol.operationTime), COUNT(ol) FROM OperationLog ol WHERE ol.operationTime >= :start GROUP BY DATE(ol.operationTime)")
    List<Object[]> countByDate(LocalDateTime start);
}
