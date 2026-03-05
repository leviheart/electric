<template>
  <div class="guide-overlay" v-if="visible">
    <div class="guide-mask" @click="handleMaskClick"></div>
    
    <div 
      class="guide-highlight" 
      v-if="targetRect"
      :style="highlightStyle"
    ></div>
    
    <div 
      class="guide-tooltip"
      v-if="currentStepData"
      :style="tooltipStyle"
    >
      <div class="tooltip-header">
        <div class="step-indicator">
          <span class="step-current">{{ currentStep + 1 }}</span>
          <span class="step-divider">/</span>
          <span class="step-total">{{ steps.length }}</span>
        </div>
        <div class="tooltip-title">{{ currentStepData.title }}</div>
      </div>
      
      <div class="tooltip-content">
        <div class="tooltip-icon">{{ currentStepData.icon }}</div>
        <div class="tooltip-text">{{ currentStepData.content }}</div>
      </div>
      
      <div class="tooltip-footer">
        <div class="footer-left">
          <el-checkbox v-model="dontShowAgain" size="small">
            不再提示
          </el-checkbox>
        </div>
        <div class="footer-right">
          <el-button 
            v-if="currentStep > 0" 
            size="small" 
            @click="prevStep"
          >
            上一步
          </el-button>
          <el-button 
            v-if="currentStep < steps.length - 1"
            type="primary" 
            size="small" 
            @click="nextStep"
          >
            下一步
          </el-button>
          <el-button 
            v-else
            type="primary" 
            size="small" 
            @click="finishGuide"
          >
            开始使用
          </el-button>
        </div>
      </div>
      
      <div class="tooltip-arrow" :class="arrowClass"></div>
    </div>
    
    <div class="guide-progress">
      <div 
        class="progress-dot" 
        v-for="(step, index) in steps" 
        :key="index"
        :class="{ active: index === currentStep, completed: index < currentStep }"
        @click="goToStep(index)"
      ></div>
    </div>
    
    <button class="skip-btn" @click="skipGuide">
      跳过引导
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch, nextTick } from 'vue'

interface GuideStep {
  target: string
  title: string
  content: string
  icon: string
  position?: 'top' | 'bottom' | 'left' | 'right'
}

const props = defineProps<{
  visible: boolean
}>()

const emit = defineEmits<{
  'update:visible': [value: boolean]
  'finish': []
  'skip': []
}>()

const currentStep = ref(0)
const dontShowAgain = ref(false)
const targetElement = ref<HTMLElement | null>(null)
const targetRect = ref<DOMRect | null>(null)

const steps: GuideStep[] = [
  {
    target: '#map',
    title: '地图操作',
    content: '这是电网地图区域，您可以点击设备图标查看详细信息，使用鼠标滚轮缩放，拖拽移动地图。',
    icon: '🗺️',
    position: 'right'
  },
  {
    target: '.control-panel',
    title: '图层控制',
    content: '在这里控制地图上图层的显示与隐藏，包括变电站、输电线路、供电区域等。',
    icon: '📊',
    position: 'right'
  },
  {
    target: '.stats-panel',
    title: '数据统计',
    content: '实时显示电网运行统计数据，包括设备数量、告警数量、平均负载率等关键指标。',
    icon: '📈',
    position: 'left'
  },
  {
    target: '.quick-entry',
    title: '功能导航',
    content: '点击这里快速访问所有功能模块，包括运维管理、分析计算、资源管理等。',
    icon: '🧭',
    position: 'right'
  },
  {
    target: '.region-selector',
    title: '区域选择',
    content: '选择不同的行政区域，快速定位到目标区域的电网设备。',
    icon: '📍',
    position: 'bottom'
  }
]

const currentStepData = computed(() => steps[currentStep.value])

const highlightStyle = computed(() => {
  console.log('highlightStyle 计算, targetRect:', targetRect.value)
  if (!targetRect.value) return {}
  const rect = targetRect.value
  return {
    left: `${rect.left - 8}px`,
    top: `${rect.top - 8}px`,
    width: `${rect.width + 16}px`,
    height: `${rect.height + 16}px`
  }
})

