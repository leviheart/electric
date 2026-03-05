<template>
  <div id="map" class="map-container"></div>
  
  <DisasterEffects 
    v-if="mapReady && activeDisasters.length > 0" 
    :disasters="activeDisasters" 
    :map="map" 
  />
  
  <transition name="slide-fade">
    <div class="state-switcher" v-show="showStateSwitcher">
      <div class="state-header">
        <span class="state-title">一图四态</span>
        <el-button text size="small" class="compare-btn" @click="showCompare = !showCompare">
          <el-icon :size="14"><DataLine /></el-icon>
          <span>对比</span>
        </el-button>
      </div>
      <div class="state-buttons">
        <button 
          v-for="state in gridStates" 
          :key="state.id"
          class="state-btn"
          :class="{ active: currentState === state.id }"
          @click="switchState(state.id)"
        >
          <span class="state-icon">{{ state.icon }}</span>
          <span class="state-name">{{ state.name }}</span>
        </button>
      </div>
      
      <transition name="slide-fade">
        <div class="state-time-selector" v-if="currentState === 'history' || currentState === 'planned'">
          <div class="time-label">{{ currentState === 'history' ? '选择历史时间' : '选择计划时间' }}</div>
          <div class="time-options">
            <button 
              v-for="option in timeOptions" 
              :key="option.value"
              class="time-btn"
              :class="{ active: selectedTime === option.value }"
              @click="selectedTime = option.value"
            >
              {{ option.label }}
            </button>
          </div>
        </div>
      </transition>
      
      <transition name="slide-fade">
        <div class="compare-panel" v-if="showCompare">
          <div class="compare-header">
            <span>状态对比</span>
          </div>
          <div class="compare-options">
            <div class="compare-item">
              <span class="compare-label">对比状态</span>
              <el-select v-model="compareState" size="small" placeholder="选择对比状态">
                <el-option v-for="s in gridStates" :key="s.id" :label="s.name" :value="s.id" />
              </el-select>
            </div>
            <div class="compare-result" v-if="compareState !== currentState">
              <div class="result-item">
                <span class="result-label">设备变化</span>
                <span class="result-value">{{ Math.floor(Math.random() * 10) + 1 }} 处</span>
              </div>
              <div class="result-item">
                <span class="result-label">负荷差异</span>
                <span class="result-value">{{ (Math.random() * 100).toFixed(1) }} MW</span>
              </div>
            </div>
          </div>
        </div>
      </transition>
      
      <div class="state-info">
        <div class="current-state">
          <span class="state-label">当前状态：</span>
          <span class="state-value">{{ currentStateName }}</span>
        </div>
        <div class="state-desc">{{ currentStateDesc }}</div>
        <div class="state-stats" v-if="currentState === 'current'">
          <div class="stat-item">
            <span class="stat-dot online"></span>
            <span>在线设备 {{ onlineCount }}</span>
          </div>
          <div class="stat-item">
            <span class="stat-dot warning"></span>
            <span>告警 {{ alertCount }}</span>
          </div>
        </div>
      </div>
    </div>
  </transition>
  
  <div class="map-legend" v-show="showLegend">
    <div class="legend-header">
      <span class="legend-title">图例说明</span>
      <button class="legend-close" @click="showLegend = false">×</button>
    </div>
    <div class="legend-content">
      <div class="legend-section">
        <div class="legend-section-title">台区设备</div>
        <div class="legend-item">
          <span class="legend-emoji">⚡</span>
          <span>配电变压器</span>
        </div>
        <div class="legend-item">
          <span class="legend-emoji">📦</span>
          <span>JP柜</span>
        </div>
        <div class="legend-item">
          <span class="legend-emoji">🔷</span>
          <span>无功补偿装置</span>
        </div>
        <div class="legend-item">
          <span class="legend-emoji">▣</span>
          <span>配电箱</span>
        </div>
        <div class="legend-item">
          <span class="legend-emoji">⬡</span>
          <span>电表箱</span>
        </div>
      </div>
      <div class="legend-section">
        <div class="legend-section-title">配电线路</div>
        <div class="legend-item">
          <div class="legend-line main-line"></div>
          <span>低压主干线</span>
        </div>
        <div class="legend-item">
          <div class="legend-line branch-line"></div>
          <span>分支线路</span>
        </div>
      </div>
      <div class="legend-section">
        <div class="legend-section-title">用户类型</div>
        <div class="legend-item">
          <div class="legend-dot residential"></div>
          <span>居民用户</span>
        </div>
        <div class="legend-item">
          <div class="legend-dot commercial"></div>
          <span>商业用户</span>
        </div>
        <div class="legend-item">
          <div class="legend-dot industrial"></div>
          <span>工业用户</span>
        </div>
        <div class="legend-item">
          <div class="legend-dot important"></div>
          <span>重要用户</span>
        </div>
      </div>
      <div class="legend-section">
        <div class="legend-section-title">线路状态</div>
        <div class="legend-item">
          <div class="legend-line normal"></div>
          <span>正常运行</span>
        </div>
        <div class="legend-item">
          <div class="legend-line warning"></div>
          <span>负载偏高</span>
        </div>
        <div class="legend-item">
          <div class="legend-line fault"></div>
          <span>故障线路</span>
        </div>
      </div>
    </div>
  </div>
  <button class="legend-toggle" v-show="!showLegend" @click="showLegend = true" title="显示图例">
    <el-icon :size="18"><Operation /></el-icon>
  </button>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted, ref, computed } from 'vue'
import mapboxgl from 'mapbox-gl'
import { Operation, DataLine } from '@element-plus/icons-vue'
import DisasterEffects from './DisasterEffects.vue'
import type { Substation, TransmissionLine, Area } from '../types'
import { getAllSubstations, getAllTransmissionLines, getAllAreas, showError } from '../api'
import { useSettingsStore } from '../stores/settingsStore'
import {
  LINE_STYLE_CONFIG,
  getVoltageStyle,
  getLoadStatusStyle,
  getLineTypeStyle,
  hexToRgba as lineHexToRgba,
  ANIMATION_CONFIG
} from '../config/lineStyles'
import {
  AREA_STYLE_CONFIG,
  getAreaStatus,
  getAreaStatusStyle,
  getTransformerIcon,
  getCustomerDensityColor,
  hexToRgba as areaHexToRgba,
  TRANSFORMER_CONFIG,
  AREA_ANIMATION_CONFIG
} from '../config/areaStyles'
import {
  ParticleAnimationSystem,
  PulseAnimationSystem
} from '../utils/particleAnimation'
import {
  RippleAnimationSystem,
  AreaPulseSystem,
  AreaHighlightSystem
} from '../utils/areaAnimation'

const settingsStore = useSettingsStore()

const props = withDefaults(defineProps<{
  accessToken: string
  mapStyle?: string
  center?: [number, number]
  zoom?: number
}>(), {
  mapStyle: 'mapbox://styles/mapbox/dark-v11',
  center: () => [116.4074, 39.9042],
  zoom: 12
})

const emit = defineEmits<{
  'map-ready': []
  'stats-update': [stats: { substations: number; lines: number; areas: number }]
  'show-detail': [data: { type: string; id: number; name: string; [key: string]: any }]
  'show-chart': [data: { type: string; id: number; name: string }]
}>()

const showLegend = ref(true)
const showStateSwitcher = ref(false)
const currentState = ref('current')
const showCompare = ref(false)
const compareState = ref('planned')
const selectedTime = ref('today')
const onlineCount = ref(156)
const activeDisasters = ref<any[]>([])
const alertCount = ref(12)

const gridStates = [
  { id: 'current', name: '现状态', icon: '🔴', desc: '实时监控电网当前运行状态，显示设备实时负荷、告警信息' },
  { id: 'planned', name: '计划态', icon: '🔵', desc: '展示未来规划的建设项目和检修计划，包括停电范围和时间' },
  { id: 'simulated', name: '预想态', icon: '🟡', desc: '模拟推演故障场景，进行N-1校验和负荷转移分析' },
  { id: 'history', name: '历史态', icon: '🟢', desc: '回溯历史某一时刻的电网状态，支持事故复盘和分析' }
]

const timeOptions = computed(() => {
  if (currentState.value === 'history') {
    return [
      { label: '今天', value: 'today' },
      { label: '昨天', value: 'yesterday' },
      { label: '上周', value: 'lastWeek' },
      { label: '上月', value: 'lastMonth' }
    ]
  } else {
    return [
      { label: '本周', value: 'thisWeek' },
      { label: '下周', value: 'nextWeek' },
      { label: '本月', value: 'thisMonth' },
      { label: '下月', value: 'nextMonth' }
    ]
  }
})

const currentStateName = computed(() => {
  const state = gridStates.find(s => s.id === currentState.value)
  return state ? state.name : ''
})

const currentStateDesc = computed(() => {
  const state = gridStates.find(s => s.id === currentState.value)
  return state ? state.desc : ''
})

const switchState = (stateId: string) => {
  currentState.value = stateId
  updateMapByState(stateId)
}

const updateMapByState = (stateId: string) => {
  if (!map) return
  
  switch (stateId) {
    case 'current':
      map.setPaintProperty('areas', 'fill-opacity', 0.3)
      map.setPaintProperty('lines', 'line-opacity', 0.8)
      break
    case 'planned':
      map.setPaintProperty('areas', 'fill-opacity', 0.2)
      map.setPaintProperty('lines', 'line-opacity', 0.5)
      break
    case 'simulated':
      map.setPaintProperty('areas', 'fill-opacity', 0.25)
      map.setPaintProperty('lines', 'line-opacity', 0.6)
      break
    case 'history':
      map.setPaintProperty('areas', 'fill-opacity', 0.35)
      map.setPaintProperty('lines', 'line-opacity', 0.7)
      break
  }
}

let map: mapboxgl.Map | null = null
const mapReady = ref(false)
let currentPopup: mapboxgl.Popup | null = null
let animationFrames: number[] = []
let timeouts: ReturnType<typeof setTimeout>[] = []

let allSubstations: Substation[] = []
let allTransmissionLines: TransmissionLine[] = []
let allAreas: Area[] = []

let particleSystem: ParticleAnimationSystem | null = null
let pulseSystem: PulseAnimationSystem | null = null
let rippleSystem: RippleAnimationSystem | null = null
let areaPulseSystem: AreaPulseSystem | null = null
let areaHighlightSystem: AreaHighlightSystem | null = null
let hoveredLineId: number | null = null
let hoveredAreaId: number | null = null

const layerVisibility = {
  substations: true,
  lines: true,
  areas: true
}

onMounted(() => {
  initMap()
})

