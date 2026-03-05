<template>
  <div class="station-diagram">
    <div class="diagram-header">
      <div class="header-left">
        <el-button type="text" @click="goBack" class="back-btn">
          <el-icon><ArrowLeft /></el-icon>
          返回
        </el-button>
        <h1 class="station-title">{{ station?.name || '站内图' }}</h1>
      </div>
      <div class="header-right">
        <el-tag :type="getStatusType(station?.status)" size="large">
          {{ station?.status || '未知' }}
        </el-tag>
        <el-tag type="info" size="large">{{ station?.voltageLevel || '-' }}</el-tag>
      </div>
    </div>

    <div class="diagram-content">
      <div class="diagram-toolbar">
        <el-button-group>
          <el-button size="small" @click="zoomIn" title="放大">
            <el-icon><ZoomIn /></el-icon>
          </el-button>
          <el-button size="small" @click="zoomOut" title="缩小">
            <el-icon><ZoomOut /></el-icon>
          </el-button>
          <el-button size="small" @click="resetView" title="重置视图">
            <el-icon><RefreshRight /></el-icon>
          </el-button>
          <el-button size="small" @click="fitToScreen" title="适应屏幕">
            <el-icon><FullScreen /></el-icon>
          </el-button>
        </el-button-group>
        <el-button-group class="ml-10">
          <el-button size="small" :type="showLabels ? 'primary' : ''" @click="showLabels = !showLabels; requestDraw()">
            <el-icon><PriceTag /></el-icon>
            标签
          </el-button>
          <el-button size="small" :type="showValues ? 'primary' : ''" @click="showValues = !showValues; requestDraw()">
            <el-icon><DataLine /></el-icon>
            数值
          </el-button>
          <el-button size="small" :type="showGrid ? 'primary' : ''" @click="showGrid = !showGrid; requestDraw()">
            <el-icon><Grid /></el-icon>
            网格
          </el-button>
        </el-button-group>
        <div class="zoom-indicator">{{ Math.round(scale * 100) }}%</div>
      </div>

      <div class="diagram-canvas-container" ref="containerRef">
        <canvas 
          ref="canvasRef" 
          class="diagram-canvas"
          @mousedown="handleMouseDown"
          @mousemove="handleMouseMove"
          @mouseup="handleMouseUp"
          @mouseleave="handleMouseUp"
          @wheel.prevent="handleWheel"
        ></canvas>
        
        <svg 
          ref="svgRef"
          class="diagram-svg-overlay"
          :style="{ transform: `translate(${offsetX}px, ${offsetY}px) scale(${scale})` }"
          @click="handleSvgClick"
        >
          <defs>
            <filter id="glow">
              <feGaussianBlur stdDeviation="3" result="coloredBlur"/>
              <feMerge>
                <feMergeNode in="coloredBlur"/>
                <feMergeNode in="SourceGraphic"/>
              </feMerge>
            </filter>
          </defs>

          <g class="equipment-layer">
            <g 
              v-for="transformer in diagramData.transformers" 
              :key="'t' + transformer.id"
              class="equipment"
              :class="{ 'selected': selectedEquipment?.id === transformer.id }"
              @click.stop="selectEquipment('transformer', transformer)"
            >
              <circle
                :cx="transformer.x"
                :cy="transformer.y"
                :r="35"
                fill="#804000"
                stroke="#ffcc00"
                stroke-width="3"
                class="transformer-body"
              />
              <circle
                :cx="transformer.x"
                :cy="transformer.y + 15"
                :r="28"
                fill="none"
                stroke="#ffcc00"
                stroke-width="2"
              />
              <text
                v-if="showLabels"
                :x="transformer.x"
                :y="transformer.y - 50"
                text-anchor="middle"
                fill="#ffcc00"
                font-size="14"
                font-weight="500"
              >
                {{ transformer.name }}
              </text>
              <text
                v-if="showValues && transformer.load"
                :x="transformer.x"
                :y="transformer.y + 70"
                text-anchor="middle"
                :fill="getLoadColor(transformer.load)"
                font-size="12"
                font-weight="600"
              >
                {{ transformer.load.toFixed(1) }}%
              </text>
            </g>

            <g 
              v-for="breaker in diagramData.breakers" 
              :key="'b' + breaker.id"
              class="equipment"
              :class="{ 'selected': selectedEquipment?.id === breaker.id }"
              @click.stop="selectEquipment('breaker', breaker)"
            >
              <rect
                :x="breaker.x - 18"
                :y="breaker.y - 12"
                width="36"
                height="24"
                :fill="breaker.closed ? '#004020' : '#400020'"
                :stroke="breaker.closed ? '#00ff80' : '#ff4040'"
                stroke-width="2"
                class="breaker-body"
                rx="4"
              />
              <line
                v-if="!breaker.closed"
                :x1="breaker.x - 12"
                :y1="breaker.y - 6"
                :x2="breaker.x + 12"
                :y2="breaker.y + 6"
                stroke="#fff"
                stroke-width="2"
              />
              <text
                v-if="showLabels"
                :x="breaker.x"
                :y="breaker.y - 20"
                text-anchor="middle"
                fill="#a0a0a0"
                font-size="11"
              >
                {{ breaker.name }}
              </text>
            </g>

            <g 
              v-for="feeder in diagramData.feeders" 
              :key="'f' + feeder.id"
              class="equipment"
              :class="{ 'selected': selectedEquipment?.id === feeder.id }"
              @click.stop="selectEquipment('feeder', feeder)"
            >
              <rect
                :x="feeder.x - 30"
                :y="feeder.y - 18"
                width="60"
                height="36"
                :fill="feeder.status === 'normal' ? 'rgba(0, 80, 40, 0.8)' : 'rgba(80, 0, 0, 0.8)'"
                :stroke="feeder.status === 'normal' ? '#00ff80' : '#ff4040'"
                stroke-width="2"
                class="feeder-body"
                rx="6"
              />
              <text
                :x="feeder.x"
                :y="feeder.y + 5"
                text-anchor="middle"
                :fill="feeder.status === 'normal' ? '#00ff80' : '#ff4040'"
                font-size="13"
                font-weight="500"
              >
                {{ feeder.name }}
              </text>
              <text
                v-if="showValues && feeder.current"
                :x="feeder.x"
                :y="feeder.y + 40"
                text-anchor="middle"
                fill="#00f0ff"
                font-size="11"
              >
                {{ feeder.current.toFixed(1) }} A
              </text>
            </g>

            <g 
              v-for="capacitor in diagramData.capacitors" 
              :key="'c' + capacitor.id"
              class="equipment"
              :class="{ 'selected': selectedEquipment?.id === capacitor.id }"
              @click.stop="selectEquipment('capacitor', capacitor)"
            >
              <rect
                :x="capacitor.x - 20"
                :y="capacitor.y - 35"
                width="40"
                height="70"
                fill="rgba(60, 0, 100, 0.8)"
                stroke="#8000ff"
                stroke-width="2"
                class="capacitor-body"
                rx="6"
              />
              <line
                :x1="capacitor.x - 12"
                :y1="capacitor.y"
                :x2="capacitor.x + 12"
                :y2="capacitor.y"
                stroke="#8000ff"
                stroke-width="2"
              />
              <text
                v-if="showLabels"
                :x="capacitor.x"
                :y="capacitor.y - 45"
                text-anchor="middle"
                fill="#8000ff"
                font-size="11"
              >
                {{ capacitor.name }}
              </text>
              <text
                v-if="showValues && capacitor.capacity"
                :x="capacitor.x"
                :y="capacitor.y + 55"
                text-anchor="middle"
                fill="#cc80ff"
                font-size="11"
              >
                {{ capacitor.capacity }} MVar
              </text>
            </g>
          </g>
        </svg>
      </div>

      <div class="diagram-sidebar" v-if="selectedEquipment">
        <div class="sidebar-header">
          <h3>设备详情</h3>
          <el-button type="text" @click="selectedEquipment = null">
            <el-icon><Close /></el-icon>
          </el-button>
        </div>
        <div class="sidebar-content">
          <div class="detail-item">
            <span class="label">设备名称</span>
            <span class="value">{{ selectedEquipment.name }}</span>
          </div>
          <div class="detail-item">
            <span class="label">设备类型</span>
            <span class="value">{{ getEquipmentTypeName(selectedEquipment.type) }}</span>
          </div>
          <div class="detail-item" v-if="selectedEquipment.status">
            <span class="label">运行状态</span>
            <span class="value" :class="selectedEquipment.status === 'normal' ? 'status-normal' : 'status-error'">
              {{ selectedEquipment.status === 'normal' ? '正常' : '异常' }}
            </span>
          </div>
          <div class="detail-item" v-if="selectedEquipment.closed !== undefined">
            <span class="label">开关状态</span>
            <span class="value" :class="selectedEquipment.closed ? 'status-normal' : 'status-error'">
              {{ selectedEquipment.closed ? '闭合' : '断开' }}
            </span>
          </div>
          <div class="detail-item" v-if="selectedEquipment.load !== undefined">
            <span class="label">负载率</span>
            <span class="value" :class="getLoadClass(selectedEquipment.load)">
              {{ selectedEquipment.load.toFixed(1) }}%
            </span>
          </div>
          <div class="detail-item" v-if="selectedEquipment.current !== undefined">
            <span class="label">电流</span>
            <span class="value">{{ selectedEquipment.current.toFixed(1) }} A</span>
          </div>
          <div class="detail-item" v-if="selectedEquipment.capacity !== undefined">
            <span class="label">容量</span>
            <span class="value">{{ selectedEquipment.capacity }} {{ selectedEquipment.type === 'capacitor' ? 'MVar' : 'MVA' }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, onUnmounted, watch, nextTick } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { 
  ArrowLeft, ZoomIn, ZoomOut, RefreshRight, FullScreen,
  PriceTag, DataLine, Grid, Close 
} from '@element-plus/icons-vue'
import { getSubstationById } from '../api/substation'

