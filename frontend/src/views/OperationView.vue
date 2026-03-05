<template>
  <TechPage title="运维管理">
    <TechTabs :tabs="tabs" v-model="activeTab">
      <template #extra>
        <PageHelp
          page-key="operation"
          page-title="运维管理"
          :help-content="operationHelpContent"
          :tips="operationHelpTips"
        />
        <TechButton type="primary" icon="Plus" @click="handleAdd">新增</TechButton>
        <TechButton icon="Refresh" @click="loadData">刷新</TechButton>
      </template>
      
      <div class="tab-content">
        <TechTable
          :columns="currentColumns"
          :data="tableData"
          :loading="loading"
          :title="currentTitle"
          :show-pagination="true"
          :total="total"
          :current-page="currentPage"
          :page-size="pageSize"
          @page-change="handlePageChange"
        >
          <template #toolbar>
            <TechButton type="primary" size="small" icon="Plus" @click="handleAdd">新增</TechButton>
            <TechButton size="small" icon="Refresh" @click="loadData">刷新</TechButton>
          </template>
          
          <template #status="{ row }">
            <span class="status-tag" :class="getStatusClass(row.status)">{{ row.status }}</span>
          </template>
          
          <template #priority="{ row }">
            <span class="priority-tag" :class="getPriorityClass(row.priority)">{{ row.priority }}</span>
          </template>
          
          <template #defectLevel="{ row }">
            <span class="level-tag" :class="getLevelClass(row.defectLevel)">{{ row.defectLevel }}</span>
          </template>
          
          <template #planStartTime="{ row }">
            {{ formatTime(row.planStartTime) }}
          </template>
          
          <template #discoveryTime="{ row }">
            {{ formatTime(row.discoveryTime) }}
          </template>
          
          <template #actions="{ row }">
            <div class="action-buttons">
              <button class="action-btn view" @click="handleView(row)">
                <el-icon><View /></el-icon>
              </button>
              <button class="action-btn edit" @click="handleEdit(row)" v-if="canEdit(row)">
                <el-icon><Edit /></el-icon>
              </button>
              <button class="action-btn process" @click="handleProcess(row)" v-if="canProcess(row)">
                <el-icon><Check /></el-icon>
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
import { View, Edit, Check, Plus, Refresh } from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechTabs from '@/components/common/TechTabs.vue'
import TechTable from '@/components/common/TechTable.vue'
import TechButton from '@/components/common/TechButton.vue'
import PageHelp from '@/components/common/PageHelp.vue'
import { maintenancePlanApi, workTicketApi, operationTicketApi, inspectionTaskApi, defectApi, dispatchOrderApi, powerOutageApi, operationLogApi } from '@/api/operation'
import { ElMessage } from 'element-plus'

const operationHelpContent = [
  {
    icon: '📋',
    title: '功能模块',
    items: [
      '检修计划：管理设备检修计划',
      '工作票：工作票签发与执行',
      '操作票：倒闸操作管理',
      '巡检任务：设备巡检派发执行',
      '缺陷管理：缺陷发现处理闭环',
      '调度指令：指令下发与执行',
      '停电管理：计划与故障停电'
    ]
  },
  {
    icon: '⚙️',
    title: '操作说明',
    items: [
      '点击新增按钮创建新记录',
      '点击表格行查看详情',
      '支持按状态筛选数据'
    ]
  }
]

const operationHelpTips = [
  '工作票和操作票需按流程执行',
  '缺陷处理完成后需验证闭环'
]

