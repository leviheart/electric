/**
 * api/stats.ts - 统计 API 服务
 */

import request from './request'
import type { ApiResponse } from '../types'

export interface DeviceStats {
  totalSubstations: number
  totalLines: number
  totalAreas: number
  totalCustomers: number
  totalLength: number
  avgLoadRate: number
  totalCapacity: number
  totalTransformerCapacity: number
}

export interface AlertStatsData {
  total: number
  active: number
  acknowledged: number
  resolved: number
  critical: number
  high: number
}

export interface TrendData {
  time: string
  value: number
}

export interface AreaDistribution {
  name: string
  count: number
  percentage: number
}

export interface DashboardStats {
  deviceStats: DeviceStats
  statusStats: Record<string, number>
  voltageStats: Record<string, number>
  alertStats: AlertStatsData
  loadTrend: TrendData[]
  areaDistribution: AreaDistribution[]
}

export function getDashboardStats(): Promise<ApiResponse<DashboardStats>> {
  return request.get('/api/stats/dashboard')
}

export function getDeviceStats(): Promise<ApiResponse<DeviceStats>> {
  return request.get('/api/stats/devices')
}

export function getStatusStats(): Promise<ApiResponse<Record<string, number>>> {
  return request.get('/api/stats/status')
}

export function getVoltageStats(): Promise<ApiResponse<Record<string, number>>> {
  return request.get('/api/stats/voltage')
}

export function getLoadTrend(): Promise<ApiResponse<TrendData[]>> {
  return request.get('/api/stats/trend')
}