interface Station {
  id: number
  name: string
  status: string
  voltageLevel: string
  loadRate?: number
  capacity?: number
}

interface DiagramData {
  mainBus: { x: number; y: number; width: number; height: number; name: string; voltage?: number } | null
  transformers: any[]
  breakers: any[]
  lines: any[]
  feeders: any[]
  capacitors: any[]
}

const route = useRoute()
const router = useRouter()
const stationId = computed(() => Number(route.params.id))

const station = ref<Station | null>(null)
const containerRef = ref<HTMLElement | null>(null)
const canvasRef = ref<HTMLCanvasElement | null>(null)

const scale = ref(1)
const offsetX = ref(0)
const offsetY = ref(0)
const showLabels = ref(true)
const showValues = ref(true)
const showGrid = ref(true)
const selectedEquipment = ref<any>(null)

const isDragging = ref(false)
const lastMouseX = ref(0)
const lastMouseY = ref(0)

const diagramData = reactive<DiagramData>({
  mainBus: null,
  transformers: [],
  breakers: [],
  lines: [],
  feeders: [],
  capacitors: []
})

let animationFrameId: number | null = null

const loadStation = async () => {
  try {
    const res = await getSubstationById(stationId.value)
    station.value = res.data
    generateDiagramData()
    await nextTick()
    resizeCanvas()
    drawCanvas()
  } catch (e) {
    console.error('加载变电站信息失败', e)
  }
}