onUnmounted(() => {
  particleSystem?.stop()
  pulseSystem?.stop()
  rippleSystem?.stop()
  areaPulseSystem?.stop()
  animationFrames.forEach(id => cancelAnimationFrame(id))
  timeouts.forEach(id => clearTimeout(id))
  animationFrames = []
  timeouts = []
  if (map) {
    map.remove()
    map = null
  }
  document.removeEventListener('click', handlePopupButtonClick)
})

const handlePopupButtonClick = (e: MouseEvent) => {
  const target = e.target as HTMLElement
  const btn = target.closest('.popup-btn') as HTMLElement
  
  if (!btn) return
  
  const type = btn.dataset.type
  const id = parseInt(btn.dataset.id || '0')
  const name = btn.dataset.name || ''
  
  if (btn.classList.contains('popup-detail-btn')) {
    const data: any = { type, id, name }
    
    if (type === 'substation') {
      data.status = btn.dataset.status
      data.voltageLevel = btn.dataset.voltage
      data.latitude = parseFloat(btn.dataset.lat || '0')
      data.longitude = parseFloat(btn.dataset.lng || '0')
    } else if (type === 'line') {
      data.status = btn.dataset.status
      data.voltageLevel = btn.dataset.voltage
      data.length = parseFloat(btn.dataset.length || '0')
    } else if (type === 'area') {
      data.status = btn.dataset.status
      data.customerCount = parseInt(btn.dataset.customers || '0')
    }
    
    emit('show-detail', data)
  } else if (btn.classList.contains('popup-chart-btn')) {
    emit('show-chart', { type, id, name })
  }
}

const initMap = () => {
  mapboxgl.accessToken = props.accessToken
  
  map = new mapboxgl.Map({
    container: 'map',
    style: settingsStore.mapStyleUrl,
    center: props.center,
    zoom: settingsStore.defaultZoom,
    maxZoom: 18,
    minZoom: 1,
    antialias: true,
    fadeDuration: 500
  })
  
  map.addControl(new mapboxgl.NavigationControl({
    showCompass: true,
    showZoom: true
  }), 'top-right')
  
  map.addControl(new mapboxgl.ScaleControl({
    maxWidth: 200,
    unit: 'metric'
  }), 'bottom-left')
  
  map.on('load', () => {
    setMapLanguage()
    initLayers()
    initDisasterLayers()
    mapReady.value = true
    emit('map-ready')
    if (settingsStore.autoLoad) {
      loadData()
    }
  })
  
  document.addEventListener('click', handlePopupButtonClick)
}

const setMapLanguage = () => {
  if (!map) return
  
  const layers = map.getStyle().layers
  if (!layers) return
  
  layers.forEach((layer: any) => {
    if (layer.layout && layer.layout['text-field']) {
      map!.setLayoutProperty(layer.id, 'text-field', ['coalesce', ['get', 'name_zh-Hans'], ['get', 'name_zh'], ['get', 'name']])
    }
  })
}

