<template>
  <div class="detail-panel" v-if="visible">
    <div class="panel-header">
      <h3>{{ entityData?.name || '详情' }}</h3>
      <el-button type="text" @click="handleClose" class="close-btn">
        <el-icon><Close /></el-icon>
      </el-button>
    </div>
    
    <div class="panel-content" v-if="entityData">
      <div class="status-row">
        <div class="status-badge" :class="getStatusClass(entityData.status)">
          {{ entityData.status }}
        </div>
      </div>
      
      <div class="info-section">
        <div class="info-item">
          <span class="info-label">ID</span>
          <span class="info-value">{{ entityData.id }}</span>
        </div>
        <div class="info-item" v-if="entityData.voltageLevel">
          <span class="info-label">电压等级</span>
          <span class="info-value">{{ entityData.voltageLevel }}</span>
        </div>
        <div class="info-item" v-if="entityData.region">
          <span class="info-label">所属区域</span>
          <span class="info-value">{{ entityData.region }}</span>
        </div>
        <div class="info-item" v-if="entityData.longitude">
          <span class="info-label">经度</span>
          <span class="info-value">{{ entityData.longitude?.toFixed(4) }}</span>
        </div>
        <div class="info-item" v-if="entityData.latitude">
          <span class="info-label">纬度</span>
          <span class="info-value">{{ entityData.latitude?.toFixed(4) }}</span>
        </div>
        <div class="info-item" v-if="entityData.loadRate !== undefined">
          <span class="info-label">负载率</span>
          <span class="info-value" :class="getLoadRateClass(entityData.loadRate)">{{ entityData.loadRate?.toFixed(1) }}%</span>
        </div>
        <div class="info-item" v-if="entityData.capacity">
          <span class="info-label">容量</span>
          <span class="info-value">{{ entityData.capacity }} MVA</span>
        </div>
        <div class="info-item" v-if="entityData.currentPower">
          <span class="info-label">当前功率</span>
          <span class="info-value">{{ entityData.currentPower?.toFixed(1) }} MW</span>
        </div>
        <div class="info-item" v-if="entityData.connectedLines">
          <span class="info-label">连接线路</span>
          <span class="info-value">{{ entityData.connectedLines }} 条</span>
        </div>
        <div class="info-item" v-if="entityData.length">
          <span class="info-label">长度</span>
          <span class="info-value">{{ entityData.length }} km</span>
        </div>
        <div class="info-item" v-if="entityData.lineType">
          <span class="info-label">线路类型</span>
          <span class="info-value">{{ entityData.lineType === 'overhead' ? '架空线' : '电缆' }}</span>
        </div>
        <div class="info-item" v-if="entityData.startSubstation">
          <span class="info-label">起点变电站</span>
          <span class="info-value">{{ entityData.startSubstation }}</span>
        </div>
        <div class="info-item" v-if="entityData.endSubstation">
          <span class="info-label">终点变电站</span>
          <span class="info-value">{{ entityData.endSubstation }}</span>
        </div>
        <div class="info-item" v-if="entityData.substationName">
          <span class="info-label">所属变电站</span>
          <span class="info-value">{{ entityData.substationName }}</span>
        </div>
        <div class="info-item" v-if="entityData.transformerCapacity">
          <span class="info-label">配变容量</span>
          <span class="info-value">{{ entityData.transformerCapacity }} kVA</span>
        </div>
        <div class="info-item" v-if="entityData.customerCount">
          <span class="info-label">用户数量</span>
          <span class="info-value">{{ entityData.customerCount }}</span>
        </div>
        <div class="info-item" v-if="entityData.importantCustomerCount">
          <span class="info-label">重要用户</span>
          <span class="info-value">{{ entityData.importantCustomerCount }}</span>
        </div>
      </div>
      
      <div class="latest-data" v-if="latestData">
        <h4>最新运行数据</h4>
        <div class="data-grid">
          <div class="data-item">
            <span class="data-label">电压</span>
            <span class="data-value">{{ latestData.voltage?.toFixed(2) }} V</span>
          </div>
          <div class="data-item">
            <span class="data-label">电流</span>
            <span class="data-value">{{ latestData.current?.toFixed(2) }} A</span>
          </div>
          <div class="data-item">
            <span class="data-label">功率</span>
            <span class="data-value">{{ latestData.power?.toFixed(2) }} kW</span>
          </div>
          <div class="data-item">
            <span class="data-label">功率因数</span>
            <span class="data-value">{{ latestData.powerFactor?.toFixed(3) }}</span>
          </div>
          <div class="data-item">
            <span class="data-label">频率</span>
            <span class="data-value">{{ latestData.frequency?.toFixed(2) }} Hz</span>
          </div>
          <div class="data-item">
            <span class="data-label">温度</span>
            <span class="data-value">{{ latestData.temperature?.toFixed(1) }} °C</span>
          </div>
        </div>
        <div class="record-time">
          记录时间: {{ formatTime(latestData.recordTime) }}
        </div>
      </div>
      <div class="no-data" v-else-if="dataLoaded">
        <el-empty description="暂无运行数据" :image-size="40" />
      </div>
      
      <div class="panel-actions">
        <el-button type="primary" size="small" class="action-btn" @click="handleShowChart">
          <el-icon><TrendCharts /></el-icon>
          <span>查看图表</span>
        </el-button>
        <el-button type="info" size="small" class="action-btn" @click="handleShowTopology">
          <el-icon><Share /></el-icon>
          <span>查看拓扑</span>
        </el-button>
        <el-button 
          v-if="entityType === 'substation'" 
          type="warning" 
          size="small" 
          class="action-btn" 
          @click="handleShowDiagram"
        >
          <el-icon><OfficeBuilding /></el-icon>
          <span>站内图</span>
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import { Close, TrendCharts, Share, OfficeBuilding } from '@element-plus/icons-vue'
import { getLatestOperationData, type OperationData } from '../api/operationData'

