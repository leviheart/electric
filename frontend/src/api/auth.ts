/**
 * api/auth.ts - 认证 API 服务
 * 
 * 功能说明：
 * 封装认证相关的 API 请求
 * 包括登录、注册、登出等操作
 * 
 * 文件关联：
 * - request.ts: 使用封装的请求方法
 * - types/index.ts: 使用类型定义
 */

import { post, get } from './request'
import type { ApiResponse } from '../types'

interface LoginRequest {
  username: string
  password: string
}

interface LoginResponse {
  token: string
  username: string
  role: string
  expiresIn: number
}

interface UserInfo {
  username: string
  role: string
}

/**
 * 用户登录
 * @param data 登录请求
 */
export async function login(data: LoginRequest): Promise<ApiResponse<LoginResponse>> {
  return post<LoginResponse>('/api/auth/login', data)
}

/**
 * 用户注册
 * @param data 注册请求
 */
export async function register(data: LoginRequest): Promise<ApiResponse<void>> {
  return post<void>('/api/auth/register', data)
}

/**
 * 获取当前用户信息
 */
export async function getCurrentUser(): Promise<ApiResponse<UserInfo>> {
  return get<UserInfo>('/api/auth/me')
}

export default {
  login,
  register,
  getCurrentUser
}
