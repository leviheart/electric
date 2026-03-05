<template>
  <el-dialog
    v-model="visible"
    title="⌨️ 快捷键"
    width="400px"
    class="shortcuts-dialog"
    :close-on-click-modal="true"
  >
    <div class="shortcuts-content">
      <div class="shortcut-group" v-for="group in shortcutGroups" :key="group.title">
        <div class="group-title">{{ group.title }}</div>
        <div class="shortcut-item" v-for="(shortcut, index) in group.items" :key="index">
          <div class="shortcut-keys">
            <kbd v-for="(key, idx) in shortcut.keys" :key="idx">{{ key }}</kbd>
          </div>
          <div class="shortcut-desc">{{ shortcut.desc }}</div>
        </div>
      </div>
    </div>
    
    <template #footer>
      <div class="shortcuts-footer">
        <span class="footer-tip">按 <kbd>?</kbd> 随时打开此面板</span>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const visible = ref(false)
const router = useRouter()

const emit = defineEmits<{
  'toggle-guide': []
  'toggle-state-switcher': []
}>()

const shortcutGroups = [
  {
    title: '导航',
    items: [
      { keys: ['M'], desc: '跳转到地图页面' },
      { keys: ['D'], desc: '跳转到数据统计' },
      { keys: ['O'], desc: '跳转到运维管理' },
      { keys: ['A'], desc: '跳转到分析计算' }
    ]
  },
  {
    title: '功能',
    items: [
      { keys: ['?'], desc: '显示快捷键帮助' },
      { keys: ['G'], desc: '重新显示新手引导' },
      { keys: ['S'], desc: '切换一图四态面板' },
      { keys: ['R'], desc: '刷新当前页面数据' }
    ]
  },
  {
    title: '通用',
    items: [
      { keys: ['Esc'], desc: '关闭弹窗/面板' },
      { keys: ['/'], desc: '聚焦搜索框' }
    ]
  }
]

const handleKeyDown = (event: KeyboardEvent) => {
  if (event.target instanceof HTMLInputElement || event.target instanceof HTMLTextAreaElement) {
    if (event.key === 'Escape') {
      (event.target as HTMLElement).blur()
    }
    return
  }
  
  switch (event.key.toLowerCase()) {
    case '?':
      visible.value = !visible.value
      break
    case 'm':
      router.push('/')
      break
    case 'd':
      router.push('/dashboard')
      break
    case 'o':
      router.push('/operation')
      break
    case 'a':
      router.push('/analysis')
      break
    case 'g':
      console.log('按下了 G 键，触发 toggle-guide 事件')
      emit('toggle-guide')
      break
    case 's':
      emit('toggle-state-switcher')
      break
    case 'r':
      window.dispatchEvent(new CustomEvent('refresh-data'))
      break
    case '/':
      event.preventDefault()
      const searchInput = document.querySelector('.search-input input') as HTMLInputElement
      searchInput?.focus()
      break
    case 'escape':
      visible.value = false
      break
  }
}

onMounted(() => {
  window.addEventListener('keydown', handleKeyDown)
})

onUnmounted(() => {
  window.removeEventListener('keydown', handleKeyDown)
})

defineExpose({
  show: () => { visible.value = true },
  hide: () => { visible.value = false }
})
</script>

<style scoped>
.shortcuts-dialog :deep(.el-dialog) {
  background: rgba(10, 10, 26, 0.98);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 16px;
}

.shortcuts-dialog :deep(.el-dialog__header) {
  background: linear-gradient(90deg, rgba(0, 240, 255, 0.1), transparent);
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
  padding: 16px 20px;
}

.shortcuts-dialog :deep(.el-dialog__title) {
  color: #00f0ff;
  font-size: 16px;
  font-weight: 600;
}

.shortcuts-dialog :deep(.el-dialog__body) {
  padding: 16px 20px;
}

.shortcuts-dialog :deep(.el-dialog__footer) {
  border-top: 1px solid rgba(0, 240, 255, 0.1);
  padding: 12px 20px;
}

.shortcuts-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.shortcut-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.group-title {
  font-size: 12px;
  color: #6a7a8a;
  text-transform: uppercase;
  letter-spacing: 1px;
  padding-bottom: 4px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.1);
}

.shortcut-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 0;
}

.shortcut-keys {
  display: flex;
  gap: 4px;
}

kbd {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-width: 24px;
  height: 24px;
  padding: 0 8px;
  background: rgba(0, 240, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 6px;
  color: #00f0ff;
  font-family: inherit;
  font-size: 12px;
  font-weight: 500;
}

.shortcut-desc {
  font-size: 13px;
  color: #a0b0c0;
}

.shortcuts-footer {
  display: flex;
  justify-content: center;
}

.footer-tip {
  font-size: 12px;
  color: #6a7a8a;
}

.footer-tip kbd {
  display: inline-flex;
  min-width: 20px;
  height: 20px;
  padding: 0 6px;
  font-size: 11px;
  margin: 0 4px;
  vertical-align: middle;
}
</style>
