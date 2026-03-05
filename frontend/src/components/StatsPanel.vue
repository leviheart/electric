<template>
  <div class="stats-panel">
    <div class="info-item">
      <span class="info-label">变电站</span>
      <span class="info-value">{{ stats.substations }}</span>
    </div>
    
    <div class="info-item">
      <span class="info-label">输电线路</span>
      <span class="info-value">{{ stats.lines }}</span>
    </div>
    
    <div class="info-item">
      <span class="info-label">台区</span>
      <span class="info-value">{{ stats.areas }}</span>
    </div>
    
    <div class="info-divider"></div>
    
    <div class="info-item" v-if="alertCount !== undefined">
      <span class="info-label">活动告警</span>
      <span class="info-value alert-value" :class="{ 'has-alert': alertCount > 0 }">{{ alertCount }}</span>
    </div>
    
    <div class="info-item" v-if="avgLoadRate !== undefined">
      <span class="info-label">平均负载</span>
      <span class="info-value" :class="getLoadClass(avgLoadRate)">{{ avgLoadRate.toFixed(1) }}%</span>
    </div>
    
    <div class="info-item" v-if="totalCapacity !== undefined">
      <span class="info-label">总容量</span>
      <span class="info-value">{{ formatCapacity(totalCapacity) }}</span>
    </div>
    
    <div class="info-item" v-if="totalCustomers !== undefined">
      <span class="info-label">总用户</span>
      <span class="info-value">{{ formatNumber(totalCustomers) }}</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { MapStats } from '../types'

const props = defineProps<{
  stats: MapStats
  alertCount?: number
  avgLoadRate?: number
  totalCapacity?: number
  totalCustomers?: number
}>()

const getLoadClass = (load: number): string => {
  if (load >= 80) return 'load-high'
  if (load >= 60) return 'load-medium'
  return 'load-normal'
}

const formatCapacity = (capacity: number): string => {
  if (capacity >= 1000) {
    return (capacity / 1000).toFixed(1) + ' GVA'
  }
  return capacity.toFixed(0) + ' MVA'
}

const formatNumber = (num: number): string => {
  if (num >= 10000) {
    return (num / 10000).toFixed(1) + '万'
  }
  return num.toString()
}
</script>

<style scoped>
.stats-panel {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 10;
  display: flex;
  gap: 30px;
  padding: 12px 30px;
  background: rgba(10, 10, 26, 0.85);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(10px);
}

.info-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
}

.info-label {
  font-size: 0.75rem;
  color: rgba(255, 255, 255, 0.6);
  text-transform: uppercase;
  letter-spacing: 1px;
}

.info-value {
  font-size: 1.5rem;
  font-weight: 700;
  color: #00f0ff;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

.info-divider {
  width: 1px;
  height: 30px;
  background: rgba(0, 240, 255, 0.3);
}

.alert-value.has-alert {
  color: #ff4040;
  text-shadow: 0 0 10px rgba(255, 64, 64, 0.5);
  animation: pulse 1s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.6; }
}

.load-normal {
  color: #00ff80;
}

.load-medium {
  color: #ffff00;
}

.load-high {
  color: #ff8000;
}

@media (max-width: 1920px) {
  .stats-panel {
    padding: 10px 24px;
    gap: 24px;
  }
  
  .info-value {
    font-size: 1.3rem;
  }
}

@media (min-width: 3840px) {
  .stats-panel {
    padding: 16px 40px;
    gap: 40px;
  }
  
  .info-value {
    font-size: 1.8rem;
  }
}
</style>
