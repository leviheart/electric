package com.electric.repository;

import com.electric.model.PowerOutage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PowerOutageRepository extends JpaRepository<PowerOutage, Long> {
    List<PowerOutage> findByStatus(String status);
    List<PowerOutage> findByOutageType(String outageType);
    List<PowerOutage> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<PowerOutage> findByApplicant(String applicant);
    List<PowerOutage> findByPlanStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(p) FROM PowerOutage p WHERE p.status = :status")
    Long countByStatus(String status);
    
    @Query("SELECT p FROM PowerOutage p WHERE p.status IN ('待审批', '已批准', '执行中')")
    List<PowerOutage> findActiveOutages();
    
    @Query("SELECT SUM(p.duration) FROM PowerOutage p WHERE p.status = '已完成'")
    Long getTotalOutageDuration();
    
    @Query("SELECT SUM(p.affectedCustomers) FROM PowerOutage p WHERE p.status = '已完成'")
    Long getTotalAffectedCustomers();
}
