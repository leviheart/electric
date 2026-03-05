<template>
  <canvas ref="canvasRef" class="disaster-canvas"></canvas>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch } from 'vue'

interface Disaster {
  id: string
  type: string
  level: string
  latitude: number
  longitude: number
  radius: number
}

const props = defineProps<{
  disasters: Disaster[]
  map: any
}>()

const canvasRef = ref<HTMLCanvasElement | null>(null)
let animationId: number | null = null
let ctx: CanvasRenderingContext2D | null = null
let time = 0

const disasterConfigs: Record<string, any> = {
  STRONG_WIND: {
    color: '#00bcd4',
    particleCount: 80,
    name: '大风'
  },
  WILDFIRE: {
    color: '#ff5722',
    particleCount: 100,
    name: '山火'
  },
  ICE_STORM: {
    color: '#03a9f4',
    particleCount: 60,
    name: '冰寒'
  },
  FLOOD: {
    color: '#2196f3',
    particleCount: 80,
    name: '洪涝'
  },
  LIGHTNING: {
    color: '#ffeb3b',
    particleCount: 40,
    name: '雷电'
  },
  EARTHQUAKE: {
    color: '#9c27b0',
    particleCount: 50,
    name: '地震'
  }
}

const initCanvas = () => {
  if (!canvasRef.value) return
  
  const canvas = canvasRef.value
  canvas.width = window.innerWidth
  canvas.height = window.innerHeight
  ctx = canvas.getContext('2d')
  
  window.addEventListener('resize', () => {
    if (canvasRef.value) {
      canvasRef.value.width = window.innerWidth
      canvasRef.value.height = window.innerHeight
    }
  })
}

const lngLatToPixel = (lng: number, lat: number) => {
  if (!props.map) return { x: window.innerWidth / 2, y: window.innerHeight / 2 }
  try {
    const point = props.map.project([lng, lat])
    return { x: point.x, y: point.y }
  } catch (e) {
    return { x: window.innerWidth / 2, y: window.innerHeight / 2 }
  }
}

const metersToPixels = (meters: number, lat: number) => {
  if (!props.map) return 100
  const zoom = props.map.getZoom()
  const metersPerPixel = 40075016.686 * Math.abs(Math.cos(lat * Math.PI / 180)) / Math.pow(2, zoom + 8)
  return meters / metersPerPixel
}

const drawWindEffect = (centerX: number, centerY: number, radiusPx: number) => {
  if (!ctx) return
  
  for (let i = 0; i < 30; i++) {
    const angle = (i / 30) * Math.PI * 2 + time * 0.02
    const r = radiusPx * (0.3 + Math.sin(time * 0.05 + i) * 0.2)
    const x = centerX + Math.cos(angle) * r
    const y = centerY + Math.sin(angle) * r
    
    ctx.beginPath()
    ctx.arc(x, y, 4, 0, Math.PI * 2)
    ctx.fillStyle = 'rgba(0, 188, 212, 0.8)'
    ctx.fill()
    
    ctx.beginPath()
    ctx.moveTo(x, y)
    const tailAngle = angle - 0.5
    ctx.lineTo(
      centerX + Math.cos(tailAngle) * r * 1.2,
      centerY + Math.sin(tailAngle) * r * 1.2
    )
    ctx.strokeStyle = 'rgba(0, 188, 212, 0.4)'
    ctx.lineWidth = 2
    ctx.stroke()
  }
  
  for (let i = 0; i < 20; i++) {
    const angle = Math.random() * Math.PI * 2
    const r = Math.random() * radiusPx
    const x = centerX + Math.cos(angle) * r
    const y = centerY + Math.sin(angle) * r
    const len = 10 + Math.random() * 20
    
    ctx.beginPath()
    ctx.moveTo(x, y)
    ctx.lineTo(x + Math.cos(angle + time * 0.1) * len, y + Math.sin(angle * 0.1) * len)
    ctx.strokeStyle = `rgba(0, 188, 212, ${0.3 + Math.random() * 0.3})`
    ctx.lineWidth = 1
    ctx.stroke()
  }
}

