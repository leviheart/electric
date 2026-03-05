package com.electric.controller;

import com.electric.model.LineLoss;
import com.electric.service.LineLossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/line-losses")
@CrossOrigin(origins = "*")
public class LineLossController {
    
    @Autowired
    private LineLossService lineLossService;
    
    @GetMapping
    public List<LineLoss> getAll() { return lineLossService.findAll(); }
    
    @GetMapping("/{id}")
    public LineLoss getById(@PathVariable Long id) { return lineLossService.findById(id).orElse(null); }
    
    @GetMapping("/line/{lineId}")
    public List<LineLoss> getByLineId(@PathVariable Long lineId) { return lineLossService.findByLineId(lineId); }
    
    @GetMapping("/high-loss")
    public List<LineLoss> getHighLoss(@RequestParam(defaultValue = "5.0") double threshold) {
        return lineLossService.findHighLossLines(threshold);
    }
}
