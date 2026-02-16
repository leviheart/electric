<template>
  <div class="app-container">
    <div class="grid-overlay" :class="{ 'no-grid': !settingsStore.showGrid }"></div>
    <nav class="app-nav">
      <div class="nav-left">
        <router-link to="/" class="nav-link" active-class="active">首页</router-link>
        <router-link to="/about" class="nav-link" active-class="active">关于</router-link>
        <router-link to="/settings" class="nav-link" active-class="active">设置</router-link>
      </div>
      <div class="nav-right">
        <span v-if="authStore.isAuthenticated" class="user-info">
          {{ authStore.username }}
        </span>
        <el-button
          v-if="authStore.isAuthenticated"
          type="danger"
          size="small"
          @click="handleLogout"
        >
          登出
        </el-button>
      </div>
    </nav>
    <main class="app-main">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
/**
 * App.vue - 电网地图系统根组件
 * 
 * 功能说明：
 * 提供应用的整体布局结构
 * 包含导航栏和路由出口
 * 支持用户登出功能
 * 应用用户设置（网格背景等）
 * 
 * 布局结构：
 * - 网格背景层：可显示/隐藏的网格背景
 * - 导航栏：首页、关于、设置、用户信息、登出按钮
 * - 主内容区：路由视图
 * 
 * 文件关联：
 * - router/index.ts: 路由配置
 * - views/*.vue: 页面组件
 * - stores/authStore.ts: 认证状态管理
 * - stores/settingsStore.ts: 设置状态管理
 */

import { useRouter } from 'vue-router'
import { useAuthStore } from './stores/authStore'
import { useSettingsStore } from './stores/settingsStore'

const router = useRouter()
const authStore = useAuthStore()
const settingsStore = useSettingsStore()

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}
</script>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

#app {
  width: 100%;
  height: 100%;
}
</style>

<style scoped>
.app-container {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a1a2e 50%, #16213e 100%);
  color: #e0e0e0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.grid-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: 
    linear-gradient(rgba(0, 240, 255, 0.1) 1px, transparent 1px),
    linear-gradient(90deg, rgba(0, 240, 255, 0.1) 1px, transparent 1px);
  background-size: 50px 50px;
  pointer-events: none;
  z-index: 0;
  transition: opacity 0.3s ease;
}

.grid-overlay.no-grid {
  opacity: 0;
}

.app-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  background: rgba(0, 0, 0, 0.3);
  border-bottom: 1px solid rgba(0, 240, 255, 0.3);
  position: relative;
  z-index: 10;
}

.nav-left {
  display: flex;
  gap: 20px;
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.nav-link {
  color: #a0a0a0;
  text-decoration: none;
  padding: 8px 16px;
  border-radius: 4px;
  transition: all 0.3s ease;
}

.nav-link:hover {
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.1);
}

.nav-link.active {
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.2);
  border: 1px solid rgba(0, 240, 255, 0.5);
}

.user-info {
  color: #00f0ff;
  font-size: 0.9rem;
}

.app-main {
  flex: 1;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

/* 自定义弹出窗口样式 */
:global(.custom-popup) {
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.5);
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.3);
  color: #e0e0e0;
  max-width: 300px;
}

:global(.custom-popup .mapboxgl-popup-content) {
  background: transparent;
  border: none;
  box-shadow: none;
  padding: 0;
}

:global(.popup-header) {
  padding: 15px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.3);
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.1), rgba(0, 128, 255, 0.1));
  border-radius: 8px 8px 0 0;
}

:global(.popup-header h3) {
  margin: 0 0 10px 0;
  color: #00f0ff;
  font-size: 1.2rem;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

:global(.status-indicator) {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
}

:global(.status-active) {
  background: rgba(0, 255, 128, 0.2);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.5);
}

:global(.status-inactive) {
  background: rgba(255, 0, 128, 0.2);
  color: #ff0080;
  border: 1px solid rgba(255, 0, 128, 0.5);
}

:global(.popup-content) {
  padding: 15px;
}

:global(.info-row) {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

:global(.info-label) {
  color: #a0a0a0;
  font-size: 0.9rem;
}

:global(.info-value) {
  color: #e0e0e0;
  font-weight: 600;
}

:global(.popup-footer) {
  padding: 10px 15px;
  border-top: 1px solid rgba(0, 240, 255, 0.3);
  background: rgba(0, 240, 255, 0.05);
  border-radius: 0 0 8px 8px;
}

:global(.popup-footer small) {
  color: #a0a0a0;
  font-size: 0.8rem;
}

/* 响应式设计 */
@media (max-width: 1920px) {
  .app-nav {
    padding: 12px 15px;
  }
}

@media (min-width: 3840px) {
  .app-nav {
    padding: 20px 40px;
  }
  
  .nav-link {
    font-size: 1.2rem;
    padding: 12px 24px;
  }
}
</style>
