package com.electric.service.impl;

import com.electric.model.TransmissionLine;
import com.electric.repository.TransmissionLineRepository;
import com.electric.service.TransmissionLineService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TransmissionLineServiceImpl - 输电线路服务实现类
 * 
 * 功能说明：
 * 实现TransmissionLineService接口定义的方法，处理输电线路相关的业务逻辑
 * 
 * 业务逻辑：
 * 1. 获取所有输电线路列表
 * 2. 根据ID获取单个输电线路详情
 * 3. 保存或更新输电线路信息
 * 4. 删除输电线路
 * 
 * 文件关联：
 * - 服务接口：实现TransmissionLineService接口
 * - 数据访问：依赖TransmissionLineRepository接口进行数据操作
 * - 控制器：被TransmissionLineController调用，处理HTTP请求
 * - 实体类：使用TransmissionLine实体类作为数据模型
 */
@Service
public class TransmissionLineServiceImpl implements TransmissionLineService {

    // 注入输电线路数据访问接口
    private final TransmissionLineRepository transmissionLineRepository;

    /**
     * 构造函数依赖注入
     * @param transmissionLineRepository 输电线路数据访问接口
     */
    public TransmissionLineServiceImpl(TransmissionLineRepository transmissionLineRepository) {
        this.transmissionLineRepository = transmissionLineRepository;
    }

    /**
     * 获取所有输电线路列表
     * @return 输电线路列表
     */
    @Override
    public List<TransmissionLine> getAllTransmissionLines() {
        // 调用Repository的findAll方法获取所有输电线路
        return transmissionLineRepository.findAll();
    }

    /**
     * 根据ID获取单个输电线路详情
     * @param id 输电线路ID
     * @return 输电线路对象，如果不存在则返回null
     */
    @Override
    public TransmissionLine getTransmissionLineById(Long id) {
        // 调用Repository的findById方法获取输电线路，使用orElse(null)处理不存在的情况
        return transmissionLineRepository.findById(id).orElse(null);
    }

    /**
     * 保存或更新输电线路信息
     * @param transmissionLine 输电线路对象
     * @return 保存后的输电线路对象
     */
    @Override
    public TransmissionLine saveTransmissionLine(TransmissionLine transmissionLine) {
        // 调用Repository的save方法保存输电线路
        // 如果transmissionLine对象包含id，则更新现有记录；否则创建新记录
        return transmissionLineRepository.save(transmissionLine);
    }

    /**
     * 删除输电线路
     * @param id 输电线路ID
     */
    @Override
    public void deleteTransmissionLine(Long id) {
        // 调用Repository的deleteById方法删除输电线路
        transmissionLineRepository.deleteById(id);
    }
}