<template>
  <TechPage title="数据管理">
    <TechTabs :tabs="tabs" v-model="activeTab">
      <template #extra>
        <TechButton type="primary" icon="Plus" @click="showAddDialog">新增{{ currentTypeName }}</TechButton>
        <TechButton icon="Refresh" @click="fetchData">刷新</TechButton>
      </template>
      
      <div class="table-container">
        <TechTable
          :columns="currentColumns"
          :data="currentData"
          :loading="loading"
          :title="currentTypeName + '列表'"
          :show-pagination="true"
          :total="total"
          :current-page="currentPage"
          :page-size="pageSize"
          @page-change="handlePageChange"
        >
          <template #status="{ row }">
            <span class="status-tag" :class="getStatusClass(row.status)">{{ row.status }}</span>
          </template>
          
          <template #voltageLevel="{ row }">
            <span class="voltage-tag">{{ row.voltageLevel }}</span>
          </template>
          
          <template #capacity="{ row }">
            <span class="number-value">{{ formatNumber(row.capacity) }}</span>
          </template>
          
          <template #length="{ row }">
            <span class="number-value">{{ formatNumber(row.length) }}</span>
          </template>
          
          <template #transformerCapacity="{ row }">
            <span class="number-value">{{ formatNumber(row.transformerCapacity) }}</span>
          </template>
          
          <template #loadRate="{ row }">
            <div class="load-bar">
              <div class="load-fill" :style="{ width: (row.loadRate || 0) + '%' }"></div>
              <span class="load-text">{{ formatNumber(row.loadRate, 0) }}%</span>
            </div>
          </template>
          
          <template #actions="{ row }">
            <div class="action-buttons">
              <button class="action-btn edit" @click="editItem(currentItemType, row)">
                <el-icon><Edit /></el-icon>
              </button>
              <button class="action-btn delete" @click="deleteItem(currentItemType, row.id)">
                <el-icon><Delete /></el-icon>
              </button>
            </div>
          </template>
        </TechTable>
      </div>
    </TechTabs>
    
    <el-dialog 
      v-model="dialogVisible" 
      :title="dialogTitle"
      width="560px"
      @close="resetForm"
      class="tech-dialog"
    >
      <el-form :model="formData" label-width="100px" ref="formRef" class="tech-form">
        <el-form-item label="名称" prop="name" required>
          <el-input v-model="formData.name" placeholder="请输入名称" />
        </el-form-item>
        
        <template v-if="dialogType === 'substation'">
          <el-form-item label="设备编号">
            <el-input v-model="formData.deviceCode" placeholder="如：BD-001" />
          </el-form-item>
          <el-form-item label="电压等级">
            <el-select v-model="formData.voltageLevel" placeholder="请选择">
              <el-option label="500kV" value="500kV" />
              <el-option label="220kV" value="220kV" />
              <el-option label="110kV" value="110kV" />
              <el-option label="35kV" value="35kV" />
            </el-select>
          </el-form-item>
          <el-form-item label="容量(MVA)">
            <el-input-number v-model="formData.capacity" :min="0" :precision="2" />
          </el-form-item>
          <el-form-item label="负载率(%)">
            <el-input-number v-model="formData.loadRate" :min="0" :max="100" />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="formData.status" placeholder="请选择">
              <el-option label="运行中" value="运行中" />
              <el-option label="停运" value="停运" />
              <el-option label="检修" value="检修" />
            </el-select>
          </el-form-item>
          <el-form-item label="负责人">
            <el-input v-model="formData.manager" placeholder="负责人姓名" />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="formData.contactPhone" placeholder="联系电话" />
          </el-form-item>
          <el-form-item label="纬度">
            <el-input-number v-model="formData.latitude" :precision="6" :step="0.0001" />
          </el-form-item>
          <el-form-item label="经度">
            <el-input-number v-model="formData.longitude" :precision="6" :step="0.0001" />
          </el-form-item>
        </template>
        
        <template v-if="dialogType === 'line'">
          <el-form-item label="设备编号">
            <el-input v-model="formData.deviceCode" placeholder="如：XL-001" />
          </el-form-item>
          <el-form-item label="起始变电站">
            <el-select v-model="formData.startSubstation" placeholder="请选择">
              <el-option v-for="sub in substations" :key="sub.id" :label="sub.name" :value="sub.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="终止变电站">
            <el-select v-model="formData.endSubstation" placeholder="请选择">
              <el-option v-for="sub in substations" :key="sub.id" :label="sub.name" :value="sub.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="电压等级">
            <el-select v-model="formData.voltageLevel" placeholder="请选择">
              <el-option label="500kV" value="500kV" />
              <el-option label="220kV" value="220kV" />
              <el-option label="110kV" value="110kV" />
              <el-option label="35kV" value="35kV" />
            </el-select>
          </el-form-item>
          <el-form-item label="长度(km)">
            <el-input-number v-model="formData.length" :precision="2" :min="0" />
          </el-form-item>
          <el-form-item label="负载率(%)">
            <el-input-number v-model="formData.loadRate" :min="0" :max="100" />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="formData.status" placeholder="请选择">
              <el-option label="运行中" value="运行中" />
              <el-option label="停运" value="停运" />
              <el-option label="检修" value="检修" />
            </el-select>
          </el-form-item>
          <el-form-item label="负责人">
            <el-input v-model="formData.manager" placeholder="负责人姓名" />
          </el-form-item>
        </template>
        
        <template v-if="dialogType === 'area'">
          <el-form-item label="设备编号">
            <el-input v-model="formData.deviceCode" placeholder="如：TQ-001" />
          </el-form-item>
          <el-form-item label="所属变电站">
            <el-select v-model="formData.substationName" placeholder="请选择">
              <el-option v-for="sub in substations" :key="sub.id" :label="sub.name" :value="sub.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="用户数">
            <el-input-number v-model="formData.customerCount" :min="0" />
          </el-form-item>
          <el-form-item label="重要用户">
            <el-input-number v-model="formData.importantCustomerCount" :min="0" />
          </el-form-item>
          <el-form-item label="变压器容量">
            <el-input-number v-model="formData.transformerCapacity" :min="0" />
          </el-form-item>
          <el-form-item label="负载率(%)">
            <el-input-number v-model="formData.loadRate" :min="0" :max="100" />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="formData.status" placeholder="请选择">
              <el-option label="正常" value="正常" />
              <el-option label="异常" value="异常" />
              <el-option label="检修" value="检修" />
            </el-select>
          </el-form-item>
          <el-form-item label="负责人">
            <el-input v-model="formData.manager" placeholder="负责人姓名" />
          </el-form-item>
        </template>
      </el-form>
      
      <template #footer>
        <div class="dialog-footer">
          <TechButton @click="dialogVisible = false">取消</TechButton>
          <TechButton type="primary" @click="saveItem">保存</TechButton>
        </div>
      </template>
    </el-dialog>
  </TechPage>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { Edit, Delete } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import TechPage from '@/components/common/TechPage.vue'
