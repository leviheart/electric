package com.electric.repository;

import com.electric.model.DispatchOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DispatchOrderRepository extends JpaRepository<DispatchOrder, Long> {
    List<DispatchOrder> findByStatus(String status);
    List<DispatchOrder> findByOrderType(String orderType);
    List<DispatchOrder> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<DispatchOrder> findByIssuer(String issuer);
    List<DispatchOrder> findByReceiver(String receiver);
    List<DispatchOrder> findByExecutor(String executor);
    List<DispatchOrder> findByPlanTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(d) FROM DispatchOrder d WHERE d.status = :status")
    Long countByStatus(String status);
    
    @Query("SELECT d FROM DispatchOrder d WHERE d.status IN ('待接收', '已接收', '执行中')")
    List<DispatchOrder> findActiveOrders();
}
