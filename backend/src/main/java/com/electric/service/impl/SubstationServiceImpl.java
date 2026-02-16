package com.electric.service.impl;

import com.electric.model.Substation;
import com.electric.repository.SubstationRepository;
import com.electric.service.SubstationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SubstationServiceImpl - 变电站服务实现类
 * 
 * 功能说明：
 * 实现SubstationService接口定义的方法，处理变电站相关的业务逻辑
 * 
 * 业务逻辑：
 * 1. 获取所有变电站列表
 * 2. 根据ID获取单个变电站详情
 * 3. 保存或更新变电站信息
 * 4. 删除变电站
 * 
 * 文件关联：
 * - 服务接口：实现SubstationService接口
 * - 数据访问：依赖SubstationRepository接口进行数据操作
 * - 控制器：被SubstationController调用，处理HTTP请求
 * - 实体类：使用Substation实体类作为数据模型
 */
@Service
public class SubstationServiceImpl implements SubstationService {

    // 注入变电站数据访问接口
    private final SubstationRepository substationRepository;

    /**
     * 构造函数依赖注入
     * @param substationRepository 变电站数据访问接口
     */
    public SubstationServiceImpl(SubstationRepository substationRepository) {
        this.substationRepository = substationRepository;
    }

    /**
     * 获取所有变电站列表
     * @return 变电站列表
     */
    @Override
    public List<Substation> getAllSubstations() {
        // 调用Repository的findAll方法获取所有变电站
        return substationRepository.findAll();
    }

    /**
     * 根据ID获取单个变电站详情
     * @param id 变电站ID
     * @return 变电站对象，如果不存在则返回null
     */
    @Override
    public Substation getSubstationById(Long id) {
        // 调用Repository的findById方法获取变电站，使用orElse(null)处理不存在的情况
        return substationRepository.findById(id).orElse(null);
    }

    /**
     * 保存或更新变电站信息
     * @param substation 变电站对象
     * @return 保存后的变电站对象
     */
    @Override
    public Substation saveSubstation(Substation substation) {
        // 调用Repository的save方法保存变电站
        // 如果substation对象包含id，则更新现有记录；否则创建新记录
        return substationRepository.save(substation);
    }

    /**
     * 删除变电站
     * @param id 变电站ID
     */
    @Override
    public void deleteSubstation(Long id) {
        // 调用Repository的deleteById方法删除变电站
        substationRepository.deleteById(id);
    }
}