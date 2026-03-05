<template>
  <div class="home-view">
    <div class="main-content">
      <div class="top-bar">
        <RegionSelector
          ref="regionSelectorRef"
          @change="handleRegionChange"
        />
        <PageHelp
          page-key="home"
          page-title="电网地图"
          :help-content="homeHelpContent"
          :tips="homeHelpTips"
        />
      </div>
      
      <ControlPanel
        :layer-visibility="layerVisibility"
        @load="handleLoad"
        @clear="handleClear"
        @toggle="handleToggle"
        @animate="handleAnimate"
      />
      
      <div class="map-wrapper">
        <MapContainer
          ref="mapRef"
          :access-token="mapboxToken"
          @stats-update="handleStatsUpdate"
          @show-detail="handleShowDetail"
          @show-chart="handleShowChart"
        />
      </div>
      
      <StatsPanel 
        :stats="mapStore.stats" 
        :alert-count="alertStore.alerts.filter(a => a.status === 'ACTIVE').length"
        :avg-load-rate="mapStore.extendedStats.avgLoadRate"
        :total-capacity="mapStore.extendedStats.totalCapacity"
        :total-customers="mapStore.extendedStats.totalCustomers"
      />
      
      <DetailPanel
        v-model="showDetailPanel"
        :entity-type="detailEntityType"
        :entity-data="detailEntityData"
        @show-chart="handleShowChartFromDetail"
        @show-topology="handleShowTopology"
      />
      
      <ChartPanel
        v-model="showChartPanel"
        :entity-type="chartEntityType"
        :entity-id="chartEntityId"
        :entity-name="chartEntityName"
      />
      
      <TopologyPanel
        :visible="showTopologyPanel"
        :topology-data="topologyData"
        @close="showTopologyPanel = false"
        @locate="handleTopologyLocate"
        @select="handleTopologySelect"
        @analyze="handleTopologyAnalyze"
      />
      
      <QuickEntry />
      
      <NaturalDisasterPanel 
        v-show="showDisasterPanel"
        @locate="handleDisasterLocate"
        @clear="handleDisasterClear"
        @close="handleDisasterClose"
        @update-map="handleDisasterUpdate"
      />
    </div>
    
    <GuideOverlay 
      v-model:visible="showGuide" 
      @finish="handleGuideFinish"
      @skip="handleGuideSkip"
    />
    
    <KeyboardShortcuts 
      ref="keyboardRef"
      @toggle-guide="showGuide = true"
      @toggle-state-switcher="toggleStateSwitcher"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted, watch, provide } from 'vue'
import { useRoute } from 'vue-router'
import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'
import StatsPanel from '../components/StatsPanel.vue'
import MapContainer from '../components/MapContainer.vue'
import ControlPanel from '../components/ControlPanel.vue'
import DetailPanel from '../components/DetailPanel.vue'
import ChartPanel from '../components/ChartPanel.vue'
import TopologyPanel from '../components/TopologyPanel.vue'
import RegionSelector from '../components/RegionSelector.vue'
import QuickEntry from '../components/QuickEntry.vue'
import GuideOverlay from '../components/common/GuideOverlay.vue'
import PageHelp from '../components/common/PageHelp.vue'
import KeyboardShortcuts from '../components/common/KeyboardShortcuts.vue'
import NaturalDisasterPanel from '../components/NaturalDisasterPanel.vue'
import { useMapStore } from '../stores/mapStore'
import { useAlertStore } from '../stores/alertStore'
import { useSearchStore } from '../stores/searchStore'
import { useFilterStore } from '../stores/filterStore'
import { getDeviceStats } from '../api/stats'
import { getTopology } from '../api/topology'
import type { Alert } from '../types/alert'
import type { Region } from '../data/regions'
import type { TopologyData } from '../api/topology'

const mapStore = useMapStore()
const alertStore = useAlertStore()
const searchStore = useSearchStore()
const filterStore = useFilterStore()
const route = useRoute()
const mapboxToken = import.meta.env.VITE_MAPBOX_TOKEN || ''
const mapRef = ref<InstanceType<typeof MapContainer> | null>(null)
const regionSelectorRef = ref<InstanceType<typeof RegionSelector> | null>(null)
const keyboardRef = ref<InstanceType<typeof KeyboardShortcuts> | null>(null)

const toggleStateSwitcher = () => {
  mapRef.value?.toggleStateSwitcher()
}