const tooltipStyle = computed(() => {
  const position = currentStepData.value?.position || 'right'
  const tooltipWidth = 320
  const tooltipHeight = 180
  const gap = 20
  
  let left = 0
  let top = 0
  
  if (targetRect.value) {
    const rect = targetRect.value
    switch (position) {
      case 'right':
        left = rect.right + gap
        top = rect.top + (rect.height - tooltipHeight) / 2
        break
      case 'left':
        left = rect.left - tooltipWidth - gap
        top = rect.top + (rect.height - tooltipHeight) / 2
        break
      case 'bottom':
        left = rect.left + (rect.width - tooltipWidth) / 2
        top = rect.bottom + gap
        break
      case 'top':
        left = rect.left + (rect.width - tooltipWidth) / 2
        top = rect.top - tooltipHeight - gap
        break
    }
  } else {
    left = window.innerWidth / 2 - tooltipWidth / 2 + 100
    top = window.innerHeight / 2 - tooltipHeight / 2
  }
  
  left = Math.max(20, Math.min(left, window.innerWidth - tooltipWidth - 20))
  top = Math.max(20, Math.min(top, window.innerHeight - tooltipHeight - 20))
  
  const style = {
    left: `${left}px`,
    top: `${top}px`
  }
  console.log('tooltipStyle:', style, 'targetRect:', targetRect.value)
  return style
})

const arrowClass = computed(() => {
  if (!currentStepData.value) return ''
  return `arrow-${currentStepData.value.position || 'right'}`
})

const updateTargetElement = async () => {
  if (!currentStepData.value) return
  
  await nextTick()
  
  // 等待元素渲染
  let retries = 0
  const maxRetries = 10
  const selector = currentStepData.value.target
  
  while (!targetElement.value && retries < maxRetries) {
    targetElement.value = document.querySelector(selector)
    if (!targetElement.value) {
      await new Promise(resolve => setTimeout(resolve, 100))
      retries++
    }
  }
  
  console.log('GuideOverlay - 查找元素:', selector, '结果:', targetElement.value, '重试次数:', retries)
  
  if (targetElement.value) {
    targetRect.value = targetElement.value.getBoundingClientRect()
    targetElement.value.classList.add('guide-highlight-element')
  } else {
    // 如果找不到元素，使用默认位置（屏幕中央）
    targetRect.value = {
      left: window.innerWidth / 2 - 200,
      top: window.innerHeight / 2 - 100,
      right: window.innerWidth / 2 + 200,
      bottom: window.innerHeight / 2 + 100,
      width: 400,
      height: 200,
      x: window.innerWidth / 2 - 200,
      y: window.innerHeight / 2 - 100,
      toJSON: () => ({})
    } as DOMRect
  }
}

const prevStep = () => {
  if (currentStep.value > 0) {
    currentStep.value--
  }
}

const nextStep = () => {
  if (currentStep.value < steps.length - 1) {
    currentStep.value++
  }
}

const goToStep = (index: number) => {
  currentStep.value = index
}

const finishGuide = () => {
  if (dontShowAgain.value) {
    localStorage.setItem('guide_completed', 'true')
  }
  emit('update:visible', false)
  emit('finish')
}

const skipGuide = () => {
  if (dontShowAgain.value) {
    localStorage.setItem('guide_completed', 'true')
  }
  emit('update:visible', false)
  emit('skip')
}

const handleMaskClick = () => {
}

watch(currentStep, () => {
  if (!props.visible) return
  if (targetElement.value) {
    targetElement.value.classList.remove('guide-highlight-element')
  }
  updateTargetElement()
})

watch(() => props.visible, (visible) => {
  console.log('GuideOverlay - visible 变化:', visible)
  if (visible) {
    currentStep.value = 0
    dontShowAgain.value = false
    updateTargetElement()
    
    // 强制触发重新渲染
    nextTick(() => {
      console.log('GuideOverlay - DOM 检查:', document.querySelector('.guide-overlay'))
      console.log('GuideOverlay - mask 检查:', document.querySelector('.guide-mask'))
      console.log('GuideOverlay - tooltip 检查:', document.querySelector('.guide-tooltip'))
    })
  } else if (targetElement.value) {
    targetElement.value.classList.remove('guide-highlight-element')
  }
})

onMounted(() => {
  console.log('GuideOverlay - mounted, visible:', props.visible)
  if (props.visible) {
    updateTargetElement()
  }
})
</script>

<style scoped>
.guide-overlay {
  position: fixed !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  bottom: 0 !important;
  z-index: 99999 !important;
  pointer-events: auto !important;
  display: block !important;
  visibility: visible !important;
  opacity: 1 !important;
}

.guide-mask {
  position: absolute !important;
  top: 0 !important;
  left: 0 !important;
  right: 0 !important;
  bottom: 0 !important;
  background: rgba(0, 0, 0, 0.7) !important;
  pointer-events: auto !important;
  display: block !important;
  visibility: visible !important;
}