const drawFireEffect = (centerX: number, centerY: number, radiusPx: number) => {
  if (!ctx) return
  
  for (let i = 0; i < 50; i++) {
    const x = centerX + (Math.random() - 0.5) * radiusPx * 1.5
    const baseY = centerY + radiusPx * 0.3
    const height = 30 + Math.random() * 50
    const flicker = Math.sin(time * 0.2 + i * 0.5) * 0.3 + 0.7
    
    const gradient = ctx.createLinearGradient(x, baseY, x, baseY - height * flicker)
    gradient.addColorStop(0, 'rgba(255, 87, 34, 0)')
    gradient.addColorStop(0.2, 'rgba(255, 152, 0, 0.9)')
    gradient.addColorStop(0.5, 'rgba(255, 87, 34, 1)')
    gradient.addColorStop(0.8, 'rgba(255, 235, 59, 0.8)')
    gradient.addColorStop(1, 'rgba(255, 255, 200, 0.6)')
    
    ctx.beginPath()
    ctx.moveTo(x - 6, baseY)
    ctx.quadraticCurveTo(
      x + Math.sin(time * 0.1 + i) * 8,
      baseY - height * flicker * 0.5,
      x + 6,
      baseY
    )
    ctx.fillStyle = gradient
    ctx.fill()
  }
  
  for (let i = 0; i < 20; i++) {
    const x = centerX + (Math.random() - 0.5) * radiusPx
    const y = centerY + (Math.random() - 0.5) * radiusPx * 0.5
    const size = 2 + Math.random() * 4
    
    ctx.beginPath()
    ctx.arc(x, y - time % 50, size, 0, Math.PI * 2)
    ctx.fillStyle = `rgba(255, ${150 + Math.random() * 100}, 0, ${0.8 - (time % 50) / 60})`
    ctx.fill()
  }
}

const drawIceEffect = (centerX: number, centerY: number, radiusPx: number) => {
  if (!ctx) return
  
  for (let i = 0; i < 15; i++) {
    const x = centerX + (Math.random() - 0.5) * radiusPx * 1.5
    const y = centerY + (Math.random() - 0.5) * radiusPx * 1.5
    const size = 8 + Math.random() * 15
    const rotation = time * 0.01 + i
    
    ctx.save()
    ctx.translate(x, y)
    ctx.rotate(rotation)
    
    ctx.beginPath()
    for (let j = 0; j < 6; j++) {
      const angle = (j / 6) * Math.PI * 2
      const px = Math.cos(angle) * size
      const py = Math.sin(angle) * size
      if (j === 0) ctx.moveTo(px, py)
      else ctx.lineTo(px, py)
      
      const innerAngle = angle + Math.PI / 6
      const innerX = Math.cos(innerAngle) * size * 0.4
      const innerY = Math.sin(innerAngle) * size * 0.4
      ctx.lineTo(innerX, innerY)
    }
    ctx.closePath()
    
    const gradient = ctx.createRadialGradient(0, 0, 0, 0, 0, size)
    gradient.addColorStop(0, 'rgba(3, 169, 244, 0.9)')
    gradient.addColorStop(0.5, 'rgba(144, 202, 249, 0.6)')
    gradient.addColorStop(1, 'rgba(3, 169, 244, 0.2)')
    
    ctx.fillStyle = gradient
    ctx.fill()
    ctx.strokeStyle = 'rgba(255, 255, 255, 0.8)'
    ctx.lineWidth = 1
    ctx.stroke()
    
    ctx.restore()
  }
  
  for (let i = 0; i < 30; i++) {
    const x = centerX + (Math.random() - 0.5) * radiusPx * 2
    const y = centerY + (Math.random() - 0.5) * radiusPx * 2
    const size = 1 + Math.random() * 3
    
    ctx.beginPath()
    ctx.arc(x, y, size, 0, Math.PI * 2)
    ctx.fillStyle = `rgba(200, 230, 255, ${0.5 + Math.random() * 0.5})`
    ctx.fill()
  }
}

