/**
 * api/substation.ts - 变电站 API 服务
 * 
 * 功能说明：
 * 封装变电站相关的 API 请求
 * 提供增删改查操作
 * 
 * 文件关联：
 * - request.ts: 使用封装的请求方法
 * - types/index.ts: 使用 Substation 类型
 * - App.vue: 调用这些 API 方法
 */

import { get, post, put, del } from './request'
import type { ApiResponse, Substation } from '../types'

const BASE_PATH = '/api/substations'

/**
 * 获取所有变电站列表
 */
export async function getAllSubstations(): Promise<ApiResponse<Substation[]>> {
  return get<Substation[]>(BASE_PATH)
}

/**
 * 根据 ID 获取变电站详情
 * @param id 变电站 ID
 */
export async function getSubstationById(id: number): Promise<ApiResponse<Substation>> {
  return get<Substation>(`${BASE_PATH}/${id}`)
}

/**
 * 新增变电站
 * @param substation 变电站数据
 */
export async function createSubstation(substation: Omit<Substation, 'id'>): Promise<ApiResponse<Substation>> {
  return post<Substation>(BASE_PATH, substation)
}

/**
 * 更新变电站
 * @param id 变电站 ID
 * @param substation 更新的变电站数据
 */
export async function updateSubstation(id: number, substation: Partial<Substation>): Promise<ApiResponse<Substation>> {
  return put<Substation>(`${BASE_PATH}/${id}`, substation)
}

/**
 * 删除变电站
 * @param id 变电站 ID
 */
export async function deleteSubstation(id: number): Promise<ApiResponse<void>> {
  return del<void>(`${BASE_PATH}/${id}`)
}

export default {
  getAllSubstations,
  getSubstationById,
  createSubstation,
  updateSubstation,
  deleteSubstation
}
