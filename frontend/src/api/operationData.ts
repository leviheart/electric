import axios from 'axios'
import type { ApiResponse } from './index'

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('electric_grid_token')
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export interface OperationData {
  id: number
  entityType: string
  entityId: number
  entityName: string
  recordTime: string
  voltage: number
  current: number
  power: number
  powerFactor: number
  frequency: number
  temperature: number
  status: string
}

export const getOperationDataByEntity = (entityType: string, entityId: number) => {
  return api.get<ApiResponse<OperationData[]>>(`/api/operation-data/entity/${entityType}/${entityId}`)
}

export const getLatestOperationData = (entityType: string, entityId: number) => {
  return api.get<ApiResponse<OperationData>>(`/api/operation-data/entity/${entityType}/${entityId}/latest`)
}

export const getOperationDataByDate = (entityType: string, entityId: number, date: string) => {
  return api.get<ApiResponse<OperationData[]>>(`/api/operation-data/entity/${entityType}/${entityId}/date/${date}`)
}

export const getOperationDataByRange = (entityType: string, entityId: number, start: string, end: string) => {
  return api.get<ApiResponse<OperationData[]>>(`/api/operation-data/entity/${entityType}/${entityId}/range`, {
    params: { start, end }
  })
}

export const getDatesWithData = (entityType: string, entityId: number) => {
  return api.get<ApiResponse<string[]>>(`/api/operation-data/entity/${entityType}/${entityId}/dates`)
}

export const getAllDatesWithData = () => {
  return api.get<ApiResponse<string[]>>(`/api/operation-data/dates`)
}
