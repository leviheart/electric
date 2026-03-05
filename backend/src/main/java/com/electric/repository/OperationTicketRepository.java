package com.electric.repository;

import com.electric.model.OperationTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationTicketRepository extends JpaRepository<OperationTicket, Long> {
    List<OperationTicket> findByStatus(String status);
    List<OperationTicket> findByTicketType(String ticketType);
    List<OperationTicket> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<OperationTicket> findByApplicant(String applicant);
    List<OperationTicket> findByOperator(String operator);
    List<OperationTicket> findByPlanTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(ot) FROM OperationTicket ot WHERE ot.status = :status")
    Long countByStatus(String status);
    
    @Query("SELECT ot FROM OperationTicket ot WHERE ot.status IN ('待审批', '已签发', '执行中')")
    List<OperationTicket> findActiveTickets();
}
