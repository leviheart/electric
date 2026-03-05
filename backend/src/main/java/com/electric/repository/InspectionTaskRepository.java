package com.electric.repository;

import com.electric.model.InspectionTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InspectionTaskRepository extends JpaRepository<InspectionTask, Long> {
    List<InspectionTask> findByStatus(String status);
    List<InspectionTask> findByInspectionType(String inspectionType);
    List<InspectionTask> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<InspectionTask> findByInspector(String inspector);
    List<InspectionTask> findByPlanStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(it) FROM InspectionTask it WHERE it.status = :status")
    Long countByStatus(String status);
    
    @Query("SELECT it FROM InspectionTask it WHERE it.status IN ('待执行', '执行中')")
    List<InspectionTask> findActiveTasks();
    
    @Query("SELECT AVG(it.normalItems * 1.0 / it.totalItems) FROM InspectionTask it WHERE it.totalItems > 0")
    Double getAverageNormalRate();
}
