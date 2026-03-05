<template>
  <TechPage title="分析计算">
    <TechTabs :tabs="tabs" v-model="activeTab">
      <template #extra>
        <TechButton type="primary" icon="Refresh" @click="loadData">刷新</TechButton>
      </template>
      
      <div class="analysis-content">
        <div class="stats-row" v-if="activeTab === 'lineLoss'">
          <div class="stat-card">
            <div class="stat-icon" style="background: linear-gradient(135deg, #00f0ff, #0080ff);">
              <el-icon :size="24"><TrendCharts /></el-icon>
            </div>
            <div class="stat-info">
              <span class="stat-value">{{ avgLossRate.toFixed(2) }}%</span>
              <span class="stat-label">平均线损率</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: linear-gradient(135deg, #ff4081, #f50057);">
              <el-icon :size="24"><Warning /></el-icon>
            </div>
            <div class="stat-info">
              <span class="stat-value">{{ highLossCount }}</span>
              <span class="stat-label">高损线路</span>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon" style="background: linear-gradient(135deg, #00ff80, #00c853);">
              <el-icon :size="24"><CircleCheck /></el-icon>
            </div>
            <div class="stat-info">
              <span class="stat-value">{{ normalCount }}</span>
              <span class="stat-label">正常线路</span>
            </div>
          </div>
        </div>

        <TechTable
          :columns="currentColumns"
          :data="tableData"
          :loading="loading"
          :show-pagination="true"
          :total="total"
          :current-page="currentPage"
          :page-size="pageSize"
          @page-change="handlePageChange"
        >
          <template #lossRate="{ row }">
            <div class="loss-rate-cell">
              <span class="loss-value" :class="getLossClass(row.lossRate)">{{ row.lossRate?.toFixed(2) }}%</span>
              <div class="loss-bar">
                <div class="loss-fill" :style="{ width: Math.min(row.lossRate, 100) + '%', background: getLossColor(row.lossRate) }"></div>
              </div>
            </div>
          </template>
          
          <template #healthScore="{ row }">
            <div class="health-cell">
              <span class="health-value" :class="getHealthClass(row.healthScore)">{{ row.healthScore?.toFixed(1) }}</span>
              <div class="health-ring" :style="{ '--progress': row.healthScore + '%' }">
                <div class="ring-fill"></div>
              </div>
            </div>
          </template>
          
          <template #riskLevel="{ row }">
            <span class="risk-tag" :class="getRiskClass(row.riskLevel)">{{ row.riskLevel }}</span>
          </template>
          
          <template #healthLevel="{ row }">
            <span class="health-tag" :class="getHealthLevelClass(row.healthLevel)">{{ row.healthLevel }}</span>
          </template>
          
          <template #saidi="{ row }">
            <span class="metric-value">{{ row.saidi?.toFixed(4) }}</span>
          </template>
          
          <template #saifi="{ row }">
            <span class="metric-value">{{ row.saifi?.toFixed(4) }}</span>
          </template>
          
          <template #asai="{ row }">
            <span class="metric-value success">{{ row.asai?.toFixed(4) }}%</span>
          </template>
          
          <template #status="{ row }">
            <span class="status-tag" :class="getStatusClass(row.status)">{{ row.status }}</span>
          </template>
          
          <template #recordTime="{ row }">
            {{ formatTime(row.recordTime) }}
          </template>
          
          <template #forecastTime="{ row }">
            {{ formatTime(row.forecastTime) }}
          </template>
          
          <template #calculationTime="{ row }">
            {{ formatTime(row.calculationTime) }}
          </template>
          
          <template #checkTime="{ row }">
            {{ formatTime(row.checkTime) }}
          </template>
          
          <template #evaluateTime="{ row }">
            {{ formatTime(row.evaluateTime) }}
          </template>
          
          <template #statisticTime="{ row }">
            {{ formatTime(row.statisticTime) }}
          </template>
          
          <template #actions="{ row }">
            <div class="action-buttons">
              <button class="action-btn view" @click="handleView(row)">
                <el-icon><View /></el-icon>
              </button>
            </div>
          </template>
        </TechTable>
      </div>
    </TechTabs>
  </TechPage>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { View, TrendCharts, Warning, CircleCheck, Refresh } from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechTabs from '@/components/common/TechTabs.vue'
import TechTable from '@/components/common/TechTable.vue'
import TechButton from '@/components/common/TechButton.vue'
import { lineLossApi, loadForecastApi, powerFlowApi, n1CheckApi, deviceHealthApi, reliabilityIndexApi } from '@/api/operation'
import { ElMessage } from 'element-plus'

const activeTab = ref('lineLoss')
const loading = ref(false)
const tableData = ref<any[]>([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const tabs = [
  { name: 'lineLoss', label: '线损分析' },
  { name: 'loadForecast', label: '负荷预测' },
  { name: 'powerFlow', label: '潮流计算' },
  { name: 'n1Check', label: 'N-1校验' },
  { name: 'deviceHealth', label: '设备健康' },
  { name: 'reliability', label: '可靠性分析' }
]

const columnsMap: Record<string, any[]> = {
  lineLoss: [
    { prop: 'lineName', label: '线路名称', width: '150px' },
    { prop: 'supplyPower', label: '供电量(kWh)', width: '120px' },
    { prop: 'sellPower', label: '售电量(kWh)', width: '120px' },
    { prop: 'lossPower', label: '损耗(kWh)', width: '100px' },
    { prop: 'lossRate', label: '线损率', width: '150px', type: 'slot' },
    { prop: 'analysisResult', label: '分析结果' },
    { prop: 'recordTime', label: '记录时间', width: '140px', type: 'slot' }
  ],
  loadForecast: [
    { prop: 'targetName', label: '目标名称', width: '150px' },
    { prop: 'forecastType', label: '预测类型', width: '100px' },
    { prop: 'forecastValue', label: '预测值(MW)', width: '120px' },
    { prop: 'actualValue', label: '实际值(MW)', width: '120px' },
    { prop: 'errorRate', label: '误差率(%)', width: '100px' },
    { prop: 'algorithm', label: '算法', width: '120px' },
    { prop: 'forecastTime', label: '预测时间', width: '140px', type: 'slot' }
  ],
  powerFlow: [
    { prop: 'calculationName', label: '计算名称', width: '150px' },
    { prop: 'nodeCount', label: '节点数', width: '80px' },
    { prop: 'branchCount', label: '支路数', width: '80px' },
    { prop: 'totalGeneration', label: '总发电(MW)', width: '110px' },
    { prop: 'totalLoad', label: '总负荷(MW)', width: '110px' },
    { prop: 'totalLoss', label: '总损耗(MW)', width: '110px' },
    { prop: 'status', label: '状态', width: '120px', type: 'slot' },
    { prop: 'analysisResult', label: '分析结果' }
  ],
  n1Check: [
    { prop: 'checkName', label: '校验名称', width: '150px' },
    { prop: 'totalItems', label: '总项数', width: '80px' },
    { prop: 'passedItems', label: '通过项', width: '80px' },
    { prop: 'failedItems', label: '失败项', width: '80px' },
    { prop: 'riskLevel', label: '风险等级', width: '100px', type: 'slot' },
    { prop: 'status', label: '状态', width: '120px', type: 'slot' },
    { prop: 'riskAssessment', label: '风险评估' }
  ],
  deviceHealth: [
    { prop: 'deviceName', label: '设备名称', width: '150px' },
    { prop: 'deviceType', label: '设备类型', width: '100px' },
    { prop: 'healthScore', label: '健康得分', width: '120px', type: 'slot' },
    { prop: 'healthLevel', label: '健康等级', width: '100px', type: 'slot' },
    { prop: 'riskLevel', label: '风险等级', width: '100px', type: 'slot' },
    { prop: 'runYears', label: '运行年限', width: '90px' },
    { prop: 'suggestion', label: '建议' }
  ],
  reliability: [
    { prop: 'targetName', label: '目标名称', width: '150px' },
    { prop: 'timeGranularity', label: '时间粒度', width: '100px' },
    { prop: 'saidi', label: 'SAIDI', width: '100px', type: 'slot' },
    { prop: 'saifi', label: 'SAIFI', width: '100px', type: 'slot' },
    { prop: 'asai', label: 'ASAI(%)', width: '100px', type: 'slot' },
    { prop: 'totalOutageCount', label: '停电次数', width: '90px' },
    { prop: 'totalOutageDuration', label: '停电时长(分)', width: '110px' }
  ]
}

const currentColumns = computed(() => columnsMap[activeTab.value] || [])

const avgLossRate = computed(() => {
  if (activeTab.value !== 'lineLoss' || tableData.value.length === 0) return 0
  return tableData.value.reduce((sum, item) => sum + (item.lossRate || 0), 0) / tableData.value.length
})

const highLossCount = computed(() => {
  if (activeTab.value !== 'lineLoss') return 0
  return tableData.value.filter(item => item.lossRate > 10).length
})

const normalCount = computed(() => {
  if (activeTab.value !== 'lineLoss') return 0
  return tableData.value.filter(item => item.lossRate <= 5).length
})

const getLossClass = (rate: number) => rate > 10 ? 'danger' : rate > 5 ? 'warning' : 'success'
const getLossColor = (rate: number) => rate > 10 ? '#ff4081' : rate > 5 ? '#ffc800' : '#00ff80'
const getHealthClass = (score: number) => score >= 90 ? 'success' : score >= 60 ? 'warning' : 'danger'
const getRiskClass = (level: string) => ({ '高风险': 'danger', '中风险': 'warning', '低风险': 'success' }[level] || 'info')
const getHealthLevelClass = (level: string) => ({ '优秀': 'success', '良好': 'primary', '一般': 'warning', '较差': 'danger' }[level] || 'info')
const getStatusClass = (status: string) => status === '计算完成' || status === '已完成' ? 'success' : 'warning'

const formatTime = (time: string) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN', { month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' })
}

const handleView = (row: any) => ElMessage.info('查看详情')
const handlePageChange = (page: number) => { currentPage.value = page; loadData() }

const loadData = async () => {
  loading.value = true
  try {
    const apiMap: Record<string, any> = {
      lineLoss: lineLossApi,
      loadForecast: loadForecastApi,
      powerFlow: powerFlowApi,
      n1Check: n1CheckApi,
      deviceHealth: deviceHealthApi,
      reliability: reliabilityIndexApi
    }
    const api = apiMap[activeTab.value]
    if (api) {
      const res = await api.getAll()
      const allData = res.data?.value || res.data || []
      total.value = allData.length
      const start = (currentPage.value - 1) * pageSize.value
      const end = start + pageSize.value
      tableData.value = allData.slice(start, end)
    }
  } catch (e) {
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

watch(activeTab, () => loadData())
onMounted(() => loadData())
</script>

<style scoped>
.analysis-content {
  height: 100%;
  overflow-y: auto;
}

.analysis-content::-webkit-scrollbar {
  width: 6px;
}

.analysis-content::-webkit-scrollbar-track {
  background: rgba(0, 240, 255, 0.05);
  border-radius: 3px;
}

.analysis-content::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}

.analysis-content::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 240, 255, 0.5);
}

