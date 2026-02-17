<template>
  <div class="alert-panel" :class="{ 'panel-open': alertStore.showPanel }">
    <div class="alert-trigger" @click="alertStore.togglePanel">
      <el-badge :value="alertStore.activeAlerts.length" :hidden="alertStore.activeAlerts.length === 0" :max="99">
        <el-button :icon="Bell" circle :class="{ 'has-critical': alertStore.hasCriticalAlerts }" />
      </el-badge>
    </div>
    
    <transition name="slide">
      <div class="alert-list" v-if="alertStore.showPanel">
        <div class="panel-header">
          <h3>告警中心</h3>
          <div class="header-actions">
            <el-tooltip :content="alertStore.soundEnabled ? '关闭声音' : '开启声音'">
              <el-button 
                :icon="alertStore.soundEnabled ? Bell : Mute" 
                circle 
                size="small"
                @click="alertStore.toggleSound"
              />
            </el-tooltip>
            <el-button :icon="Close" circle size="small" @click="alertStore.togglePanel" />
          </div>
        </div>
        
        <div class="stats-bar">
          <div class="stat-item critical">
            <span class="stat-value">{{ alertStore.stats.critical }}</span>
            <span class="stat-label">严重</span>
          </div>
          <div class="stat-item high">
            <span class="stat-value">{{ alertStore.stats.high }}</span>
            <span class="stat-label">高</span>
          </div>
          <div class="stat-item medium">
            <span class="stat-value">{{ alertStore.stats.medium }}</span>
            <span class="stat-label">中</span>
          </div>
          <div class="stat-item low">
            <span class="stat-value">{{ alertStore.stats.low }}</span>
            <span class="stat-label">低</span>
          </div>
        </div>
        
        <div class="filter-tabs">
          <el-radio-group v-model="filterStatus" size="small">
            <el-radio-button label="all">全部</el-radio-button>
            <el-radio-button label="active">活动</el-radio-button>
            <el-radio-button label="acknowledged">已确认</el-radio-button>
            <el-radio-button label="resolved">已解决</el-radio-button>
          </el-radio-group>
        </div>
        
        <div class="alert-items">
          <div v-if="filteredAlerts.length === 0" class="no-alerts">
            <el-empty description="暂无告警" :image-size="80" />
          </div>
          
          <div
            v-for="alert in filteredAlerts"
            :key="alert.id"
            class="alert-item"
            :class="[`level-${alert.level.toLowerCase()}`, `status-${alert.status.toLowerCase()}`]"
          >
            <div class="alert-header">
              <div class="alert-type">
                <span class="type-icon">{{ getTypeIcon(alert.type) }}</span>
                <span class="type-label">{{ getTypeLabel(alert.type) }}</span>
              </div>
              <div class="alert-level" :style="{ color: getLevelColor(alert.level) }">
                {{ getLevelLabel(alert.level) }}
              </div>
            </div>
            
            <div class="alert-title">{{ alert.title }}</div>
            <div class="alert-message">{{ alert.message }}</div>
            
            <div class="alert-meta">
              <span class="device-name">{{ alert.deviceName }}</span>
              <span class="timestamp">{{ formatTime(alert.timestamp) }}</span>
            </div>
            
            <div class="alert-actions" v-if="alert.status !== 'RESOLVED'">
              <el-button
                v-if="alert.status === 'ACTIVE'"
                size="small"
                type="warning"
                @click="handleAck(alert.id)"
              >
                确认
              </el-button>
              <el-button
                size="small"
                type="success"
                @click="handleResolve(alert.id)"
              >
                解决
              </el-button>
              <el-button
                size="small"
                @click="handleLocate(alert)"
                v-if="alert.latitude && alert.longitude"
              >
                定位
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
/**
 * AlertPanel.vue - 告警面板组件
 * 
 * 功能说明：
 * 1. 显示告警通知图标和未读数量
 * 2. 展开显示告警列表
 * 3. 支持告警确认、解决、定位操作
 * 4. 支持按状态筛选告警
 * 5. 支持声音提醒开关
 * 
 * 组件结构：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ [🔔] (带未读数量角标)                                            │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ 告警中心                                    [🔊] [✕]            │
 * │ [严重: 2] [高: 5] [中: 3] [低: 1]                                │
 * │ [全部] [活动] [已确认] [已解决]                                   │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ ⚡ 设备离线  严重                                                │
 * │ 设备离线告警                                                     │
 * │ 朝阳220kV变电站已离线，请检查设备连接状态                        │
 * │ 朝阳220kV变电站  2024-01-15 10:30:00                            │
 * │ [确认] [解决] [定位]                                             │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 事件：
 * - locate: 用户点击定位按钮时触发，参数为告警对象
 * 
 * 文件关联：
 * - stores/alertStore.ts: 告警状态管理
 * - types/alert.ts: 告警类型定义
 */

import { ref, computed, onMounted } from 'vue'
import { Bell, Close, Mute } from '@element-plus/icons-vue'
import { useAlertStore } from '../stores/alertStore'
import type { Alert } from '../types/alert'
import { AlertType, AlertLevel, AlertLevelConfig, AlertTypeConfig, AlertStatus } from '../types/alert'

const emit = defineEmits<{
  locate: [alert: Alert]
}>()

