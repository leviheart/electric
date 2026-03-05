<template>
  <!--
    登录页面容器
    - 使用 flex 布局实现垂直水平居中
    - 全屏高度，带深色渐变背景
  -->
  <div class="login-view">
    <!--
      登录卡片
      - 包含标题、表单、提示信息
      - 半透明背景，带发光边框效果
    -->
    <div class="login-card">
      <!-- 系统标题 -->
      <h1>电网地图系统</h1>
      
      <!-- 页面副标题 -->
      <h2>用户登录</h2>
      
      <!--
        登录表单
        - 使用 Element Plus 的 el-form 组件
        - ref="formRef": 获取表单实例，用于表单验证
        - :model="form": 绑定表单数据对象
        - :rules="rules": 绑定表单验证规则
        - class="login-form": 应用自定义样式
      -->
      <el-form ref="formRef" :model="form" :rules="rules" class="login-form">
        <!--
          用户名输入框
          - prop="username": 指定验证规则的字段名
        -->
        <el-form-item prop="username">
          <!--
            el-input 组件
            - v-model="form.username": 双向绑定用户名
            - placeholder: 输入框提示文字
            - prefix-icon="User": 前置图标（Element Plus 图标）
            - size="large": 大尺寸输入框
          -->
          <el-input
            v-model="form.username"
            placeholder="用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <!--
          密码输入框
          - prop="password": 指定验证规则的字段名
        -->
        <el-form-item prop="password">
          <!--
            el-input 组件（密码类型）
            - type="password": 密码输入框，字符显示为圆点
            - show-password: 显示密码切换按钮
            - @keyup.enter="handleLogin": 按下回车键时触发登录
          -->
          <el-input
            v-model="form.password"
            type="password"
            placeholder="密码"
            prefix-icon="Lock"
            size="large"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <!-- 登录按钮区域 -->
        <el-form-item>
          <!--
            登录按钮
            - type="primary": 主要按钮样式（蓝色）
            - size="large": 大尺寸按钮
            - :loading="loading": 显示加载状态（防止重复提交）
            - @click="handleLogin": 点击时调用登录方法
          -->
          <el-button
            type="primary"
            size="large"
            :loading="loading"
            class="login-button"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
      
      <!-- 底部提示信息 -->
      <div class="login-footer">
        <p>默认账户：admin / admin123</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * LoginView.vue - 登录页面
 * 
 * 功能说明：
 * 提供用户登录界面，实现以下功能：
 * 1. 用户名密码输入
 * 2. 表单验证（必填项校验）
 * 3. 登录请求发送
 * 4. Token 存储和页面跳转
 * 
 * 交互流程：
 * 1. 用户输入用户名和密码
 * 2. 点击登录按钮或按回车键
 * 3. 验证表单是否填写完整
 * 4. 调用后端登录接口
 * 5. 成功后保存 Token 并跳转到首页
 * 6. 失败则显示错误提示
 * 
 * 文件关联：
 * - api/auth.ts: 调用登录 API
 * - stores/authStore.ts: 存储认证状态
 * - router/index.ts: 登录成功后跳转
 */

// ==================== 导入依赖 ====================

/**
 * Vue 响应式 API
 * - ref: 创建响应式引用，用于基本类型和对象
 * - reactive: 创建响应式对象
 */
import { ref, reactive } from 'vue'

/**
 * Vue Router
 * - useRouter: 获取路由实例，用于编程式导航
 */
import { useRouter } from 'vue-router'

/**
 * Element Plus 组件
 * - ElMessage: 消息提示组件
 */
import { ElMessage } from 'element-plus'

/**
 * Element Plus 类型定义
 * - FormInstance: 表单实例类型
 * - FormRules: 表单验证规则类型
 */
import type { FormInstance, FormRules } from 'element-plus'

/**
 * 登录 API
 * - login: 发送登录请求
 */
import { login } from '../api/auth'

/**
 * 认证状态管理
 * - useAuthStore: 获取认证 store 实例
 */
import { useAuthStore } from '../stores/authStore'

// ==================== 初始化 ====================

/**
 * 路由实例
 * 用于登录成功后跳转到首页
 */
const router = useRouter()

/**
 * 认证状态管理实例
 * 用于存储 Token 和用户信息
 */
const authStore = useAuthStore()

// ==================== 响应式数据 ====================

/**
 * 表单实例引用
 * - 类型: FormInstance | null
 * - 用途: 调用表单验证方法
 * - 使用: formRef.value?.validate()
 */
const formRef = ref<FormInstance>()

/**
 * 加载状态
 * - 类型: boolean
 * - 用途: 控制登录按钮的加载状态，防止重复提交
 * - true: 正在登录，显示加载动画
 * - false: 登录完成或未开始
 */
