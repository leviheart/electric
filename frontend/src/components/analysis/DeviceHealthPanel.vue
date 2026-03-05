<template>
  <div class="panel">
    <div class="toolbar">
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="deviceName" label="设备名称" width="150" />
      <el-table-column prop="deviceType" label="设备类型" width="100" />
      <el-table-column prop="healthScore" label="健康得分" width="100">
        <template #default="{ row }">
          <el-tag :type="row.healthScore >= 90 ? 'success' : row.healthScore >= 60 ? 'warning' : 'danger'">{{ row.healthScore?.toFixed(1) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="healthLevel" label="健康等级" width="100">
        <template #default="{ row }">
          <el-tag :type="{ '优秀': 'success', '良好': 'primary', '一般': 'warning', '较差': 'danger' }[row.healthLevel] || 'info'">{{ row.healthLevel }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="riskLevel" label="风险等级" width="100">
        <template #default="{ row }">
          <el-tag :type="{ '高': 'danger', '中': 'warning', '低': 'success' }[row.riskLevel] || 'info'">{{ row.riskLevel }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="runYears" label="运行年限" width="90" />
      <el-table-column prop="suggestion" label="建议" min-width="200" />
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { deviceHealthApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await deviceHealthApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; }</style>
