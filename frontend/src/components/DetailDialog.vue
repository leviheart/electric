<template>
  <el-dialog
    v-model="visible"
    :title="entityData?.name || '详情'"
    width="600px"
    class="detail-dialog"
    @close="handleClose"
  >
    <div class="detail-content" v-if="entityData">
      <div class="detail-header">
        <div class="status-badge" :class="getStatusClass(entityData.status)">
          {{ entityData.status }}
        </div>
      </div>
      
      <el-descriptions :column="2" border>
        <el-descriptions-item label="ID">{{ entityData.id }}</el-descriptions-item>
        <el-descriptions-item label="名称">{{ entityData.name }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="entityData.status === '运行中' || entityData.status === '正常' ? 'success' : 'warning'">
            {{ entityData.status }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="电压等级" v-if="entityData.voltageLevel">
          {{ entityData.voltageLevel }}
        </el-descriptions-item>
        <el-descriptions-item label="经度" v-if="entityData.longitude">
          {{ entityData.longitude?.toFixed(4) }}
        </el-descriptions-item>
        <el-descriptions-item label="纬度" v-if="entityData.latitude">
          {{ entityData.latitude?.toFixed(4) }}
        </el-descriptions-item>
        <el-descriptions-item label="长度" v-if="entityData.length">
          {{ entityData.length }} km
        </el-descriptions-item>
        <el-descriptions-item label="起点" v-if="entityData.startSubstation">
          {{ entityData.startSubstation }}
        </el-descriptions-item>
        <el-descriptions-item label="终点" v-if="entityData.endSubstation">
          {{ entityData.endSubstation }}
        </el-descriptions-item>
        <el-descriptions-item label="所属变电站" v-if="entityData.substationName">
          {{ entityData.substationName }}
        </el-descriptions-item>
        <el-descriptions-item label="用户数量" v-if="entityData.customerCount">
          {{ entityData.customerCount }}
        </el-descriptions-item>
      </el-descriptions>
      
      <div class="latest-data" v-if="latestData">
        <h4>最新运行数据</h4>
        <el-descriptions :column="3" border size="small">
          <el-descriptions-item label="电压">{{ latestData.voltage?.toFixed(2) }} V</el-descriptions-item>
          <el-descriptions-item label="电流">{{ latestData.current?.toFixed(2) }} A</el-descriptions-item>
          <el-descriptions-item label="功率">{{ latestData.power?.toFixed(2) }} kW</el-descriptions-item>
          <el-descriptions-item label="功率因数">{{ latestData.powerFactor?.toFixed(3) }}</el-descriptions-item>
          <el-descriptions-item label="频率">{{ latestData.frequency?.toFixed(2) }} Hz</el-descriptions-item>
          <el-descriptions-item label="温度">{{ latestData.temperature?.toFixed(1) }} °C</el-descriptions-item>
        </el-descriptions>
        <div class="record-time">
          记录时间: {{ formatTime(latestData.recordTime) }}
        </div>
      </div>
      
      <div class="no-data" v-else-if="dataLoaded">
        <el-empty description="暂无运行数据" :image-size="60" />
      </div>
    </div>
    
    <template #footer>
      <el-button @click="handleClose">关闭</el-button>
      <el-button type="primary" @click="handleShowChart">
        <el-icon><TrendCharts /></el-icon>
        查看图表
      </el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { TrendCharts } from '@element-plus/icons-vue'
import { getLatestOperationData, type OperationData } from '../api/operationData'

interface EntityData {
  id: number
  name: string
  status: string
  voltageLevel?: string
  longitude?: number
  latitude?: number
  length?: number
  startSubstation?: string
  endSubstation?: string
  substationName?: string
  customerCount?: number
}

const props = defineProps<{
  modelValue: boolean
  entityType: string
  entityData: EntityData | null
}>()

const emit = defineEmits<{
  'update:modelValue': [value: boolean]
  'show-chart': [entityType: string, entityId: number, entityName: string]
}>()

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

const formatTime = (time: string): string => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}
</script>

<style scoped>
.detail-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.1), rgba(0, 128, 255, 0.1));
  border-bottom: 1px solid rgba(0, 240, 255, 0.3);
}

.detail-dialog :deep(.el-dialog__title) {
  color: #00f0ff;
}

.detail-content {
  padding: 10px 0;
}

.detail-header {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 15px;
}

.status-badge {
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
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

.latest-data {
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid rgba(0, 240, 255, 0.2);
}

.latest-data h4 {
  color: #00f0ff;
  margin-bottom: 10px;
  font-size: 14px;
}

.record-time {
  margin-top: 10px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  text-align: right;
}

.no-data {
  margin-top: 20px;
}

:deep(.el-descriptions) {
  --el-descriptions-item-bordered-label-background: rgba(0, 240, 255, 0.1);
}

:deep(.el-descriptions__label) {
  color: rgba(255, 255, 255, 0.7);
}

:deep(.el-descriptions__content) {
  color: #ffffff;
}
</style>
