package com.electric.repository;

import com.electric.model.TransmissionLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissionLineRepository extends JpaRepository<TransmissionLine, Long> {
}