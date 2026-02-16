package com.electric.service;

import com.electric.model.Area;
import java.util.List;

public interface AreaService {
    List<Area> getAllAreas();
    Area getAreaById(Long id);
    Area saveArea(Area area);
    void deleteArea(Long id);
}