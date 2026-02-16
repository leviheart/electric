package com.electric.service.impl;

import com.electric.model.Area;
import com.electric.repository.AreaRepository;
import com.electric.service.AreaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AreaServiceImpl - 台区服务实现类
 * 
 * 功能说明：
 * 实现 AreaService 接口定义的方法，处理台区相关的业务逻辑
 * 
 * 业务逻辑：
 * 1. 获取所有台区列表
 * 2. 根据ID获取单个台区详情
 * 3. 保存或更新台区信息
 * 4. 删除台区
 * 
 * 文件关联：
 * - 服务接口：AreaService.java
 * - 数据访问：AreaRepository
 * - 控制器：AreaController
 * - 实体类：Area
 * 
 * 新人提示：
 * - 台区是配电变压器的供电范围
 * - 台区数据包含多边形边界坐标，用于地图展示
 */
@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    /**
     * 构造函数依赖注入
     * @param areaRepository 台区数据访问接口
     */
    public AreaServiceImpl(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    /**
     * 获取所有台区列表
     * @return 台区列表
     */
    @Override
    public List<Area> getAllAreas() {
        return areaRepository.findAll();
    }

    /**
     * 根据ID获取单个台区详情
     * @param id 台区ID
     * @return 台区对象，如果不存在则返回null
     */
    @Override
    public Area getAreaById(Long id) {
        return areaRepository.findById(id).orElse(null);
    }

    /**
     * 保存或更新台区信息
     * @param area 台区对象
     * @return 保存后的台区对象
     */
    @Override
    public Area saveArea(Area area) {
        return areaRepository.save(area);
    }

    /**
     * 删除台区
     * @param id 台区ID
     */
    @Override
    public void deleteArea(Long id) {
        areaRepository.deleteById(id);
    }
}
