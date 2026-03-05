package com.electric.service;

import com.electric.model.DispatchOrder;
import com.electric.repository.DispatchOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DispatchOrderService {
    
    @Autowired
    private DispatchOrderRepository dispatchOrderRepository;
    
    public List<DispatchOrder> findAll() {
        return dispatchOrderRepository.findAll();
    }
    
    public Optional<DispatchOrder> findById(Long id) {
        return dispatchOrderRepository.findById(id);
    }
    
    public DispatchOrder save(DispatchOrder order) {
        return dispatchOrderRepository.save(order);
    }
    
    public void deleteById(Long id) {
        dispatchOrderRepository.deleteById(id);
    }
    
    public List<DispatchOrder> findByStatus(String status) {
        return dispatchOrderRepository.findByStatus(status);
    }
    
    public List<DispatchOrder> findActiveOrders() {
        return dispatchOrderRepository.findActiveOrders();
    }
    
    public DispatchOrder issue(Long id, String issuer, String issuerDept) {
        Optional<DispatchOrder> opt = dispatchOrderRepository.findById(id);
        if (opt.isPresent()) {
            DispatchOrder order = opt.get();
            order.setStatus("待接收");
            order.setIssuer(issuer);
            order.setIssuerDept(issuerDept);
            order.setIssueTime(LocalDateTime.now());
            return dispatchOrderRepository.save(order);
        }
        return null;
    }
    
    public DispatchOrder receive(Long id, String receiver, String receiverDept) {
        Optional<DispatchOrder> opt = dispatchOrderRepository.findById(id);
        if (opt.isPresent()) {
            DispatchOrder order = opt.get();
            order.setStatus("已接收");
            order.setReceiver(receiver);
            order.setReceiverDept(receiverDept);
            order.setReceiveTime(LocalDateTime.now());
            return dispatchOrderRepository.save(order);
        }
        return null;
    }
    
    public DispatchOrder execute(Long id, String executor) {
        Optional<DispatchOrder> opt = dispatchOrderRepository.findById(id);
        if (opt.isPresent()) {
            DispatchOrder order = opt.get();
            order.setStatus("执行中");
            order.setExecutor(executor);
            order.setExecuteTime(LocalDateTime.now());
            return dispatchOrderRepository.save(order);
        }
        return null;
    }
    
    public DispatchOrder complete(Long id, String executeResult, String feedback) {
        Optional<DispatchOrder> opt = dispatchOrderRepository.findById(id);
        if (opt.isPresent()) {
            DispatchOrder order = opt.get();
            order.setStatus("已完成");
            order.setCompleteTime(LocalDateTime.now());
            order.setExecuteResult(executeResult);
            order.setFeedback(feedback);
            order.setFeedbackTime(LocalDateTime.now());
            return dispatchOrderRepository.save(order);
        }
        return null;
    }
    
    public Long countByStatus(String status) {
        return dispatchOrderRepository.countByStatus(status);
    }
}
