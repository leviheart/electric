<template>
  <!--
    头部容器
    - 包含标题和控制按钮
    - 相对定位，z-index 确保在地图上层
  -->
  <header class="app-header">
    <!--
      系统标题
      - 青色发光文字效果
      - 居中显示
    -->
    <h1 class="app-title">电网地图系统</h1>
    
    <!--
      控制按钮区域
      - 水平排列，居中显示
      - 自动换行（flex-wrap）
    -->
    <div class="map-controls">
      <!--
        加载数据按钮
        - type="primary": 主要按钮样式（渐变蓝色）
        - @click: 点击时触发 load 事件
      -->
      <el-button type="primary" @click="$emit('load')">加载数据</el-button>
      
      <!--
        清空地图按钮
        - 默认样式（透明背景）
        - @click: 点击时触发 clear 事件
      -->
      <el-button @click="$emit('clear')">清空地图</el-button>
      
      <!--
        变电站图层切换按钮
        - @click: 点击时触发 toggle 事件，传入 'substations'
      -->
      <el-button @click="$emit('toggle', 'substations')">显示/隐藏变电站</el-button>
      
      <!--
        输电线路图层切换按钮
        - @click: 点击时触发 toggle 事件，传入 'lines'
      -->
      <el-button @click="$emit('toggle', 'lines')">显示/隐藏输电线路</el-button>
      
      <!--
        台区图层切换按钮
        - @click: 点击时触发 toggle 事件，传入 'areas'
      -->
      <el-button @click="$emit('toggle', 'areas')">显示/隐藏台区</el-button>
      
      <!--
        地图动画按钮
        - @click: 点击时触发 animate 事件
      -->
      <el-button @click="$emit('animate')">地图动画</el-button>
    </div>
  </header>
</template>

<script setup lang="ts">
/**
 * AppHeader.vue - 应用头部组件
 * 
 * 功能说明：
 * 显示应用标题和控制按钮，提供以下操作：
 * 1. 加载数据：从后端获取变电站、线路、台区数据
 * 2. 清空地图：清除地图上的所有标记和图层
 * 3. 图层切换：显示/隐藏变电站、线路、台区图层
 * 4. 地图动画：执行地图飞行动画效果
 * 
 * 组件通信：
 * - 使用 emit 向父组件传递事件
 * - 父组件（HomeView）处理具体业务逻辑
 * 
 * 设计模式：
 * - 展示组件（Dumb Component）：只负责 UI 展示
 * - 业务逻辑由父组件处理
 * 
 * 文件关联：
 * - views/HomeView.vue: 父组件，接收并处理事件
 * - components/MapContainer.vue: 实际执行地图操作
 */

// ==================== 事件定义 ====================

/**
 * 定义组件事件
 * 
 * 使用 TypeScript 泛型定义事件类型，确保类型安全
 * 
 * 事件列表：
 * - load: 加载数据事件，无参数
 * - clear: 清空地图事件，无参数
 * - toggle: 图层切换事件，参数为图层类型
 * - animate: 地图动画事件，无参数
 * 
 * 使用方式：
 * - 在模板中：$emit('load') 或 $emit('toggle', 'substations')
 * - 在父组件中：@load="handleLoad" 或 @toggle="handleToggle"
 */
defineEmits<{
  /** 加载数据事件 */
  load: []
  /** 清空地图事件 */
  clear: []
  /** 图层切换事件，参数为图层类型 */
  toggle: [layerType: string]
  /** 地图动画事件 */
  animate: []
}>()
</script>

<style scoped>
/*
 * 头部容器样式
 * - 相对定位，确保在地图上层
 * - 不压缩（flex-shrink: 0）
 */
.app-header {
  position: relative;
  z-index: 1;
  flex-shrink: 0;
}

/*
 * 系统标题样式
 * - 居中显示
 * - 青色发光效果
 * - 字母间距 2px
 */
.app-title {
  text-align: center;
  font-size: 2.5rem;
  margin-bottom: 30px;
  color: #00f0ff;
  text-shadow: 0 0 20px rgba(0, 240, 255, 0.5);
  letter-spacing: 2px;
}

/*
 * 控制按钮区域样式
 * - 水平排列
 * - 居中对齐
 * - 按钮间距 12px
 * - 自动换行
 */
.map-controls {
  display: flex;
  gap: 12px;
  justify-content: center;
  flex-wrap: wrap;
}

/*
 * Element Plus 按钮样式覆盖
 * - 圆角边框
 * - 内边距
 * - 过渡动画
 */
:deep(.el-button) {
  border-radius: 8px;
  padding: 12px 24px;
  font-size: 1rem;
  font-weight: 600;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

/*
 * 主要按钮样式
 * - 渐变背景
 * - 发光阴影
 */
:deep(.el-button--primary) {
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.5);
  color: #0a0a1a;
}

/*
 * 主要按钮悬停效果
 * - 增强发光
 * - 上移 2px
 */
:deep(.el-button--primary:hover) {
  box-shadow: 0 0 25px rgba(0, 240, 255, 0.8);
  transform: translateY(-2px);
}

/*
 * 普通按钮样式
 * - 半透明背景
 * - 发光边框
 */
:deep(.el-button) {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  color: #00f0ff;
}

/*
 * 普通按钮悬停效果
 * - 背景变亮
 * - 边框发光
 */
:deep(.el-button:hover) {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.3);
}

/*
 * 响应式设计 - 小屏幕
 * - 缩小标题字号
 * - 减小按钮间距
 */
@media (max-width: 1920px) {
  .app-title {
    font-size: 2rem;
  }
  
  .map-controls {
    gap: 10px;
  }
}

/*
 * 响应式设计 - 大屏幕（4K）
 * - 放大标题字号
 * - 增大按钮尺寸
 */
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
