<template>
  <TechPage title="资源管理">
    <TechTabs :tabs="tabs" v-model="activeTab">
      <template #extra>
        <TechButton type="primary" icon="Refresh" @click="loadData">刷新</TechButton>
      </template>
      
      <div class="resource-content">
        <!-- 文档管理 -->
        <template v-if="activeTab === 'document'">
          <div class="search-bar">
            <el-input v-model="searchKeyword" placeholder="搜索文档..." prefix-icon="Search" clearable @keyup.enter="handleSearch" class="search-input" />
            <TechButton type="primary" @click="handleSearch">搜索</TechButton>
          </div>
          <div class="document-grid">
            <div class="doc-card" v-for="doc in tableData" :key="doc.id" @click="handleView(doc)">
              <div class="doc-icon" :class="getDocTypeClass(doc.docType)">
                <el-icon :size="32"><Document /></el-icon>
              </div>
              <div class="doc-info">
                <span class="doc-title">{{ doc.title }}</span>
                <span class="doc-meta">{{ doc.docType }} · {{ doc.author }} · {{ formatFileSize(doc.fileSize) }}</span>
                <span class="doc-date">{{ formatTime(doc.uploadTime) }}</span>
              </div>
              <div class="doc-stats">
                <span class="download-count">{{ doc.downloadCount }} 次下载</span>
              </div>
            </div>
          </div>
        </template>
        
        <!-- 视频监控 -->
        <template v-if="activeTab === 'video'">
          <div class="video-stats">
            <div class="video-stat-card online">
              <el-icon :size="24"><VideoCamera /></el-icon>
              <span class="stat-num">{{ onlineCount }}</span>
              <span class="stat-label">在线</span>
            </div>
            <div class="video-stat-card offline">
              <el-icon :size="24"><VideoPause /></el-icon>
              <span class="stat-num">{{ offlineCount }}</span>
              <span class="stat-label">离线</span>
            </div>
          </div>
          <div class="video-grid">
            <div class="video-card" v-for="monitor in tableData" :key="monitor.id" :class="{ offline: monitor.status !== '在线' }">
              <div class="video-preview">
                <div class="preview-placeholder">
                  <el-icon :size="48"><VideoCamera /></el-icon>
                </div>
                <div class="video-status" :class="monitor.status === '在线' ? 'online' : 'offline'">
                  {{ monitor.status }}
                </div>
              </div>
              <div class="video-info">
                <span class="video-name">{{ monitor.name }}</span>
                <span class="video-location">{{ monitor.location }}</span>
                <span class="video-device">{{ monitor.deviceName }}</span>
              </div>
              <div class="video-actions">
                <TechButton size="small" @click="handleView(monitor)">查看</TechButton>
                <TechButton size="small" type="info" @click="handlePlayback(monitor)">回放</TechButton>
              </div>
            </div>
          </div>
        </template>
        
        <!-- 数据初始化 -->
        <template v-if="activeTab === 'dataInit'">
          <div class="init-container">
            <div class="init-header">
              <div class="init-icon">
                <el-icon :size="48"><Coin /></el-icon>
              </div>
              <div class="init-title">
                <h3>数据初始化</h3>
                <p>点击下方按钮生成模拟测试数据，用于演示和测试系统功能</p>
              </div>
            </div>
            
            <div class="init-main">
              <TechButton type="primary" size="large" :loading="loading" @click="generateAll" class="generate-all-btn">
                <el-icon><MagicStick /></el-icon>
                一键生成所有数据
              </TechButton>
            </div>
            
            <div class="init-grid">
              <div class="init-item" v-for="item in initItems" :key="item.key">
                <div class="init-item-icon" :style="{ background: item.gradient }">
                  <el-icon :size="20"><component :is="item.icon" /></el-icon>
                </div>
                <span class="init-item-label">{{ item.label }}</span>
                <TechButton size="small" :loading="loading" @click="generateSingle(item.key)">生成</TechButton>
              </div>
            </div>
            
            <div class="init-result" v-if="result">
              <div class="result-header">
                <span>生成结果</span>
                <el-button text size="small" @click="result = null">清除</el-button>
              </div>
              <pre>{{ JSON.stringify(result, null, 2) }}</pre>
            </div>
          </div>
        </template>
      </div>
    </TechTabs>
  </TechPage>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import { 
  Document, VideoCamera, VideoPause, Coin, MagicStick, Refresh,
  Tools, Tickets, View, Warning, List, Promotion, SwitchButton,
  TrendCharts, DataLine, Connection, CircleCheckFilled, FirstAidKit, Stopwatch, Folder
} from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechTabs from '@/components/common/TechTabs.vue'
import TechButton from '@/components/common/TechButton.vue'
import { documentApi, videoMonitorApi, dataInitApi } from '@/api/operation'
import { ElMessage } from 'element-plus'

