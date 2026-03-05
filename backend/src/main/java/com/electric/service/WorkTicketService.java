package com.electric.service;

import com.electric.model.WorkTicket;
import com.electric.repository.WorkTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkTicketService {
    
    @Autowired
    private WorkTicketRepository workTicketRepository;
    
    public List<WorkTicket> findAll() {
        return workTicketRepository.findAll();
    }
    
    public Optional<WorkTicket> findById(Long id) {
        return workTicketRepository.findById(id);
    }
    
    public WorkTicket save(WorkTicket ticket) {
        return workTicketRepository.save(ticket);
    }
    
    public void deleteById(Long id) {
        workTicketRepository.deleteById(id);
    }
    
    public List<WorkTicket> findByStatus(String status) {
        return workTicketRepository.findByStatus(status);
    }
    
    public List<WorkTicket> findActiveTickets() {
        return workTicketRepository.findActiveTickets();
    }
    
    public WorkTicket submit(Long id) {
        Optional<WorkTicket> opt = workTicketRepository.findById(id);
        if (opt.isPresent()) {
            WorkTicket ticket = opt.get();
            ticket.setStatus("待审批");
            ticket.setApplyTime(LocalDateTime.now());
            return workTicketRepository.save(ticket);
        }
        return null;
    }
    
    public WorkTicket approve(Long id, String approver) {
        Optional<WorkTicket> opt = workTicketRepository.findById(id);
        if (opt.isPresent()) {
            WorkTicket ticket = opt.get();
            ticket.setStatus("已批准");
            ticket.setApprover(approver);
            ticket.setApproveTime(LocalDateTime.now());
            return workTicketRepository.save(ticket);
        }
        return null;
    }
    
    public WorkTicket issue(Long id, String issuer) {
        Optional<WorkTicket> opt = workTicketRepository.findById(id);
        if (opt.isPresent()) {
            WorkTicket ticket = opt.get();
            ticket.setStatus("已签发");
            ticket.setIssuer(issuer);
            ticket.setIssueTime(LocalDateTime.now());
            return workTicketRepository.save(ticket);
        }
        return null;
    }
    
    public WorkTicket receive(Long id, String receiver) {
        Optional<WorkTicket> opt = workTicketRepository.findById(id);
        if (opt.isPresent()) {
            WorkTicket ticket = opt.get();
            ticket.setStatus("已接收");
            ticket.setReceiver(receiver);
            ticket.setReceiveTime(LocalDateTime.now());
            return workTicketRepository.save(ticket);
        }
        return null;
    }
    
    public WorkTicket permit(Long id, String permissioner) {
        Optional<WorkTicket> opt = workTicketRepository.findById(id);
        if (opt.isPresent()) {
            WorkTicket ticket = opt.get();
            ticket.setStatus("许可中");
            ticket.setWorkPermissioner(permissioner);
            ticket.setPermitTime(LocalDateTime.now());
            ticket.setActualStartTime(LocalDateTime.now());
            return workTicketRepository.save(ticket);
        }
        return null;
    }
    
    public WorkTicket end(Long id, String ender) {
        Optional<WorkTicket> opt = workTicketRepository.findById(id);
        if (opt.isPresent()) {
            WorkTicket ticket = opt.get();
            ticket.setStatus("已终结");
            ticket.setWorkEnder(ender);
            ticket.setEndTime(LocalDateTime.now());
            ticket.setActualEndTime(LocalDateTime.now());
            return workTicketRepository.save(ticket);
        }
        return null;
    }
    
    public Long countByStatus(String status) {
        return workTicketRepository.countByStatus(status);
    }
}
