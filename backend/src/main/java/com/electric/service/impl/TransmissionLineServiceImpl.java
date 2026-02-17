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
 * 实现 TransmissionLineService 接口定义的方法，处理输电线路相关的业务逻辑
 * 
 * 业务逻辑：
 * 1. 获取所有输电线路列表
 * 2. 根据ID获取单个输电线路详情
 * 3. 保存或更新输电线路信息
 * 4. 删除输电线路
 * 
 * 文件关联：
 * - 服务接口：TransmissionLineService.java
 * - 数据访问：TransmissionLineRepository
 * - 控制器：TransmissionLineController
 * - 实体类：TransmissionLine
 * 
 * 新人提示：
 * - @Service 注解标记这是一个服务层组件
 * - 构造函数注入是推荐的依赖注入方式
 */
@Service
public class TransmissionLineServiceImpl implements TransmissionLineService {

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
        return transmissionLineRepository.findAll();
    }

    /**
     * 根据ID获取单个输电线路详情
     * @param id 输电线路ID
     * @return 输电线路对象，如果不存在则返回null
     */
    @Override
    public TransmissionLine getTransmissionLineById(Long id) {
        return transmissionLineRepository.findById(id).orElse(null);
    }

    /**
     * 保存或更新输电线路信息
     * @param line 输电线路对象
     * @return 保存后的输电线路对象
     */
    @Override
    public TransmissionLine saveTransmissionLine(TransmissionLine line) {
        return transmissionLineRepository.save(line);
    }

    /**
     * 删除输电线路
     * @param id 输电线路ID
     */
    @Override
    public void deleteTransmissionLine(Long id) {
        if (!transmissionLineRepository.existsById(id)) {
            throw BusinessException.notFound("输电线路", id);
        }
        transmissionLineRepository.deleteById(id);
    }
}
