<template>
  <div class="panel">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">新增工作票</el-button>
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="ticketCode" label="票号" width="120" />
      <el-table-column prop="ticketType" label="类型" width="130" />
      <el-table-column prop="title" label="标题" min-width="150" />
      <el-table-column prop="deviceName" label="设备" width="120" />
      <el-table-column prop="workLeader" label="负责人" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="handleView(row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { workTicketApi } from '@/api/operation'
import { ElMessage, ElMessageBox } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await workTicketApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const getStatusType = (status: string) => {
  const map: Record<string, string> = { '待审批': 'warning', '已签发': 'success', '许可中': 'primary', '已终结': 'info' }
  return map[status] || 'info'
}
const handleAdd = () => ElMessage.info('新增工作票')
const handleView = (row: any) => ElMessageBox.alert(JSON.stringify(row, null, 2), '详情')
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; display: flex; }</style>