const generateDiagramData = () => {
  const baseY = 100
  const busWidth = 850
  const busX = 125
  const voltageLevel = station.value?.voltageLevel || '110kV'
  
  diagramData.mainBus = {
    x: busX,
    y: baseY,
    width: busWidth,
    height: 24,
    name: `${voltageLevel} 主母线`,
    voltage: parseInt(voltageLevel) || 110
  }
  
  diagramData.transformers = [
    { id: 1, name: '#1主变', x: 280, y: 260, load: 45 + Math.random() * 30, capacity: 50 },
    { id: 2, name: '#2主变', x: 520, y: 260, load: 40 + Math.random() * 35, capacity: 50 },
    { id: 3, name: '#3主变', x: 760, y: 260, load: 35 + Math.random() * 40, capacity: 50 }
  ]
  
  diagramData.breakers = [
    { id: 1, name: 'DL1', x: 280, y: 160, closed: true },
    { id: 2, name: 'DL2', x: 520, y: 160, closed: true },
    { id: 3, name: 'DL3', x: 760, y: 160, closed: true },
    { id: 4, name: 'DL4', x: 280, y: 370, closed: true },
    { id: 5, name: 'DL5', x: 520, y: 370, closed: true },
    { id: 6, name: 'DL6', x: 760, y: 370, closed: false }
  ]
  
  diagramData.lines = [
    { x1: 280, y1: 124, x2: 280, y2: 148 },
    { x1: 520, y1: 124, x2: 520, y2: 148 },
    { x1: 760, y1: 124, x2: 760, y2: 148 },
    { x1: 280, y1: 172, x2: 280, y2: 225 },
    { x1: 520, y1: 172, x2: 520, y2: 225 },
    { x1: 760, y1: 172, x2: 760, y2: 225 },
    { x1: 280, y1: 295, x2: 280, y2: 358 },
    { x1: 520, y1: 295, x2: 520, y2: 358 },
    { x1: 760, y1: 295, x2: 760, y2: 358 },
    { x1: 280, y1: 382, x2: 280, y2: 480 },
    { x1: 520, y1: 382, x2: 520, y2: 480 },
    { x1: 760, y1: 382, x2: 760, y2: 480 }
  ]
  
  diagramData.feeders = [
    { id: 1, name: 'F1', x: 180, y: 500, status: 'normal', current: 120 + Math.random() * 50 },
    { id: 2, name: 'F2', x: 280, y: 500, status: 'normal', current: 100 + Math.random() * 60 },
    { id: 3, name: 'F3', x: 380, y: 500, status: 'normal', current: 80 + Math.random() * 70 },
    { id: 4, name: 'F4', x: 480, y: 500, status: 'normal', current: 90 + Math.random() * 55 },
    { id: 5, name: 'F5', x: 580, y: 500, status: 'error', current: 0 },
    { id: 6, name: 'F6', x: 680, y: 500, status: 'normal', current: 110 + Math.random() * 45 },
    { id: 7, name: 'F7', x: 780, y: 500, status: 'normal', current: 95 + Math.random() * 60 },
    { id: 8, name: 'F8', x: 880, y: 500, status: 'normal', current: 85 + Math.random() * 50 }
  ]
  
  diagramData.capacitors = [
    { id: 1, name: 'C1', x: 230, y: 600, capacity: 10 },
    { id: 2, name: 'C2', x: 430, y: 600, capacity: 10 },
    { id: 3, name: 'C3', x: 630, y: 600, capacity: 15 },
    { id: 4, name: 'C4', x: 830, y: 600, capacity: 15 }
  ]
}

