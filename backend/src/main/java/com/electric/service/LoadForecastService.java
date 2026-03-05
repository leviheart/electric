package com.electric.service;

import com.electric.model.LoadForecast;
import com.electric.repository.LoadForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class LoadForecastService {
    
    @Autowired
    private LoadForecastRepository loadForecastRepository;
    
    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    public List<LoadForecast> findAll() {
        return loadForecastRepository.findAll();
    }
    
    public Optional<LoadForecast> findById(Long id) {
        return loadForecastRepository.findById(id);
    }
    
    public LoadForecast save(LoadForecast forecast) {
        return loadForecastRepository.save(forecast);
    }
    
    public void deleteById(Long id) {
        loadForecastRepository.deleteById(id);
    }
    
    public List<LoadForecast> findByTarget(String targetType, Long targetId) {
        return loadForecastRepository.findByTargetTypeAndTargetId(targetType, targetId);
    }
    
    public List<LoadForecast> findForecasts(String targetType, Long targetId, LocalDateTime start) {
        return loadForecastRepository.findForecasts(targetType, targetId, start);
    }
    
    public List<LoadForecast> findUpcomingForecasts() {
        return loadForecastRepository.findUpcomingForecasts(LocalDateTime.now());
    }
    
    public Double getAverageErrorRate() {
        return loadForecastRepository.getAverageErrorRate();
    }
    
    public List<LoadForecast> generateForecast(String targetType, Long targetId, String targetName,
                                               Double baseLoad, int hours) {
        List<LoadForecast> forecasts = new ArrayList<>();
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 1; i <= hours; i++) {
            LoadForecast forecast = new LoadForecast();
            forecast.setTargetType(targetType);
            forecast.setTargetId(targetId);
            forecast.setTargetName(targetName);
            forecast.setForecastTime(now.plusHours(i));
            forecast.setForecastType("短期预测");
            forecast.setAlgorithm("时间序列分析");
            forecast.setModel("ARIMA");
            
            double hourFactor = getHourFactor(now.plusHours(i).getHour());
            double randomFactor = 0.9 + random.nextDouble() * 0.2;
            double forecastValue = baseLoad * hourFactor * randomFactor;
            
            forecast.setForecastValue(round2(forecastValue));
            forecast.setConfidence("95%");
            forecast.setWeatherInfo("晴");
            forecast.setTemperature(round2(25.0 + random.nextDouble() * 5));
            forecast.setHolidayFlag("否");
            
            forecasts.add(forecast);
            loadForecastRepository.save(forecast);
        }
        
        return forecasts;
    }
    
    private double getHourFactor(int hour) {
        if (hour >= 8 && hour <= 11) return 1.2;
        if (hour >= 14 && hour <= 17) return 1.3;
        if (hour >= 18 && hour <= 21) return 1.5;
        if (hour >= 22 || hour <= 5) return 0.6;
        return 1.0;
    }
    
    public void updateActualValue(Long id, Double actualValue) {
        Optional<LoadForecast> opt = loadForecastRepository.findById(id);
        if (opt.isPresent()) {
            LoadForecast forecast = opt.get();
            forecast.setActualValue(actualValue);
            forecast.setErrorValue(Math.abs(actualValue - forecast.getForecastValue()));
            if (actualValue != 0) {
                forecast.setErrorRate(forecast.getErrorValue() / actualValue * 100);
            }
            loadForecastRepository.save(forecast);
        }
    }
}
