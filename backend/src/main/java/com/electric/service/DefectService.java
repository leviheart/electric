package com.electric.service;

import com.electric.model.Defect;
import com.electric.repository.DefectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefectService {
    
    @Autowired
    private DefectRepository defectRepository;
    
    public List<Defect> findAll() {
        return defectRepository.findAll();
    }
    
    public Optional<Defect> findById(Long id) {
        return defectRepository.findById(id);
    }
    
    public Defect save(Defect defect) {
        return defectRepository.save(defect);
    }
    
    public void deleteById(Long id) {
        defectRepository.deleteById(id);
    }
    
    public List<Defect> findByStatus(String status) {
        return defectRepository.findByStatus(status);
    }
    
    public List<Defect> findActiveDefects() {
        return defectRepository.findActiveDefects();
    }
    
    public Defect handle(Long id, String handler, String solution) {
        Optional<Defect> opt = defectRepository.findById(id);
        if (opt.isPresent()) {
            Defect defect = opt.get();
            defect.setStatus("处理中");
            defect.setHandler(handler);
            defect.setHandleTime(LocalDateTime.now());
            defect.setSolution(solution);
            return defectRepository.save(defect);
        }
        return null;
    }
    
    public Defect complete(Long id, String handlerComment) {
        Optional<Defect> opt = defectRepository.findById(id);
        if (opt.isPresent()) {
            Defect defect = opt.get();
            defect.setStatus("待验证");
            defect.setCompleteTime(LocalDateTime.now());
            defect.setHandlerComment(handlerComment);
            return defectRepository.save(defect);
        }
        return null;
    }
    
    public Defect verify(Long id, String verifier, String verifyResult, String verifyComment) {
        Optional<Defect> opt = defectRepository.findById(id);
        if (opt.isPresent()) {
            Defect defect = opt.get();
            defect.setStatus("已验证".equals(verifyResult) ? "已闭环" : "处理中");
            defect.setVerifier(verifier);
            defect.setVerifyTime(LocalDateTime.now());
            defect.setVerifyResult(verifyResult);
            defect.setVerifyComment(verifyComment);
            return defectRepository.save(defect);
        }
        return null;
    }
    
    public Long countByStatus(String status) {
        return defectRepository.countByStatus(status);
    }
    
    public Long countActiveByLevel(String level) {
        return defectRepository.countActiveByLevel(level);
    }
}
