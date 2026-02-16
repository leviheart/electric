/**
 * stores/authStore.ts - 认证状态管理
 * 
 * 功能说明：
 * 使用 Pinia 管理用户认证状态
 * 包括 Token 存储、用户信息、登录状态等
 * 
 * 文件关联：
 * - main.ts: 注册 Pinia
 * - views/LoginView.vue: 登录时设置状态
 * - api/request.ts: 读取 Token 添加到请求头
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

const TOKEN_KEY = 'electric_grid_token'
const USER_KEY = 'electric_grid_user'

export const useAuthStore = defineStore('auth', {
  state: (): AuthState => ({
    token: localStorage.getItem(TOKEN_KEY),
    user: JSON.parse(localStorage.getItem(USER_KEY) || 'null')
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    isAdmin: (state) => state.user?.role === 'ADMIN',
    username: (state) => state.user?.username || ''
  },

  actions: {
    setToken(token: string) {
      this.token = token
      localStorage.setItem(TOKEN_KEY, token)
    },

    setUser(user: User) {
      this.user = user
      localStorage.setItem(USER_KEY, JSON.stringify(user))
    },

    logout() {
      this.token = null
      this.user = null
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem(USER_KEY)
    },

    getToken(): string | null {
      return this.token
    }
  }
})