const initLayers = () => {
  if (!map) return
  
  map.addSource('substations', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'substations-glow',
    type: 'circle',
    source: 'substations',
    paint: {
      'circle-color': ['match', ['get', 'voltageLevel'], 
        '1000kV', LINE_STYLE_CONFIG.voltageStyles['1000kV'].color,
        '500kV', LINE_STYLE_CONFIG.voltageStyles['500kV'].color,
        '220kV', LINE_STYLE_CONFIG.voltageStyles['220kV'].color,
        '110kV', LINE_STYLE_CONFIG.voltageStyles['110kV'].color,
        '35kV', LINE_STYLE_CONFIG.voltageStyles['35kV'].color,
        '10kV', LINE_STYLE_CONFIG.voltageStyles['10kV'].color,
        LINE_STYLE_CONFIG.voltageStyles['110kV'].color
      ],
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 8, 15, 12, 25, 16, 40],
      'circle-opacity': 0.15,
      'circle-blur': 0.5
    }
  })
  
  map.addLayer({
    id: 'substations-pulse',
    type: 'circle',
    source: 'substations',
    paint: {
      'circle-color': ['match', ['get', 'voltageLevel'], 
        '1000kV', LINE_STYLE_CONFIG.voltageStyles['1000kV'].glowColor,
        '500kV', LINE_STYLE_CONFIG.voltageStyles['500kV'].glowColor,
        '220kV', LINE_STYLE_CONFIG.voltageStyles['220kV'].glowColor,
        '110kV', LINE_STYLE_CONFIG.voltageStyles['110kV'].glowColor,
        '35kV', LINE_STYLE_CONFIG.voltageStyles['35kV'].glowColor,
        '10kV', LINE_STYLE_CONFIG.voltageStyles['10kV'].glowColor,
        LINE_STYLE_CONFIG.voltageStyles['110kV'].glowColor
      ],
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 8, 10, 12, 15, 16, 25],
      'circle-opacity': 0.3
    }
  })
  
  map.addLayer({
    id: 'substations-points',
    type: 'circle',
    source: 'substations',
    paint: {
      'circle-color': ['match', ['get', 'voltageLevel'], 
        '1000kV', LINE_STYLE_CONFIG.voltageStyles['1000kV'].coreColor,
        '500kV', LINE_STYLE_CONFIG.voltageStyles['500kV'].coreColor,
        '220kV', LINE_STYLE_CONFIG.voltageStyles['220kV'].coreColor,
        '110kV', LINE_STYLE_CONFIG.voltageStyles['110kV'].coreColor,
        '35kV', LINE_STYLE_CONFIG.voltageStyles['35kV'].coreColor,
        '10kV', LINE_STYLE_CONFIG.voltageStyles['10kV'].coreColor,
        LINE_STYLE_CONFIG.voltageStyles['110kV'].coreColor
      ],
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 8, 6, 12, 10, 16, 16],
      'circle-opacity': 0.95,
      'circle-stroke-width': 2,
      'circle-stroke-color': '#ffffff'
    }
  })
  
  map.addLayer({
    id: 'substations-icon',
    type: 'symbol',
    source: 'substations',
    layout: {
      'text-field': '⚡',
      'text-size': ['interpolate', ['linear'], ['zoom'], 8, 10, 12, 14, 16, 20],
      'text-allow-overlap': false,
      'text-ignore-placement': false
    },
    paint: {
      'text-color': '#ffffff'
    }
  })
  
  map.addSource('lines', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'lines-glow-outer',
    type: 'line',
    source: 'lines',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': ['match', ['get', 'voltageLevel'], 
        '1000kV', LINE_STYLE_CONFIG.voltageStyles['1000kV'].color,
        '500kV', LINE_STYLE_CONFIG.voltageStyles['500kV'].color,
        '220kV', LINE_STYLE_CONFIG.voltageStyles['220kV'].color,
        '110kV', LINE_STYLE_CONFIG.voltageStyles['110kV'].color,
        '35kV', LINE_STYLE_CONFIG.voltageStyles['35kV'].color,
        '10kV', LINE_STYLE_CONFIG.voltageStyles['10kV'].color,
        LINE_STYLE_CONFIG.voltageStyles['110kV'].color
      ],
      'line-width': ['interpolate', ['linear'], ['zoom'], 
        8, ['match', ['get', 'voltageLevel'], '1000kV', 12, '500kV', 10, '220kV', 8, '110kV', 6, '35kV', 4, '10kV', 3, 6],
        12, ['match', ['get', 'voltageLevel'], '1000kV', 24, '500kV', 20, '220kV', 16, '110kV', 12, '35kV', 8, '10kV', 6, 12],
        16, ['match', ['get', 'voltageLevel'], '1000kV', 40, '500kV', 32, '220kV', 24, '110kV', 18, '35kV', 12, '10kV', 9, 18]
      ],
      'line-opacity': 0.1,
      'line-blur': 8
    }
  })
  
  map.addLayer({
    id: 'lines-glow',
    type: 'line',
    source: 'lines',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': ['match', ['get', 'voltageLevel'], 
        '1000kV', LINE_STYLE_CONFIG.voltageStyles['1000kV'].glowColor,
        '500kV', LINE_STYLE_CONFIG.voltageStyles['500kV'].glowColor,
        '220kV', LINE_STYLE_CONFIG.voltageStyles['220kV'].glowColor,
        '110kV', LINE_STYLE_CONFIG.voltageStyles['110kV'].glowColor,
        '35kV', LINE_STYLE_CONFIG.voltageStyles['35kV'].glowColor,
        '10kV', LINE_STYLE_CONFIG.voltageStyles['10kV'].glowColor,
        LINE_STYLE_CONFIG.voltageStyles['110kV'].glowColor
      ],
      'line-width': ['interpolate', ['linear'], ['zoom'], 
        8, ['match', ['get', 'voltageLevel'], '1000kV', 8, '500kV', 6, '220kV', 5, '110kV', 4, '35kV', 3, '10kV', 2, 4],
        12, ['match', ['get', 'voltageLevel'], '1000kV', 16, '500kV', 12, '220kV', 10, '110kV', 8, '35kV', 6, '10kV', 4, 8],
        16, ['match', ['get', 'voltageLevel'], '1000kV', 24, '500kV', 18, '220kV', 14, '110kV', 10, '35kV', 8, '10kV', 5, 10]
      ],
      'line-opacity': 0.3,
      'line-blur': 4
    }
  })
  
  map.addLayer({
    id: 'lines',
    type: 'line',
    source: 'lines',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': ['match', ['get', 'voltageLevel'], 
        '1000kV', LINE_STYLE_CONFIG.voltageStyles['1000kV'].coreColor,
        '500kV', LINE_STYLE_CONFIG.voltageStyles['500kV'].coreColor,
        '220kV', LINE_STYLE_CONFIG.voltageStyles['220kV'].coreColor,
        '110kV', LINE_STYLE_CONFIG.voltageStyles['110kV'].coreColor,
        '35kV', LINE_STYLE_CONFIG.voltageStyles['35kV'].coreColor,
        '10kV', LINE_STYLE_CONFIG.voltageStyles['10kV'].coreColor,
        LINE_STYLE_CONFIG.voltageStyles['110kV'].coreColor
      ],
      'line-width': ['interpolate', ['linear'], ['zoom'], 
        8, ['match', ['get', 'voltageLevel'], '1000kV', 3, '500kV', 2.5, '220kV', 2, '110kV', 1.5, '35kV', 1, '10kV', 0.8, 1.5],
        12, ['match', ['get', 'voltageLevel'], '1000kV', 6, '500kV', 5, '220kV', 4, '110kV', 3, '35kV', 2, '10kV', 1.5, 3],
        16, ['match', ['get', 'voltageLevel'], '1000kV', 10, '500kV', 8, '220kV', 6, '110kV', 4, '35kV', 3, '10kV', 2, 4]
      ],
      'line-opacity': 1,
      'line-dasharray': ['match', ['get', 'lineType'], 
        'cable', ['literal', [10, 5]], 
        ['literal', []]
      ]
    }
  })
  
  map.addSource('particles-glow', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'particles-glow',
    type: 'circle',
    source: 'particles-glow',
    paint: {
      'circle-radius': ['coalesce', ['get', 'radius'], 6],
      'circle-color': ['coalesce', ['get', 'color'], '#ffffff'],
      'circle-opacity': ['coalesce', ['get', 'opacity'], 0.4],
      'circle-blur': 0.8
    }
  })
  
  map.addSource('particles', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'particles',
    type: 'circle',
    source: 'particles',
    paint: {
      'circle-radius': ['coalesce', ['get', 'radius'], 3],
      'circle-color': ['coalesce', ['get', 'color'], '#ffffff'],
      'circle-opacity': ['coalesce', ['get', 'opacity'], 0.9],
      'circle-blur': 0.2
    }
  })
  
  map.addSource('line-highlight', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'line-highlight-glow',
    type: 'line',
    source: 'line-highlight',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': '#ffffff',
      'line-width': 12,
      'line-opacity': 0.3,
      'line-blur': 6
    }
  })
  
  map.addLayer({
    id: 'line-highlight',
    type: 'line',
    source: 'line-highlight',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': '#ffffff',
      'line-width': 6,
      'line-opacity': 0.8
    }
  })
  
  map.addSource('areas', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'areas-glow',
    type: 'fill',
    source: 'areas',
    paint: {
      'fill-color': ['get', 'fillColor'],
      'fill-opacity': 0.1
    }
  })
  
  map.addLayer({
    id: 'areas',
    type: 'fill',
    source: 'areas',
    paint: {
      'fill-color': ['get', 'fillColor'],
      'fill-opacity': ['coalesce', ['get', 'fillOpacity'], 0.25]
    }
  })
  
  map.addLayer({
    id: 'areas-outline',
    type: 'line',
    source: 'areas',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': ['get', 'color'],
      'line-width': ['coalesce', ['get', 'borderWidth'], 2],
      'line-opacity': 0.8
    }
  })
  
  map.addSource('area-highlight', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'area-highlight-fill',
    type: 'fill',
    source: 'area-highlight',
    paint: {
      'fill-color': '#ffffff',
      'fill-opacity': 0.15
    }
  })
  
  map.addLayer({
    id: 'area-highlight-outline',
    type: 'line',
    source: 'area-highlight',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': '#ffffff',
      'line-width': 3,
      'line-opacity': 0.9
    }
  })
  
  map.addSource('area-selected', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'area-selected-fill',
    type: 'fill',
    source: 'area-selected',
    paint: {
      'fill-color': '#00f0ff',
      'fill-opacity': 0.2
    }
  })
  
  map.addLayer({
    id: 'area-selected-outline',
    type: 'line',
    source: 'area-selected',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': '#00f0ff',
      'line-width': 4,
      'line-opacity': 1
    }
  })
  
  map.addSource('area-ripples', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'area-ripples',
    type: 'circle',
    source: 'area-ripples',
    paint: {
      'circle-radius': ['coalesce', ['get', 'radius'], 10],
      'circle-color': ['coalesce', ['get', 'color'], '#ff0040'],
      'circle-opacity': ['coalesce', ['get', 'opacity'], 0.6],
      'circle-stroke-width': 2,
      'circle-stroke-color': ['coalesce', ['get', 'color'], '#ff0040'],
      'circle-stroke-opacity': ['coalesce', ['get', 'opacity'], 0.8],
      'circle-blur': 0.3
    }
  })
  
  map.addSource('transformers', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'transformers-glow',
    type: 'circle',
    source: 'transformers',
    paint: {
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 8, 12, 12, 20, 16, 32],
      'circle-color': ['get', 'color'],
      'circle-opacity': 0.3,
      'circle-blur': 0.8
    }
  })
  
  map.addLayer({
    id: 'transformers-pulse',
    type: 'circle',
    source: 'transformers',
    paint: {
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 8, 8, 12, 14, 16, 22],
      'circle-color': ['get', 'color'],
      'circle-opacity': 0.5
    }
  })
  
  map.addLayer({
    id: 'transformers-points',
    type: 'circle',
    source: 'transformers',
    paint: {
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 8, 5, 12, 8, 16, 12],
      'circle-color': ['get', 'color'],
      'circle-opacity': 0.95,
      'circle-stroke-width': 2,
      'circle-stroke-color': '#ffffff'
    }
  })
  
  map.addLayer({
    id: 'transformers-icon',
    type: 'symbol',
    source: 'transformers',
    layout: {
      'text-field': ['get', 'icon'],
      'text-size': ['interpolate', ['linear'], ['zoom'], 8, 10, 12, 14, 16, 18],
      'text-allow-overlap': false,
      'text-ignore-placement': false
    },
    paint: {
      'text-color': '#ffffff'
    }
  })
  
  map.addSource('low-voltage-lines', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'low-voltage-lines',
    type: 'line',
    source: 'low-voltage-lines',
    minzoom: 14,
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': ['coalesce', ['get', 'color'], '#ffc107'],
      'line-width': ['interpolate', ['linear'], ['zoom'], 14, 1.5, 16, 2.5, 18, 3.5],
      'line-opacity': ['coalesce', ['get', 'opacity'], 0.7]
    }
  })
  
  map.addSource('branch-lines', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'branch-lines',
    type: 'line',
    source: 'branch-lines',
    minzoom: 15,
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': ['coalesce', ['get', 'color'], '#ffc107'],
      'line-width': ['interpolate', ['linear'], ['zoom'], 15, 0.8, 17, 1.5, 19, 2],
      'line-opacity': ['coalesce', ['get', 'opacity'], 0.5],
      'line-dasharray': [1, 1]
    }
  })
  
  map.addSource('distribution-boxes', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'distribution-boxes',
    type: 'circle',
    source: 'distribution-boxes',
    minzoom: 15,
    paint: {
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 15, 2, 17, 4, 19, 6],
      'circle-color': ['coalesce', ['get', 'color'], '#00f0ff'],
      'circle-opacity': 0.8,
      'circle-stroke-width': 1,
      'circle-stroke-color': '#ffffff',
      'circle-stroke-opacity': 0.6
    }
  })
  
  map.addSource('area-labels', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'area-labels',
    type: 'symbol',
    source: 'area-labels',
    minzoom: 13,
    layout: {
      'text-field': ['concat', ['get', 'code'], '\n', ['get', 'name']],
      'text-size': ['interpolate', ['linear'], ['zoom'], 13, 9, 15, 11, 18, 13],
      'text-anchor': 'top',
      'text-offset': [0, 0.5],
      'text-allow-overlap': false
    },
    paint: {
      'text-color': '#ffffff',
      'text-halo-color': '#000000',
      'text-halo-width': 1.5,
      'text-halo-blur': 1
    }
  })
  
  map.addSource('equipment-layer', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'equipment-layer',
    type: 'symbol',
    source: 'equipment-layer',
    minzoom: 15,
    layout: {
      'text-field': [
        'match', ['get', 'equipmentType'],
        'transformer', '⚡',
        'jp_box', '📦',
        'capacitor', '🔷',
        'distribution_box', '▣',
        'meter_box', '⬡',
        '●'
      ],
      'text-size': ['interpolate', ['linear'], ['zoom'], 
        15, 14, 
        17, 18, 
        19, 24
      ],
      'text-allow-overlap': false,
      'text-anchor': 'center'
    },
    paint: {
      'text-color': ['coalesce', ['get', 'color'], '#00f0ff'],
      'text-halo-color': '#000000',
      'text-halo-width': 1
    }
  })
  
  map.addSource('customer-points', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'customer-points',
    type: 'circle',
    source: 'customer-points',
    minzoom: 15,
    paint: {
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 
        15, ['coalesce', ['get', 'size'], 3], 
        17, ['coalesce', ['get', 'size'], 5], 
        19, ['coalesce', ['get', 'size'], 8]
      ],
      'circle-color': ['coalesce', ['get', 'color'], '#64ffda'],
      'circle-opacity': ['coalesce', ['get', 'opacity'], 0.7],
      'circle-stroke-width': 1,
      'circle-stroke-color': '#ffffff',
      'circle-stroke-opacity': 0.5
    }
  })
  
  map.addSource('region-highlight', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'region-highlight-fill',
    type: 'fill',
    source: 'region-highlight',
    paint: {
      'fill-color': '#00f0ff',
      'fill-opacity': 0.15
    }
  })
  
  map.addLayer({
    id: 'region-highlight-line',
    type: 'line',
    source: 'region-highlight',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': '#00f0ff',
      'line-width': 3,
      'line-opacity': 0.8
    }
  })
  
  map.addSource('region-children', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'region-children-fill',
    type: 'fill',
    source: 'region-children',
    paint: {
      'fill-color': '#00f0ff',
      'fill-opacity': 0.08
    }
  })
  
  map.addLayer({
    id: 'region-children-line',
    type: 'line',
    source: 'region-children',
    layout: { 'line-join': 'round', 'line-cap': 'round' },
    paint: {
      'line-color': '#00f0ff',
      'line-width': 1.5,
      'line-opacity': 0.5
    }
  })
  
  setupMapInteractions()
  
  particleSystem = new ParticleAnimationSystem(map)
  pulseSystem = new PulseAnimationSystem(map)
  rippleSystem = new RippleAnimationSystem(map)
  areaPulseSystem = new AreaPulseSystem(map)
  areaHighlightSystem = new AreaHighlightSystem(map)
  
  initAlertLayers()
}

const initAlertLayers = () => {
  if (!map) return
  
  map.addSource('alerts', {
    type: 'geojson',
    data: { type: 'FeatureCollection', features: [] }
  })
  
  map.addLayer({
    id: 'alerts-pulse',
    type: 'circle',
    source: 'alerts',
    paint: {
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 5, 20, 10, 40, 15, 60],
      'circle-color': ['get', 'color'],
      'circle-opacity': 0.3,
      'circle-blur': 0.5
    }
  })
  
  map.addLayer({
    id: 'alerts-points',
    type: 'circle',
    source: 'alerts',
    paint: {
      'circle-radius': ['interpolate', ['linear'], ['zoom'], 5, 8, 10, 12, 15, 18],
      'circle-color': ['get', 'color'],
      'circle-opacity': 0.95,
      'circle-stroke-width': 2,
      'circle-stroke-color': '#ffffff'
    }
  })
  
  map.addLayer({
    id: 'alerts-icon',
    type: 'symbol',
    source: 'alerts',
    layout: {
      'text-field': ['get', 'icon'],
      'text-size': ['interpolate', ['linear'], ['zoom'], 5, 12, 10, 16, 15, 20],
      'text-allow-overlap': false,
      'text-ignore-placement': false
    },
    paint: {
      'text-color': '#ffffff'
    }
  })
}

