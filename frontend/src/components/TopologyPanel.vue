<template>
  <div class="topology-panel" v-if="visible">
    <div class="panel-header">
      <h3>{{ title }}</h3>
      <el-button :icon="Close" circle size="small" @click="close" />
    </div>
    
    <div class="panel-content">
      <div class="center-info">
        <div class="info-header">
          <span class="type-icon">{{ getTypeIcon(topologyData?.centerType) }}</span>
          <span class="type-name">{{ topologyData?.center?.name }}</span>
        </div>
        <div class="info-stats">
          <div class="stat-item">
            <span class="stat-value">{{ topologyData?.statistics?.connectedLineCount || 0 }}</span>
            <span class="stat-label">相连线路</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">{{ topologyData?.statistics?.connectedSubstationCount || 0 }}</span>
            <span class="stat-label">相连变电站</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">{{ topologyData?.statistics?.suppliedAreaCount || 0 }}</span>
            <span class="stat-label">供电台区</span>
          </div>
          <div class="stat-item">
            <span class="stat-value">{{ formatNumber(topologyData?.statistics?.totalCustomers || 0) }}</span>
            <span class="stat-label">用户数</span>
          </div>
        </div>
      </div>
      
      <div class="section" v-if="topologyData?.connectedLines?.length">
        <h4>相连线路</h4>
        <div class="item-list">
          <div 
            v-for="line in topologyData.connectedLines" 
            :key="line.id"
            class="list-item"
            @click="selectLine(line)"
          >
            <span class="item-icon">〰</span>
            <div class="item-info">
              <span class="item-name">{{ line.name }}</span>
              <span class="item-meta">{{ line.startSubstation }} → {{ line.endSubstation }}</span>
            </div>
            <span class="voltage-tag">{{ line.voltageLevel }}</span>
          </div>
        </div>
      </div>
      
      <div class="section" v-if="topologyData?.connectedSubstations?.length">
        <h4>相连变电站</h4>
        <div class="item-list">
          <div 
            v-for="sub in topologyData.connectedSubstations" 
            :key="sub.id"
            class="list-item"
            @click="selectSubstation(sub)"
          >
            <span class="item-icon">⚡</span>
            <div class="item-info">
              <span class="item-name">{{ sub.name }}</span>
              <span class="item-meta">{{ sub.voltageLevel }} · {{ sub.status }}</span>
            </div>
            <el-button size="small" @click.stop="locateDevice(sub)">定位</el-button>
          </div>
        </div>
      </div>
      
      <div class="section" v-if="topologyData?.suppliedAreas?.length">
        <h4>供电台区</h4>
        <div class="item-list scrollable">
          <div 
            v-for="area in topologyData.suppliedAreas" 
            :key="area.id"
            class="list-item"
            @click="selectArea(area)"
          >
            <span class="item-icon">📍</span>
            <div class="item-info">
              <span class="item-name">{{ area.name }}</span>
              <span class="item-meta">用户: {{ area.customerCount }} · {{ area.status }}</span>
            </div>
            <el-button size="small" @click.stop="locateDevice(area)">定位</el-button>
          </div>
        </div>
      </div>
      
      <div class="actions">
        <el-button type="danger" @click="analyzeFault">
          <el-icon><Warning /></el-icon>
          故障影响分析
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * TopologyPanel.vue - 拓扑关系面板组件
 * 
 * 功能说明：
 * 展示设备的拓扑关系信息，包括：
 * 1. 中心设备信息
 * 2. 相连的线路列表
 * 3. 相连的变电站列表
 * 4. 供电的台区列表
 * 5. 故障影响分析
 * 
 * 组件结构：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 拓扑关系                                              [✕]       │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ ⚡ 朝阳220kV变电站                                               │
 * │ [相连线路: 3] [相连变电站: 2] [供电台区: 10] [用户数: 5000]      │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ 相连线路                                                         │
 * │ 〰 朝阳-海淀线    朝阳→海淀              220kV                  │
 * │ 〰 朝阳-通州线    朝阳→通州              110kV                  │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ 相连变电站                                                       │
 * │ ⚡ 海淀110kV变电站   110kV·运行中        [定位]                  │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ 供电台区                                                         │
 * │ 📍 朝阳区A台区   用户: 500·正常          [定位]                  │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ [⚠ 故障影响分析]                                                 │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 事件：
 * - close: 关闭面板
 * - locate: 定位设备
 * - select: 选择设备
 * - analyze: 故障分析
 */

