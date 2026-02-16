package com.electric.service;

import com.electric.model.TransmissionLine;
import java.util.List;

public interface TransmissionLineService {
    List<TransmissionLine> getAllTransmissionLines();
    TransmissionLine getTransmissionLineById(Long id);
    TransmissionLine saveTransmissionLine(TransmissionLine transmissionLine);
    void deleteTransmissionLine(Long id);
}