const getLevelText = (level: string): string => {
  const levelMap: Record<string, string> = {
    CRITICAL: '严重',
    HIGH: '高',
    MEDIUM: '中',
    LOW: '低'
  }
  return levelMap[level] || level
}

const getTypeText = (type: string): string => {
  const typeMap: Record<string, string> = {
    DEVICE_OFFLINE: '设备离线',
    OVERLOAD: '负载过高',
    VOLTAGE_ABNORMAL: '电压异常',
    TEMPERATURE_HIGH: '温度过高',
    FAULT: '故障告警',
    MAINTENANCE: '检修提醒'
  }
  return typeMap[type] || type
}

const getAlertColor = (level: string): string => {
  const colorMap: Record<string, string> = {
    CRITICAL: '#ff0040',
    HIGH: '#ff4500',
    MEDIUM: '#ff8000',
    LOW: '#ffff00'
  }
  return colorMap[level] || '#ff8000'
}

const getAlertIcon = (type: string): string => {
  const iconMap: Record<string, string> = {
    DEVICE_OFFLINE: '🔌',
    OVERLOAD: '⚡',
    VOLTAGE_ABNORMAL: '📊',
    TEMPERATURE_HIGH: '🌡️',
    FAULT: '🔴',
    MAINTENANCE: '🔧'
  }
  return iconMap[type] || '⚠️'
}

const updateAlertsOnMap = (alerts: any[]) => {
  if (!map) return
  
  const features = alerts.map(alert => ({
    type: 'Feature',
    properties: {
      id: alert.id,
      title: alert.title,
      message: alert.message,
      level: alert.level,
      type: alert.type,
      deviceType: alert.deviceType,
      deviceId: alert.deviceId,
      deviceName: alert.deviceName,
      timestamp: alert.timestamp,
      color: getAlertColor(alert.level),
      icon: getAlertIcon(alert.type)
    },
    geometry: {
      type: 'Point',
      coordinates: [alert.longitude || 116.4, alert.latitude || 39.9]
    }
  }))
  
  const source = map.getSource('alerts') as mapboxgl.GeoJSONSource
  source?.setData({ type: 'FeatureCollection', features })
}

const setupMapInteractions = () => {
  if (!map) return

  map.on('click', (e) => {
    const features = map!.queryRenderedFeatures(e.point, {
      layers: ['substations-points', 'lines', 'transformers-points', 'areas', 'alerts-points']
    })
    
    if (features.length === 0) return
    
    const alertFeature = features.find(f => f.layer.id === 'alerts-points')
    const substationFeature = features.find(f => f.layer.id === 'substations-points')
    const lineFeature = features.find(f => f.layer.id === 'lines')
    const transformerFeature = features.find(f => f.layer.id === 'transformers-points')
    const areaFeature = features.find(f => f.layer.id === 'areas')
    
    if (alertFeature) {
      handleAlertClick(alertFeature)
    } else if (substationFeature) {
      handleSubstationClick(substationFeature)
    } else if (lineFeature) {
      handleLineClick(lineFeature, e.lngLat)
    } else if (transformerFeature) {
      handleTransformerClick(transformerFeature)
    } else if (areaFeature) {
      handleAreaClick(areaFeature, e.lngLat)
    }
  })
  
  map.on('mouseenter', 'substations-points', () => {
    map!.getCanvas().style.cursor = 'pointer'
  })
  map.on('mouseleave', 'substations-points', () => {
    map!.getCanvas().style.cursor = ''
  })
  
  map.on('mouseenter', 'lines', () => {
    map!.getCanvas().style.cursor = 'pointer'
  })
  map.on('mouseleave', 'lines', () => {
    map!.getCanvas().style.cursor = ''
    clearLineHighlight()
  })
  
  map.on('mouseenter', 'areas', (e) => {
    map!.getCanvas().style.cursor = 'pointer'
    const feature = e.features?.[0]
    if (feature) {
      const props = feature.properties
      const coordinates = JSON.parse(props.geometryJson || '[]')
      if (coordinates.length > 0) {
        areaHighlightSystem?.highlightArea(props.id, coordinates.map((p: number[]) => [p[1], p[0]]))
      }
    }
  })
  map.on('mouseleave', 'areas', () => {
    map!.getCanvas().style.cursor = ''
    areaHighlightSystem?.clearHighlight()
  })
  
  map.on('mouseenter', 'transformers-points', () => {
    map!.getCanvas().style.cursor = 'pointer'
  })
  map.on('mouseleave', 'transformers-points', () => {
    map!.getCanvas().style.cursor = ''
  })
  
  map.on('mouseenter', 'alerts-points', () => {
    map!.getCanvas().style.cursor = 'pointer'
  })
  map.on('mouseleave', 'alerts-points', () => {
    map!.getCanvas().style.cursor = ''
  })
}

const handleAlertClick = (feature: any) => {
  const props = feature.properties
  const coords = (feature.geometry as any).coordinates
  
  if (currentPopup) currentPopup.remove()
  
  currentPopup = new mapboxgl.Popup({
    offset: 25,
    className: 'custom-popup alert-popup',
    closeOnClick: true
  })
    .setLngLat(coords)
    .setHTML(`
      <div class="popup-header alert-header-${props.level}">
        <h3>⚠️ ${props.title}</h3>
        <div class="status-indicator status-${props.level}">${getLevelText(props.level)}</div>
      </div>
      <div class="popup-content">
        <div class="info-row"><span class="info-label">设备:</span><span class="info-value">${props.deviceName}</span></div>
        <div class="info-row"><span class="info-label">类型:</span><span class="info-value">${getTypeText(props.type)}</span></div>
        <div class="info-row"><span class="info-label">消息:</span><span class="info-value">${props.message}</span></div>
        <div class="info-row"><span class="info-label">时间:</span><span class="info-value">${new Date(props.timestamp).toLocaleString()}</span></div>
      </div>
      <div class="popup-actions">
        <button class="popup-btn popup-ack-btn" data-alert-id="${props.id}">确认告警</button>
        <button class="popup-btn popup-resolve-btn" data-alert-id="${props.id}">解决告警</button>
      </div>
    `)
    .addTo(map!)
}

const handleSubstationClick = (feature: any) => {
  const props = feature.properties
  const coords = (feature.geometry as any).coordinates
  
  if (currentPopup) currentPopup.remove()
  
  currentPopup = new mapboxgl.Popup({
    offset: 25,
    className: 'custom-popup',
    closeOnClick: true
  })
    .setLngLat(coords)
    .setHTML(`
      <div class="popup-header">
        <h3>${props.name}</h3>
        <div class="status-indicator ${props.status === '运行中' ? 'status-active' : 'status-inactive'}">${props.status}</div>
      </div>
      <div class="popup-content">
        <div class="info-row"><span class="info-label">ID:</span><span class="info-value">${props.id}</span></div>
        <div class="info-row"><span class="info-label">电压等级:</span><span class="info-value">${props.voltageLevel}</span></div>
        <div class="info-row"><span class="info-label">状态:</span><span class="info-value">${props.status}</span></div>
      </div>
      <div class="popup-actions">
        <button class="popup-btn popup-detail-btn" data-type="substation" data-id="${props.id}" data-name="${props.name}" data-status="${props.status}" data-voltage="${props.voltageLevel}" data-lat="${coords[1]}" data-lng="${coords[0]}">查看详情</button>
        <button class="popup-btn popup-chart-btn" data-type="substation" data-id="${props.id}" data-name="${props.name}">数据图表</button>
      </div>
      <div class="popup-footer"><small>最后更新: ${new Date().toLocaleString()}</small></div>
    `)
    .addTo(map!)
}

const handleLineClick = (feature: any, lngLat: mapboxgl.LngLat) => {
  const props = feature.properties
  const lineType = props.lineType || 'overhead'
  const loadRate = props.loadRate !== undefined ? `${props.loadRate}%` : 'N/A'
  
  if (currentPopup) currentPopup.remove()
  
  highlightLine(props.id, JSON.parse(feature.properties?.geometry || '[]'))
  
  currentPopup = new mapboxgl.Popup({
    className: 'custom-popup',
    closeOnClick: true
  })
    .setLngLat(lngLat)
    .setHTML(`
      <div class="popup-header">
        <h3>${props.name}</h3>
        <div class="status-indicator ${props.status === '运行中' ? 'status-active' : 'status-inactive'}">${props.status}</div>
      </div>
      <div class="popup-content">
        <div class="info-row"><span class="info-label">ID:</span><span class="info-value">${props.id}</span></div>
        <div class="info-row"><span class="info-label">长度:</span><span class="info-value">${props.length}km</span></div>
        <div class="info-row"><span class="info-label">电压等级:</span><span class="info-value">${props.voltageLevel}</span></div>
        <div class="info-row"><span class="info-label">线路类型:</span><span class="info-value">${lineType === 'cable' ? '电缆' : '架空线'}</span></div>
        <div class="info-row"><span class="info-label">负载率:</span><span class="info-value">${loadRate}</span></div>
      </div>
      <div class="popup-actions">
        <button class="popup-btn popup-detail-btn" data-type="line" data-id="${props.id}" data-name="${props.name}" data-status="${props.status}" data-voltage="${props.voltageLevel}" data-length="${props.length}">查看详情</button>
        <button class="popup-btn popup-chart-btn" data-type="line" data-id="${props.id}" data-name="${props.name}">数据图表</button>
      </div>
      <div class="popup-footer"><small>最后更新: ${new Date().toLocaleString()}</small></div>
    `)
    .addTo(map!)
}

const handleTransformerClick = (feature: any) => {
  const props = feature.properties
  const coords = (feature.geometry as any).coordinates
  
  if (currentPopup) currentPopup.remove()
  
  currentPopup = new mapboxgl.Popup({
    offset: 25,
    className: 'custom-popup',
    closeOnClick: true
  })
    .setLngLat(coords)
    .setHTML(`
      <div class="popup-header">
        <h3>${props.name}</h3>
        <div class="status-indicator ${props.areaStatus === 'normal' ? 'status-active' : props.areaStatus === 'fault' ? 'status-fault' : 'status-warning'}">${getStatusText(props.areaStatus)}</div>
      </div>
      <div class="popup-content">
        <div class="info-row"><span class="info-label">配变容量:</span><span class="info-value">${props.capacity || 'N/A'}</span></div>
        <div class="info-row"><span class="info-label">负载率:</span><span class="info-value">${props.loadRate !== undefined ? props.loadRate + '%' : 'N/A'}</span></div>
        <div class="info-row"><span class="info-label">供电用户:</span><span class="info-value">${props.customerCount || 0}户</span></div>
      </div>
      <div class="popup-footer"><small>点击台区查看更多详情</small></div>
    `)
    .addTo(map!)
}

