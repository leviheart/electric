import type { AreaStatus } from '../types'

export interface AreaStatusStyle {
  fillColor: string
  fillOpacity: number
  borderColor: string
  borderWidth: number
  pulseAnimation: boolean
  pulseSpeed: number
  rippleEffect: boolean
  glowIntensity: number
}

export interface AreaStyleConfig {
  statusStyles: Record<AreaStatus, AreaStatusStyle>
  transformerIcon: Record<AreaStatus, string>
  customerDensityColors: string[]
}

export const AREA_STYLE_CONFIG: AreaStyleConfig = {
  statusStyles: {
    normal: {
      fillColor: '#00ff80',
      fillOpacity: 0.15,
      borderColor: '#00ff80',
      borderWidth: 2,
      pulseAnimation: false,
      pulseSpeed: 0,
      rippleEffect: false,
      glowIntensity: 0
    },
    warning: {
      fillColor: '#ffff00',
      fillOpacity: 0.2,
      borderColor: '#ffff00',
      borderWidth: 2,
      pulseAnimation: true,
      pulseSpeed: 2000,
      rippleEffect: false,
      glowIntensity: 0.3
    },
    overload: {
      fillColor: '#ff8000',
      fillOpacity: 0.25,
      borderColor: '#ff8000',
      borderWidth: 3,
      pulseAnimation: true,
      pulseSpeed: 1000,
      rippleEffect: false,
      glowIntensity: 0.6
    },
    fault: {
      fillColor: '#ff0040',
      fillOpacity: 0.3,
      borderColor: '#ff0040',
      borderWidth: 3,
      pulseAnimation: true,
      pulseSpeed: 500,
      rippleEffect: true,
      glowIntensity: 1.0
    },
    maintenance: {
      fillColor: '#888888',
      fillOpacity: 0.2,
      borderColor: '#888888',
      borderWidth: 2,
      pulseAnimation: false,
      pulseSpeed: 0,
      rippleEffect: false,
      glowIntensity: 0
    }
  },
  
  transformerIcon: {
    normal: '⚡',
    warning: '⚠',
    overload: '🔥',
    fault: '🔴',
    maintenance: '🔧'
  },
  
  customerDensityColors: [
    '#00ff80',
    '#80ff00',
    '#ffff00',
    '#ff8000',
    '#ff0040'
  ]
}

export function getAreaStatus(loadRate?: number, status?: string): AreaStatus {
  if (status === '故障') return 'fault'
  if (status === '检修') return 'maintenance'
  if (loadRate === undefined) return 'normal'
  if (loadRate >= 100) return 'fault'
  if (loadRate >= 80) return 'overload'
  if (loadRate >= 50) return 'warning'
  return 'normal'
}

export function getAreaStatusStyle(loadRate?: number, status?: string): AreaStatusStyle {
  const areaStatus = getAreaStatus(loadRate, status)
  return AREA_STYLE_CONFIG.statusStyles[areaStatus]
}

export function getTransformerIcon(loadRate?: number, status?: string): string {
  const areaStatus = getAreaStatus(loadRate, status)
  return AREA_STYLE_CONFIG.transformerIcon[areaStatus]
}

export function hexToRgba(hex: string, alpha: number): string {
  const r = parseInt(hex.slice(1, 3), 16)
  const g = parseInt(hex.slice(3, 5), 16)
  const b = parseInt(hex.slice(5, 7), 16)
  return `rgba(${r}, ${g}, ${b}, ${alpha})`
}

export function getCustomerDensityColor(customerCount: number): string {
  if (customerCount > 1000) return AREA_STYLE_CONFIG.customerDensityColors[4]
  if (customerCount > 500) return AREA_STYLE_CONFIG.customerDensityColors[3]
  if (customerCount > 200) return AREA_STYLE_CONFIG.customerDensityColors[2]
  if (customerCount > 100) return AREA_STYLE_CONFIG.customerDensityColors[1]
  return AREA_STYLE_CONFIG.customerDensityColors[0]
}

export const RIPPLE_CONFIG = {
  maxRadius: 100,
  minRadius: 10,
  duration: 2000,
  interval: 3000,
  opacity: 0.6,
  strokeWidth: 2
}

export const TRANSFORMER_CONFIG = {
  baseRadius: 8,
  glowRadius: 16,
  pulseRadius: 24,
  iconSize: 16
}

export const AREA_ANIMATION_CONFIG = {
  pulseMinOpacity: 0.1,
  pulseMaxOpacity: 0.3,
  hoverOpacity: 0.4,
  selectedOpacity: 0.5,
  transitionDuration: 300
}