const resizeCanvas = () => {
  if (!canvasRef.value || !containerRef.value) return
  
  const container = containerRef.value
  const dpr = window.devicePixelRatio || 1
  
  canvasRef.value.width = container.clientWidth * dpr
  canvasRef.value.height = container.clientHeight * dpr
  canvasRef.value.style.width = container.clientWidth + 'px'
  canvasRef.value.style.height = container.clientHeight + 'px'
}

const drawCanvas = () => {
  const canvas = canvasRef.value
  if (!canvas) return
  
  const ctx = canvas.getContext('2d')
  if (!ctx) return
  
  const dpr = window.devicePixelRatio || 1
  ctx.save()
  ctx.scale(dpr, dpr)
  
  const width = canvas.width / dpr
  const height = canvas.height / dpr
  
  ctx.fillStyle = '#0a0a1a'
  ctx.fillRect(0, 0, width, height)
  
  ctx.save()
  ctx.translate(offsetX.value, offsetY.value)
  ctx.scale(scale.value, scale.value)
  
  if (showGrid.value) {
    drawGrid(ctx, width, height)
  }
  
  drawConnections(ctx)
  drawMainBus(ctx)
  
  ctx.restore()
  ctx.restore()
}

const drawGrid = (ctx: CanvasRenderingContext2D, _width: number, _height: number) => {
  const gridSize = 50
  ctx.strokeStyle = 'rgba(0, 240, 255, 0.06)'
  ctx.lineWidth = 1
  
  const startX = Math.floor(-offsetX.value / scale.value / gridSize) * gridSize - gridSize
  const startY = Math.floor(-offsetY.value / scale.value / gridSize) * gridSize - gridSize
  const endX = startX + 1500
  const endY = startY + 1000
  
  for (let x = startX; x <= endX; x += gridSize) {
    ctx.beginPath()
    ctx.moveTo(x, startY)
    ctx.lineTo(x, endY)
    ctx.stroke()
  }
  
  for (let y = startY; y <= endY; y += gridSize) {
    ctx.beginPath()
    ctx.moveTo(startX, y)
    ctx.lineTo(endX, y)
    ctx.stroke()
  }
}

const drawConnections = (ctx: CanvasRenderingContext2D) => {
  ctx.strokeStyle = '#00f0ff'
  ctx.lineWidth = 3
  ctx.lineCap = 'round'
  ctx.shadowColor = 'rgba(0, 240, 255, 0.4)'
  ctx.shadowBlur = 6
  
  diagramData.lines.forEach(line => {
    ctx.beginPath()
    ctx.moveTo(line.x1, line.y1)
    ctx.lineTo(line.x2, line.y2)
    ctx.stroke()
  })
  
  ctx.shadowBlur = 0
}

