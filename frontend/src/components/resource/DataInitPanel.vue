<template>
  <div class="panel">
    <el-card header="数据初始化">
      <el-alert type="info" :closable="false" style="margin-bottom: 20px;">
        点击下方按钮可生成模拟测试数据，用于演示和测试系统功能。
      </el-alert>
      <el-row :gutter="20">
        <el-col :span="6">
          <el-button type="primary" size="large" @click="generateAll" :loading="loading" style="width: 100%; margin-bottom: 15px;">
            一键生成所有数据
          </el-button>
        </el-col>
      </el-row>
      <el-divider />
      <el-row :gutter="15">
        <el-col :span="4" v-for="(btn, index) in buttons" :key="index">
          <el-button @click="btn.action" :loading="loading" style="width: 100%; margin-bottom: 10px;">{{ btn.label }}</el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card header="生成结果" v-if="result" style="margin-top: 20px;">
      <pre>{{ JSON.stringify(result, null, 2) }}</pre>
    </el-card>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { dataInitApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const result = ref<any>(null)
const generateAll = async () => {
  loading.value = true
  try { result.value = (await dataInitApi.generateAll()).data; ElMessage.success('数据生成完成') }
  catch (e) { ElMessage.error('生成失败') } finally { loading.value = false }
}
const buttons = [
  { label: '检修计划', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateMaintenancePlans()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '工作票', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateWorkTickets()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '操作票', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateOperationTickets()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '巡检任务', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateInspectionTasks()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '缺陷数据', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateDefects()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '操作日志', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateOperationLogs()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '调度指令', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateDispatchOrders()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '停电数据', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generatePowerOutages()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '线损数据', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateLineLosses()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '负荷预测', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateLoadForecasts()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '潮流计算', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generatePowerFlows()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: 'N-1校验', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateN1Checks()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '设备健康', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateDeviceHealths()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '可靠性指标', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateReliabilityIndices()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '文档数据', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateDocuments()).data; ElMessage.success('完成') } finally { loading.value = false } } },
  { label: '视频监控', action: async () => { loading.value = true; try { result.value = (await dataInitApi.generateVideoMonitors()).data; ElMessage.success('完成') } finally { loading.value = false } } },
]
</script>
<style scoped>.panel { padding: 10px; } pre { background: #f5f5f5; padding: 10px; border-radius: 4px; overflow: auto; max-height: 400px; }</style>
