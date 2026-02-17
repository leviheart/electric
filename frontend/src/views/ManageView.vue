<template>
  <div class="manage-view">
    <div class="manage-header">
      <h1>数据管理</h1>
      <div class="header-actions">
        <el-button type="primary" @click="showAddDialog">
          <el-icon><Plus /></el-icon>
          新增{{ currentTypeName }}
        </el-button>
      </div>
    </div>
    
    <div class="tabs-section">
      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <el-tab-pane label="变电站管理" name="substations">
          <div class="table-section">
            <el-table :data="substations" style="width: 100%" v-loading="loading">
              <el-table-column prop="id" label="ID" width="80" />
              <el-table-column prop="name" label="名称" min-width="150" />
              <el-table-column prop="voltageLevel" label="电压等级" width="100" />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="{ row }">
                  <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="latitude" label="纬度" width="120" />
              <el-table-column prop="longitude" label="经度" width="120" />
              <el-table-column label="操作" width="180" fixed="right">
                <template #default="{ row }">
                  <el-button size="small" @click="editItem('substation', row)">编辑</el-button>
                  <el-button size="small" type="danger" @click="deleteItem('substation', row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        
        <el-tab-pane label="线路管理" name="lines">
          <div class="table-section">
            <el-table :data="lines" style="width: 100%" v-loading="loading">
              <el-table-column prop="id" label="ID" width="80" />
              <el-table-column prop="name" label="名称" min-width="150" />
              <el-table-column prop="startSubstation" label="起点" width="120" />
              <el-table-column prop="endSubstation" label="终点" width="120" />
              <el-table-column prop="voltageLevel" label="电压等级" width="100" />
              <el-table-column prop="length" label="长度(km)" width="100" />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="{ row }">
                  <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="180" fixed="right">
                <template #default="{ row }">
                  <el-button size="small" @click="editItem('line', row)">编辑</el-button>
                  <el-button size="small" type="danger" @click="deleteItem('line', row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
        
        <el-tab-pane label="台区管理" name="areas">
          <div class="table-section">
            <el-table :data="areas" style="width: 100%" v-loading="loading">
              <el-table-column prop="id" label="ID" width="80" />
              <el-table-column prop="name" label="名称" min-width="150" />
              <el-table-column prop="substationName" label="所属变电站" width="150" />
              <el-table-column prop="customerCount" label="用户数" width="100" />
              <el-table-column prop="status" label="状态" width="100">
                <template #default="{ row }">
                  <el-tag :type="getStatusType(row.status)">{{ row.status }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="180" fixed="right">
                <template #default="{ row }">
                  <el-button size="small" @click="editItem('area', row)">编辑</el-button>
                  <el-button size="small" type="danger" @click="deleteItem('area', row.id)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    
    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle"
      width="500px"
      @close="resetForm"
    >
      <el-form :model="formData" label-width="100px" ref="formRef">
        <el-form-item label="名称" prop="name" required>
          <el-input v-model="formData.name" placeholder="请输入名称" />
        </el-form-item>
        
        <template v-if="dialogType === 'substation'">
          <el-form-item label="电压等级">
            <el-select v-model="formData.voltageLevel" placeholder="请选择电压等级">
              <el-option label="220kV" value="220kV" />
              <el-option label="110kV" value="110kV" />
              <el-option label="35kV" value="35kV" />
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="formData.status" placeholder="请选择状态">
              <el-option label="运行中" value="运行中" />
              <el-option label="停运" value="停运" />
              <el-option label="检修" value="检修" />
            </el-select>
          </el-form-item>
          <el-form-item label="纬度">
            <el-input-number v-model="formData.latitude" :precision="6" :step="0.0001" />
          </el-form-item>
          <el-form-item label="经度">
            <el-input-number v-model="formData.longitude" :precision="6" :step="0.0001" />
          </el-form-item>
        </template>
        
        <template v-if="dialogType === 'line'">
          <el-form-item label="起始变电站">
            <el-select v-model="formData.startSubstation" placeholder="请选择起始变电站">
              <el-option 
                v-for="sub in substations" 
                :key="sub.id" 
                :label="sub.name" 
                :value="sub.name" 
              />
            </el-select>
          </el-form-item>
          <el-form-item label="终止变电站">
            <el-select v-model="formData.endSubstation" placeholder="请选择终止变电站">
              <el-option 
                v-for="sub in substations" 
                :key="sub.id" 
                :label="sub.name" 
                :value="sub.name" 
              />
            </el-select>
          </el-form-item>
          <el-form-item label="电压等级">
            <el-select v-model="formData.voltageLevel" placeholder="请选择电压等级">
              <el-option label="220kV" value="220kV" />
              <el-option label="110kV" value="110kV" />
              <el-option label="35kV" value="35kV" />
            </el-select>
          </el-form-item>
          <el-form-item label="长度(km)">
            <el-input-number v-model="formData.length" :precision="2" :step="1" />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="formData.status" placeholder="请选择状态">
              <el-option label="运行中" value="运行中" />
              <el-option label="停运" value="停运" />
              <el-option label="检修" value="检修" />
            </el-select>
          </el-form-item>
        </template>
        
        <template v-if="dialogType === 'area'">
          <el-form-item label="所属变电站">
            <el-select v-model="formData.substationName" placeholder="请选择变电站">
              <el-option 
                v-for="sub in substations" 
                :key="sub.id" 
                :label="sub.name" 
                :value="sub.name" 
              />
            </el-select>
          </el-form-item>
          <el-form-item label="用户数">
            <el-input-number v-model="formData.customerCount" :min="0" />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="formData.status" placeholder="请选择状态">
              <el-option label="正常" value="正常" />
              <el-option label="异常" value="异常" />
              <el-option label="检修" value="检修" />
            </el-select>
          </el-form-item>
        </template>
      </el-form>
      
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveItem">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
/**
 * ManageView.vue - 数据管理页面
 * 
 * 功能说明：
 * 提供变电站、线路、台区的数据管理功能，包括：
 * 1. 数据列表展示
 * 2. 新增数据
 * 3. 编辑数据
 * 4. 删除数据
 * 
 * 页面结构：
 * ┌─────────────────────────────────────────────────────────────────┐
 * │ 数据管理                                    [新增变电站]         │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ [变电站管理] [线路管理] [台区管理]                               │
 * ├─────────────────────────────────────────────────────────────────┤
 * │ ID │ 名称 │ 电压等级 │ 状态 │ 纬度 │ 经度 │ 操作               │
 * │ 1  │ 朝阳 │ 220kV   │ 运行 │ 39.9 │ 116.4│ [编辑] [删除]      │
 * │ 2  │ 海淀 │ 110kV   │ 运行 │ 39.9 │ 116.3│ [编辑] [删除]      │
 * └─────────────────────────────────────────────────────────────────┘
 * 
 * 文件关联：
 * - api/substation.ts: 变电站 API
 * - api/transmissionLine.ts: 线路 API
 * - api/area.ts: 台区 API
 */

import { ref, computed, onMounted } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAllSubstations, createSubstation, updateSubstation, deleteSubstation } from '../api/substation'
import { getAllTransmissionLines, createTransmissionLine, updateTransmissionLine, deleteTransmissionLine } from '../api/transmissionLine'
import { getAllAreas, createArea, updateArea, deleteArea } from '../api/area'
import type { Substation, TransmissionLine, Area } from '../types'