const drawFloodEffect = (centerX: number, centerY: number, radiusPx: number) => {
  if (!ctx) return
  
  for (let ring = 0; ring < 5; ring++) {
    const ringRadius = radiusPx * (0.3 + ring * 0.2)
    const waveOffset = Math.sin(time * 0.05 + ring) * 10
    
    ctx.beginPath()
    ctx.arc(centerX, centerY + waveOffset, ringRadius, 0, Math.PI * 2)
    ctx.strokeStyle = `rgba(33, 150, 243, ${0.6 - ring * 0.1})`
    ctx.lineWidth = 3
    ctx.stroke()
  }
  
  for (let i = 0; i < 40; i++) {
    const angle = (i / 40) * Math.PI * 2
    const r = radiusPx * (0.5 + Math.sin(time * 0.03 + i * 0.2) * 0.3)
    const x = centerX + Math.cos(angle) * r
    const y = centerY + Math.sin(angle) * r
    
    ctx.beginPath()
    ctx.arc(x, y, 3, 0, Math.PI * 2)
    ctx.fillStyle = 'rgba(100, 181, 246, 0.8)'
    ctx.fill()
  }
  
  for (let i = 0; i < 20; i++) {
    const x = centerX + (Math.random() - 0.5) * radiusPx * 1.5
    const y = centerY + (Math.random() - 0.5) * radiusPx * 1.5
    
    ctx.beginPath()
    ctx.arc(x, y, 2, 0, Math.PI * 2)
    ctx.fillStyle = 'rgba(255, 255, 255, 0.6)'
    ctx.fill()
  }
}

const drawLightningEffect = (centerX: number, centerY: number, radiusPx: number) => {
  if (!ctx) return
  
  if (Math.random() > 0.9) {
    const startX = centerX + (Math.random() - 0.5) * radiusPx
    const startY = centerY - radiusPx * 0.8
    
    ctx.beginPath()
    ctx.moveTo(startX, startY)
    
    let currentX = startX
    let currentY = startY
    
    for (let i = 0; i < 8; i++) {
      currentX += (Math.random() - 0.5) * 40
      currentY += radiusPx * 0.2
      ctx.lineTo(currentX, currentY)
    }
    
    ctx.strokeStyle = 'rgba(255, 235, 59, 1)'
    ctx.lineWidth = 4
    ctx.stroke()
    
    ctx.strokeStyle = 'rgba(255, 255, 255, 0.8)'
    ctx.lineWidth = 8
    ctx.stroke()
    
    ctx.strokeStyle = 'rgba(255, 235, 59, 0.5)'
    ctx.lineWidth = 20
    ctx.stroke()
  }
  
  for (let i = 0; i < 10; i++) {
    const x = centerX + (Math.random() - 0.5) * radiusPx
    const y = centerY + (Math.random() - 0.5) * radiusPx
    const size = 5 + Math.random() * 10
    
    const glow = ctx.createRadialGradient(x, y, 0, x, y, size)
    glow.addColorStop(0, 'rgba(255, 235, 59, 0.9)')
    glow.addColorStop(0.5, 'rgba(255, 193, 7, 0.5)')
    glow.addColorStop(1, 'rgba(255, 235, 59, 0)')
    
    ctx.beginPath()
    ctx.arc(x, y, size, 0, Math.PI * 2)
    ctx.fillStyle = glow
    ctx.fill()
  }
}

