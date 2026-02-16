/**
 * api/request.ts - API 请求封装
 * 
 * 功能说明：
 * 封装 fetch 请求，提供统一的错误处理和响应解析
 * 支持所有 HTTP 方法（GET、POST、PUT、DELETE）
 * 自动添加 JWT Token 到请求头
 * 
 * 文件关联：
 * - types/index.ts: 使用 ApiResponse 和 ApiError 类型
 * - stores/authStore.ts: 读取 Token
 * - App.vue: 调用这些 API 方法获取数据
 */

import type { ApiResponse, ApiError } from '../types'

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

interface RequestConfig {
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  headers?: Record<string, string>
  body?: any
}

function handleError(error: any): ApiError {
  if (error instanceof TypeError && error.message === 'Failed to fetch') {
    return {
      code: 0,
      message: '网络连接失败，请检查后端服务是否启动'
    }
  }
  
  if (error.status) {
    return {
      code: error.status,
      message: error.message || '请求失败'
    }
  }
  
  return {
    code: -1,
    message: error.message || '未知错误'
  }
}

export function showError(error: ApiError): void {
  console.error('API Error:', error)
  
  let errorMessage = error.message
  
  if (error.data) {
    const fieldErrors = Object.entries(error.data)
      .map(([field, msg]) => `${field}: ${msg}`)
      .join('\n')
    errorMessage += `\n${fieldErrors}`
  }
  
  alert(errorMessage)
}

function getToken(): string | null {
  return localStorage.getItem('electric_grid_token')
}

async function request<T>(url: string, config: RequestConfig = {}): Promise<ApiResponse<T>> {
  const { method = 'GET', headers = {}, body } = config
  
  const token = getToken()
  
  const defaultHeaders: Record<string, string> = {
    'Content-Type': 'application/json',
    ...headers
  }
  
  if (token) {
    defaultHeaders['Authorization'] = `Bearer ${token}`
  }
  
  const options: RequestInit = {
    method,
    headers: defaultHeaders,
    mode: 'cors'
  }
  
  if (body && method !== 'GET') {
    options.body = JSON.stringify(body)
  }
  
  try {
    const response = await fetch(`${BASE_URL}${url}`, options)
    
    const data = await response.json()
    
    if (!response.ok) {
      throw {
        status: response.status,
        message: data.message || '请求失败',
        data: data.data
      }
    }
    
    return data as ApiResponse<T>
  } catch (error: any) {
    throw handleError(error)
  }
}

export function get<T>(url: string): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'GET' })
}

export function post<T>(url: string, body: any): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'POST', body })
}

export function put<T>(url: string, body: any): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'PUT', body })
}

export function del<T>(url: string): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'DELETE' })
}

export default {
  get,
  post,
  put,
  del,
  showError
}
