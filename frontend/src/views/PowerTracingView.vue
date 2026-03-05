<template>
  <TechPage title="电源追溯">
    <template #header-actions>
      <PageHelp
        page-key="power-tracing"
        page-title="电源追溯"
        :help-content="helpContent"
        :tips="helpTips"
      />
      <TechButton icon="Refresh" @click="loadData">刷新</TechButton>
    </template>

    <div class="power-tracing-content">
      <div class="search-section">
        <div class="search-bar">
          <el-input 
            v-model="searchText" 
            placeholder="输入设备名称或编号进行追溯" 
            style="width: 300px"
            clearable
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
          <TechButton type="primary" icon="Search" @click="handleTrace">开始追溯</TechButton>
        </div>
        <div class="quick-select">
          <span class="label">快速选择：</span>
          <el-tag 
            v-for="item in quickSelectItems" 
            :key="item.id"
            class="quick-tag"
            @click="handleQuickSelect(item)"
          >
            {{ item.name }}
          </el-tag>
        </div>
      </div>

      <div class="tracing-result" v-if="tracingResult">
        <div class="result-header">
          <div class="device-info">
            <h3>{{ tracingResult.device.name }}</h3>
            <el-tag :type="tracingResult.device.status === '正常' ? 'success' : 'danger'">
              {{ tracingResult.device.status }}
            </el-tag>
          </div>
          <div class="device-meta">
            <span>设备类型：{{ tracingResult.device.type }}</span>
            <span>电压等级：{{ tracingResult.device.voltage }}</span>
            <span>供电区域：{{ tracingResult.device.area }}</span>
          </div>
        </div>

        <div class="tracing-path">
          <h4>
            <el-icon><Guide /></el-icon>
            供电路径追溯
          </h4>
          <div class="path-container">
            <div 
              class="path-node" 
              v-for="(node, index) in tracingResult.path" 
              :key="index"
              :class="{ 
                'node-start': index === 0, 
                'node-end': index === tracingResult.path.length - 1,
                'node-warning': node.status === '异常'
              }"
            >
              <div class="node-icon">
                <el-icon :size="24">
                  <component :is="getNodeIcon(node.type)" />
                </el-icon>
              </div>
              <div class="node-info">
                <div class="node-name">{{ node.name }}</div>
                <div class="node-meta">
                  <span>{{ node.type }}</span>
                  <span>{{ node.voltage }}</span>
                </div>
                <el-tag 
                  v-if="node.status === '异常'" 
                  type="danger" 
                  size="small"
                >
                  异常
                </el-tag>
              </div>
              <div class="node-arrow" v-if="index < tracingResult.path.length - 1">
                <el-icon :size="20"><ArrowRight /></el-icon>
              </div>
            </div>
          </div>
        </div>

        <div class="tracing-details">
          <div class="detail-card">
            <h4>
              <el-icon><DataLine /></el-icon>
              供电信息
            </h4>
            <div class="detail-content">
              <div class="detail-item">
                <span class="label">供电电源</span>
                <span class="value">{{ tracingResult.details.powerSource }}</span>
              </div>
              <div class="detail-item">
                <span class="label">供电线路</span>
                <span class="value">{{ tracingResult.details.powerLine }}</span>
              </div>
              <div class="detail-item">
                <span class="label">供电容量</span>
                <span class="value">{{ tracingResult.details.capacity }}</span>
              </div>
              <div class="detail-item">
                <span class="label">当前负载</span>
                <span class="value">{{ tracingResult.details.load }}</span>
              </div>
              <div class="detail-item">
                <span class="label">负载率</span>
                <span class="value">{{ tracingResult.details.loadRate }}</span>
              </div>
              <div class="detail-item">
                <span class="label">供电距离</span>
                <span class="value">{{ tracingResult.details.distance }}</span>
              </div>
            </div>
          </div>

          <div class="detail-card">
            <h4>
              <el-icon><Connection /></el-icon>
              备用电源
            </h4>
            <div class="detail-content">
              <div class="detail-item">
                <span class="label">备用电源</span>
                <span class="value">{{ tracingResult.details.backupSource }}</span>
              </div>
              <div class="detail-item">
                <span class="label">备用线路</span>
                <span class="value">{{ tracingResult.details.backupLine }}</span>
              </div>
              <div class="detail-item">
                <span class="label">备用容量</span>
                <span class="value">{{ tracingResult.details.backupCapacity }}</span>
              </div>
              <div class="detail-item">
                <span class="label">切换时间</span>
                <span class="value">{{ tracingResult.details.switchTime }}</span>
              </div>
            </div>
          </div>

          <div class="detail-card">
            <h4>
              <el-icon><Warning /></el-icon>
              风险评估
            </h4>
            <div class="detail-content">
              <div class="risk-item">
                <span class="label">风险等级</span>
                <el-tag :type="getRiskType(tracingResult.risk.level)">
                  {{ tracingResult.risk.level }}
                </el-tag>
              </div>
              <div class="risk-item">
                <span class="label">风险因素</span>
                <div class="risk-factors">
                  <el-tag 
                    v-for="(factor, index) in tracingResult.risk.factors" 
                    :key="index"
                    size="small"
                    class="risk-factor"
                  >
                    {{ factor }}
                  </el-tag>
                </div>
              </div>
              <div class="risk-item">
                <span class="label">建议措施</span>
                <div class="suggestions">
                  <div 
                    v-for="(suggestion, index) in tracingResult.risk.suggestions" 
                    :key="index"
                    class="suggestion"
                  >
                    {{ index + 1 }}. {{ suggestion }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="topology-section">
          <h4>
            <el-icon><Share /></el-icon>
            供电拓扑图
          </h4>
          <div class="topology-container">
            <svg class="topology-svg" viewBox="0 0 900 400">
              <defs>
                <linearGradient id="lineGradient" x1="0%" y1="0%" x2="100%" y2="0%">
                  <stop offset="0%" style="stop-color:#00f0ff;stop-opacity:1" />
                  <stop offset="100%" style="stop-color:#0080ff;stop-opacity:1" />
                </linearGradient>
                <filter id="glow">
                  <feGaussianBlur stdDeviation="3" result="coloredBlur"/>
                  <feMerge>
                    <feMergeNode in="coloredBlur"/>
                    <feMergeNode in="SourceGraphic"/>
                  </feMerge>
                </filter>
                <marker id="arrowhead" markerWidth="10" markerHeight="7" refX="9" refY="3.5" orient="auto">
                  <polygon points="0 0, 10 3.5, 0 7" fill="#00f0ff" />
                </marker>
              </defs>
              
              <g class="topology-nodes">
                <g class="node power-company" transform="translate(50, 200)">
                  <rect x="0" y="0" width="120" height="60" rx="8" fill="rgba(102, 126, 234, 0.3)" stroke="#667eea" stroke-width="2" filter="url(#glow)" />
                  <text x="60" y="25" text-anchor="middle" fill="#e8e8e8" font-size="12">市供电公司</text>
                  <text x="60" y="42" text-anchor="middle" fill="#6a7a8a" font-size="10">220kV</text>
                </g>
                
                <g class="node substation-1" transform="translate(250, 100)">
                  <rect x="0" y="0" width="100" height="50" rx="8" fill="rgba(0, 240, 255, 0.1)" stroke="#00f0ff" stroke-width="2" filter="url(#glow)" />
                  <text x="50" y="22" text-anchor="middle" fill="#e8e8e8" font-size="11">中心变电站</text>
                  <text x="50" y="38" text-anchor="middle" fill="#6a7a8a" font-size="10">110kV</text>
                </g>
                
                <g class="node substation-2" transform="translate(250, 250)">
                  <rect x="0" y="0" width="100" height="50" rx="8" fill="rgba(0, 240, 255, 0.1)" stroke="#00f0ff" stroke-width="2" filter="url(#glow)" />
                  <text x="50" y="22" text-anchor="middle" fill="#e8e8e8" font-size="11">南郊变电站</text>
                  <text x="50" y="38" text-anchor="middle" fill="#6a7a8a" font-size="10">110kV</text>
                </g>
                
                <g class="node substation-3" transform="translate(450, 100)">
                  <rect x="0" y="0" width="100" height="50" rx="8" fill="rgba(17, 153, 142, 0.3)" stroke="#11998e" stroke-width="2" filter="url(#glow)" />
                  <text x="50" y="22" text-anchor="middle" fill="#e8e8e8" font-size="11">城东变电站</text>
                  <text x="50" y="38" text-anchor="middle" fill="#6a7a8a" font-size="10">35kV</text>
                </g>
                
                <g class="node substation-4" transform="translate(450, 250)">
                  <rect x="0" y="0" width="100" height="50" rx="8" fill="rgba(255, 152, 0, 0.3)" stroke="#ff9800" stroke-width="2" filter="url(#glow)" />
                  <text x="50" y="22" text-anchor="middle" fill="#e8e8e8" font-size="11">城西变电站</text>
                  <text x="50" y="38" text-anchor="middle" fill="#6a7a8a" font-size="10">35kV</text>
                </g>
                
                <g class="node distribution" transform="translate(650, 175)">
                  <rect x="0" y="0" width="90" height="50" rx="8" fill="rgba(0, 240, 255, 0.1)" stroke="#00f0ff" stroke-width="2" filter="url(#glow)" />
                  <text x="45" y="22" text-anchor="middle" fill="#e8e8e8" font-size="11">医院配电房</text>
                  <text x="45" y="38" text-anchor="middle" fill="#6a7a8a" font-size="10">10kV</text>
                </g>
                
                <g class="node user" transform="translate(800, 175)">
                  <rect x="0" y="0" width="80" height="50" rx="8" fill="rgba(56, 239, 125, 0.3)" stroke="#38ef7d" stroke-width="2" filter="url(#glow)" />
                  <text x="40" y="22" text-anchor="middle" fill="#e8e8e8" font-size="11">市中心医院</text>
                  <text x="40" y="38" text-anchor="middle" fill="#6a7a8a" font-size="10">用户</text>
                </g>
              </g>
              
              <g class="topology-edges">
                <path d="M170 200 Q210 200 250 125" stroke="url(#lineGradient)" stroke-width="2" fill="none" marker-end="url(#arrowhead)" />
                <path d="M170 200 Q210 200 250 275" stroke="url(#lineGradient)" stroke-width="2" fill="none" marker-end="url(#arrowhead)" />
                <path d="M350 125 L450 125" stroke="url(#lineGradient)" stroke-width="2" fill="none" marker-end="url(#arrowhead)" />
                <path d="M350 275 L450 275" stroke="rgba(255, 152, 0, 0.6)" stroke-width="2" stroke-dasharray="5,5" fill="none" marker-end="url(#arrowhead)" />
                <path d="M550 125 Q600 125 650 200" stroke="url(#lineGradient)" stroke-width="2" fill="none" marker-end="url(#arrowhead)" />
                <path d="M550 275 Q600 275 650 200" stroke="rgba(255, 152, 0, 0.6)" stroke-width="2" stroke-dasharray="5,5" fill="none" marker-end="url(#arrowhead)" />
                <path d="M740 200 L800 200" stroke="url(#lineGradient)" stroke-width="2" fill="none" marker-end="url(#arrowhead)" />
              </g>
              
              <g class="topology-labels">
                <text x="200" y="145" fill="#6a7a8a" font-size="10">中心线</text>
                <text x="200" y="295" fill="#6a7a8a" font-size="10">南郊线</text>
                <text x="400" y="110" fill="#6a7a8a" font-size="10">城东线</text>
                <text x="400" y="295" fill="#ff9800" font-size="10">城西线(备用)</text>
                <text x="610" y="145" fill="#6a7a8a" font-size="10">主供线路</text>
                <text x="610" y="275" fill="#ff9800" font-size="10">备用线路</text>
              </g>
              
              <g class="power-flow">
                <circle r="4" fill="#00f0ff">
                  <animateMotion dur="3s" repeatCount="indefinite" path="M170 200 Q210 200 250 125 L450 125 Q500 125 650 200 L800 200" />
                </circle>
                <circle r="3" fill="#ff9800" opacity="0.6">
                  <animateMotion dur="4s" repeatCount="indefinite" path="M170 200 Q210 200 250 275 L450 275 Q500 275 650 200" />
                </circle>
              </g>
            </svg>
            
            <div class="topology-legend">
              <div class="legend-item">
                <span class="legend-line solid"></span>
                <span>主供线路</span>
              </div>
              <div class="legend-item">
                <span class="legend-line dashed"></span>
                <span>备用线路</span>
              </div>
              <div class="legend-item">
                <span class="legend-dot primary"></span>
                <span>主电源流向</span>
              </div>
              <div class="legend-item">
                <span class="legend-dot backup"></span>
                <span>备用电源流向</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="empty-state" v-else>
        <el-icon :size="64"><Search /></el-icon>
        <p>输入设备名称或编号开始追溯</p>
        <p class="sub-text">支持追溯变电站、线路、台区等设备的供电来源</p>
      </div>
    </div>
  </TechPage>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { 
  Search, Guide, ArrowRight, DataLine, Connection, Warning, Share,
  OfficeBuilding, Lightning, HomeFilled
} from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechButton from '@/components/common/TechButton.vue'
import PageHelp from '@/components/common/PageHelp.vue'

const route = useRoute()

const helpContent = [
  {
    icon: '🔍',
    title: '追溯功能',
    items: [
      '输入设备名称或编号进行追溯',
      '展示完整的供电路径',
      '分析供电可靠性和风险'
    ]
  },
  {
    icon: '📊',
    title: '分析内容',
    items: [
      '供电电源和线路信息',
      '备用电源配置情况',
      '风险评估和建议措施'
    ]
  }
]

const helpTips = [
  '支持追溯变电站、线路、台区等设备',
  '追溯结果可导出为报告'
]

const searchText = ref('')
const loading = ref(false)
const tracingResult = ref<any>(null)

const quickSelectItems = ref([
  { id: 1, name: '市中心医院' },
  { id: 2, name: '市政府大楼' },
  { id: 3, name: '火车站' },
  { id: 4, name: '电信数据中心' },
  { id: 5, name: '城东变电站' }
])

const mockResult = {
  device: {
    name: '市中心医院',
    type: '重要用户',
    voltage: '10kV',
    status: '正常',
    area: '市中心区域'
  },
  path: [
    { name: '市供电公司', type: '供电公司', voltage: '220kV', status: '正常' },
    { name: '中心变电站', type: '变电站', voltage: '110kV', status: '正常' },
    { name: '城东变电站', type: '变电站', voltage: '35kV', status: '正常' },
    { name: '医院配电房', type: '配电房', voltage: '10kV', status: '正常' },
    { name: '市中心医院', type: '用户', voltage: '10kV', status: '正常' }
  ],
  details: {
    powerSource: '城东变电站',
    powerLine: '城东线10kV线路',
    capacity: '2000kVA',
    load: '1200kW',
    loadRate: '60%',
    distance: '2.5km',
    backupSource: '城西变电站',
    backupLine: '城西线10kV线路',
    backupCapacity: '1500kVA',
    switchTime: '3秒'
  },
  risk: {
    level: '低风险',
    factors: ['单路供电距离较长'],
    suggestions: [
      '定期检查备用电源可用性',
      '建议增加供电线路监测点'
    ]
  }
}

const getNodeIcon = (type: string) => {
  switch (type) {
    case '供电公司':
      return OfficeBuilding
    case '变电站':
      return Lightning
    case '配电房':
      return HomeFilled
    default:
      return OfficeBuilding
  }
}

const getRiskType = (level: string) => {
  switch (level) {
    case '高风险':
      return 'danger'
    case '中风险':
      return 'warning'
    default:
      return 'success'
  }
}

const handleTrace = () => {
  if (!searchText.value) return
  
  loading.value = true
  setTimeout(() => {
    tracingResult.value = mockResult
    loading.value = false
  }, 500)
}

const handleQuickSelect = (item: any) => {
  searchText.value = item.name
  handleTrace()
}

const loadData = () => {
  // 刷新数据
}

onMounted(() => {
  const id = route.query.id
  const name = route.query.name as string
  if (name) {
    searchText.value = name
    handleTrace()
  }
})
</script>

<style scoped>
.power-tracing-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.search-section {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  padding: 20px;
}

.search-bar {
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}

.quick-select {
  display: flex;
  align-items: center;
  gap: 8px;
}

.quick-select .label {
  color: #6a7a8a;
  font-size: 13px;
}

.quick-tag {
  cursor: pointer;
  transition: all 0.3s ease;
}

.quick-tag:hover {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
  color: #00f0ff;
}

.tracing-result {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.result-header {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  padding: 20px;
}

.device-info {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.device-info h3 {
  margin: 0;
  font-size: 20px;
  color: #e8e8e8;
}

.device-meta {
  display: flex;
  gap: 24px;
  color: #6a7a8a;
  font-size: 13px;
}

.tracing-path {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  padding: 20px;
}

.tracing-path h4 {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 0 0 16px 0;
  color: #00f0ff;
  font-size: 16px;
}

.path-container {
  display: flex;
  align-items: center;
  overflow-x: auto;
  padding: 10px 0;
}

.path-node {
  display: flex;
  align-items: center;
  flex-shrink: 0;
}

.node-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  background: rgba(0, 240, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #00f0ff;
}

.node-start .node-icon {
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-color: #667eea;
  color: white;
}

.node-end .node-icon {
  background: linear-gradient(135deg, #11998e, #38ef7d);
  border-color: #11998e;
  color: white;
}

.node-warning .node-icon {
  background: rgba(255, 87, 108, 0.2);
  border-color: #ff576c;
  color: #ff576c;
}

.node-info {
  margin-left: 12px;
  min-width: 100px;
}

.node-name {
  font-size: 14px;
  color: #e8e8e8;
  font-weight: 500;
}

.node-meta {
  display: flex;
  gap: 8px;
  font-size: 12px;
  color: #6a7a8a;
  margin-top: 4px;
}

.node-arrow {
  margin: 0 16px;
  color: #00f0ff;
}

.tracing-details {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.detail-card {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  padding: 20px;
}

.detail-card h4 {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 0 0 16px 0;
  color: #00f0ff;
  font-size: 15px;
  padding-bottom: 12px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.1);
}

.detail-content {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.detail-item, .risk-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-item .label, .risk-item .label {
  color: #6a7a8a;
  font-size: 13px;
}

.detail-item .value {
  color: #e8e8e8;
  font-size: 13px;
}

.risk-factors {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.risk-factor {
  background: rgba(255, 193, 7, 0.1);
  border-color: rgba(255, 193, 7, 0.3);
  color: #ffc107;
}

.suggestions {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 8px;
}

.suggestion {
  font-size: 13px;
  color: #a0b0c0;
  line-height: 1.5;
}

.topology-section {
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  padding: 20px;
}

.topology-section h4 {
  display: flex;
  align-items: center;
  gap: 8px;
  margin: 0 0 16px 0;
  color: #00f0ff;
  font-size: 15px;
}

.topology-container {
  height: 400px;
  border-radius: 8px;
  overflow: hidden;
  background: rgba(0, 0, 0, 0.3);
  position: relative;
}

.topology-svg {
  width: 100%;
  height: 100%;
}

.topology-legend {
  position: absolute;
  bottom: 10px;
  left: 10px;
  display: flex;
  gap: 16px;
  background: rgba(0, 0, 0, 0.6);
  padding: 8px 12px;
  border-radius: 6px;
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 11px;
  color: #a0b0c0;
}

.legend-line {
  width: 20px;
  height: 2px;
}

.legend-line.solid {
  background: linear-gradient(90deg, #00f0ff, #0080ff);
}

.legend-line.dashed {
  background: repeating-linear-gradient(
    90deg,
    #ff9800 0px,
    #ff9800 5px,
    transparent 5px,
    transparent 10px
  );
}

.legend-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.legend-dot.primary {
  background: #00f0ff;
  box-shadow: 0 0 6px #00f0ff;
}

.legend-dot.backup {
  background: #ff9800;
  box-shadow: 0 0 6px #ff9800;
}

.topology-placeholder {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.2);
  color: #4a5568;
}

.topology-placeholder p {
  margin: 8px 0 0 0;
}

.topology-placeholder .sub-text {
  font-size: 12px;
  color: #3a4a5a;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 0;
  color: #4a5568;
}

.empty-state p {
  margin: 16px 0 0 0;
  font-size: 16px;
}

.empty-state .sub-text {
  font-size: 13px;
  color: #3a4a5a;
  margin-top: 8px;
}
</style>