const drawEarthquakeEffect = (centerX: number, centerY: number, radiusPx: number) => {
  if (!ctx) return
  
  const waveCount = 3
  for (let w = 0; w < waveCount; w++) {
    const waveRadius = radiusPx * (0.3 + w * 0.25 + Math.sin(time * 0.08 + w) * 0.1)
    
    ctx.beginPath()
    ctx.arc(centerX, centerY, waveRadius, 0, Math.PI * 2)
    ctx.strokeStyle = `rgba(156, 39, 176, ${0.5 - w * 0.12})`
    ctx.lineWidth = 4 - w
    ctx.stroke()
  }
  
  for (let i = 0; i < 8; i++) {
    const angle = (i / 8) * Math.PI * 2
    const length = radiusPx * (0.4 + Math.random() * 0.3)
    
    ctx.beginPath()
    ctx.moveTo(centerX, centerY)
    
    let x = centerX
    let y = centerY
    for (let j = 0; j < 5; j++) {
      x += Math.cos(angle + (Math.random() - 0.5) * 0.3) * length / 5
      y += Math.sin(angle + (Math.random() - 0.5) * 0.3) * length / 5
      ctx.lineTo(x, y)
    }
    
    ctx.strokeStyle = 'rgba(156, 39, 176, 0.6)'
    ctx.lineWidth = 2
    ctx.stroke()
  }
  
  for (let i = 0; i < 15; i++) {
    const angle = Math.random() * Math.PI * 2
    const r = Math.random() * radiusPx * 0.7
    const x = centerX + Math.cos(angle) * r
    const y = centerY + Math.sin(angle) * r
    const size = 2 + Math.random() * 4
    
    ctx.beginPath()
    ctx.arc(x, y, size, 0, Math.PI * 2)
    ctx.fillStyle = `rgba(156, 39, 176, ${0.4 + Math.random() * 0.4})`
    ctx.fill()
  }
}

const drawDisasterZone = (disaster: Disaster) => {
  if (!ctx || !props.map) return
  
  const center = lngLatToPixel(disaster.longitude, disaster.latitude)
  const config = disasterConfigs[disaster.type] || disasterConfigs.WILDFIRE
  const radiusPx = metersToPixels(disaster.radius, disaster.latitude)
  
  const gradient = ctx.createRadialGradient(center.x, center.y, 0, center.x, center.y, radiusPx)
  gradient.addColorStop(0, `${config.color}44`)
  gradient.addColorStop(0.5, `${config.color}22`)
  gradient.addColorStop(1, `${config.color}00`)
  
  ctx.beginPath()
  ctx.arc(center.x, center.y, radiusPx, 0, Math.PI * 2)
  ctx.fillStyle = gradient
  ctx.fill()
  
  ctx.beginPath()
  ctx.arc(center.x, center.y, radiusPx, 0, Math.PI * 2)
  ctx.strokeStyle = config.color
  ctx.lineWidth = 3
  ctx.setLineDash([10, 5])
  ctx.stroke()
  ctx.setLineDash([])
  
  const pulseRadius = radiusPx * (0.9 + Math.sin(time * 0.1) * 0.1)
  ctx.beginPath()
  ctx.arc(center.x, center.y, pulseRadius, 0, Math.PI * 2)
  ctx.strokeStyle = `${config.color}88`
  ctx.lineWidth = 2
  ctx.stroke()
  
  if (disaster.type === 'STRONG_WIND') {
    drawWindEffect(center.x, center.y, radiusPx)
  } else if (disaster.type === 'WILDFIRE') {
    drawFireEffect(center.x, center.y, radiusPx)
  } else if (disaster.type === 'ICE_STORM') {
    drawIceEffect(center.x, center.y, radiusPx)
  } else if (disaster.type === 'FLOOD') {
    drawFloodEffect(center.x, center.y, radiusPx)
  } else if (disaster.type === 'LIGHTNING') {
    drawLightningEffect(center.x, center.y, radiusPx)
  } else if (disaster.type === 'EARTHQUAKE') {
    drawEarthquakeEffect(center.x, center.y, radiusPx)
  }
  
  ctx.font = 'bold 14px Arial'
  ctx.fillStyle = '#ffffff'
  ctx.textAlign = 'center'
  ctx.fillText(config.name, center.x, center.y - radiusPx - 10)
}

const animate = () => {
  if (!ctx || !canvasRef.value) {
    animationId = requestAnimationFrame(animate)
    return
  }
  
  ctx.clearRect(0, 0, canvasRef.value.width, canvasRef.value.height)
  
  time++
  
  props.disasters.forEach(disaster => {
    drawDisasterZone(disaster)
  })
  
  animationId = requestAnimationFrame(animate)
}

watch(() => props.disasters, () => {
}, { deep: true })

onMounted(() => {
  initCanvas()
  animate()
})

onUnmounted(() => {
  if (animationId) {
    cancelAnimationFrame(animationId)
  }
})
</script>

<style scoped>
.disaster-canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 10;
}
</style>
