/**
 * stores/index.ts - 状态管理统一导出
 * 
 * 功能说明：
 * 统一导出所有 Pinia store
 * 
 * 文件关联：
 * - main.ts: 注册 Pinia
 * - 各组件: 使用导出的 store
 */

export * from './mapStore'
export * from './authStore'
export * from './settingsStore'
export * from './alertStore'