import { computed } from 'vue'
import { Close, Warning } from '@element-plus/icons-vue'
import type { TopologyData, TopologyStats } from '../api/topology'
import type { Substation, TransmissionLine, Area } from '../types'

const props = defineProps<{
  visible: boolean
  topologyData: TopologyData | null
}>()

const emit = defineEmits<{
  close: []
  locate: [device: Substation | TransmissionLine | Area]
  select: [type: string, id: number]
  analyze: [type: string, id: number]
}>()

const title = computed(() => {
  if (!props.topologyData) return '拓扑关系'
  switch (props.topologyData.centerType) {
    case 'SUBSTATION': return '变电站拓扑'
    case 'LINE': return '线路拓扑'
    case 'AREA': return '台区拓扑'
    default: return '拓扑关系'
  }
})

const getTypeIcon = (type?: string): string => {
  switch (type) {
    case 'SUBSTATION': return '⚡'
    case 'LINE': return '〰'
    case 'AREA': return '📍'
    default: return '📌'
  }
}

const formatNumber = (num: number): string => {
  if (num >= 10000) {
    return (num / 10000).toFixed(1) + '万'
  }
  return num.toString()
}

const close = () => {
  emit('close')
}

const locateDevice = (device: any) => {
  emit('locate', device)
}

const selectSubstation = (sub: Substation) => {
  emit('select', 'substation', sub.id)
}

const selectLine = (line: TransmissionLine) => {
  emit('select', 'line', line.id)
}

const selectArea = (area: Area) => {
  emit('select', 'area', area.id)
}

const analyzeFault = () => {
  if (props.topologyData) {
    const type = props.topologyData.centerType.toLowerCase()
    const id = (props.topologyData.center as any).id
    emit('analyze', type, id)
  }
}
</script>

<style scoped>
.topology-panel {
  position: absolute;
  top: 80px;
  right: 20px;
  width: 320px;
  max-height: calc(100% - 100px);
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
  overflow: hidden;
  z-index: 100;
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

.panel-content {
  padding: 16px;
  max-height: 500px;
  overflow-y: auto;
}

.center-info {
  margin-bottom: 16px;
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.info-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 12px;
}

.type-icon {
  font-size: 24px;
}

.type-name {
  font-size: 16px;
  font-weight: 500;
  color: #ffffff;
}

.info-stats {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 12px;
}

.stat-item {
  text-align: center;
  padding: 8px;
  background: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
}

.stat-value {
  display: block;
  font-size: 18px;
  font-weight: bold;
  color: #00f0ff;
}

.stat-label {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.6);
}

.section {
  margin-bottom: 16px;
}

.section h4 {
  margin: 0 0 10px 0;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.8);
}

.item-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.item-list.scrollable {
  max-height: 150px;
  overflow-y: auto;
}

.list-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px;
  background: rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s;
}

.list-item:hover {
  background: rgba(0, 240, 255, 0.1);
}

.item-icon {
  font-size: 16px;
}

.item-info {
  flex: 1;
  min-width: 0;
}

.item-name {
  display: block;
  font-size: 13px;
  color: #ffffff;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.item-meta {
  display: block;
  font-size: 11px;
  color: rgba(255, 255, 255, 0.5);
}

.voltage-tag {
  padding: 2px 8px;
  background: rgba(0, 240, 255, 0.2);
  border-radius: 4px;
  font-size: 11px;
  color: #00f0ff;
}

.actions {
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
}

.actions :deep(.el-button) {
  width: 100%;
}

.item-list::-webkit-scrollbar {
  width: 4px;
}

.item-list::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.2);
}

.item-list::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 2px;
}
</style>
