<template>
  <div class="panel">
    <div class="toolbar">
      <el-button type="primary" @click="handleCalculate">执行计算</el-button>
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="calculationName" label="计算名称" width="150" />
      <el-table-column prop="nodeCount" label="节点数" width="80" />
      <el-table-column prop="branchCount" label="支路数" width="80" />
      <el-table-column prop="totalGeneration" label="总发电(MW)" width="110" />
      <el-table-column prop="totalLoad" label="总负荷(MW)" width="110" />
      <el-table-column prop="totalLoss" label="总损耗(MW)" width="110" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === '计算完成' ? 'success' : 'warning'">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="analysisResult" label="分析结果" min-width="150" />
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { powerFlowApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await powerFlowApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const handleCalculate = async () => {
  try { await powerFlowApi.calculate({ name: '潮流计算' }); ElMessage.success('计算完成'); loadData() } catch (e) { ElMessage.error('计算失败') }
}
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; }</style>
