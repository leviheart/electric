package com.electric.controller;

import com.electric.model.DispatchOrder;
import com.electric.service.DispatchOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dispatch-orders")
@CrossOrigin(origins = "*")
public class DispatchOrderController {
    
    @Autowired
    private DispatchOrderService dispatchOrderService;
    
    @GetMapping
    public List<DispatchOrder> getAll() { return dispatchOrderService.findAll(); }
    
    @GetMapping("/{id}")
    public DispatchOrder getById(@PathVariable Long id) { return dispatchOrderService.findById(id).orElse(null); }
    
    @GetMapping("/status/{status}")
    public List<DispatchOrder> getByStatus(@PathVariable String status) { return dispatchOrderService.findByStatus(status); }
    
    @GetMapping("/active")
    public List<DispatchOrder> getActive() { return dispatchOrderService.findActiveOrders(); }
    
    @PostMapping
    public DispatchOrder create(@RequestBody DispatchOrder order) { return dispatchOrderService.save(order); }
    
    @PutMapping("/{id}")
    public DispatchOrder update(@PathVariable Long id, @RequestBody DispatchOrder order) {
        order.setId(id);
        return dispatchOrderService.save(order);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { dispatchOrderService.deleteById(id); }
}
