package com.electric.controller;

import com.electric.exception.BusinessException;
import com.electric.model.ApiResponse;
import com.electric.model.TransmissionLine;
import com.electric.service.TransmissionLineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TransmissionLineController - 输电线路控制器
 * 
 * 功能说明：
 * 提供符合RESTful标准的输电线路相关API接口，支持增删改查操作
 * 使用 @Valid 注解进行参数校验，使用 BusinessException 处理业务异常
 * 
 * RESTful API接口：
 * - GET /api/transmission-lines: 获取所有输电线路列表
 * - GET /api/transmission-lines/{id}: 根据ID获取单个输电线路详情
 * - POST /api/transmission-lines: 新增输电线路
 * - PUT /api/transmission-lines/{id}: 更新输电线路信息
 * - DELETE /api/transmission-lines/{id}: 删除输电线路
 * 
 * 文件关联：
 * - 服务层：依赖TransmissionLineService接口处理业务逻辑
 * - 模型层：使用TransmissionLine实体类作为数据传输对象
 * - 异常处理：GlobalExceptionHandler 统一处理异常
 * - 前端调用：前端App.vue组件通过fetch调用这些API获取输电线路数据
 */
@RestController
@RequestMapping("/api/transmission-lines")
@CrossOrigin(origins = "*")
public class TransmissionLineController {

    private final TransmissionLineService transmissionLineService;

    public TransmissionLineController(TransmissionLineService transmissionLineService) {
        this.transmissionLineService = transmissionLineService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TransmissionLine>>> getAllTransmissionLines() {
        List<TransmissionLine> lines = transmissionLineService.getAllTransmissionLines();
        return ResponseEntity.ok(ApiResponse.success(lines));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TransmissionLine>> getTransmissionLineById(@PathVariable Long id) {
        TransmissionLine line = transmissionLineService.getTransmissionLineById(id);
        if (line == null) {
            throw BusinessException.notFound("输电线路", id);
        }
        return ResponseEntity.ok(ApiResponse.success(line));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TransmissionLine>> createTransmissionLine(
            @Valid @RequestBody TransmissionLine line) {
        TransmissionLine savedLine = transmissionLineService.saveTransmissionLine(line);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("输电线路创建成功", savedLine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TransmissionLine>> updateTransmissionLine(
            @PathVariable Long id, 
            @Valid @RequestBody TransmissionLine line) {
        TransmissionLine existingLine = transmissionLineService.getTransmissionLineById(id);
        if (existingLine == null) {
            throw BusinessException.notFound("输电线路", id);
        }
        line.setId(id);
        TransmissionLine updatedLine = transmissionLineService.saveTransmissionLine(line);
        return ResponseEntity.ok(ApiResponse.success("输电线路更新成功", updatedLine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTransmissionLine(@PathVariable Long id) {
        transmissionLineService.deleteTransmissionLine(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("输电线路删除成功", null));
    }
}