const alertStore = useAlertStore()
const filterStatus = ref('all')

const filteredAlerts = computed(() => {
  if (filterStatus.value === 'all') {
    return alertStore.alerts
  }
  return alertStore.alerts.filter(a => a.status === filterStatus.value.toUpperCase())
})

const getTypeIcon = (type: AlertType): string => {
  return AlertTypeConfig[type]?.icon || '⚠️'
}

const getTypeLabel = (type: AlertType): string => {
  return AlertTypeConfig[type]?.label || '未知'
}

const getLevelColor = (level: AlertLevel): string => {
  return AlertLevelConfig[level]?.color || '#ffffff'
}

const getLevelLabel = (level: AlertLevel): string => {
  return AlertLevelConfig[level]?.label || '未知'
}

const formatTime = (timestamp: string): string => {
  const date = new Date(timestamp)
  return date.toLocaleString('zh-CN', {
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

const handleAck = async (id: string) => {
  try {
    await alertStore.ackAlert(id, 'operator')
  } catch (error) {
    console.error('确认告警失败:', error)
  }
}

const handleResolve = async (id: string) => {
  try {
    await alertStore.resolveAlertById(id)
  } catch (error) {
    console.error('解决告警失败:', error)
  }
}

const handleLocate = (alert: Alert) => {
  emit('locate', alert)
}

onMounted(() => {
  alertStore.fetchAlerts()
  alertStore.fetchStats()
})
</script>

<style scoped>
.alert-panel {
  position: fixed;
  top: 20px;
  right: 20px;
  z-index: 1000;
}

.alert-trigger {
  position: relative;
}

.alert-trigger :deep(.el-button) {
  background: rgba(10, 10, 26, 0.8);
  border: 1px solid rgba(0, 240, 255, 0.3);
  color: #00f0ff;
}

.alert-trigger :deep(.el-button:hover) {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
}

.alert-trigger :deep(.el-button.has-critical) {
  animation: pulse 1s infinite;
}

@keyframes pulse {
  0%, 100% { box-shadow: 0 0 0 0 rgba(255, 0, 128, 0.5); }
  50% { box-shadow: 0 0 0 10px rgba(255, 0, 128, 0); }
}

.alert-list {
  position: absolute;
  top: 50px;
  right: 0;
  width: 380px;
  max-height: 500px;
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
  overflow: hidden;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.panel-header h3 {
  margin: 0;
  color: #00f0ff;
  font-size: 16px;
}

.header-actions {
  display: flex;
  gap: 8px;
}

.stats-bar {
  display: flex;
  padding: 12px 16px;
  gap: 12px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.stat-item {
  flex: 1;
  text-align: center;
  padding: 8px;
  border-radius: 8px;
  background: rgba(0, 0, 0, 0.2);
}

.stat-value {
  display: block;
  font-size: 20px;
  font-weight: bold;
}

.stat-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.stat-item.critical .stat-value { color: #ff0080; }
.stat-item.high .stat-value { color: #ff6600; }
.stat-item.medium .stat-value { color: #ffff00; }
.stat-item.low .stat-value { color: #00ff80; }

.filter-tabs {
  padding: 12px 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.filter-tabs :deep(.el-radio-button__inner) {
  background: transparent;
  border-color: rgba(0, 240, 255, 0.3);
  color: rgba(255, 255, 255, 0.6);
}

.filter-tabs :deep(.el-radio-button__original-radio:checked + .el-radio-button__inner) {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
  color: #00f0ff;
}

.alert-items {
  max-height: 300px;
  overflow-y: auto;
  padding: 8px;
}

.no-alerts {
  padding: 20px;
}

.alert-item {
  padding: 12px;
  margin-bottom: 8px;
  border-radius: 8px;
  background: rgba(0, 0, 0, 0.3);
  border-left: 3px solid;
}

.alert-item.level-critical { border-left-color: #ff0080; }
.alert-item.level-high { border-left-color: #ff6600; }
.alert-item.level-medium { border-left-color: #ffff00; }
.alert-item.level-low { border-left-color: #00ff80; }

.alert-item.status-resolved {
  opacity: 0.6;
}

.alert-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.alert-type {
  display: flex;
  align-items: center;
  gap: 6px;
}

.type-icon {
  font-size: 16px;
}

.type-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
}

.alert-level {
  font-size: 12px;
  font-weight: bold;
}

.alert-title {
  font-size: 14px;
  font-weight: 500;
  color: #ffffff;
  margin-bottom: 4px;
}

.alert-message {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 8px;
  line-height: 1.4;
}

.alert-meta {
  display: flex;
  justify-content: space-between;
  font-size: 11px;
  color: rgba(255, 255, 255, 0.4);
  margin-bottom: 8px;
}

.alert-actions {
  display: flex;
  gap: 8px;
}

.alert-actions :deep(.el-button) {
  font-size: 12px;
  padding: 4px 8px;
}

.slide-enter-active,
.slide-leave-active {
  transition: all 0.3s ease;
}

.slide-enter-from,
.slide-leave-to {
  opacity: 0;
  transform: translateX(20px);
}

.alert-items::-webkit-scrollbar {
  width: 6px;
}

.alert-items::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.2);
}

.alert-items::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}
</style>
