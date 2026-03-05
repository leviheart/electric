package com.electric.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "load_forecast")
public class LoadForecast {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String targetType;
    private Long targetId;
    private String targetName;
    private LocalDateTime forecastTime;
    private String forecastType;
    private Double forecastValue;
    private Double actualValue;
    private Double errorValue;
    private Double errorRate;
    private String algorithm;
    private String model;
    @Column(columnDefinition = "TEXT")
    private String parameters;
    @Column(columnDefinition = "TEXT")
    private String historicalData;
    private String weatherInfo;
    private Double temperature;
    private String holidayFlag;
    private String confidence;
    private Double maxValue;
    private Double minValue;
    private LocalDateTime maxTime;
    private LocalDateTime minTime;
    private String peakType;
    private String remark;

    public LoadForecast() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTargetType() { return targetType; }
    public void setTargetType(String targetType) { this.targetType = targetType; }
    public Long getTargetId() { return targetId; }
    public void setTargetId(Long targetId) { this.targetId = targetId; }
    public String getTargetName() { return targetName; }
    public void setTargetName(String targetName) { this.targetName = targetName; }
    public LocalDateTime getForecastTime() { return forecastTime; }
    public void setForecastTime(LocalDateTime forecastTime) { this.forecastTime = forecastTime; }
    public String getForecastType() { return forecastType; }
    public void setForecastType(String forecastType) { this.forecastType = forecastType; }
    public Double getForecastValue() { return forecastValue; }
    public void setForecastValue(Double forecastValue) { this.forecastValue = forecastValue; }
    public Double getActualValue() { return actualValue; }
    public void setActualValue(Double actualValue) { this.actualValue = actualValue; }
    public Double getErrorValue() { return errorValue; }
    public void setErrorValue(Double errorValue) { this.errorValue = errorValue; }
    public Double getErrorRate() { return errorRate; }
    public void setErrorRate(Double errorRate) { this.errorRate = errorRate; }
    public String getAlgorithm() { return algorithm; }
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getParameters() { return parameters; }
    public void setParameters(String parameters) { this.parameters = parameters; }
    public String getHistoricalData() { return historicalData; }
    public void setHistoricalData(String historicalData) { this.historicalData = historicalData; }
    public String getWeatherInfo() { return weatherInfo; }
    public void setWeatherInfo(String weatherInfo) { this.weatherInfo = weatherInfo; }
    public Double getTemperature() { return temperature; }
    public void setTemperature(Double temperature) { this.temperature = temperature; }
    public String getHolidayFlag() { return holidayFlag; }
    public void setHolidayFlag(String holidayFlag) { this.holidayFlag = holidayFlag; }
    public String getConfidence() { return confidence; }
    public void setConfidence(String confidence) { this.confidence = confidence; }
    public Double getMaxValue() { return maxValue; }
    public void setMaxValue(Double maxValue) { this.maxValue = maxValue; }
    public Double getMinValue() { return minValue; }
    public void setMinValue(Double minValue) { this.minValue = minValue; }
    public LocalDateTime getMaxTime() { return maxTime; }
    public void setMaxTime(LocalDateTime maxTime) { this.maxTime = maxTime; }
    public LocalDateTime getMinTime() { return minTime; }
    public void setMinTime(LocalDateTime minTime) { this.minTime = minTime; }
    public String getPeakType() { return peakType; }
    public void setPeakType(String peakType) { this.peakType = peakType; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
