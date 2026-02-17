<template>
  <div class="home-view">
    <AppHeader
      @load="handleLoad"
      @clear="handleClear"
      @toggle="handleToggle"
      @animate="handleAnimate"
    />
    
    <div class="search-section">
      <SearchBox
        :show-filters="true"
        @select="handleSearchSelect"
        @clear="handleSearchClear"
      />
    </div>
    
    <StatsPanel :stats="mapStore.stats" />
    
    <MapContainer
      ref="mapRef"
      :access-token="mapboxToken"
      @stats-update="handleStatsUpdate"
    />
    
    <AlertPanel @locate="handleAlertLocate" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import AppHeader from '../components/AppHeader.vue'
import StatsPanel from '../components/StatsPanel.vue'
import MapContainer from '../components/MapContainer.vue'
import SearchBox from '../components/SearchBox.vue'
import AlertPanel from '../components/AlertPanel.vue'
import { useMapStore } from '../stores/mapStore'
import { useAlertStore } from '../stores/alertStore'
import type { Substation, TransmissionLine, Area } from '../types'
import type { Alert } from '../types/alert'

const mapStore = useMapStore()
const alertStore = useAlertStore()
const mapboxToken = import.meta.env.VITE_MAPBOX_TOKEN || ''
const mapRef = ref<InstanceType<typeof MapContainer> | null>(null)

let ws: WebSocket | null = null
let reconnectTimer: ReturnType<typeof setInterval> | null = null

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

const handleSearchSelect = (result: { type: string; data: Substation | TransmissionLine | Area }) => {
  const data = result.data as any
  if (data.latitude && data.longitude) {
    mapRef.value?.flyTo(data.longitude, data.latitude, 16)
  }
}

const handleSearchClear = () => {
  console.log('搜索已清空')
}

const handleAlertLocate = (alert: Alert) => {
  if (alert.latitude && alert.longitude) {
    mapRef.value?.flyTo(alert.longitude, alert.latitude, 16)
  }
}

const connectWebSocket = () => {
  const wsUrl = `${location.protocol === 'https:' ? 'wss:' : 'ws:'}//${location.hostname}:8080/ws`
  
  try {
    ws = new WebSocket(wsUrl)
    
    ws.onopen = () => {
      console.log('WebSocket 连接成功')
      alertStore.setConnected(true)
    }
    
    ws.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data)
        if (data.type === 'alert') {
          alertStore.addAlert(data.payload)
        } else if (data.type === 'update') {
          alertStore.updateAlert(data.payload)
        } else if (data.type === 'delete') {
          alertStore.removeAlertById(data.payload)
        }
      } catch (e) {
        console.log('WebSocket 消息解析失败:', e)
      }
    }
    
    ws.onclose = () => {
      console.log('WebSocket 连接关闭')
      alertStore.setConnected(false)
      scheduleReconnect()
    }
    
    ws.onerror = (error) => {
      console.error('WebSocket 错误:', error)
    }
  } catch (e) {
    console.error('WebSocket 连接失败:', e)
    scheduleReconnect()
  }
}

const scheduleReconnect = () => {
  if (reconnectTimer) return
  reconnectTimer = setInterval(() => {
    console.log('尝试重新连接 WebSocket...')
    connectWebSocket()
  }, 5000)
}

const disconnectWebSocket = () => {
  if (reconnectTimer) {
    clearInterval(reconnectTimer)
    reconnectTimer = null
  }
  if (ws) {
    ws.close()
    ws = null
  }
}

onMounted(() => {
  alertStore.fetchAlerts()
  alertStore.fetchStats()
  connectWebSocket()
})

onUnmounted(() => {
  disconnectWebSocket()
})
</script>

<style scoped>
.home-view {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.search-section {
  display: flex;
  justify-content: center;
  flex-shrink: 0;
}
</style>
