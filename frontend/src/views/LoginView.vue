<template>
  <div class="login-view">
    <div class="login-card">
      <h1>电网地图系统</h1>
      <h2>用户登录</h2>
      
      <el-form ref="formRef" :model="form" :rules="rules" class="login-form">
        <el-form-item prop="username">
          <el-input
            v-model="form.username"
            placeholder="用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="password">
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
        
        <el-form-item>
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
 * 提供用户登录界面
 * 调用后端认证接口获取 JWT Token
 * 存储 Token 到 localStorage
 * 
 * 文件关联：
 * - api/auth.ts: 认证 API
 * - stores/authStore.ts: 认证状态管理
 * - router/index.ts: 路由跳转
 */

import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { login } from '../api/auth'
import { useAuthStore } from '../stores/authStore'

const router = useRouter()
const authStore = useAuthStore()

const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive({
  username: '',
  password: ''
})

const rules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  if (!formRef.value) return
  
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    
    try {
      const response = await login({
        username: form.username,
        password: form.password
      })
      
      if (response.code === 200 && response.data) {
        authStore.setToken(response.data.token)
        authStore.setUser({
          username: response.data.username,
          role: response.data.role
        })
        
        ElMessage.success('登录成功')
        router.push('/')
      } else {
        ElMessage.error(response.message || '登录失败')
      }
    } catch (error: any) {
      ElMessage.error(error.message || '登录失败，请检查网络连接')
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.login-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}

.login-card {
  background: rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 40px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 0 30px rgba(0, 240, 255, 0.2);
}

.login-card h1 {
  color: #00f0ff;
  text-align: center;
  margin-bottom: 10px;
  font-size: 1.8rem;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

.login-card h2 {
  color: #a0a0a0;
  text-align: center;
  margin-bottom: 30px;
  font-size: 1.2rem;
  font-weight: normal;
}

.login-form {
  width: 100%;
}

.login-button {
  width: 100%;
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
  height: 45px;
  font-size: 1rem;
}

.login-button:hover {
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.5);
}

.login-footer {
  margin-top: 20px;
  text-align: center;
}

.login-footer p {
  color: #a0a0a0;
  font-size: 0.9rem;
}

:deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
  box-shadow: none;
}

:deep(.el-input__wrapper:hover) {
  border-color: rgba(0, 240, 255, 0.5);
}

:deep(.el-input__wrapper.is-focus) {
  border-color: #00f0ff;
}

:deep(.el-input__inner) {
  color: #e0e0e0;
}

:deep(.el-input__inner::placeholder) {
  color: #606060;
}
</style>
