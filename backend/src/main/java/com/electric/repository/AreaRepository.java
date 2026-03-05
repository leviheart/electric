package com.electric.repository;

import com.electric.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    
    List<Area> findBySubstationName(String substationName);
    
    List<Area> findBySubstationNameContaining(String keyword);
    
    List<Area> findByStatus(String status);
    
    List<Area> findByNameContaining(String keyword);
    
    List<Area> findByLoadRateGreaterThan(Double loadRate);
    
    List<Area> findByLoadRateBetween(Double min, Double max);
    
    List<Area> findByCustomerCountGreaterThan(Integer count);
    
    List<Area> findByImportantCustomerCountGreaterThan(Integer count);
    
    @Query("SELECT COUNT(a) FROM Area a WHERE a.status = :status")
    Long countByStatus(@Param("status") String status);
    
    @Query("SELECT AVG(a.loadRate) FROM Area a")
    Double getAverageLoadRate();
    
    @Query("SELECT SUM(a.customerCount) FROM Area a")
    Long getTotalCustomerCount();
    
    @Query("SELECT SUM(a.transformerCapacity) FROM Area a")
    Double getTotalCapacity();
    
    @Query("SELECT a FROM Area a WHERE a.loadRate > :threshold ORDER BY a.loadRate DESC")
    List<Area> findTopOverloadedAreas(@Param("threshold") Double threshold);
}
