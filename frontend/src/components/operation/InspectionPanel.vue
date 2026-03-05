<template>
  <div class="panel">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">新增巡检任务</el-button>
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="taskCode" label="任务编号" width="120" />
      <el-table-column prop="title" label="标题" min-width="150" />
      <el-table-column prop="inspectionType" label="类型" width="100" />
      <el-table-column prop="deviceName" label="设备" width="120" />
      <el-table-column prop="inspector" label="巡检员" width="80" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === '已完成' ? 'success' : 'warning'">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="100" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="handleView(row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { inspectionTaskApi } from '@/api/operation'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await inspectionTaskApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const handleAdd = () => ElMessage.info('新增巡检任务')
const handleView = (row: any) => ElMessageBox.alert(JSON.stringify(row, null, 2), '详情')
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; display: flex; }</style>
