<template>
  <div class="panel">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">添加监控</el-button>
      <el-button @click="loadData">刷新</el-button>
    </div>
    <el-row :gutter="20">
      <el-col :span="6" v-for="monitor in tableData" :key="monitor.id">
        <el-card class="monitor-card" :body-style="{ padding: '10px' }">
          <div class="monitor-header">
            <span class="monitor-name">{{ monitor.name }}</span>
            <el-tag :type="monitor.status === '在线' ? 'success' : 'danger'" size="small">{{ monitor.status }}</el-tag>
          </div>
          <div class="monitor-location">{{ monitor.location }}</div>
          <div class="monitor-info">
            <div>设备: {{ monitor.deviceName }}</div>
            <div>IP: {{ monitor.ip }}:{{ monitor.port }}</div>
            <div>分辨率: {{ monitor.resolution }}</div>
          </div>
          <div class="monitor-actions">
            <el-button size="small" type="primary" @click="handleView(monitor)">查看</el-button>
            <el-button size="small" @click="handlePlayback(monitor)">回放</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { videoMonitorApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await videoMonitorApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const handleAdd = () => ElMessage.info('添加监控')
const handleView = (row: any) => ElMessage.info('查看监控: ' + row.streamUrl)
const handlePlayback = (row: any) => ElMessage.info('回放监控')
onMounted(() => loadData())
</script>
<style scoped>
.panel { padding: 10px; }
.toolbar { margin-bottom: 15px; display: flex; }
.monitor-card { margin-bottom: 15px; }
.monitor-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px; }
.monitor-name { font-weight: bold; }
.monitor-location { color: #666; font-size: 12px; margin-bottom: 8px; }
.monitor-info { font-size: 12px; color: #888; margin-bottom: 8px; }
.monitor-info div { margin-bottom: 2px; }
.monitor-actions { display: flex; gap: 5px; }
</style>
