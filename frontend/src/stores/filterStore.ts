import { defineStore } from 'pinia'

export const useFilterStore = defineStore('filter', {
  state: () => ({
    voltageLevel: '',
    status: ''
  }),
  
  actions: {
    setVoltageLevel(value: string) {
      this.voltageLevel = value
    },
    
    setStatus(value: string) {
      this.status = value
    },
    
    setFilter(voltageLevel: string, status: string) {
      this.voltageLevel = voltageLevel
      this.status = status
    },
    
    clearFilters() {
      this.voltageLevel = ''
      this.status = ''
    }
  }
})
