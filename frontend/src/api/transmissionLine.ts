/**
 * api/transmissionLine.ts - 输电线路 API 服务
 * 
 * 功能说明：
 * 封装输电线路相关的 API 请求
 * 提供增删改查操作
 * 
 * 文件关联：
 * - request.ts: 使用封装的请求方法
 * - types/index.ts: 使用 TransmissionLine 类型
 * - App.vue: 调用这些 API 方法
 */

import { get, post, put, del } from './request'
import type { ApiResponse, TransmissionLine } from '../types'

const BASE_PATH = '/api/transmission-lines'

/**
 * 获取所有输电线路列表
 */
export async function getAllTransmissionLines(): Promise<ApiResponse<TransmissionLine[]>> {
  return get<TransmissionLine[]>(BASE_PATH)
}

/**
 * 根据 ID 获取输电线路详情
 * @param id 输电线路 ID
 */
export async function getTransmissionLineById(id: number): Promise<ApiResponse<TransmissionLine>> {
  return get<TransmissionLine>(`${BASE_PATH}/${id}`)
}

/**
 * 新增输电线路
 * @param line 输电线路数据
 */
export async function createTransmissionLine(line: Omit<TransmissionLine, 'id'>): Promise<ApiResponse<TransmissionLine>> {
  return post<TransmissionLine>(BASE_PATH, line)
}

/**
 * 更新输电线路
 * @param id 输电线路 ID
 * @param line 更新的输电线路数据
 */
export async function updateTransmissionLine(id: number, line: Partial<TransmissionLine>): Promise<ApiResponse<TransmissionLine>> {
  return put<TransmissionLine>(`${BASE_PATH}/${id}`, line)
}

/**
 * 删除输电线路
 * @param id 输电线路 ID
 */
export async function deleteTransmissionLine(id: number): Promise<ApiResponse<void>> {
  return del<void>(`${BASE_PATH}/${id}`)
}

export default {
  getAllTransmissionLines,
  getTransmissionLineById,
  createTransmissionLine,
  updateTransmissionLine,
  deleteTransmissionLine
}
