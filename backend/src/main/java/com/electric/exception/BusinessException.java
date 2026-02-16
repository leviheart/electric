package com.electric.exception;

import org.springframework.http.HttpStatus;

/**
 * BusinessException - 业务异常类
 * 
 * 功能说明：
 * 自定义业务异常，用于在业务逻辑中抛出可预期的异常
 * 可以指定 HTTP 状态码和错误消息
 * 
 * 使用场景：
 * 1. 资源不存在时抛出
 * 2. 业务规则校验失败时抛出
 * 3. 权限不足时抛出
 * 
 * 文件关联：
 * - GlobalExceptionHandler: 全局异常处理器会捕获此异常
 * - Service: 服务层抛出此异常
 */
public class BusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    /**
     * 默认构造函数，使用 400 Bad Request 状态码
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    /**
     * 带状态码的构造函数
     * @param message 错误消息
     * @param httpStatus HTTP状态码
     */
    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    /**
     * 带原因异常的构造函数
     * @param message 错误消息
     * @param cause 原因异常
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    /**
     * 完整构造函数
     * @param message 错误消息
     * @param httpStatus HTTP状态码
     * @param cause 原因异常
     */
    public BusinessException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    /**
     * 获取HTTP状态码
     * @return HTTP状态码
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * 静态工厂方法：创建404 Not Found异常
     * @param resourceName 资源名称
     * @param id 资源ID
     * @return 业务异常
     */
    public static BusinessException notFound(String resourceName, Object id) {
        return new BusinessException(
                String.format("%s 不存在，ID: %s", resourceName, id),
                HttpStatus.NOT_FOUND
        );
    }

    /**
     * 静态工厂方法：创建400 Bad Request异常
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException badRequest(String message) {
        return new BusinessException(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * 静态工厂方法：创建403 Forbidden异常
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException forbidden(String message) {
        return new BusinessException(message, HttpStatus.FORBIDDEN);
    }

    /**
     * 静态工厂方法：创建409 Conflict异常
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException conflict(String message) {
        return new BusinessException(message, HttpStatus.CONFLICT);
    }
}
