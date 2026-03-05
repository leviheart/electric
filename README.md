# 电网地理信息系统

一个基于 Vue 3 + Spring Boot 的电网地图管理系统，提供变电站、输电线路的可视化展示与运维管理功能。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端 | Spring Boot 3.2 + Java 17 + Spring Data JPA + Spring Security |
| 数据库 | H2（嵌入式，文件持久化） |
| 前端 | Vue 3 + TypeScript + Vite + Pinia + Vue Router |
| UI | Element Plus |
| 地图 | Mapbox GL |
| 图表 | ECharts |
| 认证 | JWT |

## 环境要求

- **JDK 17+**
- **Node.js 18+**
- **Maven 3.6+**

## 快速启动

### 1. 配置前端环境变量

```bash
cd frontend
cp .env.example .env
```

编辑 `.env` 文件，填入你的 Mapbox Token：

```env
VITE_MAPBOX_TOKEN=你的mapbox_token
VITE_API_BASE_URL=http://localhost:8080
```

> Mapbox Token 获取地址：https://account.mapbox.com/access-tokens/

### 2. 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端启动后访问：
- API 地址：http://localhost:8080
- H2 控制台：http://localhost:8080/h2-console

### 3. 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端启动后访问：http://localhost:5173

## 默认账户

| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 管理员 |
| user | user123 | 普通用户 |

## 功能模块

| 模块 | 功能 |
|------|------|
| 地图展示 | 变电站、输电线路、台区的地理信息可视化 |
| 运维管理 | 巡检任务、缺陷管理、工作票、操作票、停电管理 |
| 分析计算 | 潮流计算、N-1校验、负荷预测、线损分析 |
| 告警系统 | 设备告警、过载告警、故障告警 |
| 数据统计 | 仪表盘、运行数据可视化 |

## 项目结构

```
electric/
├── backend/                    # 后端 Spring Boot 项目
│   ├── src/main/java/com/electric/
│   │   ├── config/            # 配置类
│   │   ├── controller/        # 控制器层
│   │   ├── service/           # 服务层
│   │   ├── repository/        # 数据访问层
│   │   ├── model/             # 实体类
│   │   └── security/          # 安全认证
│   └── pom.xml
│
├── frontend/                   # 前端 Vue 3 项目
│   ├── src/
│   │   ├── api/               # API 请求封装
│   │   ├── components/        # 组件
│   │   ├── views/             # 页面
│   │   ├── stores/            # 状态管理
│   │   └── router/            # 路由配置
│   └── package.json
│
└── README.md
```

## 文档

- [功能模块说明](docs/FEATURES.md) - 各功能模块详细介绍
- [开发指南](docs/DEVELOPMENT.md) - 环境配置、开发指南、常见问题
- [部署指南](docs/DEPLOYMENT.md) - 生产环境部署参考

## 常见问题

### Q: 地图无法显示？

检查 `.env` 文件中的 `VITE_MAPBOX_TOKEN` 是否正确配置。Token 获取详见 [开发指南](docs/DEVELOPMENT.md#mapbox-token-获取)。

### Q: 登录失败？

确保后端已启动，检查后端控制台是否有错误信息。首次启动会自动初始化默认用户数据。

### Q: H2 控制台连接失败？

H2 控制台地址：http://localhost:8080/h2-console
JDBC URL：`jdbc:h2:file:./data/electricdb`
用户名：`sa`，密码为空。

### Q: 端口被占用？

修改后端端口：编辑 `backend/src/main/resources/application.properties`：
```properties
server.port=8081
```

同时修改前端 `.env` 文件：
```env
VITE_API_BASE_URL=http://localhost:8081
```

### Q: 更多问题？

查看 [开发指南](docs/DEVELOPMENT.md) 获取更多帮助。
