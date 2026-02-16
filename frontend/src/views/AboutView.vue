<template>
  <div class="about-view">
    <div class="about-card">
      <h1>关于系统</h1>
      <div class="about-content">
        <h2>电网地图系统</h2>
        <p>版本: 1.0.0</p>
        
        <h3>功能介绍</h3>
        <ul>
          <li>变电站管理：在地图上展示变电站位置和状态</li>
          <li>输电线路管理：展示输电线路走向和属性</li>
          <li>台区管理：展示配电变压器供电范围</li>
          <li>图层控制：支持显示/隐藏不同类型的设备</li>
          <li>数据统计：实时显示设备数量统计</li>
        </ul>
        
        <h3>技术栈</h3>
        <ul>
          <li>前端：Vue 3 + TypeScript + Vite + Pinia</li>
          <li>地图：Mapbox GL JS</li>
          <li>UI组件：Element Plus</li>
          <li>后端：Spring Boot 3 + JPA + H2</li>
        </ul>
        
        <h3>开发团队</h3>
        <p>电网信息化研发团队</p>
      </div>
      
      <div class="about-footer">
        <el-button @click="$router.push('/')">返回首页</el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * AboutView.vue - 关于页面
 * 
 * 功能说明：
 * 展示系统信息、功能介绍和技术栈
 */
</script>

<style scoped>
.about-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-sizing: border-box;
}

.about-card {
  background: rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(0, 240, 255, 0.3);
  border-radius: 12px;
  padding: 30px;
  max-width: 600px;
  width: 100%;
  box-shadow: 0 0 20px rgba(0, 240, 255, 0.2);
}

.about-card h1 {
  color: #00f0ff;
  text-align: center;
  margin-bottom: 20px;
  text-shadow: 0 0 10px rgba(0, 240, 255, 0.5);
}

.about-content h2 {
  color: #00f0ff;
  margin-bottom: 10px;
}

.about-content h3 {
  color: #00f0ff;
  margin-top: 20px;
  margin-bottom: 10px;
}

.about-content p {
  color: #e0e0e0;
  margin-bottom: 10px;
}

.about-content ul {
  color: #e0e0e0;
  padding-left: 20px;
}

.about-content li {
  margin-bottom: 8px;
}

.about-footer {
  margin-top: 30px;
  text-align: center;
}

.about-footer .el-button {
  background: rgba(0, 240, 255, 0.2);
  border: 1px solid rgba(0, 240, 255, 0.5);
  color: #00f0ff;
}

.about-footer .el-button:hover {
  background: rgba(0, 240, 255, 0.3);
}
</style>