const drawMainBus = (ctx: CanvasRenderingContext2D) => {
  if (!diagramData.mainBus) return
  
  const bus = diagramData.mainBus
  
  const gradient = ctx.createLinearGradient(bus.x, 0, bus.x + bus.width, 0)
  gradient.addColorStop(0, 'rgba(0, 96, 128, 1)')
  gradient.addColorStop(0.5, 'rgba(0, 160, 200, 1)')
  gradient.addColorStop(1, 'rgba(0, 96, 128, 1)')
  
  ctx.fillStyle = gradient
  ctx.shadowColor = 'rgba(0, 240, 255, 0.5)'
  ctx.shadowBlur = 12
  
  ctx.beginPath()
  ctx.roundRect(bus.x, bus.y, bus.width, bus.height, 4)
  ctx.fill()
  
  ctx.shadowBlur = 0
  
  if (showLabels.value) {
    ctx.fillStyle = '#00f0ff'
    ctx.font = 'bold 14px sans-serif'
    ctx.textAlign = 'center'
    ctx.fillText(bus.name, bus.x + bus.width / 2, bus.y + bus.height / 2 + 5)
  }
  
  if (showValues.value && bus.voltage) {
    ctx.fillStyle = '#00ff80'
    ctx.font = '12px sans-serif'
    ctx.textAlign = 'center'
    ctx.fillText(`${bus.voltage} kV`, bus.x + bus.width / 2, bus.y + bus.height + 18)
  }
}

const handleMouseDown = (e: MouseEvent) => {
  if ((e.target as HTMLElement).tagName === 'svg') return
  
  isDragging.value = true
  lastMouseX.value = e.clientX
  lastMouseY.value = e.clientY
  if (containerRef.value) {
    containerRef.value.style.cursor = 'grabbing'
  }
}

const handleMouseMove = (e: MouseEvent) => {
  if (!isDragging.value) return
  
  const dx = e.clientX - lastMouseX.value
  const dy = e.clientY - lastMouseY.value
  
  offsetX.value += dx
  offsetY.value += dy
  
  lastMouseX.value = e.clientX
  lastMouseY.value = e.clientY
  
  requestDraw()
}

const handleMouseUp = () => {
  isDragging.value = false
  if (containerRef.value) {
    containerRef.value.style.cursor = 'grab'
  }
}

const handleWheel = (e: WheelEvent) => {
  const delta = e.deltaY > 0 ? 0.9 : 1.1
  const newScale = Math.max(0.3, Math.min(3, scale.value * delta))
  
  const rect = containerRef.value?.getBoundingClientRect()
  if (!rect) return
  
  const mouseX = e.clientX - rect.left
  const mouseY = e.clientY - rect.top
  
  offsetX.value = mouseX - (mouseX - offsetX.value) * (newScale / scale.value)
  offsetY.value = mouseY - (mouseY - offsetY.value) * (newScale / scale.value)
  
  scale.value = newScale
  requestDraw()
}

const handleSvgClick = () => {
  selectedEquipment.value = null
}

const requestDraw = () => {
  if (animationFrameId) {
    cancelAnimationFrame(animationFrameId)
  }
  animationFrameId = requestAnimationFrame(drawCanvas)
}

const zoomIn = () => {
  scale.value = Math.min(3, scale.value * 1.2)
  requestDraw()
}

const zoomOut = () => {
  scale.value = Math.max(0.3, scale.value / 1.2)
  requestDraw()
}

const resetView = () => {
  scale.value = 1
  offsetX.value = 0
  offsetY.value = 0
  requestDraw()
}

const fitToScreen = () => {
  if (!containerRef.value) return
  
  const containerWidth = containerRef.value.clientWidth
  const containerHeight = containerRef.value.clientHeight
  
  const diagramWidth = 1100
  const diagramHeight = 700
  
  const scaleX = containerWidth / diagramWidth
  const scaleY = containerHeight / diagramHeight
  
  scale.value = Math.min(scaleX, scaleY) * 0.85
  offsetX.value = (containerWidth - diagramWidth * scale.value) / 2
  offsetY.value = (containerHeight - diagramHeight * scale.value) / 2
  
  requestDraw()
}

const goBack = () => {
  router.push('/')
}

const selectEquipment = (type: string, equipment: any) => {
  selectedEquipment.value = { ...equipment, type }
}

const getStatusType = (status: string): 'success' | 'warning' | 'danger' | 'info' => {
  switch (status) {
    case '运行中':
    case '正常':
      return 'success'
    case '备用':
      return 'info'
    case '检修中':
    case '检修':
      return 'warning'
    default:
      return 'danger'
  }
}

