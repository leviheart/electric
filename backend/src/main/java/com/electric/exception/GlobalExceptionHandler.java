package com.electric.exception;

import com.electric.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler - 全局异常处理器
 * 
 * 功能说明：
 * 使用 @RestControllerAdvice 注解，统一处理控制器层抛出的各种异常
 * 将异常转换为统一的 API 响应格式，便于前端处理
 * 
 * 处理的异常类型：
 * 1. BusinessException - 业务异常（自定义）
 * 2. MethodArgumentNotValidException - 参数校验异常
 * 3. MethodArgumentTypeMismatchException - 参数类型不匹配
 * 4. NoHandlerFoundException - 404异常
 * 5. Exception - 其他未知异常
 * 
 * 文件关联：
 * - ApiResponse: 统一响应格式类
 * - Controller: 所有控制器抛出的异常都会被这里捕获
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * @param ex 业务异常
     * @return 统一错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        ApiResponse<Void> response = ApiResponse.error(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    /**
     * 处理参数校验异常
     * 当 @Valid 注解校验失败时抛出此异常
     * @param ex 参数校验异常
     * @return 统一错误响应，包含具体的字段错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponse<Map<String, String>> response = ApiResponse.error("参数校验失败", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 处理参数类型不匹配异常
     * 当请求参数类型与期望类型不匹配时抛出
     * @param ex 参数类型不匹配异常
     * @return 统一错误响应
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        String message = String.format("参数 '%s' 类型错误，期望类型: %s", 
                ex.getName(), 
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "未知");
        ApiResponse<Void> response = ApiResponse.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 处理404异常
     * @param ex 404异常
     * @return 统一错误响应
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NoHandlerFoundException ex) {
        ApiResponse<Void> response = ApiResponse.error("请求的资源不存在: " + ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 处理空指针异常
     * @param ex 空指针异常
     * @return 统一错误响应
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Void>> handleNullPointerException(NullPointerException ex) {
        ApiResponse<Void> response = ApiResponse.error("系统内部错误：空指针异常");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * 处理非法参数异常
     * @param ex 非法参数异常
     * @return 统一错误响应
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse<Void> response = ApiResponse.error("参数错误: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 处理其他所有未捕获的异常
     * 作为最后的兜底处理，防止异常信息直接暴露给用户
     * @param ex 异常
     * @return 统一错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        // 在生产环境中，应该记录异常日志
        ex.printStackTrace();
        ApiResponse<Void> response = ApiResponse.error("系统繁忙，请稍后重试");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
