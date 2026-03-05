<template>
  <div class="panel">
    <div class="toolbar">
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="targetName" label="目标名称" width="150" />
      <el-table-column prop="timeGranularity" label="时间粒度" width="100" />
      <el-table-column prop="saidi" label="SAIDI" width="100">
        <template #default="{ row }">{{ row.saidi?.toFixed(4) }}</template>
      </el-table-column>
      <el-table-column prop="saifi" label="SAIFI" width="100">
        <template #default="{ row }">{{ row.saifi?.toFixed(4) }}</template>
      </el-table-column>
      <el-table-column prop="asai" label="ASAI(%)" width="100">
        <template #default="{ row }">
          <el-tag :type="row.asai >= 99.9 ? 'success' : 'warning'">{{ row.asai?.toFixed(4) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="totalOutageCount" label="停电次数" width="90" />
      <el-table-column prop="totalOutageDuration" label="停电时长(分钟)" width="120" />
      <el-table-column prop="mainOutageReason" label="主要停电原因" width="120" />
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { reliabilityIndexApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await reliabilityIndexApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; }</style>
