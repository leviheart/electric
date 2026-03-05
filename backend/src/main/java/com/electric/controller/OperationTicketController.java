package com.electric.controller;

import com.electric.model.OperationTicket;
import com.electric.service.OperationTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/operation-tickets")
@CrossOrigin(origins = "*")
public class OperationTicketController {
    
    @Autowired
    private OperationTicketService operationTicketService;
    
    @GetMapping
    public List<OperationTicket> getAll() { return operationTicketService.findAll(); }
    
    @GetMapping("/{id}")
    public ResponseEntity<OperationTicket> getById(@PathVariable Long id) {
        return operationTicketService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public List<OperationTicket> getByStatus(@PathVariable String status) { return operationTicketService.findByStatus(status); }
    
    @GetMapping("/active")
    public List<OperationTicket> getActive() { return operationTicketService.findActiveTickets(); }
    
    @PostMapping
    public OperationTicket create(@RequestBody OperationTicket ticket) { return operationTicketService.save(ticket); }
    
    @PutMapping("/{id}")
    public ResponseEntity<OperationTicket> update(@PathVariable Long id, @RequestBody OperationTicket ticket) {
        return operationTicketService.findById(id).map(existing -> { ticket.setId(id); return ResponseEntity.ok(operationTicketService.save(ticket)); }).orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (operationTicketService.findById(id).isPresent()) { operationTicketService.deleteById(id); return ResponseEntity.ok().build(); }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/submit")
    public ResponseEntity<OperationTicket> submit(@PathVariable Long id) {
        OperationTicket ticket = operationTicketService.submit(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/approve")
    public ResponseEntity<OperationTicket> approve(@PathVariable Long id, @RequestParam String approver) {
        OperationTicket ticket = operationTicketService.approve(id, approver);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/issue")
    public ResponseEntity<OperationTicket> issue(@PathVariable Long id, @RequestParam String issuer) {
        OperationTicket ticket = operationTicketService.issue(id, issuer);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/start")
    public ResponseEntity<OperationTicket> start(@PathVariable Long id) {
        OperationTicket ticket = operationTicketService.start(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/complete")
    public ResponseEntity<OperationTicket> complete(@PathVariable Long id, @RequestParam String result) {
        OperationTicket ticket = operationTicketService.complete(id, result);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }
}