const handleAreaClick = (feature: any, lngLat: mapboxgl.LngLat) => {
  const props = feature.properties
  const loadRate = props.loadRate !== undefined ? `${props.loadRate}%` : 'N/A'
  const transformerCapacity = props.transformerCapacity ? `${props.transformerCapacity}kVA` : 'N/A'
  const importantCustomers = props.importantCustomerCount || 0
  
  if (currentPopup) currentPopup.remove()
  
  const coordinates = JSON.parse(props.geometryJson || '[]')
  if (coordinates.length > 0) {
    areaHighlightSystem?.selectArea(props.id, coordinates.map((p: number[]) => [p[1], p[0]]))
  }
  
  currentPopup = new mapboxgl.Popup({
    className: 'custom-popup',
    closeOnClick: true
  })
    .setLngLat(lngLat)
    .setHTML(`
      <div class="popup-header">
        <h3>${props.name}</h3>
        <div class="status-indicator ${props.areaStatus === 'normal' ? 'status-active' : props.areaStatus === 'fault' ? 'status-fault' : 'status-warning'}">${getStatusText(props.areaStatus)}</div>
      </div>
      <div class="popup-content">
        <div class="info-row"><span class="info-label">ID:</span><span class="info-value">${props.id}</span></div>
        <div class="info-row"><span class="info-label">用户数:</span><span class="info-value">${props.customerCount}户</span></div>
        <div class="info-row"><span class="info-label">重要用户:</span><span class="info-value">${importantCustomers}户</span></div>
        <div class="info-row"><span class="info-label">配变容量:</span><span class="info-value">${transformerCapacity}</span></div>
        <div class="info-row"><span class="info-label">负载率:</span><span class="info-value">${loadRate}</span></div>
        <div class="info-row"><span class="info-label">所属变电站:</span><span class="info-value">${props.substationName || 'N/A'}</span></div>
      </div>
      <div class="popup-actions">
        <button class="popup-btn popup-detail-btn" data-type="area" data-id="${props.id}" data-name="${props.name}" data-status="${props.status}" data-customers="${props.customerCount}">查看详情</button>
        <button class="popup-btn popup-chart-btn" data-type="area" data-id="${props.id}" data-name="${props.name}">数据图表</button>
      </div>
      <div class="popup-footer"><small>最后更新: ${new Date().toLocaleString()}</small></div>
    `)
    .addTo(map!)
}

const getStatusText = (status: string): string => {
  const statusMap: Record<string, string> = {
    normal: '正常',
    warning: '预警',
    overload: '过载',
    fault: '故障',
    maintenance: '检修'
  }
  return statusMap[status] || status || '正常'
}

const highlightLine = (lineId: number, coordinates: number[][]) => {
  if (!map) return
  
  hoveredLineId = lineId
  
  const highlightSource = map.getSource('line-highlight') as mapboxgl.GeoJSONSource
  highlightSource?.setData({
    type: 'FeatureCollection',
    features: [{
      type: 'Feature',
      properties: { id: lineId },
      geometry: { type: 'LineString', coordinates }
    }]
  })
}

const clearLineHighlight = () => {
  if (!map) return
  
  hoveredLineId = null
  
  const highlightSource = map.getSource('line-highlight') as mapboxgl.GeoJSONSource
  highlightSource?.setData({ type: 'FeatureCollection', features: [] })
}

const initDisasterLayers = () => {
}

const updateDisasters = (disasters: any[]) => {
  activeDisasters.value = disasters
}

const loadData = async () => {
  if (!map) return
  
  try {
    const [substationRes, lineRes, areaRes] = await Promise.all([
      getAllSubstations(),
      getAllTransmissionLines(),
      getAllAreas()
    ])
    
    const substations = substationRes.data || []
    const transmissionLines = lineRes.data || []
    const areas = areaRes.data || []
    
    allSubstations = substations
    allTransmissionLines = transmissionLines
    allAreas = areas
    
    emit('stats-update', {
      substations: substations.length,
      lines: transmissionLines.length,
      areas: areas.length
    })
    
    updateMapData()
    
    if (particleSystem) {
      particleSystem.initialize(transmissionLines)
      particleSystem.start()
    }
    
    if (pulseSystem) {
      pulseSystem.start()
    }
    
  } catch (error: any) {
    showError(error)
  }
}

