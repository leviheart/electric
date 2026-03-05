package com.electric.service;

import com.electric.model.OperationTicket;
import com.electric.repository.OperationTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationTicketService {
    
    @Autowired
    private OperationTicketRepository operationTicketRepository;
    
    public List<OperationTicket> findAll() {
        return operationTicketRepository.findAll();
    }
    
    public Optional<OperationTicket> findById(Long id) {
        return operationTicketRepository.findById(id);
    }
    
    public OperationTicket save(OperationTicket ticket) {
        return operationTicketRepository.save(ticket);
    }
    
    public void deleteById(Long id) {
        operationTicketRepository.deleteById(id);
    }
    
    public List<OperationTicket> findByStatus(String status) {
        return operationTicketRepository.findByStatus(status);
    }
    
    public List<OperationTicket> findActiveTickets() {
        return operationTicketRepository.findActiveTickets();
    }
    
    public OperationTicket submit(Long id) {
        Optional<OperationTicket> opt = operationTicketRepository.findById(id);
        if (opt.isPresent()) {
            OperationTicket ticket = opt.get();
            ticket.setStatus("待审批");
            ticket.setApplyTime(LocalDateTime.now());
            return operationTicketRepository.save(ticket);
        }
        return null;
    }
    
    public OperationTicket approve(Long id, String approver) {
        Optional<OperationTicket> opt = operationTicketRepository.findById(id);
        if (opt.isPresent()) {
            OperationTicket ticket = opt.get();
            ticket.setStatus("已批准");
            ticket.setApprover(approver);
            ticket.setApproveTime(LocalDateTime.now());
            return operationTicketRepository.save(ticket);
        }
        return null;
    }
    
    public OperationTicket issue(Long id, String issuer) {
        Optional<OperationTicket> opt = operationTicketRepository.findById(id);
        if (opt.isPresent()) {
            OperationTicket ticket = opt.get();
            ticket.setStatus("已签发");
            ticket.setIssuer(issuer);
            ticket.setIssueTime(LocalDateTime.now());
            return operationTicketRepository.save(ticket);
        }
        return null;
    }
    
    public OperationTicket start(Long id) {
        Optional<OperationTicket> opt = operationTicketRepository.findById(id);
        if (opt.isPresent()) {
            OperationTicket ticket = opt.get();
            ticket.setStatus("执行中");
            ticket.setStartTime(LocalDateTime.now());
            return operationTicketRepository.save(ticket);
        }
        return null;
    }
    
    public OperationTicket complete(Long id, String result) {
        Optional<OperationTicket> opt = operationTicketRepository.findById(id);
        if (opt.isPresent()) {
            OperationTicket ticket = opt.get();
            ticket.setStatus("已完成");
            ticket.setEndTime(LocalDateTime.now());
            ticket.setResult(result);
            ticket.setCompletedSteps(ticket.getTotalSteps());
            return operationTicketRepository.save(ticket);
        }
        return null;
    }
    
    public Long countByStatus(String status) {
        return operationTicketRepository.countByStatus(status);
    }
}
