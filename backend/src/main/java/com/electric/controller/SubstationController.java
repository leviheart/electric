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
 * 提供符合RESTful标准的变电站相关API接口，支持增删改查操作
 * 使用 @Valid 注解进行参数校验，使用 BusinessException 处理业务异常
 * 
 * RESTful API接口：
 * - GET /api/substations: 获取所有变电站列表
 * - GET /api/substations/{id}: 根据ID获取单个变电站详情
 * - POST /api/substations: 新增变电站
 * - PUT /api/substations/{id}: 更新变电站信息
 * - DELETE /api/substations/{id}: 删除变电站
 * 
 * 现代化开发标准：
 * - 使用RESTful URL设计（名词路径，HTTP方法表示操作）
 * - 使用合适的HTTP状态码
 * - 使用JSON作为数据交换格式
 * - 依赖注入进行服务层调用
 * - 参数校验使用 @Valid 注解
 * - 异常处理使用 BusinessException
 * - 跨域支持
 * 
 * 文件关联：
 * - 服务层：依赖SubstationService接口处理业务逻辑
 * - 模型层：使用Substation实体类作为数据传输对象
 * - 异常处理：GlobalExceptionHandler 统一处理异常
 * - 前端调用：前端App.vue组件通过fetch调用这些API获取变电站数据
 */
@RestController
@RequestMapping("/api/substations")
@CrossOrigin(origins = "*")
public class SubstationController {

    private final SubstationService substationService;

    public SubstationController(SubstationService substationService) {
        this.substationService = substationService;
    }

    /**
     * 获取所有变电站列表
     * @return 变电站列表的标准API响应
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Substation>>> getAllSubstations() {
        List<Substation> substations = substationService.getAllSubstations();
        return ResponseEntity.ok(ApiResponse.success(substations));
    }

    /**
     * 根据ID获取单个变电站详情
     * @param id 变电站ID
     * @return 变电站详情的标准API响应
     * @throws BusinessException 当变电站不存在时抛出
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Substation>> getSubstationById(@PathVariable Long id) {
        Substation substation = substationService.getSubstationById(id);
        if (substation == null) {
            throw BusinessException.notFound("变电站", id);
        }
        return ResponseEntity.ok(ApiResponse.success(substation));
    }

    /**
     * 新增变电站
     * @param substation 变电站对象（经过参数校验）
     * @return 新增的变电站的标准API响应
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Substation>> createSubstation(@Valid @RequestBody Substation substation) {
        Substation savedSubstation = substationService.saveSubstation(substation);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("变电站创建成功", savedSubstation));
    }

    /**
     * 更新变电站信息
     * @param id 变电站ID
     * @param substation 变电站对象（经过参数校验）
     * @return 更新后的变电站的标准API响应
     * @throws BusinessException 当变电站不存在时抛出
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Substation>> updateSubstation(
            @PathVariable Long id, 
            @Valid @RequestBody Substation substation) {
        Substation existingSubstation = substationService.getSubstationById(id);
        if (existingSubstation == null) {
            throw BusinessException.notFound("变电站", id);
        }
        substation.setId(id);
        Substation updatedSubstation = substationService.saveSubstation(substation);
        return ResponseEntity.ok(ApiResponse.success("变电站更新成功", updatedSubstation));
    }

    /**
     * 删除变电站
     * @param id 变电站ID
     * @return 无内容的标准API响应
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSubstation(@PathVariable Long id) {
        substationService.deleteSubstation(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("变电站删除成功", null));
    }
}
