/**
 * api/alert.ts - 告警 API 服务
 */

import request from './request'
import type { ApiResponse } from '../types'
import type { Alert, AlertStats } from '../types/alert'

export function getAllAlerts(): Promise<ApiResponse<Alert[]>> {
  return request.get('/api/alerts')
}

export function getActiveAlerts(): Promise<ApiResponse<Alert[]>> {
  return request.get('/api/alerts/active')
}

export function getAlertById(id: string): Promise<ApiResponse<Alert>> {
  return request.get(`/api/alerts/${id}`)
}

export function createAlert(alert: Partial<Alert>): Promise<ApiResponse<Alert>> {
  return request.post('/api/alerts', alert)
}

export function acknowledgeAlert(id: string, username?: string): Promise<ApiResponse<Alert>> {
  return request.put(`/api/alerts/${id}/ack`, { username })
}

export function resolveAlert(id: string): Promise<ApiResponse<Alert>> {
  return request.put(`/api/alerts/${id}/resolve`)
}

export function deleteAlert(id: string): Promise<ApiResponse<void>> {
  return request.del(`/api/alerts/${id}`)
}

export function getAlertStats(): Promise<ApiResponse<AlertStats>> {
  return request.get('/api/alerts/stats')
}
