package com.electric.service;

import com.electric.model.Substation;
import java.util.List;

/**
 * SubstationService - 变电站服务接口
 * 
 * 功能说明：
 * 定义变电站相关的业务操作接口
 * 采用接口-实现分离的设计模式，便于扩展和测试
 * 
 * 设计模式说明：
 * - 接口定义：定义业务方法签名
 * - 实现类：SubstationServiceImpl.java 实现具体业务逻辑
 * - 依赖注入：Spring 会自动注入实现类到需要的地方
 * 
 * 业务方法说明：
 * - getAllSubstations(): 获取所有变电站列表，用于地图展示
 * - getSubstationById(Long id): 根据ID获取单个变电站详情
 * - saveSubstation(Substation substation): 保存或更新变电站信息
 * - deleteSubstation(Long id): 根据ID删除变电站
 * 
 * 文件关联：
 * - 实现类：SubstationServiceImpl.java - 具体业务逻辑实现
 * - 实体类：Substation.java - 变电站数据模型
 * - 控制器：SubstationController.java - 调用此接口处理HTTP请求
 * 
 * 新人提示：
 * - 接口只定义方法签名，不包含实现代码
 * - 使用时通过依赖注入获取实现类：
 *   @Autowired
 *   private SubstationService substationService;
 */
public interface SubstationService {
    
    /**
     * 获取所有变电站列表
     * 用途：在地图上展示所有变电站位置
     * @return 变电站列表，如果没有数据则返回空列表
     */
    List<Substation> getAllSubstations();
    
    /**
     * 根据ID获取单个变电站详情
     * 用途：查看变电站详细信息或编辑前获取数据
     * @param id 变电站ID
     * @return 变电站对象，如果不存在则返回null
     */
    Substation getSubstationById(Long id);
    
    /**
     * 保存或更新变电站信息
     * 用途：新增变电站或更新已有变电站信息
     * 逻辑：如果substation对象包含id则更新，否则新增
     * @param substation 变电站对象（包含要保存的数据）
     * @return 保存后的变电站对象（包含生成的ID）
     */
    Substation saveSubstation(Substation substation);
    
    /**
     * 删除变电站
     * 用途：根据ID删除指定的变电站
     * 注意：删除前应检查是否存在关联数据（如输电线路）
     * @param id 要删除的变电站ID
     */
    void deleteSubstation(Long id);
}