.guide-highlight {
  position: absolute !important;
  border-radius: 12px !important;
  box-shadow: 0 0 0 4px rgba(0, 240, 255, 0.6), 0 0 30px rgba(0, 240, 255, 0.4) !important;
  pointer-events: none !important;
  z-index: 1 !important;
  display: block !important;
  visibility: visible !important;
  animation: pulse-highlight 2s ease-in-out infinite;
}

@keyframes pulse-highlight {
  0%, 100% {
    box-shadow: 0 0 0 4px rgba(0, 240, 255, 0.6), 0 0 30px rgba(0, 240, 255, 0.4);
  }
  50% {
    box-shadow: 0 0 0 6px rgba(0, 240, 255, 0.8), 0 0 50px rgba(0, 240, 255, 0.6);
  }
}

.guide-tooltip {
  position: absolute !important;
  width: 320px !important;
  background: rgba(10, 10, 26, 0.98) !important;
  border: 1px solid rgba(0, 240, 255, 0.5) !important;
  border-radius: 16px !important;
  padding: 20px !important;
  pointer-events: auto !important;
  z-index: 2 !important;
  display: block !important;
  visibility: visible !important;
  box-shadow: 0 8px 40px rgba(0, 0, 0, 0.5), 0 0 60px rgba(0, 240, 255, 0.2) !important;
  backdrop-filter: blur(10px);
}

.tooltip-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}

.step-indicator {
  display: flex;
  align-items: center;
  gap: 2px;
  padding: 4px 10px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.2), rgba(0, 128, 255, 0.2));
  border-radius: 12px;
  font-size: 12px;
}

.step-current {
  color: #00f0ff;
  font-weight: 600;
}

.step-divider {
  color: #4a5568;
}

.step-total {
  color: #6a7a8a;
}

.tooltip-title {
  font-size: 16px;
  font-weight: 600;
  color: #fff;
}

.tooltip-content {
  display: flex;
  gap: 12px;
  margin-bottom: 20px;
}

.tooltip-icon {
  font-size: 32px;
  flex-shrink: 0;
}

.tooltip-text {
  font-size: 14px;
  color: #a0b0c0;
  line-height: 1.6;
}

.tooltip-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-top: 16px;
  border-top: 1px solid rgba(0, 240, 255, 0.1);
}

.footer-left {
  display: flex;
  align-items: center;
}

.footer-left :deep(.el-checkbox__label) {
  color: #6a7a8a;
  font-size: 12px;
}

.footer-right {
  display: flex;
  gap: 8px;
}

.tooltip-arrow {
  position: absolute;
  width: 12px;
  height: 12px;
  background: rgba(10, 10, 26, 0.98);
  border: 1px solid rgba(0, 240, 255, 0.5);
  transform: rotate(45deg);
}

.tooltip-arrow.arrow-right {
  left: -7px;
  top: 50%;
  margin-top: -6px;
  border-right: none;
  border-top: none;
}

.tooltip-arrow.arrow-left {
  right: -7px;
  top: 50%;
  margin-top: -6px;
  border-left: none;
  border-bottom: none;
}

.tooltip-arrow.arrow-bottom {
  left: 50%;
  top: -7px;
  margin-left: -6px;
  border-bottom: none;
  border-right: none;
}

.tooltip-arrow.arrow-top {
  left: 50%;
  bottom: -7px;
  margin-left: -6px;
  border-top: none;
  border-left: none;
}

.guide-progress {
  position: absolute;
  bottom: 80px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
  pointer-events: auto;
  z-index: 2;
}

.progress-dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.3);
  cursor: pointer;
  transition: all 0.3s ease;
}

.progress-dot:hover {
  background: rgba(255, 255, 255, 0.5);
}

.progress-dot.active {
  background: #00f0ff;
  box-shadow: 0 0 10px rgba(0, 240, 255, 0.6);
  transform: scale(1.2);
}

.progress-dot.completed {
  background: rgba(0, 240, 255, 0.5);
}

.skip-btn {
  position: absolute;
  top: 20px;
  right: 20px;
  padding: 8px 16px;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  color: #a0b0c0;
  font-size: 13px;
  cursor: pointer;
  pointer-events: auto;
  z-index: 2;
  transition: all 0.3s ease;
}

.skip-btn:hover {
  background: rgba(255, 255, 255, 0.2);
  color: #fff;
}
</style>

<style>
.guide-highlight-element {
  position: relative !important;
  z-index: 10000 !important;
}
</style>
