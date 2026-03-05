<template>
  <button class="tech-button" :class="[`tech-button--${type}`, { 'is-plain': plain, 'is-round': round, 'is-loading': loading }]" :disabled="disabled || loading" @click="$emit('click')">
    <span class="button-content">
      <el-icon v-if="loading" class="loading-icon"><Loading /></el-icon>
      <el-icon v-else-if="icon"><component :is="icon" /></el-icon>
      <slot></slot>
    </span>
  </button>
</template>

<script setup lang="ts">
import { Loading } from '@element-plus/icons-vue'

defineProps<{
  type?: 'primary' | 'success' | 'warning' | 'danger' | 'info' | 'default'
  plain?: boolean
  round?: boolean
  icon?: any
  loading?: boolean
  disabled?: boolean
}>()

defineEmits<{
  click: []
}>()
</script>

<style scoped>
.tech-button {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 10px 20px;
  font-size: 14px;
  font-weight: 500;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.25s ease;
  border: 1px solid transparent;
  background: transparent;
  color: #c8d0e0;
  position: relative;
  overflow: hidden;
}

.tech-button::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.1), transparent);
  transition: left 0.5s ease;
}

.tech-button:hover::before {
  left: 100%;
}

.button-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  position: relative;
  z-index: 1;
  width: 100%;
  height: 100%;
}

.tech-button--default {
  background: rgba(30, 40, 60, 0.8);
  border-color: rgba(100, 120, 150, 0.3);
}

.tech-button--default:hover {
  background: rgba(50, 60, 80, 0.9);
  border-color: rgba(100, 120, 150, 0.5);
  color: #fff;
}

.tech-button--primary {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.2) 0%, rgba(0, 128, 255, 0.2) 100%);
  border-color: rgba(0, 240, 255, 0.5);
  color: #00f0ff;
}

.tech-button--primary:hover {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.3) 0%, rgba(0, 128, 255, 0.3) 100%);
  border-color: rgba(0, 240, 255, 0.8);
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.3);
}

.tech-button--success {
  background: linear-gradient(135deg, rgba(0, 255, 128, 0.2) 0%, rgba(0, 200, 83, 0.2) 100%);
  border-color: rgba(0, 255, 128, 0.5);
  color: #00ff80;
}

.tech-button--success:hover {
  background: linear-gradient(135deg, rgba(0, 255, 128, 0.3) 0%, rgba(0, 200, 83, 0.3) 100%);
  border-color: rgba(0, 255, 128, 0.8);
  box-shadow: 0 0 20px rgba(0, 255, 128, 0.3);
}

.tech-button--warning {
  background: linear-gradient(135deg, rgba(255, 152, 0, 0.2) 0%, rgba(255, 87, 34, 0.2) 100%);
  border-color: rgba(255, 152, 0, 0.5);
  color: #ff9800;
}

.tech-button--warning:hover {
  background: linear-gradient(135deg, rgba(255, 152, 0, 0.3) 0%, rgba(255, 87, 34, 0.3) 100%);
  border-color: rgba(255, 152, 0, 0.8);
  box-shadow: 0 0 20px rgba(255, 152, 0, 0.3);
}

.tech-button--danger {
  background: linear-gradient(135deg, rgba(255, 64, 129, 0.2) 0%, rgba(245, 0, 87, 0.2) 100%);
  border-color: rgba(255, 64, 129, 0.5);
  color: #ff4081;
}

.tech-button--danger:hover {
  background: linear-gradient(135deg, rgba(255, 64, 129, 0.3) 0%, rgba(245, 0, 87, 0.3) 100%);
  border-color: rgba(255, 64, 129, 0.8);
  box-shadow: 0 0 20px rgba(255, 64, 129, 0.3);
}

.tech-button--info {
  background: linear-gradient(135deg, rgba(100, 255, 218, 0.2) 0%, rgba(0, 191, 165, 0.2) 100%);
  border-color: rgba(100, 255, 218, 0.5);
  color: #64ffda;
}

.tech-button--info:hover {
  background: linear-gradient(135deg, rgba(100, 255, 218, 0.3) 0%, rgba(0, 191, 165, 0.3) 100%);
  border-color: rgba(100, 255, 218, 0.8);
  box-shadow: 0 0 20px rgba(100, 255, 218, 0.3);
}

.tech-button.is-plain {
  background: transparent;
}

.tech-button.is-round {
  border-radius: 20px;
}

.tech-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.tech-button.is-loading {
  pointer-events: none;
}

.loading-icon {
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}
</style>
