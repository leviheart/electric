import type { Map, GeoJSONSource } from 'mapbox-gl'
import { RIPPLE_CONFIG } from '../config/areaStyles'

interface Ripple {
  areaId: number
  center: [number, number]
  radius: number
  opacity: number
  startTime: number
  color: string
}

interface FaultArea {
  id: number
  center: [number, number]
  color: string
}

export class RippleAnimationSystem {
  private map: Map
  private ripples: Ripple[] = []
  private faultAreas: FaultArea[] = []
  private animationId: number | null = null
  private isRunning = false

  constructor(map: Map) {
    this.map = map
  }

  addFaultArea(areaId: number, center: [number, number], color: string = '#ff0040'): void {
    const existing = this.faultAreas.find(a => a.id === areaId)
    if (existing) {
      existing.center = center
      existing.color = color
    } else {
      this.faultAreas.push({ id: areaId, center, color })
    }
  }

  removeFaultArea(areaId: number): void {
    this.faultAreas = this.faultAreas.filter(a => a.id !== areaId)
    this.ripples = this.ripples.filter(r => r.areaId !== areaId)
  }

  clearAllFaultAreas(): void {
    this.faultAreas = []
    this.ripples = []
  }

  start(): void {
    if (this.isRunning) return
    this.isRunning = true
    this.animate()
  }

  stop(): void {
    this.isRunning = false
    if (this.animationId !== null) {
      cancelAnimationFrame(this.animationId)
      this.animationId = null
    }
  }

  private animate = (): void => {
    if (!this.isRunning) return

    const now = Date.now()

    this.faultAreas.forEach(area => {
      const lastRipple = this.ripples
        .filter(r => r.areaId === area.id)
        .sort((a, b) => b.startTime - a.startTime)[0]

      if (!lastRipple || now - lastRipple.startTime > RIPPLE_CONFIG.interval) {
        this.ripples.push({
          areaId: area.id,
          center: area.center,
          radius: RIPPLE_CONFIG.minRadius,
          opacity: RIPPLE_CONFIG.opacity,
          startTime: now,
          color: area.color
        })
      }
    })

    this.ripples = this.ripples.filter(ripple => {
      const elapsed = now - ripple.startTime
      if (elapsed > RIPPLE_CONFIG.duration) return false

      const progress = elapsed / RIPPLE_CONFIG.duration
      ripple.radius = RIPPLE_CONFIG.minRadius + (RIPPLE_CONFIG.maxRadius - RIPPLE_CONFIG.minRadius) * progress
      ripple.opacity = RIPPLE_CONFIG.opacity * (1 - progress)

      return true
    })

    this.renderRipples()

    this.animationId = requestAnimationFrame(this.animate)
  }

  private renderRipples(): void {
    const features = this.ripples.map(ripple => ({
      type: 'Feature',
      geometry: {
        type: 'Point',
        coordinates: ripple.center
      },
      properties: {
        areaId: ripple.areaId,
        radius: ripple.radius,
        opacity: ripple.opacity,
        color: ripple.color
      }
    }))

    const source = this.map.getSource('area-ripples') as GeoJSONSource
    source?.setData({
      type: 'FeatureCollection',
      features
    })
  }
}

export class AreaPulseSystem {
  private map: Map
  private animationId: number | null = null
  private isRunning = false
  private phase = 0
  private pulseAreas: Map<number, { speed: number; baseOpacity: number }> = new Map()

  constructor(map: Map) {
    this.map = map
  }

  setPulsingArea(areaId: number, speed: number = 1000, baseOpacity: number = 0.15): void {
    this.pulseAreas.set(areaId, { speed, baseOpacity })
  }

  removePulsingArea(areaId: number): void {
    this.pulseAreas.delete(areaId)
  }

  clearAll(): void {
    this.pulseAreas.clear()
  }

  start(): void {
    if (this.isRunning) return
    this.isRunning = true
    this.animate()
  }

  stop(): void {
    this.isRunning = false
    if (this.animationId !== null) {
      cancelAnimationFrame(this.animationId)
      this.animationId = null
    }
  }

  private animate = (): void => {
    if (!this.isRunning) return

    this.phase += 0.05
    if (this.phase > Math.PI * 2) {
      this.phase -= Math.PI * 2
    }

    const pulseOpacity = 0.15 + Math.sin(this.phase) * 0.1

    try {
      this.map.setPaintProperty('areas', 'fill-opacity', pulseOpacity)
    } catch (e) {
      // Layer might not exist
    }

    this.animationId = requestAnimationFrame(this.animate)
  }
}

export class AreaHighlightSystem {
  private map: Map
  private highlightedAreaId: number | null = null
  private selectedAreaId: number | null = null

  constructor(map: Map) {
    this.map = map
  }

  highlightArea(areaId: number | null, coordinates: number[][]): void {
    this.highlightedAreaId = areaId

    const source = this.map.getSource('area-highlight') as GeoJSONSource
    if (areaId && coordinates.length > 0) {
      source?.setData({
        type: 'FeatureCollection',
        features: [{
          type: 'Feature',
          properties: { id: areaId },
          geometry: { type: 'Polygon', coordinates: [coordinates] }
        }]
      })
    } else {
      source?.setData({ type: 'FeatureCollection', features: [] })
    }
  }

  selectArea(areaId: number | null, coordinates: number[][]): void {
    this.selectedAreaId = areaId

    const source = this.map.getSource('area-selected') as GeoJSONSource
    if (areaId && coordinates.length > 0) {
      source?.setData({
        type: 'FeatureCollection',
        features: [{
          type: 'Feature',
          properties: { id: areaId },
          geometry: { type: 'Polygon', coordinates: [coordinates] }
        }]
      })
    } else {
      source?.setData({ type: 'FeatureCollection', features: [] })
    }
  }

  clearHighlight(): void {
    this.highlightArea(null, [])
  }

  clearSelection(): void {
    this.selectArea(null, [])
  }

  getHighlightedArea(): number | null {
    return this.highlightedAreaId
  }

  getSelectedArea(): number | null {
    return this.selectedAreaId
  }
}
