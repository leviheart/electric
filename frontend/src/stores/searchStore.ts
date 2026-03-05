import { defineStore } from 'pinia'
import type { Substation, TransmissionLine, Area } from '../types'
import type { Alert } from '../types/alert'

interface SearchResult {
  type: string
  data: Substation | TransmissionLine | Area | Alert
}

export const useSearchStore = defineStore('search', {
  state: () => ({
    selectedResult: null as SearchResult | null
  }),
  
  actions: {
    selectResult(result: SearchResult) {
      this.selectedResult = result
    },
    
    clearResult() {
      this.selectedResult = null
    }
  }
})
