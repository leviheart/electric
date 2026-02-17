/**
 * stores/alertStore.ts - 告警状态管理
 * 
 * 功能说明：
 * 使用 Pinia 管理告警状态
 * 支持 WebSocket 实时接收告警
 * 提供告警的增删改查操作
 * 
 * 状态结构：
 * - alerts: 告警列表
 * - stats: 告警统计
 * - connected: WebSocket 连接状态
 * - soundEnabled: 声音提醒开关
 * 
 * 文件关联：
 * - types/alert.ts: 告警类型定义
 * - api/alert.ts: 告警 API 服务
 * - components/AlertPanel.vue: 告警面板组件
 */

import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Alert, AlertStats } from '../types/alert'
import { AlertStatus, AlertLevel } from '../types/alert'
import { getAllAlerts, getAlertStats, acknowledgeAlert, resolveAlert, deleteAlert } from '../api/alert'

export const useAlertStore = defineStore('alert', () => {
  const alerts = ref<Alert[]>([])
  const stats = ref<AlertStats>({
    total: 0,
    active: 0,
    acknowledged: 0,
    resolved: 0,
    critical: 0,
    high: 0,
    medium: 0,
    low: 0
  })
  const connected = ref(false)
  const soundEnabled = ref(true)
  const showPanel = ref(false)

  const activeAlerts = computed(() => 
    alerts.value.filter(a => a.status === AlertStatus.ACTIVE)
  )

  const criticalAlerts = computed(() =>
    alerts.value.filter(a => a.level === AlertLevel.CRITICAL && a.status === AlertStatus.ACTIVE)
  )

  const hasActiveAlerts = computed(() => activeAlerts.value.length > 0)

  const hasCriticalAlerts = computed(() => criticalAlerts.value.length > 0)

  async function fetchAlerts() {
    try {
      const response = await getAllAlerts()
      alerts.value = response.data || []
    } catch (error) {
      console.error('获取告警列表失败:', error)
    }
  }

  async function fetchStats() {
    try {
      const response = await getAlertStats()
      stats.value = response.data
    } catch (error) {
      console.error('获取告警统计失败:', error)
    }
  }

  async function ackAlert(id: string, username?: string) {
    try {
      const response = await acknowledgeAlert(id, username)
      const index = alerts.value.findIndex(a => a.id === id)
      if (index !== -1) {
        alerts.value[index] = response.data
      }
      await fetchStats()
    } catch (error) {
      console.error('确认告警失败:', error)
    }
  }

  async function resolveAlertById(id: string) {
    try {
      const response = await resolveAlert(id)
      const index = alerts.value.findIndex(a => a.id === id)
      if (index !== -1) {
        alerts.value[index] = response.data
      }
      await fetchStats()
    } catch (error) {
      console.error('解决告警失败:', error)
    }
  }

  async function removeAlert(id: string) {
    try {
      await deleteAlert(id)
      alerts.value = alerts.value.filter(a => a.id !== id)
      await fetchStats()
    } catch (error) {
      console.error('删除告警失败:', error)
    }
  }

  function addAlert(alert: Alert) {
    const index = alerts.value.findIndex(a => a.id === alert.id)
    if (index === -1) {
      alerts.value.unshift(alert)
      stats.value.total++
      if (alert.status === AlertStatus.ACTIVE) {
        stats.value.active++
      }
      if (soundEnabled.value) {
        playAlertSound(alert.level)
      }
    }
  }

  function updateAlert(alert: Alert) {
    const index = alerts.value.findIndex(a => a.id === alert.id)
    if (index !== -1) {
      alerts.value[index] = alert
    }
  }

  function removeAlertById(id: string) {
    alerts.value = alerts.value.filter(a => a.id !== id)
  }

  function playAlertSound(level: AlertLevel) {
    if (level === AlertLevel.CRITICAL || level === AlertLevel.HIGH) {
      try {
        const audioContext = new (window.AudioContext || (window as any).webkitAudioContext)()
        const oscillator = audioContext.createOscillator()
        const gainNode = audioContext.createGain()
        
        oscillator.connect(gainNode)
        gainNode.connect(audioContext.destination)
        
        oscillator.frequency.value = level === AlertLevel.CRITICAL ? 880 : 660
        oscillator.type = 'sine'
        
        gainNode.gain.setValueAtTime(0.3, audioContext.currentTime)
        gainNode.gain.exponentialRampToValueAtTime(0.01, audioContext.currentTime + 0.5)
        
        oscillator.start(audioContext.currentTime)
        oscillator.stop(audioContext.currentTime + 0.5)
      } catch (e) {
        console.log('播放告警声音失败:', e)
      }
    }
  }

  function togglePanel() {
    showPanel.value = !showPanel.value
  }

  function toggleSound() {
    soundEnabled.value = !soundEnabled.value
  }

  function setConnected(value: boolean) {
    connected.value = value
  }

  return {
    alerts,
    stats,
    connected,
    soundEnabled,
    showPanel,
    activeAlerts,
    criticalAlerts,
    hasActiveAlerts,
    hasCriticalAlerts,
    fetchAlerts,
    fetchStats,
    ackAlert,
    resolveAlertById,
    removeAlert,
    addAlert,
    updateAlert,
    removeAlertById,
    playAlertSound,
    togglePanel,
    toggleSound,
    setConnected
  }
})
