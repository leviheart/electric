<template>
  <div class="quick-entry">
    <transition name="fade">
      <div class="entry-panel" v-show="!collapsed">
        <div class="panel-header">
          <div class="header-left">
            <span class="panel-title">功能导航</span>
            <span class="panel-subtitle">快速访问系统功能</span>
          </div>
          <el-button text size="small" @click="collapsed = true" class="close-btn">
            <el-icon><Close /></el-icon>
          </el-button>
        </div>
        
        <div class="panel-content">
          <div class="menu-section" v-for="section in menuSections" :key="section.title">
            <div class="section-title">
              <el-icon :size="14"><component :is="section.icon" /></el-icon>
              <span>{{ section.title }}</span>
            </div>
            <div class="section-items">
              <div 
                v-for="item in section.items" 
                :key="item.path" 
                class="entry-item"
                :class="{ active: isActive(item), disabled: item.disabled }"
                @click="!item.disabled && navigate(item)"
              >
                <div class="item-left">
                  <div class="item-icon" :style="{ background: item.gradient }">
                    <el-icon :size="20"><component :is="item.icon" /></el-icon>
                  </div>
                  <div class="item-info">
                    <div class="item-header">
                      <span class="item-label">{{ item.label }}</span>
                      <span class="item-badge" v-if="item.badge">{{ item.badge }}</span>
                      <span class="item-alert" v-if="item.alertCount">{{ item.alertCount }}</span>
                      <span class="current-tag" v-if="isActive(item)">当前</span>
                      <el-icon v-if="item.disabled" :size="14" class="lock-icon"><Lock /></el-icon>
                    </div>
                    <span class="item-desc">{{ item.desc }}</span>
                  </div>
                </div>
                <div class="item-right">
                  <el-icon class="arrow-icon"><ArrowRight /></el-icon>
                </div>
              </div>
            </div>
          </div>
          
          <div class="recommend-section" v-if="recommendItems.length > 0">
            <div class="section-title recommend-title">
              <el-icon :size="14"><Star /></el-icon>
              <span>推荐操作</span>
            </div>
            <div class="recommend-items">
              <div 
                class="recommend-item" 
                v-for="(rec, index) in recommendItems" 
                :key="index"
                @click="handleRecommend(rec)"
              >
                <span class="recommend-icon">{{ rec.icon }}</span>
                <span class="recommend-text">{{ rec.text }}</span>
              </div>
            </div>
          </div>
        </div>
        
        <div class="panel-footer">
          <div class="tip-item">
            <el-icon :size="12"><InfoFilled /></el-icon>
            <span>点击菜单项快速跳转到对应功能页面</span>
          </div>
        </div>
      </div>
    </transition>
    
    <div class="entry-trigger" @click="collapsed = !collapsed" :class="{ expanded: !collapsed }">
      <el-icon :size="22"><Menu /></el-icon>
      <transition name="text-fade">
        <span class="trigger-text" v-if="collapsed">功能导航</span>
      </transition>
      <div class="trigger-badge" v-if="collapsed && hasNewFeatures">
        <span>新</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, inject } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { 
  Menu, Close, Location, DataLine, Setting, Tickets, TrendCharts, Folder, User,
  ArrowRight, InfoFilled, Lock, Monitor, Operation, Document, Star, Connection, Guide, Warning
} from '@element-plus/icons-vue'
import { useAlertStore } from '../stores/alertStore'

interface MenuItem {
  path: string
  label: string
  desc: string
  gradient: string
  icon: any
  badge?: string
  alertCount?: string
  disabled?: boolean
  isPanel?: boolean
}

interface MenuSection {
  title: string
  icon: any
  items: MenuItem[]
}

const router = useRouter()
const route = useRoute()
const collapsed = ref(true)
const hasNewFeatures = ref(false)
const alertStore = useAlertStore()

const toggleStateSwitcher = inject<() => void>('toggleStateSwitcher')
const toggleDisasterPanel = inject<() => void>('toggleDisasterPanel')
const closeDisasterPanel = inject<() => void>('closeDisasterPanel')