const activeTab = ref('document')
const loading = ref(false)
const tableData = ref<any[]>([])
const searchKeyword = ref('')
const result = ref<any>(null)

const tabs = [
  { name: 'document', label: '文档管理' },
  { name: 'video', label: '视频监控' },
  { name: 'dataInit', label: '数据初始化' }
]

const initItems = [
  { key: 'maintenancePlans', label: '检修计划', icon: Tools, gradient: 'linear-gradient(135deg, #00f0ff, #0080ff)' },
  { key: 'workTickets', label: '工作票', icon: Tickets, gradient: 'linear-gradient(135deg, #e040fb, #7c4dff)' },
  { key: 'operationTickets', label: '操作票', icon: Document, gradient: 'linear-gradient(135deg, #ff4081, #f50057)' },
  { key: 'inspectionTasks', label: '巡检任务', icon: View, gradient: 'linear-gradient(135deg, #00ff80, #00c853)' },
  { key: 'defects', label: '缺陷数据', icon: Warning, gradient: 'linear-gradient(135deg, #ff9800, #ff5722)' },
  { key: 'operationLogs', label: '操作日志', icon: List, gradient: 'linear-gradient(135deg, #64ffda, #00bfa5)' },
  { key: 'dispatchOrders', label: '调度指令', icon: Promotion, gradient: 'linear-gradient(135deg, #ffc107, #ff9800)' },
  { key: 'powerOutages', label: '停电数据', icon: SwitchButton, gradient: 'linear-gradient(135deg, #ff5252, #d32f2f)' },
  { key: 'lineLosses', label: '线损数据', icon: TrendCharts, gradient: 'linear-gradient(135deg, #448aff, #2962ff)' },
  { key: 'loadForecasts', label: '负荷预测', icon: DataLine, gradient: 'linear-gradient(135deg, #69f0ae, #00e676)' },
  { key: 'powerFlows', label: '潮流计算', icon: Connection, gradient: 'linear-gradient(135deg, #ea80fc, #e040fb)' },
  { key: 'n1Checks', label: 'N-1校验', icon: CircleCheckFilled, gradient: 'linear-gradient(135deg, #ff80ab, #ff4081)' },
  { key: 'deviceHealths', label: '设备健康', icon: FirstAidKit, gradient: 'linear-gradient(135deg, #b388ff, #7c4dff)' },
  { key: 'reliabilityIndices', label: '可靠性指标', icon: Stopwatch, gradient: 'linear-gradient(135deg, #80d8ff, #40c4ff)' },
  { key: 'documents', label: '文档数据', icon: Folder, gradient: 'linear-gradient(135deg, #ffd180, #ffab40)' },
  { key: 'videoMonitors', label: '视频监控', icon: VideoCamera, gradient: 'linear-gradient(135deg, #82b1ff, #448aff)' }
]

const onlineCount = computed(() => tableData.value.filter(m => m.status === '在线').length)
const offlineCount = computed(() => tableData.value.filter(m => m.status !== '在线').length)

const getDocTypeClass = (type: string) => {
  const map: Record<string, string> = {
    '技术文档': 'tech',
    '操作规程': 'guide',
    '检修记录': 'record',
    '图纸资料': 'drawing'
  }
  return map[type] || 'default'
}

const formatFileSize = (size: number) => {
  if (!size) return '-'
  if (size < 1024) return size + ' B'
  if (size < 1024 * 1024) return (size / 1024).toFixed(1) + ' KB'
  return (size / 1024 / 1024).toFixed(1) + ' MB'
}

const formatTime = (time: string) => {
  if (!time) return '-'
  return new Date(time).toLocaleDateString('zh-CN')
}

const handleView = (row: any) => ElMessage.info('查看: ' + (row.title || row.name))
const handlePlayback = (row: any) => ElMessage.info('回放: ' + row.name)
const handleSearch = async () => {
  if (!searchKeyword.value) { loadData(); return }
  try { tableData.value = (await documentApi.search(searchKeyword.value)).data } catch (e) { ElMessage.error('搜索失败') }
}

