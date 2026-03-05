package com.electric.controller;

import com.electric.model.WorkTicket;
import com.electric.service.WorkTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/work-tickets")
@CrossOrigin(origins = "*")
public class WorkTicketController {
    
    @Autowired
    private WorkTicketService workTicketService;
    
    @GetMapping
    public List<WorkTicket> getAll() {
        return workTicketService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<WorkTicket> getById(@PathVariable Long id) {
        return workTicketService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public List<WorkTicket> getByStatus(@PathVariable String status) {
        return workTicketService.findByStatus(status);
    }
    
    @GetMapping("/active")
    public List<WorkTicket> getActive() {
        return workTicketService.findActiveTickets();
    }
    
    @PostMapping
    public WorkTicket create(@RequestBody WorkTicket ticket) {
        return workTicketService.save(ticket);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<WorkTicket> update(@PathVariable Long id, @RequestBody WorkTicket ticket) {
        return workTicketService.findById(id)
            .map(existing -> {
                ticket.setId(id);
                return ResponseEntity.ok(workTicketService.save(ticket));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (workTicketService.findById(id).isPresent()) {
            workTicketService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/submit")
    public ResponseEntity<WorkTicket> submit(@PathVariable Long id) {
        WorkTicket ticket = workTicketService.submit(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/approve")
    public ResponseEntity<WorkTicket> approve(@PathVariable Long id, @RequestParam String approver) {
        WorkTicket ticket = workTicketService.approve(id, approver);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/issue")
    public ResponseEntity<WorkTicket> issue(@PathVariable Long id, @RequestParam String issuer) {
        WorkTicket ticket = workTicketService.issue(id, issuer);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/receive")
    public ResponseEntity<WorkTicket> receive(@PathVariable Long id, @RequestParam String receiver) {
        WorkTicket ticket = workTicketService.receive(id, receiver);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/permit")
    public ResponseEntity<WorkTicket> permit(@PathVariable Long id, @RequestParam String permissioner) {
        WorkTicket ticket = workTicketService.permit(id, permissioner);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/end")
    public ResponseEntity<WorkTicket> end(@PathVariable Long id, @RequestParam String ender) {
        WorkTicket ticket = workTicketService.end(id, ender);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
}