const updateMapData = () => {
  if (!map) return
  
  const substationFeatures = allSubstations.map(s => ({
    type: 'Feature',
    properties: {
      id: s.id,
      name: s.name,
      voltageLevel: s.voltageLevel,
      status: s.status
    },
    geometry: {
      type: 'Point',
      coordinates: [parseFloat(String(s.longitude)), parseFloat(String(s.latitude))]
    }
  }))
  
  const substationSource = map.getSource('substations') as mapboxgl.GeoJSONSource
  substationSource?.setData({ type: 'FeatureCollection', features: substationFeatures })
  
  const lineFeatures = allTransmissionLines.map(line => {
    try {
      const points = JSON.parse(line.geometry)
      const coordinates = points.map((p: number[]) => [p[1], p[0]])
      return {
        type: 'Feature',
        properties: {
          id: line.id,
          name: line.name,
          voltageLevel: line.voltageLevel,
          status: line.status,
          length: line.length,
          lineType: line.lineType || 'overhead',
          loadRate: line.loadRate,
          geometry: line.geometry
        },
        geometry: { type: 'LineString', coordinates }
      }
    } catch {
      return null
    }
  }).filter(Boolean)
  
  const lineSource = map.getSource('lines') as mapboxgl.GeoJSONSource
  lineSource?.setData({ type: 'FeatureCollection', features: lineFeatures as any[] })
  
  const areaFeatures = allAreas.map(area => {
    try {
      const bounds = JSON.parse(area.geometry)
      const coordinates = bounds.map((p: number[]) => [p[1], p[0]])
      
      const areaStatus = getAreaStatus(area.loadRate, area.status)
      const statusStyle = getAreaStatusStyle(area.loadRate, area.status)
      const densityColor = getCustomerDensityColor(area.customerCount)
      
      return {
        type: 'Feature',
        properties: {
          id: area.id,
          name: area.name,
          customerCount: area.customerCount,
          status: area.status,
          areaStatus,
          loadRate: area.loadRate,
          transformerCapacity: area.transformerCapacity,
          importantCustomerCount: area.importantCustomerCount || 0,
          substationName: area.substationName,
          color: statusStyle.borderColor,
          fillColor: areaHexToRgba(statusStyle.fillColor, statusStyle.fillOpacity),
          fillOpacity: statusStyle.fillOpacity,
          borderWidth: statusStyle.borderWidth,
          geometryJson: area.geometry
        },
        geometry: { type: 'Polygon', coordinates: [coordinates] }
      }
    } catch {
      return null
    }
  }).filter(Boolean)
  
  const areaSource = map.getSource('areas') as mapboxgl.GeoJSONSource
  areaSource?.setData({ type: 'FeatureCollection', features: areaFeatures as any[] })
  
  const transformerFeatures = allAreas
    .filter(area => area.transformerLocation || area.geometry)
    .map(area => {
      let coords: [number, number]
      
      if (area.transformerLocation) {
        coords = [area.transformerLocation.lng, area.transformerLocation.lat]
      } else {
        try {
          const bounds = JSON.parse(area.geometry)
          if (bounds.length === 0) return null
          const lats = bounds.map((p: number[]) => p[0])
          const lngs = bounds.map((p: number[]) => p[1])
          coords = [
            (Math.min(...lngs) + Math.max(...lngs)) / 2,
            (Math.min(...lats) + Math.max(...lats)) / 2
          ]
        } catch {
          return null
        }
      }
      
      const areaStatus = getAreaStatus(area.loadRate, area.status)
      const statusStyle = getAreaStatusStyle(area.loadRate, area.status)
      const icon = getTransformerIcon(area.loadRate, area.status)
      
      return {
        type: 'Feature',
        properties: {
          id: area.id,
          name: area.name,
          areaStatus,
          color: statusStyle.borderColor,
          icon,
          capacity: area.transformerCapacity ? `${area.transformerCapacity}kVA` : null,
          loadRate: area.loadRate,
          customerCount: area.customerCount
        },
        geometry: {
          type: 'Point',
          coordinates: coords
        }
      }
    })
    .filter(Boolean)
  
  const transformerSource = map.getSource('transformers') as mapboxgl.GeoJSONSource
  transformerSource?.setData({ type: 'FeatureCollection', features: transformerFeatures as any[] })
  
  const lowVoltageLineFeatures: any[] = []
  const branchLineFeatures: any[] = []
  const customerPointFeatures: any[] = []
  const distributionBoxFeatures: any[] = []
  const areaLabelFeatures: any[] = []
  const equipmentFeatures: any[] = []
  
  allAreas.forEach(area => {
    try {
      const bounds = JSON.parse(area.geometry)
      if (bounds.length < 3) return
      
      const lats = bounds.map((p: number[]) => p[0])
      const lngs = bounds.map((p: number[]) => p[1])
      const centerLng = (Math.min(...lngs) + Math.max(...lngs)) / 2
      const centerLat = (Math.min(...lats) + Math.max(...lats)) / 2
      
      const transformerLng = area.transformerLocation?.lng || centerLng
      const transformerLat = area.transformerLocation?.lat || centerLat
      
      const areaStatus = getAreaStatus(area.loadRate, area.status)
      const lineColor = areaStatus === 'fault' ? '#ff4081' : areaStatus === 'warning' ? '#ffc107' : '#4caf50'
      
      areaLabelFeatures.push({
        type: 'Feature',
        properties: {
          areaId: area.id,
          name: area.name,
          code: area.deviceCode || `TQ-${area.id.toString().padStart(4, '0')}`,
          capacity: area.transformerCapacity || 200,
          customerCount: area.customerCount || 0
        },
        geometry: {
          type: 'Point',
          coordinates: [transformerLng, transformerLat - 0.0008]
        }
      })
      
      const transformerModel = area.transformerModel || ['S11-M-200', 'S13-M-250', 'S11-M-315', 'S13-M-400'][Math.floor(Math.random() * 4)]
      const transformerManufacturer = area.transformerManufacturer || ['特变电工', '西门子', 'ABB', '许继电气'][Math.floor(Math.random() * 4)]
      
      equipmentFeatures.push({
        type: 'Feature',
        properties: {
          areaId: area.id,
          equipmentType: 'transformer',
          name: `${area.name}变压器`,
          model: transformerModel,
          manufacturer: transformerManufacturer,
          capacity: area.transformerCapacity || 200,
          color: '#00f0ff',
          size: 8
        },
        geometry: {
          type: 'Point',
          coordinates: [transformerLng, transformerLat]
        }
      })
      
      const jpBoxLng = transformerLng + 0.0003
      const jpBoxLat = transformerLat + 0.0002
      equipmentFeatures.push({
        type: 'Feature',
        properties: {
          areaId: area.id,
          equipmentType: 'jp_box',
          name: `${area.name}JP柜`,
          model: 'JP-400kVA',
          color: '#7c4dff',
          size: 5
        },
        geometry: {
          type: 'Point',
          coordinates: [jpBoxLng, jpBoxLat]
        }
      })
      
      const capacitorLng = transformerLng - 0.0002
      const capacitorLat = transformerLat + 0.0003
      equipmentFeatures.push({
        type: 'Feature',
        properties: {
          areaId: area.id,
          equipmentType: 'capacitor',
          name: `${area.name}无功补偿`,
          model: 'TBBZ-100kvar',
          color: '#00bcd4',
          size: 4
        },
        geometry: {
          type: 'Point',
          coordinates: [capacitorLng, capacitorLat]
        }
      })
      
      const numMainLines = Math.min(4, Math.ceil((area.customerCount || 50) / 40))
      const lineModels = ['LGJ-35', 'LGJ-50', 'LGJ-70', 'JKLYJ-50']
      
      for (let i = 0; i < numMainLines; i++) {
        const angle = (i / numMainLines) * 2 * Math.PI + Math.PI / 4
        const mainDistance = 0.002 + Math.random() * 0.002
        const mainEndLng = transformerLng + Math.cos(angle) * mainDistance
        const mainEndLat = transformerLat + Math.sin(angle) * mainDistance
        
        lowVoltageLineFeatures.push({
          type: 'Feature',
          properties: {
            areaId: area.id,
            areaName: area.name,
            color: lineColor,
            opacity: 0.7,
            lineType: 'main',
            lineModel: lineModels[i % lineModels.length],
            length: Math.round(mainDistance * 111000)
          },
          geometry: {
            type: 'LineString',
            coordinates: [[transformerLng, transformerLat], [mainEndLng, mainEndLat]]
          }
        })
        
        const boxLng = transformerLng + (mainEndLng - transformerLng) * 0.15
        const boxLat = transformerLat + (mainEndLat - transformerLat) * 0.15
        distributionBoxFeatures.push({
          type: 'Feature',
          properties: {
            areaId: area.id,
            areaName: area.name,
            boxType: 'distribution',
            color: '#00f0ff'
          },
          geometry: {
            type: 'Point',
            coordinates: [boxLng, boxLat]
          }
        })
        
        equipmentFeatures.push({
          type: 'Feature',
          properties: {
            areaId: area.id,
            equipmentType: 'distribution_box',
            name: `${area.name}配电箱${i + 1}号`,
            model: 'XL-21',
            color: '#00f0ff',
            size: 4
          },
          geometry: {
            type: 'Point',
            coordinates: [boxLng, boxLat]
          }
        })
        
        const numBranches = 2 + Math.floor(Math.random() * 2)
        for (let b = 0; b < numBranches; b++) {
          const branchAngle = angle + (Math.random() - 0.5) * 0.8
          const branchStartT = 0.3 + Math.random() * 0.4
          const branchStartLng = transformerLng + (mainEndLng - transformerLng) * branchStartT
          const branchStartLat = transformerLat + (mainEndLat - transformerLat) * branchStartT
          const branchDistance = 0.0008 + Math.random() * 0.001
          const branchEndLng = branchStartLng + Math.cos(branchAngle) * branchDistance
          const branchEndLat = branchStartLat + Math.sin(branchAngle) * branchDistance
          
          branchLineFeatures.push({
            type: 'Feature',
            properties: {
              areaId: area.id,
              areaName: area.name,
              color: lineColor,
              opacity: 0.5,
              lineType: 'branch',
              lineModel: 'LGJ-25'
            },
            geometry: {
              type: 'LineString',
              coordinates: [[branchStartLng, branchStartLat], [branchEndLng, branchEndLat]]
            }
          })
          
          const meterBoxLng = branchStartLng + (branchEndLng - branchStartLng) * 0.3
          const meterBoxLat = branchStartLat + (branchEndLat - branchStartLat) * 0.3
          distributionBoxFeatures.push({
            type: 'Feature',
            properties: {
              areaId: area.id,
              areaName: area.name,
              boxType: 'meter',
              color: '#ffc107'
            },
            geometry: {
              type: 'Point',
              coordinates: [meterBoxLng, meterBoxLat]
            }
          })
          
          equipmentFeatures.push({
            type: 'Feature',
            properties: {
              areaId: area.id,
              equipmentType: 'meter_box',
              name: `电表箱${i + 1}-${b + 1}`,
              model: 'DD862',
              color: '#ffc107',
              size: 3
            },
            geometry: {
              type: 'Point',
              coordinates: [meterBoxLng, meterBoxLat]
            }
          })
        }
        
        const numCustomersOnLine = Math.ceil((area.customerCount || 50) / numMainLines / 4)
        for (let j = 0; j < numCustomersOnLine; j++) {
          const t = 0.2 + Math.random() * 0.75
          const offset = (Math.random() - 0.5) * 0.0008
          const customerLng = transformerLng + (mainEndLng - transformerLng) * t + offset
          const customerLat = transformerLat + (mainEndLat - transformerLat) * t + offset * 0.8
          
          const rand = Math.random()
          let customerType: string
          let customerColor: string
          let customerSize: number
          
          if (rand < 0.05 && area.importantCustomerCount && area.importantCustomerCount > 0) {
            customerType = 'important'
            customerColor = '#ff9800'
            customerSize = 6
          } else if (rand < 0.15) {
            customerType = 'commercial'
            customerColor = '#e040fb'
            customerSize = 5
          } else if (rand < 0.25) {
            customerType = 'industrial'
            customerColor = '#00bcd4'
            customerSize = 5
          } else {
            customerType = 'residential'
            customerColor = '#64ffda'
            customerSize = 4
          }
          
          customerPointFeatures.push({
            type: 'Feature',
            properties: {
              areaId: area.id,
              areaName: area.name,
              customerType,
              color: customerColor,
              size: customerSize,
              opacity: customerType === 'important' ? 0.9 : 0.7
            },
            geometry: {
              type: 'Point',
              coordinates: [customerLng, customerLat]
            }
          })
        }
      }
    } catch {}
  })
  
  const lowVoltageLineSource = map.getSource('low-voltage-lines') as mapboxgl.GeoJSONSource
  lowVoltageLineSource?.setData({ type: 'FeatureCollection', features: lowVoltageLineFeatures })
  
  const branchLineSource = map.getSource('branch-lines') as mapboxgl.GeoJSONSource
  if (branchLineSource) {
    branchLineSource.setData({ type: 'FeatureCollection', features: branchLineFeatures })
  }
  
  const customerPointSource = map.getSource('customer-points') as mapboxgl.GeoJSONSource
  customerPointSource?.setData({ type: 'FeatureCollection', features: customerPointFeatures })
  
  const distributionBoxSource = map.getSource('distribution-boxes') as mapboxgl.GeoJSONSource
  if (distributionBoxSource) {
    distributionBoxSource.setData({ type: 'FeatureCollection', features: distributionBoxFeatures })
  }
  
  const areaLabelSource = map.getSource('area-labels') as mapboxgl.GeoJSONSource
  if (areaLabelSource) {
    areaLabelSource.setData({ type: 'FeatureCollection', features: areaLabelFeatures })
  }
  
  const equipmentSource = map.getSource('equipment-layer') as mapboxgl.GeoJSONSource
  if (equipmentSource) {
    equipmentSource.setData({ type: 'FeatureCollection', features: equipmentFeatures })
  }
  
  rippleSystem?.clearAllFaultAreas()
  allAreas.forEach(area => {
    const areaStatus = getAreaStatus(area.loadRate, area.status)
    if (areaStatus === 'fault') {
      let center: [number, number] | null = null
      
      if (area.transformerLocation) {
        center = [area.transformerLocation.lng, area.transformerLocation.lat]
      } else {
        try {
          const bounds = JSON.parse(area.geometry)
          if (bounds.length > 0) {
            const lats = bounds.map((p: number[]) => p[0])
            const lngs = bounds.map((p: number[]) => p[1])
            center = [
              (Math.min(...lngs) + Math.max(...lngs)) / 2,
              (Math.min(...lats) + Math.max(...lats)) / 2
            ]
          }
        } catch {}
      }
      
      if (center) {
        rippleSystem?.addFaultArea(area.id, center, AREA_STYLE_CONFIG.statusStyles.fault.borderColor)
      }
    }
  })
  
  if (rippleSystem && !rippleSystem['isRunning']) {
    rippleSystem.start()
  }
  
  if (areaPulseSystem && !areaPulseSystem['isRunning']) {
    areaPulseSystem.start()
  }
}

const clearMap = () => {
  if (!map) return
  
  const substationSource = map.getSource('substations') as mapboxgl.GeoJSONSource
  substationSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const lineSource = map.getSource('lines') as mapboxgl.GeoJSONSource
  lineSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const particleSource = map.getSource('particles') as mapboxgl.GeoJSONSource
  particleSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const particleGlowSource = map.getSource('particles-glow') as mapboxgl.GeoJSONSource
  particleGlowSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const lineHighlightSource = map.getSource('line-highlight') as mapboxgl.GeoJSONSource
  lineHighlightSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const areaSource = map.getSource('areas') as mapboxgl.GeoJSONSource
  areaSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const areaHighlightSource = map.getSource('area-highlight') as mapboxgl.GeoJSONSource
  areaHighlightSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const areaSelectedSource = map.getSource('area-selected') as mapboxgl.GeoJSONSource
  areaSelectedSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const areaRipplesSource = map.getSource('area-ripples') as mapboxgl.GeoJSONSource
  areaRipplesSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const transformerSource = map.getSource('transformers') as mapboxgl.GeoJSONSource
  transformerSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const regionSource = map.getSource('region-highlight') as mapboxgl.GeoJSONSource
  regionSource?.setData({ type: 'FeatureCollection', features: [] })
  
  const childrenSource = map.getSource('region-children') as mapboxgl.GeoJSONSource
  childrenSource?.setData({ type: 'FeatureCollection', features: [] })
  
  rippleSystem?.clearAllFaultAreas()
}

