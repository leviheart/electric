<template>
  <div class="tech-tabs">
    <div class="tabs-header">
      <div class="tabs-nav">
        <div 
          v-for="tab in tabs" 
          :key="tab.name" 
          class="tab-item"
          :class="{ active: modelValue === tab.name }"
          @click="$emit('update:modelValue', tab.name)"
        >
          <el-icon v-if="tab.icon"><component :is="tab.icon" /></el-icon>
          <span>{{ tab.label }}</span>
          <div class="tab-indicator"></div>
        </div>
      </div>
      <div class="tabs-extra">
        <slot name="extra"></slot>
      </div>
    </div>
    <div class="tabs-content">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  tabs: { name: string; label: string; icon?: any }[]
  modelValue: string
}>()

defineEmits<{
  'update:modelValue': [value: string]
}>()
</script>

<style scoped>
.tech-tabs {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.tabs-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background: rgba(10, 15, 30, 0.8);
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.tabs-nav {
  display: flex;
  gap: 4px;
}

.tab-item {
  position: relative;
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 16px 24px;
  font-size: 14px;
  color: #6a7a8a;
  cursor: pointer;
  transition: all 0.25s ease;
  border-bottom: 2px solid transparent;
}

.tab-item:hover {
  color: #a0b0c0;
  background: rgba(0, 240, 255, 0.05);
}

.tab-item.active {
  color: #00f0ff;
}

.tab-item.active .tab-indicator {
  opacity: 1;
}

.tab-indicator {
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 60%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #00f0ff, transparent);
  opacity: 0;
  transition: opacity 0.25s ease;
}

.tabs-extra {
  display: flex;
  align-items: center;
  gap: 10px;
}

.tabs-content {
  flex: 1;
  overflow: auto;
  padding: 20px;
}
</style>
