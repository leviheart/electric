package com.electric.repository;

import com.electric.model.LineLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LineLossRepository extends JpaRepository<LineLoss, Long> {
    List<LineLoss> findByLineId(Long lineId);
    List<LineLoss> findByLineType(String lineType);
    List<LineLoss> findByTimeGranularity(String timeGranularity);
    List<LineLoss> findByRecordTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT AVG(ll.lossRate) FROM LineLoss ll WHERE ll.recordTime >= :start")
    Double getAverageLossRate(LocalDateTime start);
    
    @Query("SELECT ll FROM LineLoss ll WHERE ll.lossRate > :threshold ORDER BY ll.lossRate DESC")
    List<LineLoss> findHighLossLines(Double threshold);
    
    @Query("SELECT ll.lineName, ll.lossRate FROM LineLoss ll WHERE ll.recordTime = (SELECT MAX(l.recordTime) FROM LineLoss l)")
    List<Object[]> getLatestLossRates();
    
    @Query("SELECT SUM(ll.lossPower) FROM LineLoss ll WHERE ll.recordTime BETWEEN :start AND :end")
    Double getTotalLossPower(LocalDateTime start, LocalDateTime end);
}
