/**
 * stores/mapStore.ts - 地图状态管理
 * 
 * 功能说明：
 * 使用 Pinia 管理地图相关的全局状态
 * 包括统计数据、图层可见性、加载状态等
 * 
 * 文件关联：
 * - main.ts: 注册 Pinia
 * - App.vue: 使用此 store
 * - components/*.vue: 使用此 store
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
  state: (): MapState => ({
    stats: {
      substations: 0,
      lines: 0,
      areas: 0
    },
    layerVisibility: {
      substations: true,
      lines: true,
      areas: true
    },
    isLoading: false,
    error: null
  }),

  getters: {
    totalItems: (state) => 
      state.stats.substations + state.stats.lines + state.stats.areas,
    
    hasData: (state) => 
      state.stats.substations > 0 || state.stats.lines > 0 || state.stats.areas > 0
  },

  actions: {
    updateStats(newStats: Partial<MapStats>) {
      this.stats = { ...this.stats, ...newStats }
    },

    resetStats() {
      this.stats = {
        substations: 0,
        lines: 0,
        areas: 0
      }
    },

    toggleLayerVisibility(layer: keyof LayerVisibility) {
      this.layerVisibility[layer] = !this.layerVisibility[layer]
    },

    setLayerVisibility(layer: keyof LayerVisibility, visible: boolean) {
      this.layerVisibility[layer] = visible
    },

    setLoading(loading: boolean) {
      this.isLoading = loading
    },

    setError(error: string | null) {
      this.error = error
    },

    clearError() {
      this.error = null
    }
  }
})