const activeTab = ref('maintenance')
const loading = ref(false)
const tableData = ref<any[]>([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const tabs = [
  { name: 'maintenance', label: '检修计划' },
  { name: 'workTicket', label: '工作票' },
  { name: 'operationTicket', label: '操作票' },
  { name: 'inspection', label: '巡检任务' },
  { name: 'defect', label: '缺陷管理' },
  { name: 'dispatch', label: '调度指令' },
  { name: 'outage', label: '停电管理' },
  { name: 'log', label: '操作日志' }
]

const columnsMap: Record<string, any[]> = {
  maintenance: [
    { prop: 'planCode', label: '计划编号', width: '120px' },
    { prop: 'title', label: '标题' },
    { prop: 'deviceName', label: '设备名称', width: '140px' },
    { prop: 'maintenanceType', label: '检修类型', width: '100px' },
    { prop: 'priority', label: '优先级', width: '80px', type: 'slot' },
    { prop: 'status', label: '状态', width: '100px', type: 'slot' },
    { prop: 'planStartTime', label: '计划开始时间', width: '160px', type: 'slot' },
    { prop: 'applicant', label: '申请人', width: '80px' }
  ],
  workTicket: [
    { prop: 'ticketCode', label: '票号', width: '120px' },
    { prop: 'ticketType', label: '票类型', width: '130px' },
    { prop: 'title', label: '标题' },
    { prop: 'deviceName', label: '设备', width: '120px' },
    { prop: 'workLeader', label: '负责人', width: '100px' },
    { prop: 'status', label: '状态', width: '100px', type: 'slot' }
  ],
  operationTicket: [
    { prop: 'ticketCode', label: '票号', width: '120px' },
    { prop: 'ticketType', label: '类型', width: '100px' },
    { prop: 'title', label: '标题' },
    { prop: 'totalSteps', label: '总步骤', width: '80px' },
    { prop: 'completedSteps', label: '已完成', width: '80px' },
    { prop: 'status', label: '状态', width: '100px', type: 'slot' }
  ],
  inspection: [
    { prop: 'taskCode', label: '任务编号', width: '120px' },
    { prop: 'title', label: '标题' },
    { prop: 'inspectionType', label: '类型', width: '100px' },
    { prop: 'deviceName', label: '设备', width: '120px' },
    { prop: 'inspector', label: '巡检员', width: '80px' },
    { prop: 'status', label: '状态', width: '100px', type: 'slot' }
  ],
  defect: [
    { prop: 'defectCode', label: '缺陷编号', width: '120px' },
    { prop: 'title', label: '标题' },
    { prop: 'deviceName', label: '设备', width: '120px' },
    { prop: 'defectLevel', label: '级别', width: '80px', type: 'slot' },
    { prop: 'status', label: '状态', width: '100px', type: 'slot' },
    { prop: 'discoveryTime', label: '发现时间', width: '160px', type: 'slot' }
  ],
  dispatch: [
    { prop: 'orderCode', label: '指令编号', width: '120px' },
    { prop: 'title', label: '标题' },
    { prop: 'orderType', label: '类型', width: '100px' },
    { prop: 'issuer', label: '下发人', width: '80px' },
    { prop: 'status', label: '状态', width: '100px', type: 'slot' }
  ],
  outage: [
    { prop: 'outageCode', label: '停电编号', width: '120px' },
    { prop: 'title', label: '标题' },
    { prop: 'outageType', label: '类型', width: '100px' },
    { prop: 'affectedCustomers', label: '影响用户', width: '100px' },
    { prop: 'status', label: '状态', width: '100px', type: 'slot' }
  ],
  log: [
    { prop: 'username', label: '用户', width: '100px' },
    { prop: 'module', label: '模块', width: '100px' },
    { prop: 'operation', label: '操作', width: '100px' },
    { prop: 'targetName', label: '目标', width: '150px' },
    { prop: 'ip', label: 'IP', width: '130px' },
    { prop: 'status', label: '状态', width: '80px', type: 'slot' }
  ]
}

const currentColumns = computed(() => columnsMap[activeTab.value] || [])
const currentTitle = computed(() => tabs.find(t => t.name === activeTab.value)?.label || '')

const getStatusClass = (status: string) => {
  const map: Record<string, string> = {
    '待审批': 'warning', '已批准': 'primary', '执行中': 'info', '已完成': 'success',
    '待执行': 'warning', '已签发': 'primary', '已接收': 'info', '许可中': 'info', '已终结': 'success',
    '待处理': 'danger', '处理中': 'warning', '待验证': 'primary', '已闭环': 'success',
    '待接收': 'warning', '已拒绝': 'danger'
  }
  return map[status] || 'info'
}

const getPriorityClass = (priority: string) => {
  const map: Record<string, string> = { '高': 'danger', '中': 'warning', '低': 'info' }
  return map[priority] || 'info'
}

const getLevelClass = (level: string) => {
  const map: Record<string, string> = { '紧急': 'danger', '重大': 'warning', '一般': 'primary', '轻微': 'info' }
  return map[level] || 'info'
}

const formatTime = (time: string) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN', { month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' })
}

const canEdit = (row: any) => ['待审批', '待处理'].includes(row.status)
const canProcess = (row: any) => ['已批准', '待处理', '待执行'].includes(row.status)

const handleAdd = () => ElMessage.info('新增功能')
const handleView = (row: any) => ElMessage.info('查看: ' + row.title || row.planCode)
const handleEdit = (row: any) => ElMessage.info('编辑: ' + row.title)
const handleProcess = (row: any) => ElMessage.info('处理: ' + row.title)
const handlePageChange = (page: number) => { currentPage.value = page; loadData() }

const loadData = async () => {
  loading.value = true
  try {
    const apiMap: Record<string, any> = {
      maintenance: maintenancePlanApi,
      workTicket: workTicketApi,
      operationTicket: operationTicketApi,
      inspection: inspectionTaskApi,
      defect: defectApi,
      dispatch: dispatchOrderApi,
      outage: powerOutageApi,
      log: operationLogApi
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
.tab-content {
  height: 100%;
  overflow-y: auto;
}

.tab-content::-webkit-scrollbar {
  width: 6px;
}

.tab-content::-webkit-scrollbar-track {
  background: rgba(0, 240, 255, 0.05);
  border-radius: 3px;
}

.tab-content::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}

.tab-content::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 240, 255, 0.5);
}

.status-tag, .priority-tag, .level-tag {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.success, .priority-tag.success, .level-tag.success {
  background: rgba(0, 255, 128, 0.15);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.3);
}

.status-tag.warning, .priority-tag.warning, .level-tag.warning {
  background: rgba(255, 200, 0, 0.15);
  color: #ffc800;
  border: 1px solid rgba(255, 200, 0, 0.3);
}

.status-tag.danger, .priority-tag.danger, .level-tag.danger {
  background: rgba(255, 64, 129, 0.15);
  color: #ff4081;
  border: 1px solid rgba(255, 64, 129, 0.3);
}

.status-tag.info, .priority-tag.info, .level-tag.info {
  background: rgba(0, 240, 255, 0.15);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.3);
}

.status-tag.primary, .priority-tag.primary, .level-tag.primary {
  background: rgba(100, 255, 218, 0.15);
  color: #64ffda;
  border: 1px solid rgba(100, 255, 218, 0.3);
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

.action-btn:hover {
  transform: scale(1.1);
}

.action-btn.view:hover {
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.1);
  border-color: rgba(0, 240, 255, 0.3);
}

.action-btn.edit:hover {
  color: #ffc800;
  background: rgba(255, 200, 0, 0.1);
  border-color: rgba(255, 200, 0, 0.3);
}

.action-btn.process:hover {
  color: #00ff80;
  background: rgba(0, 255, 128, 0.1);
  border-color: rgba(0, 255, 128, 0.3);
}
</style>