import TechTabs from '@/components/common/TechTabs.vue'
import TechTable from '@/components/common/TechTable.vue'
import TechButton from '@/components/common/TechButton.vue'
import { getAllSubstations, createSubstation, updateSubstation, deleteSubstation } from '@/api/substation'
import { getAllTransmissionLines, createTransmissionLine, updateTransmissionLine, deleteTransmissionLine } from '@/api/transmissionLine'
import { getAllAreas, createArea, updateArea, deleteArea } from '@/api/area'
import type { Substation, TransmissionLine, Area } from '@/types'

const loading = ref(false)
const activeTab = ref('substations')
const substations = ref<Substation[]>([])
const lines = ref<TransmissionLine[]>([])
const areas = ref<Area[]>([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const tabs = [
  { name: 'substations', label: '变电站管理' },
  { name: 'lines', label: '线路管理' },
  { name: 'areas', label: '台区管理' }
]

const substationColumns = [
  { prop: 'deviceCode', label: '设备编号', width: '100px' },
  { prop: 'name', label: '名称' },
  { prop: 'voltageLevel', label: '电压等级', width: '100px', type: 'slot' },
  { prop: 'capacity', label: '容量(MVA)', width: '110px', type: 'slot' },
  { prop: 'loadRate', label: '负载率', width: '140px', type: 'slot' },
  { prop: 'status', label: '状态', width: '100px', type: 'slot' },
  { prop: 'manager', label: '负责人', width: '80px' }
]

const lineColumns = [
  { prop: 'deviceCode', label: '设备编号', width: '100px' },
  { prop: 'name', label: '名称' },
  { prop: 'startSubstation', label: '起点', width: '100px' },
  { prop: 'endSubstation', label: '终点', width: '100px' },
  { prop: 'voltageLevel', label: '电压等级', width: '100px', type: 'slot' },
  { prop: 'length', label: '长度(km)', width: '100px', type: 'slot' },
  { prop: 'loadRate', label: '负载率', width: '140px', type: 'slot' },
  { prop: 'status', label: '状态', width: '100px', type: 'slot' }
]

const areaColumns = [
  { prop: 'deviceCode', label: '设备编号', width: '100px' },
  { prop: 'name', label: '名称' },
  { prop: 'substationName', label: '所属变电站', width: '120px' },
  { prop: 'customerCount', label: '用户数', width: '90px' },
  { prop: 'transformerCapacity', label: '变压器容量', width: '110px', type: 'slot' },
  { prop: 'loadRate', label: '负载率', width: '140px', type: 'slot' },
  { prop: 'status', label: '状态', width: '100px', type: 'slot' }
]

const currentColumns = computed(() => {
  const map: Record<string, any[]> = {
    substations: substationColumns,
    lines: lineColumns,
    areas: areaColumns
  }
  return map[activeTab.value] || []
})

const currentData = computed(() => {
  const map: Record<string, any[]> = {
    substations: substations.value,
    lines: lines.value,
    areas: areas.value
  }
  const allData = map[activeTab.value] || []
  total.value = allData.length
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return allData.slice(start, end)
})

const handlePageChange = (page: number) => { currentPage.value = page }

const currentTypeName = computed(() => {
  const map: Record<string, string> = {
    substations: '变电站',
    lines: '线路',
    areas: '台区'
  }
  return map[activeTab.value] || ''
})

const currentItemType = computed(() => {
  const map: Record<string, string> = {
    substations: 'substation',
    lines: 'line',
    areas: 'area'
  }
  return map[activeTab.value] || ''
})

const dialogVisible = ref(false)
const dialogType = ref('')
const isEdit = ref(false)
const formData = ref<any>({})
const formRef = ref()

const dialogTitle = computed(() => (isEdit.value ? '编辑' : '新增') + currentTypeName.value)

const getStatusClass = (status: string) => {
  const map: Record<string, string> = {
    '运行中': 'success', '正常': 'success',
    '停运': 'danger', '异常': 'danger',
    '检修': 'warning'
  }
  return map[status] || 'info'
}

const formatNumber = (value: number | null | undefined, decimals: number = 2) => {
  if (value == null) return '-'
  return value.toFixed(decimals)
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
    ElMessage.error('获取数据失败')
  } finally {
    loading.value = false
  }
}

const showAddDialog = () => {
  isEdit.value = false
  dialogType.value = currentItemType.value
  formData.value = { status: '运行中', loadRate: 50 }
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
    await ElMessageBox.confirm('确定要删除此记录吗？', '确认删除', { type: 'warning' })
    const apiMap: Record<string, any> = {
      substation: deleteSubstation,
      line: deleteTransmissionLine,
      area: deleteArea
    }
    await apiMap[type](id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (error) {
    if (error !== 'cancel') ElMessage.error('删除失败')
  }
}

const saveItem = async () => {
  try {
    const apiMap: Record<string, any> = {
      substation: { create: createSubstation, update: updateSubstation },
      line: { create: createTransmissionLine, update: updateTransmissionLine },
      area: { create: createArea, update: updateArea }
    }
    const api = apiMap[dialogType.value]
    if (isEdit.value) {
      await api.update(formData.value.id, formData.value)
    } else {
      await api.create(formData.value)
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

onMounted(() => fetchData())
</script>

<style scoped>
.table-container {
  height: 100%;
  overflow-y: auto;
}

.table-container::-webkit-scrollbar {
  width: 6px;
}

.table-container::-webkit-scrollbar-track {
  background: rgba(0, 240, 255, 0.05);
  border-radius: 3px;
}

.table-container::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}

.table-container::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 240, 255, 0.5);
}

.status-tag {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.success {
  background: rgba(0, 255, 128, 0.15);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.3);
}

.status-tag.warning {
  background: rgba(255, 200, 0, 0.15);
  color: #ffc800;
  border: 1px solid rgba(255, 200, 0, 0.3);
}

.status-tag.danger {
  background: rgba(255, 64, 129, 0.15);
  color: #ff4081;
  border: 1px solid rgba(255, 64, 129, 0.3);
}

.status-tag.info {
  background: rgba(0, 240, 255, 0.15);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.3);
}

.voltage-tag {
  display: inline-block;
  padding: 2px 8px;
  background: rgba(100, 255, 218, 0.15);
  color: #64ffda;
  border-radius: 4px;
  font-size: 12px;
  font-family: 'Consolas', monospace;
}

.number-value {
  font-family: 'Consolas', 'Monaco', monospace;
  color: #64ffda;
}

.load-bar {
  position: relative;
  width: 100%;
  height: 20px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 10px;
  overflow: hidden;
}

.load-fill {
  height: 100%;
  background: linear-gradient(90deg, #00f0ff, #00ff80);
  border-radius: 10px;
  transition: width 0.3s ease;
}

.load-text {
  position: absolute;
  right: 8px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 11px;
  color: #fff;
}

.action-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
}

.action-btn {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 6px;
  border: 1px solid transparent;
  cursor: pointer;
  transition: all 0.2s ease;
  background: transparent;
  color: #6a7a8a;
}

.action-btn:hover {
  transform: scale(1.1);
}

.action-btn.edit:hover {
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.1);
  border-color: rgba(0, 240, 255, 0.3);
}

.action-btn.delete:hover {
  color: #ff4081;
  background: rgba(255, 64, 129, 0.1);
  border-color: rgba(255, 64, 129, 0.3);
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

:deep(.el-dialog) {
  background: rgba(10, 10, 26, 0.98);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 16px;
  backdrop-filter: blur(20px);
}

:deep(.el-dialog__header) {
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  padding: 16px 20px;
}

:deep(.el-dialog__title) {
  color: #00f0ff;
  font-weight: 600;
}

:deep(.el-dialog__body) {
  padding: 20px;
}

:deep(.el-form-item__label) {
  color: rgba(255, 255, 255, 0.8);
}

:deep(.el-input__wrapper),
:deep(.el-select__wrapper),
:deep(.el-input-number) {
  background: rgba(0, 0, 0, 0.3) !important;
  border: 1px solid rgba(0, 240, 255, 0.2) !important;
  box-shadow: none !important;
}

:deep(.el-input__inner),
:deep(.el-select__selected-item) {
  color: #fff !important;
}
</style>
