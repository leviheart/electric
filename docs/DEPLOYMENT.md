# 部署指南

本文档提供生产环境部署的基本指南。

## 目录

- [打包构建](#打包构建)
- [生产环境配置](#生产环境配置)
- [安全注意事项](#安全注意事项)
- [未来扩展](#未来扩展)

---

## 打包构建

### 后端打包

```bash
cd backend
mvn clean package -DskipTests
```

打包完成后，JAR 文件位于 `target/backend-1.0.0-SNAPSHOT.jar`

运行方式：
```bash
java -jar target/backend-1.0.0-SNAPSHOT.jar
```

### 前端打包

```bash
cd frontend
npm run build
```

打包完成后，静态文件位于 `dist/` 目录。

部署方式：
- 使用 Nginx 托管静态文件
- 或使用 `npm run preview` 预览打包结果

---

## 生产环境配置

### 后端配置

创建 `application-prod.properties`：

```properties
# 服务端口
server.port=8080

# 数据库（建议切换到 MySQL/PostgreSQL）
spring.datasource.url=jdbc:mysql://localhost:3306/electric_grid
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA 配置
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# JWT 配置（使用环境变量）
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# 禁用 H2 控制台
spring.h2.console.enabled=false
```

启动命令：
```bash
java -jar backend-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod
```

### 前端配置

创建 `.env.production`：

```env
VITE_MAPBOX_TOKEN=your_production_token
VITE_API_BASE_URL=https://your-domain.com
```

### Nginx 配置示例

```nginx
server {
    listen 80;
    server_name your-domain.com;

    # 前端静态文件
    location / {
        root /var/www/electric/dist;
        try_files $uri $uri/ /index.html;
    }

    # 后端 API 代理
    location /api {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    # WebSocket 代理
    location /ws {
        proxy_pass http://localhost:8080;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
```

---

## 安全注意事项

### 必须修改的配置

| 配置项 | 说明 |
|--------|------|
| JWT 密钥 | 使用强密码，通过环境变量配置 |
| 数据库密码 | 使用强密码，不要硬编码 |
| H2 控制台 | 生产环境必须禁用 |
| CORS 配置 | 限制允许的域名 |

### 环境变量

推荐使用环境变量管理敏感配置：

```bash
# Linux/Mac
export JWT_SECRET=your-very-long-and-secure-secret-key
export DB_PASSWORD=your-database-password

# Windows PowerShell
$env:JWT_SECRET="your-very-long-and-secure-secret-key"
$env:DB_PASSWORD="your-database-password"
```

### HTTPS 配置

生产环境强烈建议启用 HTTPS：
- 使用 Let's Encrypt 免费证书
- 或使用云服务商提供的 SSL 证书

---

## 未来扩展

以下内容待实际部署时补充：

- [ ] Docker 容器化部署
- [ ] Docker Compose 编排
- [ ] CI/CD 自动化部署
- [ ] 数据库备份策略
- [ ] 监控告警配置
- [ ] 日志收集方案

---

## 常见问题

### Q: 如何切换数据库？

1. 添加 MySQL/PostgreSQL 驱动依赖到 `pom.xml`
2. 修改 `application-prod.properties` 中的数据源配置
3. 创建数据库并执行初始化脚本

### Q: 如何查看日志？

```bash
# 查看实时日志
tail -f logs/spring.log

# 或使用 journalctl（如果使用 systemd）
journalctl -u electric-grid -f
```

### Q: 如何备份数据？

```bash
# MySQL 备份
mysqldump -u username -p electric_grid > backup.sql

# PostgreSQL 备份
pg_dump electric_grid > backup.sql
```
