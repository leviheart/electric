package com.electric.service;

import com.electric.model.OperationData;
import com.electric.repository.OperationDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperationDataService {

    private final OperationDataRepository operationDataRepository;

    public OperationDataService(OperationDataRepository operationDataRepository) {
        this.operationDataRepository = operationDataRepository;
    }

    public List<OperationData> getAllOperationData() {
        return operationDataRepository.findAll();
    }

    public Optional<OperationData> getOperationDataById(Long id) {
        return operationDataRepository.findById(id);
    }

    public List<OperationData> getOperationDataByEntity(String entityType, Long entityId) {
        return operationDataRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    public List<OperationData> getOperationDataByEntityAndTimeRange(
            String entityType, Long entityId, LocalDateTime start, LocalDateTime end) {
        return operationDataRepository.findByEntityTypeAndEntityIdAndRecordTimeBetween(
                entityType, entityId, start, end);
    }

    public List<OperationData> getOperationDataByEntityAndDate(
            String entityType, Long entityId, LocalDate date) {
        return operationDataRepository.findByEntityTypeAndEntityIdAndDate(entityType, entityId, date);
    }

    public Optional<OperationData> getLatestOperationData(String entityType, Long entityId) {
        OperationData data = operationDataRepository.findLatestByEntityTypeAndEntityId(entityType, entityId);
        return Optional.ofNullable(data);
    }

    public List<LocalDate> getAvailableDates(String entityType, Long entityId) {
        return operationDataRepository.findDistinctDatesByEntityTypeAndEntityId(entityType, entityId);
    }

    @Transactional
    public OperationData saveOperationData(OperationData operationData) {
        return operationDataRepository.save(operationData);
    }

    @Transactional
    public void deleteOperationData(Long id) {
        operationDataRepository.deleteById(id);
    }

    public long count() {
        return operationDataRepository.count();
    }
}
