package com.electric.service;

import com.electric.model.N1Check;
import com.electric.repository.N1CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class N1CheckService {
    
    @Autowired
    private N1CheckRepository n1CheckRepository;
    
    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
    
    public List<N1Check> findAll() {
        return n1CheckRepository.findAll();
    }
    
    public Optional<N1Check> findById(Long id) {
        return n1CheckRepository.findById(id);
    }
    
    public N1Check save(N1Check n1Check) {
        return n1CheckRepository.save(n1Check);
    }
    
    public void deleteById(Long id) {
        n1CheckRepository.deleteById(id);
    }
    
    public N1Check findLatestCompleted() {
        return n1CheckRepository.findLatestCompleted();
    }
    
    public N1Check performCheck(String checkName, String checkType, String checkItems) {
        N1Check check = new N1Check();
        check.setCheckCode("N1" + System.currentTimeMillis());
        check.setCheckName(checkName);
        check.setCheckTime(LocalDateTime.now());
        check.setCheckType(checkType);
        check.setCheckItems(checkItems);
        check.setStatus("校验中");
        
        N1Check saved = n1CheckRepository.save(check);
        
        simulateCheck(saved);
        
        return n1CheckRepository.save(saved);
    }
    
    private void simulateCheck(N1Check check) {
        Random random = new Random();
        
        check.setStatus("已完成");
        check.setTotalItems(20 + random.nextInt(30));
        check.setPassedItems(check.getTotalItems() - random.nextInt(5));
        check.setFailedItems(check.getTotalItems() - check.getPassedItems());
        
        if (check.getFailedItems() > 0) {
            check.setFailedDetails("线路" + (1 + random.nextInt(5)) + "N-1后过载");
            check.setOverloadLines("线路" + (1 + random.nextInt(3)));
            check.setOverloadTransformers("变压器" + (1 + random.nextInt(2)));
            check.setMaxOverloadRate(round2(100.0 + random.nextDouble() * 30));
            check.setMaxOverloadDevice("线路" + (1 + random.nextInt(5)));
            
            if (check.getMaxOverloadRate() > 120) {
                check.setRiskLevel("高风险");
                check.setRiskAssessment("存在严重N-1越限，需立即处理");
                check.setSuggestion("建议进行电网改造或负荷转移");
            } else {
                check.setRiskLevel("中风险");
                check.setRiskAssessment("存在N-1越限，需关注");
                check.setSuggestion("建议优化运行方式");
            }
        } else {
            check.setRiskLevel("低风险");
            check.setRiskAssessment("N-1校验全部通过");
            check.setSuggestion("系统满足N-1安全准则");
        }
    }
    
    public Long countByRiskLevel(String riskLevel) {
        return n1CheckRepository.countByRiskLevel(riskLevel);
    }
}
