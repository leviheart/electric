package com.electric.repository;

import com.electric.model.Defect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DefectRepository extends JpaRepository<Defect, Long> {
    List<Defect> findByStatus(String status);
    List<Defect> findByDefectLevel(String defectLevel);
    List<Defect> findByDefectType(String defectType);
    List<Defect> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<Defect> findByDiscoverer(String discoverer);
    List<Defect> findByHandler(String handler);
    List<Defect> findByDiscoveryTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(d) FROM Defect d WHERE d.status = :status")
    Long countByStatus(String status);
    
    @Query("SELECT d FROM Defect d WHERE d.status IN ('待处理', '处理中')")
    List<Defect> findActiveDefects();
    
    @Query("SELECT COUNT(d) FROM Defect d WHERE d.defectLevel = :level AND d.status IN ('待处理', '处理中')")
    Long countActiveByLevel(String level);
}
