package com.electric.repository;

import com.electric.model.Substation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubstationRepository extends JpaRepository<Substation, Long> {
}