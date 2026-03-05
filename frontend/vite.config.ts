/**
 * Vite 配置文件
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 一、什么是 Vite？
 * ═══════════════════════════════════════════════════════════════════════════
 * 
 * Vite 是新一代前端构建工具，由 Vue.js 作者尤雨溪开发。
 * 
 * 核心特点：
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ 1. 极速开发服务器启动                                                   │
 * │    - 利用浏览器原生 ES 模块，无需打包即可启动                            │
 * │    - 传统工具（Webpack）需要打包所有文件才能启动                         │
 * │                                                                          │
 * │ 2. 即时热更新 (HMR)                                                     │
 * │    - 无论项目多大，热更新速度都很快                                      │
 * │    - 只更新改变的模块，不重新加载整个页面                                │
 * │                                                                          │
 * │ 3. 生产环境使用 Rollup 打包                                             │
 * │    - 代码分割、Tree-shaking 自动优化                                     │
 * │    - 输出体积小、加载快                                                  │
 * └─────────────────────────────────────────────────────────────────────────┘
 * 
 * ═══════════════════════════════════════════════════════════════════════════
 * 二、配置项详解
 * ═══════════════════════════════════════════════════════════════════════════
 */
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  
  define: {
    global: 'globalThis'
  },

  /**
   * resolve - 模块解析配置
   */
  resolve: {
    /**
     * alias - 路径别名
     * ┌─────────────────────────────────────────────────────────────────────────┐
     * │ 将 @ 映射到 src 目录                                                    │
     * │                                                                          │
     * │ 使用前：                                                                 │
     * │ import MyComponent from '../../../components/MyComponent.vue'          │
     * │                                                                          │
     * │ 使用后：                                                                 │
     * │ import MyComponent from '@/components/MyComponent.vue'                 │
     * │                                                                          │
     * │ 好处：                                                                   │
     * │ - 路径更短更清晰                                                         │
     * │ - 移动文件时不需要修改导入路径                                           │
     * └─────────────────────────────────────────────────────────────────────────┘
     */
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },

  /**
   * server - 开发服务器配置
   */
  server: {
    // 开发服务器端口
    port: 5173,
    
    /**
     * proxy - 代理配置
     * ┌─────────────────────────────────────────────────────────────────────────┐
     * │ 为什么需要代理？                                                         │
     * │                                                                          │
     * │ 开发环境：                                                               │
     * │ - 前端运行在 http://localhost:5173                                      │
     * │ - 后端运行在 http://localhost:8080                                      │
     * │ - 直接请求后端会有跨域问题（CORS）                                       │
     * │                                                                          │
     * │ 代理原理：                                                               │
     * │ - 前端请求 http://localhost:5173/api/xxx                                │
     * │ - Vite 代理转发到 http://localhost:8080/api/xxx                         │
     * │ - 浏览器认为是同源请求，不会有跨域问题                                   │
     * │                                                                          │
     * │ changeOrigin: true                                                      │
     * │ - 修改请求头中的 Origin 为目标 URL                                       │
     * │ - 有些后端服务会检查 Origin                                             │
     * └─────────────────────────────────────────────────────────────────────────┘
     */
    proxy: {
      // API 请求代理
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      },
      // WebSocket 代理
      '/ws': {
        target: 'ws://localhost:8080',
        ws: true  // 启用 WebSocket 代理
      }
    }
  },

  /**
   * build - 生产构建配置
   */
  build: {
    // 输出目录
    outDir: 'dist',
    // 静态资源目录
    assetsDir: 'assets',
    // 生成 source map 便于调试（生产环境可关闭）
    sourcemap: false,
    // 代码分割策略
    rollupOptions: {
      output: {
        // 分包策略：将第三方库单独打包
        manualChunks: {
          'vue-vendor': ['vue', 'vue-router', 'pinia'],
          'mapbox': ['mapbox-gl']
        }
      }
    }
  }
})
