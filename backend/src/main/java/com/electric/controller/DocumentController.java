package com.electric.controller;

import com.electric.model.Document;
import com.electric.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {
    
    @Autowired
    private DocumentService documentService;
    
    @GetMapping
    public List<Document> getAll() { return documentService.findAll(); }
    
    @GetMapping("/{id}")
    public Document getById(@PathVariable Long id) { return documentService.findById(id).orElse(null); }
    
    @GetMapping("/type/{docType}")
    public List<Document> getByType(@PathVariable String docType) { return documentService.findByDocType(docType); }
    
    @GetMapping("/search")
    public List<Document> search(@RequestParam String keyword) { return documentService.searchByKeyword(keyword); }
    
    @PostMapping
    public Document create(@RequestBody Document doc) { return documentService.upload(doc); }
    
    @PutMapping("/{id}")
    public Document update(@PathVariable Long id, @RequestBody Document doc) {
        doc.setId(id);
        return documentService.upload(doc);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { documentService.deleteById(id); }
}
