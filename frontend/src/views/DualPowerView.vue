<template>
  <TechPage title="双电源管理">
    <template #header-actions>
      <PageHelp
        page-key="dual-power"
        page-title="双电源管理"
        :help-content="helpContent"
        :tips="helpTips"
      />
      <TechButton type="primary" icon="Plus" @click="handleAdd">新增配置</TechButton>
      <TechButton icon="Refresh" @click="loadData">刷新</TechButton>
    </template>

    <div class="dual-power-content">
      <div class="stats-cards">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #667eea, #764ba2);">
            <el-icon :size="24"><Connection /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.total }}</div>
            <div class="stat-label">双电源用户</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #11998e, #38ef7d);">
            <el-icon :size="24"><CircleCheck /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.normal }}</div>
            <div class="stat-label">正常运行</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #f093fb, #f5576c);">
            <el-icon :size="24"><Warning /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.warning }}</div>
            <div class="stat-label">异常告警</div>
          </div>
        </div>
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #4facfe, #00f2fe);">
            <el-icon :size="24"><DataLine /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.reliability }}%</div>
            <div class="stat-label">供电可靠率</div>
          </div>
        </div>
      </div>

      <div class="filter-bar">
        <el-input 
          v-model="searchText" 
          placeholder="搜索用户名称" 
          style="width: 200px"
          clearable
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-select v-model="filterStatus" placeholder="运行状态" style="width: 120px" clearable>
          <el-option label="全部" value="" />
          <el-option label="正常" value="正常" />
          <el-option label="异常" value="异常" />
        </el-select>
        <el-select v-model="filterType" placeholder="用户类型" style="width: 120px" clearable>
          <el-option label="全部" value="" />
          <el-option label="一级重要" value="一级重要" />
          <el-option label="二级重要" value="二级重要" />
          <el-option label="临时重要" value="临时重要" />
        </el-select>
      </div>

      <TechTable 
        :data="filteredData" 
        :columns="columns"
        :loading="loading"
        :pagination="pagination"
        @page-change="handlePageChange"
      >
        <template #status="{ row }">
          <el-tag :type="row.status === '正常' ? 'success' : 'danger'" size="small">
            {{ row.status }}
          </el-tag>
        </template>
        <template #mainPower="{ row }">
          <div class="power-info">
            <span class="power-name">{{ row.mainPower.name }}</span>
            <el-tag size="small" type="info">{{ row.mainPower.voltage }}</el-tag>
          </div>
        </template>
        <template #backupPower="{ row }">
          <div class="power-info">
            <span class="power-name">{{ row.backupPower.name }}</span>
            <el-tag size="small" type="info">{{ row.backupPower.voltage }}</el-tag>
          </div>
        </template>
        <template #autoSwitch="{ row }">
          <el-switch v-model="row.autoSwitch" disabled />
        </template>
        <template #actions="{ row }">
          <div class="action-buttons">
            <el-button type="primary" link size="small" @click="handleView(row)">查看</el-button>
            <el-button type="primary" link size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="primary" link size="small" @click="handleTrace(row)">追溯</el-button>
          </div>
        </template>
      </TechTable>
    </div>

    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle" 
      width="600px"
      class="tech-dialog"
    >
      <el-form :model="formData" label-width="100px">
        <el-form-item label="用户名称">
          <el-input v-model="formData.name" />
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select v-model="formData.type" style="width: 100%">
            <el-option label="一级重要" value="一级重要" />
            <el-option label="二级重要" value="二级重要" />
            <el-option label="临时重要" value="临时重要" />
          </el-select>
        </el-form-item>
        <el-form-item label="主电源">
          <el-input v-model="formData.mainPowerName" placeholder="变电站/线路名称" />
        </el-form-item>
        <el-form-item label="主电源电压">
          <el-select v-model="formData.mainPowerVoltage" style="width: 100%">
            <el-option label="110kV" value="110kV" />
            <el-option label="35kV" value="35kV" />
            <el-option label="10kV" value="10kV" />
          </el-select>
        </el-form-item>
        <el-form-item label="备用电源">
          <el-input v-model="formData.backupPowerName" placeholder="变电站/线路名称" />
        </el-form-item>
        <el-form-item label="备用电源电压">
          <el-select v-model="formData.backupPowerVoltage" style="width: 100%">
            <el-option label="110kV" value="110kV" />
            <el-option label="35kV" value="35kV" />
            <el-option label="10kV" value="10kV" />
          </el-select>
        </el-form-item>
        <el-form-item label="自动切换">
          <el-switch v-model="formData.autoSwitch" />
        </el-form-item>
        <el-form-item label="切换时间">
          <el-input-number v-model="formData.switchTime" :min="0" :max="60" />
          <span style="margin-left: 8px">秒</span>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </TechPage>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Connection, CircleCheck, Warning, DataLine, Search } from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechTable from '@/components/common/TechTable.vue'
import TechButton from '@/components/common/TechButton.vue'
import PageHelp from '@/components/common/PageHelp.vue'

const router = useRouter()

const helpContent = [
  {
    icon: '🔌',
    title: '双电源配置',
    items: [
      '管理重要用户的双电源供电配置',
      '支持主电源和备用电源信息录入',
      '可设置自动切换和切换时间'
    ]
  },
  {
    icon: '📊',
    title: '监控功能',
    items: [
      '实时监控双电源运行状态',
      '异常情况自动告警',
      '供电可靠性统计分析'
    ]
  }
]

