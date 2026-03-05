<template>
  <div class="region-selector">
    <el-dropdown trigger="click" @command="handleSelect" placement="bottom-start">
      <div class="selector-btn">
        <span>{{ displayText }}</span>
        <el-icon class="arrow"><ArrowDown /></el-icon>
      </div>
      <template #dropdown>
        <el-dropdown-menu class="region-menu">
          <el-dropdown-item 
            v-if="currentRegion && currentRegion.level !== 'country'"
            :command="getParentCode()"
            class="back-item"
          >
            <el-icon><ArrowLeft /></el-icon>
            <span>返回上级</span>
          </el-dropdown-item>
          <el-dropdown-item 
            :command="currentRegion?.code || 'CN'"
            :class="{ 'is-active': true }"
            class="current-item"
          >
            <span>{{ currentRegion?.name || '全网' }}</span>
          </el-dropdown-item>
          <el-dropdown-item 
            v-for="item in childRegions"
            :key="item.code"
            :command="item.code"
          >
            <span>{{ item.name }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { ArrowDown, ArrowLeft } from '@element-plus/icons-vue'
import { regionData, findRegionByCode, findRegionByAdcode, getRegionPath, type Region } from '../data/regions'

const props = defineProps<{
  modelValue?: string
}>()

const emit = defineEmits<{
  'update:modelValue': [code: string]
  'change': [region: Region]
}>()

const currentCode = ref('CN')
const currentRegion = ref<Region>(regionData)

const displayText = computed(() => {
  return currentRegion.value?.name || '全网'
})

const childRegions = computed((): Region[] => {
  return currentRegion.value?.children || []
})

const getParentCode = (): string => {
  const path = getRegionPath(currentCode.value)
  if (!path || path.length <= 1) return 'CN'
  return path[path.length - 2]?.code || 'CN'
}

const handleSelect = (code: string) => {
  if (code === currentCode.value) {
    return
  }
  
  const region = findRegionByCode(code)
  if (!region) return
  
  currentCode.value = code
  currentRegion.value = region
  
  emit('update:modelValue', code)
  emit('change', region)
}

watch(() => props.modelValue, (code) => {
  if (code) {
    const region = findRegionByCode(code)
    if (region) {
      currentCode.value = code
      currentRegion.value = region
    }
  }
}, { immediate: true })
</script>

<style scoped>
.region-selector {
  display: flex;
  align-items: center;
}

.selector-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.15), rgba(0, 128, 255, 0.1));
  border: 1px solid rgba(0, 240, 255, 0.5);
  border-radius: 6px;
  color: #00f0ff;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  min-width: 140px;
}

.selector-btn:hover {
  background: linear-gradient(135deg, rgba(0, 240, 255, 0.25), rgba(0, 128, 255, 0.2));
  border-color: #00f0ff;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.3);
}

.selector-btn .arrow {
  font-size: 12px;
  transition: transform 0.3s ease;
}
</style>

<style>
.region-menu {
  background: rgba(10, 10, 26, 0.98) !important;
  border: 1px solid rgba(0, 240, 255, 0.4) !important;
  border-radius: 8px !important;
  max-height: 450px !important;
  overflow-y: auto !important;
  min-width: 180px !important;
  padding: 8px 0 !important;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5), 0 0 30px rgba(0, 240, 255, 0.1) !important;
}

.region-menu .el-dropdown-menu__item {
  color: rgba(255, 255, 255, 0.9) !important;
  font-size: 14px !important;
  padding: 10px 16px !important;
  line-height: 1.5 !important;
  margin: 2px 8px !important;
  border-radius: 4px !important;
}

.region-menu .el-dropdown-menu__item:hover {
  background: rgba(0, 240, 255, 0.15) !important;
  color: #00f0ff !important;
}

.region-menu .el-dropdown-menu__item.back-item {
  color: rgba(255, 255, 255, 0.6) !important;
  border-bottom: 1px solid rgba(0, 240, 255, 0.2);
  margin-bottom: 8px !important;
  border-radius: 0 !important;
}

.region-menu .el-dropdown-menu__item.current-item {
  background: rgba(0, 240, 255, 0.2) !important;
  color: #00f0ff !important;
  font-weight: 600 !important;
}
</style>
