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
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 设计模式：接口-实现分离                                          │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ 优点：                                                          │
 * │ 1. 解耦：调用者只依赖接口，不依赖具体实现                         │
 * │ 2. 可测试：可以轻松 Mock 接口进行单元测试                         │
 * │ 3. 可扩展：可以有多种实现，如切换数据库                           │
 * │                                                                 │
 * │ 使用方式：                                                      │
 * │ @Autowired                                                      │
 * │ private SubstationService substationService; // 注入接口        │
 * │                                                                 │
 * │ Spring 会自动找到 SubstationServiceImpl 并注入                   │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 业务方法说明：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 方法名              │ 说明                                      │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ getAllSubstations() │ 获取所有变电站列表，用于地图展示            │
 * │ getSubstationById() │ 根据ID获取单个变电站详情                   │
 * │ saveSubstation()    │ 保存或更新变电站信息                       │
 * │ deleteSubstation()  │ 根据ID删除变电站                           │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - 实现类：SubstationServiceImpl.java - 具体业务逻辑实现
 * - 实体类：Substation.java - 变电站数据模型
 * - 控制器：SubstationController.java - 调用此接口处理HTTP请求
 * 
 * 新人提示：
 * - 接口只定义方法签名，不包含实现代码
 * - 方法签名包括：返回类型、方法名、参数列表
 * - 实现类在 SubstationServiceImpl.java 中
 */
public interface SubstationService {
    
    /**
     * 获取所有变电站列表
     * 
     * 使用场景：
     * - 地图页面加载时获取所有变电站数据
     * - 在地图上展示所有变电站位置
     * 
     * 返回值说明：
     * - 返回所有变电站的列表
     * - 如果没有数据，返回空列表（不是 null）
     * 
     * @return 变电站列表，如果没有数据则返回空列表
     */
    List<Substation> getAllSubstations();
    
    /**
     * 根据ID获取单个变电站详情
     * 
     * 使用场景：
     * - 查看变电站详细信息
     * - 编辑变电站前获取数据
     * 
     * 返回值说明：
     * - 找到则返回变电站对象
     * - 未找到则返回 null
     * 
     * @param id 变电站ID
     * @return 变电站对象，如果不存在则返回null
     */
    Substation getSubstationById(Long id);
    
    /**
     * 保存或更新变电站信息
     * 
     * 使用场景：
     * - 新增变电站
     * - 修改现有变电站信息
     * 
     * 逻辑说明：
     * - 如果 substation 对象包含 id，则更新对应记录
     * - 如果 substation 对象不包含 id，则新增记录
     * 
     * @param substation 变电站对象（包含要保存的数据）
     * @return 保存后的变电站对象（包含生成的ID）
     */
    Substation saveSubstation(Substation substation);
    
    /**
     * 删除变电站
     * 
     * 使用场景：
     * - 删除不再使用的变电站
     * 
     * 注意事项：
     * - 删除前应检查是否存在关联数据（如输电线路）
     * - 删除操作不可逆
     * 
     * @param id 要删除的变电站ID
     */
    void deleteSubstation(Long id);
}
