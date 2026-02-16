/**
 * stores/mapStore.ts - 地图状态管理
 * 
 * 功能说明：
 * 使用 Pinia 管理地图相关的全局状态
 * 包括统计数据、图层可见性、加载状态等
 * 
 * 状态说明：
 * - stats: 地图统计数据（变电站、线路、台区数量）
 * - layerVisibility: 图层显示/隐藏状态
 * - isLoading: 数据加载中状态
 * - error: 错误信息
 * 
 * Getters 说明：
 * - totalItems: 总数据条目数
 * - hasData: 是否有数据
 * 
 * Actions 说明：
 * - updateStats: 更新统计数据
 * - resetStats: 重置统计数据
 * - toggleLayerVisibility: 切换图层可见性
 * - setLayerVisibility: 设置图层可见性
 * - setLoading: 设置加载状态
 * - setError: 设置错误信息
 * 
 * 文件关联：
 * - main.ts: 注册 Pinia
 * - App.vue: 使用此 store
 * - components/StatsPanel.vue: 显示统计数据
 * - components/MapContainer.vue: 使用图层可见性状态
 * 
 * 新人提示：
 * - Pinia 是 Vue 3 推荐的状态管理库
 * - defineStore 第一个参数是 store 的唯一标识
 * - state 使用箭头函数返回初始状态
 * - getters 用于派生状态
 * - actions 用于修改状态
 */

import { defineStore } from 'pinia'
import type { MapStats, LayerVisibility } from '../types'

interface MapState {
  stats: MapStats
  layerVisibility: LayerVisibility
  isLoading: boolean
  error: string | null
}

export const useMapStore = defineStore('map', {
  /**
   * 状态定义
   * 使用箭头函数返回初始状态对象
   */
  state: (): MapState => ({
    // 统计数据
    stats: {
      substations: 0,
      lines: 0,
      areas: 0
    },
    // 图层可见性
    layerVisibility: {
      substations: true,
      lines: true,
      areas: true
    },
    // 加载状态
    isLoading: false,
    // 错误信息
    error: null
  }),

  /**
   * 计算属性
   * 用于派生状态，类似 Vue 的 computed
   */
  getters: {
    /**
     * 计算总数据条目数
     * @param state 状态对象
     * @return 总条目数
     */
    totalItems: (state) => 
      state.stats.substations + state.stats.lines + state.stats.areas,
    
    /**
     * 判断是否有数据
     * @param state 状态对象
     * @return 是否有数据
     */
    hasData: (state) => 
      state.stats.substations > 0 || state.stats.lines > 0 || state.stats.areas > 0
  },

  /**
   * 操作方法
   * 用于修改状态，类似 Vue 的 methods
   */
  actions: {
    /**
     * 更新统计数据
     * @param newStats 新的统计数据（部分更新）
     */
    updateStats(newStats: Partial<MapStats>) {
      this.stats = { ...this.stats, ...newStats }
    },

    /**
     * 重置统计数据
     * 将所有统计值归零
     */
    resetStats() {
      this.stats = {
        substations: 0,
        lines: 0,
        areas: 0
      }
    },

    /**
     * 切换图层可见性
     * @param layer 图层类型
     */
    toggleLayerVisibility(layer: keyof LayerVisibility) {
      this.layerVisibility[layer] = !this.layerVisibility[layer]
    },

    /**
     * 设置图层可见性
     * @param layer 图层类型
     * @param visible 是否可见
     */
    setLayerVisibility(layer: keyof LayerVisibility, visible: boolean) {
      this.layerVisibility[layer] = visible
    },

    /**
     * 设置加载状态
     * @param loading 是否加载中
     */
    setLoading(loading: boolean) {
      this.isLoading = loading
    },

    /**
     * 设置错误信息
     * @param error 错误信息
     */
    setError(error: string | null) {
      this.error = error
    },

    /**
     * 清除错误信息
     */
    clearError() {
      this.error = null
    }
  }
})
