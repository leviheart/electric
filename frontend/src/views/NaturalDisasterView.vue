<template>
  <TechPage title="自然灾害预警">
    <template #header-actions>
      <PageHelp
        page-key="natural-disaster"
        page-title="自然灾害预警"
        :help-content="helpContent"
        :tips="helpTips"
      />
      <TechButton type="primary" icon="Plus" @click="handleAddDisaster">发布预警</TechButton>
      <TechButton icon="Refresh" @click="loadData">刷新</TechButton>
    </template>

    <div class="disaster-content">
      <div class="stats-cards">
        <div class="stat-card" v-for="stat in disasterStats" :key="stat.type">
          <div class="stat-icon" :style="{ background: stat.gradient }">
            <span class="stat-emoji">{{ stat.icon }}</span>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stat.count }}</div>
            <div class="stat-label">{{ stat.label }}</div>
          </div>
        </div>
      </div>

      <div class="filter-bar">
        <el-input 
          v-model="searchText" 
          placeholder="搜索预警信息" 
          style="width: 200px"
          clearable
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-select v-model="filterType" placeholder="灾害类型" style="width: 120px" clearable>
          <el-option label="全部" value="" />
          <el-option label="大风" value="STRONG_WIND" />
          <el-option label="山火" value="WILDFIRE" />
          <el-option label="冰寒" value="ICE_STORM" />
          <el-option label="洪涝" value="FLOOD" />
          <el-option label="雷电" value="LIGHTNING" />
          <el-option label="地震" value="EARTHQUAKE" />
        </el-select>
        <el-select v-model="filterLevel" placeholder="预警级别" style="width: 120px" clearable>
          <el-option label="全部" value="" />
          <el-option label="红色" value="CRITICAL" />
          <el-option label="橙色" value="HIGH" />
          <el-option label="黄色" value="MEDIUM" />
          <el-option label="蓝色" value="LOW" />
        </el-select>
      </div>

      <div class="disaster-grid">
        <div 
          class="disaster-card" 
          v-for="disaster in filteredDisasters" 
          :key="disaster.id"
          :class="[`level-${disaster.level.toLowerCase()}`]"
        >
          <div class="card-header">
            <div class="disaster-icon">{{ getDisasterIcon(disaster.type) }}</div>
            <div class="disaster-title">
              <div class="title-text">{{ disaster.title }}</div>
              <el-tag :type="getLevelType(disaster.level)" size="small">
                {{ getLevelLabel(disaster.level) }}
              </el-tag>
            </div>
          </div>
          
          <div class="card-body">
            <div class="info-row">
              <span class="label">灾害类型</span>
              <span class="value">{{ getTypeLabel(disaster.type) }}</span>
            </div>
            <div class="info-row">
              <span class="label">影响区域</span>
              <span class="value">{{ disaster.location }}</span>
            </div>
            <div class="info-row">
              <span class="label">影响半径</span>
              <span class="value">{{ disaster.radius / 1000 }} 公里</span>
            </div>
            <div class="info-row">
              <span class="label">影响设备</span>
              <span class="value">{{ disaster.affectedDevices }} 台</span>
            </div>
            <div class="info-row description">
              <span class="label">预警描述</span>
              <span class="value">{{ disaster.description }}</span>
            </div>
          </div>
          
          <div class="card-footer">
            <span class="time">{{ formatTime(disaster.timestamp) }}</span>
            <div class="actions">
              <el-button type="primary" size="small" @click="handleLocate(disaster)">定位</el-button>
              <el-button type="warning" size="small" @click="handleEdit(disaster)">编辑</el-button>
              <el-button type="success" size="small" @click="handleResolve(disaster)">解除</el-button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle" 
      width="600px"
      class="tech-dialog"
    >
      <el-form :model="formData" label-width="100px">
        <el-form-item label="灾害类型">
          <el-select v-model="formData.type" style="width: 100%">
            <el-option label="大风预警" value="STRONG_WIND" />
            <el-option label="山火预警" value="WILDFIRE" />
            <el-option label="冰寒预警" value="ICE_STORM" />
            <el-option label="洪涝预警" value="FLOOD" />
            <el-option label="雷电预警" value="LIGHTNING" />
            <el-option label="地震预警" value="EARTHQUAKE" />
          </el-select>
        </el-form-item>
        <el-form-item label="预警级别">
          <el-select v-model="formData.level" style="width: 100%">
            <el-option label="红色预警" value="CRITICAL" />
            <el-option label="橙色预警" value="HIGH" />
            <el-option label="黄色预警" value="MEDIUM" />
            <el-option label="蓝色预警" value="LOW" />
          </el-select>
        </el-form-item>
        <el-form-item label="预警标题">
          <el-input v-model="formData.title" placeholder="请输入预警标题" />
        </el-form-item>
        <el-form-item label="影响区域">
          <el-input v-model="formData.location" placeholder="请输入影响区域" />
        </el-form-item>
        <el-form-item label="影响半径">
          <el-input-number v-model="formData.radius" :min="100" :max="50000" :step="100" />
          <span style="margin-left: 8px">米</span>
        </el-form-item>
        <el-form-item label="预警描述">
          <el-input v-model="formData.description" type="textarea" :rows="3" placeholder="请输入预警描述" />
        </el-form-item>
        <el-form-item label="经度">
          <el-input-number v-model="formData.longitude" :precision="6" :min="-180" :max="180" />
        </el-form-item>
        <el-form-item label="纬度">
          <el-input-number v-model="formData.latitude" :precision="6" :min="-90" :max="90" />
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
import { Search } from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechButton from '@/components/common/TechButton.vue'
import PageHelp from '@/components/common/PageHelp.vue'

