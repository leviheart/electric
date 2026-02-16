package com.electric.service;

import com.electric.model.TransmissionLine;
import java.util.List;

/**
 * TransmissionLineService - 输电线路服务接口
 * 
 * 功能说明：
 * 定义输电线路相关的业务操作接口
 * 
 * 业务方法说明：
 * - getAllTransmissionLines(): 获取所有输电线路列表，用于地图展示
 * - getTransmissionLineById(Long id): 根据ID获取单个输电线路详情
 * - saveTransmissionLine(TransmissionLine line): 保存或更新输电线路信息
 * - deleteTransmissionLine(Long id): 根据ID删除输电线路
 * 
 * 文件关联：
 * - 实现类：TransmissionLineServiceImpl.java - 具体业务逻辑实现
 * - 实体类：TransmissionLine.java - 输电线路数据模型
 * - 控制器：TransmissionLineController.java - 调用此接口处理HTTP请求
 * 
 * 新人提示：
 * - 输电线路数据包含几何坐标(geometry)，用于在地图上绘制线路
 * - geometry 字段存储 JSON 格式的坐标数组
 */
public interface TransmissionLineService {
    
    /**
     * 获取所有输电线路列表
     * @return 输电线路列表
     */
    List<TransmissionLine> getAllTransmissionLines();
    
    /**
     * 根据ID获取单个输电线路详情
     * @param id 输电线路ID
     * @return 输电线路对象，如果不存在则返回null
     */
    TransmissionLine getTransmissionLineById(Long id);
    
    /**
     * 保存或更新输电线路信息
     * @param line 输电线路对象
     * @return 保存后的输电线路对象
     */
    TransmissionLine saveTransmissionLine(TransmissionLine line);
    
    /**
     * 删除输电线路
     * @param id 输电线路ID
     */
    void deleteTransmissionLine(Long id);
}
