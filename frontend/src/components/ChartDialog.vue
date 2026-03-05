<template>
  <el-dialog
    v-model="visible"
    :title="entityName + ' - 运行数据图表'"
    width="900px"
    class="chart-dialog"
    @close="handleClose"
  >
    <div class="chart-content">
      <div class="chart-controls">
        <div class="date-picker-wrapper">
          <span class="label">选择日期:</span>
          <el-date-picker
            v-model="selectedDate"
            type="date"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            :clearable="false"
            :disabled-date="disabledDate"
            @change="loadData"
          >
            <template #default="cell">
              <div class="date-cell">
                <span>{{ cell.text }}</span>
                <span v-if="hasDataDate(cell.date)" class="data-dot"></span>
              </div>
            </template>
          </el-date-picker>
        </div>
        
        <div class="chart-type-selector">
          <el-radio-group v-model="chartType" @change="updateChart">
            <el-radio-button label="voltage">电压</el-radio-button>
            <el-radio-button label="current">电流</el-radio-button>
            <el-radio-button label="power">功率</el-radio-button>
            <el-radio-button label="all">综合</el-radio-button>
          </el-radio-group>
        </div>
      </div>
      
      <div class="chart-container" ref="chartRef"></div>
      
      <div class="data-stats" v-if="dataList.length > 0">
        <div class="stat-item">
          <span class="stat-label">平均电压</span>
          <span class="stat-value">{{ avgVoltage.toFixed(2) }} V</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">平均电流</span>
          <span class="stat-value">{{ avgCurrent.toFixed(2) }} A</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">平均功率</span>
          <span class="stat-value">{{ avgPower.toFixed(2) }} kW</span>
        </div>
        <div class="stat-item">
          <span class="stat-label">数据点数</span>
          <span class="stat-value">{{ dataList.length }}</span>
        </div>
      </div>
      
      <el-empty v-if="dataList.length === 0 && loaded" description="该日期暂无数据" />
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch, computed, onMounted, nextTick } from 'vue'
import * as echarts from 'echarts'
import { getOperationDataByDate, getDatesWithData, type OperationData } from '../api/operationData'

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
const dataDates = ref<Set<string>>(new Set())
const loaded = ref(false)
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
  if (dataList.value.length === 0) return 0
  return dataList.value.reduce((sum, d) => sum + (d.voltage || 0), 0) / dataList.value.length
})

const avgCurrent = computed(() => {
  if (dataList.value.length === 0) return 0
  return dataList.value.reduce((sum, d) => sum + (d.current || 0), 0) / dataList.value.length
})

const avgPower = computed(() => {
  if (dataList.value.length === 0) return 0
  return dataList.value.reduce((sum, d) => sum + (d.power || 0), 0) / dataList.value.length
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

const disabledDate = (date: Date): boolean => {
  return false
}

const loadData = async () => {
  if (!selectedDate.value) return
  
  loaded.value = false
  try {
    const res = await getOperationDataByDate(props.entityType, props.entityId, selectedDate.value)
    dataList.value = res.data.data.sort((a, b) => 
      new Date(a.recordTime).getTime() - new Date(b.recordTime).getTime()
    )
    await nextTick()
    updateChart()
  } catch (e) {
    console.error('加载数据失败', e)
  } finally {
    loaded.value = true
  }
}

const updateChart = () => {
  if (!chartRef.value) return
  
  if (!chart) {
    chart = echarts.init(chartRef.value)
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
    tooltip: {
      trigger: 'axis',
      backgroundColor: 'rgba(10, 10, 26, 0.9)',
      borderColor: 'rgba(0, 240, 255, 0.3)',
      textStyle: { color: '#fff' }
    },
    legend: {
      data: series.map(s => s.name),
      textStyle: { color: '#fff' },
      top: 10
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: 60,
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: times,
      axisLine: { lineStyle: { color: 'rgba(0, 240, 255, 0.3)' } },
      axisLabel: { color: '#fff' }
    },
    yAxis: yAxis.map(y => ({
      ...y,
      axisLine: { lineStyle: { color: 'rgba(0, 240, 255, 0.3)' } },
      axisLabel: { color: '#fff' },
      splitLine: { lineStyle: { color: 'rgba(0, 240, 255, 0.1)' } }
    })),
    series: series.map((s, i) => ({
      ...s,
      lineStyle: { 
        color: ['#00f0ff', '#ff0080', '#00ff80'][i % 3],
        width: 2
      },
      itemStyle: { color: ['#00f0ff', '#ff0080', '#00ff80'][i % 3] }
    }))
  }
  
  chart.setOption(option, true)
}

const handleClose = () => {
  visible.value = false
  dataList.value = []
  if (chart) {
    chart.dispose()
    chart = null
  }
}

onMounted(() => {
  window.addEventListener('resize', () => {
    chart?.resize()
  })
})
</script>

<style scoped>
.chart-dialog :deep(.el-dialog__header) {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.1), rgba(0, 128, 255, 0.1));
  border-bottom: 1px solid rgba(0, 240, 255, 0.3);
}

.chart-dialog :deep(.el-dialog__title) {
  color: #00f0ff;
}

.chart-content {
  min-height: 400px;
}

.chart-controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.date-picker-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
}

.date-picker-wrapper .label {
  color: rgba(255, 255, 255, 0.7);
  font-size: 14px;
}

.date-picker-wrapper :deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
}

.date-picker-wrapper :deep(.el-input__inner) {
  color: #fff;
}

.date-cell {
  position: relative;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.data-dot {
  position: absolute;
  bottom: 2px;
  left: 50%;
  transform: translateX(-50%);
  width: 4px;
  height: 4px;
  background: #00f0ff;
  border-radius: 50%;
}

.chart-container {
  width: 100%;
  height: 350px;
}

.data-stats {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
  padding: 15px;
  background: rgba(0, 240, 255, 0.05);
  border-radius: 8px;
  border: 1px solid rgba(0, 240, 255, 0.2);
}

.stat-item {
  text-align: center;
}

.stat-label {
  display: block;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  margin-bottom: 5px;
}

.stat-value {
  font-size: 18px;
  font-weight: 600;
  color: #00f0ff;
}
</style>
