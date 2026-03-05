/**
 * types/alert.ts - 告警类型定义
 * 
 * 功能说明：
 * 定义告警相关的 TypeScript 类型
 * 与后端 Alert 实体类对应
 */

/**
 * 告警类型枚举
 */
export enum AlertType {
  DEVICE_OFFLINE = 'DEVICE_OFFLINE',
  OVERLOAD = 'OVERLOAD',
  VOLTAGE_ABNORMAL = 'VOLTAGE_ABNORMAL',
  TEMPERATURE_HIGH = 'TEMPERATURE_HIGH',
  FAULT = 'FAULT',
  MAINTENANCE = 'MAINTENANCE',
  STRONG_WIND = 'STRONG_WIND',
  WILDFIRE = 'WILDFIRE',
  ICE_STORM = 'ICE_STORM',
  FLOOD = 'FLOOD',
  LIGHTNING = 'LIGHTNING',
  EARTHQUAKE = 'EARTHQUAKE'
}

/**
 * 告警级别枚举
 */
export enum AlertLevel {
  CRITICAL = 'CRITICAL',
  HIGH = 'HIGH',
  MEDIUM = 'MEDIUM',
  LOW = 'LOW'
}

/**
 * 告警状态枚举
 */
export enum AlertStatus {
  ACTIVE = 'ACTIVE',
  ACKNOWLEDGED = 'ACKNOWLEDGED',
  RESOLVED = 'RESOLVED'
}

/**
 * 告警数据类型
 * 与后端 Alert 实体类对应
 */
export interface Alert {
  id: string
  type: AlertType
  level: AlertLevel
  status: AlertStatus
  deviceType: string
  deviceId: number
  deviceName: string
  title: string
  message: string
  timestamp: string
  acknowledgedAt?: string
  resolvedAt?: string
  acknowledgedBy?: string
  latitude?: number
  longitude?: number
}

/**
 * 告警统计数据
 */
export interface AlertStats {
  total: number
  active: number
  acknowledged: number
  resolved: number
  critical: number
  high: number
  medium: number
  low: number
}

/**
 * 告警级别配置
 */
export const AlertLevelConfig = {
  [AlertLevel.CRITICAL]: {
    label: '严重',
    color: '#ff0080',
    bgColor: 'rgba(255, 0, 128, 0.2)'
  },
  [AlertLevel.HIGH]: {
    label: '高',
    color: '#ff6600',
    bgColor: 'rgba(255, 102, 0, 0.2)'
  },
  [AlertLevel.MEDIUM]: {
    label: '中',
    color: '#ffff00',
    bgColor: 'rgba(255, 255, 0, 0.2)'
  },
  [AlertLevel.LOW]: {
    label: '低',
    color: '#00ff80',
    bgColor: 'rgba(0, 255, 128, 0.2)'
  }
}

/**
 * 告警类型配置
 */
export const AlertTypeConfig = {
  [AlertType.DEVICE_OFFLINE]: { label: '设备离线', icon: '🔌' },
  [AlertType.OVERLOAD]: { label: '负载过高', icon: '📊' },
  [AlertType.VOLTAGE_ABNORMAL]: { label: '电压异常', icon: '⚡' },
  [AlertType.TEMPERATURE_HIGH]: { label: '温度过高', icon: '🌡️' },
  [AlertType.FAULT]: { label: '故障告警', icon: '⚠️' },
  [AlertType.MAINTENANCE]: { label: '检修提醒', icon: '🔧' },
  [AlertType.STRONG_WIND]: { label: '大风预警', icon: '💨', isNaturalDisaster: true },
  [AlertType.WILDFIRE]: { label: '山火预警', icon: '🔥', isNaturalDisaster: true },
  [AlertType.ICE_STORM]: { label: '冰寒预警', icon: '❄️', isNaturalDisaster: true },
  [AlertType.FLOOD]: { label: '洪涝预警', icon: '🌊', isNaturalDisaster: true },
  [AlertType.LIGHTNING]: { label: '雷电预警', icon: '⛈️', isNaturalDisaster: true },
  [AlertType.EARTHQUAKE]: { label: '地震预警', icon: '🌍', isNaturalDisaster: true }
}

/**
 * 告警状态配置
 */
export const AlertStatusConfig = {
  [AlertStatus.ACTIVE]: { label: '活动', color: '#ff0080' },
  [AlertStatus.ACKNOWLEDGED]: { label: '已确认', color: '#ffff00' },
  [AlertStatus.RESOLVED]: { label: '已解决', color: '#00ff80' }
}
