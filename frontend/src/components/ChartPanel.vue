<template>
  <div class="chart-panel" v-if="visible">
    <div class="panel-header">
      <h3>{{ entityName }} - 运行数据</h3>
      <el-button type="text" @click="handleClose" class="close-btn">
        <el-icon><Close /></el-icon>
      </el-button>
    </div>
    
    <div class="panel-controls">
      <div class="date-picker-wrapper">
        <el-date-picker
          v-model="selectedDate"
          type="date"
          placeholder="选择日期"
          format="YYYY-MM-DD"
          value-format="YYYY-MM-DD"
          :clearable="false"
          size="small"
          @change="loadData"
        >
          <template #default="{ date }">
            <div class="date-cell" :class="{ 'has-data': hasDataDate(date) }">
              <span class="date-text">{{ date.getDate() }}</span>
              <span v-if="hasDataDate(date)" class="data-dot"></span>
            </div>
          </template>
        </el-date-picker>
      </div>
      
      <div class="chart-type-selector">
        <el-radio-group v-model="chartType" size="small" @change="updateChart">
          <el-radio-button label="all">综合</el-radio-button>
          <el-radio-button label="voltage">电压</el-radio-button>
          <el-radio-button label="current">电流</el-radio-button>
          <el-radio-button label="power">功率</el-radio-button>
        </el-radio-group>
      </div>
    </div>
    
    <div class="panel-content">
      <div class="chart-container" ref="chartRef" v-loading="loading"></div>
      
      <div class="data-stats" v-if="dataList.length > 0">
        <div class="stat-item">
          <span class="stat-label">平均电压</span>
          <span class="stat-value">{{ avgVoltage }} V</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">平均电流</span>
          <span class="stat-value">{{ avgCurrent }} A</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">平均功率</span>
          <span class="stat-value">{{ avgPower }} kW</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">数据点数</span>
          <span class="stat-value">{{ dataList.length }}</span>
        </div>
      </div>
      
      <div class="load-more" v-if="hasMoreData">
        <el-button size="small" @click="loadMoreData" :loading="loadingMore">
          加载更多数据
        </el-button>
      </div>
      
      <el-empty v-if="dataList.length === 0 && loaded" description="该日期暂无数据" :image-size="40" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch, computed, nextTick } from 'vue'
import { Close } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { getOperationDataByDate, getDatesWithData, type OperationData } from '../api/operationData'

const MAX_POINTS = 100
const PAGE_SIZE = 48

const props = defineProps<{
  modelValue: boolean
  entityType: string
  entityId: number
  entityName: string
}>()

const emit = defineEmits<{
  'update:modelValue': [value: boolean]
}>()

const visible = ref(props.modelValue)
const selectedDate = ref(new Date().toISOString().split('T')[0])
const chartType = ref('all')
const dataList = ref<OperationData[]>([])
const rawData = ref<OperationData[]>([])
const dataDates = ref<Set<string>>(new Set())
const loaded = ref(false)
const loading = ref(false)
const loadingMore = ref(false)
const hasMoreData = ref(false)
const chartRef = ref<HTMLElement | null>(null)
let chart: echarts.ECharts | null = null

watch(() => props.modelValue, async (val) => {
  visible.value = val
  if (val) {
    await loadDates()
    loadData()
  }
})

watch(visible, (val) => {
  emit('update:modelValue', val)
})

const avgVoltage = computed(() => {
  if (dataList.value.length === 0) return '0.00'
  const avg = dataList.value.reduce((sum, d) => sum + (d.voltage || 0), 0) / dataList.value.length
  return avg.toFixed(2)
})

const avgCurrent = computed(() => {
  if (dataList.value.length === 0) return '0.00'
  const avg = dataList.value.reduce((sum, d) => sum + (d.current || 0), 0) / dataList.value.length
  return avg.toFixed(2)
})

const avgPower = computed(() => {
  if (dataList.value.length === 0) return '0.00'
  const avg = dataList.value.reduce((sum, d) => sum + (d.power || 0), 0) / dataList.value.length
  return avg.toFixed(2)
})

