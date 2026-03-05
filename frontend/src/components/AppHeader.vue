<template>
  <header class="app-header">
    <div class="header-left">
      <el-menu mode="horizontal" :ellipsis="false" background-color="transparent" text-color="#00f0ff" active-text-color="#00ff80" :default-active="activeMenu">
        <el-menu-item index="/" @click="navigate('/')">地图</el-menu-item>
        <el-menu-item index="/dashboard" @click="navigate('/dashboard')">统计</el-menu-item>
        <el-menu-item index="/manage" @click="navigate('/manage')">管理</el-menu-item>
        <el-menu-item index="/operation" @click="navigate('/operation')">运维</el-menu-item>
        <el-menu-item index="/analysis" @click="navigate('/analysis')">分析</el-menu-item>
        <el-menu-item index="/resource" @click="navigate('/resource')">资源</el-menu-item>
        <el-menu-item index="/users" @click="navigate('/users')">用户</el-menu-item>
      </el-menu>
    </div>
    <h1 class="app-title">电网地图系统</h1>
    <div class="header-right">
      <SearchBox :show-filters="true" @select="$emit('search-select', $event)" @clear="$emit('search-clear')" />
    </div>
  </header>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import SearchBox from './SearchBox.vue'

const router = useRouter()
const route = useRoute()

const activeMenu = computed(() => route.path)

const navigate = (path: string) => {
  router.push(path)
}

defineEmits<{
  'search-select': [result: any]
  'search-clear': []
}>()
</script>

<style scoped>
.app-header {
  flex-shrink: 0;
  padding: 0 20px;
  background: rgba(10, 10, 26, 0.95);
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header-left {
  flex: 1;
}

.app-title {
  flex: 1;
  text-align: center;
  font-size: 1.4rem;
  margin: 0;
  color: #00f0ff;
  text-shadow: 0 0 20px rgba(0, 240, 255, 0.5);
  letter-spacing: 4px;
  font-weight: 300;
  white-space: nowrap;
}

.header-right {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.header-right :deep(.search-box) {
  max-width: 350px;
}

:deep(.el-menu) {
  border-bottom: none !important;
}

:deep(.el-menu-item) {
  padding: 0 15px;
  font-size: 14px;
}

:deep(.el-menu-item:hover) {
  background-color: rgba(0, 240, 255, 0.1) !important;
}

:deep(.el-menu-item.is-active) {
  border-bottom: 2px solid #00ff80 !important;
}
</style>