const loading = ref(false)
const activeTab = ref('substations')
const substations = ref<Substation[]>([])
const lines = ref<TransmissionLine[]>([])
const areas = ref<Area[]>([])

const dialogVisible = ref(false)
const dialogType = ref('')
const isEdit = ref(false)
const formData = ref<any>({})
const formRef = ref()

const currentTypeName = computed(() => {
  switch (activeTab.value) {
    case 'substations': return '变电站'
    case 'lines': return '线路'
    case 'areas': return '台区'
    default: return ''
  }
})

const dialogTitle = computed(() => {
  return (isEdit.value ? '编辑' : '新增') + currentTypeName.value
})

const getStatusType = (status: string): string => {
  switch (status) {
    case '运行中':
    case '正常':
      return 'success'
    case '停运':
    case '异常':
      return 'danger'
    case '检修':
      return 'warning'
    default:
      return 'info'
  }
}

const fetchData = async () => {
  loading.value = true
  try {
    const [subRes, lineRes, areaRes] = await Promise.all([
      getAllSubstations(),
      getAllTransmissionLines(),
      getAllAreas()
    ])
    substations.value = subRes.data || []
    lines.value = lineRes.data || []
    areas.value = areaRes.data || []
  } catch (error) {
    console.error('获取数据失败:', error)
  } finally {
    loading.value = false
  }
}

const handleTabChange = () => {
  // Tab切换时的处理
}

const showAddDialog = () => {
  isEdit.value = false
  dialogType.value = activeTab.value.slice(0, -1)
  formData.value = { status: '运行中' }
  dialogVisible.value = true
}

const editItem = (type: string, item: any) => {
  isEdit.value = true
  dialogType.value = type
  formData.value = { ...item }
  dialogVisible.value = true
}

const deleteItem = async (type: string, id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除此记录吗？', '确认删除', {
      type: 'warning'
    })
    
    switch (type) {
      case 'substation':
        await deleteSubstation(id)
        break
      case 'line':
        await deleteTransmissionLine(id)
        break
      case 'area':
        await deleteArea(id)
        break
    }
    
    ElMessage.success('删除成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const saveItem = async () => {
  try {
    switch (dialogType.value) {
      case 'substation':
        if (isEdit.value) {
          await updateSubstation(formData.value.id, formData.value)
        } else {
          await createSubstation(formData.value)
        }
        break
      case 'line':
        if (isEdit.value) {
          await updateTransmissionLine(formData.value.id, formData.value)
        } else {
          await createTransmissionLine(formData.value)
        }
        break
      case 'area':
        if (isEdit.value) {
          await updateArea(formData.value.id, formData.value)
        } else {
          await createArea(formData.value)
        }
        break
    }
    
    ElMessage.success('保存成功')
    dialogVisible.value = false
    fetchData()
  } catch (error) {
    ElMessage.error('保存失败')
  }
}

const resetForm = () => {
  formData.value = {}
  formRef.value?.resetFields()
}

onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.manage-view {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

.manage-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.manage-header h1 {
  color: #00f0ff;
  font-size: 24px;
  margin: 0;
}

.tabs-section {
  background: rgba(10, 10, 26, 0.8);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 20px;
}

.table-section {
  margin-top: 16px;
}

:deep(.el-table) {
  background: transparent;
  color: #ffffff;
}

:deep(.el-table th) {
  background: rgba(0, 0, 0, 0.3);
  color: #00f0ff;
}

:deep(.el-table td) {
  background: transparent;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

:deep(.el-table--enable-row-hover .el-table__body tr:hover > td) {
  background: rgba(0, 240, 255, 0.1);
}

:deep(.el-tabs__item) {
  color: rgba(255, 255, 255, 0.6);
}

:deep(.el-tabs__item.is-active) {
  color: #00f0ff;
}

:deep(.el-tabs__active-bar) {
  background: #00f0ff;
}

:deep(.el-dialog) {
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
}

:deep(.el-dialog__title) {
  color: #00f0ff;
}

:deep(.el-form-item__label) {
  color: rgba(255, 255, 255, 0.8);
}
</style>
