export type VoltageLevel = '1000kV' | '500kV' | '220kV' | '110kV' | '35kV' | '10kV'
export type LineType = 'overhead' | 'cable'
export type LoadStatus = 'normal' | 'warning' | 'overload' | 'critical'

export interface VoltageStyle {
  color: string
  glowColor: string
  coreColor: string
  width: number
  glowWidth: number
  particleColor: string
  particleSpeed: number
  particleCount: number
}

export interface LineStyleConfig {
  voltageStyles: Record<VoltageLevel, VoltageStyle>
  lineTypeStyles: Record<LineType, { dashArray: number[]; opacity: number }>
  loadStatusStyles: Record<LoadStatus, { color: string; speed: number; pulseIntensity: number }>
}

export const LINE_STYLE_CONFIG: LineStyleConfig = {
  voltageStyles: {
    '1000kV': {
      color: '#FF0040',
      glowColor: '#FF3366',
      coreColor: '#FF6699',
      width: 6,
      glowWidth: 12,
      particleColor: '#FF9999',
      particleSpeed: 0.002,
      particleCount: 8
    },
    '500kV': {
      color: '#FF4500',
      glowColor: '#FF6633',
      coreColor: '#FF9966',
      width: 5,
      glowWidth: 10,
      particleColor: '#FFB380',
      particleSpeed: 0.0018,
      particleCount: 6
    },
    '220kV': {
      color: '#ff0080',
      glowColor: '#ff3399',
      coreColor: '#ff66b3',
      width: 4,
      glowWidth: 8,
      particleColor: '#ff99cc',
      particleSpeed: 0.0015,
      particleCount: 5
    },
    '110kV': {
      color: '#00f0ff',
      glowColor: '#33ffff',
      coreColor: '#66e0ff',
      width: 3,
      glowWidth: 6,
      particleColor: '#99eeff',
      particleSpeed: 0.0012,
      particleCount: 4
    },
    '35kV': {
      color: '#00ff80',
      glowColor: '#33ff99',
      coreColor: '#66ffb3',
      width: 2,
      glowWidth: 4,
      particleColor: '#99ffcc',
      particleSpeed: 0.001,
      particleCount: 3
    },
    '10kV': {
      color: '#1E90FF',
      glowColor: '#4DA6FF',
      coreColor: '#80BFFF',
      width: 1.5,
      glowWidth: 3,
      particleColor: '#B3D9FF',
      particleSpeed: 0.0008,
      particleCount: 2
    }
  },
  
  lineTypeStyles: {
    overhead: {
      dashArray: [],
      opacity: 1
    },
    cable: {
      dashArray: [10, 5],
      opacity: 0.9
    }
  },
  
  loadStatusStyles: {
    normal: {
      color: '#00ff80',
      speed: 1,
      pulseIntensity: 0
    },
    warning: {
      color: '#ffff00',
      speed: 1.5,
      pulseIntensity: 0.3
    },
    overload: {
      color: '#ff8000',
      speed: 2,
      pulseIntensity: 0.6
    },
    critical: {
      color: '#ff0040',
      speed: 3,
      pulseIntensity: 1
    }
  }
}

export function getVoltageStyle(voltageLevel: string): VoltageStyle {
  const level = voltageLevel as VoltageLevel
  return LINE_STYLE_CONFIG.voltageStyles[level] || LINE_STYLE_CONFIG.voltageStyles['110kV']
}

export function getLineTypeStyle(lineType?: LineType) {
  return LINE_STYLE_CONFIG.lineTypeStyles[lineType || 'overhead']
}

export function getLoadStatus(loadRate?: number): LoadStatus {
  if (loadRate === undefined) return 'normal'
  if (loadRate >= 100) return 'critical'
  if (loadRate >= 80) return 'overload'
  if (loadRate >= 50) return 'warning'
  return 'normal'
}

export function getLoadStatusStyle(loadRate?: number) {
  const status = getLoadStatus(loadRate)
  return LINE_STYLE_CONFIG.loadStatusStyles[status]
}

export function hexToRgba(hex: string, alpha: number): string {
  const r = parseInt(hex.slice(1, 3), 16)
  const g = parseInt(hex.slice(3, 5), 16)
  const b = parseInt(hex.slice(5, 7), 16)
  return `rgba(${r}, ${g}, ${b}, ${alpha})`
}

export function interpolateColor(color1: string, color2: string, factor: number): string {
  const r1 = parseInt(color1.slice(1, 3), 16)
  const g1 = parseInt(color1.slice(3, 5), 16)
  const b1 = parseInt(color1.slice(5, 7), 16)
  
  const r2 = parseInt(color2.slice(1, 3), 16)
  const g2 = parseInt(color2.slice(3, 5), 16)
  const b2 = parseInt(color2.slice(5, 7), 16)
  
  const r = Math.round(r1 + (r2 - r1) * factor)
  const g = Math.round(g1 + (g2 - g1) * factor)
  const b = Math.round(b1 + (b2 - b1) * factor)
  
  return `#${r.toString(16).padStart(2, '0')}${g.toString(16).padStart(2, '0')}${b.toString(16).padStart(2, '0')}`
}

export const PARTICLE_CONFIG = {
  trailLength: 5,
  trailFadeStart: 0.8,
  baseRadius: 3,
  glowRadius: 6,
  minSpeed: 0.0005,
  maxSpeed: 0.003,
  spawnInterval: 100
}

export const ANIMATION_CONFIG = {
  pulseDuration: 2000,
  pulseMinOpacity: 0.3,
  pulseMaxOpacity: 0.8,
  flowAnimationDuration: 1000,
  hoverHighlightScale: 1.5,
  clickZoomLevel: 15
}
