<template>
  <header class="app-header">
    <h1 class="app-title">电网地图系统</h1>
    <div class="map-controls">
      <el-button type="primary" @click="$emit('load')">加载数据</el-button>
      <el-button @click="$emit('clear')">清空地图</el-button>
      <el-button @click="$emit('toggle', 'substations')">显示/隐藏变电站</el-button>
      <el-button @click="$emit('toggle', 'lines')">显示/隐藏输电线路</el-button>
      <el-button @click="$emit('toggle', 'areas')">显示/隐藏台区</el-button>
      <el-button @click="$emit('animate')">地图动画</el-button>
    </div>
  </header>
</template>

<script setup lang="ts">
/**
 * AppHeader.vue - 应用头部组件
 * 
 * 功能说明：
 * 显示应用标题和控制按钮
 * 通过事件向父组件传递用户操作
 * 
 * 事件：
 * - load: 加载数据
 * - clear: 清空地图
 * - toggle: 切换图层显示/隐藏
 * - animate: 执行地图动画
 * 
 * 文件关联：
 * - App.vue: 父组件，接收并处理事件
 */
defineEmits<{
  load: []
  clear: []
  toggle: [layerType: string]
  animate: []
}>()
</script>

<style scoped>
.app-header {
  position: relative;
  z-index: 1;
  flex-shrink: 0;
}

.app-title {
  text-align: center;
  font-size: 2.5rem;
  margin-bottom: 30px;
  color: #00f0ff;
  text-shadow: 0 0 20px rgba(0, 240, 255, 0.5);
  letter-spacing: 2px;
}

.map-controls {
  display: flex;
  gap: 12px;
  justify-content: center;
  flex-wrap: wrap;
}

/* 科技感按钮样式 */
:deep(.el-button) {
  border-radius: 8px;
  padding: 12px 24px;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

:deep(.el-button--primary) {
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.5);
  color: #0a0a1a;
}

:deep(.el-button--primary:hover) {
  box-shadow: 0 0 25px rgba(0, 240, 255, 0.8);
  transform: translateY(-2px);
}

:deep(.el-button) {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  color: #00f0ff;
}

:deep(.el-button:hover) {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.3);
}

/* 响应式设计 */
@media (max-width: 1920px) {
  .app-title {
    font-size: 2rem;
  }
  
  .map-controls {
    gap: 10px;
  }
}

@media (min-width: 3840px) {
  .app-title {
    font-size: 3.5rem;
    margin-bottom: 40px;
  }
  
  .map-controls {
    margin-bottom: 30px;
  }
  
  :deep(.el-button) {
    padding: 16px 32px;
    font-size: 1.2rem;
  }
}
</style>
