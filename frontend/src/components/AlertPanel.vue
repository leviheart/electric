<template>
  <div class="alert-panel-container">
    <div class="alert-trigger-btn" @click="togglePanel">
      <el-badge :value="activeCount" :hidden="activeCount === 0" :max="99">
        <el-button :icon="Bell" circle :class="{ 'has-critical': hasCritical }" />
      </el-badge>
    </div>
    
    <transition name="slide-fade">
      <div class="alert-panel" v-if="showPanel">
        <div class="panel-header">
          <div class="header-title">
            <el-icon><Warning /></el-icon>
            <span>预警中心</span>
          </div>
          <el-button :icon="Close" circle size="small" @click="showPanel = false" />
        </div>
        
        <div class="panel-filters">
          <div class="date-filter">
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
              size="small"
              :clearable="true"
              @change="handleDateChange"
            />
          </div>
          
          <div class="type-filter">
            <el-select v-model="filterType" placeholder="预警类型" size="small" clearable>
              <el-option label="全部类型" value="" />
              <el-option-group label="设备告警">
                <el-option label="设备离线" value="DEVICE_OFFLINE" />
                <el-option label="负载过高" value="OVERLOAD" />
                <el-option label="电压异常" value="VOLTAGE_ABNORMAL" />
                <el-option label="温度过高" value="TEMPERATURE_HIGH" />
                <el-option label="故障告警" value="FAULT" />
                <el-option label="检修提醒" value="MAINTENANCE" />
              </el-option-group>
              <el-option-group label="自然灾害">
                <el-option label="大风预警" value="STRONG_WIND" />
                <el-option label="山火预警" value="WILDFIRE" />
                <el-option label="冰寒预警" value="ICE_STORM" />
                <el-option label="洪涝预警" value="FLOOD" />
                <el-option label="雷电预警" value="LIGHTNING" />
                <el-option label="地震预警" value="EARTHQUAKE" />
              </el-option-group>
            </el-select>
          </div>
          
          <div class="level-filter">
            <el-select v-model="filterLevel" placeholder="预警级别" size="small" clearable>
              <el-option label="全部级别" value="" />
              <el-option label="严重" value="CRITICAL" />
              <el-option label="高" value="HIGH" />
              <el-option label="中" value="MEDIUM" />
              <el-option label="低" value="LOW" />
            </el-select>
          </div>
        </div>
        
        <div class="stats-row">
          <div class="stat-item critical" @click="filterByLevel('CRITICAL')">
            <span class="stat-value">{{ stats.critical }}</span>
            <span class="stat-label">严重</span>
          </div>
          <div class="stat-item high" @click="filterByLevel('HIGH')">
            <span class="stat-value">{{ stats.high }}</span>
            <span class="stat-label">高</span>
          </div>
          <div class="stat-item medium" @click="filterByLevel('MEDIUM')">
            <span class="stat-value">{{ stats.medium }}</span>
            <span class="stat-label">中</span>
          </div>
          <div class="stat-item low" @click="filterByLevel('LOW')">
            <span class="stat-value">{{ stats.low }}</span>
            <span class="stat-label">低</span>
          </div>
        </div>
        
        <div class="status-tabs">
          <el-radio-group v-model="filterStatus" size="small">
            <el-radio-button label="">全部</el-radio-button>
            <el-radio-button label="ACTIVE">活动</el-radio-button>
            <el-radio-button label="ACKNOWLEDGED">已确认</el-radio-button>
            <el-radio-button label="RESOLVED">已解决</el-radio-button>
          </el-radio-group>
        </div>
        
        <div class="alert-list">
          <div v-if="filteredAlerts.length === 0" class="empty-state">
            <el-empty description="暂无预警数据" :image-size="60" />
          </div>
          
          <div
            v-for="alert in filteredAlerts"
            :key="alert.id"
            class="alert-item"
            :class="[`level-${alert.level.toLowerCase()}`]"
            @click="handleLocate(alert)"
          >
            <div class="alert-icon">
              {{ getTypeIcon(alert.type) }}
            </div>
            
            <div class="alert-content">
              <div class="alert-header">
                <span class="alert-title">{{ alert.title }}</span>
                <span class="alert-level" :style="{ color: getLevelColor(alert.level) }">
                  {{ getLevelLabel(alert.level) }}
                </span>
              </div>
              
              <div class="alert-message">{{ alert.message }}</div>
              
              <div class="alert-footer">
                <span class="device-info">
                  <el-icon><Location /></el-icon>
                  {{ alert.deviceName }}
                </span>
                <span class="alert-time">{{ formatTime(alert.timestamp) }}</span>
              </div>
            </div>
            
            <div class="alert-actions" @click.stop>
              <el-button
                v-if="alert.status === 'ACTIVE'"
                size="small"
                type="warning"
                plain
                @click="handleAck(alert.id)"
              >
                确认
              </el-button>
              <el-button
                v-if="alert.status !== 'RESOLVED'"
                size="small"
                type="success"
                plain
                @click="handleResolve(alert.id)"
              >
                解决
              </el-button>
            </div>
          </div>
        </div>
        
        <div class="panel-footer">
          <el-button size="small" @click="refreshAlerts">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
          <el-button size="small" type="danger" plain @click="clearResolved">
            清除已解决
          </el-button>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { Bell, Close, Warning, Location, Refresh } from '@element-plus/icons-vue'
