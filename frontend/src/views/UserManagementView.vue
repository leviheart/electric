<template>
  <TechPage title="用户管理">
    <div class="user-stats">
      <div class="stat-card">
        <div class="stat-icon total"><el-icon :size="24"><User /></el-icon></div>
        <div class="stat-info">
          <span class="stat-value">{{ users.length }}</span>
          <span class="stat-label">用户总数</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon admin"><el-icon :size="24"><UserFilled /></el-icon></div>
        <div class="stat-info">
          <span class="stat-value">{{ adminCount }}</span>
          <span class="stat-label">管理员</span>
        </div>
      </div>
      <div class="stat-card">
        <div class="stat-icon active"><el-icon :size="24"><CircleCheck /></el-icon></div>
        <div class="stat-info">
          <span class="stat-value">{{ activeCount }}</span>
          <span class="stat-label">启用用户</span>
        </div>
      </div>
    </div>

    <TechTable
      :columns="columns"
      :data="users"
      :loading="loading"
      title="用户列表"
    >
      <template #toolbar>
        <TechButton type="primary" icon="Plus" @click="showAddDialog = true">添加用户</TechButton>
        <TechButton icon="Refresh" @click="loadUsers">刷新</TechButton>
      </template>

      <template #role="{ row }">
        <span class="role-tag" :class="row.role === 'ADMIN' ? 'admin' : 'user'">
          {{ row.role === 'ADMIN' ? '管理员' : '普通用户' }}
        </span>
      </template>

      <template #enabled="{ row }">
        <span class="status-tag" :class="row.enabled ? 'active' : 'disabled'">
          {{ row.enabled ? '启用' : '禁用' }}
        </span>
      </template>

      <template #createdAt="{ row }">
        {{ formatTime(row.createdAt) }}
      </template>

      <template #actions="{ row }">
        <div class="action-buttons">
          <button class="action-btn edit" @click="handleEdit(row)" title="编辑">
            <el-icon><Edit /></el-icon>
          </button>
          <button 
            class="action-btn" 
            :class="row.enabled ? 'warning' : 'success'"
            @click="handleToggleEnable(row)"
            :title="row.enabled ? '禁用' : '启用'"
          >
            <el-icon><component :is="row.enabled ? 'Lock' : 'Unlock'" /></el-icon>
          </button>
          <button 
            class="action-btn danger" 
            @click="handleDelete(row)"
            :disabled="row.role === 'ADMIN'"
            title="删除"
          >
            <el-icon><Delete /></el-icon>
          </button>
        </div>
      </template>
    </TechTable>

    <el-dialog v-model="showAddDialog" title="添加用户" width="420px" class="tech-dialog">
      <el-form :model="addForm" label-width="80px" class="tech-form">
        <el-form-item label="用户名">
          <el-input v-model="addForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="addForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="addForm.role" style="width: 100%">
            <el-option label="普通用户" value="USER" />
            <el-option label="管理员" value="ADMIN" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <TechButton @click="showAddDialog = false">取消</TechButton>
        <TechButton type="primary" @click="handleAdd">确定</TechButton>
      </template>
    </el-dialog>

    <el-dialog v-model="showEditDialog" title="编辑用户" width="420px" class="tech-dialog">
      <el-form :model="editForm" label-width="80px" class="tech-form">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="editForm.role" style="width: 100%">
            <el-option label="普通用户" value="USER" />
            <el-option label="管理员" value="ADMIN" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <TechButton @click="showEditDialog = false">取消</TechButton>
        <TechButton type="primary" @click="handleSaveEdit">保存</TechButton>
      </template>
    </el-dialog>
  </TechPage>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { User, UserFilled, CircleCheck, Edit, Delete, Lock, Unlock } from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechTable from '@/components/common/TechTable.vue'
import TechButton from '@/components/common/TechButton.vue'
import { getUsers, updateUser, deleteUser, toggleUserEnable, type UserInfo } from '../api/user'
import { register } from '../api/auth'

const users = ref<UserInfo[]>([])
const loading = ref(false)
const showAddDialog = ref(false)
const showEditDialog = ref(false)

const columns = [
  { prop: 'id', label: 'ID', width: '80px' },
  { prop: 'username', label: '用户名', width: '150px' },
  { prop: 'role', label: '角色', width: '120px', type: 'slot' },
  { prop: 'enabled', label: '状态', width: '100px', type: 'slot' },
  { prop: 'createdAt', label: '创建时间', width: '180px', type: 'slot' },
  { prop: 'actions', label: '操作', width: '150px', type: 'slot' }
]

const addForm = ref({ username: '', password: '', role: 'USER' })
const editForm = ref({ id: 0, username: '', role: '' })

