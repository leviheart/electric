import type { Map, GeoJSONSource } from 'mapbox-gl'
import {
  getVoltageStyle,
  getLoadStatusStyle,
  hexToRgba,
  PARTICLE_CONFIG
} from '../config/lineStyles'
import type { TransmissionLine } from '../types'

interface Particle {
  lineIndex: number
  progress: number
  speed: number
  color: string
  trail: number[][]
  voltageLevel: string
  loadRate?: number
}

interface LineData {
  coordinates: number[][]
  voltageLevel: string
  loadRate?: number
  lineType?: 'overhead' | 'cable'
}

export class ParticleAnimationSystem {
  private map: Map
  private particles: Particle[] = []
  private lineData: LineData[] = []
  private animationId: number | null = null
  private isRunning = false
  private lastTime = 0
  private pulsePhase = 0

  constructor(map: Map) {
    this.map = map
  }

  initialize(lines: TransmissionLine[]): void {
    this.particles = []
    this.lineData = []

    lines.forEach((line, index) => {
      try {
        const points = JSON.parse(line.geometry)
        const coordinates = points.map((p: number[]) => [p[1], p[0]])
        
        this.lineData.push({
          coordinates,
          voltageLevel: line.voltageLevel,
          loadRate: line.loadRate,
          lineType: line.lineType
        })

        const style = getVoltageStyle(line.voltageLevel)
        const loadStyle = getLoadStatusStyle(line.loadRate)
        const particleCount = style.particleCount

        for (let i = 0; i < particleCount; i++) {
          this.particles.push({
            lineIndex: index,
            progress: Math.random(),
            speed: style.particleSpeed * loadStyle.speed * (0.8 + Math.random() * 0.4),
            color: style.particleColor,
            trail: [],
            voltageLevel: line.voltageLevel,
            loadRate: line.loadRate
          })
        }
      } catch (e) {
        console.warn('Failed to parse line geometry:', e)
      }
    })
  }

  start(): void {
    if (this.isRunning) return
    this.isRunning = true
    this.lastTime = performance.now()
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

    const currentTime = performance.now()
    const deltaTime = (currentTime - this.lastTime) / 1000
    this.lastTime = currentTime

    this.pulsePhase += deltaTime
    if (this.pulsePhase > Math.PI * 2) {
      this.pulsePhase -= Math.PI * 2
    }

    this.updateParticles(deltaTime)
    this.renderParticles()

    this.animationId = requestAnimationFrame(this.animate)
  }

  private updateParticles(deltaTime: number): void {
    this.particles.forEach(particle => {
      const lineInfo = this.lineData[particle.lineIndex]
      if (!lineInfo || lineInfo.coordinates.length < 2) return

      particle.progress += particle.speed
      if (particle.progress > 1) {
        particle.progress = 0
        particle.trail = []
      }

      const currentPoint = this.getPointOnLine(lineInfo.coordinates, particle.progress)
      
      particle.trail.unshift([...currentPoint])
      if (particle.trail.length > PARTICLE_CONFIG.trailLength) {
        particle.trail.pop()
      }
    })
  }

  private renderParticles(): void {
    const features: any[] = []

    this.particles.forEach(particle => {
      const lineInfo = this.lineData[particle.lineIndex]
      if (!lineInfo) return

      const loadStyle = getLoadStatusStyle(particle.loadRate)
      const pulseIntensity = loadStyle.pulseIntensity
      const pulseOpacity = pulseIntensity > 0 
        ? 0.7 + Math.sin(this.pulsePhase * 3) * pulseIntensity * 0.3
        : 0.9

      if (particle.trail.length > 0) {
        features.push({
          type: 'Feature',
          geometry: {
            type: 'Point',
            coordinates: particle.trail[0]
          },
          properties: {
            color: particle.color,
            opacity: pulseOpacity,
            radius: PARTICLE_CONFIG.baseRadius,
            isHead: true
          }
        })

        particle.trail.forEach((point, index) => {
          if (index === 0) return
          const trailOpacity = pulseOpacity * (1 - index / PARTICLE_CONFIG.trailLength) * 0.6
          const trailRadius = PARTICLE_CONFIG.baseRadius * (1 - index / PARTICLE_CONFIG.trailLength * 0.5)
          
          features.push({
            type: 'Feature',
            geometry: {
              type: 'Point',
              coordinates: point
            },
            properties: {
              color: particle.color,
              opacity: trailOpacity,
              radius: trailRadius,
              isHead: false
            }
          })
        })
      }
    })

    const source = this.map.getSource('particles') as GeoJSONSource
    source?.setData({
      type: 'FeatureCollection',
      features
    })

    const glowFeatures = features.filter(f => f.properties.isHead)
    const glowSource = this.map.getSource('particles-glow') as GeoJSONSource
    glowSource?.setData({
      type: 'FeatureCollection',
      features: glowFeatures.map(f => ({
        ...f,
        properties: {
          ...f.properties,
          radius: PARTICLE_CONFIG.glowRadius
        }
      }))
    })
  }

  private getPointOnLine(coords: number[][], progress: number): number[] {
    const totalLength = coords.length - 1
    const position = progress * totalLength
    const index = Math.floor(position)
    const t = position - index

    if (index >= coords.length - 1) {
      return [...coords[coords.length - 1]]
    }

    const start = coords[index]
    const end = coords[index + 1]

    return [
      start[0] + (end[0] - start[0]) * t,
      start[1] + (end[1] - start[1]) * t
    ]
  }

  updateLineLoadRate(lineId: number, loadRate: number): void {
    const lineIndex = this.lineData.findIndex((_, i) => i === lineId)
    if (lineIndex === -1) return

    this.lineData[lineIndex].loadRate = loadRate

    this.particles
      .filter(p => p.lineIndex === lineIndex)
      .forEach(particle => {
        const style = getVoltageStyle(particle.voltageLevel)
        const loadStyle = getLoadStatusStyle(loadRate)
        particle.loadRate = loadRate
        particle.speed = style.particleSpeed * loadStyle.speed * (0.8 + Math.random() * 0.4)
      })
  }

  getParticleCount(): number {
    return this.particles.length
  }

  getLineCount(): number {
    return this.lineData.length
  }
}

export class PulseAnimationSystem {
  private map: Map
  private animationId: number | null = null
  private isRunning = false
  private phase = 0

  constructor(map: Map) {
    this.map = map
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

    this.phase += 0.02
    if (this.phase > Math.PI * 2) {
      this.phase -= Math.PI * 2
    }

    const pulseOpacity = 0.15 + Math.sin(this.phase) * 0.1
    const glowOpacity = 0.3 + Math.sin(this.phase) * 0.15

    try {
      this.map.setPaintProperty('lines-glow-outer', 'line-opacity', pulseOpacity)
      this.map.setPaintProperty('lines-glow', 'line-opacity', glowOpacity)
    } catch (e) {
      // Layer might not exist yet
    }

    this.animationId = requestAnimationFrame(this.animate)
  }
}

export class FlowLineAnimation {
  private map: Map
  private animationId: number | null = null
  private isRunning = false
  private offset = 0

  constructor(map: Map) {
    this.map = map
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

    this.offset -= 0.5
    if (this.offset < -30) {
      this.offset = 0
    }

    try {
      this.map.setPaintProperty('lines-flow', 'line-dasharray', [0.5, 0.5, 10, 10])
    } catch (e) {
      // Layer might not exist
    }

    this.animationId = requestAnimationFrame(this.animate)
  }
}