const toggleLayer = (layerType: 'substations' | 'lines' | 'areas') => {
  layerVisibility[layerType] = !layerVisibility[layerType]
  
  if (!map) return
  
  const visibility = layerVisibility[layerType] ? 'visible' : 'none'
  
  switch (layerType) {
    case 'substations':
      map.setLayoutProperty('substations-glow', 'visibility', visibility)
      map.setLayoutProperty('substations-pulse', 'visibility', visibility)
      map.setLayoutProperty('substations-points', 'visibility', visibility)
      map.setLayoutProperty('substations-icon', 'visibility', visibility)
      break
    case 'lines':
      map.setLayoutProperty('lines', 'visibility', visibility)
      map.setLayoutProperty('lines-glow', 'visibility', visibility)
      map.setLayoutProperty('lines-glow-outer', 'visibility', visibility)
      map.setLayoutProperty('particles', 'visibility', visibility)
      map.setLayoutProperty('particles-glow', 'visibility', visibility)
      map.setLayoutProperty('line-highlight', 'visibility', visibility)
      map.setLayoutProperty('line-highlight-glow', 'visibility', visibility)
      
      if (visibility === 'visible') {
        particleSystem?.start()
        pulseSystem?.start()
      } else {
        particleSystem?.stop()
        pulseSystem?.stop()
      }
      break
    case 'areas':
      map.setLayoutProperty('areas-glow', 'visibility', visibility)
      map.setLayoutProperty('areas', 'visibility', visibility)
      map.setLayoutProperty('areas-outline', 'visibility', visibility)
      map.setLayoutProperty('area-highlight-fill', 'visibility', visibility)
      map.setLayoutProperty('area-highlight-outline', 'visibility', visibility)
      map.setLayoutProperty('area-selected-fill', 'visibility', visibility)
      map.setLayoutProperty('area-selected-outline', 'visibility', visibility)
      map.setLayoutProperty('area-ripples', 'visibility', visibility)
      map.setLayoutProperty('transformers-glow', 'visibility', visibility)
      map.setLayoutProperty('transformers-pulse', 'visibility', visibility)
      map.setLayoutProperty('transformers-points', 'visibility', visibility)
      map.setLayoutProperty('transformers-icon', 'visibility', visibility)
      
      if (visibility === 'visible') {
        rippleSystem?.start()
        areaPulseSystem?.start()
      } else {
        rippleSystem?.stop()
        areaPulseSystem?.stop()
      }
      break
  }
}

const animateMap = () => {
  if (!map) return
  
  map.flyTo({
    center: [116.4174, 39.9142],
    zoom: 14,
    bearing: 0,
    pitch: 60,
    duration: 2000
  })
  
  setTimeout(() => {
    map?.easeTo({ bearing: 90, duration: 1500 })
  }, 2000)
  
  setTimeout(() => {
    map?.easeTo({
      center: props.center,
      zoom: props.zoom,
      bearing: 0,
      pitch: 0,
      duration: 2000
    })
  }, 3500)
}

const filterData = (voltageLevel: string, status: string) => {
  if (!map) return
  
  let filteredSubstations = allSubstations
  let filteredLines = allTransmissionLines
  let filteredAreas = allAreas
  
  if (voltageLevel) {
    filteredSubstations = filteredSubstations.filter(s => s.voltageLevel === voltageLevel)
    filteredLines = filteredLines.filter(l => l.voltageLevel === voltageLevel)
  }
  
  if (status) {
    filteredSubstations = filteredSubstations.filter(s => s.status === status)
    filteredLines = filteredLines.filter(l => l.status === status)
    filteredAreas = filteredAreas.filter(a => a.status === status)
  }
  
  emit('stats-update', {
    substations: filteredSubstations.length,
    lines: filteredLines.length,
    areas: filteredAreas.length
  })
  
  const substationFeatures = filteredSubstations.map(s => ({
    type: 'Feature',
    properties: {
      id: s.id,
      name: s.name,
      voltageLevel: s.voltageLevel,
      status: s.status
    },
    geometry: {
      type: 'Point',
      coordinates: [parseFloat(String(s.longitude)), parseFloat(String(s.latitude))]
    }
  }))
  
  const substationSource = map.getSource('substations') as mapboxgl.GeoJSONSource
  substationSource?.setData({ type: 'FeatureCollection', features: substationFeatures })
  
  const lineFeatures = filteredLines.map(line => {
    try {
      const points = JSON.parse(line.geometry)
      const coordinates = points.map((p: number[]) => [p[1], p[0]])
      return {
        type: 'Feature',
        properties: {
          id: line.id,
          name: line.name,
          voltageLevel: line.voltageLevel,
          status: line.status,
          length: line.length,
          lineType: line.lineType || 'overhead',
          loadRate: line.loadRate,
          geometry: line.geometry
        },
        geometry: { type: 'LineString', coordinates }
      }
    } catch {
      return null
    }
  }).filter(Boolean)
  
  const lineSource = map.getSource('lines') as mapboxgl.GeoJSONSource
  lineSource?.setData({ type: 'FeatureCollection', features: lineFeatures as any[] })
  
  if (particleSystem) {
    particleSystem.initialize(filteredLines)
  }
  
  const areaFeatures = filteredAreas.map(area => {
    try {
      const bounds = JSON.parse(area.geometry)
      const coordinates = bounds.map((p: number[]) => [p[1], p[0]])
      
      let color = '#00ff80'
      if (area.customerCount > 1000) color = '#ff0080'
      else if (area.customerCount > 500) color = '#ffff00'
      
      return {
        type: 'Feature',
        properties: {
          id: area.id,
          name: area.name,
          customerCount: area.customerCount,
          status: area.status,
          color,
          fillColor: hexToRgba(color, 0.25)
        },
        geometry: { type: 'Polygon', coordinates: [coordinates] }
      }
    } catch {
      return null
    }
  }).filter(Boolean)
  
  const areaSource = map.getSource('areas') as mapboxgl.GeoJSONSource
  areaSource?.setData({ type: 'FeatureCollection', features: areaFeatures as any[] })
}

const flyTo = (lng: number, lat: number, zoom?: number) => {
  if (!map) return
  
  map.flyTo({
    center: [lng, lat],
    zoom: zoom || 16,
    duration: 1500
  })
}

const flyToRegion = async (center: [number, number], zoom: number, adcode?: string) => {
  if (!map) return
  
  const regionSource = map.getSource('region-highlight') as mapboxgl.GeoJSONSource
  const childrenSource = map.getSource('region-children') as mapboxgl.GeoJSONSource
  
  if (adcode && adcode !== '100000') {
    try {
      const geoJsonUrl = `https://geo.datav.aliyun.com/areas_v3/bound/${adcode}.json`
      const response = await fetch(geoJsonUrl)
      const geoJson = await response.json()
      
      regionSource?.setData(geoJson)
      
      const fullGeoJsonUrl = `https://geo.datav.aliyun.com/areas_v3/bound/${adcode}_full.json`
      try {
        const fullResponse = await fetch(fullGeoJsonUrl)
        const fullGeoJson = await fullResponse.json()
        
        if (fullGeoJson.features && fullGeoJson.features.length > 1) {
          const childrenFeatures = fullGeoJson.features.filter((f: any) => f.properties.adcode !== adcode)
          childrenSource?.setData({
            type: 'FeatureCollection',
            features: childrenFeatures
          })
        } else {
          childrenSource?.setData({ type: 'FeatureCollection', features: [] })
        }
      } catch {
        childrenSource?.setData({ type: 'FeatureCollection', features: [] })
      }
      
      if (geoJson.features && geoJson.features.length > 0) {
        const bounds = new mapboxgl.LngLatBounds()
        geoJson.features.forEach((feature: any) => {
          if (feature.geometry.type === 'Polygon') {
            feature.geometry.coordinates[0].forEach((coord: number[]) => {
              bounds.extend([coord[0], coord[1]])
            })
          } else if (feature.geometry.type === 'MultiPolygon') {
            feature.geometry.coordinates.forEach((polygon: number[][][]) => {
              polygon[0].forEach((coord: number[]) => {
                bounds.extend([coord[0], coord[1]])
              })
            })
          }
        })
        
        map.fitBounds(bounds, { padding: 50, duration: 1500 })
      }
    } catch (e) {
      console.error('加载地理边界失败', e)
      map.flyTo({ center, zoom, duration: 1500 })
    }
  } else {
    regionSource?.setData({ type: 'FeatureCollection', features: [] })
    childrenSource?.setData({ type: 'FeatureCollection', features: [] })
    
    map.flyTo({
      center: center,
      zoom: zoom,
      duration: 1500
    })
  }
}

const highlightRegion = async (adcode?: string) => {
  if (!map) return
  
  const regionSource = map.getSource('region-highlight') as mapboxgl.GeoJSONSource
  const childrenSource = map.getSource('region-children') as mapboxgl.GeoJSONSource
  
  if (adcode && adcode !== '100000') {
    try {
      const geoJsonUrl = `https://geo.datav.aliyun.com/areas_v3/bound/${adcode}.json`
      const response = await fetch(geoJsonUrl)
      const geoJson = await response.json()
      regionSource?.setData(geoJson)
      
      const fullGeoJsonUrl = `https://geo.datav.aliyun.com/areas_v3/bound/${adcode}_full.json`
      try {
        const fullResponse = await fetch(fullGeoJsonUrl)
        const fullGeoJson = await fullResponse.json()
        
        if (fullGeoJson.features && fullGeoJson.features.length > 1) {
          const childrenFeatures = fullGeoJson.features.filter((f: any) => f.properties.adcode !== adcode)
          childrenSource?.setData({
            type: 'FeatureCollection',
            features: childrenFeatures
          })
        } else {
          childrenSource?.setData({ type: 'FeatureCollection', features: [] })
        }
      } catch {
        childrenSource?.setData({ type: 'FeatureCollection', features: [] })
      }
    } catch (e) {
      console.error('加载地理边界失败', e)
      regionSource?.setData({ type: 'FeatureCollection', features: [] })
      childrenSource?.setData({ type: 'FeatureCollection', features: [] })
    }
  } else {
    regionSource?.setData({ type: 'FeatureCollection', features: [] })
    childrenSource?.setData({ type: 'FeatureCollection', features: [] })
  }
}

const clearRegionHighlight = () => {
  if (!map) return
  
  const regionSource = map.getSource('region-highlight') as mapboxgl.GeoJSONSource
  const childrenSource = map.getSource('region-children') as mapboxgl.GeoJSONSource
  regionSource?.setData({ type: 'FeatureCollection', features: [] })
  childrenSource?.setData({ type: 'FeatureCollection', features: [] })
}

