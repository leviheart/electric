package com.electric.repository;

import com.electric.model.OperationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperationDataRepository extends JpaRepository<OperationData, Long> {
    
    List<OperationData> findByEntityTypeAndEntityId(String entityType, Long entityId);
    
    List<OperationData> findByEntityTypeAndEntityIdAndRecordTimeBetween(
        String entityType, Long entityId, LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT DISTINCT CAST(o.recordTime AS java.time.LocalDate) FROM OperationData o " +
           "WHERE o.entityType = :entityType AND o.entityId = :entityId")
    List<LocalDate> findDistinctDatesByEntityTypeAndEntityId(
        @Param("entityType") String entityType, @Param("entityId") Long entityId);
    
    @Query("SELECT o FROM OperationData o WHERE o.entityType = :entityType " +
           "AND o.entityId = :entityId AND CAST(o.recordTime AS java.time.LocalDate) = :date " +
           "ORDER BY o.recordTime")
    List<OperationData> findByEntityTypeAndEntityIdAndDate(
        @Param("entityType") String entityType, @Param("entityId") Long entityId, @Param("date") LocalDate date);
    
    @Query("SELECT o FROM OperationData o WHERE o.entityType = :entityType " +
           "AND o.entityId = :entityId ORDER BY o.recordTime DESC LIMIT 1")
    OperationData findLatestByEntityTypeAndEntityId(
        @Param("entityType") String entityType, @Param("entityId") Long entityId);
    
    @Query("SELECT DISTINCT CAST(o.recordTime AS java.time.LocalDate) FROM OperationData o")
    List<LocalDate> findAllDistinctDates();
}