provide('toggleStateSwitcher', toggleStateSwitcher)

const showDisasterPanel = ref(false)
const toggleDisasterPanel = () => {
  if (showDisasterPanel.value) {
    handleDisasterClose()
  } else {
    showDisasterPanel.value = true
  }
}
const closeDisasterPanel = () => {
  handleDisasterClose()
}
provide('toggleDisasterPanel', toggleDisasterPanel)
provide('closeDisasterPanel', closeDisasterPanel)

const layerVisibility = reactive({
  substations: true,
  lines: true,
  areas: true
})

const showDetailPanel = ref(false)
const detailEntityType = ref('')
const detailEntityData = ref<any>(null)

const showGuide = ref(false)

watch(showGuide, (newVal) => {
  console.log('showGuide 变化:', newVal)
})

const homeHelpContent = [
  {
    icon: '🗺️',
    title: '地图操作',
    items: [
      '点击设备图标查看详细信息',
      '使用鼠标滚轮缩放地图',
      '拖拽移动地图位置',
      '双击设备快速定位'
    ]
  },
  {
    icon: '📊',
    title: '图层控制',
    items: [
      '左侧面板控制图层显示',
      '可切换变电站、线路、台区图层',
      '支持筛选电压等级和状态'
    ]
  },
  {
    icon: '📈',
    title: '数据查看',
    items: [
      '右侧面板显示统计数据',
      '点击设备查看运行曲线',
      '支持查看设备拓扑关系'
    ]
  }
]

const homeHelpTips = [
  '首次使用建议完成新手引导',
  '使用功能导航可快速访问各模块',
  '告警信息会实时推送到地图上'
]

const showChartPanel = ref(false)
const chartEntityType = ref('')
const chartEntityId = ref(0)
const chartEntityName = ref('')

const showTopologyPanel = ref(false)
const topologyData = ref<TopologyData | null>(null)

let stompClient: Client | null = null
let reconnectTimer: ReturnType<typeof setInterval> | null = null

const handleLoad = () => {
  mapRef.value?.loadData()
}

const handleClear = () => {
  mapRef.value?.clearMap()
  mapStore.resetStats()
}

const handleToggle = (layerType: 'substations' | 'lines' | 'areas') => {
  layerVisibility[layerType] = !layerVisibility[layerType]
  mapStore.toggleLayerVisibility(layerType)
  mapRef.value?.toggleLayer(layerType)
}

const handleAnimate = () => {
  mapRef.value?.animateMap()
}

const handleStatsUpdate = (newStats: { substations: number; lines: number; areas: number }) => {
  mapStore.updateStats(newStats)
}

const handleAlertLocate = (alert: Alert) => {
  if (alert.latitude && alert.longitude) {
    mapRef.value?.flyTo(alert.longitude, alert.latitude, 16)
  }
}

const handleShowDetail = (data: any) => {
  detailEntityType.value = data.type
  detailEntityData.value = data
  showDetailPanel.value = true
  showChartPanel.value = false
}

const handleShowChart = (data: { type: string; id: number; name: string }) => {
  chartEntityType.value = data.type
  chartEntityId.value = data.id
  chartEntityName.value = data.name
  showChartPanel.value = true
  showDetailPanel.value = false
}

const handleShowChartFromDetail = (entityType: string, entityId: number, entityName: string) => {
  chartEntityType.value = entityType
  chartEntityId.value = entityId
  chartEntityName.value = entityName
  showChartPanel.value = true
  showDetailPanel.value = false
}

const handleRegionChange = (region: Region) => {
  mapRef.value?.flyToRegion(region.center, region.zoom, region.adcode)
}

const handleTopologyLocate = (device: any) => {
  if (device.latitude && device.longitude) {
    mapRef.value?.flyTo(device.longitude, device.latitude, 16)
  }
}

const handleTopologySelect = async (type: string, id: number) => {
  try {
    const res = await getTopology(type, id)
    topologyData.value = res.data
  } catch (e) {
    console.error('获取拓扑数据失败', e)
  }
}

const handleTopologyAnalyze = (type: string, id: number) => {
  console.log('故障影响分析:', type, id)
}

const handleShowTopology = async (entityType: string, entityId: number) => {
  try {
    const res = await getTopology(entityType, entityId)
    topologyData.value = res.data
    showTopologyPanel.value = true
    showDetailPanel.value = false
  } catch (e) {
    console.error('获取拓扑数据失败', e)
  }
}

