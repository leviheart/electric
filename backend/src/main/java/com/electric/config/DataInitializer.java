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
            Substation[] substations = {
                new Substation("朝阳220kV变电站", 39.9242, 116.4674, "220kV", "运行中"),
                new Substation("海淀110kV变电站", 39.9842, 116.2974, "110kV", "运行中"),
                new Substation("丰台35kV变电站", 39.8542, 116.2874, "35kV", "运行中"),
                new Substation("东城110kV变电站", 39.9342, 116.4174, "110kV", "运行中"),
                new Substation("西城220kV变电站", 39.9142, 116.3674, "220kV", "运行中"),
                new Substation("通州110kV变电站", 39.9042, 116.6574, "110kV", "运行中"),
                new Substation("大兴35kV变电站", 39.7242, 116.3374, "35kV", "运行中"),
                new Substation("顺义220kV变电站", 40.1242, 116.6574, "220kV", "运行中"),
                new Substation("昌平110kV变电站", 40.2142, 116.2374, "110kV", "运行中"),
                new Substation("石景山35kV变电站", 39.9042, 116.1874, "35kV", "运行中"),
                new Substation("房山220kV变电站", 39.7442, 116.1374, "220kV", "运行中"),
                new Substation("门头沟110kV变电站", 39.9442, 116.0974, "110kV", "运行中"),
                new Substation("怀柔35kV变电站", 40.3142, 116.6374, "35kV", "运行中"),
                new Substation("密云220kV变电站", 40.3742, 116.8474, "220kV", "运行中"),
                new Substation("平谷110kV变电站", 40.1442, 117.1174, "110kV", "运行中"),
                new Substation("延庆35kV变电站", 40.4642, 115.9774, "35kV", "运行中")
            };
            
            for (Substation s : substations) {
                substationRepository.save(s);
            }
        }
    }

    private void initTransmissionLines() {
        if (transmissionLineRepository.count() == 0) {
            TransmissionLine[] lines = {
                new TransmissionLine("朝阳-海淀线", "朝阳220kV变电站", "海淀110kV变电站", 18.5, "220kV", "运行中", 
                    "[[39.9242, 116.4674], [39.9542, 116.3874], [39.9842, 116.2974]]"),
                new TransmissionLine("海淀-丰台线", "海淀110kV变电站", "丰台35kV变电站", 15.2, "110kV", "运行中", 
                    "[[39.9842, 116.2974], [39.9142, 116.2874], [39.8542, 116.2874]]"),
                new TransmissionLine("东城-西城线", "东城110kV变电站", "西城220kV变电站", 5.8, "110kV", "运行中", 
                    "[[39.9342, 116.4174], [39.9242, 116.3874], [39.9142, 116.3674]]"),
                new TransmissionLine("朝阳-通州线", "朝阳220kV变电站", "通州110kV变电站", 16.3, "220kV", "运行中", 
                    "[[39.9242, 116.4674], [39.9142, 116.5574], [39.9042, 116.6574]]"),
                new TransmissionLine("丰台-大兴线", "丰台35kV变电站", "大兴35kV变电站", 14.7, "35kV", "运行中", 
                    "[[39.8542, 116.2874], [39.7842, 116.3074], [39.7242, 116.3374]]"),
                new TransmissionLine("顺义-昌平线", "顺义220kV变电站", "昌平110kV变电站", 22.4, "220kV", "运行中", 
                    "[[40.1242, 116.6574], [40.1674, 116.4474], [40.2142, 116.2374]]"),
                new TransmissionLine("石景山-门头沟线", "石景山35kV变电站", "门头沟110kV变电站", 12.1, "110kV", "运行中", 
                    "[[39.9042, 116.1874], [39.9242, 116.1374], [39.9442, 116.0974]]"),
                new TransmissionLine("房山-丰台线", "房山220kV变电站", "丰台35kV变电站", 20.6, "220kV", "运行中", 
                    "[[39.7442, 116.1374], [39.7942, 116.2074], [39.8542, 116.2874]]"),
                new TransmissionLine("怀柔-密云线", "怀柔35kV变电站", "密云220kV变电站", 18.9, "110kV", "运行中", 
                    "[[40.3142, 116.6374], [40.3442, 116.7374], [40.3742, 116.8474]]"),
                new TransmissionLine("平谷-顺义线", "平谷110kV变电站", "顺义220kV变电站", 25.3, "110kV", "运行中", 
                    "[[40.1442, 117.1174], [40.1342, 116.8874], [40.1242, 116.6574]]"),
                new TransmissionLine("延庆-昌平线", "延庆35kV变电站", "昌平110kV变电站", 28.7, "35kV", "运行中", 
                    "[[40.4642, 115.9774], [40.3374, 116.1074], [40.2142, 116.2374]]"),
                new TransmissionLine("西城-海淀线", "西城220kV变电站", "海淀110kV变电站", 8.4, "220kV", "运行中", 
                    "[[39.9142, 116.3674], [39.9474, 116.3274], [39.9842, 116.2974]]"),
                new TransmissionLine("朝阳-东城线", "朝阳220kV变电站", "东城110kV变电站", 4.2, "110kV", "运行中", 
                    "[[39.9242, 116.4674], [39.9292, 116.4374], [39.9342, 116.4174]]"),
                new TransmissionLine("大兴-房山线", "大兴35kV变电站", "房山220kV变电站", 11.8, "110kV", "运行中", 
                    "[[39.7242, 116.3374], [39.7342, 116.2374], [39.7442, 116.1374]]"),
                new TransmissionLine("通州-大兴线", "通州110kV变电站", "大兴35kV变电站", 32.5, "110kV", "运行中", 
                    "[[39.9042, 116.6574], [39.8142, 116.4974], [39.7242, 116.3374]]")
            };
            
            for (TransmissionLine l : lines) {
                transmissionLineRepository.save(l);
            }
        }
    }

    private void initAreas() {
        if (areaRepository.count() == 0) {
            Area[] areas = {
                new Area("朝阳区A台区", "朝阳220kV变电站", 1200, "正常", 
                    "[[39.91, 116.45], [39.94, 116.45], [39.94, 116.48], [39.91, 116.48], [39.91, 116.45]]"),
                new Area("朝阳区B台区", "朝阳220kV变电站", 850, "正常", 
                    "[[39.92, 116.46], [39.93, 116.46], [39.93, 116.47], [39.92, 116.47], [39.92, 116.46]]"),
                new Area("海淀区A台区", "海淀110kV变电站", 950, "正常", 
                    "[[39.97, 116.28], [39.99, 116.28], [39.99, 116.31], [39.97, 116.31], [39.97, 116.28]]"),
                new Area("海淀区B台区", "海淀110kV变电站", 680, "正常", 
                    "[[39.98, 116.29], [39.99, 116.29], [39.99, 116.30], [39.98, 116.30], [39.98, 116.29]]"),
                new Area("丰台区台区", "丰台35kV变电站", 420, "正常", 
                    "[[39.84, 116.27], [39.86, 116.27], [39.86, 116.30], [39.84, 116.30], [39.84, 116.27]]"),
                new Area("东城区台区", "东城110kV变电站", 1500, "正常", 
                    "[[39.92, 116.40], [39.94, 116.40], [39.94, 116.43], [39.92, 116.43], [39.92, 116.40]]"),
                new Area("西城区台区", "西城220kV变电站", 1350, "正常", 
                    "[[39.90, 116.35], [39.92, 116.35], [39.92, 116.38], [39.90, 116.38], [39.90, 116.35]]"),
                new Area("通州区台区", "通州110kV变电站", 780, "正常", 
                    "[[39.89, 116.64], [39.91, 116.64], [39.91, 116.67], [39.89, 116.67], [39.89, 116.64]]"),
                new Area("大兴区A台区", "大兴35kV变电站", 560, "正常", 
                    "[[39.71, 116.32], [39.73, 116.32], [39.73, 116.35], [39.71, 116.35], [39.71, 116.32]]"),
                new Area("大兴区B台区", "大兴35kV变电站", 380, "正常", 
                    "[[39.72, 116.33], [39.73, 116.33], [39.73, 116.34], [39.72, 116.34], [39.72, 116.33]]"),
                new Area("顺义区台区", "顺义220kV变电站", 620, "正常", 
                    "[[40.11, 116.64], [40.13, 116.64], [40.13, 116.67], [40.11, 116.67], [40.11, 116.64]]"),
                new Area("昌平区台区", "昌平110kV变电站", 480, "正常", 
                    "[[40.20, 116.22], [40.22, 116.22], [40.22, 116.25], [40.20, 116.25], [40.20, 116.22]]"),
                new Area("石景山区台区", "石景山35kV变电站", 340, "正常", 
                    "[[39.89, 116.17], [39.91, 116.17], [39.91, 116.20], [39.89, 116.20], [39.89, 116.17]]"),
                new Area("房山区台区", "房山220kV变电站", 520, "正常", 
                    "[[39.73, 116.12], [39.75, 116.12], [39.75, 116.15], [39.73, 116.15], [39.73, 116.12]]"),
                new Area("门头沟区台区", "门头沟110kV变电站", 290, "正常", 
                    "[[39.93, 116.08], [39.95, 116.08], [39.95, 116.11], [39.93, 116.11], [39.93, 116.08]]"),
                new Area("怀柔区台区", "怀柔35kV变电站", 180, "正常", 
                    "[[40.30, 116.62], [40.32, 116.62], [40.32, 116.65], [40.30, 116.65], [40.30, 116.62]]"),
                new Area("密云区台区", "密云220kV变电站", 220, "正常", 
                    "[[40.36, 116.83], [40.38, 116.83], [40.38, 116.86], [40.36, 116.86], [40.36, 116.83]]"),
                new Area("平谷区台区", "平谷110kV变电站", 260, "正常", 
                    "[[40.13, 117.10], [40.15, 117.10], [40.15, 117.13], [40.13, 117.13], [40.13, 117.10]]"),
                new Area("延庆区台区", "延庆35kV变电站", 150, "正常", 
                    "[[40.45, 115.96], [40.47, 115.96], [40.47, 115.99], [40.45, 115.99], [40.45, 115.96]]")
            };
            
            for (Area a : areas) {
                areaRepository.save(a);
            }
        }
    }
}
