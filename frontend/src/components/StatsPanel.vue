<template>
  <!--
    统计面板容器
    - 水平排列统计项
    - 半透明背景，发光边框
    - 相对定位，确保在地图上层
  -->
  <div class="stats-panel">
    <!--
      变电站数量统计项
      - 显示标签和数值
      - 数值从 props.stats.substations 获取
    -->
    <div class="info-item">
      <span class="info-label">变电站数量:</span>
      <!--
        数值显示
        - 使用插值表达式 {{ }} 显示响应式数据
        - stats.substations: 变电站数量
      -->
      <span class="info-value">{{ stats.substations }}</span>
    </div>
    
    <!--
      输电线路数量统计项
      - 显示标签和数值
      - 数值从 props.stats.lines 获取
    -->
    <div class="info-item">
      <span class="info-label">输电线路数量:</span>
      <span class="info-value">{{ stats.lines }}</span>
    </div>
    
    <!--
      台区数量统计项
      - 显示标签和数值
      - 数值从 props.stats.areas 获取
    -->
    <div class="info-item">
      <span class="info-label">台区数量:</span>
      <span class="info-value">{{ stats.areas }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * StatsPanel.vue - 数据统计面板组件
 * 
 * 功能说明：
 * 显示电网数据的统计信息，包括：
 * 1. 变电站数量
 * 2. 输电线路数量
 * 3. 台区数量
 * 
 * 数据流向：
 * 1. MapContainer 加载数据完成
 * 2. 触发 stats-update 事件
 * 3. HomeView 更新 mapStore
 * 4. StatsPanel 自动响应更新（Vue 响应式）
 * 
 * 组件通信：
 * - 使用 props 接收父组件传递的统计数据
 * - 数据变化时自动更新显示
 * 
 * 设计模式：
 * - 展示组件（Dumb Component）：只负责数据展示
 * - 不包含业务逻辑
 * 
 * 文件关联：
 * - views/HomeView.vue: 父组件，传递统计数据
 * - types/index.ts: 使用 MapStats 类型定义
 * - stores/mapStore.ts: 数据来源
 */

// ==================== 导入依赖 ====================

/**
 * 类型定义
 * - MapStats: 统计数据类型接口
 */
import type { MapStats } from '../types'

// ==================== 属性定义 ====================

/**
 * 定义组件属性（Props）
 * 
 * 使用 TypeScript 泛型定义属性类型，确保类型安全
 * 
 * 属性说明：
 * - stats: 统计数据对象
 *   - substations: 变电站数量
 *   - lines: 输电线路数量
 *   - areas: 台区数量
 * 
 * 使用方式：
 * - 在父组件中：<StatsPanel :stats="mapStore.stats" />
 * - stats 对象变化时，组件自动更新
 */
defineProps<{
  /** 统计数据对象 */
  stats: MapStats
}>()
</script>

<style scoped>
/*
 * 统计面板容器样式
 * - 水平排列
 * - 居中对齐
 * - 半透明背景
 * - 发光边框
 * - 相对定位，确保在地图上层
 */
.stats-panel {
  display: flex;
  gap: 25px;
  justify-content: center;
  padding: 15px 20px;
  background: rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.2);
  position: relative;
  z-index: 1;
  flex-shrink: 0;
  flex-wrap: wrap;
}

/*
 * 单个统计项样式
 * - 垂直排列
 * - 居中对齐
 * - 标签和数值间距 5px
 */
.info-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

/*
 * 统计标签样式
 * - 灰色小字
 * - 大写字母
 * - 字母间距 1px
 */
.info-label {
  font-size: 0.9rem;
  color: #a0a0a0;
  text-transform: uppercase;
  letter-spacing: 1px;
}

/*
 * 统计数值样式
 * - 大字号
 * - 粗体
 * - 青色发光效果
 */
.info-value {
  font-size: 1.8rem;
  font-weight: 700;
  color: #00f0ff;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

/*
 * 响应式设计 - 小屏幕
 * - 缩小内边距
 * - 缩小数值字号
 */
@media (max-width: 1920px) {
  .stats-panel {
    padding: 12px 15px;
    gap: 20px;
  }
  
  .info-value {
    font-size: 1.5rem;
  }
}

/*
 * 响应式设计 - 大屏幕（4K）
 * - 放大内边距
 * - 放大数值字号
 */
@media (min-width: 3840px) {
  .stats-panel {
    margin-bottom: 30px;
    padding: 25px;
    gap: 40px;
  }
  
  .info-value {
    font-size: 2.2rem;
  }
}
</style>
