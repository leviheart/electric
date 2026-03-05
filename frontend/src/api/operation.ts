import axios from 'axios'

const API_BASE = '/api'

export const maintenancePlanApi = {
  getAll: () => axios.get(`${API_BASE}/maintenance-plans`),
  getById: (id: number) => axios.get(`${API_BASE}/maintenance-plans/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/maintenance-plans/status/${status}`),
  getActive: () => axios.get(`${API_BASE}/maintenance-plans/active`),
  create: (data: any) => axios.post(`${API_BASE}/maintenance-plans`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/maintenance-plans/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/maintenance-plans/${id}`),
  submit: (id: number) => axios.post(`${API_BASE}/maintenance-plans/${id}/submit`),
  approve: (id: number, approver: string, comment?: string) => 
    axios.post(`${API_BASE}/maintenance-plans/${id}/approve`, null, { params: { approver, comment } }),
  reject: (id: number, approver: string, comment?: string) =>
    axios.post(`${API_BASE}/maintenance-plans/${id}/reject`, null, { params: { approver, comment } }),
  start: (id: number) => axios.post(`${API_BASE}/maintenance-plans/${id}/start`),
  complete: (id: number, result: string) => 
    axios.post(`${API_BASE}/maintenance-plans/${id}/complete`, null, { params: { result } })
}

export const workTicketApi = {
  getAll: () => axios.get(`${API_BASE}/work-tickets`),
  getById: (id: number) => axios.get(`${API_BASE}/work-tickets/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/work-tickets/status/${status}`),
  getActive: () => axios.get(`${API_BASE}/work-tickets/active`),
  create: (data: any) => axios.post(`${API_BASE}/work-tickets`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/work-tickets/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/work-tickets/${id}`),
  submit: (id: number) => axios.post(`${API_BASE}/work-tickets/${id}/submit`),
  approve: (id: number, approver: string) => 
    axios.post(`${API_BASE}/work-tickets/${id}/approve`, null, { params: { approver } }),
  issue: (id: number, issuer: string) =>
    axios.post(`${API_BASE}/work-tickets/${id}/issue`, null, { params: { issuer } }),
  receive: (id: number, receiver: string) =>
    axios.post(`${API_BASE}/work-tickets/${id}/receive`, null, { params: { receiver } }),
  permit: (id: number, permissioner: string) =>
    axios.post(`${API_BASE}/work-tickets/${id}/permit`, null, { params: { permissioner } }),
  end: (id: number, ender: string) =>
    axios.post(`${API_BASE}/work-tickets/${id}/end`, null, { params: { ender } })
}

export const operationTicketApi = {
  getAll: () => axios.get(`${API_BASE}/operation-tickets`),
  getById: (id: number) => axios.get(`${API_BASE}/operation-tickets/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/operation-tickets/status/${status}`),
  getActive: () => axios.get(`${API_BASE}/operation-tickets/active`),
  create: (data: any) => axios.post(`${API_BASE}/operation-tickets`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/operation-tickets/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/operation-tickets/${id}`),
  submit: (id: number) => axios.post(`${API_BASE}/operation-tickets/${id}/submit`),
  approve: (id: number, approver: string) => 
    axios.post(`${API_BASE}/operation-tickets/${id}/approve`, null, { params: { approver } }),
  issue: (id: number, issuer: string) =>
    axios.post(`${API_BASE}/operation-tickets/${id}/issue`, null, { params: { issuer } }),
  start: (id: number) => axios.post(`${API_BASE}/operation-tickets/${id}/start`),
  complete: (id: number, result: string) =>
    axios.post(`${API_BASE}/operation-tickets/${id}/complete`, null, { params: { result } })
}

export const inspectionTaskApi = {
  getAll: () => axios.get(`${API_BASE}/inspection-tasks`),
  getById: (id: number) => axios.get(`${API_BASE}/inspection-tasks/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/inspection-tasks/status/${status}`),
  getActive: () => axios.get(`${API_BASE}/inspection-tasks/active`),
  create: (data: any) => axios.post(`${API_BASE}/inspection-tasks`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/inspection-tasks/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/inspection-tasks/${id}`),
  start: (id: number) => axios.post(`${API_BASE}/inspection-tasks/${id}/start`),
  complete: (id: number, result: string, normalItems: number, abnormalItems: number) =>
    axios.post(`${API_BASE}/inspection-tasks/${id}/complete`, null, { params: { result, normalItems, abnormalItems } })
}

