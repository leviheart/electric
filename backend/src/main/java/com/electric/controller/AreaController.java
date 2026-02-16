package com.electric.controller;

import com.electric.exception.BusinessException;
import com.electric.model.ApiResponse;
import com.electric.model.Area;
import com.electric.service.AreaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AreaController - 台区控制器
 * 
 * 功能说明：
 * 提供符合RESTful标准的台区相关API接口，支持增删改查操作
 * 使用 @Valid 注解进行参数校验，使用 BusinessException 处理业务异常
 * 
 * RESTful API接口：
 * - GET /api/areas: 获取所有台区列表
 * - GET /api/areas/{id}: 根据ID获取单个台区详情
 * - POST /api/areas: 新增台区
 * - PUT /api/areas/{id}: 更新台区信息
 * - DELETE /api/areas/{id}: 删除台区
 * 
 * 文件关联：
 * - 服务层：依赖AreaService接口处理业务逻辑
 * - 模型层：使用Area实体类作为数据传输对象
 * - 异常处理：GlobalExceptionHandler 统一处理异常
 * - 前端调用：前端App.vue组件通过fetch调用这些API获取台区数据
 */
@RestController
@RequestMapping("/api/areas")
@CrossOrigin(origins = "*")
public class AreaController {

    private final AreaService areaService;

    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Area>>> getAllAreas() {
        List<Area> areas = areaService.getAllAreas();
        return ResponseEntity.ok(ApiResponse.success(areas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Area>> getAreaById(@PathVariable Long id) {
        Area area = areaService.getAreaById(id);
        if (area == null) {
            throw BusinessException.notFound("台区", id);
        }
        return ResponseEntity.ok(ApiResponse.success(area));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Area>> createArea(@Valid @RequestBody Area area) {
        Area savedArea = areaService.saveArea(area);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("台区创建成功", savedArea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Area>> updateArea(
            @PathVariable Long id, 
            @Valid @RequestBody Area area) {
        Area existingArea = areaService.getAreaById(id);
        if (existingArea == null) {
            throw BusinessException.notFound("台区", id);
        }
        area.setId(id);
        Area updatedArea = areaService.saveArea(area);
        return ResponseEntity.ok(ApiResponse.success("台区更新成功", updatedArea));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArea(@PathVariable Long id) {
        areaService.deleteArea(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("台区删除成功", null));
    }
}
