<template>
  <div class="natural-disaster-panel" :class="{ 'has-active': activeDisaster }">
    <div class="disaster-header">
      <div class="header-title">
        <span class="header-icon">🌪️</span>
        <span>自然灾害预警</span>
        <el-badge :value="activeCount" :max="99" class="header-badge" v-if="activeCount > 0" />
      </div>
      <div class="header-actions">
        <el-button 
          v-if="activeDisaster" 
          size="small" 
          type="danger" 
          plain 
          @click="clearDisaster"
        >
          清除显示
        </el-button>
        <el-button size="small" text @click="refreshDisasters">
          <el-icon><Refresh /></el-icon>
        </el-button>
        <el-button size="small" text @click="closePanel">
          <el-icon><Close /></el-icon>
        </el-button>
      </div>
    </div>

    <div class="disaster-stats">
      <div 
        class="stat-item" 
        v-for="stat in disasterStats" 
        :key="stat.type"
        :class="{ active: selectedType === stat.type }"
        @click="toggleType(stat.type)"
      >
        <span class="stat-icon">{{ stat.icon }}</span>
        <span class="stat-count">{{ stat.count }}</span>
        <span class="stat-label">{{ stat.label }}</span>
      </div>
    </div>

    <div class="disaster-list">
      <div 
        class="disaster-item" 
        v-for="disaster in filteredDisasters" 
        :key="disaster.id"
        :class="[`disaster-${disaster.type.toLowerCase()}`, { 'is-active': activeDisaster?.id === disaster.id }]"
        @click="handleLocate(disaster)"
      >
        <div class="disaster-icon">{{ getDisasterIcon(disaster.type) }}</div>
        <div class="disaster-info">
          <div class="disaster-title">
            <span>{{ disaster.title }}</span>
            <el-tag :type="getLevelType(disaster.level)" size="small">
              {{ getLevelLabel(disaster.level) }}
            </el-tag>
          </div>
          <div class="disaster-desc">{{ disaster.description }}</div>
          <div class="disaster-meta">
            <span class="disaster-location">
              <el-icon><Location /></el-icon>
              {{ disaster.location }}
            </span>
            <span class="disaster-radius">半径 {{ (disaster.radius / 1000).toFixed(1) }}km</span>
          </div>
        </div>
        <div class="disaster-actions" @click.stop>
          <el-button 
            type="primary" 
            size="small" 
            circle
            :icon="activeDisaster?.id === disaster.id ? 'Check' : 'Aim'"
            @click="handleLocate(disaster)"
          />
        </div>
      </div>
    </div>

    <div class="disaster-footer">
      <div class="legend">
        <div class="legend-item" v-for="item in legendItems" :key="item.type">
          <span class="legend-dot" :style="{ background: item.color }"></span>
          <span class="legend-text">{{ item.label }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { Refresh, Location, Close } from '@element-plus/icons-vue'

interface Disaster {
  id: string
  type: string
  level: string
  title: string
  description: string
  location: string
  latitude: number
  longitude: number
  radius: number
  timestamp: string
  affectedDevices: number
}

const emit = defineEmits<{
  locate: [disaster: Disaster]
  clear: []
  close: []
  'update-map': [disasters: Disaster[]]
}>()

const selectedType = ref('')
const activeDisaster = ref<Disaster | null>(null)

const disasters = ref<Disaster[]>([
  {
    id: '1',
    type: 'STRONG_WIND',
    level: 'HIGH',
    title: '大风橙色预警',
    description: '预计未来6小时内将出现10级以上大风，可能影响输电线路安全',
    location: '城东区',
    latitude: 30.58,
    longitude: 114.35,
    radius: 5000,
    timestamp: '2024-01-15T10:30:00',
    affectedDevices: 12
  },
  {
    id: '2',
    type: 'WILDFIRE',
    level: 'CRITICAL',
    title: '山火红色预警',
    description: '北山区域发现山火，距离110kV北山线仅2公里，请立即关注',
    location: '北山区',
    latitude: 30.62,
    longitude: 114.42,
    radius: 3000,
    timestamp: '2024-01-15T09:45:00',
    affectedDevices: 8
  },
  {
    id: '3',
    type: 'ICE_STORM',
    level: 'MEDIUM',
    title: '冰冻黄色预警',
    description: '高海拔区域气温骤降，可能出现线路覆冰情况',
    location: '西岭山区',
    latitude: 30.55,
    longitude: 114.28,
    radius: 8000,
    timestamp: '2024-01-15T08:00:00',
    affectedDevices: 5
  },
  {
    id: '4',
    type: 'FLOOD',
    level: 'HIGH',
    title: '洪涝橙色预警',
    description: '河流水位上涨，可能淹没低洼地区电力设施',
    location: '河滨区',
    latitude: 30.52,
    longitude: 114.38,
    radius: 4000,
    timestamp: '2024-01-15T11:00:00',
    affectedDevices: 6
  },
  {
    id: '5',
    type: 'LIGHTNING',
    level: 'MEDIUM',
    title: '雷电黄色预警',
    description: '强对流天气即将来临，请注意防雷保护',
    location: '全市范围',
    latitude: 30.57,
    longitude: 114.35,
    radius: 15000,
    timestamp: '2024-01-15T12:00:00',
    affectedDevices: 25
  },
  {
    id: '6',
    type: 'EARTHQUAKE',
    level: 'HIGH',
    title: '地震橙色预警',
    description: '监测到4.5级地震，震源深度10公里，可能影响供电设施',
    location: '西南山区',
    latitude: 30.48,
    longitude: 114.22,
    radius: 10000,
    timestamp: '2024-01-15T14:00:00',
    affectedDevices: 15
  }
])

const activeCount = computed(() => disasters.value.length)

const disasterStats = computed(() => [
  { type: 'STRONG_WIND', icon: '💨', label: '大风', count: disasters.value.filter(d => d.type === 'STRONG_WIND').length },
  { type: 'WILDFIRE', icon: '🔥', label: '山火', count: disasters.value.filter(d => d.type === 'WILDFIRE').length },
  { type: 'ICE_STORM', icon: '❄️', label: '冰寒', count: disasters.value.filter(d => d.type === 'ICE_STORM').length },
  { type: 'FLOOD', icon: '🌊', label: '洪涝', count: disasters.value.filter(d => d.type === 'FLOOD').length },
  { type: 'LIGHTNING', icon: '⛈️', label: '雷电', count: disasters.value.filter(d => d.type === 'LIGHTNING').length },
  { type: 'EARTHQUAKE', icon: '🌍', label: '地震', count: disasters.value.filter(d => d.type === 'EARTHQUAKE').length }
])

const filteredDisasters = computed(() => {
  if (!selectedType.value) return disasters.value
  return disasters.value.filter(d => d.type === selectedType.value)
})

const legendItems = [
  { type: 'STRONG_WIND', label: '大风', color: '#00bcd4' },
  { type: 'WILDFIRE', label: '山火', color: '#ff5722' },
  { type: 'ICE_STORM', label: '冰寒', color: '#03a9f4' },
  { type: 'FLOOD', label: '洪涝', color: '#2196f3' },
  { type: 'LIGHTNING', label: '雷电', color: '#ffeb3b' },
  { type: 'EARTHQUAKE', label: '地震', color: '#9c27b0' }
]

const toggleType = (type: string) => {
  selectedType.value = selectedType.value === type ? '' : type
}

const getDisasterIcon = (type: string): string => {
  const icons: Record<string, string> = {
    STRONG_WIND: '💨',
    WILDFIRE: '🔥',
    ICE_STORM: '❄️',
    FLOOD: '🌊',
    LIGHTNING: '⛈️',
    EARTHQUAKE: '🌍'
  }
  return icons[type] || '⚠️'
}

const getLevelLabel = (level: string): string => {
  const labels: Record<string, string> = {
    CRITICAL: '红',
    HIGH: '橙',
    MEDIUM: '黄',
    LOW: '蓝'
  }
  return labels[level] || level
}

const getLevelType = (level: string): string => {
  const types: Record<string, string> = {
    CRITICAL: 'danger',
    HIGH: 'warning',
    MEDIUM: 'primary',
    LOW: 'info'
  }
  return types[level] || 'info'
}

const handleLocate = (disaster: Disaster) => {
  activeDisaster.value = disaster
  emit('locate', disaster)
}

const clearDisaster = () => {
  activeDisaster.value = null
  emit('clear')
}

const closePanel = () => {
  activeDisaster.value = null
  emit('close')
}

const refreshDisasters = () => {
  emit('update-map', disasters.value)
}

onMounted(() => {
  emit('update-map', disasters.value)
})
</script>

<style scoped>
.natural-disaster-panel {
  position: fixed;
  left: 20px;
  top: 130px;
  width: 320px;
  max-height: calc(100vh - 160px);
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(255, 100, 50, 0.3);
  border-radius: 12px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  z-index: 100;
  transition: all 0.3s ease;
}

.natural-disaster-panel.has-active {
  border-color: rgba(255, 100, 50, 0.6);
  box-shadow: 0 0 30px rgba(255, 100, 50, 0.2);
}

.disaster-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: linear-gradient(90deg, rgba(255, 100, 50, 0.2), transparent);
  border-bottom: 1px solid rgba(255, 100, 50, 0.2);
}

