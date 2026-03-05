package com.electric.service;

import com.electric.model.VideoMonitor;
import com.electric.repository.VideoMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VideoMonitorService {
    
    @Autowired
    private VideoMonitorRepository videoMonitorRepository;
    
    public List<VideoMonitor> findAll() {
        return videoMonitorRepository.findAll();
    }
    
    public Optional<VideoMonitor> findById(Long id) {
        return videoMonitorRepository.findById(id);
    }
    
    public VideoMonitor save(VideoMonitor monitor) {
        return videoMonitorRepository.save(monitor);
    }
    
    public void deleteById(Long id) {
        videoMonitorRepository.deleteById(id);
    }
    
    public List<VideoMonitor> findByStatus(String status) {
        return videoMonitorRepository.findByStatus(status);
    }
    
    public List<VideoMonitor> findByDevice(String deviceType, Long deviceId) {
        return videoMonitorRepository.findByDevice(deviceType, deviceId);
    }
    
    public Long countOnline() {
        return videoMonitorRepository.countOnline();
    }
    
    public Long countOffline() {
        return videoMonitorRepository.countOffline();
    }
    
    public VideoMonitor updateStatus(Long id, String status) {
        Optional<VideoMonitor> opt = videoMonitorRepository.findById(id);
        if (opt.isPresent()) {
            VideoMonitor monitor = opt.get();
            monitor.setStatus(status);
            if ("在线".equals(status)) {
                monitor.setLastOnlineTime(java.time.LocalDateTime.now());
            }
            return videoMonitorRepository.save(monitor);
        }
        return null;
    }
}