export const defectApi = {
  getAll: () => axios.get(`${API_BASE}/defects`),
  getById: (id: number) => axios.get(`${API_BASE}/defects/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/defects/status/${status}`),
  getActive: () => axios.get(`${API_BASE}/defects/active`),
  create: (data: any) => axios.post(`${API_BASE}/defects`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/defects/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/defects/${id}`),
  handle: (id: number, handler: string, solution: string) =>
    axios.post(`${API_BASE}/defects/${id}/handle`, null, { params: { handler, solution } }),
  complete: (id: number, handlerComment?: string) =>
    axios.post(`${API_BASE}/defects/${id}/complete`, null, { params: { handlerComment } }),
  verify: (id: number, verifier: string, verifyResult: string, verifyComment?: string) =>
    axios.post(`${API_BASE}/defects/${id}/verify`, null, { params: { verifier, verifyResult, verifyComment } })
}

export const operationLogApi = {
  getAll: () => axios.get(`${API_BASE}/operation-logs`),
  getById: (id: number) => axios.get(`${API_BASE}/operation-logs/${id}`),
  getByUsername: (username: string) => axios.get(`${API_BASE}/operation-logs/username/${username}`),
  getByModule: (module: string) => axios.get(`${API_BASE}/operation-logs/module/${module}`),
  getRecent: (days: number = 7) => axios.get(`${API_BASE}/operation-logs/recent`, { params: { days } }),
  getByTimeRange: (start: string, end: string) => 
    axios.get(`${API_BASE}/operation-logs/time-range`, { params: { start, end } }),
  countByModule: () => axios.get(`${API_BASE}/operation-logs/count-by-module`),
  countByDate: (days: number = 30) => axios.get(`${API_BASE}/operation-logs/count-by-date`, { params: { days } }),
  getFailedCount: () => axios.get(`${API_BASE}/operation-logs/failed-count`)
}

export const dispatchOrderApi = {
  getAll: () => axios.get(`${API_BASE}/dispatch-orders`),
  getById: (id: number) => axios.get(`${API_BASE}/dispatch-orders/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/dispatch-orders/status/${status}`),
  getActive: () => axios.get(`${API_BASE}/dispatch-orders/active`),
  create: (data: any) => axios.post(`${API_BASE}/dispatch-orders`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/dispatch-orders/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/dispatch-orders/${id}`)
}

export const powerOutageApi = {
  getAll: () => axios.get(`${API_BASE}/power-outages`),
  getById: (id: number) => axios.get(`${API_BASE}/power-outages/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/power-outages/status/${status}`),
  getActive: () => axios.get(`${API_BASE}/power-outages/active`),
  create: (data: any) => axios.post(`${API_BASE}/power-outages`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/power-outages/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/power-outages/${id}`)
}

export const lineLossApi = {
  getAll: () => axios.get(`${API_BASE}/line-losses`),
  getById: (id: number) => axios.get(`${API_BASE}/line-losses/${id}`),
  getByLineId: (lineId: number) => axios.get(`${API_BASE}/line-losses/line/${lineId}`),
  getHighLoss: (threshold: number) => axios.get(`${API_BASE}/line-losses/high-loss`, { params: { threshold } })
}

export const loadForecastApi = {
  getAll: () => axios.get(`${API_BASE}/load-forecasts`),
  getById: (id: number) => axios.get(`${API_BASE}/load-forecasts/${id}`),
  getByTarget: (targetType: string, targetId: number) => 
    axios.get(`${API_BASE}/load-forecasts/target/${targetType}/${targetId}`),
  getUpcoming: () => axios.get(`${API_BASE}/load-forecasts/upcoming`)
}

export const powerFlowApi = {
  getAll: () => axios.get(`${API_BASE}/power-flows`),
  getById: (id: number) => axios.get(`${API_BASE}/power-flows/${id}`),
  getLatest: () => axios.get(`${API_BASE}/power-flows/latest`),
  calculate: (data: any) => axios.post(`${API_BASE}/power-flows/calculate`, data)
}

export const n1CheckApi = {
  getAll: () => axios.get(`${API_BASE}/n1-checks`),
  getById: (id: number) => axios.get(`${API_BASE}/n1-checks/${id}`),
  getLatest: () => axios.get(`${API_BASE}/n1-checks/latest`),
  perform: (data: any) => axios.post(`${API_BASE}/n1-checks/perform`, data)
}

export const deviceHealthApi = {
  getAll: () => axios.get(`${API_BASE}/device-healths`),
  getById: (id: number) => axios.get(`${API_BASE}/device-healths/${id}`),
  getByDevice: (deviceType: string, deviceId: number) => 
    axios.get(`${API_BASE}/device-healths/device/${deviceType}/${deviceId}`),
  getLowHealth: (threshold: number) => axios.get(`${API_BASE}/device-healths/low-health`, { params: { threshold } }),
  evaluate: (data: any) => axios.post(`${API_BASE}/device-healths/evaluate`, data)
}

export const reliabilityIndexApi = {
  getAll: () => axios.get(`${API_BASE}/reliability-indices`),
  getById: (id: number) => axios.get(`${API_BASE}/reliability-indices/${id}`),
  getByTarget: (targetType: string, targetId: number) =>
    axios.get(`${API_BASE}/reliability-indices/target/${targetType}/${targetId}`),
  calculate: (data: any) => axios.post(`${API_BASE}/reliability-indices/calculate`, data)
}

export const documentApi = {
  getAll: () => axios.get(`${API_BASE}/documents`),
  getById: (id: number) => axios.get(`${API_BASE}/documents/${id}`),
  getByType: (docType: string) => axios.get(`${API_BASE}/documents/type/${docType}`),
  search: (keyword: string) => axios.get(`${API_BASE}/documents/search`, { params: { keyword } }),
  create: (data: any) => axios.post(`${API_BASE}/documents`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/documents/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/documents/${id}`),
  download: (id: number) => axios.get(`${API_BASE}/documents/${id}/download`)
}

