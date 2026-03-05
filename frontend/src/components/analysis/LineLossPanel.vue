<template>
  <div class="panel">
    <div class="toolbar">
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="lineName" label="线路名称" width="150" />
      <el-table-column prop="supplyPower" label="供电量(kWh)" width="120" />
      <el-table-column prop="sellPower" label="售电量(kWh)" width="120" />
      <el-table-column prop="lossPower" label="损耗电量(kWh)" width="130" />
      <el-table-column prop="lossRate" label="线损率(%)" width="100">
        <template #default="{ row }">
          <el-tag :type="row.lossRate > 10 ? 'danger' : row.lossRate > 5 ? 'warning' : 'success'">{{ row.lossRate?.toFixed(2) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="analysisResult" label="分析结果" min-width="150" />
      <el-table-column prop="recordTime" label="记录时间" width="160">
        <template #default="{ row }">{{ formatTime(row.recordTime) }}</template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { lineLossApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await lineLossApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const formatTime = (time: string) => time ? new Date(time).toLocaleString() : '-'
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; }</style>