const router = useRouter()

const helpContent = [
  {
    icon: '🌪️',
    title: '灾害监测',
    items: [
      '实时监测大风、山火、冰寒等自然灾害',
      '自动关联影响范围内的电力设备',
      '支持手动发布和解除预警'
    ]
  },
  {
    icon: '📍',
    title: '地图展示',
    items: [
      '在地图上直观展示灾害影响范围',
      '不同灾害类型使用不同颜色标识',
      '支持快速定位和设备关联分析'
    ]
  }
]

const helpTips = [
  '红色预警表示灾害已发生或即将发生',
  '建议及时通知相关运维人员',
  '可通过首页面板快速查看当前预警'
]

const loading = ref(false)
const searchText = ref('')
const filterType = ref('')
const filterLevel = ref('')
const dialogVisible = ref(false)
const dialogTitle = ref('发布预警')

const formData = ref({
  id: '',
  type: 'STRONG_WIND',
  level: 'MEDIUM',
  title: '',
  location: '',
  description: '',
  radius: 5000,
  latitude: 30.57,
  longitude: 114.35
})

const disasters = ref([
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
  }
])

const disasterStats = computed(() => [
  { type: 'STRONG_WIND', icon: '💨', label: '大风', count: disasters.value.filter(d => d.type === 'STRONG_WIND').length, gradient: 'linear-gradient(135deg, #00bcd4, #00acc1)' },
  { type: 'WILDFIRE', icon: '🔥', label: '山火', count: disasters.value.filter(d => d.type === 'WILDFIRE').length, gradient: 'linear-gradient(135deg, #ff5722, #e64a19)' },
  { type: 'ICE_STORM', icon: '❄️', label: '冰寒', count: disasters.value.filter(d => d.type === 'ICE_STORM').length, gradient: 'linear-gradient(135deg, #03a9f4, #0288d1)' },
  { type: 'FLOOD', icon: '🌊', label: '洪涝', count: disasters.value.filter(d => d.type === 'FLOOD').length, gradient: 'linear-gradient(135deg, #2196f3, #1976d2)' },
  { type: 'LIGHTNING', icon: '⛈️', label: '雷电', count: disasters.value.filter(d => d.type === 'LIGHTNING').length, gradient: 'linear-gradient(135deg, #ffeb3b, #ffc107)' },
  { type: 'EARTHQUAKE', icon: '🌍', label: '地震', count: disasters.value.filter(d => d.type === 'EARTHQUAKE').length, gradient: 'linear-gradient(135deg, #9c27b0, #7b1fa2)' }
])

