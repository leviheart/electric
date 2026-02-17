<template>
  <div class="dashboard-view">
    <div class="dashboard-header">
      <h1>数据统计仪表盘</h1>
      <el-button @click="refreshData" :loading="loading">
        <el-icon><Refresh /></el-icon>
        刷新数据
      </el-button>
    </div>
    
    <div class="stats-cards">
      <div class="stat-card substation">
        <div class="stat-icon">⚡</div>
        <div class="stat-content">
          <div class="stat-value">{{ stats.deviceStats?.totalSubstations || 0 }}</div>
          <div class="stat-label">变电站</div>
        </div>
      </div>
      
      <div class="stat-card line">
        <div class="stat-icon">〰</div>
        <div class="stat-content">
          <div class="stat-value">{{ stats.deviceStats?.totalLines || 0 }}</div>
          <div class="stat-label">输电线路</div>
        </div>
      </div>
      
      <div class="stat-card area">
        <div class="stat-icon">📍</div>
        <div class="stat-content">
          <div class="stat-value">{{ stats.deviceStats?.totalAreas || 0 }}</div>
          <div class="stat-label">台区</div>
        </div>
      </div>
      
      <div class="stat-card customer">
        <div class="stat-icon">👥</div>
        <div class="stat-content">
          <div class="stat-value">{{ formatNumber(stats.deviceStats?.totalCustomers || 0) }}</div>
          <div class="stat-label">用户总数</div>
        </div>
      </div>
    </div>
    
    <div class="charts-row">
      <div class="chart-card">
        <h3>运行状态分布</h3>
        <div class="chart-container" ref="statusChartRef"></div>
      </div>
      
      <div class="chart-card">
        <h3>电压等级分布</h3>
        <div class="chart-container" ref="voltageChartRef"></div>
      </div>
      
      <div class="chart-card">
        <h3>告警统计</h3>
        <div class="alert-stats">
          <div class="alert-stat-item critical">
            <span class="alert-value">{{ stats.alertStats?.critical || 0 }}</span>
            <span class="alert-label">严重</span>
          </div>
          <div class="alert-stat-item high">
            <span class="alert-value">{{ stats.alertStats?.high || 0 }}</span>
            <span class="alert-label">高</span>
          </div>
          <div class="alert-stat-item active">
            <span class="alert-value">{{ stats.alertStats?.active || 0 }}</span>
            <span class="alert-label">活动</span>
          </div>
          <div class="alert-stat-item resolved">
            <span class="alert-value">{{ stats.alertStats?.resolved || 0 }}</span>
            <span class="alert-label">已解决</span>
          </div>
        </div>
      </div>
    </div>
    
    <div class="charts-row">
      <div class="chart-card large">
        <h3>负载趋势（24小时）</h3>
        <div class="chart-container" ref="trendChartRef"></div>
      </div>
      
      <div class="chart-card">
        <h3>台区分布（按变电站）</h3>
        <div class="distribution-list">
          <div 
            v-for="(item, index) in stats.areaDistribution" 
            :key="index"
            class="distribution-item"
          >
            <div class="distribution-name">{{ item.name }}</div>
            <div class="distribution-bar">
              <div 
                class="distribution-fill" 
                :style="{ width: item.percentage + '%' }"
              ></div>
            </div>
            <div class="distribution-value">{{ item.count }} ({{ item.percentage }}%)</div>
          </div>
          <div v-if="!stats.areaDistribution?.length" class="no-data">
            暂无数据
          </div>
        </div>
      </div>
    </div>
    
    <div class="summary-row">
      <div class="summary-card">
        <h4>线路总长度</h4>
        <div class="summary-value">{{ stats.deviceStats?.totalLength || 0 }} km</div>
      </div>
      <div class="summary-card">
        <h4>设备运行率</h4>
        <div class="summary-value">{{ runningRate }}%</div>
      </div>
      <div class="summary-card">
        <h4>告警处理率</h4>
        <div class="summary-value">{{ alertResolveRate }}%</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * DashboardView.vue - 数据统计仪表盘页面
 * 
 * 功能说明：
 * 展示电网系统的各类统计数据，包括：
 * 1. 设备统计：变电站、线路、台区、用户数量
 * 2. 运行状态分布：运行中、停运、检修
 * 3. 电压等级分布：220kV、110kV、35kV
 * 4. 告警统计：各级别告警数量
 * 5. 负载趋势：24小时负载变化
 * 6. 台区分布：按变电站统计台区数量
 * 
 * 页面结构：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 数据统计仪表盘                                    [刷新数据]    │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ [⚡ 变电站: 10] [〰 线路: 15] [📍 台区: 50] [👥 用户: 10000]    │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ [运行状态分布] [电压等级分布] [告警统计]                         │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ [负载趋势图表（24小时）]        [台区分布列表]                   │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ [线路总长度: 500km] [设备运行率: 95%] [告警处理率: 80%]          │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - api/stats.ts: 统计 API 服务
 * - types/index.ts: 类型定义
 */

