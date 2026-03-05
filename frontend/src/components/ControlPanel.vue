<template>
  <div class="control-panel">
    <div class="control-group">
      <el-tooltip content="加载数据" placement="right">
        <el-button type="primary" @click="$emit('load')" circle>
          <el-icon><Download /></el-icon>
        </el-button>
      </el-tooltip>
      
      <el-tooltip content="清空地图" placement="right">
        <el-button @click="$emit('clear')" circle>
          <el-icon><Delete /></el-icon>
        </el-button>
      </el-tooltip>
    </div>
    
    <div class="control-divider"></div>
    
    <div class="control-group">
      <el-tooltip content="变电站图层" placement="right">
        <el-button 
          :type="layerVisibility.substations ? 'primary' : 'default'" 
          @click="$emit('toggle', 'substations')"
          circle
        >
          <el-icon><Lightning /></el-icon>
        </el-button>
      </el-tooltip>
      
      <el-tooltip content="输电线路图层" placement="right">
        <el-button 
          :type="layerVisibility.lines ? 'primary' : 'default'" 
          @click="$emit('toggle', 'lines')"
          circle
        >
          <el-icon><Connection /></el-icon>
        </el-button>
      </el-tooltip>
      
      <el-tooltip content="台区图层" placement="right">
        <el-button 
          :type="layerVisibility.areas ? 'primary' : 'default'" 
          @click="$emit('toggle', 'areas')"
          circle
        >
          <el-icon><Location /></el-icon>
        </el-button>
      </el-tooltip>
    </div>
    
    <div class="control-divider"></div>
    
    <div class="control-group">
      <el-tooltip content="地图动画" placement="right">
        <el-button @click="$emit('animate')" circle>
          <el-icon><VideoPlay /></el-icon>
        </el-button>
      </el-tooltip>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Download, Delete, VideoPlay, Lightning, Connection, Location } from '@element-plus/icons-vue'

defineProps<{
  layerVisibility: {
    substations: boolean
    lines: boolean
    areas: boolean
  }
}>()

defineEmits<{
  load: []
  clear: []
  toggle: [layerType: string]
  animate: []
}>()
</script>

<style scoped>
.control-panel {
  position: absolute;
  left: 15px;
  top: 50%;
  transform: translateY(-50%);
  z-index: 10;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
  padding: 10px;
  background: rgba(10, 10, 26, 0.85);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 10px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(10px);
}

.control-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
}

.control-divider {
  width: 28px;
  height: 1px;
  background: rgba(0, 240, 255, 0.2);
  margin: 2px 0;
}

:deep(.el-button) {
  width: 36px !important;
  height: 36px !important;
  min-width: 36px !important;
  padding: 0 !important;
  display: inline-flex !important;
  align-items: center !important;
  justify-content: center !important;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(0, 240, 255, 0.3);
  color: #00f0ff;
  transition: all 0.3s ease;
  margin: 0 !important;
}

:deep(.el-button:hover) {
  background: rgba(0, 240, 255, 0.2);
  border-color: #00f0ff;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.3);
}

:deep(.el-button--primary) {
  background: linear-gradient(135deg, #00f0ff, #0080ff) !important;
  border: none !important;
  box-shadow: 0 0 15px rgba(0, 240, 255, 0.5);
  color: #0a0a1a !important;
}

:deep(.el-button--primary:hover) {
  box-shadow: 0 0 25px rgba(0, 240, 255, 0.8);
}

:deep(.el-icon) {
  font-size: 16px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
</style>