const loadDates = async () => {
  try {
    const res = await getDatesWithData(props.entityType, props.entityId)
    dataDates.value = new Set(res.data.data.map(d => d.toString()))
  } catch (e) {
    console.error('加载日期列表失败', e)
  }
}

const hasDataDate = (date: Date): boolean => {
  const dateStr = date.toISOString().split('T')[0]
  return dataDates.value.has(dateStr)
}

const loadData = async () => {
  if (!selectedDate.value) return
  
  loading.value = true
  loaded.value = false
  
  try {
    const res = await getOperationDataByDate(props.entityType, props.entityId, selectedDate.value)
    rawData.value = res.data.data.sort((a, b) => 
      new Date(a.recordTime).getTime() - new Date(b.recordTime).getTime()
    )
    
    hasMoreData.value = rawData.value.length > PAGE_SIZE
    
    dataList.value = sampleData(rawData.value.slice(0, PAGE_SIZE))
    
    await nextTick()
    updateChart()
  } catch (e) {
    console.error('加载数据失败', e)
  } finally {
    loaded.value = true
    loading.value = false
  }
}

const loadMoreData = async () => {
  loadingMore.value = true
  
  try {
    const currentLength = dataList.value.length
    const moreData = rawData.value.slice(currentLength, currentLength + PAGE_SIZE)
    
    if (moreData.length > 0) {
      const allData = [...rawData.value.slice(0, currentLength + PAGE_SIZE)]
      dataList.value = sampleData(allData)
      hasMoreData.value = currentLength + PAGE_SIZE < rawData.value.length
      updateChart()
    }
  } finally {
    loadingMore.value = false
  }
}

const sampleData = (data: OperationData[]): OperationData[] => {
  if (data.length <= MAX_POINTS) return data
  
  const sampled: OperationData[] = []
  const step = data.length / MAX_POINTS
  
  for (let i = 0; i < MAX_POINTS; i++) {
    const index = Math.floor(i * step)
    sampled.push(data[index])
  }
  
  return sampled
}

const updateChart = () => {
  if (!chartRef.value) return
  
  if (!chart) {
    chart = echarts.init(chartRef.value, undefined, { renderer: 'canvas' })
  }
  
  const times = dataList.value.map(d => {
    const date = new Date(d.recordTime)
    return `${date.getHours()}:${date.getMinutes().toString().padStart(2, '0')}`
  })
  
  const voltageData = dataList.value.map(d => d.voltage)
  const currentData = dataList.value.map(d => d.current)
  const powerData = dataList.value.map(d => d.power)
  
  let series: any[] = []
  let yAxis: any[] = []
  
  if (chartType.value === 'all') {
    series = [
      { name: '电压', type: 'line', data: voltageData, smooth: true, yAxisIndex: 0 },
      { name: '电流', type: 'line', data: currentData, smooth: true, yAxisIndex: 1 },
      { name: '功率', type: 'line', data: powerData, smooth: true, yAxisIndex: 1 }
    ]
    yAxis = [
      { type: 'value', name: '电压(V)', position: 'left' },
      { type: 'value', name: '电流(A)/功率(kW)', position: 'right' }
    ]
  } else if (chartType.value === 'voltage') {
    series = [{ name: '电压', type: 'line', data: voltageData, smooth: true, areaStyle: { opacity: 0.3 } }]
    yAxis = [{ type: 'value', name: '电压(V)' }]
  } else if (chartType.value === 'current') {
    series = [{ name: '电流', type: 'line', data: currentData, smooth: true, areaStyle: { opacity: 0.3 } }]
    yAxis = [{ type: 'value', name: '电流(A)' }]
  } else {
    series = [{ name: '功率', type: 'line', data: powerData, smooth: true, areaStyle: { opacity: 0.3 } }]
    yAxis = [{ type: 'value', name: '功率(kW)' }]
  }
  
  const option = {
    backgroundColor: 'transparent',
    animation: dataList.value.length <= 50,
    animationDuration: 500,
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(10, 10, 26, 0.9)',
      borderColor: 'rgba(0, 240, 255, 0.3)',
      textStyle: { color: '#fff', fontSize: 11 },
      formatter: (params: any[]) => {
        if (!params || params.length === 0) return ''
        let result = `<div style="margin-bottom: 4px;">${params[0].axisValue}</div>`
        params.forEach((item: any) => {
          const value = item.value !== undefined ? Number(item.value).toFixed(2) : '-'
          result += `<div style="display: flex; justify-content: space-between; gap: 20px;">
            <span>${item.marker} ${item.seriesName}</span>
            <span style="font-weight: bold;">${value}</span>
          </div>`
        })
        return result
      }
    },
    legend: {
      data: series.map(s => s.name),
      textStyle: { color: '#fff', fontSize: 10 },
      top: 5,
      itemWidth: 15,
      itemHeight: 8
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: 35,
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: times,
      axisLine: { lineStyle: { color: 'rgba(0, 240, 255, 0.3)' } },
      axisLabel: { color: '#fff', fontSize: 9, interval: Math.floor(times.length / 6) }
    },
    yAxis: yAxis.map(y => ({
      ...y,
      axisLine: { lineStyle: { color: 'rgba(0, 240, 255, 0.3)' } },
      axisLabel: { color: '#fff', fontSize: 9 },
      splitLine: { lineStyle: { color: 'rgba(0, 240, 255, 0.1)' } },
      nameTextStyle: { fontSize: 9 }
    })),
    series: series.map((s, i) => ({
      ...s,
      symbol: 'none',
      lineStyle: { 
        color: ['#00f0ff', '#ff0080', '#00ff80'][i % 3],
        width: 1.5
      },
      itemStyle: { color: ['#00f0ff', '#ff0080', '#00ff80'][i % 3] }
    }))
  }
  
  chart.setOption(option, true)
}

