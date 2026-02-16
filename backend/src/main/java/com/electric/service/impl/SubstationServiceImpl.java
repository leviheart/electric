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
 * 实现 SubstationService 接口定义的方法，处理变电站相关的业务逻辑
 * 
 * 架构层次：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ Controller 层（控制器）                                          │
 * │ - 接收 HTTP 请求                                                 │
 * │ - 调用 Service 层处理业务                                        │
 * │ - 返回 HTTP 响应                                                 │
 * └───────────────────────────┬─────────────────────────────────────┘
 *                             │ 调用
 * ┌───────────────────────────▼─────────────────────────────────────┐
 * │ Service 层（服务层）- 当前文件                                   │
 * │ - 处理业务逻辑                                                   │
 * │ - 调用 Repository 层访问数据                                     │
 * │ - 处理事务                                                       │
 * └───────────────────────────┬─────────────────────────────────────┘
 *                             │ 调用
 * ┌───────────────────────────▼─────────────────────────────────────┐
 * │ Repository 层（数据访问层）                                       │
 * │ - 执行数据库操作                                                  │
 * │ - 返回实体对象                                                    │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 注解说明：
 * - @Service: 标记这是一个服务层组件，Spring 会自动扫描并创建实例
 * 
 * 文件关联：
 * - 服务接口：SubstationService.java
 * - 数据访问：SubstationRepository
 * - 控制器：SubstationController
 * - 实体类：Substation
 * 
 * 新人提示：
 * - 构造函数注入是推荐的依赖注入方式
 * - 使用 final 修饰依赖，确保不可变
 * - @Service 注解使此类可以被 @Autowired 注入
 */
@Service
public class SubstationServiceImpl implements SubstationService {

    /**
     * 变电站数据访问接口
     * - 使用 final 修饰，确保依赖不可变
     * - 通过构造函数注入
     */
    private final SubstationRepository substationRepository;

    /**
     * 构造函数依赖注入
     * 
     * Spring 会自动：
     * 1. 找到 SubstationRepository 的实现类
     * 2. 创建实例
     * 3. 注入到此构造函数
     * 
     * 优点：
     * - 依赖明确，不可变
     * - 便于单元测试（可以 Mock）
     * - 不需要 @Autowired 注解（Spring 4.3+ 单构造函数可省略）
     * 
     * @param substationRepository 变电站数据访问接口
     */
    public SubstationServiceImpl(SubstationRepository substationRepository) {
        this.substationRepository = substationRepository;
    }

    /**
     * 获取所有变电站列表
     * 
     * 实现逻辑：
     * 1. 调用 Repository 的 findAll() 方法
     * 2. 返回查询结果
     * 
     * @return 变电站列表
     */
    @Override
    public List<Substation> getAllSubstations() {
        // 调用 JpaRepository 提供的 findAll() 方法
        // 返回所有变电站记录
        return substationRepository.findAll();
    }

    /**
     * 根据ID获取单个变电站详情
     * 
     * 实现逻辑：
     * 1. 调用 Repository 的 findById() 方法
     * 2. 使用 orElse(null) 处理 Optional
     * 3. 找到返回对象，未找到返回 null
     * 
     * @param id 变电站ID
     * @return 变电站对象，如果不存在则返回null
     */
    @Override
    public Substation getSubstationById(Long id) {
        // findById 返回 Optional<Substation>
        // orElse(null) 表示如果值不存在则返回 null
        return substationRepository.findById(id).orElse(null);
    }

    /**
     * 保存或更新变电站信息
     * 
     * 实现逻辑：
     * 1. 调用 Repository 的 save() 方法
     * 2. JPA 会根据 id 是否存在决定新增或更新
     * 
     * 新增 vs 更新：
     * - id 为 null：执行 INSERT
     * - id 不为 null 且存在于数据库：执行 UPDATE
     * 
     * @param substation 变电站对象
     * @return 保存后的变电站对象
     */
    @Override
    public Substation saveSubstation(Substation substation) {
        // save() 方法会自动判断是新增还是更新
        // 返回保存后的对象（包含生成的 id）
        return substationRepository.save(substation);
    }

    /**
     * 删除变电站
     * 
     * 实现逻辑：
     * 1. 调用 Repository 的 deleteById() 方法
     * 2. 根据 ID 删除对应记录
     * 
     * 注意事项：
     * - 如果记录不存在，会抛出 EmptyResultDataAccessException
     * - 实际项目中应先检查是否存在
     * 
     * @param id 变电站ID
     */
    @Override
    public void deleteSubstation(Long id) {
        // 根据 ID 删除记录
        substationRepository.deleteById(id);
    }
}
