/**
 * api/request.ts - API 请求封装
 * 
 * 功能说明：
 * 封装 fetch 请求，提供统一的错误处理和响应解析
 * 支持所有 HTTP 方法（GET、POST、PUT、DELETE）
 * 自动添加 JWT Token 到请求头
 * 
 * 主要功能：
 * 1. 统一的请求配置
 * 2. 自动添加 Authorization 请求头
 * 3. 统一的错误处理
 * 4. 统一的响应解析
 * 
 * 导出函数：
 * - get<T>(url): 发送 GET 请求
 * - post<T>(url, body): 发送 POST 请求
 * - put<T>(url, body): 发送 PUT 请求
 * - del<T>(url): 发送 DELETE 请求
 * - showError(error): 显示错误提示
 * 
 * 文件关联：
 * - types/index.ts: 使用 ApiResponse 和 ApiError 类型
 * - stores/authStore.ts: 读取 Token
 * - api/auth.ts: 认证 API
 * - api/substation.ts: 变电站 API
 * 
 * 新人提示：
 * - fetch 是浏览器原生提供的 HTTP 请求方法
 * - 泛型 T 用于指定响应数据的类型
 * - import.meta.env 用于读取 Vite 环境变量
 */

import type { ApiResponse, ApiError } from '../types'

// API 基础地址，从环境变量读取
const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

/**
 * 请求配置接口
 */
interface RequestConfig {
  method?: 'GET' | 'POST' | 'PUT' | 'DELETE'
  headers?: Record<string, string>
  body?: any
}

/**
 * 统一错误处理函数
 * 
 * 将各种错误转换为统一的 ApiError 格式
 * 
 * @param error 原始错误对象
 * @return 格式化的错误信息
 */
function handleError(error: any): ApiError {
  // 网络连接失败
  if (error instanceof TypeError && error.message === 'Failed to fetch') {
    return {
      code: 0,
      message: '网络连接失败，请检查后端服务是否启动'
    }
  }
  
  // HTTP 错误
  if (error.status) {
    return {
      code: error.status,
      message: error.message || '请求失败'
    }
  }
  
  // 其他未知错误
  return {
    code: -1,
    message: error.message || '未知错误'
  }
}

/**
 * 显示错误提示
 * 
 * 在控制台打印错误，并通过 alert 显示给用户
 * 
 * @param error 错误对象
 */
export function showError(error: ApiError): void {
  console.error('API Error:', error)
  
  let errorMessage = error.message
  
  // 如果有字段错误，拼接错误信息
  if (error.data) {
    const fieldErrors = Object.entries(error.data)
      .map(([field, msg]) => `${field}: ${msg}`)
      .join('\n')
    errorMessage += `\n${fieldErrors}`
  }
  
  alert(errorMessage)
}

/**
 * 从 localStorage 获取 Token
 * @return Token 字符串或 null
 */
function getToken(): string | null {
  return localStorage.getItem('electric_grid_token')
}

/**
 * 通用请求函数
 * 
 * 所有 API 请求的核心实现
 * 自动添加 Token 到请求头
 * 统一处理响应和错误
 * 
 * @param url 请求路径（不含基础地址）
 * @param config 请求配置
 * @return Promise<ApiResponse<T>>
 */
async function request<T>(url: string, config: RequestConfig = {}): Promise<ApiResponse<T>> {
  const { method = 'GET', headers = {}, body } = config
  
  // 获取 Token
  const token = getToken()
  
  // 构建请求头
  const defaultHeaders: Record<string, string> = {
    'Content-Type': 'application/json',
    ...headers
  }
  
  // 如果有 Token，添加到请求头
  if (token) {
    defaultHeaders['Authorization'] = `Bearer ${token}`
  }
  
  // 构建请求选项
  const options: RequestInit = {
    method,
    headers: defaultHeaders,
    mode: 'cors'
  }
  
  // 如果有请求体且不是 GET 请求，添加请求体
  if (body && method !== 'GET') {
    options.body = JSON.stringify(body)
  }
  
  try {
    // 发送请求
    const response = await fetch(`${BASE_URL}${url}`, options)
    
    // 解析响应
    const data = await response.json()
    
    // 如果响应不成功，抛出错误
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

/**
 * 发送 GET 请求
 * @param url 请求路径
 * @return Promise<ApiResponse<T>>
 */
export function get<T>(url: string): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'GET' })
}

/**
 * 发送 POST 请求
 * @param url 请求路径
 * @param body 请求体
 * @return Promise<ApiResponse<T>>
 */
export function post<T>(url: string, body: any): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'POST', body })
}

/**
 * 发送 PUT 请求
 * @param url 请求路径
 * @param body 请求体
 * @return Promise<ApiResponse<T>>
 */
export function put<T>(url: string, body: any): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'PUT', body })
}

/**
 * 发送 DELETE 请求
 * @param url 请求路径
 * @return Promise<ApiResponse<T>>
 */
export function del<T>(url: string): Promise<ApiResponse<T>> {
  return request<T>(url, { method: 'DELETE' })
}

// 默认导出
export default {
  get,
  post,
  put,
  del,
  showError
}
