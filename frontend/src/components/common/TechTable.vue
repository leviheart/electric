<template>
  <div class="tech-table-container">
    <div class="table-toolbar" v-if="$slots.toolbar || title">
      <div class="toolbar-left">
        <span class="table-title" v-if="title">{{ title }}</span>
      </div>
      <div class="toolbar-right">
        <slot name="toolbar"></slot>
      </div>
    </div>
    
    <div class="table-wrapper">
      <table class="tech-table">
        <thead>
          <tr>
            <th v-for="col in columns" :key="col.prop" :style="{ width: col.width }">
              {{ col.label }}
            </th>
            <th v-if="$slots.actions" class="actions-column">操作</th>
          </tr>
        </thead>
        <tbody v-if="!loading && data.length > 0">
          <tr v-for="(row, index) in data" :key="index" @click="$emit('row-click', row)">
            <td v-for="col in columns" :key="col.prop">
              <slot :name="col.prop" :row="row" :value="row[col.prop]">
                <template v-if="col.type === 'status'">
                  <span class="status-tag" :class="getStatusClass(row[col.prop], col.statusMap)">
                    {{ row[col.prop] }}
                  </span>
                </template>
                <template v-else-if="col.type === 'time'">
                  {{ formatTime(row[col.prop]) }}
                </template>
                <template v-else-if="col.type === 'number'">
                  <span class="number-value">{{ formatNumber(row[col.prop], col.decimals) }}</span>
                </template>
                <template v-else>
                  {{ row[col.prop] }}
                </template>
              </slot>
            </td>
            <td v-if="$slots.actions" class="actions-cell">
              <slot name="actions" :row="row"></slot>
            </td>
          </tr>
        </tbody>
        <tbody v-else-if="loading">
          <tr>
            <td :colspan="columns.length + ($slots.actions ? 1 : 0)" class="loading-cell">
              <div class="loading-spinner">
                <div class="spinner"></div>
                <span>加载中...</span>
              </div>
            </td>
          </tr>
        </tbody>
        <tbody v-else>
          <tr>
            <td :colspan="columns.length + ($slots.actions ? 1 : 0)" class="empty-cell">
              <div class="empty-content">
                <el-icon :size="48"><Document /></el-icon>
                <span>暂无数据</span>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div class="table-footer" v-if="showPagination">
      <span class="total-info">共 {{ total }} 条</span>
      <div class="pagination">
        <button class="page-btn" :disabled="currentPage <= 1" @click="$emit('page-change', currentPage - 1)">
          <el-icon><ArrowLeft /></el-icon>
        </button>
        <span class="page-info">{{ currentPage }} / {{ totalPages }}</span>
        <button class="page-btn" :disabled="currentPage >= totalPages" @click="$emit('page-change', currentPage + 1)">
          <el-icon><ArrowRight /></el-icon>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { Document, ArrowLeft, ArrowRight } from '@element-plus/icons-vue'

interface Column {
  prop: string
  label: string
  width?: string
  type?: 'text' | 'status' | 'time' | 'number'
  statusMap?: Record<string, string>
  decimals?: number
}

const props = defineProps<{
  columns: Column[]
  data: any[]
  loading?: boolean
  title?: string
  showPagination?: boolean
  total?: number
  currentPage?: number
  pageSize?: number
}>()

defineEmits<{
  'row-click': [row: any]
  'page-change': [page: number]
}>()

const totalPages = computed(() => {
  if (!props.total || !props.pageSize) return 1
  return Math.ceil(props.total / props.pageSize)
})

const getStatusClass = (value: string, statusMap?: Record<string, string>) => {
  if (!statusMap) return ''
  return statusMap[value] || ''
}

const formatTime = (time: string) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

const formatNumber = (value: number, decimals = 2) => {
  if (value == null) return '-'
  return value.toFixed(decimals)
}
</script>

<style scoped>
.tech-table-container {
  background: rgba(10, 10, 26, 0.6);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  overflow: hidden;
  backdrop-filter: blur(10px);
}

.table-toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background: linear-gradient(90deg, rgba(0, 240, 255, 0.08) 0%, transparent 100%);
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
}

.table-title {
  font-size: 16px;
  font-weight: 600;
  color: #00f0ff;
  letter-spacing: 1px;
}

.toolbar-right {
  display: flex;
  gap: 10px;
}

.table-wrapper {
  overflow-x: auto;
}

.tech-table {
  width: 100%;
  border-collapse: collapse;
}

.tech-table th {
  padding: 14px 16px;
  text-align: left;
  font-size: 13px;
  font-weight: 500;
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.05);
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  white-space: nowrap;
  letter-spacing: 0.5px;
}

.tech-table td {
  padding: 14px 16px;
  font-size: 13px;
  color: #c8d0e0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
  transition: background 0.2s ease;
}

.tech-table tbody tr {
  transition: all 0.2s ease;
}

.tech-table tbody tr:hover {
  background: rgba(0, 240, 255, 0.08);
}

.tech-table tbody tr:hover td {
  color: #fff;
}

.actions-column {
  width: 150px;
  text-align: center !important;
}

.actions-cell {
  text-align: center;
}

.status-tag {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.success {
  background: rgba(0, 255, 128, 0.15);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.3);
}

.status-tag.warning {
  background: rgba(255, 200, 0, 0.15);
  color: #ffc800;
  border: 1px solid rgba(255, 200, 0, 0.3);
}

.status-tag.danger {
  background: rgba(255, 64, 129, 0.15);
  color: #ff4081;
  border: 1px solid rgba(255, 64, 129, 0.3);
}

.status-tag.info {
  background: rgba(0, 240, 255, 0.15);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.3);
}

.status-tag.primary {
  background: rgba(100, 255, 218, 0.15);
  color: #64ffda;
  border: 1px solid rgba(100, 255, 218, 0.3);
}

.number-value {
  font-family: 'Consolas', 'Monaco', monospace;
  color: #64ffda;
}

.loading-cell, .empty-cell {
  padding: 60px 20px !important;
  text-align: center;
}

.loading-spinner {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
  color: #00f0ff;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid rgba(0, 240, 255, 0.2);
  border-top-color: #00f0ff;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.empty-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  color: #4a5568;
}

.table-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 14px 20px;
  background: rgba(0, 240, 255, 0.03);
  border-top: 1px solid rgba(0, 240, 255, 0.15);
}

.total-info {
  font-size: 13px;
  color: #6a7a8a;
}

.pagination {
  display: flex;
  align-items: center;
  gap: 12px;
}

.page-btn {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 240, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 6px;
  color: #00f0ff;
  cursor: pointer;
  transition: all 0.2s ease;
}

.page-btn:hover:not(:disabled) {
  background: rgba(0, 240, 255, 0.2);
  border-color: rgba(0, 240, 255, 0.5);
}

.page-btn:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.page-info {
  font-size: 13px;
  color: #c8d0e0;
}
</style>