const filteredDisasters = computed(() => {
  let data = disasters.value
  if (searchText.value) {
    data = data.filter(d => 
      d.title.includes(searchText.value) || 
      d.location.includes(searchText.value) ||
      d.description.includes(searchText.value)
    )
  }
  if (filterType.value) {
    data = data.filter(d => d.type === filterType.value)
  }
  if (filterLevel.value) {
    data = data.filter(d => d.level === filterLevel.value)
  }
  return data
})

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

const getTypeLabel = (type: string): string => {
  const labels: Record<string, string> = {
    STRONG_WIND: '大风预警',
    WILDFIRE: '山火预警',
    ICE_STORM: '冰寒预警',
    FLOOD: '洪涝预警',
    LIGHTNING: '雷电预警',
    EARTHQUAKE: '地震预警'
  }
  return labels[type] || type
}

const getLevelLabel = (level: string): string => {
  const labels: Record<string, string> = {
    CRITICAL: '红色预警',
    HIGH: '橙色预警',
    MEDIUM: '黄色预警',
    LOW: '蓝色预警'
  }
  return labels[level] || level
}

const getLevelType = (level: string): string => {
  const types: Record<string, string> = {
    CRITICAL: 'danger',
    HIGH: 'warning',
    MEDIUM: '',
    LOW: 'info'
  }
  return types[level] || ''
}

const formatTime = (timestamp: string): string => {
  const date = new Date(timestamp)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const loadData = () => {
  loading.value = true
  setTimeout(() => {
    loading.value = false
  }, 500)
}

const handleAddDisaster = () => {
  dialogTitle.value = '发布预警'
  formData.value = {
    id: '',
    type: 'STRONG_WIND',
    level: 'MEDIUM',
    title: '',
    location: '',
    description: '',
    radius: 5000,
    latitude: 30.57,
    longitude: 114.35
  }
  dialogVisible.value = true
}

const handleLocate = (disaster: any) => {
  const disasterData = encodeURIComponent(JSON.stringify(disaster))
  router.push({
    path: '/',
    query: {
      disaster: disasterData
    }
  })
}

const handleEdit = (disaster: any) => {
  dialogTitle.value = '编辑预警'
  formData.value = { ...disaster }
  dialogVisible.value = true
}

const handleResolve = (disaster: any) => {
  console.log('解除预警:', disaster)
}

const handleSave = () => {
  dialogVisible.value = false
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.disaster-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.stats-cards {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
}

.stat-card {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  padding: 16px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.stat-icon {
  width: 44px;
  height: 44px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.stat-emoji {
  font-size: 22px;
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-value {
  font-size: 22px;
  font-weight: 600;
  color: #00f0ff;
}

.stat-label {
  font-size: 12px;
  color: #6a7a8a;
  margin-top: 2px;
}

.filter-bar {
  display: flex;
  gap: 12px;
}

.disaster-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.disaster-card {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  overflow: hidden;
}

.disaster-card.level-critical {
  border-color: rgba(255, 0, 64, 0.5);
  background: rgba(255, 0, 64, 0.05);
}

.disaster-card.level-high {
  border-color: rgba(255, 152, 0, 0.5);
  background: rgba(255, 152, 0, 0.05);
}

.disaster-card.level-medium {
  border-color: rgba(255, 235, 59, 0.5);
  background: rgba(255, 235, 59, 0.05);
}

.disaster-card.level-low {
  border-color: rgba(33, 150, 243, 0.5);
  background: rgba(33, 150, 243, 0.05);
}

.card-header {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.1);
}

.disaster-icon {
  font-size: 36px;
}

.disaster-title {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.title-text {
  font-size: 16px;
  font-weight: 600;
  color: #e8e8e8;
}

.card-body {
  padding: 16px;
}

.info-row {
  display: flex;
  margin-bottom: 10px;
}

.info-row .label {
  width: 80px;
  color: #6a7a8a;
  font-size: 13px;
  flex-shrink: 0;
}

.info-row .value {
  flex: 1;
  color: #e8e8e8;
  font-size: 13px;
}

.info-row.description .value {
  line-height: 1.5;
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background: rgba(0, 0, 0, 0.2);
  border-top: 1px solid rgba(0, 240, 255, 0.1);
}

.time {
  font-size: 12px;
  color: #6a7a8a;
}

.actions {
  display: flex;
  gap: 8px;
}
</style>
