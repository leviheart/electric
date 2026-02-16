<template>
  <!--
    首页视图容器
    - 使用 flex 布局，垂直排列子组件
    - 全屏高度，组件间有间距
  -->
  <div class="home-view">
    <!--
      头部控制组件
      - 提供加载、清除、切换图层、动画等控制按钮
      - @load: 加载数据按钮点击事件
      - @clear: 清除数据按钮点击事件
      - @toggle: 图层切换事件
      - @animate: 动画按钮点击事件
    -->
    <AppHeader
      @load="handleLoad"
      @clear="handleClear"
      @toggle="handleToggle"
      @animate="handleAnimate"
    />
    
    <!--
      统计面板组件
      - 显示变电站、线路、台区数量统计
      - :stats="mapStore.stats": 传入统计数据
    -->
    <StatsPanel :stats="mapStore.stats" />
    
    <!--
      地图容器组件
      - 核心组件，显示 Mapbox 地图
      - ref="mapRef": 获取组件实例，调用子组件方法
      - :access-token: Mapbox 访问令牌
      - @stats-update: 数据加载完成后更新统计
    -->
    <MapContainer
      ref="mapRef"
      :access-token="mapboxToken"
      @stats-update="handleStatsUpdate"
    />
  </div>
</template>

<script setup lang="ts">
/**
 * HomeView.vue - 首页视图
 * 
 * 功能说明：
 * 地图主页，作为系统的核心页面，整合以下功能：
 * 1. 展示 Mapbox 地图
 * 2. 提供数据加载、清除控制
 * 3. 提供图层显示/隐藏切换
 * 4. 显示数据统计信息
 * 5. 提供地图动画效果
 * 
 * 组件结构：
 * - AppHeader: 头部控制按钮
 * - StatsPanel: 数据统计面板
 * - MapContainer: 地图容器（核心组件）
 * 
 * 数据流向：
 * 1. 用户点击按钮 -> 触发事件处理方法
 * 2. 方法调用 MapContainer 的方法 -> 执行地图操作
 * 3. MapContainer 加载数据 -> 触发 stats-update 事件
 * 4. HomeView 更新 mapStore -> StatsPanel 显示最新统计
 * 
 * 文件关联：
 * - components/AppHeader.vue: 头部控制组件
 * - components/StatsPanel.vue: 统计面板组件
 * - components/MapContainer.vue: 地图容器组件
 * - stores/mapStore.ts: 地图状态管理
 */

// ==================== 导入依赖 ====================

/**
 * Vue 响应式 API
 * - ref: 创建响应式引用，用于获取子组件实例
 */
import { ref } from 'vue'

/**
 * 子组件导入
 * - AppHeader: 头部控制按钮组件
 * - StatsPanel: 数据统计面板组件
 * - MapContainer: 地图容器组件
 */
import AppHeader from '../components/AppHeader.vue'
import StatsPanel from '../components/StatsPanel.vue'
import MapContainer from '../components/MapContainer.vue'

/**
 * 状态管理
 * - useMapStore: 地图状态管理 Store
 */
import { useMapStore } from '../stores/mapStore'

// ==================== 初始化 ====================

/**
 * 地图状态管理实例
 * 用于存储和更新统计数据、图层可见性等
 */
const mapStore = useMapStore()

/**
 * Mapbox 访问令牌
 * - 从环境变量 VITE_MAPBOX_TOKEN 读取
 * - 如果未配置则使用空字符串（地图将无法加载）
 */
const mapboxToken = import.meta.env.VITE_MAPBOX_TOKEN || ''

// ==================== 响应式数据 ====================

/**
 * 地图组件实例引用
 * - 类型: MapContainer 组件实例或 null
 * - 用途: 调用子组件的方法（loadData, clearMap, toggleLayer, animateMap）
 * - 使用: mapRef.value?.loadData()
 */
const mapRef = ref<InstanceType<typeof MapContainer> | null>(null)

// ==================== 事件处理方法 ====================

/**
 * 处理加载数据事件
 * 
 * 当用户点击"加载数据"按钮时触发
 * 调用 MapContainer 的 loadData 方法，从后端获取数据并渲染到地图
 */
const handleLoad = () => {
  // 使用可选链操作符，防止 mapRef.value 为 null 时报错
  mapRef.value?.loadData()
}

/**
 * 处理清除数据事件
 * 
 * 当用户点击"清除数据"按钮时触发
 * 1. 调用 MapContainer 的 clearMap 方法，清除地图上的所有标记和图层
 * 2. 重置统计数据为 0
 */
const handleClear = () => {
  // 清除地图上的所有数据
  mapRef.value?.clearMap()
  // 重置统计数据
  mapStore.resetStats()
}

/**
 * 处理图层切换事件
 * 
 * 当用户点击图层切换按钮时触发
 * 同时更新 store 和地图组件的图层可见性
 * 
 * @param layerType - 图层类型
 *   - 'substations': 变电站图层
 *   - 'lines': 输电线路图层
 *   - 'areas': 台区图层
 */
const handleToggle = (layerType: 'substations' | 'lines' | 'areas') => {
  // 更新 store 中的图层可见性状态
  mapStore.toggleLayerVisibility(layerType)
  // 调用地图组件的图层切换方法
  mapRef.value?.toggleLayer(layerType)
}

/**
 * 处理动画事件
 * 
 * 当用户点击"动画"按钮时触发
 * 调用 MapContainer 的 animateMap 方法，执行地图动画效果
 */
const handleAnimate = () => {
  mapRef.value?.animateMap()
}

/**
 * 处理统计更新事件
 * 
 * 当 MapContainer 加载数据完成后触发
 * 更新 store 中的统计数据，StatsPanel 会自动响应更新
 * 
 * @param newStats - 新的统计数据
 *   - substations: 变电站数量
 *   - lines: 输电线路数量
 *   - areas: 台区数量
 */
const handleStatsUpdate = (newStats: { substations: number; lines: number; areas: number }) => {
  // 更新 store 中的统计数据
  mapStore.updateStats(newStats)
}
</script>

<style scoped>
/*
 * 首页视图容器样式
 * - 全宽全高
 * - flex 布局，垂直方向排列
 * - 子组件间距 15px
 */
.home-view {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 15px;
}
</style>
