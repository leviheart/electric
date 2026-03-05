package com.electric.repository;

import com.electric.model.LoadForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoadForecastRepository extends JpaRepository<LoadForecast, Long> {
    List<LoadForecast> findByTargetTypeAndTargetId(String targetType, Long targetId);
    List<LoadForecast> findByForecastType(String forecastType);
    List<LoadForecast> findByForecastTimeBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT lf FROM LoadForecast lf WHERE lf.targetType = :targetType AND lf.targetId = :targetId AND lf.forecastTime >= :start ORDER BY lf.forecastTime ASC")
    List<LoadForecast> findForecasts(String targetType, Long targetId, LocalDateTime start);
    
    @Query("SELECT AVG(lf.errorRate) FROM LoadForecast lf WHERE lf.actualValue IS NOT NULL")
    Double getAverageErrorRate();
    
    @Query("SELECT lf FROM LoadForecast lf WHERE lf.forecastTime >= :now ORDER BY lf.forecastTime ASC")
    List<LoadForecast> findUpcomingForecasts(LocalDateTime now);
}