const menuSections = computed<MenuSection[]>(() => [
  {
    title: '监控中心',
    icon: Monitor,
    items: [
      { path: '/', label: '电网地图', desc: '实时监控电网设备运行状态，查看设备分布和告警信息', gradient: 'linear-gradient(135deg, #00f0ff 0%, #0080ff 100%)', icon: Location },
      { path: '/dashboard', label: '数据统计', desc: '查看电网运行数据统计报表和可视化图表', gradient: 'linear-gradient(135deg, #00ff80 0%, #00c853 100%)', icon: DataLine, alertCount: alertStore.alerts.filter(a => a.status === 'ACTIVE').length > 0 ? `${alertStore.alerts.filter(a => a.status === 'ACTIVE').length}🔴` : '' }
    ]
  },
  {
    title: '运维管理',
    icon: Operation,
    items: [
      { path: '/manage', label: '数据管理', desc: '管理变电站、输电线路、供电区域等基础数据', gradient: 'linear-gradient(135deg, #ff9800 0%, #ff5722 100%)', icon: Setting },
      { path: '/operation', label: '运维管理', desc: '检修计划、工作票、操作票、巡检任务等运维工作', gradient: 'linear-gradient(135deg, #e040fb 0%, #7c4dff 100%)', icon: Tickets },
      { path: '/analysis', label: '分析计算', desc: '潮流计算、N-1校验、负荷预测、线损分析等专业计算', gradient: 'linear-gradient(135deg, #ff4081 0%, #f50057 100%)', icon: TrendCharts }
    ]
  },
  {
    title: '高级功能',
    icon: TrendCharts,
    items: [
      { path: 'state-switcher', label: '一图四态', desc: '现状态、计划态、预想态、历史态四态切换展示', gradient: 'linear-gradient(135deg, #ff6b6b 0%, #feca57 100%)', icon: TrendCharts, badge: '核心', isPanel: true },
      { path: '/dual-power', label: '双电源管理', desc: '管理重要用户双电源配置，查看供电可靠性', gradient: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', icon: Connection, badge: '新' },
      { path: '/power-tracing', label: '电源追溯', desc: '追溯设备供电来源，分析供电路径和拓扑关系', gradient: 'linear-gradient(135deg, #11998e 0%, #38ef7d 100%)', icon: Guide, badge: '新' },
      { path: 'natural-disaster', label: '自然灾害预警', desc: '大风、山火、冰寒、洪涝等自然灾害监测预警', gradient: 'linear-gradient(135deg, #ff5722 0%, #ff9800 100%)', icon: Warning, badge: '新', isPanel: true }
    ]
  },
  {
    title: '系统管理',
    icon: Document,
    items: [
      { path: '/resource', label: '资源管理', desc: '文档管理、视频监控、数据初始化等资源功能', gradient: 'linear-gradient(135deg, #ffc107 0%, #ff9800 100%)', icon: Folder },
      { path: '/users', label: '用户管理', desc: '管理系统用户、角色权限和账户安全设置', gradient: 'linear-gradient(135deg, #64ffda 0%, #00bfa5 100%)', icon: User }
    ]
  }
])

const recommendItems = computed(() => {
  const items = []
  const activeAlerts = alertStore.alerts.filter(a => a.status === 'ACTIVE')
  
  if (activeAlerts.length > 0) {
    items.push({
      icon: '🔴',
      text: `处理 ${activeAlerts.length} 条告警`,
      action: 'alerts'
    })
  }
  
  items.push({
    icon: '📋',
    text: '查看今日巡检任务',
    action: 'inspection'
  })
  
  return items
})

const isActive = (item: MenuItem) => {
  if (item.isPanel) return false
  return route.path === item.path
}

const navigate = (item: MenuItem) => { 
  if (item.isPanel && item.path === 'state-switcher') {
    closeDisasterPanel?.()
    toggleStateSwitcher?.()
    collapsed.value = true
    return
  }
  if (item.isPanel && item.path === 'natural-disaster') {
    toggleDisasterPanel?.()
    collapsed.value = true
    return
  }
  closeDisasterPanel?.()
  router.push(item.path)
  collapsed.value = true 
}

const handleRecommend = (rec: any) => {
  switch (rec.action) {
    case 'alerts':
      router.push('/dashboard')
      break
    case 'inspection':
      router.push('/operation?tab=inspection')
      break
  }
  collapsed.value = true
}
</script>

<style scoped>
.quick-entry {
  position: absolute;
  left: 20px;
  bottom: 120px;
  z-index: 100;
}

.entry-trigger {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 14px 22px;
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.4);
  border-radius: 28px;
  cursor: pointer;
  color: #00f0ff;
  transition: all 0.3s ease;
  backdrop-filter: blur(10px);
  box-shadow: 0 4px 20px rgba(0, 240, 255, 0.2);
  position: relative;
}

.entry-trigger:hover {
  background: rgba(0, 240, 255, 0.15);
  border-color: rgba(0, 240, 255, 0.6);
  box-shadow: 0 6px 30px rgba(0, 240, 255, 0.4);
  transform: translateY(-2px);
}

.entry-trigger.expanded {
  background: rgba(0, 240, 255, 0.2);
}

.trigger-text {
  font-size: 14px;
  font-weight: 500;
  letter-spacing: 1px;
}

