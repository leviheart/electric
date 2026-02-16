/**
 * api/area.ts - 台区 API 服务
 * 
 * 功能说明：
 * 封装台区相关的 API 请求
 * 提供增删改查操作
 * 
 * 文件关联：
 * - request.ts: 使用封装的请求方法
 * - types/index.ts: 使用 Area 类型
 * - App.vue: 调用这些 API 方法
 */

import { get, post, put, del } from './request'
import type { ApiResponse, Area } from '../types'

const BASE_PATH = '/api/areas'

/**
 * 获取所有台区列表
 */
export async function getAllAreas(): Promise<ApiResponse<Area[]>> {
  return get<Area[]>(BASE_PATH)
}

/**
 * 根据 ID 获取台区详情
 * @param id 台区 ID
 */
export async function getAreaById(id: number): Promise<ApiResponse<Area>> {
  return get<Area>(`${BASE_PATH}/${id}`)
}

/**
 * 新增台区
 * @param area 台区数据
 */
export async function createArea(area: Omit<Area, 'id'>): Promise<ApiResponse<Area>> {
  return post<Area>(BASE_PATH, area)
}

/**
 * 更新台区
 * @param id 台区 ID
 * @param area 更新的台区数据
 */
export async function updateArea(id: number, area: Partial<Area>): Promise<ApiResponse<Area>> {
  return put<Area>(`${BASE_PATH}/${id}`, area)
}

/**
 * 删除台区
 * @param id 台区 ID
 */
export async function deleteArea(id: number): Promise<ApiResponse<void>> {
  return del<void>(`${BASE_PATH}/${id}`)
}

export default {
  getAllAreas,
  getAreaById,
  createArea,
  updateArea,
  deleteArea
}
