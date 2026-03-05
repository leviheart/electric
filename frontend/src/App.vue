<template>
  <div class="app-container">
    <div class="grid-overlay" :class="{ 'no-grid': !settingsStore.showGrid }"></div>
    <nav class="app-nav">
      <div class="nav-left">
        <router-link to="/" class="nav-link" active-class="active">首页</router-link>
        <router-link to="/users" class="nav-link" active-class="active">用户管理</router-link>
        <router-link to="/about" class="nav-link" active-class="active">关于</router-link>
        <router-link to="/settings" class="nav-link" active-class="active">设置</router-link>
      </div>
      <div class="nav-center">
        <h1 class="app-title">电网地图系统</h1>
        <SearchBox
          v-if="showSearch"
          :show-filters="true"
          @select="handleSearchSelect"
          @clear="handleSearchClear"
        />
      </div>
      <div class="nav-right">
        <div v-if="showSearch" class="filter-group">
          <el-select
            v-model="filterStore.voltageLevel"
            placeholder="电压等级"
            size="small"
            clearable
            @change="handleFilterChange"
          >
            <el-option label="220kV" value="220kV" />
            <el-option label="110kV" value="110kV" />
            <el-option label="35kV" value="35kV" />
          </el-select>
          <el-select
            v-model="filterStore.status"
            placeholder="运行状态"
            size="small"
            clearable
            @change="handleFilterChange"
          >
            <el-option label="运行中" value="运行中" />
            <el-option label="备用" value="备用" />
            <el-option label="检修中" value="检修中" />
          </el-select>
        </div>
        <span v-if="authStore.isAuthenticated" class="user-info">
          {{ authStore.username }}
        </span>
        <el-button
          v-if="authStore.isAuthenticated"
          type="danger"
          size="small"
          @click="handleLogout"
        >
          登出
        </el-button>
      </div>
    </nav>
    <main class="app-main">
      <router-view />
    </main>
    <AlertPanel v-if="showSearch" @locate="handleAlertLocate" />
  </div>
</template>

<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'
import { computed, ref } from 'vue'
import { useAuthStore } from './stores/authStore'
import { useSettingsStore } from './stores/settingsStore'
import { useSearchStore } from './stores/searchStore'
import { useFilterStore } from './stores/filterStore'
import { useAlertStore } from './stores/alertStore'
import SearchBox from './components/SearchBox.vue'
import AlertPanel from './components/AlertPanel.vue'
import type { Alert } from './types/alert'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()
const settingsStore = useSettingsStore()
const searchStore = useSearchStore()
const filterStore = useFilterStore()
const alertStore = useAlertStore()

const showSearch = computed(() => route.path === '/')

const handleLogout = () => {
  authStore.logout()
  router.push('/login')
}

const handleSearchSelect = (result: any) => {
  searchStore.selectResult(result)
}

const handleSearchClear = () => {
  searchStore.clearResult()
}

const handleFilterChange = () => {
  filterStore.setFilter(filterStore.voltageLevel, filterStore.status)
}

const mapRef = ref<any>(null)

const handleAlertLocate = (alert: Alert) => {
  if (alert.latitude && alert.longitude) {
    searchStore.selectResult({
      type: 'alert',
      data: alert
    })
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  width: 100%;
  height: 100%;
  overflow: hidden;
}

#app {
  width: 100%;
  height: 100%;
}

::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 4px;
}

::-webkit-scrollbar-thumb {
  background: linear-gradient(180deg, rgba(0, 240, 255, 0.3), rgba(0, 128, 255, 0.3));
  border-radius: 4px;
  transition: background 0.3s ease;
}

::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(180deg, rgba(0, 240, 255, 0.5), rgba(0, 128, 255, 0.5));
}

::-webkit-scrollbar-corner {
  background: rgba(0, 0, 0, 0.2);
}

.el-table__body-wrapper::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

.el-select-dropdown__wrap::-webkit-scrollbar {
  width: 6px;
}
</style>

<style scoped>
.app-container {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a1a2e 50%, #16213e 100%);
  color: #e0e0e0;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  position: relative;
  overflow: hidden;
  display: flex;
  flex-direction: column;
}

.grid-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: 
    linear-gradient(rgba(0, 240, 255, 0.1) 1px, transparent 1px),
    linear-gradient(90deg, rgba(0, 240, 255, 0.1) 1px, transparent 1px);
  background-size: 50px 50px;
  pointer-events: none;
  z-index: 0;
  transition: opacity 0.3s ease;
}

