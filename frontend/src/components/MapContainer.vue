<template>
  <div id="map" class="map-container"></div>
</template>

<script setup lang="ts">
/**
 * MapContainer.vue - 地图容器组件
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、Vue 3 组合式 API (Composition API)
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * <script setup lang="ts">
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ <script setup> 是 Vue 3 的语法糖，简化了组合式 API 的使用               │
 * │                                                                          │
 * │ 相比传统的 Options API：                                                 │
 * │ - 不需要 export default {}                                              │
 * │ - 不需要 return 响应式变量                                               │
 * │ - 代码更简洁，TypeScript 支持更好                                        │
 * │                                                                          │
 * │ lang="ts" 表示使用 TypeScript                                           │
 * │ - 提供类型检查                                                          │
 * │ - 更好的 IDE 支持                                                       │
 * │ - 编译时发现错误                                                        │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、Mapbox GL JS 简介
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * Mapbox GL JS 是一个用于在 Web 浏览器中渲染交互式地图的 JavaScript 库。
 * 
 * 核心概念：
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 1. Map（地图实例）                                                       │
 * │    - 地图的核心对象，管理地图状态和渲染                                   │
 * │    - 包含样式、中心点、缩放级别等配置                                     │
 * │                                                                          │
 * │ 2. Source（数据源）                                                      │
 * │    - 地图数据的来源，可以是 GeoJSON、矢量瓦片、栅格等                      │
 * │    - 每个 Source 有唯一的 ID                                             │
 * │                                                                          │
 * │ 3. Layer（图层）                                                         │
 * │    - 定义如何渲染 Source 中的数据                                        │
 * │    - 类型：circle（圆点）、line（线）、fill（填充）、symbol（符号）等      │
 * │    - 每个 Layer 必须关联一个 Source                                      │
 * │                                                                          │
 * │ 4. Style（样式）                                                         │
 * │    - 定义地图的整体外观                                                  │
 * │    - 包含所有 Source 和 Layer 的定义                                     │
 * │    - Mapbox 提供多种预设样式：dark-v11、streets-v12、satellite-v9 等      │
 * │                                                                          │
 * │ 数据流：                                                                 │
 * │ Source (GeoJSON数据) → Layer (渲染规则) → 地图显示                       │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 三、组件属性 (Props)
 * ═══════════════════════════════════════════════════════════════════════════
 */
import { onMounted, onUnmounted } from 'vue'
import mapboxgl from 'mapbox-gl'
import type { Substation, TransmissionLine, Area } from '../types'
import { getAllSubstations, getAllTransmissionLines, getAllAreas, showError } from '../api'
import { useSettingsStore } from '../stores/settingsStore'

const settingsStore = useSettingsStore()