interface EntityData {
  id: number
  name: string
  status: string
  voltageLevel?: string
  longitude?: number
  latitude?: number
  loadRate?: number
  capacity?: number
  currentPower?: number
  connectedLines?: number
  region?: string
  length?: number
  lineType?: string
  startSubstation?: string
  endSubstation?: string
  substationName?: string
  transformerCapacity?: number
  customerCount?: number
  importantCustomerCount?: number
}

const props = defineProps<{
  modelValue: boolean
  entityType: string
  entityData: EntityData | null
}>()

const emit = defineEmits<{
  'update:modelValue': [value: boolean]
  'show-chart': [entityType: string, entityId: number, entityName: string]
  'show-topology': [entityType: string, entityId: number]
}>()

const router = useRouter()
const visible = ref(props.modelValue)
const latestData = ref<OperationData | null>(null)
const dataLoaded = ref(false)

watch(() => props.modelValue, (val) => {
  visible.value = val
  if (val && props.entityData) {
    loadLatestData()
  }
})

watch(visible, (val) => {
  emit('update:modelValue', val)
})

const loadLatestData = async () => {
  if (!props.entityData) return
  
  dataLoaded.value = false
  try {
    const res = await getLatestOperationData(props.entityType, props.entityData.id)
    latestData.value = res.data.data
  } catch (e) {
    console.error('加载最新数据失败', e)
  } finally {
    dataLoaded.value = true
  }
}

const handleClose = () => {
  visible.value = false
  latestData.value = null
}

const handleShowChart = () => {
  if (props.entityData) {
    emit('show-chart', props.entityType, props.entityData.id, props.entityData.name)
  }
}

const handleShowTopology = () => {
  if (props.entityData) {
    emit('show-topology', props.entityType, props.entityData.id)
  }
}

const handleShowDiagram = () => {
  if (props.entityData) {
    router.push(`/station-diagram/${props.entityData.id}`)
  }
}

const getStatusClass = (status: string): string => {
  switch (status) {
    case '运行中':
    case '正常':
      return 'status-active'
    case '备用':
      return 'status-standby'
    case '检修中':
    case '检修':
      return 'status-maintenance'
    default:
      return 'status-error'
  }
}

const getLoadRateClass = (loadRate: number): string => {
  if (loadRate >= 90) return 'load-critical'
  if (loadRate >= 80) return 'load-high'
  if (loadRate >= 60) return 'load-medium'
  return 'load-normal'
}

const formatTime = (time: string): string => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}
</script>

<style scoped>
.detail-panel {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 320px;
  max-height: calc(100% - 20px);
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 8px;
  z-index: 100;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 15px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.1), rgba(0, 128, 255, 0.1));
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.panel-header h3 {
  margin: 0;
  font-size: 14px;
  color: #00f0ff;
  font-weight: 500;
}

.close-btn {
  color: rgba(255, 255, 255, 0.6);
  padding: 0;
}