import { useAlertStore } from '../stores/alertStore'
import type { Alert } from '../types/alert'
import { AlertType, AlertLevel } from '../types/alert'

const props = defineProps<{
  modelValue?: boolean
}>()

const emit = defineEmits<{
  'update:modelValue': [value: boolean]
  locate: [alert: Alert]
}>()

const alertStore = useAlertStore()

const showPanel = ref(props.modelValue || false)
const dateRange = ref<[string, string] | null>(null)
const filterType = ref('')
const filterLevel = ref('')
const filterStatus = ref('')

watch(() => props.modelValue, (val) => {
  showPanel.value = val || false
})

watch(showPanel, (val) => {
  emit('update:modelValue', val)
})

const togglePanel = () => {
  showPanel.value = !showPanel.value
}

const activeCount = computed(() => {
  return alertStore.alerts.filter(a => a.status === 'ACTIVE').length
})

const hasCritical = computed(() => {
  return alertStore.alerts.some(a => a.level === 'CRITICAL' && a.status === 'ACTIVE')
})

const stats = computed(() => alertStore.stats)

const filteredAlerts = computed(() => {
  let alerts = alertStore.alerts
  
  if (dateRange.value && dateRange.value[0] && dateRange.value[1]) {
    const startDate = new Date(dateRange.value[0]).getTime()
    const endDate = new Date(dateRange.value[1] + 'T23:59:59').getTime()
    alerts = alerts.filter(a => {
      const alertTime = new Date(a.timestamp).getTime()
      return alertTime >= startDate && alertTime <= endDate
    })
  }
  
  if (filterType.value) {
    alerts = alerts.filter(a => a.type === filterType.value)
  }
  
  if (filterLevel.value) {
    alerts = alerts.filter(a => a.level === filterLevel.value)
  }
  
  if (filterStatus.value) {
    alerts = alerts.filter(a => a.status === filterStatus.value)
  }
  
  return alerts.sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())
})

const handleDateChange = () => {
  // Date filter is reactive through computed
}

const filterByLevel = (level: string) => {
  filterLevel.value = filterLevel.value === level ? '' : level
}

const getTypeIcon = (type: AlertType): string => {
  const icons: Record<string, string> = {
    DEVICE_OFFLINE: '🔌',
    OVERLOAD: '⚡',
    VOLTAGE_ABNORMAL: '📊',
    TEMPERATURE_HIGH: '🌡️',
    FAULT: '🔴',
    MAINTENANCE: '🔧',
    STRONG_WIND: '💨',
    WILDFIRE: '🔥',
    ICE_STORM: '❄️',
    FLOOD: '🌊',
    LIGHTNING: '⛈️',
    EARTHQUAKE: '🌍'
  }
  return icons[type] || '⚠️'
}

const getLevelColor = (level: AlertLevel): string => {
  const colors: Record<string, string> = {
    CRITICAL: '#ff0040',
    HIGH: '#ff4500',
    MEDIUM: '#ff8000',
    LOW: '#ffff00'
  }
  return colors[level] || '#ffffff'
}

const getLevelLabel = (level: AlertLevel): string => {
  const labels: Record<string, string> = {
    CRITICAL: '严重',
    HIGH: '高',
    MEDIUM: '中',
    LOW: '低'
  }
  return labels[level] || level
}

