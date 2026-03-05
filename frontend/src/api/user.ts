import request from './request'
import type { ApiResponse } from '../types'

export interface UserInfo {
  id: number
  username: string
  role: string
  enabled: boolean
}

export interface UpdateUserRequest {
  username?: string
  role?: string
}

export interface ChangePasswordRequest {
  oldPassword: string
  newPassword: string
}

export function getUsers(): Promise<ApiResponse<UserInfo[]>> {
  return request.get('/api/users')
}

export function getUserById(id: number): Promise<ApiResponse<UserInfo>> {
  return request.get(`/api/users/${id}`)
}

export function updateUser(id: number, data: UpdateUserRequest): Promise<ApiResponse<UserInfo>> {
  return request.put(`/api/users/${id}`, data)
}

export function deleteUser(id: number): Promise<ApiResponse<void>> {
  return request.del(`/api/users/${id}`)
}

export function changePassword(id: number, data: ChangePasswordRequest): Promise<ApiResponse<void>> {
  return request.put(`/api/users/${id}/password`, data)
}

export function toggleUserEnable(id: number, enabled: boolean): Promise<ApiResponse<void>> {
  return request.put(`/api/users/${id}/enable`, { enabled })
}
