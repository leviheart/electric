package com.electric.repository;

import com.electric.model.ReliabilityIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReliabilityIndexRepository extends JpaRepository<ReliabilityIndex, Long> {
    List<ReliabilityIndex> findByTargetTypeAndTargetId(String targetType, Long targetId);
    List<ReliabilityIndex> findByTimeGranularity(String timeGranularity);
    List<ReliabilityIndex> findByStatisticTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT ri FROM ReliabilityIndex ri WHERE ri.targetType = :targetType AND ri.targetId = :targetId ORDER BY ri.statisticTime DESC LIMIT 1")
    Optional<ReliabilityIndex> findLatestByTarget(String targetType, Long targetId);
    
    @Query("SELECT AVG(ri.saidi) FROM ReliabilityIndex ri")
    Double getAverageSaidi();
    
    @Query("SELECT AVG(ri.saifi) FROM ReliabilityIndex ri")
    Double getAverageSaifi();
    
    @Query("SELECT AVG(ri.asai) FROM ReliabilityIndex ri")
    Double getAverageAsai();
}