const loading = ref(false)

/**
 * 表单数据对象
 * - 使用 reactive 创建响应式对象
 * - 字段:
 *   - username: 用户名
 *   - password: 密码
 */
const form = reactive({
  username: '',
  password: ''
})

/**
 * 表单验证规则
 * - 类型: FormRules
 * - 字段验证:
 *   - username: 必填，失去焦点时触发验证
 *   - password: 必填，失去焦点时触发验证
 */
const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// ==================== 方法 ====================

/**
 * 处理登录
 * 
 * 执行流程：
 * 1. 验证表单是否填写完整
 * 2. 设置加载状态
 * 3. 调用登录 API
 * 4. 处理响应结果
 * 5. 成功则保存 Token 并跳转
 * 6. 失败则显示错误信息
 * 
 * @returns {Promise<void>}
 */
const handleLogin = async () => {
  // 检查表单引用是否存在
  if (!formRef.value) return
  
  // 验证表单
  // - valid: 布尔值，表示表单是否通过验证
  // - 使用 async/await 处理异步验证
  await formRef.value.validate(async (valid) => {
    // 如果验证失败，直接返回
    if (!valid) return
    
    // 设置加载状态，显示加载动画
    loading.value = true
    
    try {
      // 调用登录 API
      // - 传入用户名和密码
      // - 返回包含 Token 的响应对象
      const response = await login({
        username: form.username,
        password: form.password
      })
      
      // 检查响应状态
      // - code === 200: 请求成功
      // - response.data: 包含 Token 和用户信息
      if (response.code === 200 && response.data) {
        // 保存 Token 到 store 和 localStorage
        authStore.setToken(response.data.token)
        
        // 保存用户信息到 store 和 localStorage
        authStore.setUser({
          username: response.data.username,
          role: response.data.role
        })
        
        // 显示成功提示
        ElMessage.success('登录成功')
        
        // 设置标记，登录后显示引导
        console.log('登录成功，设置 just_logged_in 标记')
        sessionStorage.setItem('just_logged_in', 'true')
        
        // 跳转到首页
        router.push('/')
      } else {
        // 显示失败提示
        ElMessage.error(response.message || '登录失败')
      }
    } catch (error: any) {
      // 捕获异常，显示错误信息
      // - error.message: 网络错误或服务器错误信息
      ElMessage.error(error.message || '登录失败，请检查网络连接')
    } finally {
      // 无论成功或失败，都关闭加载状态
      loading.value = false
    }
  })
}
</script>

<style scoped>
/*
 * 登录页面容器样式
 * - 全屏高度，flex 布局居中
 * - 深色背景，内边距
 */
.login-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}

/*
 * 登录卡片样式
 * - 半透明黑色背景
 * - 发光边框效果
 * - 圆角阴影
 */
.login-card {
  background: rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 40px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 0 30px rgba(0, 240, 255, 0.2);
}

/*
 * 系统标题样式
 * - 青色发光文字
 * - 居中显示
 */
.login-card h1 {
  color: #00f0ff;
  text-align: center;
  margin-bottom: 10px;
  font-size: 1.8rem;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

/*
 * 副标题样式
 * - 灰色文字
 * - 居中显示
 */
.login-card h2 {
  color: #a0a0a0;
  text-align: center;
  margin-bottom: 30px;
  font-size: 1.2rem;
  font-weight: normal;
}

/*
 * 表单样式
 * - 全宽显示
 */
.login-form {
  width: 100%;
}

/*
 * 登录按钮样式
 * - 全宽显示
 * - 渐变背景
 */
.login-button {
  width: 100%;
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
  height: 45px;
  font-size: 1rem;
}

/*
 * 登录按钮悬停效果
 * - 发光阴影
 */
.login-button:hover {
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.5);
}

/*
 * 底部提示区域样式
 * - 居中显示
 * - 上边距
 */
.login-footer {
  margin-top: 20px;
  text-align: center;
}

/*
 * 提示文字样式
 * - 灰色小字
 */
.login-footer p {
  color: #a0a0a0;
  font-size: 0.9rem;
}

/*
 * Element Plus 输入框样式覆盖
 * - 深色背景
 * - 发光边框
 */
:deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
  box-shadow: none;
}

/*
 * 输入框悬停效果
 */
:deep(.el-input__wrapper:hover) {
  border-color: rgba(0, 240, 255, 0.5);
}

/*
 * 输入框聚焦效果
 */
:deep(.el-input__wrapper.is-focus) {
  border-color: #00f0ff;
}

/*
 * 输入文字样式
 */
:deep(.el-input__inner) {
  color: #e0e0e0;
}

/*
 * 占位符文字样式
 */
:deep(.el-input__inner::placeholder) {
  color: #606060;
}
</style>
