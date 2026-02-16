<template>
  <div class="home-view">
    <AppHeader
      @load="handleLoad"
      @clear="handleClear"
      @toggle="handleToggle"
      @animate="handleAnimate"
    />
    <StatsPanel :stats="mapStore.stats" />
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
 * 地图主页，展示电网设备和交互功能
 * 
 * 文件关联：
 * - components/*.vue: 子组件
 * - stores/mapStore.ts: 状态管理
 */

import { ref } from 'vue'
import AppHeader from '../components/AppHeader.vue'
import StatsPanel from '../components/StatsPanel.vue'
import MapContainer from '../components/MapContainer.vue'
import { useMapStore } from '../stores/mapStore'

const mapStore = useMapStore()

const mapboxToken = import.meta.env.VITE_MAPBOX_TOKEN || ''

const mapRef = ref<InstanceType<typeof MapContainer> | null>(null)

const handleLoad = () => {
  mapRef.value?.loadData()
}

const handleClear = () => {
  mapRef.value?.clearMap()
  mapStore.resetStats()
}

const handleToggle = (layerType: 'substations' | 'lines' | 'areas') => {
  mapStore.toggleLayerVisibility(layerType)
  mapRef.value?.toggleLayer(layerType)
}

const handleAnimate = () => {
  mapRef.value?.animateMap()
}

const handleStatsUpdate = (newStats: { substations: number; lines: number; areas: number }) => {
  mapStore.updateStats(newStats)
}
</script>

<style scoped>
.home-view {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 15px;
}
</style>