.trigger-badge {
  position: absolute;
  top: -6px;
  right: -6px;
  background: linear-gradient(135deg, #ff4081, #f50057);
  color: white;
  font-size: 10px;
  padding: 2px 6px;
  border-radius: 10px;
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% { transform: scale(1); }
  50% { transform: scale(1.1); }
}

.entry-panel {
  position: absolute;
  bottom: 65px;
  left: 0;
  width: 380px;
  background: rgba(10, 10, 26, 0.98);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 16px;
  backdrop-filter: blur(15px);
  overflow: hidden;
  box-shadow: 0 8px 40px rgba(0, 0, 0, 0.5), 0 0 60px rgba(0, 240, 255, 0.15);
}

.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background: linear-gradient(90deg, rgba(0, 240, 255, 0.1) 0%, transparent 100%);
  border-bottom: 1px solid rgba(0, 240, 255, 0.15);
}

.header-left {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.panel-title {
  font-size: 15px;
  font-weight: 600;
  color: #00f0ff;
  letter-spacing: 2px;
}

.panel-subtitle {
  font-size: 11px;
  color: #6a7a8a;
}

.close-btn {
  color: #6a7a8a !important;
  padding: 6px !important;
}

.close-btn:hover {
  color: #00f0ff !important;
}

.panel-content {
  padding: 12px;
  max-height: 420px;
  overflow-y: auto;
}

.panel-content::-webkit-scrollbar {
  width: 4px;
}

.panel-content::-webkit-scrollbar-thumb {
  background: rgba(0, 240, 255, 0.3);
  border-radius: 2px;
}

.menu-section {
  margin-bottom: 12px;
}

.menu-section:last-child {
  margin-bottom: 0;
}

.section-title {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 12px;
  font-size: 11px;
  color: #6a7a8a;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.section-items {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.entry-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 14px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.25s ease;
  border: 1px solid transparent;
}

.entry-item:hover:not(.disabled) {
  background: rgba(0, 240, 255, 0.08);
  border-color: rgba(0, 240, 255, 0.2);
}

.entry-item.active {
  background: rgba(0, 240, 255, 0.12);
  border-color: rgba(0, 240, 255, 0.4);
}

.entry-item.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.item-left {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
  min-width: 0;
}

.item-icon {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
  transition: all 0.25s ease;
  box-shadow: 0 3px 12px rgba(0, 0, 0, 0.3);
}

.entry-item:hover:not(.disabled) .item-icon {
  transform: scale(1.08);
}

.entry-item.active .item-icon {
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.5);
}

.item-info {
  display: flex;
  flex-direction: column;
  gap: 3px;
  flex: 1;
  min-width: 0;
}

.item-header {
  display: flex;
  align-items: center;
  gap: 6px;
}

.item-label {
  font-size: 14px;
  color: #e8e8e8;
  font-weight: 500;
}

.item-badge {
  background: linear-gradient(135deg, #ff4081, #f50057);
  color: white;
  font-size: 10px;
  padding: 1px 6px;
  border-radius: 8px;
}

.item-alert {
  background: linear-gradient(135deg, #ff4444, #cc0000);
  color: white;
  font-size: 10px;
  padding: 1px 6px;
  border-radius: 8px;
  animation: pulse-alert 2s infinite;
}

@keyframes pulse-alert {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.7; }
}

.current-tag {
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  color: white;
  font-size: 10px;
  padding: 1px 6px;
  border-radius: 8px;
}

.lock-icon {
  color: #6a7a8a;
}

.item-desc {
  font-size: 11px;
  color: #6a7a8a;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.item-right {
  flex-shrink: 0;
  margin-left: 8px;
}

.arrow-icon {
  color: #4a5568;
  transition: all 0.2s ease;
}

.entry-item:hover:not(.disabled) .arrow-icon {
  color: #00f0ff;
  transform: translateX(3px);
}

.recommend-section {
  margin-top: 12px;
  padding-top: 12px;
  border-top: 1px solid rgba(0, 240, 255, 0.1);
}

.recommend-title {
  color: #ffc800;
}

.recommend-items {
  display: flex;
  flex-direction: column;
  gap: 6px;
  padding: 0 8px;
}

.recommend-item {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 12px;
  background: rgba(255, 200, 0, 0.05);
  border: 1px solid rgba(255, 200, 0, 0.2);
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.25s ease;
}

.recommend-item:hover {
  background: rgba(255, 200, 0, 0.1);
  border-color: rgba(255, 200, 0, 0.4);
  transform: translateX(4px);
}

.recommend-icon {
  font-size: 14px;
}

.recommend-text {
  font-size: 13px;
  color: #e8e8e8;
}

.panel-footer {
  padding: 12px 16px;
  background: rgba(0, 240, 255, 0.03);
  border-top: 1px solid rgba(0, 240, 255, 0.1);
}

.tip-item {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 11px;
  color: #6a7a8a;
}

.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(10px) scale(0.95);
}

.text-fade-enter-active,
.text-fade-leave-active {
  transition: all 0.2s ease;
}

.text-fade-enter-from,
.text-fade-leave-to {
  opacity: 0;
  width: 0;
}
</style>