/**
 * defineProps<T>() - 定义组件属性
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ TypeScript 泛型语法，定义属性的类型                                       │
 * │                                                                          │
 * │ 属性说明：                                                               │
 * │ - accessToken: Mapbox API 密钥（必填）                                   │
 * │ - mapStyle?: 地图样式 URL（可选，? 表示可选）                             │
 * │ - center?: 中心点坐标 [经度, 纬度]（可选）                                │
 * │ - zoom?: 缩放级别 0-22（可选）                                           │
 * │                                                                          │
 * │ withDefaults(): 为可选属性提供默认值                                     │
 * │ - mapStyle: 暗色主题地图                                                 │
 * │ - center: 北京市中心 [116.4074, 39.9042]                                │
 * │ - zoom: 12（可以看到整个城市）                                           │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
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
}>()

let map: mapboxgl.Map | null = null
let markers: mapboxgl.Marker[] = []
let lines: string[] = []
let polygons: string[] = []
let animationFrames: number[] = []
let timeouts: ReturnType<typeof setTimeout>[] = []

const layerVisibility = {
  substations: true,
  lines: true,
  areas: true
}

const hexToRgba = (hex: string, alpha: number): string => {
  const r = parseInt(hex.slice(1, 3), 16)
  const g = parseInt(hex.slice(3, 5), 16)
  const b = parseInt(hex.slice(5, 7), 16)
  return `rgba(${r}, ${g}, ${b}, ${alpha})`
}

onMounted(() => {
  initMap()
})

onUnmounted(() => {
  animationFrames.forEach(id => cancelAnimationFrame(id))
  timeouts.forEach(id => clearTimeout(id))
  animationFrames = []
  timeouts = []
  clearMap()
  if (map) {
    map.remove()
    map = null
  }
})

const initMap = () => {
  mapboxgl.accessToken = props.accessToken
  
  map = new mapboxgl.Map({
    container: 'map',
    style: settingsStore.mapStyleUrl,
    center: props.center,
    zoom: settingsStore.defaultZoom,
    maxZoom: 18,
    minZoom: 8,
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
    emit('map-ready')
    if (settingsStore.autoLoad) {
      loadData()
    }
  })
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

const loadData = async () => {
  if (!map) return
  
  clearMap()
  
  try {
    const [substationRes, lineRes, areaRes] = await Promise.all([
      getAllSubstations(),
      getAllTransmissionLines(),
      getAllAreas()
    ])
    
    const substations = substationRes.data || []
    const transmissionLines = lineRes.data || []
    const areas = areaRes.data || []
    
    emit('stats-update', {
      substations: substations.length,
      lines: transmissionLines.length,
      areas: areas.length
    })
    
    addSubstationsToMap(substations)
    addTransmissionLinesToMap(transmissionLines)
    addAreasToMap(areas)
    
  } catch (error: any) {
    showError(error)
  }
}

const clearMap = () => {
  markers.forEach(marker => marker.remove())
  markers = []
  
  lines.forEach(lineId => {
    const layerIds = [
      lineId,
      `${lineId}-outer-glow`,
      `${lineId}-inner-glow`,
      `${lineId}-particle`
    ]
    layerIds.forEach(layerId => {
      if (map?.getLayer(layerId)) {
        map.removeLayer(layerId)
      }
    })
    
    const sourceIds = [lineId, `particle-${lineId.replace('line-', '')}`]
    sourceIds.forEach(sourceId => {
      if (map?.getSource(sourceId)) {
        map.removeSource(sourceId)
      }
    })
  })
  lines = []
  
  polygons.forEach(areaId => {
    if (map?.getLayer(areaId)) {
      map.removeLayer(areaId)
    }
  })
  polygons.forEach(areaId => {
    if (map?.getSource(areaId)) {
      map.removeSource(areaId)
    }
  })
  polygons = []
}

const toggleLayer = (layerType: 'substations' | 'lines' | 'areas') => {
  layerVisibility[layerType] = !layerVisibility[layerType]
  
  if (!map) return
  
  switch (layerType) {
    case 'substations':
      markers.forEach(marker => {
        marker.getElement().style.display = layerVisibility.substations ? 'block' : 'none'
      })
      break
    case 'lines':
      lines.forEach(lineId => {
        if (map?.getLayer(lineId)) {
          map.setLayoutProperty(lineId, 'visibility', layerVisibility.lines ? 'visible' : 'none')
        }
      })
      break
    case 'areas':
      polygons.forEach(areaId => {
        if (map?.getLayer(areaId)) {
          map.setLayoutProperty(areaId, 'visibility', layerVisibility.areas ? 'visible' : 'none')
        }
      })
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
    map?.easeTo({
      bearing: 90,
      duration: 1500
    })
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

const addSubstationsToMap = (substations: Substation[]) => {
  if (!map) return
  
  substations.forEach((substation) => {
    const lng = parseFloat(String(substation.longitude))
    const lat = parseFloat(String(substation.latitude))
    
    const markerElement = document.createElement('div')
    
    let markerColor = '#00f0ff'
    switch (substation.voltageLevel) {
      case '220kV':
        markerColor = '#ff0080'
        break
      case '110kV':
        markerColor = '#00f0ff'
        break
      case '35kV':
        markerColor = '#00ff80'
        break
    }
    
    markerElement.innerHTML = `
      <svg width="40" height="40" viewBox="0 0 40 40" style="cursor: pointer;">
        <circle cx="20" cy="20" r="12" fill="${markerColor}" opacity="0.2">
          <animate attributeName="r" values="12;16;12" dur="2s" repeatCount="indefinite"/>
          <animate attributeName="opacity" values="0.2;0;0.2" dur="2s" repeatCount="indefinite"/>
        </circle>
        <circle cx="20" cy="20" r="8" fill="${markerColor}"/>
        <circle cx="20" cy="20" r="8" fill="none" stroke="#ffffff" stroke-width="2"/>
        <circle cx="20" cy="20" r="10" fill="none" stroke="${markerColor}" stroke-width="1" opacity="0.6">
          <animate attributeName="opacity" values="0.6;1;0.6" dur="1.5s" repeatCount="indefinite"/>
        </circle>
        <text x="20" y="24" text-anchor="middle" font-size="16" fill="#ffffff">⚡</text>
      </svg>
    `
    
    const marker = new mapboxgl.Marker({
      element: markerElement,
      anchor: 'center'
    })
      .setLngLat([lng, lat])
      .setPopup(new mapboxgl.Popup({
        offset: 40,
        className: 'custom-popup'
      }).setHTML(`
        <div class="popup-header">
          <h3>${substation.name}</h3>
          <div class="status-indicator ${substation.status === '运行中' ? 'status-active' : 'status-inactive'}">
            ${substation.status}
          </div>
        </div>
        <div class="popup-content">
          <div class="info-row">
            <span class="info-label">ID:</span>
            <span class="info-value">${substation.id}</span>
          </div>
          <div class="info-row">
            <span class="info-label">电压等级:</span>
            <span class="info-value">${substation.voltageLevel}</span>
          </div>
          <div class="info-row">
            <span class="info-label">状态:</span>
            <span class="info-value">${substation.status}</span>
          </div>
        </div>
        <div class="popup-footer">
          <small>最后更新: ${new Date().toLocaleString()}</small>
        </div>
      `))
      .addTo(map)
    
    markers.push(marker)
  })
}

const addTransmissionLinesToMap = (transmissionLines: TransmissionLine[]) => {
  if (!map) return
  
  transmissionLines.forEach((line, index) => {
    try {
      const points = JSON.parse(line.geometry)
      const coordinates = points.map((point: number[]) => [point[1], point[0]])
      
      const lineId = `line-${line.id}`
      const particleId = `particle-${line.id}`
      
      // 检查并移除已存在的图层和源
      const existingLayers = [
        lineId,
        `${lineId}-outer-glow`,
        `${lineId}-inner-glow`,
        `${lineId}-particle`
      ]
      existingLayers.forEach(layerId => {
        if (map?.getLayer(layerId)) {
          map.removeLayer(layerId)
        }
      })
      
      if (map?.getSource(lineId)) {
        map.removeSource(lineId)
      }
      if (map?.getSource(particleId)) {
        map.removeSource(particleId)
      }
      
      let lineColor = '#00f0ff'
      let glowColor = '#00f0ff'
      let particleSpeed = 2
      switch (line.voltageLevel) {
        case '220kV':
          lineColor = '#ff3366'
          glowColor = '#ff0080'
          particleSpeed = 3
          break
        case '110kV':
          lineColor = '#00ccff'
          glowColor = '#00f0ff'
          particleSpeed = 2
          break
        case '35kV':
          lineColor = '#00ff99'
          glowColor = '#00ff80'
          particleSpeed = 1.5
          break
      }
      
      map!.addSource(lineId, {
        type: 'geojson',
        data: {
          type: 'Feature',
          properties: {
            name: line.name,
            id: line.id,
            length: line.length,
            voltageLevel: line.voltageLevel,
            status: line.status
          },
          geometry: {
            type: 'LineString',
            coordinates: coordinates
          }
        }
      })
      
      map!.addLayer({
        id: lineId + '-outer-glow',
        type: 'line',
        source: lineId,
        layout: {
          'line-join': 'round',
          'line-cap': 'round'
        },
        paint: {
          'line-color': glowColor,
          'line-width': 25,
          'line-opacity': 0.08,
          'line-blur': 20
        }
      })
      
      map!.addLayer({
        id: lineId + '-inner-glow',
        type: 'line',
        source: lineId,
        layout: {
          'line-join': 'round',
          'line-cap': 'round'
        },
        paint: {
          'line-color': glowColor,
          'line-width': 12,
          'line-opacity': 0.2,
          'line-blur': 8
        }
      })
      
      map!.addLayer({
        id: lineId,
        type: 'line',
        source: lineId,
        layout: {
          'line-join': 'round',
          'line-cap': 'round'
        },
        paint: {
          'line-color': lineColor,
          'line-width': 2.5,
          'line-opacity': 1
        }
      })
      
      const totalLength = coordinates.reduce((acc: number, coord: number[], i: number) => {
        if (i === 0) return 0
        const prev = coordinates[i - 1]
        const dx = coord[0] - prev[0]
        const dy = coord[1] - prev[1]
        return acc + Math.sqrt(dx * dx + dy * dy)
      }, 0)
      
      const particleCount = Math.max(3, Math.floor(totalLength * 50))
      const particles: any[] = []
      
      for (let i = 0; i < particleCount; i++) {
        particles.push({
          type: 'Feature',
          geometry: {
            type: 'Point',
            coordinates: coordinates[0]
          },
          properties: {
            progress: i / particleCount,
            speed: 0.001 * particleSpeed * (0.8 + Math.random() * 0.4)
          }
        })
      }
      
      map!.addSource(particleId, {
        type: 'geojson',
        data: {
          type: 'FeatureCollection',
          features: particles
        }
      })
      
      map!.addLayer({
        id: particleId,
        type: 'circle',
        source: particleId,
        paint: {
          'circle-radius': ['interpolate', ['linear'], ['zoom'], 8, 2, 12, 4, 16, 8],
          'circle-color': '#ffffff',
          'circle-opacity': ['interpolate', ['linear'], ['zoom'], 8, 0.4, 12, 0.6, 16, 0.8],
          'circle-blur': 0.5
        }
      })
      
      let animationFrame: number
      const particleSource = map!.getSource(particleId) as mapboxgl.GeoJSONSource
      
      const animateParticles = () => {
        if (!map || !map.getLayer(particleId)) return
        
        const updatedParticles = particles.map((p: any) => {
          let progress = p.properties.progress + p.properties.speed
          if (progress > 1) progress = 0
          
          let totalDist = 0
          let coord = coordinates[0]
          
          for (let i = 1; i < coordinates.length; i++) {
            const prev = coordinates[i - 1]
            const curr = coordinates[i]
            const dx = curr[0] - prev[0]
            const dy = curr[1] - prev[1]
            const segLen = Math.sqrt(dx * dx + dy * dy)
            
            if (totalDist + segLen >= progress * totalLength) {
              const segProgress = (progress * totalLength - totalDist) / segLen
              coord = [
                prev[0] + dx * segProgress,
                prev[1] + dy * segProgress
              ]
              break
            }
            totalDist += segLen
          }
          
          p.properties.progress = progress
          p.geometry.coordinates = coord
          return p
        })
        
        particleSource.setData({
          type: 'FeatureCollection',
          features: updatedParticles
        })
        
        animationFrame = requestAnimationFrame(animateParticles)
        animationFrames.push(animationFrame)
      }
      
      const timeoutId = setTimeout(() => {
        animationFrame = requestAnimationFrame(animateParticles)
        animationFrames.push(animationFrame)
      }, index * 100)
      timeouts.push(timeoutId)
      
      map!.on('click', lineId, (e: mapboxgl.MapMouseEvent) => {
        const features = map!.queryRenderedFeatures(e.point, {
          layers: [lineId]
        })
        
        if (features.length > 0) {
          const feature = features[0]
          new mapboxgl.Popup({
            className: 'custom-popup'
          })
            .setLngLat(e.lngLat)
            .setHTML(`
              <div class="popup-header">
                <h3>${feature.properties.name}</h3>
                <div class="status-indicator ${feature.properties.status === '运行中' ? 'status-active' : 'status-inactive'}">
                  ${feature.properties.status}
                </div>
              </div>
              <div class="popup-content">
                <div class="info-row">
                  <span class="info-label">ID:</span>
                  <span class="info-value">${feature.properties.id}</span>
                </div>
                <div class="info-row">
                  <span class="info-label">长度:</span>
                  <span class="info-value">${feature.properties.length}km</span>
                </div>
                <div class="info-row">
                  <span class="info-label">电压等级:</span>
                  <span class="info-value">${feature.properties.voltageLevel}</span>
                </div>
              </div>
              <div class="popup-footer">
                <small>最后更新: ${new Date().toLocaleString()}</small>
              </div>
            `)
            .addTo(map!)
        }
      })
      
      map!.on('mouseenter', lineId, () => {
        map!.getCanvas().style.cursor = 'pointer'
        map!.setPaintProperty(lineId, 'line-width', 4)
        map!.setPaintProperty(lineId + '-inner-glow', 'line-width', 18)
        map!.setPaintProperty(lineId + '-inner-glow', 'line-opacity', 0.35)
      })
      
      map!.on('mouseleave', lineId, () => {
        map!.getCanvas().style.cursor = ''
        map!.setPaintProperty(lineId, 'line-width', 2.5)
        map!.setPaintProperty(lineId + '-inner-glow', 'line-width', 12)
        map!.setPaintProperty(lineId + '-inner-glow', 'line-opacity', 0.2)
      })
      
      lines.push(lineId)
      lines.push(lineId + '-outer-glow')
      lines.push(lineId + '-inner-glow')
      lines.push(particleId)
    } catch (e) {
      console.error(`解析线路数据失败 [${line.name}]:`, e)
    }
  })
}

const addAreasToMap = (areas: Area[]) => {
  if (!map) return
  
  areas.forEach((area) => {
    try {
      const bounds = JSON.parse(area.geometry)
      const coordinates = bounds.map((point: number[]) => [point[1], point[0]])
      
      const areaId = `area-${area.id}`
      const areaOutlineId = `area-outline-${area.id}`
      const areaGlowId = `area-glow-${area.id}`
      
      let areaColor = '#00ff80'
      if (area.customerCount > 1000) {
        areaColor = '#ff0080'
      } else if (area.customerCount > 500) {
        areaColor = '#ffff00'
      }
      
      map!.addSource(areaId, {
        type: 'geojson',
        data: {
          type: 'Feature',
          properties: {
            name: area.name,
            id: area.id,
            customerCount: area.customerCount,
            status: area.status,
            color: areaColor
          },
          geometry: {
            type: 'Polygon',
            coordinates: [coordinates]
          }
        }
      })
      
      map!.addLayer({
        id: areaGlowId,
        type: 'line',
        source: areaId,
        layout: {
          'line-join': 'round',
          'line-cap': 'round'
        },
        paint: {
          'line-color': areaColor,
          'line-width': 6,
          'line-opacity': 0.4,
          'line-blur': 8
        }
      })
      
      map!.addLayer({
        id: areaId,
        type: 'fill',
        source: areaId,
        paint: {
          'fill-color': hexToRgba(areaColor, 0.25),
          'fill-outline-color': 'transparent'
        }
      })
      
      map!.addLayer({
        id: areaOutlineId,
        type: 'line',
        source: areaId,
        layout: {
          'line-join': 'round',
          'line-cap': 'round'
        },
        paint: {
          'line-color': areaColor,
          'line-width': 2,
          'line-opacity': 0.8
        }
      })
      
      map!.on('click', areaId, (e: mapboxgl.MapMouseEvent) => {
        const features = map!.queryRenderedFeatures(e.point, {
          layers: [areaId]
        })
        
        if (features.length > 0) {
          const feature = features[0]
          new mapboxgl.Popup({
            className: 'custom-popup'
          })
            .setLngLat(e.lngLat)
            .setHTML(`
              <div class="popup-header">
                <h3>${feature.properties.name}</h3>
                <div class="status-indicator ${feature.properties.status === '正常' ? 'status-active' : 'status-inactive'}">
                  ${feature.properties.status}
                </div>
              </div>
              <div class="popup-content">
                <div class="info-row">
                  <span class="info-label">ID:</span>
                  <span class="info-value">${feature.properties.id}</span>
                </div>
                <div class="info-row">
                  <span class="info-label">用户数:</span>
                  <span class="info-value">${feature.properties.customerCount}</span>
                </div>
              </div>
              <div class="popup-footer">
                <small>最后更新: ${new Date().toLocaleString()}</small>
              </div>
            `)
            .addTo(map!)
        }
      })
      
      map!.on('mouseenter', areaId, () => {
        map!.getCanvas().style.cursor = 'pointer'
        map!.setPaintProperty(areaId, 'fill-color', hexToRgba(areaColor, 0.4))
        map!.setPaintProperty(areaOutlineId, 'line-width', 4)
        map!.setPaintProperty(areaGlowId, 'line-opacity', 0.6)
      })
      
      map!.on('mouseleave', areaId, () => {
        map!.getCanvas().style.cursor = ''
        map!.setPaintProperty(areaId, 'fill-color', hexToRgba(areaColor, 0.25))
        map!.setPaintProperty(areaOutlineId, 'line-width', 2)
        map!.setPaintProperty(areaGlowId, 'line-opacity', 0.4)
      })
      
      polygons.push(areaId, areaOutlineId, areaGlowId)
    } catch (e) {
      console.error(`解析台区数据失败 [${area.name}]:`, e)
    }
  })
}

const flyTo = (lng: number, lat: number, zoom?: number) => {
  if (!map) return
  
  map.flyTo({
    center: [lng, lat],
    zoom: zoom || 16,
    duration: 1500
  })
}

defineExpose({
  loadData,
  clearMap,
  toggleLayer,
  animateMap,
  flyTo
})
</script>

<style scoped>
.map-container {
  width: 100%;
  flex: 1;
  min-height: 0;
  border: 2px solid rgba(0, 240, 255, 0.5);
  border-radius: 12px;
  box-shadow: 0 0 30px rgba(0, 240, 255, 0.2);
  overflow: hidden;
  position: relative;
  z-index: 1;
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
}

@keyframes scan {
  0% { transform: translateX(-100%); }
  100% { transform: translateX(100%); }
}
</style>