const getLoadColor = (load: number): string => {
  if (load >= 90) return '#ff4040'
  if (load >= 80) return '#ff8c00'
  if (load >= 60) return '#ffff00'
  return '#00ff80'
}

const getLoadClass = (load: number): string => {
  if (load >= 90) return 'load-critical'
  if (load >= 80) return 'load-high'
  if (load >= 60) return 'load-medium'
  return 'load-normal'
}

const getEquipmentTypeName = (type: string): string => {
  const typeMap: Record<string, string> = {
    'transformer': '变压器',
    'breaker': '断路器',
    'feeder': '馈线',
    'capacitor': '电容器'
  }
  return typeMap[type] || type
}

const handleResize = () => {
  resizeCanvas()
  requestDraw()
}

onMounted(() => {
  loadStation()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  if (animationFrameId) {
    cancelAnimationFrame(animationFrameId)
  }
})

watch(stationId, () => {
  loadStation()
})
</script>

<style scoped>
.station-diagram {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a1a2e 50%, #16213e 100%);
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.diagram-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 20px;
  background: rgba(10, 10, 26, 0.95);
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  flex-shrink: 0;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.back-btn {
  color: #00f0ff;
  font-size: 14px;
}

.back-btn:hover {
  color: #00ff80;
}

.station-title {
  color: #00f0ff;
  font-size: 1.3rem;
  margin: 0;
  text-shadow: 0 0 20px rgba(0, 240, 255, 0.5);
}

.header-right {
  display: flex;
  gap: 10px;
}

.diagram-content {
  flex: 1;
  display: flex;
  overflow: hidden;
  position: relative;
}

.diagram-toolbar {
  position: absolute;
  top: 15px;
  left: 15px;
  z-index: 10;
  display: flex;
  gap: 10px;
  align-items: center;
}

.diagram-toolbar .el-button {
  background: rgba(10, 10, 26, 0.9);
  border-color: rgba(0, 240, 255, 0.3);
  color: #00f0ff;
}

.diagram-toolbar .el-button:hover {
  background: rgba(0, 240, 255, 0.1);
  border-color: rgba(0, 240, 255, 0.5);
}

.ml-10 {
  margin-left: 10px;
}

.zoom-indicator {
  background: rgba(10, 10, 26, 0.9);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 4px;
  padding: 5px 12px;
  color: #00f0ff;
  font-size: 12px;
}

.diagram-canvas-container {
  flex: 1;
  position: relative;
  overflow: hidden;
  cursor: grab;
}

.diagram-canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.diagram-svg-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 1100px;
  height: 700px;
  transform-origin: 0 0;
  pointer-events: none;
}

.equipment-layer {
  pointer-events: all;
}

.equipment {
  cursor: pointer;
  transition: filter 0.2s ease;
}

.equipment:hover {
  filter: brightness(1.3);
}

.equipment.selected {
  filter: drop-shadow(0 0 12px #fff);
}

.transformer-body {
  filter: drop-shadow(0 0 8px rgba(255, 200, 0, 0.4));
}

.breaker-body {
  filter: drop-shadow(0 0 4px rgba(0, 255, 128, 0.3));
}

.feeder-body {
  transition: all 0.2s ease;
}

.capacitor-body {
  filter: drop-shadow(0 0 6px rgba(128, 0, 255, 0.3));
}

.diagram-sidebar {
  width: 280px;
  background: rgba(10, 10, 26, 0.95);
  border-left: 1px solid rgba(0, 240, 255, 0.2);
  display: flex;
  flex-direction: column;
  flex-shrink: 0;
}

.sidebar-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
}

.sidebar-header h3 {
  color: #00f0ff;
  margin: 0;
  font-size: 14px;
}

.sidebar-content {
  padding: 15px;
  flex: 1;
  overflow-y: auto;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid rgba(0, 240, 255, 0.1);
}

.detail-item:last-child {
  border-bottom: none;
}

.detail-item .label {
  color: rgba(255, 255, 255, 0.6);
  font-size: 12px;
}

.detail-item .value {
  color: #fff;
  font-size: 12px;
  font-weight: 500;
}

.status-normal {
  color: #00ff80;
}

.status-error {
  color: #ff4040;
}

.load-normal {
  color: #00ff80;
}

.load-medium {
  color: #ffff00;
}

.load-high {
  color: #ff8c00;
}

.load-critical {
  color: #ff4040;
  font-weight: bold;
}
</style>
