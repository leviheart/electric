package com.electric.repository;

import com.electric.model.N1Check;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface N1CheckRepository extends JpaRepository<N1Check, Long> {
    List<N1Check> findByStatus(String status);
    List<N1Check> findByCheckType(String checkType);
    List<N1Check> findByTargetTypeAndTargetId(String targetType, Long targetId);
    List<N1Check> findByRiskLevel(String riskLevel);
    List<N1Check> findByCheckTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT n FROM N1Check n ORDER BY n.checkTime DESC")
    List<N1Check> findAllOrderByTimeDesc();
    
    @Query("SELECT n FROM N1Check n WHERE n.status = '已完成' ORDER BY n.checkTime DESC LIMIT 1")
    N1Check findLatestCompleted();
    
    @Query("SELECT COUNT(n) FROM N1Check n WHERE n.riskLevel = :riskLevel")
    Long countByRiskLevel(String riskLevel);
}