const helpTips = [
  '一级重要用户必须配置双电源',
  '建议定期检查备用电源可用性'
]

const loading = ref(false)
const searchText = ref('')
const filterStatus = ref('')
const filterType = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('新增双电源配置')

const stats = ref({
  total: 156,
  normal: 142,
  warning: 14,
  reliability: 99.2
})

const pagination = ref({
  current: 1,
  pageSize: 10,
  total: 156
})

const formData = ref({
  id: 0,
  name: '',
  type: '二级重要',
  mainPowerName: '',
  mainPowerVoltage: '10kV',
  backupPowerName: '',
  backupPowerVoltage: '10kV',
  autoSwitch: true,
  switchTime: 3
})

const columns = [
  { prop: 'name', label: '用户名称', width: 140 },
  { prop: 'type', label: '用户类型', width: 90 },
  { prop: 'mainPower', label: '主电源', width: 140, slot: true },
  { prop: 'backupPower', label: '备用电源', width: 140, slot: true },
  { prop: 'autoSwitch', label: '自动切换', width: 80, slot: true },
  { prop: 'switchTime', label: '切换时间', width: 80 },
  { prop: 'status', label: '状态', width: 70, slot: true },
  { prop: 'lastCheck', label: '最后检查', width: 100 },
  { prop: 'actions', label: '操作', width: 180, slot: true }
]

const tableData = ref([
  {
    id: 1,
    name: '市中心医院',
    type: '一级重要',
    mainPower: { name: '城东变', voltage: '10kV' },
    backupPower: { name: '城西变', voltage: '10kV' },
    autoSwitch: true,
    switchTime: 3,
    status: '正常',
    lastCheck: '2024-01-15'
  },
  {
    id: 2,
    name: '市政府大楼',
    type: '一级重要',
    mainPower: { name: '中心变', voltage: '35kV' },
    backupPower: { name: '南郊变', voltage: '35kV' },
    autoSwitch: true,
    switchTime: 2,
    status: '正常',
    lastCheck: '2024-01-15'
  },
  {
    id: 3,
    name: '火车站',
    type: '一级重要',
    mainPower: { name: '北站变', voltage: '10kV' },
    backupPower: { name: '东站变', voltage: '10kV' },
    autoSwitch: false,
    switchTime: 5,
    status: '异常',
    lastCheck: '2024-01-14'
  },
  {
    id: 4,
    name: '电信数据中心',
    type: '二级重要',
    mainPower: { name: '科技园变', voltage: '10kV' },
    backupPower: { name: '高新区变', voltage: '10kV' },
    autoSwitch: true,
    switchTime: 1,
    status: '正常',
    lastCheck: '2024-01-15'
  },
  {
    id: 5,
    name: '大型商场A',
    type: '临时重要',
    mainPower: { name: '商业区变', voltage: '10kV' },
    backupPower: { name: '步行街变', voltage: '10kV' },
    autoSwitch: true,
    switchTime: 5,
    status: '正常',
    lastCheck: '2024-01-13'
  }
])

const filteredData = computed(() => {
  let data = tableData.value
  if (searchText.value) {
    data = data.filter(item => item.name.includes(searchText.value))
  }
  if (filterStatus.value) {
    data = data.filter(item => item.status === filterStatus.value)
  }
  if (filterType.value) {
    data = data.filter(item => item.type === filterType.value)
  }
  return data
})

const loadData = () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
  }, 500)
}

const handlePageChange = (page: number) => {
  pagination.value.current = page
}

const handleAdd = () => {
  dialogTitle.value = '新增双电源配置'
  formData.value = {
    id: 0,
    name: '',
    type: '二级重要',
    mainPowerName: '',
    mainPowerVoltage: '10kV',
    backupPowerName: '',
    backupPowerVoltage: '10kV',
    autoSwitch: true,
    switchTime: 3
  }
  dialogVisible.value = true
}

const handleView = (row: any) => {
  dialogTitle.value = '查看双电源配置'
  formData.value = { ...row, mainPowerName: row.mainPower.name, mainPowerVoltage: row.mainPower.voltage, backupPowerName: row.backupPower.name, backupPowerVoltage: row.backupPower.voltage }
  dialogVisible.value = true
}

const handleEdit = (row: any) => {
  dialogTitle.value = '编辑双电源配置'
  formData.value = { ...row, mainPowerName: row.mainPower.name, mainPowerVoltage: row.mainPower.voltage, backupPowerName: row.backupPower.name, backupPowerVoltage: row.backupPower.voltage }
  dialogVisible.value = true
}

const handleTrace = (row: any) => {
  router.push(`/power-tracing?id=${row.id}&name=${row.name}`)
}

const handleSave = () => {
  dialogVisible.value = false
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.dual-power-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.stats-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
}

.stat-card {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-size: 24px;
  font-weight: 600;
  color: #00f0ff;
}

.stat-label {
  font-size: 13px;
  color: #6a7a8a;
  margin-top: 4px;
}

.filter-bar {
  display: flex;
  gap: 12px;
}

.power-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.power-name {
  color: #e8e8e8;
}

.action-buttons {
  display: flex;
  gap: 4px;
  flex-wrap: nowrap;
}

.action-buttons .el-button {
  padding: 4px 8px;
  font-size: 12px;
}
</style>
