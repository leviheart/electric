<template>
  <div class="tech-page">
    <div class="page-header" v-if="title">
      <div class="header-decoration"></div>
      <h2 class="page-title">{{ title }}</h2>
      <div class="header-actions">
        <slot name="header-actions"></slot>
      </div>
    </div>
    <div class="page-content">
      <slot></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  title?: string
}>()
</script>

<style scoped>
.tech-page {
  height: 100%;
  padding: 20px;
  background: linear-gradient(180deg, rgba(5, 10, 20, 0.95) 0%, rgba(10, 15, 30, 0.95) 100%);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.page-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
  position: relative;
  flex-shrink: 0;
}

.header-decoration {
  width: 4px;
  height: 28px;
  background: linear-gradient(180deg, #00f0ff 0%, #0080ff 100%);
  border-radius: 2px;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.5);
}

.page-title {
  margin: 0;
  font-size: 22px;
  font-weight: 600;
  color: #fff;
  letter-spacing: 2px;
  flex: 1;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.page-content {
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  animation: fadeIn 0.3s ease;
}

.page-content::-webkit-scrollbar {
  width: 6px;
}

.page-content::-webkit-scrollbar-track {
  background: rgba(0, 240, 255, 0.05);
  border-radius: 3px;
}

.page-content::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 3px;
}

.page-content::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 240, 255, 0.5);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