.stats-row {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: rgba(10, 10, 26, 0.6);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  backdrop-filter: blur(10px);
}

.stat-icon {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-value {
  font-size: 28px;
  font-weight: 600;
  color: #fff;
  font-family: 'Consolas', monospace;
}

.stat-label {
  font-size: 13px;
  color: #6a7a8a;
}

.loss-rate-cell {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.loss-value {
  font-weight: 600;
  font-family: 'Consolas', monospace;
}

.loss-value.success { color: #00ff80; }
.loss-value.warning { color: #ffc800; }
.loss-value.danger { color: #ff4081; }

.loss-bar {
  width: 100%;
  height: 4px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 2px;
  overflow: hidden;
}

.loss-fill {
  height: 100%;
  border-radius: 2px;
  transition: width 0.3s ease;
}

.health-cell {
  display: flex;
  align-items: center;
  gap: 10px;
}

.health-value {
  font-weight: 600;
  font-family: 'Consolas', monospace;
}

.health-value.success { color: #00ff80; }
.health-value.warning { color: #ffc800; }
.health-value.danger { color: #ff4081; }

.risk-tag, .health-tag, .status-tag {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.risk-tag.success, .health-tag.success, .status-tag.success {
  background: rgba(0, 255, 128, 0.15);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.3);
}

.risk-tag.warning, .health-tag.warning, .status-tag.warning {
  background: rgba(255, 200, 0, 0.15);
  color: #ffc800;
  border: 1px solid rgba(255, 200, 0, 0.3);
}

.risk-tag.danger, .health-tag.danger, .status-tag.danger {
  background: rgba(255, 64, 129, 0.15);
  color: #ff4081;
  border: 1px solid rgba(255, 64, 129, 0.3);
}

.health-tag.primary {
  background: rgba(100, 255, 218, 0.15);
  color: #64ffda;
  border: 1px solid rgba(100, 255, 218, 0.3);
}

.metric-value {
  font-family: 'Consolas', monospace;
  color: #00f0ff;
}

.metric-value.success {
  color: #00ff80;
}

.action-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
}

.action-btn {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  border: 1px solid transparent;
  cursor: pointer;
  transition: all 0.2s ease;
  background: transparent;
  color: #6a7a8a;
}

.action-btn.view:hover {
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.1);
  border-color: rgba(0, 240, 255, 0.3);
}
</style>
