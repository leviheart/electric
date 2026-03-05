/**
 * stores/settingsStore.ts - 设置状态管理
 * 
 * 功能说明：
 * 使用 Pinia 管理系统设置
 * 设置会持久化到 localStorage
 * 支持中文地图样式
 * 
 * 文件关联：
 * - views/SettingsView.vue: 设置页面
 * - components/MapContainer.vue: 应用设置
 */

import { defineStore } from 'pinia'

interface Settings {
  mapStyle: 'dark' | 'light' | 'satellite'
  defaultZoom: number
  autoLoad: boolean
  showGrid: boolean
  enableAnimations: boolean
  enableNotifications: boolean
  soundEnabled: boolean
}

const DEFAULT_SETTINGS: Settings = {
  mapStyle: 'dark',
  defaultZoom: 12,
  autoLoad: true,
  showGrid: true,
  enableAnimations: true,
  enableNotifications: true,
  soundEnabled: false
}

const SETTINGS_KEY = 'electric_grid_settings'

export const useSettingsStore = defineStore('settings', {
  state: (): Settings => {
    const saved = localStorage.getItem(SETTINGS_KEY)
    if (saved) {
      try {
        return { ...DEFAULT_SETTINGS, ...JSON.parse(saved) }
      } catch {
        return { ...DEFAULT_SETTINGS }
      }
    }
    return { ...DEFAULT_SETTINGS }
  },

  getters: {
    mapStyleUrl: (state) => {
      switch (state.mapStyle) {
        case 'light':
          return 'mapbox://styles/mapbox/light-v11'
        case 'satellite':
          return 'mapbox://styles/mapbox/satellite-streets-v12'
        default:
          return 'mapbox://styles/mapbox/dark-v11'
      }
    },
    mapLanguage: () => 'zh-Hans'
  },

  actions: {
    save() {
      localStorage.setItem(SETTINGS_KEY, JSON.stringify({
        mapStyle: this.mapStyle,
        defaultZoom: this.defaultZoom,
        autoLoad: this.autoLoad,
        showGrid: this.showGrid,
        enableAnimations: this.enableAnimations,
        enableNotifications: this.enableNotifications,
        soundEnabled: this.soundEnabled
      }))
    },

    reset() {
      Object.assign(this, DEFAULT_SETTINGS)
      this.save()
    },

    saveSettings() {
      this.save()
    },

    updateSettings(newSettings: Partial<Settings>) {
      Object.assign(this, newSettings)
      this.save()
    }
  }
})
