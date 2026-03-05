package com.electric.repository;

import com.electric.model.TransmissionLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransmissionLineRepository extends JpaRepository<TransmissionLine, Long> {
    
    List<TransmissionLine> findByVoltageLevel(String voltageLevel);
    
    List<TransmissionLine> findByStatus(String status);
    
    List<TransmissionLine> findByLineType(String lineType);
    
    List<TransmissionLine> findByNameContaining(String keyword);
    
    List<TransmissionLine> findByStartSubstation(String startSubstation);
    
    List<TransmissionLine> findByEndSubstation(String endSubstation);
    
    List<TransmissionLine> findByStartSubstationOrEndSubstation(String startSubstation, String endSubstation);
    
    List<TransmissionLine> findByLoadRateGreaterThan(Double loadRate);
    
    List<TransmissionLine> findByLoadRateBetween(Double min, Double max);
    
    @Query("SELECT l FROM TransmissionLine l WHERE l.length BETWEEN :minLen AND :maxLen")
    List<TransmissionLine> findByLengthRange(
        @Param("minLen") Double minLen,
        @Param("maxLen") Double maxLen
    );
    
    @Query("SELECT COUNT(l) FROM TransmissionLine l WHERE l.status = :status")
    Long countByStatus(@Param("status") String status);
    
    @Query("SELECT AVG(l.loadRate) FROM TransmissionLine l")
    Double getAverageLoadRate();
    
    @Query("SELECT SUM(l.length) FROM TransmissionLine l")
    Double getTotalLength();
}
