# 开发指南

本文档提供详细的开发环境配置和开发指南。

## 目录

- [环境配置](#环境配置)
- [Mapbox Token 获取](#mapbox-token-获取)
- [后端开发](#后端开发)
- [前端开发](#前端开发)
- [数据库操作](#数据库操作)
- [前后端联调](#前后端联调)
- [常见问题](#常见问题)

---

## 环境配置

### JDK 安装

1. 下载 JDK 17：https://adoptium.net/
2. 安装后验证：
   ```bash
   java -version
   # 输出应包含：openjdk version "17.x.x"
   ```

### Node.js 安装

1. 下载 Node.js LTS：https://nodejs.org/
2. 安装后验证：
   ```bash
   node -v
   # 输出应类似：v18.x.x 或更高
   npm -v
   ```

### Maven 安装

1. 下载 Maven：https://maven.apache.org/download.cgi
2. 配置环境变量 `MAVEN_HOME`
3. 验证：
   ```bash
   mvn -v
   # 输出应包含：Apache Maven 3.x.x
   ```

---

## Mapbox Token 获取

Mapbox 是地图服务提供商，本项目使用其地图展示功能。

### 步骤一：注册账号

1. 访问 https://account.mapbox.com/
2. 点击 "Sign up" 注册账号
3. 填写邮箱、用户名、密码
4. 验证邮箱

### 步骤二：获取 Token

1. 登录后进入 https://account.mapbox.com/access-tokens/
2. 你会看到一个默认的 "Default public token"
3. 点击右侧的复制按钮复制 Token

### 步骤三：配置 Token

编辑 `frontend/.env` 文件：

```env
VITE_MAPBOX_TOKEN=pk.eyJ1Ijoiyour-username...
VITE_API_BASE_URL=http://localhost:8080
```

### Token 权限说明

默认的 public token 已包含以下权限：
- Maps: Read maps
- Styles: Read styles
- Datasets: Read datasets

如需更多权限，可创建新的 Token 并选择所需权限。

---

## 后端开发

### 项目结构

```
backend/src/main/java/com/electric/
├── config/           # 配置类
│   ├── SecurityConfig.java      # Spring Security 配置
│   ├── CorsConfig.java          # 跨域配置
│   ├── WebSocketConfig.java     # WebSocket 配置
│   └── DataInitializer.java     # 数据初始化
├── controller/       # 控制器层 - 处理 HTTP 请求
├── service/          # 服务层 - 业务逻辑
├── repository/       # 数据访问层 - 数据库操作
├── model/            # 实体类 - 数据库表映射
├── dto/              # 数据传输对象
├── security/         # 安全相关
│   ├── JwtUtil.java             # JWT 工具类
│   └── JwtAuthenticationFilter.java
└── exception/        # 异常处理
```

### 启动后端

```bash
cd backend
mvn spring-boot:run
```

### 数据库访问

H2 控制台：http://localhost:8080/h2-console

连接参数：
- JDBC URL: `jdbc:h2:file:./data/electricdb`
- 用户名: `sa`
- 密码: (空)

### 完整示例：添加新功能模块

以下示例演示如何添加一个完整的"设备类型"管理模块。

#### 步骤 1：创建实体类

在 `model/` 目录创建 `DeviceType.java`：

```java
package com.electric.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "device_types")
public class DeviceType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "类型名称不能为空")
    @Column(nullable = false, unique = true)
    private String name;
    
    private String description;
    
    @Column(name = "voltage_level")
    private String voltageLevel;
    
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = createdAt;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
    
    // 构造函数
    public DeviceType() {}
    
    public DeviceType(String name, String description, String voltageLevel) {
        this.name = name;
        this.description = description;
        this.voltageLevel = voltageLevel;
    }
    
    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getVoltageLevel() { return voltageLevel; }
    public void setVoltageLevel(String voltageLevel) { this.voltageLevel = voltageLevel; }
    
    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(java.time.LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(java.time.LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
```

#### 步骤 2：创建 Repository

在 `repository/` 目录创建 `DeviceTypeRepository.java`：

```java
package com.electric.repository;

import com.electric.model.DeviceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
    
    // 根据名称查找
    Optional<DeviceType> findByName(String name);
    
    // 根据电压等级查找
    List<DeviceType> findByVoltageLevel(String voltageLevel);
    
    // 检查名称是否存在
    boolean existsByName(String name);
    
    // 根据名称模糊查询
    List<DeviceType> findByNameContaining(String keyword);
}
```

#### 步骤 3：创建 Service

在 `service/` 目录创建 `DeviceTypeService.java`：

```java
package com.electric.service;

import com.electric.model.DeviceType;
import java.util.List;
import java.util.Optional;

public interface DeviceTypeService {
    List<DeviceType> findAll();
    Optional<DeviceType> findById(Long id);
    Optional<DeviceType> findByName(String name);
    DeviceType save(DeviceType deviceType);
    DeviceType update(Long id, DeviceType deviceType);
    void deleteById(Long id);
    List<DeviceType> search(String keyword);
}
```

在 `service/impl/` 目录创建 `DeviceTypeServiceImpl.java`：

```java
package com.electric.service.impl;

import com.electric.exception.BusinessException;
import com.electric.model.DeviceType;
import com.electric.repository.DeviceTypeRepository;
import com.electric.service.DeviceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeviceTypeServiceImpl implements DeviceTypeService {
    
    @Autowired
    private DeviceTypeRepository deviceTypeRepository;
    
    @Override
    public List<DeviceType> findAll() {
        return deviceTypeRepository.findAll();
    }
    
    @Override
    public Optional<DeviceType> findById(Long id) {
        return deviceTypeRepository.findById(id);
    }
    
    @Override
    public Optional<DeviceType> findByName(String name) {
        return deviceTypeRepository.findByName(name);
    }
    
    @Override
    public DeviceType save(DeviceType deviceType) {
        // 检查名称是否已存在
        if (deviceTypeRepository.existsByName(deviceType.getName())) {
            throw new BusinessException("设备类型名称已存在: " + deviceType.getName());
        }
        return deviceTypeRepository.save(deviceType);
    }
    
    @Override
    public DeviceType update(Long id, DeviceType deviceType) {
        DeviceType existing = deviceTypeRepository.findById(id)
            .orElseThrow(() -> new BusinessException("设备类型不存在: " + id));
        
        // 如果修改了名称，检查新名称是否已存在
        if (!existing.getName().equals(deviceType.getName()) 
            && deviceTypeRepository.existsByName(deviceType.getName())) {
            throw new BusinessException("设备类型名称已存在: " + deviceType.getName());
        }
        
        existing.setName(deviceType.getName());
        existing.setDescription(deviceType.getDescription());
        existing.setVoltageLevel(deviceType.getVoltageLevel());
        
        return deviceTypeRepository.save(existing);
    }
    
    @Override
    public void deleteById(Long id) {
        if (!deviceTypeRepository.existsById(id)) {
            throw new BusinessException("设备类型不存在: " + id);
        }
        deviceTypeRepository.deleteById(id);
    }
    
    @Override
    public List<DeviceType> search(String keyword) {
        return deviceTypeRepository.findByNameContaining(keyword);
    }
}
```

#### 步骤 4：创建 Controller

在 `controller/` 目录创建 `DeviceTypeController.java`：

```java
package com.electric.controller;

import com.electric.model.ApiResponse;
import com.electric.model.DeviceType;
import com.electric.service.DeviceTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device-types")
@CrossOrigin(origins = "*")
public class DeviceTypeController {
    
    @Autowired
    private DeviceTypeService deviceTypeService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<DeviceType>>> getAll() {
        List<DeviceType> types = deviceTypeService.findAll();
        return ResponseEntity.ok(ApiResponse.success(types));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceType>> getById(@PathVariable Long id) {
        return deviceTypeService.findById(id)
            .map(type -> ResponseEntity.ok(ApiResponse.success(type)))
            .orElse(ResponseEntity.ok(ApiResponse.error("设备类型不存在")));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DeviceType>>> search(@RequestParam String keyword) {
        List<DeviceType> types = deviceTypeService.search(keyword);
        return ResponseEntity.ok(ApiResponse.success(types));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<DeviceType>> create(@Valid @RequestBody DeviceType deviceType) {
        DeviceType saved = deviceTypeService.save(deviceType);
        return ResponseEntity.ok(ApiResponse.success(saved, "创建成功"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DeviceType>> update(
            @PathVariable Long id, 
            @Valid @RequestBody DeviceType deviceType) {
        DeviceType updated = deviceTypeService.update(id, deviceType);
        return ResponseEntity.ok(ApiResponse.success(updated, "更新成功"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        deviceTypeService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "删除成功"));
    }
}
```

#### 步骤 5：测试 API

启动后端后，使用以下方式测试：

```bash
# 获取所有设备类型
curl http://localhost:8080/api/device-types

# 创建设备类型
curl -X POST http://localhost:8080/api/device-types \
  -H "Content-Type: application/json" \
  -d '{"name":"变压器","description":"电力变压器","voltageLevel":"220kV"}'

# 查询单个
curl http://localhost:8080/api/device-types/1

# 更新
curl -X PUT http://localhost:8080/api/device-types/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"主变压器","description":"主变压器设备","voltageLevel":"500kV"}'

# 删除
curl -X DELETE http://localhost:8080/api/device-types/1
```

---

## 前端开发

### 项目结构

```
frontend/src/
├── api/              # API 请求封装
├── components/       # 组件
│   ├── analysis/     # 分析计算组件
│   ├── operation/    # 运维管理组件
│   ├── resource/     # 资源管理组件
│   └── common/       # 通用组件
├── views/            # 页面视图
├── stores/           # Pinia 状态管理
├── router/           # 路由配置
├── types/            # TypeScript 类型定义
├── utils/            # 工具函数
├── config/           # 配置文件
└── data/             # 静态数据
```

### 启动前端

```bash
cd frontend
npm install
npm run dev
```

### 完整示例：添加新页面

以下示例演示如何添加一个完整的"设备类型管理"页面。

#### 步骤 1：创建 API 封装

在 `api/` 目录创建 `deviceType.ts`：

```typescript
import request from './request'
import type { ApiResponse } from './request'

export interface DeviceType {
  id?: number
  name: string
  description?: string
  voltageLevel?: string
  createdAt?: string
  updatedAt?: string
}

export const deviceTypeApi = {
  getAll: () => 
    request.get<ApiResponse<DeviceType[]>>('/api/device-types'),
  
  getById: (id: number) => 
    request.get<ApiResponse<DeviceType>>(`/api/device-types/${id}`),
  
  search: (keyword: string) => 
    request.get<ApiResponse<DeviceType[]>>('/api/device-types/search', { 
      params: { keyword } 
    }),
  
  create: (data: DeviceType) => 
    request.post<ApiResponse<DeviceType>>('/api/device-types', data),
  
  update: (id: number, data: DeviceType) => 
    request.put<ApiResponse<DeviceType>>(`/api/device-types/${id}`, data),
  
  delete: (id: number) => 
    request.delete<ApiResponse<void>>(`/api/device-types/${id}`)
}
```

#### 步骤 2：创建页面组件

在 `views/` 目录创建 `DeviceTypeView.vue`：

```vue
<template>
  <TechPage title="设备类型管理">
    <template #extra>
      <TechButton type="primary" icon="Plus" @click="handleAdd">新增</TechButton>
      <TechButton icon="Refresh" @click="loadData">刷新</TechButton>
    </template>
    
    <div class="search-bar">
      <el-input 
        v-model="searchKeyword" 
        placeholder="搜索设备类型..." 
        prefix-icon="Search" 
        clearable 
        @keyup.enter="handleSearch"
        class="search-input" 
      />
      <TechButton type="primary" @click="handleSearch">搜索</TechButton>
    </div>
    
    <TechTable
      :columns="columns"
      :data="tableData"
      :loading="loading"
      :show-pagination="true"
      :total="total"
      :current-page="currentPage"
      :page-size="pageSize"
      @page-change="handlePageChange"
    >
      <template #actions="{ row }">
        <div class="action-buttons">
          <button class="action-btn edit" @click="handleEdit(row)">
            <el-icon><Edit /></el-icon>
          </button>
          <button class="action-btn delete" @click="handleDelete(row)">
            <el-icon><Delete /></el-icon>
          </button>
        </div>
      </template>
    </TechTable>
    
    <el-dialog 
      v-model="dialogVisible" 
      :title="isEdit ? '编辑设备类型' : '新增设备类型'"
      width="500px"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="类型名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入类型名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="电压等级" prop="voltageLevel">
          <el-select v-model="form.voltageLevel" placeholder="请选择电压等级">
            <el-option label="1000kV" value="1000kV" />
            <el-option label="500kV" value="500kV" />
            <el-option label="220kV" value="220kV" />
            <el-option label="110kV" value="110kV" />
            <el-option label="35kV" value="35kV" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </TechPage>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import TechPage from '@/components/common/TechPage.vue'
import TechTable from '@/components/common/TechTable.vue'
import TechButton from '@/components/common/TechButton.vue'
import { deviceTypeApi, type DeviceType } from '@/api/deviceType'

const loading = ref(false)
const submitting = ref(false)
const tableData = ref<DeviceType[]>([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchKeyword = ref('')

const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const form = reactive<DeviceType>({
  name: '',
  description: '',
  voltageLevel: ''
})

const columns = [
  { prop: 'id', label: 'ID', width: '80px' },
  { prop: 'name', label: '类型名称', width: '150px' },
  { prop: 'description', label: '描述' },
  { prop: 'voltageLevel', label: '电压等级', width: '100px' },
  { prop: 'createdAt', label: '创建时间', width: '160px' },
  { prop: 'actions', label: '操作', width: '120px', type: 'slot' }
]

const rules: FormRules = {
  name: [{ required: true, message: '请输入类型名称', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const res = await deviceTypeApi.getAll()
    const allData = res.data.data || []
    total.value = allData.length
    const start = (currentPage.value - 1) * pageSize.value
    tableData.value = allData.slice(start, start + pageSize.value)
  } catch (e) {
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = async () => {
  if (!searchKeyword.value) {
    loadData()
    return
  }
  loading.value = true
  try {
    const res = await deviceTypeApi.search(searchKeyword.value)
    tableData.value = res.data.data || []
    total.value = tableData.value.length
  } catch (e) {
    ElMessage.error('搜索失败')
  } finally {
    loading.value = false
  }
}

const handleAdd = () => {
  isEdit.value = false
  Object.assign(form, { name: '', description: '', voltageLevel: '' })
  dialogVisible.value = true
}

const handleEdit = (row: DeviceType) => {
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

const handleDelete = async (row: DeviceType) => {
  try {
    await ElMessageBox.confirm('确定要删除该设备类型吗？', '提示', { type: 'warning' })
    await deviceTypeApi.delete(row.id!)
    ElMessage.success('删除成功')
    loadData()
  } catch (e: any) {
    if (e !== 'cancel') ElMessage.error('删除失败')
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    submitting.value = true
    try {
      if (isEdit.value) {
        await deviceTypeApi.update(form.id!, form)
        ElMessage.success('更新成功')
      } else {
        await deviceTypeApi.create(form)
        ElMessage.success('创建成功')
      }
      dialogVisible.value = false
      loadData()
    } catch (e) {
      ElMessage.error(isEdit.value ? '更新失败' : '创建失败')
    } finally {
      submitting.value = false
    }
  })
}

const handlePageChange = (page: number) => {
  currentPage.value = page
  loadData()
}

onMounted(() => loadData())
</script>

<style scoped>
.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}
.search-input {
  width: 300px;
}
.action-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
}
.action-btn {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  border: 1px solid transparent;
  cursor: pointer;
  background: transparent;
  color: #6a7a8a;
}
.action-btn:hover {
  transform: scale(1.1);
}
.action-btn.edit:hover {
  color: #ffc800;
  background: rgba(255, 200, 0, 0.1);
}
.action-btn.delete:hover {
  color: #ff4081;
  background: rgba(255, 64, 129, 0.1);
}
</style>
```

#### 步骤 3：添加路由

在 `router/index.ts` 添加路由：

```typescript
{
  path: '/device-types',
  name: 'DeviceTypes',
  component: () => import('../views/DeviceTypeView.vue'),
  meta: {
    title: '设备类型管理',
    requiresAuth: true
  }
}
```

#### 步骤 4：添加导航菜单（可选）

在 `AppHeader.vue` 或导航组件中添加菜单项。

---

## 数据库操作

### H2 数据库控制台

访问地址：http://localhost:8080/h2-console

连接参数：
- JDBC URL: `jdbc:h2:file:./data/electricdb`
- 用户名: `sa`
- 密码: (空)

### 常用 SQL 示例

```sql
-- 查看所有表
SHOW TABLES;

-- 查看表结构
DESCRIBE substation;

-- 查询数据
SELECT * FROM substation LIMIT 10;

-- 统计数据
SELECT voltage_level, COUNT(*) as count 
FROM substation 
GROUP BY voltage_level;

-- 更新数据
UPDATE substation SET status = '运行中' WHERE id = 1;

-- 删除数据
DELETE FROM substation WHERE id = 1;
```

### JPA 查询方法命名规则

| 关键字 | 示例 | 生成的 SQL |
|--------|------|-----------|
| `findBy` | `findByName` | `WHERE name = ?` |
| `And` | `findByNameAndStatus` | `WHERE name = ? AND status = ?` |
| `Or` | `findByNameOrStatus` | `WHERE name = ? OR status = ?` |
| `Between` | `findByLoadRateBetween` | `WHERE load_rate BETWEEN ? AND ?` |
| `LessThan` | `findByLoadRateLessThan` | `WHERE load_rate < ?` |
| `GreaterThan` | `findByLoadRateGreaterThan` | `WHERE load_rate > ?` |
| `Like` | `findByNameLike` | `WHERE name LIKE ?` |
| `Containing` | `findByNameContaining` | `WHERE name LIKE %?%` |
| `In` | `findByStatusIn` | `WHERE status IN (?)` |
| `OrderBy` | `findByStatusOrderByName` | `WHERE status = ? ORDER BY name` |

### 自定义查询

使用 `@Query` 注解：

```java
@Query("SELECT s FROM Substation s WHERE s.loadRate > :threshold")
List<Substation> findHighLoadSubstations(@Param("threshold") double threshold);

@Query(value = "SELECT * FROM substation WHERE voltage_level = :level", nativeQuery = true)
List<Substation> findByVoltageLevelNative(@Param("level") String level);
```

---

## 前后端联调

### 跨域配置

后端已配置 CORS，允许所有来源访问。如需限制，修改 `CorsConfig.java`：

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("http://localhost:5173")  // 限制前端地址
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

### API 请求封装

前端使用 axios 封装请求，位于 `api/request.ts`：

```typescript
import axios from 'axios'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  timeout: 10000
})

// 请求拦截器 - 添加 Token
request.interceptors.request.use(config => {
  const token = localStorage.getItem('electric_grid_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 响应拦截器 - 处理错误
request.interceptors.response.use(
  response => response,
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('electric_grid_token')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

export default request
```

### 调试技巧

1. **后端日志**：查看控制台输出，SQL 语句已开启显示
2. **前端调试**：使用浏览器开发者工具 Network 面板查看请求
3. **Vue DevTools**：安装 Vue.js devtools 浏览器插件调试组件状态
4. **断点调试**：
   - 后端：在 IDE 中设置断点
   - 前端：在浏览器 Sources 面板设置断点

### 常见联调问题

| 问题 | 原因 | 解决方案 |
|------|------|----------|
| 401 Unauthorized | Token 过期或无效 | 重新登录获取新 Token |
| 404 Not Found | API 路径错误 | 检查 Controller 注解路径 |
| 400 Bad Request | 参数格式错误 | 检查请求体格式和字段名 |
| 500 Internal Error | 后端异常 | 查看后端控制台错误日志 |
| CORS Error | 跨域配置问题 | 检查 CorsConfig 配置 |

---

## 常见问题

### 后端启动失败

**问题**: 端口 8080 被占用

**解决**: 修改 `backend/src/main/resources/application.properties`:
```properties
server.port=8081
```

同时修改前端 `.env` 文件：
```env
VITE_API_BASE_URL=http://localhost:8081
```

### 前端启动失败

**问题**: npm install 报错

**解决**: 
```bash
# 清除缓存
npm cache clean --force
# 删除 node_modules
rm -rf node_modules
# 重新安装
npm install
```

### 地图显示空白

**问题**: 地图区域空白，无内容显示

**解决**:
1. 检查 `.env` 文件中 `VITE_MAPBOX_TOKEN` 是否正确
2. 检查浏览器控制台是否有 Token 相关错误
3. 确认 Token 未过期且有足够的 API 调用额度

### 登录后立即退出

**问题**: 登录成功后页面跳转又回到登录页

**解决**:
1. 检查浏览器控制台是否有错误
2. 清除浏览器 localStorage：`localStorage.clear()`
3. 重新登录

### 数据库数据丢失

**问题**: 重启后端后数据消失

**解决**: 
确认 `application.properties` 使用的是文件模式：
```properties
spring.datasource.url=jdbc:h2:file:./data/electricdb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
```

### 热更新不生效

**问题**: 修改代码后页面不更新

**解决**:
- 前端：确保使用 `npm run dev` 启动（Vite 自带热更新）
- 后端：添加 `spring-boot-devtools` 依赖（已添加）

---

## 开发工具推荐

### IDE

- 后端：IntelliJ IDEA（推荐）、Eclipse
- 前端：VS Code（推荐）

### VS Code 插件

- Vue - Official (Vue Language Features)
- TypeScript Vue Plugin (Volar)
- ESLint
- Prettier

### 浏览器插件

- Vue.js devtools
- Redux DevTools（用于 Pinia 调试）

### API 测试工具

- Postman
- Apifox
- curl 命令行
