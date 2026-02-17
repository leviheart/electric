/**
 * api/search.ts - 搜索 API 服务
 */

import request from './request'
import type { ApiResponse, Substation, TransmissionLine, Area } from '../types'

export interface SearchParams {
  keyword?: string
  voltageLevel?: string
  status?: string
  minLat?: number
  maxLat?: number
  minLng?: number
  maxLng?: number
}

export interface SearchResult {
  substations: Substation[]
  lines: TransmissionLine[]
  areas: Area[]
  keyword: string
  totalCount: number
}

function buildQueryString(params: SearchParams): string {
  const queryParts: string[] = []
  
  if (params.keyword) queryParts.push(`keyword=${encodeURIComponent(params.keyword)}`)
  if (params.voltageLevel) queryParts.push(`voltageLevel=${encodeURIComponent(params.voltageLevel)}`)
  if (params.status) queryParts.push(`status=${encodeURIComponent(params.status)}`)
  if (params.minLat !== undefined) queryParts.push(`minLat=${params.minLat}`)
  if (params.maxLat !== undefined) queryParts.push(`maxLat=${params.maxLat}`)
  if (params.minLng !== undefined) queryParts.push(`minLng=${params.minLng}`)
  if (params.maxLng !== undefined) queryParts.push(`maxLng=${params.maxLng}`)
  
  return queryParts.length > 0 ? `?${queryParts.join('&')}` : ''
}

export function globalSearch(params: SearchParams = {}): Promise<ApiResponse<SearchResult>> {
  const queryString = buildQueryString(params)
  return request.get(`/api/search${queryString}`)
}

export function searchSubstations(params: SearchParams = {}): Promise<ApiResponse<Substation[]>> {
  const queryString = buildQueryString(params)
  return request.get(`/api/search/substations${queryString}`)
}

export function searchLines(params: SearchParams = {}): Promise<ApiResponse<TransmissionLine[]>> {
  const queryString = buildQueryString(params)
  return request.get(`/api/search/lines${queryString}`)
}

export function searchAreas(params: SearchParams = {}): Promise<ApiResponse<Area[]>> {
  const queryString = buildQueryString(params)
  return request.get(`/api/search/areas${queryString}`)
}
