/**
 * router/index.ts - Vue Router 配置
 * 
 * 功能说明：
 * 配置前端路由，支持多页面导航
 * 添加路由守卫，保护需要认证的页面
 * 
 * 路由配置：
 * - /login: 登录页面（无需认证）
 * - /: 首页（地图页面，需要认证）
 * - /about: 关于页面（需要认证）
 * - /settings: 设置页面（需要认证）
 * 
 * 文件关联：
 * - main.ts: 注册路由
 * - views/*.vue: 页面组件
 * - stores/authStore.ts: 认证状态管理
 */

import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/LoginView.vue'),
    meta: {
      title: '登录',
      requiresAuth: false
    }
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/HomeView.vue'),
    meta: {
      title: '电网地图系统',
      requiresAuth: true
    }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: () => import('../views/DashboardView.vue'),
    meta: {
      title: '数据统计',
      requiresAuth: true
    }
  },
  {
    path: '/manage',
    name: 'Manage',
    component: () => import('../views/ManageView.vue'),
    meta: {
      title: '数据管理',
      requiresAuth: true
    }
  },
  {
    path: '/operation',
    name: 'Operation',
    component: () => import('../views/OperationView.vue'),
    meta: {
      title: '运维管理',
      requiresAuth: true
    }
  },
  {
    path: '/analysis',
    name: 'Analysis',
    component: () => import('../views/AnalysisView.vue'),
    meta: {
      title: '分析计算',
      requiresAuth: true
    }
  },
  {
    path: '/dual-power',
    name: 'DualPower',
    component: () => import('../views/DualPowerView.vue'),
    meta: {
      title: '双电源管理',
      requiresAuth: true
    }
  },
  {
    path: '/power-tracing',
    name: 'PowerTracing',
    component: () => import('../views/PowerTracingView.vue'),
    meta: {
      title: '电源追溯',
      requiresAuth: true
    }
  },
  {
    path: '/natural-disaster',
    name: 'NaturalDisaster',
    component: () => import('../views/NaturalDisasterView.vue'),
    meta: {
      title: '自然灾害预警',
      requiresAuth: true
    }
  },
  {
    path: '/resource',
    name: 'Resource',
    component: () => import('../views/ResourceView.vue'),
    meta: {
      title: '资源管理',
      requiresAuth: true
    }
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/AboutView.vue'),
    meta: {
      title: '关于系统',
      requiresAuth: true
    }
  },
  {
    path: '/settings',
    name: 'Settings',
    component: () => import('../views/SettingsView.vue'),
    meta: {
      title: '系统设置',
      requiresAuth: true
    }
  },
  {
    path: '/users',
    name: 'Users',
    component: () => import('../views/UserManagementView.vue'),
    meta: {
      title: '用户管理',
      requiresAuth: true
    }
  },
  {
    path: '/station-diagram/:id',
    name: 'StationDiagram',
    component: () => import('../views/StationDiagramView.vue'),
    meta: {
      title: '站内图',
      requiresAuth: true
    }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFoundView.vue'),
    meta: {
      title: '页面未找到',
      requiresAuth: false
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, _from, next) => {
  document.title = (to.meta.title as string) || '电网地图系统'
  
  const token = localStorage.getItem('electric_grid_token')
  const requiresAuth = to.meta.requiresAuth !== false
  
  if (requiresAuth && !token) {
    next('/login')
  } else if (to.path === '/login' && token) {
    next('/')
  } else {
    next()
  }
})

export default router
