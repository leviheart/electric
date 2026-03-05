/**
 * api/topology.ts - 拓扑关系 API 服务
 */

import request from './request'
import type { ApiResponse, Substation, TransmissionLine, Area } from '../types'

export interface TopologyStats {
  connectedLineCount: number
  connectedSubstationCount: number
  suppliedAreaCount: number
  totalCustomers: number
  totalLineLength: number
}

export interface TopologyData {
  center: Substation | TransmissionLine | Area
  centerType: 'SUBSTATION' | 'LINE' | 'AREA'
  connectedLines: TransmissionLine[]
  connectedSubstations: Substation[]
  suppliedAreas: Area[]
  statistics: TopologyStats
}

export function getSubstationTopology(id: number): Promise<ApiResponse<TopologyData>> {
  return request.get(`/api/topology/substation/${id}`)
}

export function getLineTopology(id: number): Promise<ApiResponse<TopologyData>> {
  return request.get(`/api/topology/line/${id}`)
}

export function getAreaTopology(id: number): Promise<ApiResponse<TopologyData>> {
  return request.get(`/api/topology/area/${id}`)
}

export function getTopology(type: string, id: number): Promise<ApiResponse<TopologyData>> {
  const typeMap: Record<string, string> = {
    'substation': 'substation',
    'line': 'line',
    'area': 'area'
  }
  const endpoint = typeMap[type] || type
  return request.get(`/api/topology/${endpoint}/${id}`)
}

export function analyzeImpact(type: string, id: number): Promise<ApiResponse<TopologyData>> {
  return request.get(`/api/topology/impact/${type}/${id}`)
}
