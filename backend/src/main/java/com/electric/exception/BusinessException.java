package com.electric.exception;

/**
 * BusinessException - 自定义业务异常类
 * 
 * 功能说明：
 * 封装业务逻辑中的异常情况，提供统一的异常处理方式
 * 携带错误码和错误信息，便于前端展示和日志记录
 * 
 * 使用场景：
 * 1. 资源不存在（404）
 * 2. 参数校验失败（400）
 * 3. 业务规则冲突（409）
 * 4. 权限不足（403）
 * 5. 其他业务异常
 * 
 * 静态工厂方法：
 * - notFound(): 创建资源不存在异常
 * - badRequest(): 创建参数错误异常
 * - conflict(): 创建冲突异常
 * - forbidden(): 创建权限不足异常
 * 
 * 文件关联：
 * - GlobalExceptionHandler: 全局异常处理器，捕获并处理此类异常
 * - Controller: 抛出业务异常
 * - Service: 抛出业务异常
 * 
 * 新人提示：
 * - 业务异常与系统异常的区别：
 *   - 业务异常：用户操作导致的预期内错误（如密码错误）
 *   - 系统异常：程序 bug 或外部服务故障（如数据库连接失败）
 * - 使用静态工厂方法创建异常，保证错误信息格式统一
 */
public class BusinessException extends RuntimeException {

    /** 错误码，对应 HTTP 状态码 */
    private final int code;
    
    /** 错误信息 */
    private final String message;

    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码
     * @return 错误码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取错误信息
     * @return 错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 创建资源不存在异常（404）
     * 
     * 使用示例：
     * throw BusinessException.notFound("变电站", 1L);
     * // 输出：变电站不存在: 1
     * 
     * @param resource 资源名称
     * @param id 资源ID
     * @return BusinessException 实例
     */
    public static BusinessException notFound(String resource, Object id) {
        return new BusinessException(404, resource + "不存在: " + id);
    }

    /**
     * 创建参数错误异常（400）
     * 
     * 使用示例：
     * throw BusinessException.badRequest("用户名不能为空");
     * 
     * @param message 错误信息
     * @return BusinessException 实例
     */
    public static BusinessException badRequest(String message) {
        return new BusinessException(400, message);
    }

    /**
     * 创建冲突异常（409）
     * 
     * 使用示例：
     * throw BusinessException.conflict("用户名已存在");
     * 
     * @param message 错误信息
     * @return BusinessException 实例
     */
    public static BusinessException conflict(String message) {
        return new BusinessException(409, message);
    }

    /**
     * 创建权限不足异常（403）
     * 
     * 使用示例：
     * throw BusinessException.forbidden("无权访问此资源");
     * 
     * @param message 错误信息
     * @return BusinessException 实例
     */
    public static BusinessException forbidden(String message) {
        return new BusinessException(403, message);
    }
}
