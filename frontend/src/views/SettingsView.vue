<template>
  <TechPage title="系统设置">
    <div class="settings-container">
      <div class="settings-section">
        <div class="section-header">
          <div class="section-icon">
            <el-icon :size="24"><MapLocation /></el-icon>
          </div>
          <div class="section-title">
            <h3>地图设置</h3>
            <p>配置地图显示样式和默认参数</p>
          </div>
        </div>
        <div class="settings-list">
          <div class="setting-item">
            <div class="setting-info">
              <span class="setting-label">地图样式</span>
              <span class="setting-desc">选择地图的显示风格</span>
            </div>
            <el-select v-model="settingsStore.mapStyle" placeholder="选择地图样式" @change="handleChange">
              <el-option label="深色模式" value="dark" />
              <el-option label="浅色模式" value="light" />
              <el-option label="卫星图" value="satellite" />
            </el-select>
          </div>
          <div class="setting-item">
            <div class="setting-info">
              <span class="setting-label">默认缩放级别</span>
              <span class="setting-desc">地图初始加载时的缩放级别</span>
            </div>
            <div class="slider-container">
              <el-slider v-model="settingsStore.defaultZoom" :min="8" :max="18" show-input @change="handleChange" />
            </div>
          </div>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <div class="section-icon">
            <el-icon :size="24"><Setting /></el-icon>
          </div>
          <div class="section-title">
            <h3>数据设置</h3>
            <p>配置数据加载和显示选项</p>
          </div>
        </div>
        <div class="settings-list">
          <div class="setting-item">
            <div class="setting-info">
              <span class="setting-label">自动加载数据</span>
              <span class="setting-desc">页面加载时自动获取设备数据</span>
            </div>
            <el-switch v-model="settingsStore.autoLoad" @change="handleChange" />
          </div>
          <div class="setting-item">
            <div class="setting-info">
              <span class="setting-label">显示网格背景</span>
              <span class="setting-desc">在地图上显示网格参考线</span>
            </div>
            <el-switch v-model="settingsStore.showGrid" @change="handleChange" />
          </div>
          <div class="setting-item">
            <div class="setting-info">
              <span class="setting-label">启用动画效果</span>
              <span class="setting-desc">启用页面过渡和交互动画</span>
            </div>
            <el-switch v-model="settingsStore.enableAnimations" @change="handleChange" />
          </div>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <div class="section-icon">
            <el-icon :size="24"><Bell /></el-icon>
          </div>
          <div class="section-title">
            <h3>通知设置</h3>
            <p>配置告警和消息通知选项</p>
          </div>
        </div>
        <div class="settings-list">
          <div class="setting-item">
            <div class="setting-info">
              <span class="setting-label">启用告警通知</span>
              <span class="setting-desc">接收实时告警推送通知</span>
            </div>
            <el-switch v-model="settingsStore.enableNotifications" @change="handleChange" />
          </div>
          <div class="setting-item">
            <div class="setting-info">
              <span class="setting-label">告警声音提醒</span>
              <span class="setting-desc">告警触发时播放提示音</span>
            </div>
            <el-switch v-model="settingsStore.soundEnabled" @change="handleChange" />
          </div>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <div class="section-icon">
            <el-icon :size="24"><User /></el-icon>
          </div>
          <div class="section-title">
            <h3>账户设置</h3>
            <p>管理您的账户和偏好</p>
          </div>
        </div>
        <div class="settings-list">
          <div class="setting-item clickable" @click="showPasswordDialog = true">
            <div class="setting-info">
              <span class="setting-label">修改密码</span>
              <span class="setting-desc">更新您的登录密码</span>
            </div>
            <el-icon><ArrowRight /></el-icon>
          </div>
          <div class="setting-item clickable" @click="handleLogout">
            <div class="setting-info">
              <span class="setting-label danger">退出登录</span>
              <span class="setting-desc">退出当前账户</span>
            </div>
            <el-icon><ArrowRight /></el-icon>
          </div>
        </div>
      </div>
    </div>

    <el-dialog v-model="showPasswordDialog" title="修改密码" width="400px">
      <el-form :model="passwordForm" label-width="100px">
        <el-form-item label="当前密码">
          <el-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入当前密码" show-password />
        </el-form-item>
        <el-form-item label="新密码">
          <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码" show-password />
        </el-form-item>
        <el-form-item label="确认新密码">
          <el-input v-model="passwordForm.confirmPassword" type="password" placeholder="请再次输入新密码" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <TechButton @click="showPasswordDialog = false">取消</TechButton>
        <TechButton type="primary" @click="handleChangePassword">确定</TechButton>
      </template>
    </el-dialog>
  </TechPage>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { MapLocation, Setting, Bell, User, ArrowRight } from '@element-plus/icons-vue'
