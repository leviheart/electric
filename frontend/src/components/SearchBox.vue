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
      >
        搜索
      </el-button>
    </div>
    
    <div class="filter-options" v-if="showFilters">
      <el-select
        v-model="selectedVoltageLevel"
        placeholder="电压等级"
        clearable
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
/**
 * SearchBox.vue - 全局搜索组件
 * 
 * 功能说明：
 * 提供全局搜索功能，支持：
 * 1. 关键字搜索：模糊匹配变电站、线路、台区名称
 * 2. 条件筛选：按电压等级、运行状态过滤
 * 3. 结果展示：分类显示搜索结果
 * 4. 快速定位：点击结果跳转到地图对应位置
 * 
 * 组件结构：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ [搜索输入框] [搜索按钮]                                          │
 * │ [电压等级筛选] [运行状态筛选]                                     │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ 找到 X 个结果                                           [关闭]  │
 * │ [变电站] [线路] [台区]                                           │
 * │ ┌─────────────────────────────────────────────────────────────┐ │
 * │ │ ⚡ 变电站名称                                                 │ │
 * │ │    220kV  运行中                                             │ │
 * │ └─────────────────────────────────────────────────────────────┘ │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 事件：
 * - select: 用户选择搜索结果时触发，参数为 { type, data }
 * - clear: 用户清空搜索时触发
 * 
 * 文件关联：
 * - api/search.ts: 搜索 API 服务
 * - types/index.ts: 类型定义
 * - views/HomeView.vue: 父组件
 */

import { ref, computed, onUnmounted } from 'vue'
import { Search, Close } from '@element-plus/icons-vue'
import { globalSearch, type SearchResult as SearchResultType, type SearchParams } from '../api/search'
import type { Substation, TransmissionLine, Area } from '../types'

// ==================== Props 定义 ====================

const props = withDefaults(defineProps<{
  /** 是否显示筛选选项 */
  showFilters?: boolean
  /** 搜索防抖延迟（毫秒） */
  debounceDelay?: number
}>(), {
  showFilters: true,
  debounceDelay: 300
})

// ==================== Emits 定义 ====================

const emit = defineEmits<{
  /** 选择搜索结果事件 */
  select: [result: { type: string; data: Substation | TransmissionLine | Area }]
  /** 清空搜索事件 */
  clear: []
}>()

// ==================== 响应式数据 ====================

/** 搜索关键字 */
const searchKeyword = ref('')

/** 选中的电压等级 */
const selectedVoltageLevel = ref('')

/** 选中的运行状态 */
const selectedStatus = ref('')

/** 加载状态 */
const loading = ref(false)

/** 是否显示搜索结果 */
const showResults = ref(false)

/** 搜索结果 */
const searchResult = ref<SearchResultType | null>(null)

/** 当前激活的标签页 */
const activeTab = ref('substations')

/** 防抖定时器 */
let debounceTimer: ReturnType<typeof setTimeout> | null = null

// ==================== 方法 ====================

/**
 * 处理搜索
 * 
 * 使用防抖机制，避免频繁请求
 */
const handleSearch = () => {
  if (debounceTimer) {
    clearTimeout(debounceTimer)
  }
  
  debounceTimer = setTimeout(() => {
    performSearch()
  }, props.debounceDelay)
}

/**
 * 执行搜索请求
 */
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

/**
 * 处理筛选条件变化
 */
const handleFilterChange = () => {
  if (searchKeyword.value.trim() || selectedVoltageLevel.value || selectedStatus.value) {
    performSearch()
  }
}

/**
 * 处理清空搜索
 */
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

/**
 * 关闭搜索结果
 */
const closeResults = () => {
  showResults.value = false
}

/**
 * 选择搜索结果
 * 
 * @param type 设备类型
 * @param data 设备数据
 */
const selectResult = (type: string, data: Substation | TransmissionLine | Area) => {
  emit('select', { type, data })
  showResults.value = false
}

/**
 * 获取状态样式类名
 * 
 * @param status 状态文本
 * @returns 样式类名
 */
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
/*
 * 搜索框容器样式
 * - 相对定位，确保搜索结果正确显示
 */
.search-box {
  position: relative;
  width: 100%;
  max-width: 600px;
}

/*
 * 搜索输入区域样式
 * - 水平排列输入框和按钮
 */
.search-input-wrapper {
  display: flex;
  gap: 10px;
}

/*
 * 输入框样式覆盖
 * - 深色主题
 */
.search-input-wrapper :deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
  box-shadow: none;
}

.search-input-wrapper :deep(.el-input__wrapper:hover) {
  border-color: rgba(0, 240, 255, 0.5);
}

.search-input-wrapper :deep(.el-input__wrapper.is-focus) {
  border-color: #00f0ff;
  box-shadow: 0 0 10px rgba(0, 240, 255, 0.3);
}

.search-input-wrapper :deep(.el-input__inner) {
  color: #ffffff;
}

.search-input-wrapper :deep(.el-input__inner::placeholder) {
  color: rgba(255, 255, 255, 0.5);
}

/*
 * 筛选选项区域样式
 */
.filter-options {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

.filter-options :deep(.el-select) {
  width: 120px;
}

.filter-options :deep(.el-select .el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
}

/*
 * 搜索结果区域样式
 * - 绝对定位，浮于其他内容之上
 */
.search-results {
  position: absolute;
  top: calc(100% + 10px);
  left: 0;
  right: 0;
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.5);
  z-index: 1000;
  max-height: 400px;
  overflow: hidden;
}

/*
 * 结果头部样式
 */
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

/*
 * 标签页样式
 */
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

/*
 * 结果列表样式
 */
.result-list {
  max-height: 280px;
  overflow-y: auto;
  padding: 8px;
}

/*
 * 单个结果项样式
 */
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

/*
 * 结果图标样式
 */
.result-icon {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  font-size: 18px;
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

/*
 * 结果信息样式
 */
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

/*
 * 标签样式
 */
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

/*
 * 无结果提示样式
 */
.no-results {
  text-align: center;
  padding: 20px;
  color: rgba(255, 255, 255, 0.5);
}

/*
 * 滚动条样式
 */
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
