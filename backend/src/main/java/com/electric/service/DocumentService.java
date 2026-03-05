package com.electric.service;

import com.electric.model.Document;
import com.electric.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    
    @Autowired
    private DocumentRepository documentRepository;
    
    public List<Document> findAll() {
        return documentRepository.findAll();
    }
    
    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }
    
    public Document save(Document document) {
        return documentRepository.save(document);
    }
    
    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }
    
    public List<Document> findByDocType(String docType) {
        return documentRepository.findByDocType(docType);
    }
    
    public List<Document> findByDevice(String deviceType, Long deviceId) {
        return documentRepository.findByDeviceTypeAndDeviceId(deviceType, deviceId);
    }
    
    public List<Document> searchByKeyword(String keyword) {
        return documentRepository.searchByKeyword(keyword);
    }
    
    public List<Object[]> countByDocType() {
        return documentRepository.countByDocType();
    }
    
    public List<Document> findPopularDocuments() {
        return documentRepository.findPopularDocuments();
    }
    
    public Document upload(Document document) {
        document.setUploadTime(LocalDateTime.now());
        document.setCreateTime(LocalDateTime.now());
        document.setUpdateTime(LocalDateTime.now());
        document.setDownloadCount(0);
        document.setStatus("已发布");
        return documentRepository.save(document);
    }
    
    public Document incrementDownloadCount(Long id) {
        Optional<Document> opt = documentRepository.findById(id);
        if (opt.isPresent()) {
            Document doc = opt.get();
            doc.setDownloadCount(doc.getDownloadCount() + 1);
            return documentRepository.save(doc);
        }
        return null;
    }
}
