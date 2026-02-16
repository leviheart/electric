package com.electric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BackendApplication - 后端Spring Boot应用的主类
 * 
 * 功能说明：
 * 1. 作为Spring Boot应用的入口点，启动整个后端服务
 * 2. 自动配置Spring Boot的核心功能，包括Web、JPA、H2数据库等
 * 3. 扫描并加载项目中的组件，包括Controller、Service、Repository等
 * 
 * 文件关联：
 * - 配置文件：resources/application.properties 中配置了应用参数和数据库连接
 * - 实体类：model包下的Substation、TransmissionLine、Area等实体类
 * - 控制器：controller包下的RESTful API接口
 * - 服务层：service包下的业务逻辑实现
 * - 数据访问：repository包下的JPA数据访问接口
 * - 数据初始化：config包下的DataInitializer类，用于应用启动时初始化示例数据
 */
@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
        // 启动Spring Boot应用
        SpringApplication.run(BackendApplication.class, args);
    }
}