defineExpose({
  loadData,
  clearMap,
  toggleLayer,
  animateMap,
  flyTo,
  flyToRegion,
  highlightRegion,
  clearRegionHighlight,
  filterData,
  updateLineLoadRate: (lineId: number, loadRate: number) => {
    particleSystem?.updateLineLoadRate(lineId, loadRate)
  },
  updateAreaStatus: (areaId: number, loadRate: number, status?: string) => {
    const area = allAreas.find(a => a.id === areaId)
    if (area) {
      area.loadRate = loadRate
      if (status) area.status = status
      updateMapData()
    }
  },
  updateDisasters: (disasters: any[]) => updateDisasters(disasters),
  setAreaFault: (areaId: number, isFault: boolean) => {
    const area = allAreas.find(a => a.id === areaId)
    if (area) {
      area.status = isFault ? '故障' : '正常'
      updateMapData()
    }
  },
  updateAlerts: updateAlertsOnMap,
  toggleStateSwitcher: (show?: boolean) => {
    showStateSwitcher.value = show !== undefined ? show : !showStateSwitcher.value
  }
})
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 100%;
  position: relative;
}

.map-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: linear-gradient(90deg, transparent, #00f0ff, transparent);
  animation: scan 3s linear infinite;
  z-index: 2;
  pointer-events: none;
}

.map-container::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: linear-gradient(90deg, transparent, #00f0ff, transparent);
  animation: scan 3s linear infinite reverse;
  z-index: 2;
  pointer-events: none;
}

@keyframes scan {
  0% { transform: translateX(-100%); }
  100% { transform: translateX(100%); }
}

:global(.popup-actions) {
  display: flex;
  gap: 8px;
  padding: 10px 15px;
  border-top: 1px solid rgba(0, 240, 255, 0.2);
}

:global(.popup-btn) {
  flex: 1;
  padding: 6px 12px;
  border: 1px solid rgba(0, 240, 255, 0.5);
  border-radius: 4px;
  background: rgba(0, 240, 255, 0.1);
  color: #00f0ff;
  font-size: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

:global(.popup-btn:hover) {
  background: rgba(0, 240, 255, 0.3);
  border-color: #00f0ff;
  box-shadow: 0 0 10px rgba(0, 240, 255, 0.3);
}

:global(.popup-detail-btn) {
  background: rgba(0, 240, 255, 0.2);
}

:global(.popup-chart-btn) {
  background: rgba(0, 255, 128, 0.1);
  border-color: rgba(0, 255, 128, 0.5);
  color: #00ff80;
}

:global(.popup-chart-btn:hover) {
  background: rgba(0, 255, 128, 0.3);
  border-color: #00ff80;
  box-shadow: 0 0 10px rgba(0, 255, 128, 0.3);
}

:global(.status-indicator) {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 11px;
  font-weight: 500;
}

:global(.status-active) {
  background: rgba(0, 255, 128, 0.2);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.5);
}

:global(.status-warning) {
  background: rgba(255, 255, 0, 0.2);
  color: #ffff00;
  border: 1px solid rgba(255, 255, 0, 0.5);
}

:global(.status-fault) {
  background: rgba(255, 0, 64, 0.2);
  color: #ff0040;
  border: 1px solid rgba(255, 0, 64, 0.5);
  animation: fault-pulse 1s ease-in-out infinite;
}

:global(.status-inactive) {
  background: rgba(136, 136, 136, 0.2);
  color: #888888;
  border: 1px solid rgba(136, 136, 136, 0.5);
}

@keyframes fault-pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}

.map-legend {
  position: absolute;
  right: 20px;
  bottom: 80px;
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 0;
  min-width: 180px;
  backdrop-filter: blur(10px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5);
  z-index: 10;
}

.legend-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  background: rgba(0, 240, 255, 0.05);
}

.legend-title {
  font-size: 14px;
  font-weight: 600;
  color: #00f0ff;
}

.legend-close {
  background: none;
  border: none;
  color: #6a7a8a;
  font-size: 18px;
  cursor: pointer;
  padding: 0;
  line-height: 1;
}

.legend-close:hover {
  color: #00f0ff;
}

.legend-content {
  padding: 12px 16px;
}

.legend-section {
  margin-bottom: 12px;
}

.legend-section:last-child {
  margin-bottom: 0;
}

.legend-section-title {
  font-size: 12px;
  color: #6a7a8a;
  margin-bottom: 8px;
  padding-bottom: 4px;
  border-bottom: 1px dashed rgba(0, 240, 255, 0.1);
}

.legend-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 6px;
  font-size: 12px;
  color: #c0c0c0;
}

.legend-item:last-child {
  margin-bottom: 0;
}

.legend-emoji {
  font-size: 16px;
  width: 20px;
  text-align: center;
  display: inline-block;
}

.legend-icon {
  width: 16px;
  height: 16px;
  border-radius: 4px;
}

.legend-icon.transformer {
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: 1px solid #00f0ff;
}

.legend-line {
  width: 24px;
  height: 3px;
  border-radius: 2px;
}

.legend-line.low-voltage,
.legend-line.main-line {
  background: #ffc107;
}

.legend-line.branch-line {
  background: repeating-linear-gradient(
    90deg,
    #ffc107,
    #ffc107 3px,
    transparent 3px,
    transparent 5px
  );
}

.legend-line.normal {
  background: #4caf50;
}

.legend-line.warning {
  background: #ffc107;
}

.legend-line.fault {
  background: #ff4081;
}

.legend-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.legend-dot.customer,
.legend-dot.residential {
  background: #64ffda;
  box-shadow: 0 0 4px rgba(100, 255, 218, 0.5);
}

.legend-dot.important {
  background: #ff9800;
  box-shadow: 0 0 4px rgba(255, 152, 0, 0.5);
}

.legend-dot.commercial {
  background: #e040fb;
  box-shadow: 0 0 4px rgba(224, 64, 251, 0.5);
}

.legend-dot.industrial {
  background: #00bcd4;
  box-shadow: 0 0 4px rgba(0, 188, 212, 0.5);
}

.legend-dot.distribution-box {
  background: #00f0ff;
  box-shadow: 0 0 4px rgba(0, 240, 255, 0.5);
}

.legend-dot.meter-box {
  background: #ffc107;
  box-shadow: 0 0 4px rgba(255, 193, 7, 0.5);
}

.legend-dot.jp-box {
  background: #7c4dff;
  box-shadow: 0 0 4px rgba(124, 77, 255, 0.5);
}

.legend-dot.capacitor {
  background: #00bcd4;
  box-shadow: 0 0 4px rgba(0, 188, 212, 0.5);
}

.legend-toggle {
  position: absolute;
  right: 20px;
  bottom: 80px;
  width: 40px;
  height: 40px;
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 8px;
  color: #00f0ff;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
  z-index: 10;
}

.legend-toggle:hover {
  background: rgba(0, 240, 255, 0.15);
  border-color: rgba(0, 240, 255, 0.5);
}

.state-switcher {
  position: absolute;
  left: 20px;
  top: 20px;
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  backdrop-filter: blur(10px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5);
  z-index: 10;
  min-width: 200px;
}

.state-header {
  padding: 12px 16px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  background: rgba(0, 240, 255, 0.05);
  border-radius: 12px 12px 0 0;
}

.state-title {
  font-size: 14px;
  font-weight: 600;
  color: #00f0ff;
  letter-spacing: 2px;
}

.state-buttons {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 8px;
  padding: 12px;
}

.state-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  padding: 12px 8px;
  background: rgba(0, 240, 255, 0.05);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.state-btn:hover {
  background: rgba(0, 240, 255, 0.1);
  border-color: rgba(0, 240, 255, 0.4);
}

.state-btn.active {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.3);
}

.state-icon {
  font-size: 20px;
}

.state-name {
  font-size: 12px;
  color: #e8e8e8;
  font-weight: 500;
}

.state-btn.active .state-name {
  color: #00f0ff;
}

.state-info {
  padding: 12px 16px;
  border-top: 1px solid rgba(0, 240, 255, 0.1);
  background: rgba(0, 0, 0, 0.2);
  border-radius: 0 0 12px 12px;
}

.current-state {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 6px;
}

.state-label {
  font-size: 11px;
  color: #6a7a8a;
}

.state-value {
  font-size: 13px;
  color: #00f0ff;
  font-weight: 600;
}

.state-desc {
  font-size: 11px;
  color: #8a9aaa;
  line-height: 1.5;
}

.compare-btn {
  color: #6a7a8a !important;
  padding: 4px 8px !important;
  font-size: 12px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.compare-btn:hover {
  color: #00f0ff !important;
}

.state-time-selector {
  padding: 12px;
  border-top: 1px solid rgba(0, 240, 255, 0.1);
  background: rgba(0, 0, 0, 0.2);
}

.time-label {
  font-size: 11px;
  color: #6a7a8a;
  margin-bottom: 8px;
}

.time-options {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.time-btn {
  padding: 4px 10px;
  background: rgba(0, 240, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.2);
  border-radius: 4px;
  color: #a0b0c0;
  font-size: 11px;
  cursor: pointer;
  transition: all 0.2s ease;
}

.time-btn:hover {
  background: rgba(0, 240, 255, 0.2);
  border-color: rgba(0, 240, 255, 0.4);
}

.time-btn.active {
  background: rgba(0, 240, 255, 0.3);
  border-color: #00f0ff;
  color: #00f0ff;
}

.compare-panel {
  padding: 12px;
  border-top: 1px solid rgba(0, 240, 255, 0.1);
  background: rgba(0, 0, 0, 0.2);
}

.compare-header {
  font-size: 12px;
  color: #00f0ff;
  margin-bottom: 10px;
}

.compare-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}

.compare-label {
  font-size: 11px;
  color: #6a7a8a;
}

.compare-result {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding-top: 10px;
  border-top: 1px dashed rgba(0, 240, 255, 0.1);
}

.result-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.result-label {
  font-size: 11px;
  color: #6a7a8a;
}

.result-value {
  font-size: 12px;
  color: #ffc107;
  font-weight: 500;
}

.state-stats {
  display: flex;
  gap: 16px;
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px dashed rgba(0, 240, 255, 0.1);
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 11px;
  color: #a0b0c0;
}

.stat-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.stat-dot.online {
  background: #00ff80;
  box-shadow: 0 0 6px rgba(0, 255, 128, 0.5);
}

.stat-dot.warning {
  background: #ffc107;
  box-shadow: 0 0 6px rgba(255, 193, 7, 0.5);
}

.slide-fade-enter-active,
.slide-fade-leave-active {
  transition: all 0.3s ease;
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
}
</style>