const generateAll = async () => {
  loading.value = true
  try { result.value = (await dataInitApi.generateAll()).data; ElMessage.success('数据生成完成') }
  catch (e) { ElMessage.error('生成失败') } finally { loading.value = false }
}

const generateSingle = async (key: string) => {
  loading.value = true
  const methodMap: Record<string, any> = {
    maintenancePlans: dataInitApi.generateMaintenancePlans,
    workTickets: dataInitApi.generateWorkTickets,
    operationTickets: dataInitApi.generateOperationTickets,
    inspectionTasks: dataInitApi.generateInspectionTasks,
    defects: dataInitApi.generateDefects,
    operationLogs: dataInitApi.generateOperationLogs,
    dispatchOrders: dataInitApi.generateDispatchOrders,
    powerOutages: dataInitApi.generatePowerOutages,
    lineLosses: dataInitApi.generateLineLosses,
    loadForecasts: dataInitApi.generateLoadForecasts,
    powerFlows: dataInitApi.generatePowerFlows,
    n1Checks: dataInitApi.generateN1Checks,
    deviceHealths: dataInitApi.generateDeviceHealths,
    reliabilityIndices: dataInitApi.generateReliabilityIndices,
    documents: dataInitApi.generateDocuments,
    videoMonitors: dataInitApi.generateVideoMonitors
  }
  try { result.value = (await methodMap[key]()).data; ElMessage.success('生成完成') }
  catch (e) { ElMessage.error('生成失败') } finally { loading.value = false }
}

const loadData = async () => {
  console.log('[ResourceView] loadData called, activeTab:', activeTab.value)
  loading.value = true
  try {
    const apiMap: Record<string, any> = { document: documentApi, video: videoMonitorApi, dataInit: null }
    const api = apiMap[activeTab.value]
    console.log('[ResourceView] api:', api ? 'found' : 'not found')
    if (api) { 
      console.log('[ResourceView] calling api.getAll()...')
      const res = await api.getAll()
      console.log('[ResourceView] response:', res)
      console.log('[ResourceView] res.data:', res.data)
      tableData.value = res.data?.value || res.data || []
      console.log('[ResourceView] tableData:', tableData.value.length, 'items')
    }
  } catch (e) { 
    console.error('[ResourceView] load error:', e)
    ElMessage.error('加载失败') 
  } finally { 
    loading.value = false 
  }
}

watch(activeTab, () => {
  console.log('[ResourceView] activeTab changed to:', activeTab.value)
  loadData()
})
onMounted(() => {
  console.log('[ResourceView] onMounted')
  loadData()
})
</script>

<style scoped>
.resource-content { 
  height: 100%; 
  overflow-y: auto;
}

.resource-content::-webkit-scrollbar {
  width: 6px;
}

.resource-content::-webkit-scrollbar-track {
  background: rgba(0, 240, 255, 0.05);
  border-radius: 3px;
}

.resource-content::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}

.resource-content::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 240, 255, 0.5);
}

