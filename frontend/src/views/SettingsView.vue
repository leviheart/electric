<template>
  <div class="settings-view">
    <div class="settings-card">
      <h1>系统设置</h1>
      
      <div class="settings-content">
        <div class="setting-item">
          <span class="setting-label">地图样式</span>
          <el-select v-model="settingsStore.mapStyle" placeholder="选择地图样式" @change="handleChange">
            <el-option label="深色模式" value="dark" />
            <el-option label="浅色模式" value="light" />
            <el-option label="卫星图" value="satellite" />
          </el-select>
        </div>
        
        <div class="setting-item">
          <span class="setting-label">默认缩放级别</span>
          <el-slider v-model="settingsStore.defaultZoom" :min="8" :max="18" show-input @change="handleChange" />
        </div>
        
        <div class="setting-item">
          <span class="setting-label">自动加载数据</span>
          <el-switch v-model="settingsStore.autoLoad" @change="handleChange" />
        </div>
        
        <div class="setting-item">
          <span class="setting-label">显示网格背景</span>
          <el-switch v-model="settingsStore.showGrid" @change="handleChange" />
        </div>
      </div>
      
      <div class="settings-footer">
        <el-button @click="saveSettings" type="primary">保存设置</el-button>
        <el-button @click="resetSettings">重置</el-button>
        <el-button @click="$router.push('/')">返回首页</el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * SettingsView.vue - 设置页面
 * 
 * 功能说明：
 * 提供系统配置选项
 * 使用 Pinia Store 管理设置状态
 * 设置会持久化到 localStorage
 * 网格背景设置立即生效
 * 
 * 文件关联：
 * - stores/settingsStore.ts: 设置状态管理
 * - components/MapContainer.vue: 应用设置
 */

import { ElMessage } from 'element-plus'
import { useSettingsStore } from '../stores/settingsStore'

const settingsStore = useSettingsStore()

const handleChange = () => {
  settingsStore.save()
}

const saveSettings = () => {
  settingsStore.save()
  ElMessage.success('设置已保存，部分设置将在刷新页面后生效')
}

const resetSettings = () => {
  settingsStore.reset()
  ElMessage.info('设置已重置')
}
</script>

<style scoped>
.settings-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}

.settings-card {
  background: rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 30px;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.2);
}

.settings-card h1 {
  color: #00f0ff;
  text-align: center;
  margin-bottom: 30px;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

.settings-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.setting-label {
  color: #e0e0e0;
  font-size: 1rem;
}

.settings-footer {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  gap: 10px;
}

.settings-footer .el-button--primary {
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
}

.settings-footer .el-button {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  color: #00f0ff;
}

:deep(.el-select) {
  width: 150px;
}

:deep(.el-slider) {
  width: 200px;
}
</style>
