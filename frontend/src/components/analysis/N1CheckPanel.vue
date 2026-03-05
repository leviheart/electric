<template>
  <div class="panel">
    <div class="toolbar">
      <el-button type="primary" @click="handleCheck">执行校验</el-button>
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="checkName" label="校验名称" width="150" />
      <el-table-column prop="totalItems" label="总项数" width="80" />
      <el-table-column prop="passedItems" label="通过项" width="80" />
      <el-table-column prop="failedItems" label="失败项" width="80" />
      <el-table-column prop="riskLevel" label="风险等级" width="100">
        <template #default="{ row }">
          <el-tag :type="{ '高风险': 'danger', '中风险': 'warning', '低风险': 'success' }[row.riskLevel] || 'info'">{{ row.riskLevel }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === '已完成' ? 'success' : 'warning'">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="riskAssessment" label="风险评估" min-width="200" />
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { n1CheckApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await n1CheckApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const handleCheck = async () => {
  try { await n1CheckApi.perform({ name: 'N-1校验' }); ElMessage.success('校验完成'); loadData() } catch (e) { ElMessage.error('校验失败') }
}
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; }</style>
