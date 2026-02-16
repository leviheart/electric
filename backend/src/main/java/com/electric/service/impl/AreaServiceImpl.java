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
 * 实现AreaService接口定义的方法，处理台区相关的业务逻辑
 * 
 * 业务逻辑：
 * 1. 获取所有台区列表
 * 2. 根据ID获取单个台区详情
 * 3. 保存或更新台区信息
 * 4. 删除台区
 * 
 * 文件关联：
 * - 服务接口：实现AreaService接口
 * - 数据访问：依赖AreaRepository接口进行数据操作
 * - 控制器：被AreaController调用，处理HTTP请求
 * - 实体类：使用Area实体类作为数据模型
 */
@Service
public class AreaServiceImpl implements AreaService {

    // 注入台区数据访问接口
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
        // 调用Repository的findAll方法获取所有台区
        return areaRepository.findAll();
    }

    /**
     * 根据ID获取单个台区详情
     * @param id 台区ID
     * @return 台区对象，如果不存在则返回null
     */
    @Override
    public Area getAreaById(Long id) {
        // 调用Repository的findById方法获取台区，使用orElse(null)处理不存在的情况
        return areaRepository.findById(id).orElse(null);
    }

    /**
     * 保存或更新台区信息
     * @param area 台区对象
     * @return 保存后的台区对象
     */
    @Override
    public Area saveArea(Area area) {
        // 调用Repository的save方法保存台区
        // 如果area对象包含id，则更新现有记录；否则创建新记录
        return areaRepository.save(area);
    }

    /**
     * 删除台区
     * @param id 台区ID
     */
    @Override
    public void deleteArea(Long id) {
        // 调用Repository的deleteById方法删除台区
        areaRepository.deleteById(id);
    }
}