package com.electric.repository;

import com.electric.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByDocType(String docType);
    List<Document> findByCategory(String category);
    List<Document> findByDeviceTypeAndDeviceId(String deviceType, Long deviceId);
    List<Document> findByStatus(String status);
    List<Document> findByUploader(String uploader);
    List<Document> findByKeywordsContaining(String keyword);
    
    @Query("SELECT d FROM Document d WHERE d.title LIKE %:keyword% OR d.keywords LIKE %:keyword% OR d.description LIKE %:keyword%")
    List<Document> searchByKeyword(String keyword);
    
    @Query("SELECT d.docType, COUNT(d) FROM Document d GROUP BY d.docType")
    List<Object[]> countByDocType();
    
    @Query("SELECT d FROM Document d ORDER BY d.downloadCount DESC")
    List<Document> findPopularDocuments();
}
