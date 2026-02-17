/**
 * types/index.ts - TypeScript 类型定义文件
 * 
 * 功能说明：
 * 定义前端项目中使用的所有 TypeScript 接口和类型
 * 确保类型安全，提高代码可维护性
 * 
 * 文件关联：
 * - App.vue: 使用这些类型定义
 * - api/*.ts: API 请求和响应使用这些类型
 */

/**
 * API 统一响应格式
 * 与后端 ApiResponse 类对应
 */
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

/**
 * 变电站数据类型
 * 与后端 Substation 实体类对应
 */
export interface Substation {
  id: number
  name: string
  latitude: number
  longitude: number
  voltageLevel: string
  status: string
}

/**
 * 输电线路数据类型
 * 与后端 TransmissionLine 实体类对应
 */
export interface TransmissionLine {
  id: number
  name: string
  startSubstation: string
  endSubstation: string
  length: number
  voltageLevel: string
  status: string
  geometry: string
}

/**
 * 台区数据类型
 * 与后端 Area 实体类对应
 */
export interface Area {
  id: number
  name: string
  substationName: string
  customerCount: number
  status: string
  geometry: string
}

/**
 * 地图统计数据类型
 */
export interface MapStats {
  substations: number
  lines: number
  areas: number
}

/**
 * 图层可见性状态类型
 */
export interface LayerVisibility {
  substations: boolean
  lines: boolean
  areas: boolean
}

/**
 * API 错误响应类型
 */
export interface ApiError {
  code: number
  message: string
  data?: Record<string, string>
}

/**
 * GeoJSON 坐标类型
 */
export type Coordinate = [number, number]

/**
 * GeoJSON LineString 类型
 */
export interface LineStringGeometry {
  type: 'LineString'
  coordinates: Coordinate[]
}

/**
 * GeoJSON Polygon 类型
 */
export interface PolygonGeometry {
  type: 'Polygon'
  coordinates: Coordinate[][]
}

/**
 * GeoJSON Feature 类型
 */
export interface GeoJsonFeature<T = LineStringGeometry | PolygonGeometry> {
  type: 'Feature'
  properties: Record<string, any>
  geometry: T
}

/**
 * ═══════════════════════════════════════════════════════════════════════════
 * 用户相关类型定义
 * ═══════════════════════════════════════════════════════════════════════════
 */

/**
 * 用户信息类型
 * 与后端 UserInfo DTO 对应
 * 
 * TypeScript 接口说明：
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ interface 定义对象的结构，与 Java 的 class 类似                          │
 * │                                                                          │
 * │ 与 Java 的区别：                                                         │
 * │ - Java: class UserInfo { private String username; ... }                 │
 * │ - TS:   interface User { username: string; ... }                        │
 * │                                                                          │
 * │ 类型注解：                                                               │
 * │ - username: string  → 字段名为 username，类型为字符串                    │
 * │ - enabled: boolean  → 字段名为 enabled，类型为布尔值                     │
 * │ - role?: string     → ? 表示可选字段，可以是 string 或 undefined         │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
export interface User {
  username: string
  role: string
  enabled: boolean
}

/**
 * 登录请求类型
 * 与后端 LoginRequest DTO 对应
 */
export interface LoginRequest {
  username: string
  password: string
}

/**
 * 登录响应类型
 * 与后端 LoginResponse DTO 对应
 */
export interface LoginResponse {
  token: string
  username: string
  role: string
  expiresIn: number
}
