<template>
  <div class="page-help">
    <el-tooltip content="帮助" placement="bottom">
      <button class="help-btn" @click="showHelp = true">
        <el-icon :size="18"><QuestionFilled /></el-icon>
      </button>
    </el-tooltip>
    
    <el-dialog
      v-model="showHelp"
      :title="`📖 ${pageTitle} - 操作指南`"
      width="500px"
      class="help-dialog"
      :close-on-click-modal="true"
    >
      <div class="help-content">
        <div class="help-section" v-for="(section, index) in helpContent" :key="index">
          <div class="section-title">
            <span class="section-icon">{{ section.icon }}</span>
            <span>{{ section.title }}</span>
          </div>
          <ul class="section-list">
            <li v-for="(item, idx) in section.items" :key="idx">
              {{ item }}
            </li>
          </ul>
        </div>
        
        <div class="help-tips" v-if="tips.length > 0">
          <div class="tips-title">💡 小提示</div>
          <div class="tips-content">
            <div class="tip-item" v-for="(tip, index) in tips" :key="index">
              {{ tip }}
            </div>
          </div>
        </div>
      </div>
      
      <template #footer>
        <div class="help-footer">
          <el-checkbox v-model="dontShowAgain" size="small">不再显示此帮助</el-checkbox>
          <el-button type="primary" @click="closeHelp">我知道了</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { QuestionFilled } from '@element-plus/icons-vue'

interface HelpSection {
  icon: string
  title: string
  items: string[]
}

const props = defineProps<{
  pageKey: string
  pageTitle: string
  helpContent: HelpSection[]
  tips?: string[]
}>()

const showHelp = ref(false)
const dontShowAgain = ref(false)

const closeHelp = () => {
  if (dontShowAgain.value) {
    localStorage.setItem(`help_${props.pageKey}_dismissed`, 'true')
  }
  showHelp.value = false
}

watch(() => props.pageKey, (key) => {
  const dismissed = localStorage.getItem(`help_${key}_dismissed`)
  dontShowAgain.value = dismissed === 'true'
}, { immediate: true })
</script>

<style scoped>
.page-help {
  display: inline-flex;
  align-items: center;
}

.help-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 240, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 8px;
  color: #00f0ff;
  cursor: pointer;
  transition: all 0.3s ease;
}

.help-btn:hover {
  background: rgba(0, 240, 255, 0.2);
  border-color: rgba(0, 240, 255, 0.5);
  transform: scale(1.05);
}

.help-dialog :deep(.el-dialog) {
  background: rgba(10, 10, 26, 0.98);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 16px;
}

.help-dialog :deep(.el-dialog__header) {
  background: linear-gradient(90deg, rgba(0, 240, 255, 0.1), transparent);
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
  padding: 16px 20px;
}

.help-dialog :deep(.el-dialog__title) {
  color: #00f0ff;
  font-size: 16px;
  font-weight: 600;
}

.help-dialog :deep(.el-dialog__body) {
  padding: 20px;
  color: #a0b0c0;
}

.help-dialog :deep(.el-dialog__footer) {
  border-top: 1px solid rgba(0, 240, 255, 0.1);
  padding: 16px 20px;
}

.help-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.help-section {
  background: rgba(0, 240, 255, 0.03);
  border: 1px solid rgba(0, 240, 255, 0.1);
  border-radius: 10px;
  padding: 14px;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 600;
  color: #e8e8e8;
  margin-bottom: 10px;
}

.section-icon {
  font-size: 16px;
}

.section-list {
  margin: 0;
  padding-left: 20px;
  list-style-type: disc;
}

.section-list li {
  font-size: 13px;
  color: #a0b0c0;
  line-height: 1.8;
}

.help-tips {
  background: linear-gradient(135deg, rgba(255, 200, 0, 0.1), rgba(255, 150, 0, 0.05));
  border: 1px solid rgba(255, 200, 0, 0.2);
  border-radius: 10px;
  padding: 14px;
}

.tips-title {
  font-size: 13px;
  font-weight: 600;
  color: #ffc800;
  margin-bottom: 8px;
}

.tips-content {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.tip-item {
  font-size: 12px;
  color: #c0a060;
  line-height: 1.6;
}

.help-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.help-footer :deep(.el-checkbox__label) {
  color: #6a7a8a;
  font-size: 12px;
}
</style>