.header-title {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #ff6432;
  font-size: 14px;
  font-weight: 600;
}

.header-icon {
  font-size: 18px;
}

.header-badge :deep(.el-badge__content) {
  background: #ff6432;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.disaster-stats {
  display: flex;
  padding: 10px;
  gap: 6px;
  border-bottom: 1px solid rgba(255, 100, 50, 0.15);
}

.stat-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 6px 2px;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.2s;
}

.stat-item:hover {
  background: rgba(255, 100, 50, 0.1);
}

.stat-item.active {
  background: rgba(255, 100, 50, 0.2);
}

.stat-icon {
  font-size: 16px;
}

.stat-count {
  font-size: 14px;
  font-weight: 600;
  color: #ff6432;
}

.stat-label {
  font-size: 9px;
  color: #6a7a8a;
}

.disaster-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
  max-height: 350px;
}

.disaster-item {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  padding: 10px;
  margin-bottom: 6px;
  border-radius: 8px;
  background: rgba(0, 0, 0, 0.3);
  border-left: 3px solid;
  cursor: pointer;
  transition: all 0.2s;
}

.disaster-item:hover {
  background: rgba(255, 100, 50, 0.1);
  transform: translateX(4px);
}

.disaster-item.is-active {
  background: rgba(255, 100, 50, 0.2);
  box-shadow: 0 0 10px rgba(255, 100, 50, 0.3);
}