import TechPage from '@/components/common/TechPage.vue'
import TechButton from '@/components/common/TechButton.vue'
import { useSettingsStore } from '@/stores/settingsStore'
import { useAuthStore } from '@/stores/authStore'

const router = useRouter()
const settingsStore = useSettingsStore()
const authStore = useAuthStore()

const showPasswordDialog = ref(false)
const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const handleChange = () => {
  settingsStore.saveSettings()
  ElMessage.success('设置已保存')
}

const handleChangePassword = () => {
  if (!passwordForm.value.oldPassword || !passwordForm.value.newPassword) {
    ElMessage.warning('请填写完整信息')
    return
  }
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致')
    return
  }
  ElMessage.success('密码修改成功')
  showPasswordDialog.value = false
  passwordForm.value = { oldPassword: '', newPassword: '', confirmPassword: '' }
}

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
  ElMessage.success('已退出登录')
}
</script>

<style scoped>
.settings-container {
  max-width: 800px;
  margin: 0 auto;
}

.settings-section {
  background: rgba(10, 10, 26, 0.6);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 12px;
  margin-bottom: 20px;
  overflow: hidden;
}

.section-header {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 20px;
  background: rgba(0, 240, 255, 0.05);
  border-bottom: 1px solid rgba(0, 240, 255, 0.1);
}

.section-icon {
  width: 48px;
  height: 48px;
  border-radius: 12px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.2), rgba(0, 128, 255, 0.2));
  display: flex;
  align-items: center;
  justify-content: center;
  color: #00f0ff;
}

.section-title h3 {
  margin: 0 0 4px 0;
  font-size: 16px;
  color: #fff;
}

.section-title p {
  margin: 0;
  font-size: 12px;
  color: #6a7a8a;
}

.settings-list {
  padding: 8px 0;
}

.setting-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  transition: background 0.2s ease;
}

.setting-item:hover {
  background: rgba(0, 240, 255, 0.05);
}

.setting-item.clickable {
  cursor: pointer;
}

.setting-item.clickable:hover {
  background: rgba(0, 240, 255, 0.1);
}

.setting-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.setting-label {
  font-size: 14px;
  color: #e8e8e8;
}

.setting-label.danger {
  color: #ff4081;
}

.setting-desc {
  font-size: 12px;
  color: #6a7a8a;
}

.slider-container {
  width: 200px;
}

:deep(.el-select) {
  width: 160px;
}

:deep(.el-select .el-input__wrapper) {
  background: rgba(10, 10, 26, 0.8);
  border: 1px solid rgba(0, 240, 255, 0.3);
  box-shadow: none;
}

:deep(.el-select .el-input__inner) {
  color: #e8e8e8;
}

:deep(.el-slider__runway) {
  background: rgba(0, 240, 255, 0.2);
}

:deep(.el-slider__bar) {
  background: linear-gradient(90deg, #00f0ff, #0080ff);
}

:deep(.el-slider__button) {
  border-color: #00f0ff;
}

:deep(.el-switch.is-checked .el-switch__core) {
  background: linear-gradient(90deg, #00f0ff, #0080ff);
  border-color: #00f0ff;
}
</style>