import { ref, onMounted, computed } from 'vue'
import { Refresh } from '@element-plus/icons-vue'
import { getDashboardStats, type DashboardStats } from '../api/stats'

const loading = ref(false)
const stats = ref<DashboardStats>({
  deviceStats: {
    totalSubstations: 0,
    totalLines: 0,
    totalAreas: 0,
    totalCustomers: 0,
    totalLength: 0
  },
  statusStats: {},
  voltageStats: {},
  alertStats: {
    total: 0,
    active: 0,
    acknowledged: 0,
    resolved: 0,
    critical: 0,
    high: 0
  },
  loadTrend: [],
  areaDistribution: []
})

const statusChartRef = ref<HTMLElement | null>(null)
const voltageChartRef = ref<HTMLElement | null>(null)
const trendChartRef = ref<HTMLElement | null>(null)

const runningRate = computed(() => {
  const status = stats.value.statusStats
  const total = (status['运行中'] || 0) + (status['停运'] || 0) + (status['检修'] || 0)
  if (total === 0) return 0
  return Math.round((status['运行中'] || 0) / total * 100)
})

const alertResolveRate = computed(() => {
  const alert = stats.value.alertStats
  if (alert.total === 0) return 100
  return Math.round(alert.resolved / alert.total * 100)
})

const formatNumber = (num: number): string => {
  if (num >= 10000) {
    return (num / 10000).toFixed(1) + '万'
  }
  return num.toString()
}

const refreshData = async () => {
  loading.value = true
  try {
    const response = await getDashboardStats()
    stats.value = response.data
    renderCharts()
  } catch (error) {
    console.error('获取统计数据失败:', error)
  } finally {
    loading.value = false
  }
}

const renderCharts = () => {
  renderStatusChart()
  renderVoltageChart()
  renderTrendChart()
}

const renderStatusChart = () => {
  if (!statusChartRef.value) return
  
  const status = stats.value.statusStats
  const data = [
    { label: '运行中', value: status['运行中'] || 0, color: '#00ff80' },
    { label: '停运', value: status['停运'] || 0, color: '#ff0080' },
    { label: '检修', value: status['检修'] || 0, color: '#ffff00' }
  ]
  
  const total = data.reduce((sum, item) => sum + item.value, 0)
  if (total === 0) return
  
  let currentAngle = 0
  const centerX = 100
  const centerY = 80
  const radius = 60
  
  let svg = `<svg viewBox="0 0 200 160" width="100%" height="100%">`
  
  data.forEach(item => {
    if (item.value === 0) return
    const angle = (item.value / total) * 360
    const startAngle = currentAngle
    const endAngle = currentAngle + angle
    
    const startRad = (startAngle - 90) * Math.PI / 180
    const endRad = (endAngle - 90) * Math.PI / 180
    
    const x1 = centerX + radius * Math.cos(startRad)
    const y1 = centerY + radius * Math.sin(startRad)
    const x2 = centerX + radius * Math.cos(endRad)
    const y2 = centerY + radius * Math.sin(endRad)
    
    const largeArc = angle > 180 ? 1 : 0
    
    svg += `<path d="M ${centerX} ${centerY} L ${x1} ${y1} A ${radius} ${radius} 0 ${largeArc} 1 ${x2} ${y2} Z" fill="${item.color}" opacity="0.8"/>`
    
    currentAngle = endAngle
  })
  
  svg += `</svg>`
  statusChartRef.value.innerHTML = svg
}

const renderVoltageChart = () => {
  if (!voltageChartRef.value) return
  
  const voltage = stats.value.voltageStats
  const data = [
    { label: '220kV', value: voltage['220kV'] || 0, color: '#ff0080' },
    { label: '110kV', value: voltage['110kV'] || 0, color: '#00f0ff' },
    { label: '35kV', value: voltage['35kV'] || 0, color: '#00ff80' }
  ]
  
  const maxValue = Math.max(...data.map(d => d.value), 1)
  
  let html = '<div class="bar-chart">'
  data.forEach(item => {
    const height = (item.value / maxValue) * 100
    html += `
      <div class="bar-item">
        <div class="bar-value">${item.value}</div>
        <div class="bar" style="height: ${height}%; background: ${item.color}"></div>
        <div class="bar-label">${item.label}</div>
      </div>
    `
  })
  html += '</div>'
  
  voltageChartRef.value.innerHTML = html
}