.grid-overlay.no-grid {
  opacity: 0;
}

.app-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 20px;
  background: rgba(10, 10, 26, 0.95);
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  position: relative;
  z-index: 10;
  flex-shrink: 0;
}

.nav-left {
  display: flex;
  gap: 15px;
  flex: 1;
}

.nav-center {
  display: flex;
  align-items: center;
  gap: 20px;
  flex: 2;
  justify-content: center;
}

.app-title {
  font-size: 1.3rem;
  margin: 0;
  color: #00f0ff;
  text-shadow: 0 0 20px rgba(0, 240, 255, 0.5);
  letter-spacing: 4px;
  font-weight: 300;
  white-space: nowrap;
}

.nav-center :deep(.search-box) {
  max-width: 280px;
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 15px;
  flex: 1;
  justify-content: flex-end;
}

.filter-group {
  display: flex;
  gap: 10px;
}

.filter-group :deep(.el-select) {
  width: 100px;
}

.filter-group :deep(.el-input__wrapper) {
  background: rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 4px;
  box-shadow: none;
}

.filter-group :deep(.el-input__inner) {
  color: #ffffff;
  font-size: 12px;
}

.filter-group :deep(.el-input__inner::placeholder) {
  color: rgba(255, 255, 255, 0.5);
}

.nav-link {
  color: #a0a0a0;
  text-decoration: none;
  padding: 6px 14px;
  border-radius: 4px;
  transition: all 0.3s ease;
  font-size: 0.9rem;
}

.nav-link:hover {
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.1);
}

.nav-link.active {
  color: #00f0ff;
  background: rgba(0, 240, 255, 0.2);
  border: 1px solid rgba(0, 240, 255, 0.5);
}

.user-info {
  color: #00f0ff;
  font-size: 0.85rem;
}

.app-main {
  flex: 1;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

:global(.custom-popup) {
  background: rgba(10, 10, 26, 0.95);
  border: 1px solid rgba(0, 240, 255, 0.5);
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.3);
  color: #e0e0e0;
  max-width: 300px;
}

:global(.custom-popup .mapboxgl-popup-content) {
  background: transparent;
  border: none;
  box-shadow: none;
  padding: 0;
}

:global(.popup-header) {
  padding: 15px;
  border-bottom: 1px solid rgba(0, 240, 255, 0.3);
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.1), rgba(0, 128, 255, 0.1));
  border-radius: 8px 8px 0 0;
}

:global(.popup-header h3) {
  margin: 0 0 10px 0;
  color: #00f0ff;
  font-size: 1.2rem;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

:global(.status-indicator) {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
}

:global(.status-active) {
  background: rgba(0, 255, 128, 0.2);
  color: #00ff80;
  border: 1px solid rgba(0, 255, 128, 0.5);
}

:global(.status-inactive) {
  background: rgba(255, 0, 128, 0.2);
  color: #ff0080;
  border: 1px solid rgba(255, 0, 128, 0.5);
}

:global(.popup-content) {
  padding: 15px;
}

:global(.info-row) {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

:global(.info-label) {
  color: #a0a0a0;
  font-size: 0.9rem;
}

:global(.info-value) {
  color: #e0e0e0;
  font-weight: 600;
}

:global(.popup-footer) {
  padding: 10px 15px;
  border-top: 1px solid rgba(0, 240, 255, 0.3);
  background: rgba(0, 240, 255, 0.05);
  border-radius: 0 0 8px 8px;
}

:global(.popup-footer small) {
  color: #a0a0a0;
  font-size: 0.8rem;
}

@media (max-width: 1920px) {
  .app-nav {
    padding: 6px 15px;
  }
  
  .app-title {
    font-size: 1.1rem;
  }
  
  .nav-center :deep(.search-box) {
    max-width: 250px;
  }
  
  .filter-group :deep(.el-select) {
    width: 90px;
  }
}

@media (min-width: 3840px) {
  .app-nav {
    padding: 12px 40px;
  }
  
  .app-title {
    font-size: 1.6rem;
  }
  
  .nav-link {
    font-size: 1.1rem;
    padding: 8px 18px;
  }
  
  .nav-center :deep(.search-box) {
    max-width: 350px;
  }
  
  .filter-group :deep(.el-select) {
    width: 120px;
  }
}
</style>
