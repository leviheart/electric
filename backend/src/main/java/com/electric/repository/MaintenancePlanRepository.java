package com.electric.repository;

import com.electric.model.MaintenancePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenancePlanRepository extends JpaRepository<MaintenancePlan, Long> {
    List<MaintenancePlan> findByStatus(String status);
    List<MaintenancePlan> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<MaintenancePlan> findByApplicant(String applicant);
    List<MaintenancePlan> findByExecutor(String executor);
    List<MaintenancePlan> findByPriority(String priority);
    List<MaintenancePlan> findByPlanStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT mp FROM MaintenancePlan mp WHERE mp.status IN ('待审批', '已批准', '执行中')")
    List<MaintenancePlan> findActivePlans();
    
    @Query("SELECT COUNT(mp) FROM MaintenancePlan mp WHERE mp.status = :status")
    Long countByStatus(String status);
    
    @Query("SELECT mp FROM MaintenancePlan mp WHERE mp.planStartTime <= :date AND mp.status = '已批准'")
    List<MaintenancePlan> findDuePlans(LocalDateTime date);
}
