<template>
  <div class="maintenance-panel">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">新增计划</el-button>
      <el-button @click="loadData">刷新</el-button>
      <el-select v-model="filterStatus" placeholder="状态筛选" clearable @change="loadData" style="width: 120px; margin-left: 10px;">
        <el-option label="待审批" value="待审批" />
        <el-option label="已批准" value="已批准" />
        <el-option label="执行中" value="执行中" />
        <el-option label="已完成" value="已完成" />
      </el-select>
    </div>
    <el-table :data="tableData" stripe border style="width: 100%" v-loading="loading">
      <el-table-column prop="planCode" label="计划编号" width="120" />
      <el-table-column prop="title" label="标题" min-width="150" />
      <el-table-column prop="deviceName" label="设备名称" width="120" />
      <el-table-column prop="maintenanceType" label="检修类型" width="100" />
      <el-table-column prop="priority" label="优先级" width="80">
        <template #default="{ row }">
          <el-tag :type="row.priority === '高' ? 'danger' : row.priority === '中' ? 'warning' : 'info'">{{ row.priority }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="planStartTime" label="计划开始时间" width="160">
        <template #default="{ row }">{{ formatTime(row.planStartTime) }}</template>
      </el-table-column>
      <el-table-column prop="applicant" label="申请人" width="80" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="handleView(row)">查看</el-button>
          <el-button size="small" type="primary" v-if="row.status === '待审批'" @click="handleApprove(row)">审批</el-button>
          <el-button size="small" type="success" v-if="row.status === '已批准'" @click="handleStart(row)">开始</el-button>
          <el-button size="small" type="warning" v-if="row.status === '执行中'" @click="handleComplete(row)">完成</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="total"
      layout="total, prev, pager, next"
      @current-change="loadData"
      style="margin-top: 15px; justify-content: flex-end;"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { maintenancePlanApi } from '@/api/operation'
import { ElMessage, ElMessageBox } from 'element-plus'

const loading = ref(false)
const tableData = ref<any[]>([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const filterStatus = ref('')

const loadData = async () => {
  loading.value = true
  try {
    const res = filterStatus.value
      ? await maintenancePlanApi.getByStatus(filterStatus.value)
      : await maintenancePlanApi.getAll()
    tableData.value = res.data
    total.value = res.data.length
  } catch (e) {
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

const getStatusType = (status: string) => {
  const map: Record<string, string> = {
    '待审批': 'warning',
    '已批准': 'primary',
    '执行中': 'success',
    '已完成': 'info',
    '已拒绝': 'danger'
  }
  return map[status] || 'info'
}

const formatTime = (time: string) => {
  if (!time) return '-'
  return new Date(time).toLocaleString()
}

const handleAdd = () => {
  ElMessage.info('新增计划功能')
}

const handleView = (row: any) => {
  ElMessageBox.alert(JSON.stringify(row, null, 2), '计划详情')
}

const handleApprove = async (row: any) => {
  try {
    await maintenancePlanApi.approve(row.id, 'admin')
    ElMessage.success('审批成功')
    loadData()
  } catch (e) {
    ElMessage.error('审批失败')
  }
}

const handleStart = async (row: any) => {
  try {
    await maintenancePlanApi.start(row.id)
    ElMessage.success('已开始执行')
    loadData()
  } catch (e) {
    ElMessage.error('操作失败')
  }
}

const handleComplete = async (row: any) => {
  try {
    await maintenancePlanApi.complete(row.id, '检修完成')
    ElMessage.success('已完成')
    loadData()
  } catch (e) {
    ElMessage.error('操作失败')
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.maintenance-panel {
  padding: 10px;
}
.toolbar {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}
</style>
