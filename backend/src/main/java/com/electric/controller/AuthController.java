package com.electric.controller;

import com.electric.dto.LoginRequest;
import com.electric.dto.LoginResponse;
import com.electric.dto.UserInfo;
import com.electric.model.ApiResponse;
import com.electric.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController - 认证控制器
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、什么是控制器 (Controller)？
 * ═══════════════════════════════════════════════════════════════════════════
 * 控制器是 MVC 架构中的 C (Controller)，负责：
 * - 接收 HTTP 请求
 * - 调用业务逻辑处理
 * - 返回 HTTP 响应
 * 
 * 在 Spring MVC 中，控制器使用 @RestController 注解标记。
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、核心注解详解
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * @RestController
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ = @Controller + @ResponseBody                                          │
 * │                                                                          │
 * │ @Controller: 标记这是一个 Spring MVC 控制器                              │
 * │ @ResponseBody: 方法的返回值直接作为 HTTP 响应体返回                        │
 * │                会被自动序列化为 JSON 格式                                 │
 * │                                                                          │
 * │ 为什么用 @RestController 而不是 @Controller？                            │
 * │ - RESTful API 只返回数据，不需要视图渲染                                  │
 * │ - 避免每个方法都加 @ResponseBody 注解                                    │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @RequestMapping("/api/auth")
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 定义这个控制器处理的基础 URL 路径                                         │
 * │ 所有方法的 URL 都会加上这个前缀                                          │
 * │                                                                          │
 * │ 例如：login 方法的完整 URL = /api/auth + /login = /api/auth/login       │
 * │                                                                          │
 * │ 常用属性：                                                               │
 * │ - value/path: URL 路径                                                  │
 * │ - method: HTTP 方法（GET、POST、PUT、DELETE）                            │
 * │ - consumes: 请求内容类型（如 application/json）                          │
 * │ - produces: 响应内容类型                                                 │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @CrossOrigin(origins = "*")
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 跨域资源共享 (CORS) 配置                                                 │
 * │                                                                          │
 * │ 什么是跨域？                                                             │
 * │ 浏览器的同源策略：不同源的 AJAX 请求会被阻止                              │
 * │ - 协议不同：http → https                                                │
 * │ - 域名不同：localhost → example.com                                     │
 * │ - 端口不同：8080 → 5173                                                 │
 * │                                                                          │
 * │ origins = "*" 表示允许所有来源访问                                       │
 * │ 生产环境应该指定具体域名：origins = "https://example.com"                │
 * │                                                                          │
 * │ 其他常用属性：                                                           │
 * │ - methods: 允许的 HTTP 方法                                              │
 * │ - allowedHeaders: 允许的请求头                                           │
 * │ - allowCredentials: 是否允许携带 Cookie                                  │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @PostMapping("/login")
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ = @RequestMapping(method = RequestMethod.POST, value = "/login")        │
 * │                                                                          │
 * │ 专门处理 POST 请求的快捷注解                                             │
 * │ 类似的有：@GetMapping、@PutMapping、@DeleteMapping、@PatchMapping        │
 * │                                                                          │
 * │ 为什么登录用 POST？                                                      │
 * │ - POST 请求体在 HTTPS 中是加密的，更安全                                 │
 * │ - POST 不会出现在浏览器历史记录和服务器日志中                             │
 * │ - GET 参数会在 URL 中暴露密码                                            │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @Valid
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 触发 Bean Validation 参数校验                                            │
 * │                                                                          │
 * │ 工作流程：                                                               │
 * │ 1. Spring 接收请求，将 JSON 反序列化为对象                               │
 * │ 2. @Valid 触发校验，检查对象上的校验注解                                  │
 * │ 3. 校验失败抛出 MethodArgumentNotValidException                         │
 * │ 4. GlobalExceptionHandler 捕获并返回错误信息                              │
 * │                                                                          │
 * │ 例如 LoginRequest 中有 @NotBlank：                                       │
 * │ - 如果 username 为空，校验失败                                           │
 * │ - 返回 {"code":400,"message":"参数校验失败","data":{"username":"用户名不能为空"}} │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @RequestBody
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 将 HTTP 请求体反序列化为 Java 对象                                        │
 * │                                                                          │
 * │ 请求：POST /api/auth/login                                              │
 * │ 请求体：{"username":"admin","password":"admin123"}                      │
 * │                    ↓                                                    │
 * │ Spring 使用 Jackson 库将 JSON 转换为 LoginRequest 对象                   │
 * │                                                                          │
 * │ 与 @RequestParam 的区别：                                                │
 * │ - @RequestParam: 获取 URL 参数 ?username=admin                          │
 * │ - @RequestBody: 获取请求体中的 JSON                                      │
 * │                                                                          │
 * │ 为什么用 @RequestBody？                                                  │
 * │ - JSON 格式更灵活，支持嵌套对象                                          │
 * │ - POST 请求体比 URL 参数更安全                                           │
 * │ - 前端常用 JSON 格式发送数据                                             │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ResponseEntity<T>
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Spring 封装的 HTTP 响应对象                                              │
 * │                                                                          │
 * │ 包含三部分：                                                             │
 * │ 1. 状态码：200 OK、400 Bad Request、404 Not Found 等                     │
 * │ 2. 响应头：Content-Type、Authorization 等                                │
 * │ 3. 响应体：泛型 T 指定的类型                                              │
 * │                                                                          │
 * │ 常用方法：                                                               │
 * │ - ResponseEntity.ok(body): 返回 200 状态码和响应体                        │
 * │ - ResponseEntity.status(404).body(body): 自定义状态码                     │
 * │ - ResponseEntity.noContent().build(): 返回 204 无内容                    │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 三、依赖注入 (Dependency Injection)
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * private final AuthService authService;
 * 
 * public AuthController(AuthService authService) {
 *     this.authService = authService;
 * }
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 什么是依赖注入？                                                         │
 * │                                                                          │
 * │ 传统方式（手动创建）：                                                   │
 * │   AuthService authService = new AuthServiceImpl(); // 篇程耦合          │
 * │                                                                          │
 * │ 依赖注入方式（Spring 自动注入）：                                        │
 * │   Spring 容器创建 AuthServiceImpl 实例                                   │
 * │   通过构造函数注入到 AuthController                                      │
 * │                                                                          │
 * │ 为什么用构造函数注入而不是字段注入？                                      │
 * │ - 字段注入：@Autowired private AuthService authService;                 │
 * │ - 构造函数注入：更安全，可以声明为 final，便于测试                        │
 * │                                                                          │
 * │ 为什么声明为 final？                                                     │
 * │ - 确保依赖在构造时就被赋值，之后不可变                                    │
 * │ - 防止意外修改依赖                                                       │
 * │ - 线程安全                                                               │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 四、RESTful API 设计规范
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ REST (Representational State Transfer) 表述性状态转移                   │
 * │                                                                          │
 * │ 设计原则：                                                               │
 * │ 1. 使用名词表示资源：/users、/substations                               │
 * │ 2. 使用 HTTP 方法表示操作：                                              │
 * │    - GET: 查询资源                                                       │
 * │    - POST: 创建资源                                                      │
 * │    - PUT: 更新资源（完整更新）                                           │
 * │    - PATCH: 更新资源（部分更新）                                         │
 * │    - DELETE: 删除资源                                                    │
 * │ 3. 使用 HTTP 状态码表示结果：                                            │
 * │    - 200: 成功                                                           │
 * │    - 201: 创建成功                                                       │
 * │    - 400: 请求参数错误                                                   │
 * │    - 401: 未认证                                                         │
 * │    - 403: 无权限                                                         │
 * │    - 404: 资源不存在                                                     │
 * │    - 500: 服务器错误                                                     │
 * │                                                                          │
 * │ 本项目 API 示例：                                                        │
 * │ - POST /api/auth/login     → 登录（不是 RESTful，是动作）               │
 * │ - POST /api/auth/register  → 注册（不是 RESTful，是动作）               │
 * │ - GET  /api/substations    → 获取所有变电站                              │
 * │ - GET  /api/substations/1  → 获取 ID 为 1 的变电站                       │
 * │ - POST /api/substations    → 创建变电站                                  │
 * │ - PUT  /api/substations/1  → 更新 ID 为 1 的变电站                       │
 * │ - DELETE /api/substations/1 → 删除 ID 为 1 的变电站                      │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("登录成功", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody LoginRequest loginRequest) {
        authService.register(loginRequest);
        return ResponseEntity.ok(ApiResponse.success("注册成功", null));
    }

    /**
     * 获取当前登录用户信息
     * 
     * @AuthenticationPrincipal 注解说明：
     * ┌─────────────────────────────────────────────────────────────────────────┐
     * │ 自动从 SecurityContext 中注入当前认证用户                                │
     * │                                                                          │
     * │ 工作原理：                                                               │
     * │ 1. JwtAuthenticationFilter 验证 Token 后设置 SecurityContext            │
     * │ 2. @AuthenticationPrincipal 从 SecurityContext 获取用户信息              │
     * │ 3. 如果未登录，该参数为 null                                             │
     │ └─────────────────────────────────────────────────────────────────────────┘
     */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfo>> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserInfo userInfo = authService.getUserInfo(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(userInfo));
    }
}
