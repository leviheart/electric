<template>
  <div class="search-box">
    <div class="search-input-wrapper">
      <el-input
        v-model="searchKeyword"
        placeholder="搜索变电站、线路、台区..."
        :prefix-icon="Search"
        clearable
        @input="handleSearch"
        @clear="handleClear"
        @keyup.enter="handleSearch"
      />
      <el-button 
        type="primary" 
        :icon="Search" 
        @click="handleSearch"
        :loading="loading"
        circle
      />
    </div>
    
    <div class="filter-options" v-if="showFilters">
      <el-select
        v-model="selectedVoltageLevel"
        placeholder="电压等级"
        clearable
        size="small"
        @change="handleFilterChange"
      >
        <el-option label="220kV" value="220kV" />
        <el-option label="110kV" value="110kV" />
        <el-option label="35kV" value="35kV" />
      </el-select>
      
      <el-select
        v-model="selectedStatus"
        placeholder="运行状态"
        clearable
        size="small"
        @change="handleFilterChange"
      >
        <el-option label="运行中" value="运行中" />
        <el-option label="停运" value="停运" />
        <el-option label="检修" value="检修" />
      </el-select>
    </div>
    
    <div class="search-results" v-if="showResults && searchResult">
      <div class="results-header">
        <span class="results-count">
          找到 {{ searchResult.totalCount }} 个结果
        </span>
        <el-button text @click="closeResults">
          <el-icon><Close /></el-icon>
        </el-button>
      </div>
      
      <div class="results-tabs">
        <el-tabs v-model="activeTab">
          <el-tab-pane 
            :label="`变电站 (${searchResult.substations.length})`" 
            name="substations"
          >
            <div class="result-list">
              <div
                v-for="item in searchResult.substations"
                :key="item.id"
                class="result-item"
                @click="selectResult('substation', item)"
              >
                <div class="result-icon substation-icon">⚡</div>
                <div class="result-info">
                  <div class="result-name">{{ item.name }}</div>
                  <div class="result-meta">
                    <span class="voltage-tag">{{ item.voltageLevel }}</span>
                    <span :class="['status-tag', getStatusClass(item.status)]">
                      {{ item.status }}
                    </span>
                  </div>
                </div>
              </div>
              <div v-if="searchResult.substations.length === 0" class="no-results">
                暂无匹配的变电站
              </div>
            </div>
          </el-tab-pane>
          
          <el-tab-pane 
            :label="`线路 (${searchResult.lines.length})`" 
            name="lines"
          >
            <div class="result-list">
              <div
                v-for="item in searchResult.lines"
                :key="item.id"
                class="result-item"
                @click="selectResult('line', item)"
              >
                <div class="result-icon line-icon">〰</div>
                <div class="result-info">
                  <div class="result-name">{{ item.name }}</div>
                  <div class="result-meta">
                    <span>{{ item.startSubstation }} → {{ item.endSubstation }}</span>
                    <span class="voltage-tag">{{ item.voltageLevel }}</span>
                    <span :class="['status-tag', getStatusClass(item.status)]">
                      {{ item.status }}
                    </span>
                  </div>
                </div>
              </div>
              <div v-if="searchResult.lines.length === 0" class="no-results">
                暂无匹配的线路
              </div>
            </div>
          </el-tab-pane>
          
          <el-tab-pane 
            :label="`台区 (${searchResult.areas.length})`" 
            name="areas"
          >
            <div class="result-list">
              <div
                v-for="item in searchResult.areas"
                :key="item.id"
                class="result-item"
                @click="selectResult('area', item)"
              >
                <div class="result-icon area-icon">📍</div>
                <div class="result-info">
                  <div class="result-name">{{ item.name }}</div>
                  <div class="result-meta">
                    <span>所属: {{ item.substationName }}</span>
                    <span>用户: {{ item.customerCount }}</span>
                    <span :class="['status-tag', getStatusClass(item.status)]">
                      {{ item.status }}
                    </span>
                  </div>
                </div>
              </div>
              <div v-if="searchResult.areas.length === 0" class="no-results">
                暂无匹配的台区
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onUnmounted } from 'vue'
import { Search, Close } from '@element-plus/icons-vue'
import { globalSearch, type SearchResult as SearchResultType, type SearchParams } from '../api/search'
import type { Substation, TransmissionLine, Area } from '../types'

const props = withDefaults(defineProps<{
  showFilters?: boolean
  debounceDelay?: number
}>(), {
  showFilters: true,
  debounceDelay: 300
})

const emit = defineEmits<{
  select: [result: { type: string; data: Substation | TransmissionLine | Area }]
  clear: []
}>()

const searchKeyword = ref('')
const selectedVoltageLevel = ref('')
const selectedStatus = ref('')
const loading = ref(false)
const showResults = ref(false)
const searchResult = ref<SearchResultType | null>(null)
const activeTab = ref('substations')
let debounceTimer: ReturnType<typeof setTimeout> | null = null

const handleSearch = () => {
  if (debounceTimer) {
    clearTimeout(debounceTimer)
  }
  
  debounceTimer = setTimeout(() => {
    performSearch()
  }, props.debounceDelay)
}

