package com.electric.repository;

import com.electric.model.PowerFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PowerFlowRepository extends JpaRepository<PowerFlow, Long> {
    List<PowerFlow> findByStatus(String status);
    List<PowerFlow> findByCalculationType(String calculationType);
    List<PowerFlow> findByCalculationTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT pf FROM PowerFlow pf ORDER BY pf.calculationTime DESC")
    List<PowerFlow> findAllOrderByTimeDesc();
    
    @Query("SELECT pf FROM PowerFlow pf WHERE pf.status = '计算完成' ORDER BY pf.calculationTime DESC LIMIT 1")
    PowerFlow findLatestCompleted();
    
    @Query("SELECT COUNT(pf) FROM PowerFlow pf WHERE pf.status = :status")
    Long countByStatus(String status);
}
