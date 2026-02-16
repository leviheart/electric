<template>
  <div class="not-found-view">
    <div class="not-found-card">
      <h1>404</h1>
      <h2>页面未找到</h2>
      <p>抱歉，您访问的页面不存在</p>
      <el-button type="primary" @click="$router.push('/')">返回首页</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
/**
 * NotFoundView.vue - 404页面
 * 
 * 功能说明：
 * 当访问不存在的路由时显示此页面
 */
</script>

<style scoped>
.not-found-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.not-found-card {
  text-align: center;
}

.not-found-card h1 {
  font-size: 8rem;
  color: #00f0ff;
  text-shadow: 0 0 30px rgba(0, 240, 255, 0.5);
  margin: 0;
}

.not-found-card h2 {
  font-size: 2rem;
  color: #e0e0e0;
  margin: 10px 0;
}

.not-found-card p {
  color: #a0a0a0;
  margin-bottom: 30px;
}

.not-found-card .el-button--primary {
  background: linear-gradient(135deg, #00f0ff, #0080ff);
  border: none;
  padding: 15px 30px;
  font-size: 1.1rem;
}
</style>
