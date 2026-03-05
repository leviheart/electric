<template>
  <div class="panel">
    <div class="toolbar">
      <el-button type="primary" @click="handleAdd">上传文档</el-button>
      <el-button @click="loadData">刷新</el-button>
      <el-input v-model="searchKeyword" placeholder="搜索文档" style="width: 200px; margin-left: 10px;" @keyup.enter="handleSearch" />
    </div>
    <el-table :data="tableData" stripe border v-loading="loading">
      <el-table-column prop="docCode" label="文档编号" width="120" />
      <el-table-column prop="title" label="标题" min-width="150" />
      <el-table-column prop="docType" label="类型" width="100" />
      <el-table-column prop="category" label="分类" width="100" />
      <el-table-column prop="author" label="作者" width="80" />
      <el-table-column prop="version" label="版本" width="80" />
      <el-table-column prop="downloadCount" label="下载次数" width="100" />
      <el-table-column prop="uploadTime" label="上传时间" width="160">
        <template #default="{ row }">{{ formatTime(row.uploadTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" width="100" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="handleDownload(row)">下载</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { documentApi } from '@/api/operation'
import { ElMessage } from 'element-plus'
const loading = ref(false)
const tableData = ref<any[]>([])
const searchKeyword = ref('')
const loadData = async () => {
  loading.value = true
  try { tableData.value = (await documentApi.getAll()).data } catch (e) { ElMessage.error('加载失败') } finally { loading.value = false }
}
const formatTime = (time: string) => time ? new Date(time).toLocaleString() : '-'
const handleAdd = () => ElMessage.info('上传文档功能')
const handleSearch = async () => {
  if (!searchKeyword.value) { loadData(); return }
  try { tableData.value = (await documentApi.search(searchKeyword.value)).data } catch (e) { ElMessage.error('搜索失败') }
}
const handleDownload = (row: any) => ElMessage.info('下载: ' + row.fileName)
onMounted(() => loadData())
</script>
<style scoped>.panel { padding: 10px; } .toolbar { margin-bottom: 15px; display: flex; align-items: center; }</style>
