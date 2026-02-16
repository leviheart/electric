package com.electric.service;

import com.electric.model.Substation;
import java.util.List;

public interface SubstationService {
    List<Substation> getAllSubstations();
    Substation getSubstationById(Long id);
    Substation saveSubstation(Substation substation);
    void deleteSubstation(Long id);
}