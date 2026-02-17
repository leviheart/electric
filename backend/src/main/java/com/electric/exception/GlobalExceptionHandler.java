package com.electric.exception;

import com.electric.model.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、为什么需要全局异常处理？
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 没有 GlobalExceptionHandler 时：                                         │
 * │                                                                          │
 * │ Controller 中需要这样写：                                                │
 * │   @PostMapping("/users")                                                │
 * │   public ResponseEntity<?> createUser(@RequestBody User user) {         │
 * │       try {                                                              │
 * │           userService.create(user);                                      │
 * │           return ResponseEntity.ok(...);                                 │
 * │       } catch (ValidationException e) {                                  │
 * │           return ResponseEntity.badRequest().body(...);                  │
 * │       } catch (NotFoundException e) {                                    │
 * │           return ResponseEntity.notFound().build();                      │
 * │       } catch (Exception e) {                                            │
 * │           return ResponseEntity.internalServerError().body(...);         │
 * │       }                                                                  │
 * │   }                                                                      │
 * │                                                                          │
 * │ 问题：                                                                   │
 * │ - 每个 Controller 都要重复写 try-catch                                   │
 * │ - 异常处理逻辑分散，难以维护                                             │
 * │ - 错误响应格式不统一                                                     │
 * │                                                                          │
 * │ 有 GlobalExceptionHandler 后：                                           │
 * │                                                                          │
 * │ Controller 只需：                                                        │
 * │   @PostMapping("/users")                                                │
 * │   public ResponseEntity<?> createUser(@RequestBody User user) {         │
 * │       userService.create(user);  // 异常会被自动捕获处理                  │
 * │       return ResponseEntity.ok(...);                                     │
 * │   }                                                                      │
 * │                                                                          │
 * │ 好处：                                                                   │
 * │ - Controller 代码更简洁                                                  │
 * │ - 异常处理逻辑集中管理                                                   │
 * │ - 错误响应格式统一                                                       │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、核心注解详解
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * @RestControllerAdvice
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ = @ControllerAdvice + @ResponseBody                                     │
 * │                                                                          │
 * │ @ControllerAdvice:                                                       │
 * │ - 这是一个控制器增强类                                                   │
 * │ - 可以拦截所有 Controller 的方法执行                                     │
 * │ - 常用于：异常处理、数据绑定、预处理等                                    │
 * │                                                                          │
 * │ @ResponseBody:                                                           │
 * │ - 方法的返回值直接作为 HTTP 响应体                                       │
 * │ - 会被自动序列化为 JSON                                                  │
 * │                                                                          │
 * │ 工作原理：                                                               │
 * │ 1. Spring 扫描所有 @ControllerAdvice 类                                  │
 * │ 2. 当 Controller 抛出异常时，查找匹配的 @ExceptionHandler 方法           │
 * │ 3. 调用对应的处理方法，返回统一格式的响应                                 │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @ExceptionHandler(BusinessException.class)
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 声明这个方法处理哪种类型的异常                                           │
 * │                                                                          │
 * │ 参数可以是：                                                             │
 * │ - 单个异常类：@ExceptionHandler(BusinessException.class)                │
 * │ - 多个异常类：@ExceptionHandler({IOException.class, SQLException.class})│
 * │ - 不指定参数：处理所有异常（等同于 @ExceptionHandler(Exception.class)）  │
 * │                                                                          │
 * │ 匹配规则：                                                               │
 * │ - 精确匹配优先：BusinessException 比 Exception 优先                     │
 * │ - 子类异常会被父类处理器捕获                                             │
 * │ - 多个匹配时，选择最具体的                                               │
 * │                                                                          │
 * │ 方法参数：                                                               │
 * │ - 异常对象：直接作为方法参数                                             │
 * │ - WebRequest / HttpServletRequest：获取请求信息                          │
 * │ - HttpSession：获取会话信息                                             │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 三、日志记录规范
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ SLF4J (Simple Logging Facade for Java)                                  │
 * │                                                                          │
 * │ 为什么用 SLF4J 而不是 System.out.println？                               │
 * │ - 性能：SLF4J 可以禁用日志级别，不执行字符串拼接                          │
 * │ - 灵活：可以输出到文件、数据库、控制台等                                  │
 * │ - 格式：自动添加时间、类名、行号等信息                                    │
 * │ - 级别：TRACE < DEBUG < INFO < WARN < ERROR                              │
 * │                                                                          │
 * │ 日志级别选择：                                                           │
 * │ - logger.trace(): 详细调试信息（生产环境通常关闭）                        │
 * │ - logger.debug(): 调试信息（开发环境开启）                                │
 * │ - logger.info(): 重要业务信息（如用户登录、订单创建）                      │
 * │ - logger.warn(): 警告信息（如参数校验失败、业务异常）                      │
 * │ - logger.error(): 错误信息（如系统异常、数据库连接失败）                   │
 * │                                                                          │
 * │ 占位符用法：                                                             │
 * │ - logger.info("用户 {} 登录成功", username)                              │
 * │ - 避免字符串拼接，性能更好                                               │
 * │ - 如果日志级别不够，不会执行字符串拼接                                    │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 四、HTTP 状态码选择
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 常用状态码：                                                             │
 * │                                                                          │
 * │ 2xx 成功：                                                               │
 * │ - 200 OK: 请求成功                                                      │
 * │ - 201 Created: 资源创建成功                                             │
 * │ - 204 No Content: 成功但无返回内容                                       │
 * │                                                                          │
 * │ 4xx 客户端错误：                                                         │
 * │ - 400 Bad Request: 请求参数错误                                         │
 * │ - 401 Unauthorized: 未认证                                              │
 * │ - 403 Forbidden: 无权限                                                 │
 * │ - 404 Not Found: 资源不存在                                             │
 * │ - 409 Conflict: 资源冲突（如用户名已存在）                                │
 * │ - 422 Unprocessable Entity: 语义错误                                    │
 * │                                                                          │
 * │ 5xx 服务器错误：                                                         │
 * │ - 500 Internal Server Error: 服务器内部错误                              │
 * │ - 502 Bad Gateway: 网关错误                                             │
 * │ - 503 Service Unavailable: 服务不可用                                   │
 * │                                                                          │
 * │ 本项目状态码映射：                                                       │
 * │ - BusinessException → 使用异常中定义的 code                              │
 * │ - 参数校验失败 → 400 Bad Request                                        │
 * │ - 其他异常 → 500 Internal Server Error                                  │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - BusinessException: 业务异常类
 * - ApiResponse: 统一响应格式
 * - Controller: 抛出异常的控制器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     * 
     * BusinessException 是我们自定义的业务异常
     * 包含错误码和错误消息
     * 
     * @param e 业务异常对象
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        // 使用 warn 级别，因为业务异常是预期内的错误
        logger.warn("业务异常: {}", e.getMessage());
        
        // 将异常中的错误码转换为 HTTP 状态码
        HttpStatus status = HttpStatus.valueOf(e.getCode());
        
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(e.getCode(), e.getMessage()));
    }

    /**
     * 处理参数校验异常
     * 
     * MethodArgumentNotValidException 在以下情况抛出：
     * - @Valid 注解触发 Bean Validation
     * - 校验失败（如 @NotBlank、@Size 等注解不满足）
     * 
     * 返回格式：
     * {
     *   "code": 400,
     *   "message": "参数校验失败",
     *   "data": {
     *     "username": "用户名不能为空",
     *     "password": "密码长度必须在6-20个字符之间"
     *   }
     * }
     * 
     * @param e 参数校验异常
     * @return 包含字段错误的响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException e) {
        logger.warn("参数校验失败: {}", e.getMessage());
        
        // 收集所有字段错误
        Map<String, String> errors = new HashMap<>();
        
        /**
         * Lambda 表达式说明：
         * e.getBindingResult().getAllErrors().forEach(error -> { ... })
         * 
         * 等价于：
         * List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
         * for (ObjectError error : allErrors) {
         *     String fieldName = ((FieldError) error).getField();
         *     String errorMessage = error.getDefaultMessage();
         *     errors.put(fieldName, errorMessage);
         * }
         */
        e.getBindingResult().getAllErrors().forEach(error -> {
            // FieldError 包含字段名和错误消息
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(400, "参数校验失败", errors));
    }

    /**
     * 处理所有未捕获的异常
     * 
     * 这是兜底的异常处理器
     * 捕获所有未被其他处理器处理的异常
     * 
     * 安全注意：
     * - 不要返回详细的错误堆栈给客户端
     * - 记录完整的错误日志便于排查
     * - 返回通用的错误消息
     * 
     * @param e 异常对象
     * @return 统一格式的错误响应
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        // 使用 error 级别，并打印完整堆栈
        logger.error("系统异常: ", e);
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(500, "服务器内部错误"));
    }
}
