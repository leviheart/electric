package com.electric.service;

import com.electric.model.Alert;
import com.electric.model.Alert.AlertType;
import com.electric.model.Alert.AlertLevel;
import com.electric.model.Alert.AlertStatus;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import com.electric.model.Area;
import com.electric.repository.SubstationRepository;
import com.electric.repository.TransmissionLineRepository;
import com.electric.repository.AreaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class AlertDetectionService {
    
    private final AlertService alertService;
    private final SubstationRepository substationRepository;
    private final TransmissionLineRepository transmissionLineRepository;
    private final AreaRepository areaRepository;
    private final Random random = new Random();
    
    private static final int MAX_ALERTS = 250;
    private static final int MIN_ALERTS = 200;
    
    public AlertDetectionService(
            AlertService alertService,
            SubstationRepository substationRepository,
            TransmissionLineRepository transmissionLineRepository,
            AreaRepository areaRepository) {
        this.alertService = alertService;
        this.substationRepository = substationRepository;
        this.transmissionLineRepository = transmissionLineRepository;
        this.areaRepository = areaRepository;
    }
    
    @Scheduled(fixedRate = 60000)
    public void detectAlerts() {
        long currentCount = alertService.countByStatus(AlertStatus.ACTIVE);
        
        if (currentCount >= MAX_ALERTS) {
            return;
        }
        
        int alertsToGenerate = (int) Math.max(0, MIN_ALERTS - currentCount);
        if (alertsToGenerate > 0) {
            alertsToGenerate = Math.min(alertsToGenerate, 5);
        } else if (currentCount < MAX_ALERTS) {
            alertsToGenerate = random.nextInt(3);
        }
        
        for (int i = 0; i < alertsToGenerate; i++) {
            generateRandomAlert();
        }
    }
    
    private void generateRandomAlert() {
        int type = random.nextInt(3);
        switch (type) {
            case 0:
                generateSubstationAlert();
                break;
            case 1:
                generateLineAlert();
                break;
            case 2:
                generateAreaAlert();
                break;
        }
    }
    
    private void generateSubstationAlert() {
        List<Substation> substations = substationRepository.findAll();
        if (substations.isEmpty()) return;
        
        Substation substation = substations.get(random.nextInt(substations.size()));
        int alertType = random.nextInt(3);
        
        Alert alert = null;
        switch (alertType) {
            case 0:
                alert = Alert.deviceOffline(
                    "substation",
                    substation.getId(),
                    substation.getName(),
                    substation.getLatitude(),
                    substation.getLongitude()
                );
                alert.setLevel(random.nextBoolean() ? AlertLevel.CRITICAL : AlertLevel.HIGH);
                break;
            case 1:
                double voltage = 180 + random.nextDouble() * 40;
                alert = Alert.voltageAbnormal(
                    "substation",
                    substation.getId(),
                    substation.getName(),
                    voltage,
                    substation.getLatitude(),
                    substation.getLongitude()
                );
                alert.setLevel(AlertLevel.HIGH);
                break;
            case 2:
                double temperature = 65 + random.nextDouble() * 20;
                alert = Alert.temperatureHigh(
                    "substation",
                    substation.getId(),
                    substation.getName(),
                    temperature,
                    substation.getLatitude(),
                    substation.getLongitude()
                );
                alert.setLevel(AlertLevel.MEDIUM);
                break;
        }
        
        if (alert != null) {
            alertService.createAlert(alert);
        }
    }
    
    private void generateLineAlert() {
        List<TransmissionLine> lines = transmissionLineRepository.findAll();
        if (lines.isEmpty()) return;
        
        TransmissionLine line = lines.get(random.nextInt(lines.size()));
        double[] centerCoords = getLineCenterCoords(line);
        
        Alert alert;
        if (random.nextBoolean()) {
            double loadRate = 85 + random.nextDouble() * 15;
            alert = Alert.overload(
                "line",
                line.getId(),
                line.getName(),
                loadRate,
                centerCoords[0],
                centerCoords[1]
            );
            alert.setLevel(loadRate > 95 ? AlertLevel.CRITICAL : AlertLevel.HIGH);
        } else {
            alert = Alert.fault(
                "line",
                line.getId(),
                line.getName(),
                "线路跳闸告警",
                centerCoords[0],
                centerCoords[1]
            );
            alert.setLevel(AlertLevel.CRITICAL);
        }
        
        alertService.createAlert(alert);
    }
    
    private void generateAreaAlert() {
        List<Area> areas = areaRepository.findAll();
        if (areas.isEmpty()) return;
        
        Area area = areas.get(random.nextInt(areas.size()));
        double[] centerCoords = getAreaCenterCoords(area);
        
        Alert alert;
        if (random.nextBoolean()) {
            double loadRate = 90 + random.nextDouble() * 10;
            alert = Alert.overload(
                "area",
                area.getId(),
                area.getName(),
                loadRate,
                centerCoords[0],
                centerCoords[1]
            );
            alert.setLevel(AlertLevel.HIGH);
        } else {
            alert = Alert.maintenance(
                "area",
                area.getId(),
                area.getName(),
                "计划检修提醒",
                centerCoords[0],
                centerCoords[1]
            );
            alert.setLevel(AlertLevel.LOW);
        }
        
        alertService.createAlert(alert);
    }
    
    private double[] getLineCenterCoords(TransmissionLine line) {
        try {
            String geometry = line.getGeometry();
            if (geometry != null && !geometry.isEmpty()) {
                String coords = geometry.replace("[[", "").replace("]]", "").replace("],[", ";");
                String[] points = coords.split(";");
                if (points.length > 0) {
                    int midIndex = points.length / 2;
                    String[] latLng = points[midIndex].replace("[", "").replace("]", "").split(",");
                    if (latLng.length >= 2) {
                        return new double[] {
                            Double.parseDouble(latLng[0].trim()),
                            Double.parseDouble(latLng[1].trim())
                        };
                    }
                }
            }
        } catch (Exception e) {
        }
        return new double[] { 39.9, 116.4 };
    }
    
    private double[] getAreaCenterCoords(Area area) {
        if (area.getTransformerLocation() != null) {
            try {
                String loc = area.getTransformerLocation();
                String latStr = loc.replaceAll(".*\"lat\":\\s*([0-9.]+).*", "$1");
                String lngStr = loc.replaceAll(".*\"lng\":\\s*([0-9.]+).*", "$1");
                return new double[] {
                    Double.parseDouble(latStr),
                    Double.parseDouble(lngStr)
                };
            } catch (Exception e) {
            }
        }
        
        try {
            String geometry = area.getGeometry();
            if (geometry != null && !geometry.isEmpty()) {
                String coords = geometry.replace("[[", "").replace("]]", "").replace("],[", ";");
                String[] points = coords.split(";");
                double sumLat = 0, sumLng = 0;
                for (String point : points) {
                    String[] latLng = point.replace("[", "").replace("]", "").split(",");
                    if (latLng.length >= 2) {
                        sumLat += Double.parseDouble(latLng[0].trim());
                        sumLng += Double.parseDouble(latLng[1].trim());
                    }
                }
                return new double[] { sumLat / points.length, sumLng / points.length };
            }
        } catch (Exception e) {
        }
        return new double[] { 39.9, 116.4 };
    }
}