const formatTime = (timestamp: string): string => {
  const date = new Date(timestamp)
  return date.toLocaleString('zh-CN', {
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const handleLocate = (alert: Alert) => {
  if (alert.latitude && alert.longitude) {
    emit('locate', alert)
  }
}

const handleAck = async (id: string) => {
  await alertStore.ackAlert(id, 'operator')
}

const handleResolve = async (id: string) => {
  await alertStore.resolveAlertById(id)
}

const refreshAlerts = () => {
  alertStore.fetchAlerts()
  alertStore.fetchStats()
}

const clearResolved = () => {
  const resolved = alertStore.alerts.filter(a => a.status === 'RESOLVED')
  resolved.forEach(a => alertStore.removeAlertById(a.id))
}
</script>

<style scoped>
.alert-panel-container {
  position: fixed;
  top: 70px;
  left: 20px;
  z-index: 1000;
}

.alert-trigger-btn :deep(.el-button) {
  background: rgba(10, 10, 26, 0.9);
  border: 2px solid rgba(0, 240, 255, 0.4);
  color: #00f0ff;
  width: 44px;
  height: 44px;
}

.alert-trigger-btn :deep(.el-button:hover) {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
}

.alert-trigger-btn :deep(.el-button.has-critical) {
  animation: critical-pulse 1s infinite;
  border-color: #ff0040;
  color: #ff0040;
}

@keyframes critical-pulse {
  0%, 100% { box-shadow: 0 0 0 0 rgba(255, 0, 64, 0.5); }
  50% { box-shadow: 0 0 0 12px rgba(255, 0, 64, 0); }
}

.alert-panel {
  position: absolute;
  top: 54px;
  left: 0;
  width: 420px;
  max-height: 80vh;
  background: rgba(10, 10, 26, 0.98);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 16px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.6);
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background: linear-gradient(180deg, rgba(0, 240, 255, 0.1) 0%, transparent 100%);
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.header-title {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #00f0ff;
  font-size: 18px;
  font-weight: 600;
}

.panel-filters {
  padding: 12px 16px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
}

.date-filter :deep(.el-date-editor) {
  width: 100%;
}

.panel-filters :deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.2);
  box-shadow: none;
}

.panel-filters :deep(.el-input__inner) {
  color: #ffffff;
}

.type-filter, .level-filter {
  flex: 1;
}

.type-filter :deep(.el-select), .level-filter :deep(.el-select) {
  width: 100%;
}

.stats-row {
  display: flex;
  padding: 12px 16px;
  gap: 10px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
}

.stat-item {
  flex: 1;
  text-align: center;
  padding: 10px 8px;
  border-radius: 10px;
  background: rgba(0, 0, 0, 0.3);
  cursor: pointer;
  transition: all 0.2s;
}

.stat-item:hover {
  background: rgba(0, 240, 255, 0.1);
}

.stat-value {
  display: block;
  font-size: 24px;
  font-weight: bold;
  line-height: 1.2;
}

.stat-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.5);
}

.stat-item.critical .stat-value { color: #ff0040; }
.stat-item.high .stat-value { color: #ff4500; }
.stat-item.medium .stat-value { color: #ff8000; }
.stat-item.low .stat-value { color: #ffff00; }

.status-tabs {
  padding: 12px 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
}

.status-tabs :deep(.el-radio-button__inner) {
  background: transparent;
  border-color: rgba(0, 240, 255, 0.2);
  color: rgba(255, 255, 255, 0.6);
}

.status-tabs :deep(.el-radio-button__original-radio:checked + .el-radio-button__inner) {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
  color: #00f0ff;
}

.alert-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
  max-height: 400px;
}

.empty-state {
  padding: 40px 20px;
}

.alert-item {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  padding: 14px;
  margin-bottom: 8px;
  border-radius: 12px;
  background: rgba(0, 0, 0, 0.3);
  border-left: 4px solid;
  cursor: pointer;
  transition: all 0.2s;
}

.alert-item:hover {
  background: rgba(0, 240, 255, 0.1);
  transform: translateX(4px);
}

.alert-item.level-critical { border-left-color: #ff0040; }
.alert-item.level-high { border-left-color: #ff4500; }
.alert-item.level-medium { border-left-color: #ff8000; }
.alert-item.level-low { border-left-color: #ffff00; }

.alert-icon {
  font-size: 28px;
  line-height: 1;
}

.alert-content {
  flex: 1;
  min-width: 0;
}

.alert-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 4px;
}

.alert-title {
  font-size: 14px;
  font-weight: 600;
  color: #ffffff;
}

.alert-level {
  font-size: 11px;
  font-weight: 600;
  padding: 2px 8px;
  border-radius: 4px;
  background: rgba(255, 255, 255, 0.1);
}

.alert-message {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  line-height: 1.4;
  margin-bottom: 8px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.alert-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 11px;
  color: rgba(255, 255, 255, 0.4);
}

.device-info {
  display: flex;
  align-items: center;
  gap: 4px;
}

.alert-actions {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.alert-actions :deep(.el-button) {
  font-size: 11px;
  padding: 4px 10px;
}

.panel-footer {
  display: flex;
  justify-content: space-between;
  padding: 12px 16px;
  border-top: 1px solid rgba(0, 240, 255, 0.15);
  background: rgba(0, 0, 0, 0.2);
}

.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  opacity: 0;
  transform: translateX(-20px);
}

.alert-list::-webkit-scrollbar {
  width: 6px;
}

.alert-list::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.2);
}

.alert-list::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}
</style>
