package com.electric.controller;

import com.electric.model.LoadForecast;
import com.electric.service.LoadForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/load-forecasts")
@CrossOrigin(origins = "*")
public class LoadForecastController {
    
    @Autowired
    private LoadForecastService loadForecastService;
    
    @GetMapping
    public List<LoadForecast> getAll() { return loadForecastService.findAll(); }
    
    @GetMapping("/{id}")
    public LoadForecast getById(@PathVariable Long id) { return loadForecastService.findById(id).orElse(null); }
    
    @GetMapping("/target/{targetType}/{targetId}")
    public List<LoadForecast> getByTarget(@PathVariable String targetType, @PathVariable Long targetId) {
        return loadForecastService.findByTarget(targetType, targetId);
    }
    
    @GetMapping("/upcoming")
    public List<LoadForecast> getUpcoming() { return loadForecastService.findUpcomingForecasts(); }
}