.disaster-item.disaster-strong_wind { border-left-color: #00bcd4; }
.disaster-item.disaster-wildfire { border-left-color: #ff5722; }
.disaster-item.disaster-ice_storm { border-left-color: #03a9f4; }
.disaster-item.disaster-flood { border-left-color: #2196f3; }
.disaster-item.disaster-lightning { border-left-color: #ffeb3b; }
.disaster-item.disaster-earthquake { border-left-color: #9c27b0; }

.disaster-icon {
  font-size: 24px;
  line-height: 1;
}

.disaster-info {
  flex: 1;
  min-width: 0;
}

.disaster-title {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 8px;
  font-size: 12px;
  font-weight: 600;
  color: #e8e8e8;
  margin-bottom: 4px;
}

.disaster-desc {
  font-size: 10px;
  color: #8a9aaa;
  line-height: 1.4;
  margin-bottom: 6px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.disaster-meta {
  display: flex;
  justify-content: space-between;
  font-size: 9px;
  color: #5a6a7a;
}

.disaster-location {
  display: flex;
  align-items: center;
  gap: 2px;
}

.disaster-actions {
  display: flex;
  align-items: center;
}

.disaster-footer {
  padding: 10px;
  border-top: 1px solid rgba(255, 100, 50, 0.15);
  background: rgba(0, 0, 0, 0.2);
}

.legend {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 4px;
}

.legend-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.legend-text {
  font-size: 10px;
  color: #6a7a8a;
}

.disaster-list::-webkit-scrollbar {
  width: 4px;
}

.disaster-list::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.2);
}

.disaster-list::-webkit-scrollbar-thumb {
  background: rgba(255, 100, 50, 0.3);
  border-radius: 2px;
}
</style>
