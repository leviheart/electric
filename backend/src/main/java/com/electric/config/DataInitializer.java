package com.electric.config;

import com.electric.model.Area;
import com.electric.model.Substation;
import com.electric.model.TransmissionLine;
import com.electric.model.User;
import com.electric.repository.AreaRepository;
import com.electric.repository.SubstationRepository;
import com.electric.repository.TransmissionLineRepository;
import com.electric.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * DataInitializer - 数据初始化类
 * 
 * 功能说明：
 * 实现CommandLineRunner接口，在Spring Boot应用启动时自动执行数据初始化操作
 * 当数据库中没有数据时，插入示例数据，包括用户、变电站、输电线路和台区
 * 
 * 初始化流程：
 * 1. 初始化默认管理员用户
 * 2. 检查变电站表是否为空，如果为空则插入示例变电站
 * 3. 检查输电线路表是否为空，如果为空则插入示例输电线路
 * 4. 检查台区表是否为空，如果为空则插入示例台区
 * 
 * 文件关联：
 * - 实体类：使用Substation、TransmissionLine、Area、User实体类创建示例数据
 * - 数据访问：依赖各Repository接口进行数据操作
 * - 应用启动：实现CommandLineRunner接口，在应用启动时自动执行
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final SubstationRepository substationRepository;
    private final TransmissionLineRepository transmissionLineRepository;
    private final AreaRepository areaRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(SubstationRepository substationRepository, 
                          TransmissionLineRepository transmissionLineRepository, 
                          AreaRepository areaRepository,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.substationRepository = substationRepository;
        this.transmissionLineRepository = transmissionLineRepository;
        this.areaRepository = areaRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initUsers();
        initSubstations();
        initTransmissionLines();
        initAreas();
    }

    private void initUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            admin.setEnabled(true);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    private void initSubstations() {
        if (substationRepository.count() == 0) {
            Substation s1 = new Substation("变电站1", 39.9142, 116.4174, "220kV", "运行中");
            Substation s2 = new Substation("变电站2", 39.8942, 116.3974, "110kV", "运行中");
            Substation s3 = new Substation("变电站3", 39.9242, 116.4274, "35kV", "运行中");
            Substation s4 = new Substation("变电站4", 39.9042, 116.4374, "110kV", "运行中");
            Substation s5 = new Substation("变电站5", 39.9342, 116.3874, "220kV", "运行中");
            Substation s6 = new Substation("变电站6", 39.8842, 116.4074, "35kV", "运行中");
            Substation s7 = new Substation("变电站7", 39.9442, 116.4474, "110kV", "运行中");
            Substation s8 = new Substation("变电站8", 39.9542, 116.4174, "220kV", "运行中");
            
            substationRepository.save(s1);
            substationRepository.save(s2);
            substationRepository.save(s3);
            substationRepository.save(s4);
            substationRepository.save(s5);
            substationRepository.save(s6);
            substationRepository.save(s7);
            substationRepository.save(s8);
        }
    }

    private void initTransmissionLines() {
        if (transmissionLineRepository.count() == 0) {
            TransmissionLine l1 = new TransmissionLine("线路1", "变电站1", "变电站2", 5.2, "220kV", "运行中", "[[39.9142, 116.4174], [39.8942, 116.3974]]");
            TransmissionLine l2 = new TransmissionLine("线路2", "变电站2", "变电站3", 6.8, "110kV", "运行中", "[[39.8942, 116.3974], [39.9242, 116.4274]]");
            TransmissionLine l3 = new TransmissionLine("线路3", "变电站3", "变电站4", 4.5, "35kV", "运行中", "[[39.9242, 116.4274], [39.9042, 116.4374]]");
            TransmissionLine l4 = new TransmissionLine("线路4", "变电站4", "变电站5", 7.2, "110kV", "运行中", "[[39.9042, 116.4374], [39.9342, 116.3874]]");
            TransmissionLine l5 = new TransmissionLine("线路5", "变电站5", "变电站6", 8.1, "220kV", "运行中", "[[39.9342, 116.3874], [39.8842, 116.4074]]");
            TransmissionLine l6 = new TransmissionLine("线路6", "变电站6", "变电站7", 5.5, "35kV", "运行中", "[[39.8842, 116.4074], [39.9442, 116.4474]]");
            TransmissionLine l7 = new TransmissionLine("线路7", "变电站7", "变电站8", 6.2, "110kV", "运行中", "[[39.9442, 116.4474], [39.9542, 116.4174]]");
            TransmissionLine l8 = new TransmissionLine("线路8", "变电站8", "变电站1", 7.8, "220kV", "运行中", "[[39.9542, 116.4174], [39.9142, 116.4174]]");
            
            transmissionLineRepository.save(l1);
            transmissionLineRepository.save(l2);
            transmissionLineRepository.save(l3);
            transmissionLineRepository.save(l4);
            transmissionLineRepository.save(l5);
            transmissionLineRepository.save(l6);
            transmissionLineRepository.save(l7);
            transmissionLineRepository.save(l8);
        }
    }

    private void initAreas() {
        if (areaRepository.count() == 0) {
            Area a1 = new Area("台区1", "变电站1", 500, "正常", "[[39.91, 116.41], [39.92, 116.41], [39.92, 116.42], [39.91, 116.42], [39.91, 116.41]]");
            Area a2 = new Area("台区2", "变电站2", 300, "正常", "[[39.89, 116.39], [39.90, 116.39], [39.90, 116.40], [39.89, 116.40], [39.89, 116.39]]");
            Area a3 = new Area("台区3", "变电站3", 400, "正常", "[[39.92, 116.42], [39.93, 116.42], [39.93, 116.43], [39.92, 116.43], [39.92, 116.42]]");
            Area a4 = new Area("台区4", "变电站4", 600, "正常", "[[39.90, 116.43], [39.91, 116.43], [39.91, 116.44], [39.90, 116.44], [39.90, 116.43]]");
            Area a5 = new Area("台区5", "变电站5", 700, "正常", "[[39.93, 116.38], [39.94, 116.38], [39.94, 116.39], [39.93, 116.39], [39.93, 116.38]]");
            Area a6 = new Area("台区6", "变电站6", 200, "正常", "[[39.88, 116.40], [39.89, 116.40], [39.89, 116.41], [39.88, 116.41], [39.88, 116.40]]");
            Area a7 = new Area("台区7", "变电站7", 800, "正常", "[[39.94, 116.44], [39.95, 116.44], [39.95, 116.45], [39.94, 116.45], [39.94, 116.44]]");
            Area a8 = new Area("台区8", "变电站8", 900, "正常", "[[39.95, 116.41], [39.96, 116.41], [39.96, 116.42], [39.95, 116.42], [39.95, 116.41]]");
            
            areaRepository.save(a1);
            areaRepository.save(a2);
            areaRepository.save(a3);
            areaRepository.save(a4);
            areaRepository.save(a5);
            areaRepository.save(a6);
            areaRepository.save(a7);
            areaRepository.save(a8);
        }
    }
}
