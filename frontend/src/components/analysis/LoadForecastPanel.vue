<template>
  <div class="panel">
    <div class="toolbar">
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="targetName" label="目标名称" width="150" />
      <el-table-column prop="forecastType" label="预测类型" width="100" />
      <el-table-column prop="forecastValue" label="预测值(MW)" width="120" />
      <el-table-column prop="actualValue" label="实际值(MW)" width="120" />
      <el-table-column prop="errorRate" label="误差率(%)" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.errorRate" :type="row.errorRate > 10 ? 'danger' : 'success'">{{ row.errorRate?.toFixed(2) }}</el-tag>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column prop="algorithm" label="算法" width="120" />
      <el-table-column prop="forecastTime" label="预测时间" width="160">
        <template #default="{ row }">{{ formatTime(row.forecastTime) }}</template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { loadForecastApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await loadForecastApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const formatTime = (time: string) => time ? new Date(time).toLocaleString() : '-'
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; }</style>
