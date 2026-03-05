package com.electric.repository;

import com.electric.model.DeviceHealth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceHealthRepository extends JpaRepository<DeviceHealth, Long> {
    List<DeviceHealth> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<DeviceHealth> findByHealthLevel(String healthLevel);
    List<DeviceHealth> findByRiskLevel(String riskLevel);
    List<DeviceHealth> findByEvaluateTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT dh FROM DeviceHealth dh WHERE dh.deviceType = :deviceType AND dh.deviceId = :deviceId ORDER BY dh.evaluateTime DESC LIMIT 1")
    Optional<DeviceHealth> findLatestByDevice(String deviceType, Long deviceId);
    
    @Query("SELECT AVG(dh.healthScore) FROM DeviceHealth dh")
    Double getAverageHealthScore();
    
    @Query("SELECT dh.healthLevel, COUNT(dh) FROM DeviceHealth dh GROUP BY dh.healthLevel")
    List<Object[]> countByHealthLevel();
    
    @Query("SELECT dh FROM DeviceHealth dh WHERE dh.healthScore < :threshold ORDER BY dh.healthScore ASC")
    List<DeviceHealth> findLowHealthDevices(Double threshold);
}
