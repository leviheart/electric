package com.electric.repository;

import com.electric.model.Substation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubstationRepository extends JpaRepository<Substation, Long> {
    
    List<Substation> findByVoltageLevel(String voltageLevel);
    
    List<Substation> findByStatus(String status);
    
    List<Substation> findByNameContaining(String keyword);
    
    List<Substation> findByRegion(String region);
    
    List<Substation> findByLoadRateGreaterThan(Double loadRate);
    
    List<Substation> findByLoadRateBetween(Double min, Double max);
    
    @Query("SELECT s FROM Substation s WHERE s.latitude BETWEEN :minLat AND :maxLat AND s.longitude BETWEEN :minLng AND :maxLng")
    List<Substation> findByBoundingBox(
        @Param("minLat") Double minLat,
        @Param("maxLat") Double maxLat,
        @Param("minLng") Double minLng,
        @Param("maxLng") Double maxLng
    );
    
    @Query("SELECT COUNT(s) FROM Substation s WHERE s.status = :status")
    Long countByStatus(@Param("status") String status);
    
    @Query("SELECT AVG(s.loadRate) FROM Substation s")
    Double getAverageLoadRate();
}