.close-btn:hover {
  color: #00f0ff;
}

.panel-content {
  padding: 15px;
  overflow-y: auto;
  flex: 1;
}

.panel-content::-webkit-scrollbar {
  width: 6px;
}

.panel-content::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 3px;
}

.panel-content::-webkit-scrollbar-thumb {
  background: linear-gradient(180deg, rgba(0, 240, 255, 0.3), rgba(0, 128, 255, 0.3));
  border-radius: 3px;
}

.panel-content::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(180deg, rgba(0, 240, 255, 0.5), rgba(0, 128, 255, 0.5));
}

.status-row {
  margin-bottom: 15px;
  text-align: right;
}

.status-badge {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 10px;
  font-size: 11px;
  font-weight: 600;
}

.status-active {
  background: rgba(0, 255, 128, 0.2);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.5);
}

.status-standby {
  background: rgba(0, 240, 255, 0.2);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.5);
}

.status-maintenance {
  background: rgba(255, 255, 0, 0.2);
  color: #ffff00;
  border: 1px solid rgba(255, 255, 0, 0.5);
}

.status-error {
  background: rgba(255, 0, 128, 0.2);
  color: #ff0080;
  border: 1px solid rgba(255, 0, 128, 0.5);
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

.load-critical {
  color: #ff0040;
  font-weight: bold;
}

.info-section {
  margin-bottom: 15px;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px solid rgba(0, 240, 255, 0.1);
}

.info-item:last-child {
  border-bottom: none;
}

.info-label {
  color: rgba(255, 255, 255, 0.6);
  font-size: 12px;
  flex-shrink: 0;
}

.info-value {
  color: #ffffff;
  font-size: 12px;
  text-align: right;
  word-break: break-all;
  padding-left: 10px;
}

.latest-data {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid rgba(0, 240, 255, 0.2);
}

.latest-data h4 {
  color: #00f0ff;
  margin: 0 0 10px 0;
  font-size: 13px;
}

.data-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 8px;
}

.data-item {
  background: rgba(0, 240, 255, 0.05);
  padding: 8px 10px;
  border-radius: 4px;
  border: 1px solid rgba(0, 240, 255, 0.1);
}

.data-label {
  display: block;
  font-size: 10px;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 2px;
}

.data-value {
  font-size: 13px;
  color: #00f0ff;
  font-weight: 500;
}

.record-time {
  margin-top: 10px;
  font-size: 11px;
  color: rgba(255, 255, 255, 0.5);
  text-align: right;
}

.no-data {
  margin-top: 15px;
}

.panel-actions {
  margin-top: 15px;
  padding-top: 15px;
  border-top: 1px solid rgba(0, 240, 255, 0.2);
  display: flex;
  gap: 10px;
}

.panel-actions .action-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  padding: 10px 12px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.panel-actions .action-btn .el-icon {
  font-size: 14px;
}

.panel-actions .el-button--primary {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.2), rgba(0, 128, 255, 0.2));
  border: 1px solid rgba(0, 240, 255, 0.4);
  color: #00f0ff;
}

.panel-actions .el-button--primary:hover {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.3), rgba(0, 128, 255, 0.3));
  border-color: rgba(0, 240, 255, 0.6);
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.3);
}

.panel-actions .el-button--info {
  background: linear-gradient(135deg, rgba(128, 128, 255, 0.2), rgba(100, 100, 200, 0.2));
  border: 1px solid rgba(128, 128, 255, 0.4);
  color: #b0b0ff;
}

.panel-actions .el-button--info:hover {
  background: linear-gradient(135deg, rgba(128, 128, 255, 0.3), rgba(100, 100, 200, 0.3));
  border-color: rgba(128, 128, 255, 0.6);
  box-shadow: 0 0 15px rgba(128, 128, 255, 0.3);
}

.panel-actions .el-button--warning {
  background: linear-gradient(135deg, rgba(255, 140, 0, 0.2), rgba(255, 100, 0, 0.2));
  border: 1px solid rgba(255, 140, 0, 0.4);
  color: #ff8c00;
}

.panel-actions .el-button--warning:hover {
  background: linear-gradient(135deg, rgba(255, 140, 0, 0.3), rgba(255, 100, 0, 0.3));
  border-color: rgba(255, 140, 0, 0.6);
  box-shadow: 0 0 15px rgba(255, 140, 0, 0.3);
}
</style>
