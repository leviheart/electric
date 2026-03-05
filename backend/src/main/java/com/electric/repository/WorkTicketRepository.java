package com.electric.repository;

import com.electric.model.WorkTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkTicketRepository extends JpaRepository<WorkTicket, Long> {
    List<WorkTicket> findByStatus(String status);
    List<WorkTicket> findByTicketType(String ticketType);
    List<WorkTicket> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<WorkTicket> findByApplicant(String applicant);
    List<WorkTicket> findByWorkLeader(String workLeader);
    List<WorkTicket> findByPlanStartTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(wt) FROM WorkTicket wt WHERE wt.status = :status")
    Long countByStatus(String status);
    
    @Query("SELECT wt FROM WorkTicket wt WHERE wt.status IN ('待审批', '已签发', '已接收', '许可中')")
    List<WorkTicket> findActiveTickets();
}
