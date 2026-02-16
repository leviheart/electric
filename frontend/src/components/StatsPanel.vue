<template>
  <div class="stats-panel">
    <div class="info-item">
      <span class="info-label">变电站数量:</span>
      <span class="info-value">{{ stats.substations }}</span>
    </div>
    <div class="info-item">
      <span class="info-label">输电线路数量:</span>
      <span class="info-value">{{ stats.lines }}</span>
    </div>
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
 * 显示电网数据的统计信息
 * 包括变电站、输电线路、台区的数量
 * 
 * 属性：
 * - stats: 统计数据对象
 * 
 * 文件关联：
 * - App.vue: 父组件，传递统计数据
 * - types/index.ts: 使用 MapStats 类型
 */
import type { MapStats } from '../types'

defineProps<{
  stats: MapStats
}>()
</script>

<style scoped>
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

.info-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 5px;
}

.info-label {
  font-size: 0.9rem;
  color: #a0a0a0;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.info-value {
  font-size: 1.8rem;
  font-weight: 700;
  color: #00f0ff;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

/* 响应式设计 */
@media (max-width: 1920px) {
  .stats-panel {
    padding: 12px 15px;
    gap: 20px;
  }
  
  .info-value {
    font-size: 1.5rem;
  }
}

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