watch(() => alertStore.alerts, (alerts) => {
  mapRef.value?.updateAlerts(alerts)
}, { deep: true })

const connectWebSocket = () => {
  if (stompClient?.connected) return
  
  try {
    stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws', null, { withCredentials: false }),
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('WebSocket 连接成功')
        alertStore.setConnected(true)
        
        stompClient?.subscribe('/topic/alerts', (message) => {
          try {
            const data = JSON.parse(message.body)
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
        })
      },
      onDisconnect: () => {
        console.log('WebSocket 连接关闭')
        alertStore.setConnected(false)
      },
      onStompError: (frame) => {
        console.error('STOMP 错误:', frame)
      }
    })
    
    stompClient.activate()
  } catch (e) {
    console.error('WebSocket 连接失败:', e)
  }
}

const disconnectWebSocket = () => {
  if (reconnectTimer) {
    clearInterval(reconnectTimer)
    reconnectTimer = null
  }
  if (stompClient) {
    stompClient.deactivate()
    stompClient = null
  }
}

onMounted(() => {
  alertStore.fetchAlerts()
  alertStore.fetchStats()
  connectWebSocket()
  loadExtendedStats()
  checkGuideStatus()
  handleDisasterFromQuery()
})

const handleDisasterFromQuery = () => {
  const disasterQuery = route.query.disaster as string
  if (disasterQuery) {
    try {
      const disaster = JSON.parse(decodeURIComponent(disasterQuery))
      setTimeout(() => {
        if (mapRef.value) {
          mapRef.value.flyTo(disaster.longitude, disaster.latitude, 13)
          mapRef.value.updateDisasters([disaster])
        }
      }, 1000)
    } catch (e) {
      console.error('解析灾害数据失败:', e)
    }
  }
}

const checkGuideStatus = () => {
  const justLoggedIn = sessionStorage.getItem('just_logged_in')
  console.log('checkGuideStatus - justLoggedIn:', justLoggedIn)
  
  if (justLoggedIn === 'true') {
    sessionStorage.removeItem('just_logged_in')
    console.log('显示引导...')
    setTimeout(() => {
      showGuide.value = true
      console.log('showGuide 设置为 true')
    }, 1000)
  }
}

const handleGuideFinish = () => {
  console.log('引导完成')
}

const handleGuideSkip = () => {
  console.log('跳过引导')
}

const handleDisasterLocate = (disaster: any) => {
  if (mapRef.value) {
    mapRef.value.flyTo(disaster.longitude, disaster.latitude, 13)
    mapRef.value.updateDisasters([disaster])
  }
}

const handleDisasterUpdate = (disasters: any[]) => {
  if (mapRef.value) {
    mapRef.value.updateDisasters(disasters)
  }
}

const handleDisasterClear = () => {
  if (mapRef.value) {
    mapRef.value.updateDisasters([])
  }
}

const handleDisasterClose = () => {
  showDisasterPanel.value = false
  if (mapRef.value) {
    mapRef.value.updateDisasters([])
  }
}

const loadExtendedStats = async () => {
  try {
    const res = await getDeviceStats()
    const data = res.data
    mapStore.updateExtendedStats({
      avgLoadRate: data.avgLoadRate || 0,
      totalCapacity: data.totalCapacity || 0,
      totalCustomers: data.totalCustomers || 0
    })
  } catch (e) {
    console.error('加载扩展统计失败', e)
  }
}

onUnmounted(() => {
  disconnectWebSocket()
})

watch(() => searchStore.selectedResult, (result) => {
  if (result) {
    const data = result.data as any
    if (data.latitude && data.longitude) {
      mapRef.value?.flyTo(data.longitude, data.latitude, 16)
    }
  }
})

watch(() => [filterStore.voltageLevel, filterStore.status], ([voltageLevel, status]) => {
  mapRef.value?.filterData(voltageLevel, status)
})
</script>

<style scoped>
.home-view {
  width: 100%;
  height: 100%;
  position: relative;
}

.main-content {
  width: 100%;
  height: 100%;
  position: relative;
}

.top-bar {
  position: absolute;
  top: 10px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 100;
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 8px 16px;
  background: rgba(10, 10, 26, 0.8);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 8px;
  backdrop-filter: blur(10px);
}

.map-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
</style>
