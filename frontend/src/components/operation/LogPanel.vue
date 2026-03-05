<template>
  <div class="panel">
    <div class="toolbar">
      <el-button @click="loadData">刷新</el-button>
      <el-date-picker v-model="dateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" @change="loadData" style="margin-left: 10px;" />
    </div>
    <el-table :data="tableData" stripe border v-loading="loading" max-height="500">
      <el-table-column prop="username" label="用户" width="100" />
      <el-table-column prop="module" label="模块" width="100" />
      <el-table-column prop="operation" label="操作" width="100" />
      <el-table-column prop="method" label="方法" width="80" />
      <el-table-column prop="targetType" label="目标类型" width="100" />
      <el-table-column prop="targetName" label="目标名称" width="150" />
      <el-table-column prop="ip" label="IP" width="130" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">{{ row.status === 1 ? '成功' : '失败' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="duration" label="耗时(ms)" width="90" />
      <el-table-column prop="operationTime" label="操作时间" width="160">
        <template #default="{ row }">{{ formatTime(row.operationTime) }}</template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { operationLogApi } from '@/api/operation'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const tableData = ref<any[]>([])
const dateRange = ref<[Date, Date] | null>(null)

const loadData = async () => {
  loading.value = true
  try { tableData.value = (await operationLogApi.getRecent(7)).data }
  catch (e) { ElMessage.error('加载失败') }
  finally { loading.value = false }
}

const formatTime = (time: string) => time ? new Date(time).toLocaleString() : '-'

onMounted(() => loadData())
</script>

<style scoped>
.panel { padding: 10px; }
.toolbar { margin-bottom: 15px; display: flex; align-items: center; }
</style>
