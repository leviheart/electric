/**
 * main.ts - 前端应用入口文件
 * 
 * 功能说明：
 * 1. 导入Vue 3的createApp函数，用于创建Vue应用实例
 * 2. 导入根组件App.vue
 * 3. 导入Element Plus UI组件库及其样式
 * 4. 导入Pinia状态管理
 * 5. 导入Vue Router路由
 * 6. 创建Vue应用实例并挂载到DOM元素
 * 
 * 文件关联：
 * - 根组件：导入并使用App.vue作为应用的根组件
 * - UI库：导入并注册Element Plus组件库
 * - 状态管理：导入并注册Pinia
 * - 路由：导入并注册Vue Router
 * - 依赖配置：对应package.json中的依赖
 */

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(ElementPlus)

app.mount('#app')
