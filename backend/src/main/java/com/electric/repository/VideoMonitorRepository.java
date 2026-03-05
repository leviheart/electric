package com.electric.repository;

import com.electric.model.VideoMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VideoMonitorRepository extends JpaRepository<VideoMonitor, Long> {
    List<VideoMonitor> findByStatus(String status);
    List<VideoMonitor> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<VideoMonitor> findByManufacturer(String manufacturer);
    
    @Query("SELECT COUNT(vm) FROM VideoMonitor vm WHERE vm.status = '在线'")
    Long countOnline();
    
    @Query("SELECT COUNT(vm) FROM VideoMonitor vm WHERE vm.status = '离线'")
    Long countOffline();
    
    @Query("SELECT vm FROM VideoMonitor vm WHERE vm.deviceType = :deviceType AND vm.deviceId = :deviceId")
    List<VideoMonitor> findByDevice(String deviceType, Long deviceId);
}