const performSearch = async () => {
  if (!searchKeyword.value.trim() && !selectedVoltageLevel.value && !selectedStatus.value) {
    showResults.value = false
    searchResult.value = null
    return
  }
  
  loading.value = true
  
  try {
    const params: SearchParams = {
      keyword: searchKeyword.value.trim() || undefined,
      voltageLevel: selectedVoltageLevel.value || undefined,
      status: selectedStatus.value || undefined
    }
    
    const response = await globalSearch(params)
    searchResult.value = response.data
    showResults.value = true
    
    if (response.data.substations.length > 0) {
      activeTab.value = 'substations'
    } else if (response.data.lines.length > 0) {
      activeTab.value = 'lines'
    } else if (response.data.areas.length > 0) {
      activeTab.value = 'areas'
    }
  } catch (error) {
    console.error('搜索失败:', error)
  } finally {
    loading.value = false
  }
}

const handleFilterChange = () => {
  if (searchKeyword.value.trim() || selectedVoltageLevel.value || selectedStatus.value) {
    performSearch()
  }
}

const handleClear = () => {
  searchKeyword.value = ''
  selectedVoltageLevel.value = ''
  selectedStatus.value = ''
  showResults.value = false
  searchResult.value = null
  emit('clear')
}

onUnmounted(() => {
  if (debounceTimer) {
    clearTimeout(debounceTimer)
    debounceTimer = null
  }
})

const closeResults = () => {
  showResults.value = false
}

const selectResult = (type: string, data: Substation | TransmissionLine | Area) => {
  emit('select', { type, data })
  showResults.value = false
}

const getStatusClass = (status: string): string => {
  switch (status) {
    case '运行中':
    case '正常':
      return 'status-active'
    case '停运':
    case '异常':
      return 'status-error'
    case '检修':
      return 'status-warning'
    default:
      return ''
  }
}
</script>

<style scoped>
.search-box {
  position: relative;
  width: 100%;
}

.search-input-wrapper {
  display: flex;
  gap: 6px;
  padding: 6px 10px;
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 20px;
}

.search-input-wrapper :deep(.el-input) {
  flex: 1;
}

.search-input-wrapper :deep(.el-input__wrapper) {
  background: transparent;
  border: none;
  box-shadow: none;
  padding: 0 8px;
}

.search-input-wrapper :deep(.el-input__inner) {
  color: #ffffff;
  font-size: 13px;
}

.search-input-wrapper :deep(.el-input__inner::placeholder) {
  color: rgba(255, 255, 255, 0.5);
}

.search-input-wrapper :deep(.el-button) {
  width: 30px;
  height: 30px;
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
  color: #0a0a1a;
}

.search-input-wrapper :deep(.el-button:hover) {
  box-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

.filter-options {
  display: none;
}

.search-results {
  position: absolute;
  top: calc(100% + 6px);
  left: 0;
  right: 0;
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 10px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
  z-index: 1000;
  max-height: 350px;
  overflow: hidden;
}

.results-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.results-count {
  color: #00f0ff;
  font-size: 14px;
}

.results-tabs :deep(.el-tabs__header) {
  margin: 0;
  padding: 0 16px;
  background: rgba(0, 0, 0, 0.2);
}

.results-tabs :deep(.el-tabs__item) {
  color: rgba(255, 255, 255, 0.6);
}

.results-tabs :deep(.el-tabs__item.is-active) {
  color: #00f0ff;
}

.results-tabs :deep(.el-tabs__active-bar) {
  background: #00f0ff;
}

.result-list {
  max-height: 280px;
  overflow-y: auto;
  padding: 8px;
}

.result-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.result-item:hover {
  background: rgba(0, 240, 255, 0.1);
}

.result-icon {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  font-size: 16px;
}

.substation-icon {
  background: rgba(255, 0, 128, 0.2);
  border: 1px solid rgba(255, 0, 128, 0.5);
}

.line-icon {
  background: rgba(0, 240, 255, 0.2);
  border: 1px solid rgba(0, 240, 255, 0.5);
}

.area-icon {
  background: rgba(0, 255, 128, 0.2);
  border: 1px solid rgba(0, 255, 128, 0.5);
}

.result-info {
  flex: 1;
  min-width: 0;
}

.result-name {
  color: #ffffff;
  font-size: 14px;
  font-weight: 500;
  margin-bottom: 4px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.result-meta {
  display: flex;
  gap: 8px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
}

.voltage-tag {
  padding: 2px 6px;
  background: rgba(0, 240, 255, 0.2);
  border-radius: 4px;
  color: #00f0ff;
}

.status-tag {
  padding: 2px 6px;
  border-radius: 4px;
}

.status-active {
  background: rgba(0, 255, 128, 0.2);
  color: #00ff80;
}

.status-error {
  background: rgba(255, 0, 128, 0.2);
  color: #ff0080;
}

.status-warning {
  background: rgba(255, 255, 0, 0.2);
  color: #ffff00;
}

.no-results {
  text-align: center;
  padding: 20px;
  color: rgba(255, 255, 255, 0.5);
}

.result-list::-webkit-scrollbar {
  width: 6px;
}

.result-list::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.2);
}

.result-list::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}

.result-list::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 240, 255, 0.5);
}
</style>
