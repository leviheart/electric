package com.electric.exception;

import com.electric.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler - 全局异常处理器
 * 
 * 功能说明：
 * 使用 @RestControllerAdvice 注解，统一处理所有 Controller 抛出的异常
 * 将异常转换为统一的 API 响应格式，便于前端处理
 * 
 * 处理的异常类型：
 * 1. BusinessException: 业务异常，返回业务错误信息
 * 2. MethodArgumentNotValidException: 参数校验失败，返回字段错误信息
 * 3. Exception: 其他未知异常，返回通用错误信息
 * 
 * 异常处理流程：
 * 1. 捕获异常
 * 2. 记录日志
 * 3. 构建统一响应格式
 * 4. 返回合适的 HTTP 状态码
 * 
 * 文件关联：
 * - BusinessException: 自定义业务异常
 * - ApiResponse: 统一响应格式
 * - Controller: 抛出异常的地方
 * 
 * 新人提示：
 * - @RestControllerAdvice 是 @ControllerAdvice + @ResponseBody 的组合
 * - @ExceptionHandler 指定要处理的异常类型
 * - 返回值会自动序列化为 JSON
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * 
     * 当 Controller 或 Service 抛出 BusinessException 时，
     * 此方法会捕获并转换为统一的响应格式
     * 
     * @param e 业务异常
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        // 记录错误日志
        System.err.println("业务异常: " + e.getMessage());
        
        // 根据错误码确定 HTTP 状态码
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        
        // 构建错误响应
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(e.getCode(), e.getMessage()));
    }

    /**
     * 处理参数校验失败异常
     * 
     * 当 @Valid 注解校验失败时，Spring 会抛出 MethodArgumentNotValidException
     * 此方法提取所有字段错误，返回详细的错误信息
     * 
     * @param e 参数校验异常
     * @return 统一格式的错误响应，包含字段错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException e) {
        // 记录错误日志
        System.err.println("参数校验失败: " + e.getMessage());
        
        // 提取所有字段错误
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        // 构建错误响应，包含字段错误详情
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, "参数校验失败", errors));
    }

    /**
     * 处理其他未知异常
     * 
     * 当发生未被其他处理器捕获的异常时，
     * 返回通用错误信息，避免暴露系统内部信息
     * 
     * @param e 异常
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        // 记录错误日志（包含堆栈信息）
        System.err.println("系统异常: " + e.getMessage());
        e.printStackTrace();
        
        // 返回通用错误信息，不暴露具体错误详情
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "服务器内部错误"));
    }
}
