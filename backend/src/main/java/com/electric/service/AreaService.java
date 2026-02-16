package com.electric.service;

import com.electric.model.Area;
import java.util.List;

/**
 * AreaService - 台区服务接口
 * 
 * 功能说明：
 * 定义台区相关的业务操作接口
 * 
 * 业务方法说明：
 * - getAllAreas(): 获取所有台区列表，用于地图展示
 * - getAreaById(Long id): 根据ID获取单个台区详情
 * - saveArea(Area area): 保存或更新台区信息
 * - deleteArea(Long id): 根据ID删除台区
 * 
 * 文件关联：
 * - 实现类：AreaServiceImpl.java - 具体业务逻辑实现
 * - 实体类：Area.java - 台区数据模型
 * - 控制器：AreaController.java - 调用此接口处理HTTP请求
 * 
 * 新人提示：
 * - 台区是指配电变压器的供电范围区域
 * - 台区在地图上显示为多边形区域
 * - geometry 字段存储多边形顶点坐标的 JSON 数组
 */
public interface AreaService {
    
    /**
     * 获取所有台区列表
     * @return 台区列表
     */
    List<Area> getAllAreas();
    
    /**
     * 根据ID获取单个台区详情
     * @param id 台区ID
     * @return 台区对象，如果不存在则返回null
     */
    Area getAreaById(Long id);
    
    /**
     * 保存或更新台区信息
     * @param area 台区对象
     * @return 保存后的台区对象
     */
    Area saveArea(Area area);
    
    /**
     * 删除台区
     * @param id 台区ID
     */
    void deleteArea(Long id);
}