/* 文档管理 */
.search-bar { display: flex; gap: 12px; margin-bottom: 20px; }
.search-input { flex: 1; max-width: 400px; }
.document-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 16px; }
.doc-card {
  display: flex; gap: 16px; padding: 16px;
  background: rgba(10, 10, 26, 0.6);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px; cursor: pointer;
  transition: all 0.25s ease;
}
.doc-card:hover { border-color: rgba(0, 240, 255, 0.5); transform: translateY(-2px); }
.doc-icon { width: 56px; height: 56px; border-radius: 10px; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }
.doc-icon.tech { background: linear-gradient(135deg, rgba(0, 240, 255, 0.2), rgba(0, 128, 255, 0.2)); color: #00f0ff; }
.doc-icon.guide { background: linear-gradient(135deg, rgba(0, 255, 128, 0.2), rgba(0, 200, 83, 0.2)); color: #00ff80; }
.doc-icon.record { background: linear-gradient(135deg, rgba(255, 152, 0, 0.2), rgba(255, 87, 34, 0.2)); color: #ff9800; }
.doc-icon.drawing { background: linear-gradient(135deg, rgba(224, 64, 251, 0.2), rgba(124, 77, 255, 0.2)); color: #e040fb; }
.doc-info { flex: 1; display: flex; flex-direction: column; gap: 4px; min-width: 0; }
.doc-title { font-size: 14px; font-weight: 500; color: #e8e8e8; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.doc-meta { font-size: 12px; color: #6a7a8a; }
.doc-date { font-size: 11px; color: #4a5568; }
.doc-stats { display: flex; align-items: flex-end; }
.download-count { font-size: 11px; color: #6a7a8a; }

/* 视频监控 */
.video-stats { display: flex; gap: 20px; margin-bottom: 20px; }
.video-stat-card { flex: 1; display: flex; align-items: center; gap: 12px; padding: 16px 20px; background: rgba(10, 10, 26, 0.6); border: 1px solid rgba(0, 240, 255, 0.2); border-radius: 12px; }
.video-stat-card.online { color: #00ff80; }
.video-stat-card.offline { color: #ff4081; }
.stat-num { font-size: 28px; font-weight: 600; font-family: 'Consolas', monospace; }
.video-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px; }
.video-card { background: rgba(10, 10, 26, 0.6); border: 1px solid rgba(0, 240, 255, 0.2); border-radius: 12px; overflow: hidden; transition: all 0.25s ease; }
.video-card:hover { border-color: rgba(0, 240, 255, 0.5); }
.video-card.offline { opacity: 0.7; }
.video-preview { position: relative; aspect-ratio: 16/9; background: rgba(0, 0, 0, 0.4); }
.preview-placeholder { position: absolute; inset: 0; display: flex; align-items: center; justify-content: center; color: #2a3a4a; }
.video-status { position: absolute; top: 10px; right: 10px; padding: 4px 10px; border-radius: 4px; font-size: 11px; font-weight: 500; }
.video-status.online { background: rgba(0, 255, 128, 0.2); color: #00ff80; }
.video-status.offline { background: rgba(255, 64, 129, 0.2); color: #ff4081; }
.video-info { padding: 12px 16px; display: flex; flex-direction: column; gap: 4px; }
.video-name { font-size: 14px; font-weight: 500; color: #e8e8e8; }
.video-location { font-size: 12px; color: #6a7a8a; }
.video-device { font-size: 11px; color: #4a5568; }
.video-actions { padding: 12px 16px; display: flex; gap: 10px; border-top: 1px solid rgba(0, 240, 255, 0.1); }

/* 数据初始化 */
.init-container { max-width: 900px; margin: 0 auto; }
.init-header { display: flex; align-items: center; gap: 20px; margin-bottom: 30px; padding: 24px; background: rgba(10, 10, 26, 0.6); border: 1px solid rgba(0, 240, 255, 0.2); border-radius: 12px; }
.init-icon { width: 80px; height: 80px; border-radius: 16px; background: linear-gradient(135deg, rgba(0, 240, 255, 0.2), rgba(0, 128, 255, 0.2)); display: flex; align-items: center; justify-content: center; color: #00f0ff; }
.init-title h3 { margin: 0 0 8px 0; font-size: 20px; color: #fff; }
.init-title p { margin: 0; font-size: 14px; color: #6a7a8a; }
.init-main { display: flex; justify-content: center; margin-bottom: 30px; }
.generate-all-btn { padding: 16px 40px; font-size: 16px; }
.init-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 12px; margin-bottom: 20px; }
.init-item { display: flex; flex-direction: column; align-items: center; gap: 10px; padding: 16px; background: rgba(10, 10, 26, 0.4); border: 1px solid rgba(0, 240, 255, 0.15); border-radius: 10px; }
.init-item-icon { width: 40px; height: 40px; border-radius: 10px; display: flex; align-items: center; justify-content: center; color: white; }
.init-item-label { font-size: 12px; color: #a0b0c0; }
.init-result { background: rgba(10, 10, 26, 0.6); border: 1px solid rgba(0, 240, 255, 0.2); border-radius: 12px; overflow: hidden; }
.result-header { display: flex; justify-content: space-between; align-items: center; padding: 12px 16px; background: rgba(0, 240, 255, 0.05); border-bottom: 1px solid rgba(0, 240, 255, 0.1); font-size: 13px; color: #00f0ff; }
.init-result pre { padding: 16px; margin: 0; font-size: 12px; color: #a0b0c0; max-height: 300px; overflow: auto; }
</style>
