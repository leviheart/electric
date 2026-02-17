/**
 * stores/authStore.ts - 认证状态管理
 * 
 * 功能说明：
 * 使用 Pinia 管理用户认证状态
 * 包括 Token 存储、用户信息、登录状态等
 * 
 * 状态说明：
 * - token: JWT Token（存储在 localStorage）
 * - user: 用户信息（用户名、角色）
 * 
 * Getters 说明：
 * - isAuthenticated: 是否已登录（有 Token）
 * - isAdmin: 是否是管理员
 * - username: 获取用户名
 * 
 * Actions 说明：
 * - setToken: 设置 Token（同时保存到 localStorage）
 * - setUser: 设置用户信息
 * - logout: 登出（清除 Token 和用户信息）
 * - getToken: 获取 Token
 * 
 * 持久化说明：
 * Token 和用户信息会自动保存到 localStorage
 * 页面刷新后会自动恢复状态
 * 
 * 文件关联：
 * - main.ts: 注册 Pinia
 * - views/LoginView.vue: 登录时设置状态
 * - api/request.ts: 读取 Token 添加到请求头
 * - router/index.ts: 读取 Token 判断登录状态
 * 
 * 新人提示：
 * - localStorage 是浏览器的本地存储，刷新页面后数据不会丢失
 * - Token 存储在 localStorage 中，关闭浏览器后仍然存在
 * - 登出时需要清除 localStorage 中的数据
 */

import { defineStore } from 'pinia'

interface User {
  username: string
  role: string
}

interface AuthState {
  token: string | null
  user: User | null
}

// localStorage 存储键名
const TOKEN_KEY = 'electric_grid_token'
const USER_KEY = 'electric_grid_user'

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem(TOKEN_KEY),
    user: (() => {
      try {
        const saved = localStorage.getItem(USER_KEY)
        return saved ? JSON.parse(saved) : null
      } catch {
        localStorage.removeItem(USER_KEY)
        return null
      }
    })()
  }),

  /**
   * 计算属性
   */
  getters: {
    /**
     * 是否已认证（已登录）
     * @param state 状态对象
     * @return 是否有 Token
     */
    isAuthenticated: (state) => !!state.token,
    
    /**
     * 是否是管理员
     * @param state 状态对象
     * @return 角色是否为 ADMIN
     */
    isAdmin: (state) => state.user?.role === 'ADMIN',
    
    /**
     * 获取用户名
     * @param state 状态对象
     * @return 用户名或空字符串
     */
    username: (state) => state.user?.username || ''
  },

  /**
   * 操作方法
   */
  actions: {
    /**
     * 设置 Token
     * 同时保存到 localStorage 和 state
     * @param token JWT Token
     */
    setToken(token: string) {
      this.token = token
      localStorage.setItem(TOKEN_KEY, token)
    },

    /**
     * 设置用户信息
     * 同时保存到 localStorage 和 state
     * @param user 用户信息对象
     */
    setUser(user: User) {
      this.user = user
      localStorage.setItem(USER_KEY, JSON.stringify(user))
    },

    /**
     * 登出
     * 清除 Token 和用户信息
     * 同时清除 localStorage 中的数据
     */
    logout() {
      this.token = null
      this.user = null
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem(USER_KEY)
    },

    /**
     * 获取 Token
     * @return JWT Token 或 null
     */
    getToken(): string | null {
      return this.token
    }
  }
})