const renderTrendChart = () => {
  if (!trendChartRef.value) return
  
  const trend = stats.value.loadTrend
  if (trend.length === 0) return
  
  const maxValue = Math.max(...trend.map(t => t.value), 1)
  const minValue = Math.min(...trend.map(t => t.value), 0)
  const range = maxValue - minValue || 1
  
  const width = 600
  const height = 200
  const padding = 40
  
  const points = trend.map((item, index) => {
    const x = padding + (index / (trend.length - 1)) * (width - 2 * padding)
    const y = height - padding - ((item.value - minValue) / range) * (height - 2 * padding)
    return `${x},${y}`
  }).join(' ')
  
  const areaPoints = `${padding},${height - padding} ${points} ${width - padding},${height - padding}`
  
  let svg = `<svg viewBox="0 0 ${width} ${height}" width="100%" height="100%" preserveAspectRatio="xMidYMid meet">`
  
  svg += `<defs>
    <linearGradient id="areaGradient" x1="0%" y1="0%" x2="0%" y2="100%">
      <stop offset="0%" style="stop-color:#00f0ff;stop-opacity:0.5"/>
      <stop offset="100%" style="stop-color:#00f0ff;stop-opacity:0.1"/>
    </linearGradient>
  </defs>`
  
  svg += `<polygon points="${areaPoints}" fill="url(#areaGradient)"/>`
  
  svg += `<polyline points="${points}" fill="none" stroke="#00f0ff" stroke-width="2"/>`
  
  trend.forEach((item, index) => {
    if (index % 4 === 0) {
      const x = padding + (index / (trend.length - 1)) * (width - 2 * padding)
      svg += `<text x="${x}" y="${height - 10}" fill="rgba(255,255,255,0.6)" font-size="10" text-anchor="middle">${item.time}</text>`
    }
  })
  
  svg += `</svg>`
  
  trendChartRef.value.innerHTML = svg
}

onMounted(() => {
  refreshData()
})
</script>

<style scoped>
.dashboard-view {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.dashboard-header h1 {
  color: #00f0ff;
  font-size: 24px;
  margin: 0;
}

.stats-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  background: rgba(10, 10, 26, 0.8);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  font-size: 32px;
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
}

.stat-card.substation .stat-icon { background: rgba(255, 0, 128, 0.2); }
.stat-card.line .stat-icon { background: rgba(0, 240, 255, 0.2); }
.stat-card.area .stat-icon { background: rgba(0, 255, 128, 0.2); }
.stat-card.customer .stat-icon { background: rgba(255, 255, 0, 0.2); }

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #ffffff;
}

.stat-label {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.6);
}

.charts-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 24px;
}

.chart-card {
  background: rgba(10, 10, 26, 0.8);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 20px;
}

.chart-card h3 {
  color: #00f0ff;
  font-size: 16px;
  margin: 0 0 16px 0;
}

.chart-card.large {
  grid-column: span 2;
}

.chart-container {
  height: 160px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.alert-stats {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.alert-stat-item {
  text-align: center;
  padding: 16px;
  border-radius: 8px;
  background: rgba(0, 0, 0, 0.3);
}

.alert-value {
  display: block;
  font-size: 24px;
  font-weight: bold;
}

.alert-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.alert-stat-item.critical .alert-value { color: #ff0080; }
.alert-stat-item.high .alert-value { color: #ff6600; }
.alert-stat-item.active .alert-value { color: #ffff00; }
.alert-stat-item.resolved .alert-value { color: #00ff80; }

.distribution-list {
  max-height: 200px;
  overflow-y: auto;
}

.distribution-item {
  display: grid;
  grid-template-columns: 100px 1fr 80px;
  align-items: center;
  gap: 12px;
  padding: 8px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.distribution-name {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.8);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.distribution-bar {
  height: 8px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
  overflow: hidden;
}

.distribution-fill {
  height: 100%;
  background: linear-gradient(90deg, #00f0ff, #0080ff);
  border-radius: 4px;
  transition: width 0.3s ease;
}

.distribution-value {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  text-align: right;
}

.no-data {
  text-align: center;
  padding: 20px;
  color: rgba(255, 255, 255, 0.5);
}

.summary-row {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.summary-card {
  background: rgba(10, 10, 26, 0.8);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 20px;
  text-align: center;
}

.summary-card h4 {
  color: rgba(255, 255, 255, 0.6);
  font-size: 14px;
  margin: 0 0 8px 0;
}

.summary-value {
  color: #00f0ff;
  font-size: 24px;
  font-weight: bold;
}

:deep(.bar-chart) {
  display: flex;
  align-items: flex-end;
  justify-content: space-around;
  height: 100%;
  padding: 20px 0;
}

:deep(.bar-item) {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

:deep(.bar) {
  width: 40px;
  border-radius: 4px 4px 0 0;
  transition: height 0.3s ease;
}

:deep(.bar-value) {
  color: #ffffff;
  font-size: 14px;
  font-weight: bold;
}

:deep(.bar-label) {
  color: rgba(255, 255, 255, 0.6);
  font-size: 12px;
}

@media (max-width: 1200px) {
  .stats-cards {
    grid-template-columns: repeat(2, 1fr);
  }
  
  .charts-row {
    grid-template-columns: 1fr;
  }
  
  .chart-card.large {
    grid-column: span 1;
  }
}
</style>