const handleClose = () => {
  visible.value = false
  dataList.value = []
  rawData.value = []
  if (chart) {
    chart.dispose()
    chart = null
  }
}
</script>

<style scoped>
.chart-panel {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 400px;
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

.panel-controls {
  padding: 10px 15px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.date-picker-wrapper :deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
}

.date-picker-wrapper :deep(.el-input__inner) {
  color: #fff;
  font-size: 12px;
}

.date-cell {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.date-cell.has-data {
  background: rgba(0, 240, 255, 0.1);
}

.date-text {
  font-size: 12px;
}

.data-dot {
  position: absolute;
  bottom: 4px;
  left: 50%;
  transform: translateX(-50%);
  width: 6px;
  height: 6px;
  background: #00f0ff;
  border-radius: 50%;
  box-shadow: 0 0 6px 2px rgba(0, 240, 255, 0.5);
}

.chart-type-selector :deep(.el-radio-button__inner) {
  background: rgba(0, 0, 0, 0.3);
  border-color: rgba(0, 240, 255, 0.3);
  color: #fff;
  font-size: 11px;
  padding: 5px 10px;
}

.chart-type-selector :deep(.el-radio-button__original-radio:checked + .el-radio-button__inner) {
  background: rgba(0, 240, 255, 0.3);
  border-color: #00f0ff;
  color: #00f0ff;
}

.panel-content {
  padding: 10px 15px;
  overflow-y: auto;
  flex: 1;
}

.chart-container {
  width: 100%;
  height: 200px;
}

.data-stats {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 8px;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid rgba(0, 240, 255, 0.2);
}

.stat-item {
  background: rgba(0, 240, 255, 0.05);
  padding: 8px;
  border-radius: 4px;
  text-align: center;
}

.stat-label {
  display: block;
  font-size: 10px;
  color: rgba(255, 255, 255, 0.5);
  margin-bottom: 3px;
}

.stat-value {
  font-size: 14px;
  font-weight: 600;
  color: #00f0ff;
}

.load-more {
  text-align: center;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid rgba(0, 240, 255, 0.2);
}
</style>
