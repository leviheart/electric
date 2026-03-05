package com.electric.service.impl;

import com.electric.dto.LoginRequest;
import com.electric.dto.LoginResponse;
import com.electric.dto.UserInfo;
import com.electric.exception.BusinessException;
import com.electric.model.Role;
import com.electric.model.User;
import com.electric.repository.RoleRepository;
import com.electric.repository.UserRepository;
import com.electric.security.JwtUtil;
import com.electric.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * AuthServiceImpl - 认证服务实现类
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、分层架构 - Service 层的作用
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 三层架构：                                                               │
 * │                                                                          │
 * │ ┌─────────────────┐                                                     │
 * │ │ Controller 层   │ ← 接收请求，参数校验，调用 Service                   │
 * │ └────────┬────────┘                                                     │
 * │          ↓                                                              │
 * │ ┌─────────────────┐                                                     │
 * │ │ Service 层      │ ← 业务逻辑处理，事务管理                             │
 * │ └────────┬────────┘                                                     │
 * │          ↓                                                              │
 * │ ┌─────────────────┐                                                     │
 * │ │ Repository 层   │ ← 数据访问，CRUD 操作                               │
 * │ └─────────────────┘                                                     │
 * │                                                                          │
 * │ 为什么需要 Service 层？                                                  │
 * │ 1. 分离关注点：Controller 处理 HTTP，Service 处理业务                    │
 * │ 2. 复用性：同一个 Service 方法可以被多个 Controller 调用                 │
 * │ 3. 事务管理：@Transactional 注解在 Service 层生效                        │
 * │ 4. 测试友好：Service 层更容易进行单元测试                                 │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、核心注解
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * @Service
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 标记这是一个 Spring 服务组件                                             │
 * │                                                                          │
 * │ 作用：                                                                   │
 * │ 1. 被 Spring 扫描并注册为 Bean（组件扫描）                                │
 * │ 2. 可以被其他组件通过依赖注入使用                                         │
 * │ 3. 支持事务管理（配合 @Transactional）                                   │
 * │                                                                          │
 * │ @Service vs @Component：                                                 │
 * │ - @Service 是 @Component 的特化版本                                      │
 * │ - 语义更清晰，表示这是业务服务层                                          │
 * │ - 功能上完全相同                                                         │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * @Value("${jwt.expiration}")
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 从配置文件注入值                                                         │
 * │                                                                          │
 * │ 工作原理：                                                               │
 * │ 1. Spring 读取 application.properties                                   │
 * │ 2. 找到 jwt.expiration 属性                                              │
 * │ 3. 将值注入到字段中                                                      │
 * │                                                                          │
 * │ 配置文件示例：                                                           │
 * │ jwt.expiration=86400000  # 24小时（毫秒）                                │
 * │                                                                          │
 * │ 好处：                                                                   │
 * │ - 配置与代码分离                                                         │
 * │ - 不同环境可以使用不同配置                                               │
 * │ - 修改配置不需要重新编译                                                 │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 依赖注入 - 为什么使用构造函数注入？
     * ┌─────────────────────────────────────────────────────────────────────────┐
     * │                                                                          │
     * │ private final: 声明为 final，确保依赖不可变                              │
     * │                                                                          │
     * │ 依赖说明：                                                               │
     * │ - UserRepository: 用户数据访问，查询/保存用户                            │
     * │ - PasswordEncoder: 密码加密器，BCrypt 实现                               │
     * │ - JwtUtil: JWT 工具类，生成/验证 Token                                   │
     * │                                                                          │
     * │ 构造函数注入 vs 字段注入：                                               │
     * │                                                                          │
     * │ ❌ 字段注入（不推荐）：                                                  │
     * │    @Autowired                                                           │
     * │    private UserRepository userRepository;                               │
     * │    - 不能声明为 final                                                   │
     * │    - 难以进行单元测试                                                   │
     * │    - 隐藏了类的依赖关系                                                 │
     * │                                                                          │
     * │ ✅ 构造函数注入（推荐）：                                                │
     * │    private final UserRepository userRepository;                         │
     * │    public AuthServiceImpl(UserRepository userRepository) {              │
     * │        this.userRepository = userRepository;                            │
     * │    }                                                                    │
     * │    - 依赖明确，声明为 final                                             │
     * │    - 便于测试（可以手动传入 mock 对象）                                  │
     * │    - Spring 4.3+ 单构造函数可省略 @Autowired                            │
     * └─────────────────────────────────────────────────────────────────────────┘
     */
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 用户登录
     * 
     * ════════════════════════════════════════════════════════════════════════
     * 登录流程：
     * ════════════════════════════════════════════════════════════════════════
     * 
     * 1. 根据用户名查询用户
     *    ┌─────────────────────────────────────────────────────────────────────┐
     *    │ userRepository.findByUsername(username)                             │
     *    │                                                                      │
     *    │ Optional<User> - Java 8 引入的容器类，避免空指针异常                  │
     *    │ - orElseThrow(): 如果值不存在，抛出异常                              │
     *    │ - orElse(): 如果值不存在，返回默认值                                 │
     *    │ - isPresent(): 判断值是否存在                                        │
     *    └─────────────────────────────────────────────────────────────────────┘
     * 
     * 2. 验证账户状态
     *    ┌─────────────────────────────────────────────────────────────────────┐
     *    │ if (!user.getEnabled())                                              │
     *    │                                                                      │
     *    │ enabled 字段控制账户是否可用                                         │
     *    │ - true: 正常使用                                                     │
     *    │ - false: 被禁用（如违规、欠费等）                                     │
     *    └─────────────────────────────────────────────────────────────────────┘
     * 
     * 3. 验证密码
     *    ┌─────────────────────────────────────────────────────────────────────┐
     *    │ passwordEncoder.matches(明文密码, 加密密码)                          │
     *    │                                                                      │
     *    │ BCrypt 工作原理：                                                    │
     *    │ 1. 从加密密码中提取盐值（salt）                                       │
     *    │ 2. 用相同的盐值加密明文密码                                           │
     *    │ 3. 比较两个加密结果是否相同                                           │
     *    │                                                                      │
     *    │ 为什么不直接比较？                                                   │
     *    │ - BCrypt 每次加密结果都不同（包含随机盐值）                            │
     *    │ - 必须使用 matches 方法进行验证                                       │
     *    │                                                                      │
     *    │ 安全提示：                                                           │
     *    │ - "用户名或密码错误" 而不是具体提示                                   │
     *    │ - 防止攻击者猜测是用户名还是密码错误                                   │
     *    └─────────────────────────────────────────────────────────────────────┘
     * 
     * 4. 生成 JWT Token
     *    ┌─────────────────────────────────────────────────────────────────────┐
     *    │ jwtUtil.generateToken(username)                                      │
     *    │                                                                      │
     *    │ JWT 结构：Header.Payload.Signature                                   │
     *    │ - Header: 算法类型                                                   │
     *    │ - Payload: 用户名、过期时间等                                         │
     *    │ - Signature: 防篡改签名                                              │
     *    └─────────────────────────────────────────────────────────────────────┘
     */
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 步骤1：查询用户，不存在则抛出业务异常
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> BusinessException.badRequest("用户名或密码错误"));
        
        // 步骤2：检查账户是否被禁用
        if (!user.getEnabled()) {
            throw BusinessException.badRequest("账户已被禁用");
        }
        
        // 步骤3：验证密码（BCrypt 比对）
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw BusinessException.badRequest("用户名或密码错误");
        }
        
        // 步骤4：生成 JWT Token
        String token = jwtUtil.generateToken(user.getUsername());
        
        // 步骤5：获取用户角色（用于前端权限控制）
        String roleName = user.getRoles().isEmpty() ? "VIEWER" : 
            user.getRoles().iterator().next().getName();
        
        // 步骤6：返回登录响应
        return new LoginResponse(token, user.getUsername(), roleName, jwtExpiration / 1000);
    }

    /**
     * 用户注册
     * 
     * ════════════════════════════════════════════════════════════════════════
     * 注册流程：
     * ════════════════════════════════════════════════════════════════════════
     * 
     * 1. 检查用户名是否已存在
     *    ┌─────────────────────────────────────────────────────────────────────┐
     *    │ userRepository.existsByUsername(username)                            │
     *    │                                                                      │
     *    │ 数据库唯一约束 + 代码检查 = 双重保障                                  │
     *    │ - 代码检查：提供友好的错误提示                                        │
     *    │ - 数据库约束：防止并发注册导致的重复                                   │
     *    └─────────────────────────────────────────────────────────────────────┘
     * 
     * 2. 密码加密
     *    ┌─────────────────────────────────────────────────────────────────────┐
     *    │ passwordEncoder.encode(password)                                     │
     *    │                                                                      │
     *    │ 永远不要存储明文密码！                                                │
     *    │ - BCrypt 自动生成盐值并加密                                           │
     *    │ - 每次加密结果不同，防止彩虹表攻击                                     │
     *    │ - 计算成本可调，抵抗暴力破解                                           │
     *    └─────────────────────────────────────────────────────────────────────┘
     * 
     * 3. 保存用户
     *    ┌─────────────────────────────────────────────────────────────────────┐
     *    │ userRepository.save(user)                                            │
     *    │                                                                      │
     *    │ Spring Data JPA 的 save 方法：                                       │
     *    │ - 如果 ID 为 null，执行 INSERT（新建）                                │
     *    │ - 如果 ID 存在，执行 UPDATE（更新）                                   │
     *    └─────────────────────────────────────────────────────────────────────┘
     */
    @Override
    public boolean register(LoginRequest loginRequest) {
        if (userRepository.existsByUsername(loginRequest.getUsername())) {
            throw BusinessException.conflict("用户名已存在");
        }
        
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
        user.setEnabled(true);
        
        String roleName = loginRequest.getRole();
        if (roleName != null && !roleName.isEmpty()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        newRole.setDescription(roleName.equals("ADMIN") ? "管理员" : "普通用户");
                        return roleRepository.save(newRole);
                    });
            Set<Role> roles = new HashSet<>();
            roles.add(role);
            user.setRoles(roles);
        } else {
            Role userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName("USER");
                        newRole.setDescription("普通用户");
                        return roleRepository.save(newRole);
                    });
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);
        }
        
        userRepository.save(user);
        return true;
    }

    /**
     * 获取用户信息
     */
    @Override
    public UserInfo getUserInfo(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> BusinessException.notFound("用户", username));
        
        String roleName = user.getRoles().isEmpty() ? "VIEWER" : 
            user.getRoles().iterator().next().getName();
        
        return new UserInfo(user.getUsername(), roleName, user.getEnabled());
    }
}
