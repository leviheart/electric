package com.electric.model;

/**
 * ApiResponse - 标准API响应结构
 * 
 * 功能说明：
 * 提供符合现代开发标准的API响应格式，包含状态码、消息和数据字段
 * 
 * 字段说明：
 * - code: HTTP状态码
 * - message: 响应消息
 * - data: 响应数据（可以是任意类型）
 * 
 * 使用场景：
 * 所有RESTful API接口都应该返回此结构的响应，确保前端能统一处理响应数据
 */
public class ApiResponse<T> {
    
    // HTTP状态码
    private int code;
    
    // 响应消息
    private String message;
    
    // 响应数据
    private T data;
    
    /**
     * 构造函数
     * @param code HTTP状态码
     * @param message 响应消息
     * @param data 响应数据
     */
    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功响应构造函数
     * @param data 响应数据
     * @return ApiResponse 成功响应对象
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }
    
    /**
     * 成功响应构造函数（带自定义消息）
     * @param message 响应消息
     * @param data 响应数据
     * @return ApiResponse 成功响应对象
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }
    
    /**
     * 错误响应构造函数
     * @param code HTTP状态码
     * @param message 错误消息
     * @return ApiResponse 错误响应对象
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
    
    /**
     * 错误响应构造函数（默认400状态码）
     * @param message 错误消息
     * @return ApiResponse 错误响应对象
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(400, message, null);
    }
    
    /**
     * 错误响应构造函数（带数据）
     * @param message 错误消息
     * @param data 错误详情数据
     * @return ApiResponse 错误响应对象
     */
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(400, message, data);
    }
    
    // Getter和Setter方法
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
}