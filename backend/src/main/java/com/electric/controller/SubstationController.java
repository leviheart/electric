package com.electric.controller;

import com.electric.exception.BusinessException;
import com.electric.model.ApiResponse;
import com.electric.model.Substation;
import com.electric.service.SubstationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SubstationController - 变电站控制器
 * 
 * 功能说明：
 * 提供变电站相关的 RESTful API 接口，处理 HTTP 请求
 * 作为前后端交互的入口，负责接收请求、调用服务、返回响应
 * 
 * RESTful API 设计规范：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ HTTP 方法 │ URL 路径              │ 操作说明                      │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ GET       │ /api/substations      │ 获取所有变电站列表             │
 * │ GET       │ /api/substations/{id} │ 获取单个变电站详情             │
 * │ POST      │ /api/substations      │ 新增变电站                     │
 * │ PUT       │ /api/substations/{id} │ 更新变电站信息                 │
 * │ DELETE    │ /api/substations/{id} │ 删除变电站                     │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 注解说明：
 * - @RestController: 标记为 RESTful 控制器，自动将返回值转为 JSON
 * - @RequestMapping: 定义基础请求路径
 * - @CrossOrigin: 允许跨域请求（开发环境使用 *，生产环境应限制域名）
 * 
 * 响应格式：
 * 所有接口返回统一的 ApiResponse 格式：
 * {
 *   "code": 200,
 *   "message": "success",
 *   "data": { ... }
 * }
 * 
 * 文件关联：
 * - 服务层：SubstationService - 处理业务逻辑
 * - 模型层：Substation - 变电站实体类
 * - 响应类：ApiResponse - 统一响应格式
 * - 异常类：BusinessException - 业务异常
 * 
 * 新人提示：
 * - Controller 只负责接收请求和返回响应，不包含业务逻辑
 * - 使用 @Valid 注解自动校验请求参数
 * - 使用 ResponseEntity 返回合适的 HTTP 状态码
 */
@RestController
@RequestMapping("/api/substations")
@CrossOrigin(origins = "*")
public class SubstationController {

    /**
     * 变电站服务接口
     * - 通过构造函数注入
     * - 用于处理业务逻辑
     */
    private final SubstationService substationService;

    /**
     * 构造函数依赖注入
     * @param substationService 变电站服务接口
     */
    public SubstationController(SubstationService substationService) {
        this.substationService = substationService;
    }

    /**
     * 获取所有变电站列表
     * 
     * HTTP 请求：
     * GET /api/substations
     * 
     * 请求示例：
     * curl http://localhost:8080/api/substations
     * 
     * 响应示例：
     * {
     *   "code": 200,
     *   "message": "success",
     *   "data": [
     *     { "id": 1, "name": "朝阳220kV变电站", ... },
     *     { "id": 2, "name": "海淀110kV变电站", ... }
     *   ]
     * }
     * 
     * @return 变电站列表的标准 API 响应
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Substation>>> getAllSubstations() {
        // 调用服务层获取所有变电站
        List<Substation> substations = substationService.getAllSubstations();
        // 返回 200 OK 状态码和响应数据
        return ResponseEntity.ok(ApiResponse.success(substations));
    }

    /**
     * 根据ID获取单个变电站详情
     * 
     * HTTP 请求：
     * GET /api/substations/{id}
     * 
     * 请求示例：
     * curl http://localhost:8080/api/substations/1
     * 
     * 响应示例（成功）：
     * {
     *   "code": 200,
     *   "message": "success",
     *   "data": { "id": 1, "name": "朝阳220kV变电站", ... }
     * }
     * 
     * 响应示例（失败）：
     * {
     *   "code": 404,
     *   "message": "变电站不存在: 1",
     *   "data": null
     * }
     * 
     * @param id 变电站ID（从 URL 路径获取）
     * @return 变电站详情的标准 API 响应
     * @throws BusinessException 当变电站不存在时抛出 404 异常
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Substation>> getSubstationById(@PathVariable Long id) {
        // 调用服务层获取变电站
        Substation substation = substationService.getSubstationById(id);
        
        // 检查变电站是否存在
        if (substation == null) {
            // 不存在则抛出 404 业务异常
            throw BusinessException.notFound("变电站", id);
        }
        
        // 返回变电站详情
        return ResponseEntity.ok(ApiResponse.success(substation));
    }

    /**
     * 新增变电站
     * 
     * HTTP 请求：
     * POST /api/substations
     * Content-Type: application/json
     * 
     * 请求体示例：
     * {
     *   "name": "新变电站",
     *   "latitude": 39.9,
     *   "longitude": 116.4,
     *   "voltageLevel": "110kV",
     *   "status": "运行中"
     * }
     * 
     * 响应示例：
     * {
     *   "code": 200,
     *   "message": "变电站创建成功",
     *   "data": { "id": 3, "name": "新变电站", ... }
     * }
     * 
     * @param substation 变电站对象（经过 @Valid 参数校验）
     * @return 新增的变电站的标准 API 响应，HTTP 状态码 201
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Substation>> createSubstation(@Valid @RequestBody Substation substation) {
        // 调用服务层保存变电站
        Substation savedSubstation = substationService.saveSubstation(substation);
        
        // 返回 201 Created 状态码
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("变电站创建成功", savedSubstation));
    }

    /**
     * 更新变电站信息
     * 
     * HTTP 请求：
     * PUT /api/substations/{id}
     * Content-Type: application/json
     * 
     * 请求体示例：
     * {
     *   "name": "更新后的变电站",
     *   "status": "检修"
     * }
     * 
     * 响应示例：
     * {
     *   "code": 200,
     *   "message": "变电站更新成功",
     *   "data": { "id": 1, "name": "更新后的变电站", ... }
     * }
     * 
     * @param id 变电站ID（从 URL 路径获取）
     * @param substation 变电站对象（经过 @Valid 参数校验）
     * @return 更新后的变电站的标准 API 响应
     * @throws BusinessException 当变电站不存在时抛出 404 异常
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Substation>> updateSubstation(
            @PathVariable Long id, 
            @Valid @RequestBody Substation substation) {
        
        // 检查变电站是否存在
        Substation existingSubstation = substationService.getSubstationById(id);
        if (existingSubstation == null) {
            throw BusinessException.notFound("变电站", id);
        }
        
        // 设置 ID，确保更新正确的记录
        substation.setId(id);
        
        // 调用服务层更新变电站
        Substation updatedSubstation = substationService.saveSubstation(substation);
        
        return ResponseEntity.ok(ApiResponse.success("变电站更新成功", updatedSubstation));
    }

    /**
     * 删除变电站
     * 
     * HTTP 请求：
     * DELETE /api/substations/{id}
     * 
     * 请求示例：
     * curl -X DELETE http://localhost:8080/api/substations/1
     * 
     * 响应示例：
     * {
     *   "code": 200,
     *   "message": "变电站删除成功",
     *   "data": null
     * }
     * 
     * @param id 变电站ID（从 URL 路径获取）
     * @return 无内容的标准 API 响应，HTTP 状态码 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubstation(@PathVariable Long id) {
        // 调用服务层删除变电站
        substationService.deleteSubstation(id);
        
        // 返回 204 No Content 状态码
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("变电站删除成功", null));
    }
}
