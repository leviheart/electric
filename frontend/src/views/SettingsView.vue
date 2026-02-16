<template>
  <!--
    设置页面容器
    - 使用 flex 布局实现垂直水平居中
    - 全屏高度，带深色背景
  -->
  <div class="settings-view">
    <!--
      设置卡片
      - 包含标题、设置项、操作按钮
      - 半透明背景，带发光边框效果
    -->
    <div class="settings-card">
      <!-- 页面标题 -->
      <h1>系统设置</h1>
      
      <!--
        设置内容区域
        - 包含所有设置项
        - 每个设置项一行
      -->
      <div class="settings-content">
        <!--
          地图样式设置项
          - 左侧显示标签
          - 右侧显示下拉选择框
        -->
        <div class="setting-item">
          <span class="setting-label">地图样式</span>
          <!--
            el-select 下拉选择框
            - v-model: 双向绑定选中的值
            - placeholder: 占位提示文字
            - @change: 值改变时触发保存
          -->
          <el-select v-model="settingsStore.mapStyle" placeholder="选择地图样式" @change="handleChange">
            <!-- 深色模式选项 -->
            <el-option label="深色模式" value="dark" />
            <!-- 浅色模式选项 -->
            <el-option label="浅色模式" value="light" />
            <!-- 卫星图选项 -->
            <el-option label="卫星图" value="satellite" />
          </el-select>
        </div>
        
        <!--
          默认缩放级别设置项
          - 使用滑块选择
        -->
        <div class="setting-item">
          <span class="setting-label">默认缩放级别</span>
          <!--
            el-slider 滑块组件
            - v-model: 双向绑定当前值
            - :min: 最小值 8
            - :max: 最大值 18
            - show-input: 显示输入框
            - @change: 值改变时触发保存
          -->
          <el-slider v-model="settingsStore.defaultZoom" :min="8" :max="18" show-input @change="handleChange" />
        </div>
        
        <!--
          自动加载数据设置项
          - 使用开关切换
        -->
        <div class="setting-item">
          <span class="setting-label">自动加载数据</span>
          <!--
            el-switch 开关组件
            - v-model: 双向绑定开关状态
            - @change: 状态改变时触发保存
          -->
          <el-switch v-model="settingsStore.autoLoad" @change="handleChange" />
        </div>
        
        <!--
          显示网格背景设置项
          - 使用开关切换
          - 切换后立即生效
        -->
        <div class="setting-item">
          <span class="setting-label">显示网格背景</span>
          <!--
            el-switch 开关组件
            - 切换后立即生效，无需刷新页面
          -->
          <el-switch v-model="settingsStore.showGrid" @change="handleChange" />
        </div>
      </div>
      
      <!--
        底部按钮区域
        - 包含保存、重置、返回按钮
      -->
      <div class="settings-footer">
        <!--
          保存按钮
          - type="primary": 主要按钮样式
          - @click="saveSettings": 点击保存设置
        -->
        <el-button @click="saveSettings" type="primary">保存设置</el-button>
        <!--
          重置按钮
          - @click="resetSettings": 点击重置为默认值
        -->
        <el-button @click="resetSettings">重置</el-button>
        <!--
          返回首页按钮
          - 使用 router.push 跳转
        -->
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
 * 提供系统配置选项，包括：
 * 1. 地图样式选择（深色/浅色/卫星图）
 * 2. 默认缩放级别设置
 * 3. 自动加载数据开关
 * 4. 网格背景显示开关
 * 
 * 设置持久化：
 * - 设置保存在 localStorage 中
 * - 页面刷新后自动恢复
 * - 部分设置需要刷新页面才能生效
 * 
 * 文件关联：
 * - stores/settingsStore.ts: 设置状态管理
 * - components/MapContainer.vue: 应用地图样式和缩放设置
 * - App.vue: 应用网格背景设置
 */

// ==================== 导入依赖 ====================

/**
 * Element Plus 组件
 * - ElMessage: 消息提示组件
 */
import { ElMessage } from 'element-plus'

/**
 * 设置状态管理
 * - useSettingsStore: 获取设置 store 实例
 */
import { useSettingsStore } from '../stores/settingsStore'

// ==================== 初始化 ====================

/**
 * 设置状态管理实例
 * 用于读取和修改系统设置
 */
const settingsStore = useSettingsStore()

// ==================== 方法 ====================

/**
 * 处理设置变更
 * 
 * 当任何设置项的值改变时触发
 * 将设置保存到 localStorage
 */
const handleChange = () => {
  // 保存设置到 localStorage
  settingsStore.save()
}

/**
 * 保存设置
 * 
 * 点击"保存设置"按钮时触发
 * 保存设置并显示成功提示
 */
const saveSettings = () => {
  // 保存设置到 localStorage
  settingsStore.save()
  // 显示成功提示
  ElMessage.success('设置已保存，部分设置将在刷新页面后生效')
}

/**
 * 重置设置
 * 
 * 点击"重置"按钮时触发
 * 将所有设置恢复为默认值
 */
const resetSettings = () => {
  // 调用 store 的重置方法
  settingsStore.reset()
  // 显示提示
  ElMessage.info('设置已重置')
}
</script>

<style scoped>
/*
 * 设置页面容器样式
 * - 全屏高度，flex 布局居中
 * - 深色背景，内边距
 */
.settings-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}

/*
 * 设置卡片样式
 * - 半透明黑色背景
 * - 发光边框效果
 * - 圆角阴影
 */
.settings-card {
  background: rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 30px;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.2);
}

/*
 * 页面标题样式
 * - 青色发光文字
 * - 居中显示
 */
.settings-card h1 {
  color: #00f0ff;
  text-align: center;
  margin-bottom: 30px;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

/*
 * 设置内容区域样式
 * - 垂直排列
 * - 设置项间距 20px
 */
.settings-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/*
 * 单个设置项样式
 * - 水平排列，两端对齐
 * - 垂直居中
 */
.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/*
 * 设置标签样式
 * - 浅色文字
 * - 固定字体大小
 */
.setting-label {
  color: #e0e0e0;
  font-size: 1rem;
}

/*
 * 底部按钮区域样式
 * - 居中排列
 * - 按钮间距 10px
 */
.settings-footer {
  margin-top: 30px;
  display: flex;
  justify-content: center;
  gap: 10px;
}

/*
 * 主要按钮样式覆盖
 * - 渐变背景
 */
.settings-footer .el-button--primary {
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
}

/*
 * 普通按钮样式覆盖
 * - 半透明背景
 * - 发光边框
 */
.settings-footer .el-button {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  color: #00f0ff;
}

/*
 * 下拉选择框宽度
 */
:deep(.el-select) {
  width: 150px;
}

/*
 * 滑块宽度
 */
:deep(.el-slider) {
  width: 200px;
}
</style>
