package com.electric.config;

import com.electric.model.Alert;
import com.electric.model.Alert.AlertType;
import com.electric.model.Alert.AlertLevel;
import com.electric.model.Alert.AlertStatus;
import com.electric.model.Area;
import com.electric.model.OperationData;
import com.electric.model.Role;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import com.electric.model.User;
import com.electric.repository.AreaRepository;
import com.electric.repository.OperationDataRepository;
import com.electric.repository.RoleRepository;
import com.electric.repository.SubstationRepository;
import com.electric.repository.TransmissionLineRepository;
import com.electric.repository.UserRepository;
import com.electric.service.AlertService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DataInitializer - 数据初始化器
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、什么是数据初始化？
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * 数据初始化是在应用启动时自动创建初始数据的过程。
 * 
 * 为什么需要数据初始化？
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 1. 开发便利：无需手动创建测试数据                                        │
 * │ 2. 演示环境：提供预置数据便于展示功能                                     │
 * │ 3. 默认配置：创建默认管理员账户、角色等                                   │
 * │ 4. 数据一致性：确保必要的基础数据存在                                     │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、核心注解和接口
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * @Component
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 将此类注册为 Spring Bean                                                │
 * │ Spring 会在应用启动时自动创建此类的实例                                   │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * implements CommandLineRunner
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ CommandLineRunner 是 Spring Boot 提供的接口                              │
 * │                                                                          │
 * │ 执行时机：                                                               │
 * │ 1. Spring 容器初始化完成                                                 │
 * │ 2. 所有 Bean 创建和注入完成                                              │
 * │ 3. 应用开始接受请求之前                                                   │
 * │                                                                          │
 * │ 工作原理：                                                               │
 * │ Spring Boot 启动 → 扫描所有 CommandLineRunner 实现 → 调用 run() 方法     │
 * │                                                                          │
 * │ run(String... args) 方法：                                               │
 * │ - args: 命令行参数（如 java -jar app.jar --server.port=9000）            │
 * │ - 在应用启动时自动调用                                                    │
 * │ - 如果抛出异常，应用启动失败                                              │
 * │                                                                          │
 * │ 类似接口：ApplicationRunner（参数封装为 ApplicationArguments）            │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 三、初始化流程
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ run() 方法执行顺序：                                                     │
 * │                                                                          │
 * │ 1. initRoles()     → 创建角色（ADMIN、USER）                             │
 * │ 2. initUsers()     → 创建用户（admin、user）                             │
 * │ 3. initSubstations() → 创建变电站数据                                    │
 * │ 4. initTransmissionLines() → 创建输电线路数据                            │
 * │ 5. initAreas()     → 创建台区数据                                        │
 * │                                                                          │
 * │ 为什么按这个顺序？                                                       │
 * │ - 角色必须先存在，用户才能关联角色                                        │
 * │ - 变电站必须先存在，线路才能关联变电站                                    │
 * │ - 变电站必须先存在，台区才能关联变电站                                    │
 * │                                                                          │
 * │ 幂等性设计：                                                             │
 * │ - if (repository.count() == 0) 只在数据为空时初始化                      │
 * │ - 多次启动不会重复创建数据                                               │
 * │ - 安全地重新部署应用                                                     │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 四、密码加密说明
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * passwordEncoder.encode("admin123")
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 使用 BCrypt 加密密码                                                    │
 * │                                                                          │
 * │ 为什么不在配置文件中存储明文密码？                                       │
 * │ - 安全风险：配置文件可能被泄露                                           │
 * │ - 审计要求：密码必须加密存储                                             │
 * │ - 最佳实践：永远不要存储明文密码                                         │
 * │                                                                          │
 * │ BCrypt 特点：                                                            │
 * │ - 每次加密结果不同（包含随机盐值）                                        │
 * │ - 相同密码的加密结果不同                                                 │
 * │ - 防止彩虹表攻击                                                         │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 五、Lambda 表达式说明
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * userRepository.findByUsername("admin").ifPresent(admin -> { ... });
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Optional.ifPresent(Consumer) 的用法                                      │
 * │                                                                          │
 * │ 传统写法：                                                               │
 * │   Optional<User> opt = userRepository.findByUsername("admin");          │
 * │   if (opt.isPresent()) {                                                │
 * │       User admin = opt.get();                                           │
 * │       // 处理逻辑                                                        │
 * │   }                                                                      │
 * │                                                                          │
 * │ Lambda 写法：                                                            │
 * │   userRepository.findByUsername("admin")                                │
 * │       .ifPresent(admin -> {                                             │
 * │           // 处理逻辑                                                    │
 * │       });                                                                │
 * │                                                                          │
 * │ 好处：                                                                   │
 * │ - 代码更简洁                                                             │
 * │ - 避免 null 检查                                                        │
 * │ - 函数式编程风格                                                         │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - SecurityConfig: 密码编码器配置
 * - UserRepository: 用户数据访问
 * - RoleRepository: 角色数据访问
 * - SubstationRepository: 变电站数据访问
 * - TransmissionLineRepository: 输电线路数据访问
 * - AreaRepository: 台区数据访问
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final SubstationRepository substationRepository;
    private final TransmissionLineRepository transmissionLineRepository;
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final OperationDataRepository operationDataRepository;
    private final AlertService alertService;

    public DataInitializer(SubstationRepository substationRepository, 
                          TransmissionLineRepository transmissionLineRepository, 
                          AreaRepository areaRepository,
                          UserRepository userRepository,
                          RoleRepository roleRepository,
                          PasswordEncoder passwordEncoder,
                          OperationDataRepository operationDataRepository,
                          AlertService alertService) {
        this.substationRepository = substationRepository;
        this.transmissionLineRepository = transmissionLineRepository;
        this.areaRepository = areaRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.operationDataRepository = operationDataRepository;
        this.alertService = alertService;
    }

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        initUsers();
        initSubstations();
        initTransmissionLines();
        initAreas();
        initOperationData();
        initAlerts();
    }

    /**
     * 初始化角色数据
     * 
     * 幂等性：只在角色表为空时创建
     */
    private void initRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role("ADMIN", "管理员");
            Role userRole = new Role("USER", "普通用户");
            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }

    /**
     * 初始化用户数据
     * 
     * 创建默认用户：
     * - admin / admin123 (管理员)
     * - user / user123 (普通用户)
     */
    private void initUsers() {
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
        Role userRole = roleRepository.findByName("USER").orElse(null);
        
        if (userRepository.count() == 0) {
            // 创建管理员账户
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEnabled(true);
            if (adminRole != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                admin.setRoles(roles);
            }
            userRepository.save(admin);

            // 创建普通用户账户
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEnabled(true);
            if (userRole != null) {
                Set<Role> roles = new HashSet<>();
                roles.add(userRole);
                user.setRoles(roles);
            }
            userRepository.save(user);
        } else {
            // 如果用户已存在但没有角色，补充角色
            userRepository.findByUsername("admin").ifPresent(admin -> {
                if (admin.getRoles().isEmpty() && adminRole != null) {
                    Set<Role> roles = new HashSet<>();
                    roles.add(adminRole);
                    admin.setRoles(roles);
                    userRepository.save(admin);
                }
            });
            
            userRepository.findByUsername("user").ifPresent(user -> {
                if (user.getRoles().isEmpty() && userRole != null) {
                    Set<Role> roles = new HashSet<>();
                    roles.add(userRole);
                    user.setRoles(roles);
                    userRepository.save(user);
                }
            });
        }
    }

    /**
     * 初始化变电站数据
     * 
     * 创建北京市各区县的变电站
     * 包含：名称、经纬度、电压等级、运行状态
     */
    private void initSubstations() {
        if (substationRepository.count() == 0) {
            List<Substation> substations = new ArrayList<>();
            
            for (Map.Entry<String, List<ChinaPowerGridData.CityData>> entry : ChinaPowerGridData.CITIES.entrySet()) {
                String province = entry.getKey();
                List<ChinaPowerGridData.CityData> cities = entry.getValue();
                
                for (ChinaPowerGridData.CityData city : cities) {
                    for (int i = 1; i <= city.substationCount; i++) {
                        double latOffset = (Math.random() - 0.5) * 0.1;
                        double lngOffset = (Math.random() - 0.5) * 0.1;
                        
                        String voltageLevel;
                        if (i == 1 && city.substationCount >= 3) {
                            voltageLevel = "500kV";
                        } else if (i <= city.substationCount / 2) {
                            voltageLevel = "220kV";
                        } else {
                            voltageLevel = "110kV";
                        }
                        
                        String status = ChinaPowerGridData.STATUSES[(int) (Math.random() * ChinaPowerGridData.STATUSES.length)];
                        
                        Substation substation = new Substation(
                            province + city.name + (voltageLevel.equals("500kV") ? "主" : "") + i + "号变电站",
                            city.lat + latOffset,
                            city.lng + lngOffset,
                            voltageLevel,
                            status
                        );
                        
                        substation.setLoadRate(20 + Math.random() * 60);
                        substation.setCapacity(voltageLevel.equals("500kV") ? 1500.0 : 
                                              voltageLevel.equals("220kV") ? 300.0 : 100.0);
                        substation.setCurrentPower(substation.getCapacity() * substation.getLoadRate() / 100 * (0.9 + Math.random() * 0.1));
                        substation.setConnectedLines(2 + (int)(Math.random() * 5));
                        substation.setRegion(province + city.name);
                        
                        substations.add(substation);
                    }
                }
            }
            
            for (Substation s : substations) {
                substationRepository.save(s);
            }
            
            System.out.println("初始化变电站完成，共 " + substations.size() + " 个变电站，分布在 " + ChinaPowerGridData.CITIES.size() + " 个省市");
        }
    }

    /**
     * 初始化输电线路数据
     * 
     * 创建连接各变电站的输电线路
     * 包含：名称、起止变电站、长度、电压等级、状态、几何路径
     * 
     * geometry 字段说明：
     * - JSON 格式的坐标数组
     * - 用于在地图上绘制线路
     * - 格式：[[lat1, lng1], [lat2, lng2], ...]
     */
    private void initTransmissionLines() {
        if (transmissionLineRepository.count() == 0) {
            List<TransmissionLine> lines = new ArrayList<>();
            
            List<Substation> substations = substationRepository.findAll();
            
            if (substations.size() > 1) {
                Map<String, List<Substation>> substationsByProvince = new HashMap<>();
                for (Substation s : substations) {
                    String province = extractProvince(s.getName());
                    substationsByProvince.computeIfAbsent(province, k -> new ArrayList<>()).add(s);
                }
                
                for (Map.Entry<String, List<Substation>> entry : substationsByProvince.entrySet()) {
                    List<Substation> provinceSubstations = entry.getValue();
                    
                    for (int i = 0; i < provinceSubstations.size(); i++) {
                        Substation from = provinceSubstations.get(i);
                        
                        int lineCount = 2 + (int) (Math.random() * 3);
                        Set<Integer> connectedIndices = new HashSet<>();
                        
                        for (int j = 0; j < lineCount && j < provinceSubstations.size() - 1; j++) {
                            int toIndex;
                            do {
                                toIndex = (int) (Math.random() * provinceSubstations.size());
                            } while (toIndex == i || connectedIndices.contains(toIndex));
                            
                            connectedIndices.add(toIndex);
                            Substation to = provinceSubstations.get(toIndex);
                            
                            double distance = calculateDistance(from.getLatitude(), from.getLongitude(), 
                                to.getLatitude(), to.getLongitude());
                            
                            String voltageLevel;
                            if (distance > 100) {
                                voltageLevel = "500kV";
                            } else if (distance > 50) {
                                voltageLevel = "220kV";
                            } else if (distance > 20) {
                                voltageLevel = "110kV";
                            } else {
                                voltageLevel = "35kV";
                            }
                            
                            String status = ChinaPowerGridData.STATUSES[(int) (Math.random() * ChinaPowerGridData.STATUSES.length)];
                            String lineType = ChinaPowerGridData.LINE_TYPES[(int) (Math.random() * ChinaPowerGridData.LINE_TYPES.length)];
                            
                            String geometry = generateLineGeometry(from.getLatitude(), from.getLongitude(), 
                                to.getLatitude(), to.getLongitude());
                            
                            TransmissionLine line = new TransmissionLine(
                                from.getName() + "-" + to.getName() + "线",
                                from.getName(),
                                to.getName(),
                                Math.round(distance * 10) / 10.0,
                                voltageLevel,
                                status,
                                geometry
                            );
                            line.setLineType(lineType);
                            line.setLoadRate(30 + Math.random() * 50);
                            
                            lines.add(line);
                        }
                    }
                }
                
                for (Map.Entry<String, List<Substation>> entry : substationsByProvince.entrySet()) {
                    List<Substation> provinceSubstations = entry.getValue();
                    if (provinceSubstations.size() > 0) {
                        Substation mainSubstation = provinceSubstations.stream()
                            .filter(s -> s.getVoltageLevel().equals("500kV"))
                            .findFirst()
                            .orElse(provinceSubstations.get(0));
                        
                        for (Map.Entry<String, List<Substation>> otherEntry : substationsByProvince.entrySet()) {
                            if (!otherEntry.getKey().equals(entry.getKey())) {
                                List<Substation> otherSubstations = otherEntry.getValue();
                                Substation targetSubstation = otherSubstations.stream()
                                    .filter(s -> s.getVoltageLevel().equals("500kV"))
                                    .findFirst()
                                    .orElse(otherSubstations.get(0));
                                
                                double distance = calculateDistance(
                                    mainSubstation.getLatitude(), mainSubstation.getLongitude(),
                                    targetSubstation.getLatitude(), targetSubstation.getLongitude()
                                );
                                
                                if (distance < 500) {
                                    String geometry = generateLineGeometry(
                                        mainSubstation.getLatitude(), mainSubstation.getLongitude(),
                                        targetSubstation.getLatitude(), targetSubstation.getLongitude()
                                    );
                                    
                                    String voltageLevel = distance > 300 ? "1000kV" : "500kV";
                                    
                                    TransmissionLine interLine = new TransmissionLine(
                                        entry.getKey() + "-" + otherEntry.getKey() + "联络线",
                                        mainSubstation.getName(),
                                        targetSubstation.getName(),
                                        Math.round(distance * 10) / 10.0,
                                        voltageLevel,
                                        "运行中",
                                        geometry
                                    );
                                    interLine.setLineType("overhead");
                                    interLine.setLoadRate(40 + Math.random() * 30);
                                    
                                    lines.add(interLine);
                                }
                            }
                        }
                    }
                }
            }
            
            for (TransmissionLine l : lines) {
                transmissionLineRepository.save(l);
            }
            
            System.out.println("初始化输电线路完成，共 " + lines.size() + " 条线路");
        }
    }
    
    private String extractProvince(String substationName) {
        for (String province : ChinaPowerGridData.PROVINCES.keySet()) {
            if (substationName.startsWith(province)) {
                return province;
            }
        }
        return "未知";
    }
    
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = lat2 - lat1;
        double dLng = lng2 - lng1;
        return Math.sqrt(dLat * dLat + dLng * dLng) * 111;
    }
    
    private String generateLineGeometry(double lat1, double lng1, double lat2, double lng2) {
        StringBuilder geometry = new StringBuilder("[[");
        geometry.append(lat1).append(", ").append(lng1).append("]");
        
        int midPoints = 1 + (int) (Math.random() * 2);
        for (int k = 0; k < midPoints; k++) {
            double ratio = (k + 1.0) / (midPoints + 1.0);
            double midLat = lat1 + (lat2 - lat1) * ratio;
            double midLng = lng1 + (lng2 - lng1) * ratio;
            
            midLat += (Math.random() - 0.5) * 0.05;
            midLng += (Math.random() - 0.5) * 0.05;
            
            geometry.append(", [").append(midLat).append(", ").append(midLng).append("]");
        }
        
        geometry.append(", [").append(lat2).append(", ").append(lng2).append("]]");
        return geometry.toString();
    }

    /**
     * 初始化台区数据
     * 
     * 创建各变电站供电的台区
     * 包含：名称、所属变电站、用户数量、状态、供电范围
     * 
     * geometry 字段说明：
     * - JSON 格式的多边形坐标
     * - 用于在地图上绘制台区范围
     * - 格式：[[lat1, lng1], [lat2, lng2], ..., [lat1, lng1]]（首尾闭合）
     */
    private void initAreas() {
        if (areaRepository.count() == 0) {
            List<Area> areas = new ArrayList<>();
            
            List<Substation> substations = substationRepository.findAll();
            int[] capacities = {100, 200, 315, 400, 500, 630, 800};
            
            for (Substation substation : substations) {
                int areaCount = 2 + (int) (Math.random() * 3);
                
                for (int i = 1; i <= areaCount; i++) {
                    double centerLat = substation.getLatitude() + (Math.random() - 0.5) * 0.05;
                    double centerLng = substation.getLongitude() + (Math.random() - 0.5) * 0.05;
                    
                    double radius = 0.01 + Math.random() * 0.02;
                    int points = 6 + (int) (Math.random() * 4);
                    StringBuilder geometry = new StringBuilder("[");
                    
                    for (int j = 0; j <= points; j++) {
                        double angle = 2 * Math.PI * j / points;
                        double r = radius * (0.8 + Math.random() * 0.4);
                        double lat = centerLat + r * Math.cos(angle);
                        double lng = centerLng + r * Math.sin(angle);
                        
                        if (j > 0) geometry.append(", ");
                        geometry.append("[").append(lat).append(", ").append(lng).append("]");
                    }
                    geometry.append("]");
                    
                    int customerCount = 30 + (int) (Math.random() * 170);
                    int capacity = capacities[(int) (Math.random() * capacities.length)];
                    double loadRate = 20 + Math.random() * 70;
                    
                    String status;
                    if (loadRate > 90) {
                        status = "故障";
                    } else if (loadRate > 80) {
                        status = "过载";
                    } else if (loadRate > 60) {
                        status = "预警";
                    } else {
                        status = "正常";
                    }
                    
                    int importantCount = (int) (Math.random() * Math.min(10, customerCount / 20));
                    
                    String transformerLocation = "{\"lat\": " + centerLat + ", \"lng\": " + centerLng + "}";
                    
                    Area area = new Area(
                        substation.getName() + "-" + i + "号台区",
                        substation.getName(),
                        customerCount,
                        status,
                        geometry.toString()
                    );
                    area.setLoadRate(loadRate);
                    area.setTransformerCapacity(capacity);
                    area.setTransformerLocation(transformerLocation);
                    area.setImportantCustomerCount(importantCount);
                    
                    areas.add(area);
                }
            }
            
            for (Area a : areas) {
                areaRepository.save(a);
            }
            
            System.out.println("初始化台区完成，共 " + areas.size() + " 个台区");
        }
    }

    private void initOperationData() {
        if (operationDataRepository.count() == 0) {
            List<OperationData> operationDataList = new ArrayList<>();
            
            List<Substation> substations = substationRepository.findAll();
            List<TransmissionLine> lines = transmissionLineRepository.findAll();
            List<Area> areas = areaRepository.findAll();
            
            LocalDate today = LocalDate.now();
            
            for (int dayOffset = 0; dayOffset < 7; dayOffset++) {
                LocalDate date = today.minusDays(dayOffset);
                
                for (Substation substation : substations) {
                    for (int hour = 0; hour < 24; hour++) {
                        for (int minute = 0; minute < 60; minute += 30) {
                            LocalDateTime recordTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
                            
                            double baseVoltage = substation.getVoltageLevel().equals("220kV") ? 220 : 
                                                substation.getVoltageLevel().equals("110kV") ? 110 : 35;
                            
                            OperationData data = new OperationData(
                                "substation",
                                substation.getId(),
                                substation.getName(),
                                recordTime,
                                baseVoltage + (Math.random() * 5 - 2.5),
                                100 + Math.random() * 500,
                                10000 + Math.random() * 50000,
                                0.85 + Math.random() * 0.1,
                                49.9 + Math.random() * 0.2,
                                25 + Math.random() * 20,
                                Math.random() > 0.05 ? "正常" : "告警"
                            );
                            operationDataList.add(data);
                        }
                    }
                }
                
                for (TransmissionLine line : lines) {
                    for (int hour = 0; hour < 24; hour++) {
                        for (int minute = 0; minute < 60; minute += 30) {
                            LocalDateTime recordTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
                            
                            double baseVoltage = line.getVoltageLevel().equals("220kV") ? 220 : 
                                                line.getVoltageLevel().equals("110kV") ? 110 : 35;
                            
                            OperationData data = new OperationData(
                                "line",
                                line.getId(),
                                line.getName(),
                                recordTime,
                                baseVoltage + (Math.random() * 5 - 2.5),
                                50 + Math.random() * 300,
                                5000 + Math.random() * 30000,
                                0.85 + Math.random() * 0.1,
                                49.9 + Math.random() * 0.2,
                                30 + Math.random() * 30,
                                Math.random() > 0.05 ? "正常" : "告警"
                            );
                            operationDataList.add(data);
                        }
                    }
                }
                
                for (Area area : areas) {
                    for (int hour = 0; hour < 24; hour++) {
                        for (int minute = 0; minute < 60; minute += 30) {
                            LocalDateTime recordTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
                            
                            OperationData data = new OperationData(
                                "area",
                                area.getId(),
                                area.getName(),
                                recordTime,
                                0.38 + Math.random() * 0.02,
                                10 + Math.random() * 100,
                                100 + Math.random() * 1000,
                                0.85 + Math.random() * 0.1,
                                49.9 + Math.random() * 0.2,
                                25 + Math.random() * 15,
                                Math.random() > 0.02 ? "正常" : "告警"
                            );
                            operationDataList.add(data);
                        }
                    }
                }
            }
            
            for (OperationData data : operationDataList) {
                operationDataRepository.save(data);
            }
            
            System.out.println("初始化运行数据完成，共 " + operationDataList.size() + " 条记录");
        }
    }

    private void initAlerts() {
        List<Substation> substations = substationRepository.findAll();
        List<TransmissionLine> lines = transmissionLineRepository.findAll();
        List<Area> areas = areaRepository.findAll();
        
        int alertCount = 0;
        
        if (substations.size() > 0) {
            for (int i = 0; i < Math.min(5, substations.size()); i++) {
                Substation s = substations.get(i);
                Alert alert = Alert.deviceOffline("substation", s.getId(), s.getName(), s.getLatitude(), s.getLongitude());
                alert.setLevel(i == 0 ? AlertLevel.CRITICAL : AlertLevel.HIGH);
                alertService.createAlert(alert);
                alertCount++;
            }
            
            for (int i = 0; i < Math.min(3, substations.size()); i++) {
                Substation s = substations.get((i + 5) % substations.size());
                Alert alert = Alert.voltageAbnormal("substation", s.getId(), s.getName(), 180 + Math.random() * 40, s.getLatitude(), s.getLongitude());
                alert.setLevel(AlertLevel.HIGH);
                alertService.createAlert(alert);
                alertCount++;
            }
            
            for (int i = 0; i < Math.min(2, substations.size()); i++) {
                Substation s = substations.get((i + 8) % substations.size());
                Alert alert = Alert.temperatureHigh("substation", s.getId(), s.getName(), 65 + Math.random() * 20, s.getLatitude(), s.getLongitude());
                alert.setLevel(AlertLevel.MEDIUM);
                alertService.createAlert(alert);
                alertCount++;
            }
        }
        
        if (lines.size() > 0) {
            for (int i = 0; i < Math.min(4, lines.size()); i++) {
                TransmissionLine line = lines.get(i);
                double[] coords = getLineCenterCoords(line);
                Alert alert = Alert.overload("line", line.getId(), line.getName(), 85 + Math.random() * 15, coords[0], coords[1]);
                alert.setLevel(i == 0 ? AlertLevel.CRITICAL : AlertLevel.HIGH);
                alertService.createAlert(alert);
                alertCount++;
            }
            
            for (int i = 0; i < Math.min(2, lines.size()); i++) {
                TransmissionLine line = lines.get((i + 4) % lines.size());
                double[] coords = getLineCenterCoords(line);
                Alert alert = Alert.fault("line", line.getId(), line.getName(), "线路跳闸告警", coords[0], coords[1]);
                alert.setLevel(AlertLevel.CRITICAL);
                alertService.createAlert(alert);
                alertCount++;
            }
        }
        
        if (areas.size() > 0) {
            for (int i = 0; i < Math.min(3, areas.size()); i++) {
                Area area = areas.get(i);
                double[] coords = getAreaCenterCoords(area);
                Alert alert = Alert.overload("area", area.getId(), area.getName(), 90 + Math.random() * 10, coords[0], coords[1]);
                alert.setLevel(AlertLevel.HIGH);
                alertService.createAlert(alert);
                alertCount++;
            }
            
            for (int i = 0; i < Math.min(2, areas.size()); i++) {
                Area area = areas.get((i + 3) % areas.size());
                double[] coords = getAreaCenterCoords(area);
                Alert alert = Alert.maintenance("area", area.getId(), area.getName(), "计划检修提醒", coords[0], coords[1]);
                alert.setLevel(AlertLevel.LOW);
                alertService.createAlert(alert);
                alertCount++;
            }
        }
        
        System.out.println("初始化预警数据完成，共 " + alertCount + " 条预警");
    }
    
    private double[] getLineCenterCoords(TransmissionLine line) {
        try {
            String geometry = line.getGeometry();
            if (geometry != null && !geometry.isEmpty()) {
                String coords = geometry.replace("[[", "").replace("]]", "").replace("],[", ";");
                String[] points = coords.split(";");
                if (points.length > 0) {
                    int midIndex = points.length / 2;
                    String[] latLng = points[midIndex].replace("[", "").replace("]", "").split(",");
                    if (latLng.length >= 2) {
                        return new double[] {
                            Double.parseDouble(latLng[0].trim()),
                            Double.parseDouble(latLng[1].trim())
                        };
                    }
                }
            }
        } catch (Exception e) {
        }
        return new double[] { 39.9, 116.4 };
    }
    
    private double[] getAreaCenterCoords(Area area) {
        if (area.getTransformerLocation() != null) {
            try {
                String loc = area.getTransformerLocation();
                String latStr = loc.replaceAll(".*\"lat\":\\s*([0-9.]+).*", "$1");
                String lngStr = loc.replaceAll(".*\"lng\":\\s*([0-9.]+).*", "$1");
                return new double[] {
                    Double.parseDouble(latStr),
                    Double.parseDouble(lngStr)
                };
            } catch (Exception e) {
            }
        }
        
        try {
            String geometry = area.getGeometry();
            if (geometry != null && !geometry.isEmpty()) {
                String coords = geometry.replace("[[", "").replace("]]", "").replace("],[", ";");
                String[] points = coords.split(";");
                double sumLat = 0, sumLng = 0;
                for (String point : points) {
                    String[] latLng = point.replace("[", "").replace("]", "").split(",");
                    if (latLng.length >= 2) {
                        sumLat += Double.parseDouble(latLng[0].trim());
                        sumLng += Double.parseDouble(latLng[1].trim());
                    }
                }
                return new double[] { sumLat / points.length, sumLng / points.length };
            }
        } catch (Exception e) {
        }
        return new double[] { 39.9, 116.4 };
    }
}