const adminCount = computed(() => users.value.filter(u => u.role === 'ADMIN').length)
const activeCount = computed(() => users.value.filter(u => u.enabled).length)

const formatTime = (time: string) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN')
}

const loadUsers = async () => {
  loading.value = true
  try {
    const res = await getUsers()
    users.value = res.data
  } catch (e) {
    ElMessage.error('加载用户列表失败')
  } finally {
    loading.value = false
  }
}

const handleAdd = async () => {
  if (!addForm.value.username || !addForm.value.password) {
    ElMessage.warning('请填写完整信息')
    return
  }
  try {
    await register({ 
      username: addForm.value.username, 
      password: addForm.value.password,
      role: addForm.value.role
    })
    ElMessage.success('添加成功')
    showAddDialog.value = false
    addForm.value = { username: '', password: '', role: 'USER' }
    loadUsers()
  } catch (e) {
    ElMessage.error('添加失败')
  }
}

const handleEdit = (user: UserInfo) => {
  editForm.value = { id: user.id, username: user.username, role: user.role }
  showEditDialog.value = true
}

const handleSaveEdit = async () => {
  try {
    await updateUser(editForm.value.id, { username: editForm.value.username, role: editForm.value.role })
    ElMessage.success('保存成功')
    showEditDialog.value = false
    loadUsers()
  } catch (e) {
    ElMessage.error('保存失败')
  }
}

const handleToggleEnable = async (user: UserInfo) => {
  try {
    await toggleUserEnable(user.id, !user.enabled)
    ElMessage.success(user.enabled ? '已禁用' : '已启用')
    loadUsers()
  } catch (e) {
    ElMessage.error('操作失败')
  }
}

const handleDelete = async (user: UserInfo) => {
  try {
    await ElMessageBox.confirm('确定要删除该用户吗？', '提示', { type: 'warning' })
    await deleteUser(user.id)
    ElMessage.success('删除成功')
    loadUsers()
  } catch (e) {
    if (e !== 'cancel') ElMessage.error('删除失败')
  }
}

onMounted(() => loadUsers())
</script>

<style scoped>
.user-stats {
  display: flex;
  gap: 20px;
  margin-bottom: 24px;
}

.stat-card {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: rgba(10, 10, 26, 0.6);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  transition: all 0.3s ease;
}

.stat-card:hover {
  border-color: rgba(0, 240, 255, 0.4);
  transform: translateY(-2px);
}

.stat-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.stat-icon.total { background: linear-gradient(135deg, #00f0ff, #0080ff); }
.stat-icon.admin { background: linear-gradient(135deg, #e040fb, #7c4dff); }
.stat-icon.active { background: linear-gradient(135deg, #00ff80, #00c853); }

.stat-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-value {
  font-size: 28px;
  font-weight: 600;
  color: #fff;
  font-family: 'Consolas', monospace;
}

.stat-label {
  font-size: 13px;
  color: #6a7a8a;
}

.role-tag {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.role-tag.admin {
  background: rgba(224, 64, 251, 0.2);
  color: #e040fb;
  border: 1px solid rgba(224, 64, 251, 0.3);
}

.role-tag.user {
  background: rgba(0, 240, 255, 0.2);
  color: #00f0ff;
  border: 1px solid rgba(0, 240, 255, 0.3);
}

.status-tag {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.status-tag.active {
  background: rgba(0, 255, 128, 0.2);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.3);
}

.status-tag.disabled {
  background: rgba(255, 64, 129, 0.2);
  color: #ff4081;
  border: 1px solid rgba(255, 64, 129, 0.3);
}

.action-buttons {
  display: flex;
  gap: 8px;
}

.action-btn {
  width: 32px;
  height: 32px;
  border: 1px solid rgba(0, 240, 255, 0.3);
  background: rgba(0, 240, 255, 0.1);
  border-radius: 6px;
  color: #00f0ff;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
}

.action-btn:hover {
  background: rgba(0, 240, 255, 0.2);
  border-color: rgba(0, 240, 255, 0.5);
}

.action-btn.edit:hover { background: rgba(0, 240, 255, 0.3); }
.action-btn.warning:hover { background: rgba(255, 152, 0, 0.3); border-color: #ff9800; color: #ff9800; }
.action-btn.success:hover { background: rgba(0, 255, 128, 0.3); border-color: #00ff80; color: #00ff80; }
.action-btn.danger:hover { background: rgba(255, 64, 129, 0.3); border-color: #ff4081; color: #ff4081; }
.action-btn:disabled { opacity: 0.4; cursor: not-allowed; }
</style>