export const videoMonitorApi = {
  getAll: () => axios.get(`${API_BASE}/video-monitors`),
  getById: (id: number) => axios.get(`${API_BASE}/video-monitors/${id}`),
  getByStatus: (status: string) => axios.get(`${API_BASE}/video-monitors/status/${status}`),
  getByDevice: (deviceType: string, deviceId: number) =>
    axios.get(`${API_BASE}/video-monitors/device/${deviceType}/${deviceId}`),
  create: (data: any) => axios.post(`${API_BASE}/video-monitors`, data),
  update: (id: number, data: any) => axios.put(`${API_BASE}/video-monitors/${id}`, data),
  delete: (id: number) => axios.delete(`${API_BASE}/video-monitors/${id}`),
  updateStatus: (id: number, status: string) =>
    axios.post(`${API_BASE}/video-monitors/${id}/status`, null, { params: { status } })
}

export const dataInitApi = {
  generateAll: () => axios.post(`${API_BASE}/data-init/generate-all`),
  generateMaintenancePlans: () => axios.post(`${API_BASE}/data-init/generate-maintenance-plans`),
  generateWorkTickets: () => axios.post(`${API_BASE}/data-init/generate-work-tickets`),
  generateOperationTickets: () => axios.post(`${API_BASE}/data-init/generate-operation-tickets`),
  generateInspectionTasks: () => axios.post(`${API_BASE}/data-init/generate-inspection-tasks`),
  generateDefects: () => axios.post(`${API_BASE}/data-init/generate-defects`),
  generateOperationLogs: () => axios.post(`${API_BASE}/data-init/generate-operation-logs`),
  generateDispatchOrders: () => axios.post(`${API_BASE}/data-init/generate-dispatch-orders`),
  generatePowerOutages: () => axios.post(`${API_BASE}/data-init/generate-power-outages`),
  generateLineLosses: () => axios.post(`${API_BASE}/data-init/generate-line-losses`),
  generateLoadForecasts: () => axios.post(`${API_BASE}/data-init/generate-load-forecasts`),
  generatePowerFlows: () => axios.post(`${API_BASE}/data-init/generate-power-flows`),
  generateN1Checks: () => axios.post(`${API_BASE}/data-init/generate-n1-checks`),
  generateDeviceHealths: () => axios.post(`${API_BASE}/data-init/generate-device-healths`),
  generateReliabilityIndices: () => axios.post(`${API_BASE}/data-init/generate-reliability-indices`),
  generateDocuments: () => axios.post(`${API_BASE}/data-init/generate-documents`),
  generateVideoMonitors: () => axios.post(`${API_BASE}/data-init/generate-video-monitors`)
}
